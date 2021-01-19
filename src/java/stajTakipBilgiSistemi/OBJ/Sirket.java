/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stajTakipBilgiSistemi.OBJ;

/**
 *
 * @author tuğçe
 */

public class Sirket {
	protected int id;
	protected String ad;
	protected String adres;
	
	public Sirket() {
	}
	
	public Sirket(String ad, String adres) {
		super();
		this.ad = ad;
		this.adres = adres;
	}

	public Sirket(int id, String ad, String adres) {
		super();
		this.id = id;
		this.ad = ad;
		this.adres = adres;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}
}

