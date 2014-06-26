package org.taucarre.smartdeals.persistence.datastore;

import org.taucarre.smartdeals.entite.deal.Deal;
import org.taucarre.smartdeals.entite.user.User;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;


public class ObjectifyDAO {

	static { ObjectifyService.register(Deal.class);
			 ObjectifyService.register(User.class);
	 		 }

	static Objectify oby() { return ObjectifyService.ofy(); };
}
