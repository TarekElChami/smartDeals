package org.taucarre.smartdeals.business.exceptions;

public class SmartDealsException extends Exception  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5817347881761167600L;
	
	private int errorId;
	private String errorMessage;
	
	
	public SmartDealsException(int id, String message){
		super(message);
		this.errorId = id;
		this.errorMessage = message;
	}
	
	public SmartDealsException(String message){
		super(message);
		this.errorMessage = message;
	}

	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String toString(){
		return this.errorId + " : " + this.errorMessage;
	}
	
	

}
