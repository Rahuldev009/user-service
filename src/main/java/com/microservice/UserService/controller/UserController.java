package com.microservice.UserService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.UserService.payloads.ApiResponse;
import com.microservice.UserService.payloads.UserDto;
import com.microservice.UserService.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "user/v1/api")
public class UserController {
	@Autowired
	private UserService userService;

	// @RequestBody: It is used to bind HTTP request with an object in a method
	// parameter.
	// Internally it uses HTTP MessageConverters to convert the body of the request.
	// When we annotate a method parameter with @RequestBody, the Spring framework
	// binds
	// the incoming HTTP request body to that parameter.

	// API-http://localhost:1212/user/v1/api/create
	@PostMapping(path = "create")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

		UserDto createdUser = userService.createUser(userDto);

		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);

	}

	// API-http://localhost:1212/user/v1/api/getAll
	@GetMapping(path = "/getAll")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		// List<UserDto> allusers = userService.getAllUsers();
		// return new ResponseEntity<List<UserDto>>(allusers, HttpStatus.OK);
		// return ResponseEntity.ok(this.userService.getAllUsers());
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());

	}

	// @PathVariable: It is used to extract the values from the URI.
	// Iefine multiplt is most suitable for the RESTful web service,
	// where the URL contains a path variable. We can de @PathVariable in a method
	// API-http://localhost:1212/user/v1/api/getSingle
	@GetMapping(path = "/{id}")
	public ResponseEntity<UserDto> getUserId(@PathVariable Integer id) {
		UserDto user = userService.getUserById(id);
		// return new ResponseEntity<UserDto>(user, HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));

	}

	// API-http://localhost:1212/user/v1/api/ID
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Integer id) {
		userService.deleteUserId(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);

		
	}

	// API-http://localhost:1212/user/v1/api/
	@PutMapping(path = "{userId}")
	public ResponseEntity<UserDto> updateTheUser( @Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		UserDto updatedUser = userService.updateTheUser(userDto, userId);
		return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);

	}
}
