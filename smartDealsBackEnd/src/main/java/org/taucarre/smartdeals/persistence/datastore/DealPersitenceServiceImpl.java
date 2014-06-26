package org.taucarre.smartdeals.persistence.datastore;

import static org.taucarre.smartdeals.persistence.datastore.ObjectifyDAO.oby;

import java.util.List;
import java.util.logging.Logger;

import org.taucarre.smartdeals.entite.deal.Deal;
import org.taucarre.smartdeals.persistence.DealPersitenceService;

import com.google.appengine.api.datastore.Entity;
import com.googlecode.objectify.Key;

public class DealPersitenceServiceImpl implements DealPersitenceService {

	private static final String TAG = DealPersitenceServiceImpl.class.getSimpleName();
	private Logger logger = Logger.getLogger(TAG);
	
	public Deal fetchDealById(Long id) {
		String webSafeString = Key.create(null, Entity.class,id.longValue()).toString();
		Key<Deal> key = Key.create(webSafeString);
		return oby().load().key(key).now();
	}

	public Deal consulterDealByNom(String nomDeal) {
		Deal deal = oby().load().type(Deal.class).filter("nomDeal", nomDeal).first().now();
		if(deal != null){
			logger.info("Consultation Deal : " + nomDeal +"/"+  deal.getIdDeal());
		}else{
			logger.info("Echec consultation Dela " + nomDeal);
		}
		return deal;
	}

	public void sauvegarderDeal(Deal deal) {
		Key<Deal> result =  oby().save().entity(deal).now();
		if(result != null){
			logger.info("Sauvegarde deal " + deal.getNomDeal() + " : " +  result.toString());
		}else{
			logger.info("Echec Sauvegarde deal " + deal.getNomDeal());
		}
		
	}

	public void modifierDeal(Deal deal) {
		oby().save().entity(deal).now();
		logger.info("Modification deal " + deal.getNomDeal()); 
		
	}

	public void supprimerDeal(Deal deal) {
		oby().delete().entity(deal).now();
		logger.info("Suppression deal " + deal.getNomDeal());
		
	}

	@Override
	public List<Deal> getListDeals() {
		List<Deal> deals = oby().load().type(Deal.class).list();
		return deals;
	}

}
