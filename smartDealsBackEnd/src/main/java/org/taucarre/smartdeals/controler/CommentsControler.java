package org.taucarre.smartdeals.controler;

import java.util.List;

import javax.inject.Named;

import org.taucarre.smartdeals.business.CommentBusinessService;
import org.taucarre.smartdeals.business.impl.CommentBusinessServiceImpl;
import org.taucarre.smartdeals.entite.comment.Comment;

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
public class CommentsControler {
	
	CommentBusinessService commentBusinessService;

	 public CommentsControler() {
		commentBusinessService =  new CommentBusinessServiceImpl();
	}
	

	@ApiMethod(name="insertComment", path="insertComment",httpMethod ="post")
	public Comment insertComment(Comment comment){
		return commentBusinessService.ajouterCommentaire(comment);
	}
	
	
	@ApiMethod(name="updateComment", path="updateComment", httpMethod="put")
	public Comment updateComment(Comment comment){
		return commentBusinessService.mettreAjourCommentaire(comment); 
	}
	
	
	@ApiMethod(name="deleteComment", path="deleteComment", httpMethod="delete")
	public void deleteComment(Comment comment) throws NotFoundException{
		commentBusinessService.supprimerCommentaire(comment);
	}
	
	
	@ApiMethod(name="listComments", path="listComments")
	public List<Comment> listComments(@Named("id")Long idDeal){
		return commentBusinessService.recupererTousLesCommentaires(idDeal);
	}
	
	@ApiMethod(name="fetchComments", path="fetchCommments" )
	public List<Comment> fetchAllComments(){
		return commentBusinessService.fetchAllDeals();
	}
	
	

};
