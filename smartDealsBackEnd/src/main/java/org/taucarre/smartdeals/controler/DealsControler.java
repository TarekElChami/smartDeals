package org.taucarre.smartdeals.controler;

import java.util.List;

import javax.inject.Named;

import org.taucarre.smartdeals.business.DealBusinessService;
import org.taucarre.smartdeals.business.exceptions.SmartDealsException;
import org.taucarre.smartdeals.business.impl.DealBusinessServiceImpl;
import org.taucarre.smartdeals.entite.deal.Deal;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;

@Api(
		name ="smartdeals",
		version = "v1",
		scopes = {Constants.EMAIL_SCOPE},
		clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
		audiences = {Constants.ANDROID_AUDIENCE},
		description = "API for manipulating the smart deals app V1 "
		)
public class DealsControler {
	
	DealBusinessService dealBusinessService;
	
	public DealsControler() {
		dealBusinessService = new DealBusinessServiceImpl();
	}
	
	
	@ApiMethod(name="getDealByName",httpMethod="get")
	public Deal getDealByName(@Named("nom") String nameDeal){
		return dealBusinessService.consulterDeal(nameDeal);
	}
	
	@ApiMethod(name="insertDeal", httpMethod="post")
	public void insertDeal(Deal deal){
		dealBusinessService.ajouterDeal(deal);
	}
	
	@ApiMethod(name="updateDeal")
	public void updateDeal(Deal deal){
		dealBusinessService.mettreAjourDeal(deal);
	}
	
	@ApiMethod(name="deleteDeal", httpMethod="delete")
	public void deleteDeal(@Named("nom") String nameDeal) throws NotFoundException{
		try {
			dealBusinessService.supptimerDeal(nameDeal);
		} catch (SmartDealsException e) {
			throw new NotFoundException(e.getErrorMessage());
		}
	}
	
	@ApiMethod(name="listDeals")
	public List<Deal> listDeals(){
		return dealBusinessService.recupererTousLesDeals();
	}

}
