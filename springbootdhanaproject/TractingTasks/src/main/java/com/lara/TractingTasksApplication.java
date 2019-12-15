package com.lara;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lara.model.Users;
import com.lara.service.UserService;


@SpringBootApplication
public class TractingTasksApplication implements CommandLineRunner {
	
	public static Map<String, Integer> map=null;
	
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(TractingTasksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		getUsersisAdmin();
	}
	
	private void getUsersisAdmin() {
		List<Users> users=userService.getAllUsers();
	    map= new HashMap<String, Integer>();
		for(Users user:users) {
			map.put(user.getUsername(), user.getId());
		}
		
		System.out.println("map details");
		System.out.println(map);
		
		
	}

}
