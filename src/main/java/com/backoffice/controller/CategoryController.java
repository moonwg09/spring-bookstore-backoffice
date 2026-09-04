package com.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backoffice.aop.LogActivity;
import com.backoffice.model.CategoryVO;
import com.backoffice.service.CategoryService;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@LogActivity("카테고리 목록 조회 화면 접속")
	@GetMapping("/list")
	public String categoryList(Model model) {
		model.addAttribute("list", categoryService.getCategoryList());
		return "/admin/category/list";
	}
	
	// 2. 카테고리 등록 화면으로 이동 (GET)
    @LogActivity("카테고리 등록 화면 접속")
    @GetMapping("/register")
    public String registerForm(Model model) {
        // 소분류를 등록할 때 '부모 카테고리(대분류)'를 선택할 수 있도록 
        // 전체 카테고리 목록을 함께 보냅니다.
        model.addAttribute("categoryList", categoryService.getCategoryList());
        return "admin/category/register";
    }

    // 3. 실제 카테고리 등록 처리 (POST)
    @LogActivity("새 카테고리 DB 등록 처리")
    @PostMapping("/register")
    public String registerProcess(CategoryVO category) {
        // 대분류일 경우 parent_id가 null(또는 0)로 들어옵니다.
        if (category.getParent_id() != null && category.getParent_id() == 0) {
            category.setParent_id(null);
        }
        categoryService.registerCategory(category);
        return "redirect:/admin/category/list";
    }

    // 4. 카테고리 수정 화면으로 이동 (GET)
    @LogActivity("카테고리 수정 화면 접속")
    @GetMapping("/modify")
    public String modifyForm(Long category_id, Model model) {
        model.addAttribute("category", categoryService.getCategory(category_id));
        model.addAttribute("categoryList", categoryService.getCategoryList()); // 부모 변경용
        return "admin/category/modify";
    }

    // 5. 실제 카테고리 수정 처리 (POST)
    @LogActivity("카테고리 정보 수정 처리")
    @PostMapping("/modify")
    public String modifyProcess(CategoryVO category) {
        if (category.getParent_id() != null && category.getParent_id() == 0) {
            category.setParent_id(null);
        }
        categoryService.modifyCategory(category);
        return "redirect:/admin/category/list";
    }

    // 6. 카테고리 삭제 처리 (POST)
    @LogActivity("카테고리 삭제 처리")
    @PostMapping("/delete")
    public String deleteProcess(Long category_id) {
        categoryService.removeCategory(category_id);
        return "redirect:/admin/category/list";
    }

}

