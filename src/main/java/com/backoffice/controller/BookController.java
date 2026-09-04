package com.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backoffice.aop.LogActivity;
import com.backoffice.model.BookVO;
import com.backoffice.service.AuthorService;
import com.backoffice.service.BookService;
import com.backoffice.service.CategoryService;
import com.backoffice.service.PublisherService;

@Controller
@RequestMapping("/admin/book")
public class BookController {

    @Autowired
    private BookService bookService;
    
    @Autowired
    private PublisherService publisherService;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private AuthorService authorService;

    // 1. 도서 목록 페이지 이동
    @LogActivity("도서 목록 조회 화면 접속")
    @GetMapping("/list")
    public String bookList(Model model) {
        model.addAttribute("list", bookService.getBookList());
        return "admin/book/list";
    }

    // 2. 도서 등록 화면으로 이동 (GET)
    @LogActivity("도서 등록 화면 접속")
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("publisherList", publisherService.getPublisherList());
        model.addAttribute("categoryList", categoryService.getCategoryList());
        model.addAttribute("authorList", authorService.getAuthorList());
        return "admin/book/register";
    }

    // 3. 실제 도서 등록 처리 (POST)
    @LogActivity("새 도서 DB 등록 처리")
    @PostMapping("/register")
    public String registerProcess(BookVO book) {
        bookService.registerBook(book);
        return "redirect:/admin/book/list";
    }

    // 4. 도서 수정 화면으로 이동 (GET)
    @LogActivity("도서 수정 화면 접속")
    @GetMapping("/modify")
    public String modifyForm(Long book_id, Model model) {
        model.addAttribute("book", bookService.getBook(book_id));
        model.addAttribute("publisherList", publisherService.getPublisherList());
        model.addAttribute("categoryList", categoryService.getCategoryList());
        model.addAttribute("authorList", authorService.getAuthorList());
        return "admin/book/modify";
    }

    // 5. 실제 도서 수정 처리 (POST)
    @LogActivity("도서 정보 수정 처리")
    @PostMapping("/modify")
    public String modifyProcess(BookVO book) {
        bookService.modifyBook(book);
        return "redirect:/admin/book/list";
    }

    // 6. 도서 삭제 처리 (POST)
    @LogActivity("도서 삭제 처리")
    @PostMapping("/delete")
    public String deleteProcess(Long book_id) {
        bookService.removeBook(book_id);
        return "redirect:/admin/book/list";
    }
}
