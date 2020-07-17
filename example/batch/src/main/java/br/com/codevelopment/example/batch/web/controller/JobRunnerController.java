package br.com.codevelopment.example.batch.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JobRunnerController {
	
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
}
