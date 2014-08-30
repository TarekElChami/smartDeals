package org.taucarre.smartdeals.business;

import java.util.List;

import org.taucarre.smartdeals.entite.comment.Comment;
 

public interface CommentBusinessService {
	
    public Comment ajouterCommentaire(Comment comment);
	public Comment mettreAjourCommentaire(Comment comment);
	public void supprimerCommentaire(Comment comment);
	public List<Comment> recupererTousLesCommentaires(Long idDeal);
	public List<Comment> fetchAllDeals();


}
