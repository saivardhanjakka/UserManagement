package com.sv.management.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sv.management.dto.QuoteApiResponseDto;
import com.sv.management.dto.ResetPwdDto;
import com.sv.management.dto.UsersDto;
import com.sv.management.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index(Model model) {
		UsersDto userDto = new UsersDto();
		model.addAttribute("user", userDto);
		return "index";

	}

	@PostMapping("/login")
	public String loginCheck(@ModelAttribute("user") UsersDto user, Model model) {

		UsersDto userDto = userService.login(user.getEmail(), user.getPassword());

		if (userDto == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "index";
		}
		Boolean pwdUpdated = userDto.getIsPwdResetDone();
		if (!pwdUpdated) {
			ResetPwdDto resetPwdDto = new ResetPwdDto();
			model.addAttribute("resetPwd", resetPwdDto);
			return "reset-password";
		} else {
			QuoteApiResponseDto dashBoardDto = userService.buildDashBoard();
			model.addAttribute("dashBoardInfo", dashBoardDto);
			return "dashboard";
		}
	}

	@PostMapping("/resetPwd")
	public String resetPwd(@ModelAttribute("resetPwd") ResetPwdDto resetPwdDto, Model model) {
		UsersDto user= userService.getUserByEmail(resetPwdDto.getEmail());
		
		if(user !=null && !user.getPassword().equals(resetPwdDto.getOldPassword())) {
			model.addAttribute("emsg","old password is incorrect");
			
		}
		if(!resetPwdDto.getNewPassword().equals(resetPwdDto.getConfirmPassword())) {
			model.addAttribute("emsg","new pwd and confirm pwd not same");	
		}
		boolean isPwdReset = userService.resetPassword(resetPwdDto);
		if (isPwdReset) {
			model.addAttribute("msg", "Password changed successfully. Please login");
			
		}
		return "reset-password";

	}

	@GetMapping("/register")
	public String openRegisterPage(Model model) {
		UsersDto userDto = new UsersDto();
		model.addAttribute("user", userDto);

		Map<Integer, String> countries = userService.getCountries();
		model.addAttribute("countries", countries);

		return "register";
	}

	@GetMapping("/states")
	@ResponseBody
	public Map<Integer, String> getStates(@RequestParam("countryId") Integer countryId) {
		return userService.getStatesByCountryId(countryId);
	}

	@GetMapping("/cities")
	@ResponseBody
	public Map<Integer, String> getCities(@RequestParam("stateId") Integer stateId) {
		return userService.getCitiesByStateId(stateId);
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") UsersDto userDto, Model model) {
		boolean isUnique = userService.isEmailUnique(userDto.getEmail());

		if (!isUnique) {
			model.addAttribute("emsg", "Email already exists");
		}
		boolean isRegistered = userService.registerUser(userDto);
		if (isRegistered) {
			model.addAttribute("msg", "Registration Successful. Please check your email for password.");

		} else {
			model.addAttribute("emsg", "Registration Failed");
		}

		return "register";

	}

	@GetMapping("/dashboard")
	public String loadDashboard(Model model) {
		QuoteApiResponseDto quoteData = userService.buildDashBoard();
		model.addAttribute("dashBoardInfo", quoteData);
		return "dashboard";
	}
	@GetMapping("/logout")
	public String logout(Model model) {
		UsersDto userDto= new UsersDto();
		model.addAttribute("user", userDto);
		return "index";
	}
}
