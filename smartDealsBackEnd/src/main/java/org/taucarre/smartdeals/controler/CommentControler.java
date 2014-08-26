package org.taucarre.smartdeals.controler;

import java.util.List;

import javax.inject.Named;

import org.taucarre.smartdeals.business.CommentBusinessService;
import org.taucarre.smartdeals.business.DealBusinessService;
import org.taucarre.smartdeals.business.exceptions.SmartDealsException;
import org.taucarre.smartdeals.business.impl.DealBusinessServiceImpl;
import org.taucarre.smartdeals.entite.comment.Comment;
import org.taucarre.smartdeals.entite.deal.Deal;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;

@Api(
		name ="smartdeals",
		version = "v1",
		scopes = {Constants.EMAIL_SCOPE},
		clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
		audiences = {Constants.ANDROID_AUDIENCE},
		description = "API for manipulating the smart deals app V1 "
		)


public class CommentControler {
	
	CommentBusinessService commentBusinessService;

	
	@ApiMethod(name="getDealComments")
	public List<Comment> getDealComments(Deal deal){
		return commentBusinessService.recupererTousLesCommentaires(deal);
	}
	

	@ApiMethod(name="insertComment", httpMethod= "post")
	public void insertComment(Comment comment){
		commentBusinessService.ajouterCommentaire(comment);
	}
	
	
	@ApiMethod(name="updateComment")
	public void updateComment(Comment comment){
		commentBusinessService.mettreAjourCommentaire(comment); ;
	}
	
	
	@ApiMethod(name="deleteComment", httpMethod="delete")
	public void deleteComment(Comment comment) throws NotFoundException{
		commentBusinessService.supprimerCommentaire(comment);
	}
	
	
	

};
