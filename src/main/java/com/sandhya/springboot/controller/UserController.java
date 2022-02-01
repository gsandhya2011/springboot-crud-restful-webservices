package com.sandhya.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sandhya.springboot.entity.User;
import com.sandhya.springboot.exception.ResourceNotFoundException;
import com.sandhya.springboot.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	//get all users
	@GetMapping
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value="id") long userId) {
		return this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with id :" +userId));
	}
	
	//create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}
	
	//update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable (value="id") long userId) {
		User existing = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with id :"+userId));
		existing.setFirstName(user.getFirstName());
		existing.setLastName(user.getLastName());
		existing.setEmail(user.getEmail());
		return this.userRepository.save(existing);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable (value="id") long userId) {
		User existing = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with id :"+userId));
		this.userRepository.delete(existing);
		return ResponseEntity.ok().build();
	}
}
