package jwdesign.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webcontroller {

	@GetMapping({"/homepage", "/", "/login"})
	public String getHomePage(){
		return "/homepage";
	}

	@GetMapping("/contact")
	public String getContact(){
		return "/contact";
	}
}
