package org.taucarre.smartdeals.persistence.datastore;

import static org.taucarre.smartdeals.persistence.datastore.ObjectifyDAO.oby;

import java.util.List;
import java.util.logging.Logger;

import org.taucarre.smartdeals.entite.user.User;
import org.taucarre.smartdeals.persistence.UserPersistenceService;

import com.google.appengine.api.datastore.Entity;
import com.googlecode.objectify.Key;

public class UserPersistenceServiceImpl implements UserPersistenceService {

	private static final String TAG = UserPersistenceServiceImpl.class.getSimpleName();
	private Logger logger = Logger.getLogger(TAG);
	
	@Override
	public User ajouterUser(User user) {
		Key<User> result = oby().save().entity(user).now();	
		if(result != null){
			logger.info("Sauvegarde user " + user.getLogin() + " : " +  result.toString());
			return getUserbyLogin(user.getLogin());
		}else{
			logger.info("Echec Sauvegarde user " + user.getLogin());
			return null;
		}
		
	}

	@Override
	public User modifierUser(User user) {
		oby().save().entity(user).now();	
		logger.info("Mofication user " + user.getLogin());
		return getUserbyLogin(user.getLogin());
		
	}

	@Override
	public void supprimerUser(User user) {
		oby().delete().entity(user).now();
		logger.info("Suppression user " + user.getLogin());
		
	}

	@Override
	public User getUserById(Long id) {
		String webSafeString = Key.create(null, Entity.class,id.longValue()).toString();
		Key<User> key = Key.create(webSafeString);
		return oby().load().key(key).now();
	}

	@Override
	public User getUserbyLogin(String login) {
		User user = oby().load().type(User.class).filter("login", login).first().now();
		if(user != null){
			logger.info("Consultation User : " + login +"/"+  user.getIdUser());
		}else{
			logger.info("Echec consultation User " + login);
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		return oby().load().type(User.class).list();
	}

}
