package org.taucarre.smartdeals.entite.deal;

import java.util.Date;

import org.taucarre.smartdeals.entite.user.Utilisateur;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Commentaire {

	@Id
	private Long idCommentaire;
	
	@Parent
	private Key<Deal> dealAssocie; 
	
	private String contenuCommentaire;
	private Date dateDeCreation;
	private Utilisateur auteur;
	
	public String getContenuCommentaire() {
		return contenuCommentaire;
	}
	public void setContenuCommentaire(String contenuCommentaire) {
		this.contenuCommentaire = contenuCommentaire;
	}
	public Date getDateDeCreation() {
		return dateDeCreation;
	}
	public void setDateDeCreation(Date dateDeCreation) {
		this.dateDeCreation = dateDeCreation;
	}
	public Utilisateur getAuteur() {
		return auteur;
	}
	public void setAuteur(Utilisateur auteur) {
		this.auteur = auteur;
	}
	
	
}
