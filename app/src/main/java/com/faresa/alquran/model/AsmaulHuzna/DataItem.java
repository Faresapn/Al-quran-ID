package com.faresa.alquran.model.AsmaulHuzna;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("number")
	private int number;

	@SerializedName("name")
	private String name;

	@SerializedName("en")
	private En en;

	@SerializedName("transliteration")
	private String transliteration;

	public void setNumber(int number){
		this.number = number;
	}

	public int getNumber(){
		return number;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setEn(En en){
		this.en = en;
	}

	public En getEn(){
		return en;
	}

	public void setTransliteration(String transliteration){
		this.transliteration = transliteration;
	}

	public String getTransliteration(){
		return transliteration;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"number = '" + number + '\'' + 
			",name = '" + name + '\'' + 
			",en = '" + en + '\'' + 
			",transliteration = '" + transliteration + '\'' + 
			"}";
		}
}