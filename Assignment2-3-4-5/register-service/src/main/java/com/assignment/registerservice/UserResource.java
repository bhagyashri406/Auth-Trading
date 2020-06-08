package com.assignment.registerservice;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserResource {

	@Autowired
	UserRepository userRepository;



	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public User save(@Valid @RequestBody User user) {
		 User userSave = userRepository.save(user);	
		 System.out.println("user save"  + userSave.getId()+ userSave.getEmail());
		return userSave;
	}


	@GetMapping(path = "/displayAll")
	public Iterable<User> RetrieveAllUser() {

		return userRepository.findAll();
	}

	@GetMapping(path = "/getUserById/{id}")
	public User GetUserById(@PathVariable Integer id) {

		Optional<User> user = userRepository.findById(id);
		 System.out.println("user getDetails"  + user.get().getId()+ user.get().getEmail());
		return user.get();
	}


	@PutMapping(path = "/user")
	public String UpdateUser(@Valid @RequestBody User user) {

		Optional<User> details = userRepository.findById(user.getId());

		if (details != null) {

			userRepository.save(user);
			return "User Details Updated";
		} else {
			return "User is not exists of id :" + user.getId();
		}
	}

	@DeleteMapping(path = "/delete/{id}")
	public String DeleteUserById(@PathVariable Integer id) {

		userRepository.deleteById(id);
		return "User Details Deleted";
	}
}
