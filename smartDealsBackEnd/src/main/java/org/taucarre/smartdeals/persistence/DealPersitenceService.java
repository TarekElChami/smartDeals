package org.taucarre.smartdeals.persistence;

import org.taucarre.smartdeals.entite.deal.Deal;

public interface DealPersitenceService {

	public Deal fetchDealById(Long id);
	public Deal consulterDealByNom(String nomDeal);
	public void sauvegarderDeal(Deal deal);
	public void modifierDeal(Deal deal);
	public void supprimerDeal(Deal deal);
	
}
