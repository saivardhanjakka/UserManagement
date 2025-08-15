package com.sv.management.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsersDto {
	private Integer id;
	private String name;
	private String email;
	private String phone;
	private String password;
	private Boolean isPwdResetDone;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
