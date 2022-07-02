package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.Departments;
import com.example.entity.Employees;
import com.example.model.InformationsParam;
import com.example.service.DepartmentsService;

@Controller
public class DepartmentsController {
	@Autowired
	private DepartmentsService departmentsService;

	@GetMapping("/dept/{dept_id}")
	public String dept(@PathVariable Long dept_id, @ModelAttribute InformationsParam iParam, Model model) {
		Departments depts = departmentsService.deptNo(dept_id);
		model.addAttribute("depts", depts);

		model.addAttribute("iParam", iParam);

		List<InformationsParam> ipList = departmentsService.info(iParam, dept_id);
		model.addAttribute("ipList", ipList);
		return "user/dept";
	}

	@PostMapping("/retire/{dept_id}/{id}")
	public String retire(@PathVariable Long dept_id, @PathVariable Long id){
		Employees emp = departmentsService.getEmpId(id);
		if (emp.getStatus().equals("在籍")) {
			emp.setStatus("退職");
			departmentsService.retire(id);
		}
		return "redirect:/dept/{dept_id}";
	}
}
