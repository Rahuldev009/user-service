package com.microservice.UserService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.UserService.payloads.UserDto;



public interface UserService {

	UserDto createUser(UserDto userDto);

	List<UserDto> getAllUsers();

	UserDto getUserById(Integer Userid);

	void deleteUserId(Integer UserId);

	UserDto updateTheUser(UserDto userDto, Integer userId);

}
