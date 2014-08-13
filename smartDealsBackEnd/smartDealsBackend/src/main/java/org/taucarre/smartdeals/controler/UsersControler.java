package org.taucarre.smartdeals.controler;

import javax.inject.Named;

import org.taucarre.smartdeals.business.UserBusinessService;
import org.taucarre.smartdeals.business.exceptions.SmartDealsException;
import org.taucarre.smartdeals.business.impl.UserBusinessServiceImpl;
import org.taucarre.smartdeals.entite.user.User;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;

@Api(
		name ="smartdeals",
		version = "v1",
		scopes = {Constants.EMAIL_SCOPE},
		clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
		audiences = {Constants.ANDROID_AUDIENCE},
		description = "API for manipulating the smart deals app V1 "
		)
public class UsersControler {

	UserBusinessService userBusinessService;
	
	public UsersControler() {
		userBusinessService = new UserBusinessServiceImpl();
	}
	
	@ApiMethod(name="getUserByName",httpMethod= "get")
	public User getUserByName(@Named("login") String login){
		return userBusinessService.getUserByLogin(login);
	}
	
	@ApiMethod(name="insertUser", httpMethod = "post")
	public User insertUser(User user){
		if(user.getIdUser() == null){
			user.setIdUser(new Long(0));
		}
		return userBusinessService.ajouterUser(user);
	}
	
	@ApiMethod(name="updateUser")
	public User updateUser(User user){
		return userBusinessService.mettreAjourUser(user);
	}
	
	@ApiMethod(name="deleteUser", httpMethod="delete")
	public void deleteUser(@Named("login") String login) throws NotFoundException{
		try {
			userBusinessService.supprimerUser(login);
		} catch (SmartDealsException e) {
			throw new NotFoundException(e.getErrorMessage());
		}
	}
}
