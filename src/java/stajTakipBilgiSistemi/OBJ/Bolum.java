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
public class Bolum {
	protected int id;
	protected String ad;
	protected String email;
	protected Fakulte fakulte;
	
	public Bolum() {
	}
	
	public Bolum(String ad, String email, Fakulte fakulte) {
		super();
		this.ad = ad;
		this.fakulte = fakulte;
		this.email = email;
	}

	public Bolum(int id, String ad, String email, Fakulte fakulte) {
		super();
		this.id = id;
		this.ad = ad;
		this.fakulte = fakulte;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Fakulte getFakulte() {
		return fakulte;
	}

	public void setFakulte(Fakulte fakulte) {
		this.fakulte = fakulte;
	}
}

