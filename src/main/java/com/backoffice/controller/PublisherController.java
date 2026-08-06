package com.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backoffice.aop.LogActivity;
import com.backoffice.model.PublisherVO;
import com.backoffice.service.PublisherService;

@Controller
@RequestMapping("/admin/publisher")
public class PublisherController {

	@Autowired
	private PublisherService publisherService;
	
	// 출판사 목록 페이지 이동 및 데이터 전달
	@LogActivity("출판사 목록 조회 화면 접속")
	@GetMapping("/list")
	public String publisherList(Model model) {
		
		model.addAttribute("list", publisherService.getPublisherList());
		
		return "admin/publisher/list";
	}
	
	// 출판사 등록 화면으로 이동(GET방식)
	@LogActivity("출판사 등록 화면 접속")
	@GetMapping("/register")
	public String registerForm() {
		
		return "admin/publisher/register";
	}
	
	// 실제 출판사 등록 처리(Post방식)
	@LogActivity("새 출판사 DB 등록 처리")
	@PostMapping("/register")
	public String registerProcess(PublisherVO publisher) {
		
		publisherService.registerPublisher(publisher);
		
		return "redirect:/admin/publisher/list";
	}
	
	// 출판사 수정 화면으로 이동(GET)
	@LogActivity("출판사 수정 화면 접속")
	@GetMapping("/modify")
	public String modifyForm(Long publisher_id, Model model) {
		
		model.addAttribute("publisher", publisherService.getPublisher(publisher_id));
		return "/admin/publisher/modify";	
	}
	
	// 실제 출판사 수정 처리(POST)
	@LogActivity("출판사 정보 수정 처리")
	@PostMapping("/modify")
	public String modifyProcess(PublisherVO publisher) {
		
		publisherService.modifyPublisher(publisher);
		return "redirect:/admin/publisher/list";
	}
	
	// 출판사 삭제 처리(POST)
	@LogActivity("출판사 삭제 처리")
	@PostMapping("/delete")
	public String deleteProcess(Long publisher_id) {
		publisherService.removePublisher(publisher_id);
		return "redirect:/admin/publisher/list";
	}
}
