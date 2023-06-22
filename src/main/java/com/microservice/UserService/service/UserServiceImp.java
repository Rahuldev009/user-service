package com.microservice.UserService.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.UserService.entity.Hotel;
import com.microservice.UserService.entity.Rating;
import com.microservice.UserService.entity.User;
import com.microservice.UserService.payloads.UserDto;
import com.microservice.UserService.repository.UserRepository;
import com.microservice.UserServiceexception.ResourceNotFaundException;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private RestTemplate restTemplate;
	private Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		User savedDate = userRepository.save(user);
		return modelMapper.map(savedDate, UserDto.class);

	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> allUsers = userRepository.findAll();
		List<UserDto> allUserSDto = allUsers.stream().map((u) -> modelMapper.map(u, UserDto.class))
				.collect(Collectors.toList());

		return allUserSDto;
	}

	@Override
	public UserDto getUserById(Integer Userid) {
		User user = userRepository.findById(Userid)
				.orElseThrow(() -> new ResourceNotFaundException("User", "id", Userid));
		/*
		 * Rating[] rateOfUsers =
		 * restTemplate.getForObject("http://localhost:1212/user/v1/api/" +
		 * user.getId(), Rating[].class); 
		 logger.info("{} ", rateOfUsers); List<Rating>
		 * ratings = Arrays.stream(rateOfUsers).toList(); ///<Rating> ratingList =
		 * ratings.stream().map(rating -> {
		 * 
		 * // Hotel hotel = restTemplate.getForObject("http://localhost:1515/hotel/api/"
		 * + // rating.getHotelId()); //Hotel hotel =
		 * restTemplate.getForObject("http://localhost:1515/hotel/api/" +
		 * rating.getHotelId(), //Hotel.class, rateOfUsers); //rating.setHotel(hotel);
		 * //return ratings; //}).collect(Collectors.toList());
		 * 
		 * user.setRatings(ratings);
		 */
		Rating[] rateOfUsers = restTemplate.getForObject("http://localhost:1919/ratings/user/" + user.getId(),
				Rating[].class);
		//logger.info("{} ", rateOfUsers);
		//
		List<Rating> ratings = Arrays.stream(rateOfUsers).toList();
		List<Rating> ratingList = ratings.stream().map(rating -> {
			ResponseEntity<Hotel> infoOfHotel = restTemplate
					.getForEntity("http://localhost:1515/hotel/api/" + rating.getHotelId(), Hotel.class);
			Hotel hotel = infoOfHotel.getBody();
			//logger.info("response status code: {} ", infoOfHotel.getStatusCode());
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public void deleteUserId(Integer UserId) {
		User user = userRepository.findById(UserId)
				.orElseThrow(() -> new ResourceNotFaundException("User", "Id", UserId));

		this.userRepository.delete(user);

	}

	@Override
	public UserDto updateTheUser(UserDto userDto, Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFaundException("User", "Id", userId));
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		userRepository.save(user);

		return modelMapper.map(user, UserDto.class);
	}

}
