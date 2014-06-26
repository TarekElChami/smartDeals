package org.taucarre.smartdeals.business.impl;

import java.util.Date;

import org.taucarre.smartdeals.business.DealBusinessService;
import org.taucarre.smartdeals.entite.deal.Deal;
import org.taucarre.smartdeals.persistence.DealPersitenceService;
import org.taucarre.smartdeals.persistence.datastore.DealPersitenceServiceImpl;


public class DealBusinessServiceImpl implements DealBusinessService {

	DealPersitenceService dealPersitenceService;
	
	public DealBusinessServiceImpl() {
		dealPersitenceService = new DealPersitenceServiceImpl();
	}
	@Override
	public Deal consulterDeal(String nomDeal) {
		// TODO Auto-generated method stub
		return dealPersitenceService.consulterDealByNom(nomDeal);
	}

	@Override
	public void ajouterDeal(Deal deal) {
		deal.setDateDeCreationDeal(new Date());
		deal.setExpire(false);
		dealPersitenceService.sauvegarderDeal(deal);
		
	}


}
