package com.microservice.UserService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.UserService.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
