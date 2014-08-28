package org.taucarre.smartdeals.business.impl;

import java.util.Date;
import java.util.List;

import org.taucarre.smartdeals.business.CommentBusinessService;
import org.taucarre.smartdeals.entite.comment.Comment;
import org.taucarre.smartdeals.persistence.CommentPersistenceService;
import org.taucarre.smartdeals.persistence.datastore.CommentPersistenceServiceImpl;

public class CommentBusinessServiceImpl implements CommentBusinessService {

	CommentPersistenceService commentPersistenceService;
	
	public CommentBusinessServiceImpl() {
		commentPersistenceService = new CommentPersistenceServiceImpl();	
	}

	
	@Override
	public Comment ajouterCommentaire(Comment comment) {
		// TODO Auto-generated method stub
	comment.setDateDeCreation(new Date());
	return commentPersistenceService.sauvegarderComment(comment);
	}

	@Override
	public Comment mettreAjourCommentaire(Comment comment) {
		// TODO Auto-generated method stub
   comment.setDateDeModification(new Date());
   return commentPersistenceService.modifierComment(comment);
	}

	@Override
	public void supprimerCommentaire(Comment comment) {
		// TODO Auto-generated method stub
		commentPersistenceService.supprimerComment(comment);
	}


	@Override
	public List<Comment> recupererTousLesCommentaires(Long idDeal) {

		return commentPersistenceService.getCommentsByDeal(idDeal);
	}

}
