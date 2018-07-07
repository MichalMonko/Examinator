package com.warchlak.factory;

import com.warchlak.DTO.UserDTO;
import com.warchlak.entity.Role;
import com.warchlak.entity.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserFactory
{
	public static User createUser(UserDTO userDTO)
	{
		User user = new User();
		
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		
		Role roleUser = new Role("ROLE_USER");
		List<Role> roles = new ArrayList<>();
		roles.add(roleUser);
		
		user.setRoles(roles);
		
		String encryptedPassword = "{bcrypt}";
		encryptedPassword += BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
		
		user.setPassword(encryptedPassword);
		
		return user;
	}
}
