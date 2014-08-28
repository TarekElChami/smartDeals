package org.taucarre.smartdeals.entite.comment;

import java.io.Serializable;
import java.util.Date;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity
public class Comment implements Serializable {
	
private static final long serialVersionUID = 8095668685587827749L;


@Id()
private Long idComment;

@Index
private Long idDeal;
@Index
private Long idUser;

private String txtComment;

private String nomUser;

@Index private Date dateDeCreation;
@Index private Date dateDeModification; 


public Comment(){
	
}


public Long getIdComment() {
	return idComment;
}

public void setIdComment(Long idComment) {
	this.idComment = idComment;
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

public String getNomUser() {
	return nomUser;
}

public void setNomUser(String nomUser) {
	this.nomUser = nomUser;
}


 
}//public class Comment
