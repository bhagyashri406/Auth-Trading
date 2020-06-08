package com.assignment.tradeservice;

import javax.validation.Valid;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "currency-exchange-service" ,url ="localhost:8000" )
@FeignClient(name = "register-service" ,url ="localhost:8081" )
@RibbonClient(name = "register-service")
public interface AuthServiceProxy {

	@PostMapping("/save")
	public User saveUser(@Valid @RequestBody User user);
	
	
	@GetMapping(path = "/getUserById/{id}")
	public User GetUserById(@PathVariable Integer id) ;
}
