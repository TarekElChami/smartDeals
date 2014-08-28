package org.taucarre.smartdeals.persistence;

import java.util.List;

import org.taucarre.smartdeals.entite.comment.Comment;



public interface CommentPersistenceService {
	
	
public Comment sauvegarderComment(Comment comment);
public void supprimerComment(Comment comment);
public Comment modifierComment(Comment comment);
public List<Comment> getCommentsByDeal(Long idDeal);

	
}


