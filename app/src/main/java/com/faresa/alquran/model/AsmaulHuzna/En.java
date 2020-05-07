package com.faresa.alquran.model.AsmaulHuzna;

import com.google.gson.annotations.SerializedName;

public class En{

	@SerializedName("meaning")
	private String meaning;

	public void setMeaning(String meaning){
		this.meaning = meaning;
	}

	public String getMeaning(){
		return meaning;
	}

	@Override
 	public String toString(){
		return 
			"En{" + 
			"meaning = '" + meaning + '\'' + 
			"}";
		}
}