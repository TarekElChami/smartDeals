package org.taucarre.smartdeals.entite.user;

import org.taucarre.smartdeals.entite.deal.Deal;

public class  UtilisateurAutorise extends  Utilisateur{

	private String loginUtilisateur;
	private String passwordUtilisateur;
	private String mailUtilisateur;
	private Byte[] avatarUtilisateur;
	private Integer notorioriteUtilisateur;
	private Role roleUtilisateur;
	
	public UtilisateurAutorise(IUtilisateur utilisateurDecore) {
		super(utilisateurDecore);
		// TODO Auto-generated constructor stub
	}
	
	public String getLoginUtilisateur() {
		return loginUtilisateur;
	}
	public void setLoginUtilisateur(String loginUtilisateur) {
		this.loginUtilisateur = loginUtilisateur;
	}
	public String getPasswordUtilisateur() {
		return passwordUtilisateur;
	}
	public void setPasswordUtilisateur(String passwordUtilisateur) {
		this.passwordUtilisateur = passwordUtilisateur;
	}
	public String getMailUtilisateur() {
		return mailUtilisateur;
	}
	public void setMailUtilisateur(String mailUtilisateur) {
		this.mailUtilisateur = mailUtilisateur;
	}
	public Byte[] getAvatarUtilisateur() {
		return avatarUtilisateur;
	}
	public void setAvatarUtilisateur(Byte[] avatarUtilisateur) {
		this.avatarUtilisateur = avatarUtilisateur;
	}
	public Integer getNotorioriteUtilisateur() {
		return notorioriteUtilisateur;
	}
	public void setNotorioriteUtilisateur(Integer notorioriteUtilisateur) {
		this.notorioriteUtilisateur = notorioriteUtilisateur;
	}
	
	public Role getRoleUtilisateur() {
		return roleUtilisateur;
	}
	public void setRoleUtilisateur(Role roleUtilisateur) {
		this.roleUtilisateur = roleUtilisateur;
	}
	public boolean pubierDeal(Deal deal){
		return false;
	}
	
	public void modifierDeal(Deal deal){};
	public void supprimerDeal(Integer idDeal){}
	public void expirerDeal(Integer idDeal){}
	public void reactiverDeal(Integer idDeal){}
	public void laisserCommentaire(String textCommentaire, Integer idDeal){
		
	}
	public void supprimerCommentaire(Integer idCommentaire){}
	
	public void signOut(){}
	
	public void evaluerDeal(){}
	
}
