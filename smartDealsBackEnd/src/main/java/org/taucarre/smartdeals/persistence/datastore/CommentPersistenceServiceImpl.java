package org.taucarre.smartdeals.persistence.datastore;

import static org.taucarre.smartdeals.persistence.datastore.ObjectifyDAO.oby;

import java.util.List;
import java.util.logging.Logger;

import org.taucarre.smartdeals.entite.comment.Comment;
import org.taucarre.smartdeals.entite.deal.Deal;
import org.taucarre.smartdeals.persistence.CommentPersistenceService;

import com.googlecode.objectify.Key;

public class CommentPersistenceServiceImpl implements CommentPersistenceService {

	private static final String TAG = CommentPersistenceServiceImpl.class.getSimpleName();
	private Logger logger = Logger.getLogger(TAG);

	
	@Override
	public void sauvegarderComment(Comment comment) {
		// TODO Auto-generated method stub

		
		Key<Comment> result =  oby().save().entity(comment).now();
		if(result != null){
			logger.info("Sauvegarde commentaire pour le deal " + comment.getParent() + " : " +  result.toString());
		}else{
			logger.info("Echec Sauvegarde commentaire pour le deal " + comment.getParent());
		}
	
	}
	
	
	public void modifierComment(Comment comment) {
		oby().save().entity(comment).now();
		logger.info("Modification du commentaire écris par " + comment.getIdUser()); 
		
	}
	
	
	
	public void supprimerComment(Comment comment) {
		oby().delete().entity(comment).now();
		logger.info("Suppression du commentaire " + comment.getIdComment());		
	}
	
	
	//Récupérer les commentaires d'un Deal Passer en paramètre
	 	public List<Comment> GetCommentsOfDeal(Deal deal) {

		List<Comment> comments = oby().load().type(Comment.class).ancestor(deal).order("-dateDeCreation").list();
		
	 
		return comments;
	}
	 	
	 	
	
	
}
