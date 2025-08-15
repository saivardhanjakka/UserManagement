package com.sv.management.dto;

import lombok.Data;

@Data
public class ResetPwdDto {
	private String email;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;

}
