package com.sv.management.service;


import java.util.Map;

import com.sv.management.dto.QuoteApiResponseDto;
import com.sv.management.dto.ResetPwdDto;
import com.sv.management.dto.UsersDto;

public interface UserService {
	public boolean  registerUser(UsersDto userDto);
	 public Map<Integer,String> getCountries();
	 public Map<Integer,String> getCitiesByStateId(Integer stateId);
	
	 public Map<Integer,String> getStatesByCountryId(Integer countryId);
	 public boolean isEmailUnique(String email);
	 public UsersDto login(String email,String password);
	 public boolean resetPassword(ResetPwdDto resetPasswordDto);
	 public QuoteApiResponseDto buildDashBoard();
	 public UsersDto getUserByEmail(String email);

}
