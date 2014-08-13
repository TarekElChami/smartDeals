package org.taucarre.smartdeals.entite.deal;

public class PhysicalDeal extends Deal {
	
	private String addressePhysiqueDeal;
	private Localisation geolocalisation;
	
	public String getAddressePhysiqueDeal() {
		return addressePhysiqueDeal;
	}
	public void setAddressePhysiqueDeal(String addressePhysiqueDeal) {
		this.addressePhysiqueDeal = addressePhysiqueDeal;
	}
	public Localisation getGeolocalisation() {
		return geolocalisation;
	}
	public void setGeolocalisation(Localisation geolocalisation) {
		this.geolocalisation = geolocalisation;
	}
	

}
