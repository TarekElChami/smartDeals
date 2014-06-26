package org.taucarre.smartdeals.controler;

import javax.inject.Named;

import org.taucarre.smartdeals.business.UserBusinessService;
import org.taucarre.smartdeals.entite.user.User;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

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
		
	}
	
	@ApiMethod(name="getUserByName",httpMethod= "get")
	public User getDealByName(@Named("login") String login){
		return userBusinessService.getUserByLogin(login);
	}
}
