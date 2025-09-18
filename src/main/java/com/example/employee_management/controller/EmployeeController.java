package com.example.employee_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // Hiển thị tất cả employee
    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", service.getAll());
        return "employee-list";
    }

    // Thêm mới employee (form)
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    // Lưu employee
    @PostMapping("/save")
    public String save(@ModelAttribute("employee") Employee e) {
        service.save(e);
        return "redirect:/employees";
    }

    // Sửa employee
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
    Employee emp = service.getById(id);
    model.addAttribute("employee", emp);
    if (emp != null && emp.getDateOfBirth() != null) {
        model.addAttribute("dobValue", emp.getDateOfBirth().toString()); // yyyy-MM-dd
    }
    return "employee-form";
    }

    // Thay đổi trạng thái employee
    @GetMapping("/status/{id}")
    public String changeStatus(@PathVariable Integer id) {
        Employee e = service.getById(id);
        if (e != null) {
            e.setStatus(!e.isStatus());
            service.save(e);
        }
        return "redirect:/employees";
    }
}