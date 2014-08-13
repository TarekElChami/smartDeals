package org.taucarre.smartdeals.entite.deal;

import java.util.ArrayList;
import java.util.List;

public enum CategorieDeal {
	HIGH_TECH("INFORMATIQUE","TELEPHONIE","CONSOMABLES","LOGICELS"),
	AUDIOVISUEL(),
	LOISIRS(),
	MODE(),
	MAISON(),
	SERVICES(),
	EPICERIE();
	

	
	CategorieDeal(String sousCategorie1, String sousCategorie2, String sousCategorie3, String SousCategorie4){
		
	}
	
	CategorieDeal(){
		
	}
	
	CategorieDeal(SousCategorie sousCategories){
		
	}
	
	private class SousCategorie{
		private List<String> listeSousCategories;
		
		protected SousCategorie(List<String> sousCategories){
			this.listeSousCategories = new ArrayList<String>(sousCategories);
		}

		public List<String> getListeSousCategories() {
			return listeSousCategories;
		}

		public void setListeSousCategories(List<String> listeSousCategories) {
			this.listeSousCategories = listeSousCategories;
		}
		
		
		
	}

}
