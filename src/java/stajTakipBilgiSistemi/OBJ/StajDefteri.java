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
public class StajDefteri {
	public static Integer KABUL_DURUM_YENI = 0;
	public static Integer KABUL_DURUM_RED = 1;
	public static Integer KABUL_DURUM_KABUL = 2;
	protected int id;
	protected StajBasvuru stajBasvuru;
	protected Integer kabulEdilenGunSayisi;
	protected String defterPath;
	protected Integer kabulDurumu;
	
	public StajDefteri() {
	}
	
	public StajDefteri(StajBasvuru stajBasvuru, Integer kabulDurumu, Integer kabulEdilenGunSayisi, String defterPath) {
		super();
		this.stajBasvuru = stajBasvuru;
		this.kabulDurumu = kabulDurumu;
		this.kabulEdilenGunSayisi = kabulEdilenGunSayisi;
		this.defterPath = defterPath;
	}

	public StajDefteri(int id, StajBasvuru stajBasvuru, Integer kabulDurumu, Integer kabulEdilenGunSayisi, String defterPath) {
		super();
		this.id = id;
		this.stajBasvuru = stajBasvuru;
		this.kabulDurumu = kabulDurumu;
		this.kabulEdilenGunSayisi = kabulEdilenGunSayisi;
		this.defterPath = defterPath;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public StajBasvuru getStajBasvuru() {
		return stajBasvuru;
	}

	public void setStajBasvuru(StajBasvuru stajBasvuru) {
		this.stajBasvuru = stajBasvuru;
	}

	public String getDefterPath() {
		return defterPath;
	}

	public void setDefterPath(String defterPath) {
		this.defterPath = defterPath;
	}
	
}