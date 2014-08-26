package org.taucarre.smartdeals.business.impl;

import java.util.Date;
import java.util.List;

import org.taucarre.smartdeals.business.CommentBusinessService;
import org.taucarre.smartdeals.entite.comment.Comment;
import org.taucarre.smartdeals.entite.deal.Deal;
import org.taucarre.smartdeals.persistence.CommentPersistenceService;
import org.taucarre.smartdeals.persistence.datastore.CommentPersistenceServiceImpl;

public class CommentBusinessServiceImpl implements CommentBusinessService {

	CommentPersistenceService commentPersistenceService;
	
	public CommentBusinessServiceImpl() {
		commentPersistenceService = new CommentPersistenceServiceImpl();	
	}
	
	
	
	@Override
	public void ajouterCommentaire(Comment comment) {
		// TODO Auto-generated method stub
	comment.setDateDeCreation(new Date());
	commentPersistenceService.sauvegarderComment(comment);
	}

	@Override
	public void mettreAjourCommentaire(Comment comment) {
		// TODO Auto-generated method stub
   comment.setDateDeModification(new Date());
   commentPersistenceService.modifierComment(comment);
	}

	@Override
	public void supprimerCommentaire(Comment comment) {
		// TODO Auto-generated method stub
		commentPersistenceService.supprimerComment(comment);
	}

	@Override
	public void supprimerCommentaireByInfos(Long dealId, Long UserId,
			Date DateDeCreation) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Comment> recupererTousLesCommentaires(Deal deal) {
		// TODO Auto-generated method stub
		
		return commentPersistenceService.GetCommentsOfDeal(deal);
	}

}
