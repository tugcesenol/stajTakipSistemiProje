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
public class Danisman {
	protected int id;
	protected String ad;
	protected String soyad;
	protected Integer sifre;
	protected String email;
	protected Bolum bolum;
	
	public Danisman() {
	}
	
	public Danisman(String ad, String soyad, Integer sifre, String email, Bolum bolum) {
		super();
		this.ad = ad;
		this.soyad = soyad;
		this.sifre = sifre;
		this.email = email;
		this.soyad = soyad;
		this.bolum = bolum;
	}

	public Danisman(int id, String ad, String soyad, Integer sifre, String email, Bolum bolum) {
		super();
		this.id = id;
		this.ad = ad;
		this.soyad = soyad;
		this.sifre = sifre;
		this.email = email;
		this.soyad = soyad;
		this.bolum = bolum;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public Integer getSifre() {
		return sifre;
	}

	public void setSifre(Integer sifre) {
		this.sifre = sifre;
	}

	public Bolum getBolum() {
		return bolum;
	}

	public void setBolum(Bolum bolum) {
		this.bolum = bolum;
	}
}

