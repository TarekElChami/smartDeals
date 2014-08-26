package org.taucarre.smartdeals.persistence;

import java.util.List;

import org.taucarre.smartdeals.entite.comment.Comment;
import org.taucarre.smartdeals.entite.deal.Deal;


public interface CommentPersistenceService {
	
	
public void sauvegarderComment(Comment comment);
public void supprimerComment(Comment comment);
public void modifierComment(Comment comment);
public List<Comment> GetCommentsOfDeal(Deal deal);

	
}


