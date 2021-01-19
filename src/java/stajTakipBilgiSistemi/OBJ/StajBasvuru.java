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

import java.sql.Date;

public class StajBasvuru {
	public static Integer KABUL_DURUM_YENI = 0;
	public static Integer KABUL_DURUM_RED = 1;
	public static Integer KABUL_DURUM_KABUL = 2;
	protected int id;
	protected Date baslangicTarihi;
	protected Date bitisTarihi;
	protected Ogrenci ogrenci;
	protected Danisman danisman;
	protected Sirket sirket;
	protected Integer kabulEdilenGunSayisi;
	protected Integer kabulDurumu;
	
	public StajBasvuru() {
	}
	
	public StajBasvuru(Date baslangicTarihi, Date bitisTarihi, Ogrenci ogrenci, Danisman danisman, Sirket sirket, Integer kabulDurumu, Integer kabulEdilenGunSayisi) {
		super();
		this.baslangicTarihi = baslangicTarihi;
		this.bitisTarihi = bitisTarihi;
		this.ogrenci = ogrenci;
		this.danisman = danisman;
		this.sirket = sirket;
		this.kabulDurumu = kabulDurumu;
		this.kabulEdilenGunSayisi = kabulEdilenGunSayisi;
	}

	public StajBasvuru(int id, Date baslangicTarihi, Date bitisTarihi, Ogrenci ogrenci, Danisman danisman, Sirket sirket, Integer kabulDurumu) {
		super();
		this.id = id;
		this.baslangicTarihi = baslangicTarihi;
		this.bitisTarihi = bitisTarihi;
		this.ogrenci = ogrenci;
		this.danisman = danisman;
		this.sirket = sirket;
		this.kabulDurumu = kabulDurumu;
	}
        
        public StajBasvuru(int id, Date baslangicTarihi, Date bitisTarihi, Ogrenci ogrenci, Danisman danisman, Sirket sirket, Integer kabulDurumu, Integer kabulEdilenGunSayisi) {
		super();
		this.id = id;
		this.baslangicTarihi = baslangicTarihi;
		this.bitisTarihi = bitisTarihi;
		this.ogrenci = ogrenci;
		this.danisman = danisman;
		this.sirket = sirket;
		this.kabulDurumu = kabulDurumu;
                this.kabulEdilenGunSayisi = kabulEdilenGunSayisi;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getBaslangicTarihi() {
		return baslangicTarihi;
	}

	public void setBaslangicTarihi(Date baslangicTarihi) {
		this.baslangicTarihi = baslangicTarihi;
	}

	public Date getBitisTarihi() {
		return bitisTarihi;
	}

	public void setBitisTarihi(Date bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}

	public Ogrenci getOgrenci() {
		return ogrenci;
	}

	public void setOgrenci(Ogrenci ogrenci) {
		this.ogrenci = ogrenci;
	}

	public Danisman getDanisman() {
		return danisman;
	}

	public void setDanisman(Danisman danisman) {
		this.danisman = danisman;
	}

	public Sirket getSirket() {
		return sirket;
	}

	public void setSirket(Sirket sirket) {
		this.sirket = sirket;
	}

	public Integer getKabulEdilenGunSayisi() {
		return kabulEdilenGunSayisi;
	}

	public void setKabulEdilenGunSayisi(Integer kabulEdilenGunSayisi) {
		this.kabulEdilenGunSayisi = kabulEdilenGunSayisi;
	}

	public Integer getKabulDurumu() {
		return kabulDurumu;
	}

	public void setKabulDurumu(Integer kabulDurumu) {
		this.kabulDurumu = kabulDurumu;
	}

	public String getKabulDurumuText() {
		if(this.kabulDurumu == KABUL_DURUM_YENI) return "Yeni Başvuru";
		else if(this.kabulDurumu == KABUL_DURUM_RED) return "Red";
		else if(this.kabulDurumu == KABUL_DURUM_KABUL) return "Kabul";
		return "";
	}
	
}

