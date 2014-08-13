package org.taucarre.smartdeals.entite.user;

import org.taucarre.smartdeals.entite.deal.Deal;

public interface IUtilisateur {
	
	public Deal consulterDeal(Integer idDeal);
	
	public Deal rechercherDeal(String nomDeal);

	public boolean seConnecter();
	
	public void ajoueterFavori(String nomDeal);
	
	public void supprimerFavori(String nomDeal);
	
	public void parametrerApplication();

}
