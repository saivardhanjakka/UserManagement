package com.sv.management.serviceImpl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sv.management.dto.QuoteApiResponseDto;
import com.sv.management.dto.ResetPwdDto;
import com.sv.management.dto.UsersDto;
import com.sv.management.entity.City;
import com.sv.management.entity.Country;
import com.sv.management.entity.State;
import com.sv.management.entity.User;
import com.sv.management.repository.CityRepository;
import com.sv.management.repository.CountryRepository;
import com.sv.management.repository.StateRepository;
import com.sv.management.repository.UserRepository;
import com.sv.management.service.EmailService;
import com.sv.management.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private EmailService emailService;

	@Override
	public boolean registerUser(UsersDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		user.setIsPwdResetDone(false);
//		Random random = new Random();
//		int number=random.nextInt(10000);
//		String generatePassword=String.valueOf(number);
String generatePassword=generatePwd();
		Country country = countryRepository.findById(userDto.getCountryId()).orElse(null);
		State state = stateRepository.findById(userDto.getStateId()).orElse(null);
		City city = cityRepository.findById(userDto.getCityId()).orElse(null);
		user.setCountry(country);
		user.setCity(city);
		user.setState(state);
		user.setPassword(generatePassword);
		User saveUser = userRepository.save(user);
		emailService.sendEmail(saveUser.getEmail(), generatePassword);
		UsersDto savedUsersDto = modelMapper.map(saveUser, UsersDto.class);

		return savedUsersDto.getId() != null;

	}

	  @Override
	    public Map<Integer, String> getCountries() {
	        List<Country> countries = countryRepository.findAll();
	        Map<Integer, String> map = new HashMap<>();
	        for (Country country : countries) {
	            map.put(country.getCountryId(), country.getName());
	        }
	        return map;
	    }

	@Override
	public Map<Integer, String> getCitiesByStateId(Integer stateId) {
List<City> cities=cityRepository.findByStateStateId(stateId)	;
Map<Integer,String> map= new HashMap<>();
for(City city:cities) {
	map.put(city.getCityId(),city.getName());
}
return map;
	}

	@Override
	public Map<Integer, String> getStatesByCountryId(Integer countryId) {
		List<State> states= stateRepository.findByCountryCountryId(countryId);
		Map<Integer,String> map= new HashMap();
		for(State state:states) {
			map.put(state.getStateId(), state.getName());
		}
		return map;
	}

	@Override
	public boolean isEmailUnique(String email) {
		User user= userRepository.findByEmail(email);
		return user==null;
	}

	@Override
	public UsersDto login(String email, String password) {

		User user = userRepository.findByEmailAndPassword(email, password);
		if (user != null) {

			UsersDto savedUserDto = modelMapper.map(user, UsersDto.class);
			return savedUserDto;

		}

		return null;
	}

	@Override
	public boolean resetPassword(ResetPwdDto resetPasswordDto) {
	    User user = userRepository.findByEmail(resetPasswordDto.getEmail());
	    
	    
	        user.setPassword(resetPasswordDto.getNewPassword());
	        user.setIsPwdResetDone(true);
	        userRepository.save(user);
	        return true; 
	 
	}

	private String generatePwd() {
		StringBuffer buffer = new StringBuffer();
		int pwdlength=5;
		String charPool="ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		Random random = new Random();
		
		for(int i=0;i<pwdlength;i++) {
			int index= random.nextInt(charPool.length());
			char charAt= charPool.charAt(index);
			buffer.append(charAt);
			
		}
		return buffer.toString();
	}
	
	@Override
	public QuoteApiResponseDto buildDashBoard() {
		String apiUrl="https://dummyjson.com/quotes/random";
		RestTemplate template = new RestTemplate();
		ResponseEntity<QuoteApiResponseDto> forEntity=template.getForEntity(apiUrl, QuoteApiResponseDto.class);
		
		 return forEntity.getBody();
	}

	@Override
	public UsersDto getUserByEmail(String email) {
		
		User byEmail = userRepository.findByEmail(email);
		if(byEmail!=null) {
			UsersDto dto = new UsersDto();
			BeanUtils.copyProperties(byEmail, dto);
			return dto;
			
		}
		return null;
	}
	
	


}
