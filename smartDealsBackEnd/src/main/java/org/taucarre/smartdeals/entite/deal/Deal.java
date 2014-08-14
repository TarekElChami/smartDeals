package org.taucarre.smartdeals.entite.deal;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Embedded;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Deal implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6393370664718631435L;
	
	@Id
	private Long idDeal;
	
	@Index
	private String nomDeal;
	
	private String nomMarchand;
	private String desciprtionDeal;
	private Date dateDeCreationDeal;
	private Date dateExpirationDeal;
	private Boolean expire;
	private String imageDeal;
//	private List<Commentaire> listeCommentaires;
//	private Evaluation evaluation;
	
	private String categorieDeal;
	
	private TypeDeal typeDeal;
	
	private Integer prix;
	
	private Integer prixGeneralementConstate;
	
	public Deal(){
//		listeCommentaires = new ArrayList<Commentaire>();
	}
	
	public String getNomDeal() {
		return nomDeal;
	}
	public void setNomDeal(String nomDeal) {
		this.nomDeal = nomDeal;
	}
	public String getDesciprtionDeal() {
		return desciprtionDeal;
	}
	public void setDesciprtionDeal(String desciprtionDeal) {
		this.desciprtionDeal = desciprtionDeal;
	}
	public Date getDateDeCreationDeal() {
		return dateDeCreationDeal;
	}
	public void setDateDeCreationDeal(Date dateDeCreationDeal) {
		this.dateDeCreationDeal = dateDeCreationDeal;
	}
	public Boolean getExpire() {
		return expire;
	}
	public void setExpire(Boolean expire) {
		this.expire = expire;
	}
	
	public String getImageDeal() {
		return imageDeal;
	}

	public void setImageDeal(String imageDeal) {
		this.imageDeal = imageDeal;
	}

	//	public List<Commentaire> getListeCommentaires() {
//		return listeCommentaires;
//	}
//	public void setListeCommentaires(List<Commentaire> listeCommentaires) {
//		this.listeCommentaires = listeCommentaires;
//	}
//	public Evaluation getEvaluation() {
//		return evaluation;
//	}
//	public void setEvaluation(Evaluation evaluation) {
//		this.evaluation = evaluation;
//	}
	public Long getIdDeal() {
		return idDeal;
	}
	public void setIdDeal(Long idDeal) {
		this.idDeal = idDeal;
	}

	public Integer getPrix() {
		return prix;
	}
	public void setPrix(Integer prix) {
		this.prix = prix;
	}
	public Integer getPrixGeneralementConstate() {
		return prixGeneralementConstate;
	}
	public void setPrixGeneralementConstate(Integer prixGeneralementConstate) {
		this.prixGeneralementConstate = prixGeneralementConstate;
	}
	public String getNomMarchand() {
		return nomMarchand;
	}
	public void setNomMarchand(String nomMarchand) {
		this.nomMarchand = nomMarchand;
	}
	public Date getDateExpirationDeal() {
		return dateExpirationDeal;
	}
	public void setDateExpirationDeal(Date dateExpirationDeal) {
		this.dateExpirationDeal = dateExpirationDeal;
	}

	public String getCategorieDeal() {
		return categorieDeal;
	}

	public void setCategorieDeal(String categorieDeal) {
		this.categorieDeal = categorieDeal;
	}

	public TypeDeal getTypeDeal() {
		return typeDeal;
	}

	public void setTypeDeal(TypeDeal typeDeal) {
		this.typeDeal = typeDeal;
	}

	

	

}
