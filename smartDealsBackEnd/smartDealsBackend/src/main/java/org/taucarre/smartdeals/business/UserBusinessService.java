package org.taucarre.smartdeals.business;

import org.taucarre.smartdeals.business.exceptions.SmartDealsException;
import org.taucarre.smartdeals.entite.user.User;

public interface UserBusinessService {

	public User getUserByLogin(String login);
	public User ajouterUser(User user);
	public User mettreAjourUser(User user);
	public void supprimerUser(String login) throws SmartDealsException;
	
	
}
