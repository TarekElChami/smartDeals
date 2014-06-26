package org.taucarre.smartdeals.entite.user;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class User {

	@Id
	private Long idUser;
	private String name;
	private String login;
	private String password;
	private String mail;
	private Byte[] avatar;
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


	public Byte[] getAvatar() {
		return avatar;
	}


	public void setAvatar(Byte[] avatar) {
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