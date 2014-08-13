package org.taucarre.smartdeals.business.impl;

import java.util.UUID;

import org.taucarre.smartdeals.business.UserBusinessService;
import org.taucarre.smartdeals.business.exceptions.ConstantesExceptions;
import org.taucarre.smartdeals.business.exceptions.SmartDealsException;
import org.taucarre.smartdeals.entite.user.User;
import org.taucarre.smartdeals.persistence.UserPersistenceService;
import org.taucarre.smartdeals.persistence.datastore.UserPersistenceServiceImpl;

public class UserBusinessServiceImpl implements UserBusinessService {

	private UserPersistenceService userPersistenceService;
	
	public UserBusinessServiceImpl() {
		this.userPersistenceService = new UserPersistenceServiceImpl();
	}

	@Override
	public User getUserByLogin(String login) {
		// TODO Auto-generated method stub
		return userPersistenceService.getUserbyLogin(login);
	}

	@Override
	public User ajouterUser(User user) {
		UUID id = UUID.randomUUID();
		user.setIdUser(id.getMostSignificantBits());
		return userPersistenceService.ajouterUser(user);
		
	}

	@Override
	public User mettreAjourUser(User user) {
		return userPersistenceService.modifierUser(user);
		
	}

	@Override
	public void supprimerUser(String login) throws SmartDealsException {
		User user = userPersistenceService.getUserbyLogin(login);
		if(user != null){
			userPersistenceService.supprimerUser(user);
		}else{
			throw new SmartDealsException(ConstantesExceptions.USER_NOT_FOUND_EXCEPTION,
								ConstantesExceptions.USER_NOT_FOUND_EXCEPTION_MESSAGE );
		}
	}

}
