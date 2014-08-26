package org.taucarre.smartdeals.entite.comment;

import java.io.Serializable;
import java.util.Date;



import javax.jdo.annotations.Embedded;

import org.taucarre.smartdeals.entite.deal.Deal;

import com.googlecode.objectify.annotation.*;
import com.googlecode.objectify.Key;


@Entity
public class Comment implements Serializable {
	
private static final long serialVersionUID = 8095668685587827749L;


@Id()
private Long idComment;

@Parent Key<Deal> parent;

@Index
private Long idDeal;
@Index
private Long idUser;

private String txtComment;
//prévoir une fonctionnalité d'un bouton j'aime ou j'aime pas
//On note le nombre de personne qui aiment ou pas ce commentaire
private Long iLike;
private Long iDislike;
@Index private Date dateDeCreation;
@Index private Date dateDeModification; 

//constructeur sans paramètres Pour objectify
private Comment() {}


public Comment(Key<Deal> parent, Long idUser, String txtComment, Long iLike, Long iDislike,Date dateDeCreation, Date dateDeModification){

    this.parent=parent;
	this.idUser=idUser;
	this.txtComment=txtComment;
	this.iLike=iLike;
	this.iDislike=iDislike;
	this.dateDeCreation=dateDeCreation;
	this.dateDeModification=dateDeModification;
	
}


public Long getIdComment() {
	return idComment;
}

public void setIdComment(Long idComment) {
	this.idComment = idComment;
}


public Key<Deal> getParent() {
	return parent;
}

public void setParent(Key<Deal> parent) {
	this.parent = parent;
}


public Long getIdDeal() {
	return idDeal;
}

public void setIdDeal(Long idDeal) {
	this.idDeal = idDeal;
}

public Long getIdUser() {
	return idUser;
}

public void setIdUser(Long idUser) {
	this.idUser = idUser;
}


public String getTxtComment() {
	return txtComment;
}
public void setTxtComment(String txtComment) {
	this.txtComment = txtComment;
}


public Long getILike() {
	return iLike;
}
public void setTxtComment(Long iLike) {
	this.iLike = iLike;
}

public Long getIDislike() {
	return iDislike;
}
public void setIDislike(Long iDislike) {
	this.iDislike = iDislike;
}

public Date getDateDeCreation() {
	return dateDeCreation;
}
public void setDateDeCreation(Date dateDeCreation) {
	this.dateDeCreation = dateDeCreation;
}
 
public Date getDateDeModification() {
	return dateDeModification;
}
public void setDateDeModification(Date dateDeModification) {
	this.dateDeModification = dateDeModification;
}
 
}//public class Comment
