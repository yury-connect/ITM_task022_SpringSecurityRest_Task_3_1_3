package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {

	@GetMapping()
	public String printWelcome(ModelMap model) {
		model.addAttribute("h1_message", "Hello! This is Task # 3.1.2. (Spring SECURITY)");
		model.addAttribute("h2_message", "Go to the solution page");
		model.addAttribute("h5_message", "Performed by: Yury Lapitski");
		return "sevice-pages/greating_page";
	}
}