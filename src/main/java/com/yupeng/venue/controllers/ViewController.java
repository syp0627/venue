package com.yupeng.venue.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/auto")
	public String seats() {
		return "auto";
	}

}