/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stajTakipBilgiSistemi.SERVLET;

/**
 *
 * @author tuğçe
 */

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import stajTakipBilgiSistemi.DAO.BolumDAO;
import stajTakipBilgiSistemi.DAO.DanismanDAO;
import stajTakipBilgiSistemi.DAO.FakulteDAO;
import stajTakipBilgiSistemi.DAO.OgrenciDAO;
import stajTakipBilgiSistemi.DAO.SirketDAO;
import stajTakipBilgiSistemi.DAO.StajBasvuruDAO;
import stajTakipBilgiSistemi.DAO.StajDefteriDAO;

import stajTakipBilgiSistemi.OBJ.Bolum;
import stajTakipBilgiSistemi.OBJ.Danisman;
import stajTakipBilgiSistemi.OBJ.Fakulte;
import stajTakipBilgiSistemi.OBJ.Ogrenci;
import stajTakipBilgiSistemi.OBJ.Sirket;
import stajTakipBilgiSistemi.OBJ.StajBasvuru;
import stajTakipBilgiSistemi.OBJ.StajDefteri;

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final String LOGGED_OGRENCI = "loggedOgrenci";
	private static final String LOGGED_DANISMAN = "loggedDanisman";
	private static final long serialVersionUID = 1L;

	public void init() {
	}

	protected boolean isValidLoginExist(HttpServletRequest request) {
		Ogrenci ogrenci = (Ogrenci)request.getSession().getAttribute(LOGGED_OGRENCI);
		Danisman danisman = (Danisman)request.getSession().getAttribute(LOGGED_DANISMAN);
		if(ogrenci!= null || danisman != null) {
			if(ogrenci!= null)
				request.setAttribute("loggedOgrenci", ogrenci);

			if(danisman!= null)
				request.setAttribute("loggedDanisman", danisman);
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		try {
			if(isValidLoginExist(request) == false) {
				switch (action) {
					case "/loginUser":
						loginAccount(request, response);
						break;
					default:
						diplayLoginPage(request, response);
						break;
				}
			}
			else {
				switch (action) {
					case "/cikisYap":
						cikisYap(request, response);
						break;
					case "/profilSayfam":
						showProfilSayfam(request, response);
						break;
					case "/danismanDuzenle":
						showEditDanismanForm(request, response);
						break;
					case "/danismanGuncelle":
						updateDanisman(request, response);
						break;
					case "/danismanListele":
						listDanisman(request, response);
						break;    
					case "/ogrenciDuzenle":
						showEditOgrenciForm(request, response);
						break;
					case "/ogrenciGuncelle":
						updateOgrenci(request, response);
						break;
					case "/ogrenciListele":
						listOgrenci(request, response);
						break;
					case "/yeniStajbasvuru":
						showNewStajBasvuruForm(request, response);
						break;
					case "/stajbasvuruEkle":
						insertStajBasvuru(request, response);
						break;
					case "/stajbasvuruSil":
						deleteStajBasvuru(request, response);
						break;
					case "/stajbasvuruDuzenle":
						showEditStajBasvuruForm(request, response);
						break;
					case "/stajbasvuruGuncelle":
						updateStajBasvuru(request, response);
						break;
					case "/stajbasvuruListele":
						listStajBasvuru(request, response);
						break;
					case "/yeniStajdefteri":
						showNewStajDefteriForm(request, response);
						break;
					case "/stajdefteriEkle":
						insertStajDefteri(request, response);
						break;
					case "/stajdefteriSil":
						deleteStajDefteri(request, response);
						break;
					case "/stajdefteriDuzenle":
						showEditStajDefteriForm(request, response);
						break;
					case "/stajdefteriGuncelle":
						updateStajDefteri(request, response);
						break;
					case "/stajdefteriListele":
						listStajDefteri(request, response);
						break;
					default:
						showProfilSayfam(request, response);
						break;
				}
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
                catch (ClassNotFoundException ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	private void diplayLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("login-form.jsp");
		dispatcher.forward(request, response);
	}

	private void loginAccount(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		String email = request.getParameter("email");
		String sifre = request.getParameter("sifre");
		Integer userType = Integer.valueOf(request.getParameter("userType"));
		request.setAttribute("userType", userType);
		if(userType == 1) {
			Danisman danisman = new DanismanDAO().authtenticateUser(email, sifre);
			if(danisman == null) {
				request.setAttribute("error", "Email veya parola bilgisi yanlış girildi!");
				diplayLoginPage(request, response);
			}
			else {
				request.getSession().setAttribute(LOGGED_DANISMAN, danisman);
				request.removeAttribute("error");
				response.sendRedirect("profilSayfam");
			}
		}
		else {
			Ogrenci ogrenci = new OgrenciDAO().authtenticateUser(email, sifre);
			if(ogrenci == null) {
				request.setAttribute("error", "Email veya parola bilgisi yanlış girildi!");
				diplayLoginPage(request, response);
			}
			else {
				request.getSession().setAttribute(LOGGED_OGRENCI, ogrenci);
				request.removeAttribute("error");
				response.sendRedirect("profilSayfam");
			}
		}
	}

	private void cikisYap(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {

		request.getSession().setAttribute(LOGGED_OGRENCI, null);
		request.getSession().setAttribute(LOGGED_DANISMAN, null);
		diplayLoginPage(request, response);
	}
	
	private void showProfilSayfam(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("profil-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listDanisman(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Danisman> listUser = new DanismanDAO().selectAllDanisman();
		request.setAttribute("listDanisman", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("danisman-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewDanismanForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Bolum> bolumListesi = new BolumDAO().selectAllBolum();
		request.setAttribute("listBolum", bolumListesi);
		RequestDispatcher dispatcher = request.getRequestDispatcher("danisman-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditDanismanForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Danisman existingUser = new DanismanDAO().selectDanisman(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("danisman-form.jsp");
		request.setAttribute("danisman", existingUser);
		List<Bolum> bolumListesi = new BolumDAO().selectAllBolum();
		request.setAttribute("listBolum", bolumListesi);
		dispatcher.forward(request, response);

	}

	private void insertDanisman(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ClassNotFoundException {
		String ad = request.getParameter("ad");
		String soyad = request.getParameter("soyad");
		String email = request.getParameter("email");
		Integer sifre = Integer.valueOf(request.getParameter("sifre"));
		Integer bolumId = Integer.parseInt(request.getParameter("bolum"));
		

		Bolum bolum = new BolumDAO().selectBolum(bolumId);
		Danisman newUser = new Danisman(ad, soyad, sifre, email, bolum);
		
		new DanismanDAO().insertDanisman(newUser);;
		response.sendRedirect("danismanListele");
	}

	private void updateDanisman(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String ad = request.getParameter("ad");
		String soyad = request.getParameter("soyad");
		String email = request.getParameter("email");
		Integer sifre = Integer.valueOf(request.getParameter("sifre"));
		Integer bolumId = Integer.parseInt(request.getParameter("bolum"));
		

		Bolum bolum = new BolumDAO().selectBolum(bolumId);
		Danisman newUser = new Danisman(id,ad, soyad, sifre, email, bolum);
		
		new DanismanDAO().updateDanisman(newUser);
		response.sendRedirect("danismanListele");
	}

	private void deleteDanisman(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		new DanismanDAO().deleteDanisman(id);
		response.sendRedirect("danismanListele");

	}
	
	private void listStajDefteri(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<StajDefteri> listUser = new ArrayList<StajDefteri>();
		Ogrenci lO = (Ogrenci)request.getSession().getAttribute(LOGGED_OGRENCI);
		if(lO == null) {
			listUser = new StajDefteriDAO().selectAllStajDefteri();
		}
		else {
			List<StajDefteri> tempListUser = new StajDefteriDAO().selectAllStajDefteri();
			for(StajDefteri stajDefteri : tempListUser) {
				if(stajDefteri.getStajBasvuru().getOgrenci().getId() == lO.getId()) {
					listUser.add(stajDefteri);
				}
			}
		}
		request.setAttribute("listStajdefteri", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("stajdefteri-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewStajDefteriForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<StajBasvuru> listUser = new StajBasvuruDAO().selectAllStajBasvuru();
		request.setAttribute("listStajbasvuru", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("stajdefteri-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditStajDefteriForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		StajDefteri existingStajDefteri = new StajDefteriDAO().selectStajDefteri(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("stajdefteri-form.jsp");
		request.setAttribute("stajdefteri", existingStajDefteri);
		List<StajBasvuru> listUser = new StajBasvuruDAO().selectAllStajBasvuru();
		request.setAttribute("listStajbasvuru", listUser);
		dispatcher.forward(request, response);

	}

	private void insertStajDefteri(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ClassNotFoundException {
		Integer stajbasvuruId = Integer.parseInt(request.getParameter("stajbasvuru"));
		Integer kabulDurumu = Integer.parseInt(request.getParameter("kabulDurumu"));
		Integer kabulGunSayisi = Integer.valueOf(request.getParameter("kabulGunSayisi"));
		File dosya = new File(request.getParameter("defter"));
		
		String defterPath=dosya.getAbsolutePath();

		StajBasvuru stajBasvuru = new StajBasvuruDAO().selectStajBasvuru(stajbasvuruId);
		StajDefteri newUser = new StajDefteri(stajBasvuru, kabulDurumu, kabulGunSayisi, defterPath);
		new StajDefteriDAO().insertStajDefteri(newUser);
		response.sendRedirect("stajdefteriListele");
	}

	private void updateStajDefteri(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Integer stajbasvuruId = Integer.parseInt(request.getParameter("stajbasvuru"));
		Integer kabulDurumu = Integer.parseInt(request.getParameter("kabulDurumu"));
		Integer kabulGunSayisi = Integer.valueOf(request.getParameter("kabulGunSayisi"));
		File dosya = new File(request.getParameter("defter"));
		
		String defterPath=dosya.getAbsolutePath();
		StajBasvuru stajBasvuru = new StajBasvuruDAO().selectStajBasvuru(stajbasvuruId);
		StajDefteri newUser = new StajDefteri(id, stajBasvuru, kabulDurumu, kabulGunSayisi, defterPath);
		new StajDefteriDAO().updateStajDefteri(newUser);
		response.sendRedirect("stajdefteriListele");
	}

	private void deleteStajDefteri(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		new StajDefteriDAO().deleteStajDefteri(id);
		response.sendRedirect("stajdefteriListele");

	}
	
	private void listStajBasvuru(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<StajBasvuru> listUser = new ArrayList<StajBasvuru>();
		Ogrenci lO = (Ogrenci)request.getSession().getAttribute(LOGGED_OGRENCI);
		if(lO == null) {
			listUser = new StajBasvuruDAO().selectAllStajBasvuru();
		}
		else {
			List<StajBasvuru> tempListUser = new StajBasvuruDAO().selectAllStajBasvuru();
			for (StajBasvuru basvuru : tempListUser) {
				if(basvuru.getOgrenci().getId() == lO.getId()) {
					listUser.add(basvuru);
				}
			}
		}
	
		request.setAttribute("listStajbasvuru", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("stajbasvuru-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewStajBasvuruForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Ogrenci> ogrenciListesi = new OgrenciDAO().selectAllOgrenci();
		List<Sirket> sirketListesi = new SirketDAO().selectAllSirket();
		List<Danisman> danismanListesi = new DanismanDAO().selectAllDanisman();
		request.setAttribute("listOgrenci", ogrenciListesi);
		request.setAttribute("listSirket", sirketListesi);
		request.setAttribute("listDanisman", danismanListesi);
		RequestDispatcher dispatcher = request.getRequestDispatcher("stajbasvuru-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditStajBasvuruForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		StajBasvuru existingStajBasvuru = new StajBasvuruDAO().selectStajBasvuru(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("stajbasvuru-form.jsp");
		request.setAttribute("stajbasvuru", existingStajBasvuru);
		List<Ogrenci> ogrenciListesi = new OgrenciDAO().selectAllOgrenci();
		List<Sirket> sirketListesi = new SirketDAO().selectAllSirket();
		List<Danisman> danismanListesi = new DanismanDAO().selectAllDanisman();
		request.setAttribute("listOgrenci", ogrenciListesi);
		request.setAttribute("listSirket", sirketListesi);
		request.setAttribute("listDanisman", danismanListesi);
		dispatcher.forward(request, response);

	}

	private void insertStajBasvuru(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ClassNotFoundException {
		Date baslangicTarihi = Date.valueOf(request.getParameter("baslangicTarihi"));
		Date bitisTarihi = Date.valueOf(request.getParameter("bitisTarihi"));
		Integer sirketId = Integer.parseInt(request.getParameter("sirket"));
		Integer ogrenciId = Integer.parseInt(request.getParameter("ogrenci"));
		Integer danismanId = Integer.parseInt(request.getParameter("danisman"));
		Integer kabulDurumu = Integer.parseInt(request.getParameter("kabulDurumu"));
		Integer kabulGunSayisi = Integer.valueOf(request.getParameter("kabulGunSayisi"));
		

		Sirket sirket = new SirketDAO().selectSirket(sirketId);
		Ogrenci ogrenci = new OgrenciDAO().selectOgrenci(ogrenciId);
		Danisman danisman = new DanismanDAO().selectDanisman(danismanId);
		StajBasvuru newUser = new StajBasvuru(baslangicTarihi, bitisTarihi, ogrenci, danisman, sirket, kabulDurumu, kabulGunSayisi);
		new StajBasvuruDAO().insertStajBasvuru(newUser);
		response.sendRedirect("stajbasvuruListele");
	}

	private void updateStajBasvuru(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Date baslangicTarihi = Date.valueOf(request.getParameter("baslangicTarihi"));
		Date bitisTarihi = Date.valueOf(request.getParameter("bitisTarihi"));
		Integer sirketId = Integer.parseInt(request.getParameter("sirket"));
		Integer ogrenciId = Integer.parseInt(request.getParameter("ogrenci"));
		Integer danismanId = Integer.parseInt(request.getParameter("danisman"));
		Integer kabulDurumu = Integer.parseInt(request.getParameter("kabulDurumu"));
		Integer kabulGunSayisi = Integer.valueOf(request.getParameter("kabulGunSayisi"));
		
		Sirket sirket = new SirketDAO().selectSirket(sirketId);
		Ogrenci ogrenci = new OgrenciDAO().selectOgrenci(ogrenciId);
		Danisman danisman = new DanismanDAO().selectDanisman(danismanId);
		StajBasvuru newUser = new StajBasvuru(id,baslangicTarihi, bitisTarihi, ogrenci, danisman, sirket, kabulDurumu, kabulGunSayisi);
		new StajBasvuruDAO().updateStajBasvuru(newUser);
		response.sendRedirect("stajbasvuruListele");
	}

	private void deleteStajBasvuru(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		new StajBasvuruDAO().deleteStajBasvuru(id);
		response.sendRedirect("stajbasvuruListele");

	}
	
	private void listOgrenci(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Ogrenci> listUser = new OgrenciDAO().selectAllOgrenci();
		request.setAttribute("listOgrenci", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ogrenci-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewOgrenciForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Fakulte> fakulteListesi = new FakulteDAO().selectAllFakulte();
		List<Bolum> bolumListesi = new BolumDAO().selectAllBolum();
		List<Danisman> danismanListesi = new DanismanDAO().selectAllDanisman();
		request.setAttribute("listFakulte", fakulteListesi);
		request.setAttribute("listBolum", bolumListesi);
		request.setAttribute("listDanisman", danismanListesi);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ogrenci-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditOgrenciForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Ogrenci existingUser = new OgrenciDAO().selectOgrenci(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ogrenci-form.jsp");
		request.setAttribute("ogrenciAcc", existingUser);
		List<Fakulte> fakulteListesi = new FakulteDAO().selectAllFakulte();
		List<Bolum> bolumListesi = new BolumDAO().selectAllBolum();
		List<Danisman> danismanListesi = new DanismanDAO().selectAllDanisman();
		request.setAttribute("listFakulte", fakulteListesi);
		request.setAttribute("listBolum", bolumListesi);
		request.setAttribute("listDanisman", danismanListesi);
		dispatcher.forward(request, response);

	}

	private void insertOgrenci(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ClassNotFoundException {
		String ad = request.getParameter("ad");
		String soyad = request.getParameter("soyad");
		String email = request.getParameter("email");
		Integer sifre = Integer.valueOf(request.getParameter("sifre"));
		Integer bolumId = Integer.parseInt(request.getParameter("bolum"));
		Integer fakulteId = Integer.parseInt(request.getParameter("fakulte"));
		Integer danismanId = Integer.parseInt(request.getParameter("danisman"));
		

		Fakulte fakulte = new FakulteDAO().selectFakulte(fakulteId);
		Bolum bolum = new BolumDAO().selectBolum(bolumId);
		Danisman danisman = new DanismanDAO().selectDanisman(danismanId);
		Ogrenci newUser = new Ogrenci(ad, soyad, sifre, email, bolum, fakulte, danisman);
		new OgrenciDAO().insertOgrenci(newUser);
		response.sendRedirect("ogrenciListele");
	}

	private void updateOgrenci(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String ad = request.getParameter("ad");
		String soyad = request.getParameter("soyad");
		String email = request.getParameter("email");
		Integer sifre = Integer.valueOf(request.getParameter("sifre"));
		Integer bolumId = Integer.parseInt(request.getParameter("bolum"));
		Integer fakulteId = Integer.parseInt(request.getParameter("fakulte"));
		Integer danismanId = Integer.parseInt(request.getParameter("danisman"));
		

		Fakulte fakulte = new FakulteDAO().selectFakulte(fakulteId);
		Bolum bolum = new BolumDAO().selectBolum(bolumId);
		Danisman danisman = new DanismanDAO().selectDanisman(danismanId);
		Ogrenci newUser = new Ogrenci(id, ad, soyad, sifre, email, bolum, fakulte, danisman);
		new OgrenciDAO().updateOgrenci(newUser);
		response.sendRedirect("ogrenciListele");
	}
}