package org.taucarre.smartdeals.controler;

import javax.inject.Named;

import org.taucarre.smartdeals.business.DealBusinessService;
import org.taucarre.smartdeals.business.impl.DealBusinessServiceImpl;
import org.taucarre.smartdeals.entite.deal.Deal;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

@Api(
		name ="smartdeals",
		version = "v1",
		scopes = {Constants.EMAIL_SCOPE},
		clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
		audiences = {Constants.ANDROID_AUDIENCE},
		description = "API for manipulating the deals V1 "
		)
public class SmartDealsControler {
	
	DealBusinessService dealBusinessService;
	
	public SmartDealsControler() {
		dealBusinessService = new DealBusinessServiceImpl();
	}
	
	@ApiMethod(name="getDealByName",httpMethod= "get")
	public Deal getDealByName(@Named("nom") String nameDeal){
		return dealBusinessService.consulterDeal(nameDeal);
	}
	
	@ApiMethod(name="insertDeal", httpMethod= "post")
	public void insertDeal(Deal deal){
		dealBusinessService.ajouterDeal(deal);
	}

}
