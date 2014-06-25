package org.taucarre.smartdeals.persistence.datastore;

import org.taucarre.smartdeals.entite.deal.Deal;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;


public class ObjectifyDAO {

	static { ObjectifyService.register(Deal.class);
	 		 }

	static Objectify oby() { return ObjectifyService.ofy(); };
}
