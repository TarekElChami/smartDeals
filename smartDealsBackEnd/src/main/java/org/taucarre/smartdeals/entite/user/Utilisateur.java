package org.taucarre.smartdeals.entite.user;

import java.util.List;

import org.taucarre.smartdeals.entite.deal.Deal;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public abstract class  Utilisateur  implements IUtilisateur{
	
	@Id
	private Long idUtilisateur;
	private String nomUtilisateur;
	
	private PreferencesUtilisateur preferences;
	private List<FavoriUtilisateur> listeFavoris;
	private IUtilisateur utilisateurDecore;

	public Utilisateur(IUtilisateur utilisateurDecore){
		this.utilisateurDecore = utilisateurDecore; 
	}
	
	public Deal consulterDeal(Integer idDeal) {
		return utilisateurDecore.consulterDeal(idDeal);
	}

	public Deal rechercherDeal(String nomDeal) {
		return utilisateurDecore.rechercherDeal(nomDeal);
	}


	public boolean seConnecter() {
		return utilisateurDecore.seConnecter();
	}


	public void ajoueterFavori(String nomDeal) {
		utilisateurDecore.ajoueterFavori(nomDeal);
	}


	public void supprimerFavori(String nomDeal) {
		utilisateurDecore.supprimerFavori(nomDeal);
	}


	public void parametrerApplication() {
		utilisateurDecore.parametrerApplication();
	}

	public String getNomUtilisateur() {
		return nomUtilisateur;
	}
	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}
	public PreferencesUtilisateur getPreferences() {
		return preferences;
	}
	public void setPreferences(PreferencesUtilisateur preferences) {
		this.preferences = preferences;
	}
	public List<FavoriUtilisateur> getListeFavoris() {
		return listeFavoris;
	}
	public void setListeFavoris(List<FavoriUtilisateur> listeFavoris) {
		this.listeFavoris = listeFavoris;
	}
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	

}
