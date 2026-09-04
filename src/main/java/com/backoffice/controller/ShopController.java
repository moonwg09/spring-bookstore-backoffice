package com.backoffice.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backoffice.model.BookReviewVO;
import com.backoffice.model.BookVO;
import com.backoffice.model.CartVO;
import com.backoffice.model.CategoryVO;
import com.backoffice.model.CustomerOrderVO;
import com.backoffice.model.MemberVO;
import com.backoffice.model.PortOnePaymentVO;
import com.backoffice.service.BookService;
import com.backoffice.service.CartService;
import com.backoffice.service.CategoryService;
import com.backoffice.service.OrderService;
import com.backoffice.service.PortOneService;
import com.backoffice.service.ReviewService;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PortOneService portOneService;
    


    // 1. 쇼핑몰 메인 페이지 (평점순 상품 TOP 4 조회)
    @GetMapping("/main")
    public String mainPage(Model model) {
    	List<BookVO> ratingList = bookService.getBooksByRating(); 
        model.addAttribute("ratingList", ratingList);
        
        // 1. 전체 카테고리 조회
        List<CategoryVO> allCategories = categoryService.getCategoryList();
        
        // 2. 부모-자식 구조로 계층화 처리
        List<CategoryVO> parentCategoryList = new java.util.ArrayList<>();
        java.util.Map<Long, java.util.List<CategoryVO>> childMap = new java.util.HashMap<>();
        
        for (CategoryVO cat : allCategories) {
            if (cat.getParent_id() == null) {
                parentCategoryList.add(cat);
            } else {
                childMap.computeIfAbsent(cat.getParent_id(), k -> new java.util.ArrayList<>()).add(cat);
            }
        }
        
        for (CategoryVO parent : parentCategoryList) {
            parent.setChildren(childMap.get(parent.getCategory_id()));
        }
        
        model.addAttribute("parentCategoryList", parentCategoryList);
        return "shop/main";
    }

 // 2. 도서 상세 페이지 (상세 정보 + 리뷰 목록 함께 조회하도록 수정)
    @GetMapping("/detail")
    public String detailPage(@RequestParam("book_id") Long bookId, Model model) {
        BookVO book = bookService.getBookDetailShop(bookId);
        List<BookReviewVO> reviewList = reviewService.getReviewList(bookId);
        
        model.addAttribute("book", book);
        model.addAttribute("reviewList", reviewList);
        return "shop/detail";
    }

    // 3. 도서 통합 검색 결과 페이지 (제목, 저자, 출판사 조건별 검색)
    @GetMapping("/search")
    public String searchPage(@RequestParam(value="searchType", required=false) String searchType,
                             @RequestParam(value="keyword", required=false) String keyword,
                             Model model) {
        List<BookVO> searchList = bookService.searchBooks(searchType, keyword);
        model.addAttribute("searchList", searchList);
        model.addAttribute("keyword", keyword);
        return "shop/search";
    }
    
 // 4. 장바구니 페이지 진입 (로그인 회원 기준 목록 조회)
    @GetMapping("/cart")
    public String cartPage(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/shop/login";
        }
        
        List<CartVO> cartList = cartService.getCartList(loginUser.getMember_Id());
        model.addAttribute("cartList", cartList);
        return "shop/cart";
    }

    // 5. 장바구니 담기 처리 (상세 페이지에서 호출)
    @PostMapping("/cart/add")
    public String addCartProcess(CartVO cart, HttpSession session) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/shop/login";
        }
        
        cart.setMember_id(loginUser.getMember_Id());
        cartService.addCart(cart);
        return "redirect:/shop/cart";
    }

    // 6. 장바구니 항목 삭제
    @GetMapping("/cart/delete")
    public String deleteCartProcess(@RequestParam("cart_id") Long cart_id) {
        cartService.removeCart(cart_id);
        return "redirect:/shop/cart";
    }
    
 // 7. 주문/결제 페이지 진입
    @GetMapping("/order")
    public String orderPage(HttpSession session, Model model) {

        MemberVO loginUser =
                (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/shop/login";
        }

        List<CartVO> cartList =
                cartService.getCartList(loginUser.getMember_Id());

        long totalSum = 0;

        for (CartVO cart : cartList) {
            totalSum +=
                    (long) cart.getPrice()
                    * cart.getQuantity();
        }

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalSum", totalSum);

        return "shop/order";
    }

    // 8. 결제 및 주문 완료 처리 (POST)
    @PostMapping("/order/pay")
    @ResponseBody
    public String orderPayProcess(
            @RequestBody CustomerOrderVO order,
            HttpSession session) {

    	System.out.println("order imp_uid = [" + order.getImp_uid() + "]");
    	System.out.println("order merchant_uid = [" + order.getMerchant_uid() + "]");
        PortOnePaymentVO payment = null;

        try {

            // 1. PortOne 결제 정보 조회
            payment =
                    portOneService.getPayment(order.getImp_uid());

            if (payment == null) {
                return "FAIL";
            }

            if (!"paid".equals(payment.getStatus())) {
                return "FAIL";
            }

            // 2. 로그인 확인
            MemberVO loginUser =
                    (MemberVO) session.getAttribute("loginUser");

            if (loginUser == null) {

                portOneService.cancelPayment(
                        payment.getImp_uid(),
                        "로그인 정보 없음"
                );

                return "REFUNDED";
            }

            // 3. 장바구니 조회
            List<CartVO> cartList =
                    cartService.getCartList(loginUser.getMember_Id());

            if (cartList == null || cartList.isEmpty()) {

                portOneService.cancelPayment(
                        payment.getImp_uid(),
                        "장바구니 정보 없음"
                );

                return "REFUNDED";
            }

            // 4. 서버 주문 금액 계산
            long serverTotalAmount = 0;

            for (CartVO cart : cartList) {
                serverTotalAmount +=
                        (long) cart.getPrice()
                        * cart.getQuantity();
            }

            // 5. merchant_uid 검증
            if (order.getMerchant_uid() == null
                    || !order.getMerchant_uid()
                            .equals(payment.getMerchant_uid())) {

                portOneService.cancelPayment(
                        payment.getImp_uid(),
                        "주문번호 불일치"
                );

                return "REFUNDED";
            }

            // 6. 금액 검증
            if (payment.getAmount() == null
                    || payment.getAmount().longValue()
                            != serverTotalAmount) {

                portOneService.cancelPayment(
                        payment.getImp_uid(),
                        "결제 금액 불일치"
                );

                return "REFUNDED";
            }

            // 7. 주문 데이터 설정
            order.setTotal_amount(serverTotalAmount);
            order.setImp_uid(payment.getImp_uid());
            order.setMerchant_uid(payment.getMerchant_uid());
            order.setStatus("COMPLETED");

            // 8. DB 주문 처리
            try {

                orderService.processOrder(
                        order,
                        loginUser,
                        cartList
                );

                return "SUCCESS";

            } catch (Exception e) {

                e.printStackTrace();

                portOneService.cancelPayment(
                        payment.getImp_uid(),
                        "주문 처리 실패"
                );

                return "REFUNDED";
            }

        } catch (Exception e) {

            e.printStackTrace();

            // PortOne 결제 정보를 조회한 이후라면 취소 시도
            if (payment != null
                    && "paid".equals(payment.getStatus())) {

                try {
                    portOneService.cancelPayment(
                            payment.getImp_uid(),
                            "서버 처리 오류"
                    );
                } catch (Exception cancelException) {
                    cancelException.printStackTrace();
                }
            }

            return "FAIL";
        }
    }

    // 9. 주문 완료 성공 화면
    @GetMapping("/order/success")
    public String orderSuccessPage() {
        return "shop/order_success";
    }
    
 // 10. 리뷰 등록 처리 (POST)
    @PostMapping("/review/add")
    public String addReviewProcess(com.backoffice.model.BookReviewVO review, HttpSession session) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/shop/login";
        }
        
        review.setMember_id(loginUser.getMember_Id());
        reviewService.registerReview(review);
        return "redirect:/shop/detail?book_id=" + review.getBook_id();
    }

    // 11. 리뷰 삭제 처리
    @GetMapping("/review/delete")
    public String deleteReviewProcess(@RequestParam("review_id") Long review_id, @RequestParam("book_id") Long book_id) {
        reviewService.removeReview(review_id);
        return "redirect:/shop/detail?book_id=" + book_id;
    }
    
 // 마이페이지 (마이룸) 진입 - 회원 정보 및 주문 내역 조회
    @GetMapping("/myroom")
    public String myRoomPage(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/shop/main";
        }
        
        List<CustomerOrderVO> orderList = orderService.getOrderHistory(loginUser.getMember_Id());
        model.addAttribute("orderList", orderList);
        return "shop/myroom";
    }
    
 // 카테고리별 도서 목록 조회 매핑 추가
    @GetMapping("/category")
    public String categoryBooks(@RequestParam("category_id") Long categoryId, Model model) {
        List<BookVO> bookList = bookService.getBooksByCategory(categoryId);
        List<CategoryVO> categoryList = categoryService.getCategoryList();
        
        model.addAttribute("ratingList", bookList); // 같은 그리드 레이아웃 재사용
        model.addAttribute("categoryList", categoryList);
        return "shop/main";
    }
    
    

}