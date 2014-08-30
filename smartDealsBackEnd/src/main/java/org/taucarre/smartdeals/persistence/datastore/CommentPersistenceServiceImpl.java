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
	public Comment sauvegarderComment(Comment comment) {
		// TODO Auto-generated method stub
		
		Key<Comment> result =  oby().save().entity(comment).now();
		if(result != null){
			logger.info("Sauvegarde commentaire pour le deal " + comment.getIdDeal() + " : " +  result.toString());
			return comment;
		}else{
			logger.info("Echec Sauvegarde commentaire pour le deal " + comment.getIdDeal());
			return null;
		}
	
	}
	
	
	public Comment modifierComment(Comment comment) {
		oby().save().entity(comment).now();
		logger.info("Modification du commentaire écris par " + comment.getNomUser()); 
		return comment;
		
	}
	
	
	
	public void supprimerComment(Comment comment) {
		oby().delete().entity(comment).now();
		logger.info("Suppression du commentaire " + comment.getIdComment());		
	}
	
	
	//Récupérer les commentaires d'un Deal Passer en paramètre
	 	public List<Comment> getCommentsByDeal(Long idDeal) {

		List<Comment> comments = oby().load().type(Comment.class).filter("idDeal", idDeal).list();
		
		return comments;
	}


		@Override
		public List<Comment> fetchAllComments() {
			List<Comment> comments = oby().load().type(Comment.class).list();
			return comments;
		}
	 	
	 	
	
	
}
