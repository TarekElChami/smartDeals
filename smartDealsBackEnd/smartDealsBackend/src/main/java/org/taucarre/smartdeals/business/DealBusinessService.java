package org.taucarre.smartdeals.business;

import java.util.List;

import org.taucarre.smartdeals.business.exceptions.SmartDealsException;
import org.taucarre.smartdeals.entite.deal.Deal;

public interface DealBusinessService {

	public  Deal consulterDeal(String nomDeal);
	public void ajouterDeal(Deal deal);
	public void mettreAjourDeal(Deal deal);
	public void supptimerDeal(String nomDeal) throws SmartDealsException;
	public List<Deal> recupererTousLesDeals();
	
}
