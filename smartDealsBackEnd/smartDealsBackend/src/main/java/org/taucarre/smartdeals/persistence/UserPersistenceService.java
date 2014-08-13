package org.taucarre.smartdeals.persistence;

import java.util.List;

import org.taucarre.smartdeals.entite.user.User;

public interface UserPersistenceService {

	public User ajouterUser(User user);
	public User modifierUser(User user);
	public void supprimerUser(User user);
	public User getUserById(Long id);
	public User getUserbyLogin(String login);
	public List<User> getAllUsers();
	
}
