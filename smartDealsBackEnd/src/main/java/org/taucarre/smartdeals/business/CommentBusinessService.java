package org.taucarre.smartdeals.business;

import java.util.Date;
import java.util.List;

import org.taucarre.smartdeals.entite.comment.Comment;
import org.taucarre.smartdeals.entite.deal.Deal;
 

public interface CommentBusinessService {
	
   public void ajouterCommentaire(Comment comment);
	public void mettreAjourCommentaire(Comment comment);
	public void supprimerCommentaire(Comment comment);
	public void supprimerCommentaireByInfos(Long dealId, Long UserId, Date DateDeCreation);
	public List<Comment> recupererTousLesCommentaires(Deal deal);


}
