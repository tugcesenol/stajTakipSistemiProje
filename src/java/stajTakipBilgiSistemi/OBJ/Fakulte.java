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
public class Fakulte {
	protected int id;
	protected String ad;
	protected String adres;
	protected String email;
	
	public Fakulte() {
	}
	
	public Fakulte(String ad, String adres, String email) {
		super();
		this.ad = ad;
		this.adres = adres;
		this.email = email;
	}

	public Fakulte(int id, String ad, String adres, String email) {
		super();
		this.id = id;
		this.ad = ad;
		this.adres = adres;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

