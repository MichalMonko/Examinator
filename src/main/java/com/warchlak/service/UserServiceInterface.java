package com.warchlak.service;

import com.warchlak.DTO.UserDTO;
import com.warchlak.entity.User;
import com.warchlak.entity.UserPendingPassword;
import com.warchlak.entity.ValidationToken;

public interface UserServiceInterface
{
	User saveUser(UserDTO userDTO);
	
	void updateUser(User user);
	
	User getUserByEmail(String email);
	
	User getUserByUsername(String username);
	
	void createValidationToken(User user, String token);
	
	ValidationToken getValidationToken(String token);
	
	void updateUserToken(String userEmail, String token);
	
	void registerUser(UserDTO userDTO, String applicationUrl);
	
	void sendPasswordChangeConfirmationLink(User user,String token, String applicationUrl);
	
	void resendUserToken(User user,String token, String applicationUrl);
	
	void updateUserToken(User user, String newToken, ValidationToken.TOKEN_TYPE tokenType);
	
	void saveUserPendingPassword(User user, String newPassword);
	
	UserPendingPassword getUserPendingPassword(User user);
}
