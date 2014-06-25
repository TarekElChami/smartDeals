package org.taucarre.smartdeals.business;

import org.taucarre.smartdeals.entite.deal.Deal;

public interface DealBusinessService {

	public  Deal consulterDeal(String nomDeal);
	public  void ajouterDeal(Deal deal);
}
