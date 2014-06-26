package org.taucarre.smartdeals.business.impl;

import java.util.Date;
import java.util.List;

import org.taucarre.smartdeals.business.DealBusinessService;
import org.taucarre.smartdeals.business.exceptions.ConstantesExceptions;
import org.taucarre.smartdeals.business.exceptions.SmartDealsException;
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
		return dealPersitenceService.consulterDealByNom(nomDeal);
	}

	@Override
	public void ajouterDeal(Deal deal) {
		deal.setDateDeCreationDeal(new Date());
		deal.setExpire(false);
		dealPersitenceService.sauvegarderDeal(deal);
		
	}
	@Override
	public void mettreAjourDeal(Deal deal) {
		dealPersitenceService.modifierDeal(deal);
		
	}
	@Override
	public void supptimerDeal(String nomDeal) throws SmartDealsException {
		Deal deal = dealPersitenceService.consulterDealByNom(nomDeal);
		if(deal != null){
			dealPersitenceService.supprimerDeal(deal);
		}else{
			throw new SmartDealsException(ConstantesExceptions.DEAL_NOT_FOUND_EXCEPTION,
								ConstantesExceptions.DEAL_NOT_FOUND_EXCEPTION_MESSAGE );
		}
		
	}
	@Override
	public List<Deal> recupererTousLesDeals() {
		return dealPersitenceService.getListDeals();
	}


}
