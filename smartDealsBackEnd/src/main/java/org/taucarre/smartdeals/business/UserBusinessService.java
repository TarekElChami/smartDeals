package org.taucarre.smartdeals.business;

import org.taucarre.smartdeals.entite.user.User;

public interface UserBusinessService {

	public User getUserByLogin(String login);
}
