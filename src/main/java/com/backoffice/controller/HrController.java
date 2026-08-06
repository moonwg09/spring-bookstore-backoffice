package com.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.backoffice.aop.LogActivity;
import com.backoffice.mapper.EmployeeMapper;
import com.backoffice.mapper.ActivityLogMapper; // 추가
import com.backoffice.model.EmployeeVO;

@Controller
@RequestMapping("/admin/hr")
public class HrController {

    @Autowired
    private EmployeeMapper empMapper;

    @Autowired
    private ActivityLogMapper logMapper; // 추가

    @LogActivity("사원 및 보안 관리 화면 접속")
    @GetMapping("/list")
    public String hrList(Model model) {
        model.addAttribute("empList", empMapper.selectEmployeeList());
        model.addAttribute("logList", logMapper.selectActivityLogList()); // 로그 목록 전달
        return "admin/hr/list";
    }

    @LogActivity("사원 권한(Role) 변경 처리")
    @PostMapping("/role")
    public String modifyRole(@RequestParam("employeeId") Long employeeId,
                             @RequestParam("role") String role) {
        EmployeeVO vo = new EmployeeVO();
        vo.setEmployeeId(employeeId);
        vo.setRole(role);
        
        empMapper.updateEmployeeRole(vo);
        return "redirect:/admin/hr/list";
    }
}