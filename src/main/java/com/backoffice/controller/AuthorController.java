package com.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backoffice.aop.LogActivity;
import com.backoffice.model.AuthorVO;
import com.backoffice.service.AuthorService;

@Controller
@RequestMapping("/admin/author")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;
	
	// 1. 저자 목록 페이지 이동
    @LogActivity("저자 목록 조회 화면 접속")
    @GetMapping("/list")
    public String authorList(Model model) {
        model.addAttribute("list", authorService.getAuthorList());
        return "admin/author/list";
    }

    // 2. 저자 등록 화면으로 이동 (GET)
    @LogActivity("저자 등록 화면 접속")
    @GetMapping("/register")
    public String registerForm() {
        return "admin/author/register";
    }

    // 3. 실제 저자 등록 처리 (POST)
    @LogActivity("새 저자 DB 등록 처리")
    @PostMapping("/register")
    public String registerProcess(AuthorVO author) {
        authorService.registerAuthor(author);
        return "redirect:/admin/author/list";
    }

    // 4. 저자 수정 화면으로 이동 (GET)
    @LogActivity("저자 수정 화면 접속")
    @GetMapping("/modify")
    public String modifyForm(Long author_id, Model model) {
        model.addAttribute("author", authorService.getAuthor(author_id));
        return "admin/author/modify";
    }

    // 5. 실제 저자 수정 처리 (POST)
    @LogActivity("저자 정보 수정 처리")
    @PostMapping("/modify")
    public String modifyProcess(AuthorVO author) {
        authorService.modifyAuthor(author);
        return "redirect:/admin/author/list";
    }

    // 6. 저자 삭제 처리 (POST)
    @LogActivity("저자 삭제 처리")
    @PostMapping("/delete")
    public String deleteProcess(Long author_id) {
        authorService.removeAuthor(author_id);
        return "redirect:/admin/author/list";
    }

}
