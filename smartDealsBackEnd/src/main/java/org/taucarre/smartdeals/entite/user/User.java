package org.taucarre.smartdeals.entite.user;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;

import com.google.api.server.spi.config.Nullable;
import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class User implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7095668685587827749L;

	@Id()
	private Long idUser;
	
	@Index
	private String login;
	
	private String name;
	
	private String password;
	
	private String mail;
	
	private String avatar;
	
	private Integer notoriorite;
	
	private boolean banned;
	
	private Role roleUtilisateur;
	
	
	public User(){
		
	}

	public Long getIdUser() {
		return idUser;
	}


	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getNotoriorite() {
		return notoriorite;
	}


	public void setNotoriorite(Integer notoriorite) {
		this.notoriorite = notoriorite;
	}


	public boolean isBanned() {
		return banned;
	}


	public void setBanned(boolean banned) {
		this.banned = banned;
	}


	public Role getRoleUtilisateur() {
		return roleUtilisateur;
	}


	public void setRoleUtilisateur(Role roleUtilisateur) {
		this.roleUtilisateur = roleUtilisateur;
	}
	
	
	
	
}
