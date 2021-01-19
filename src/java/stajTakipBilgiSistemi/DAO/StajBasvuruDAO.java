/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stajTakipBilgiSistemi.DAO;

/**
 *
 * @author tuğçe
 */

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import stajTakipBilgiSistemi.OBJ.Danisman;
import stajTakipBilgiSistemi.OBJ.Ogrenci;
import stajTakipBilgiSistemi.OBJ.Sirket;
import stajTakipBilgiSistemi.OBJ.StajBasvuru;

public class StajBasvuruDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/stajtakipsistemi?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

	private static final String INSERT_STAJBASVURU_SQL = "INSERT INTO stajbasvuru" + "  (danisman, ogrenci, sirket, baslangic_tarihi, bitis_tarihi, kabul_durumu) VALUES (?, ?, ?, ?, ?, ?);";

	private static final String SELECT_STAJBASVURU_BY_ID = "select id, danisman, ogrenci, sirket, baslangic_tarihi, bitis_tarihi, kabul_durumu from stajbasvuru where id =?";
	private static final String SELECT_ALL_STAJBASVURU = "select * from stajbasvuru";
	private static final String DELETE_STAJBASVURU_SQL = "delete from stajbasvuru where id = ?;";
	private static final String UPDATE_STAJBASVURU_SQL = "update stajbasvuru set danisman = ?, ogrenci = ?, sirket = ?, baslangic_tarihi = ?, bitis_tarihi = ?, kabul_durumu =? where id = ?;";

	public StajBasvuruDAO() {
	}

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        return (Connection) DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

	public void insertStajBasvuru(StajBasvuru stajbasvuru) throws SQLException, ClassNotFoundException {
		System.out.println(INSERT_STAJBASVURU_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_STAJBASVURU_SQL)) {
			statement.setInt(1, stajbasvuru.getDanisman().getId());
			statement.setInt(2, stajbasvuru.getOgrenci().getId());
			statement.setInt(3, stajbasvuru.getSirket().getId());
			statement.setDate(4, stajbasvuru.getBaslangicTarihi());
			statement.setDate(5, stajbasvuru.getBitisTarihi());
			statement.setInt(6, stajbasvuru.getKabulDurumu());
			System.out.println(statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public StajBasvuru selectStajBasvuru(int id) {
		StajBasvuru stajbasvuru = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement statement = connection.prepareStatement(SELECT_STAJBASVURU_BY_ID);) {
			statement.setInt(1, id);
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				Integer danismanId = Integer.valueOf(rs.getString("danisman"));
				Integer ogrenciId = Integer.valueOf(rs.getString("ogrenci"));
				Integer sirketId = Integer.valueOf(rs.getString("sirket"));
				Date baslangicTarihi = rs.getDate("baslangic_tarihi");
				Date bitisTarihi = rs.getDate("bitis_tarihi");
				Integer kabulDurumu = Integer.valueOf(rs.getString("kabul_durumu"));
			
				Danisman danisman = new DanismanDAO().selectDanisman(danismanId);
				Ogrenci ogrenci = new OgrenciDAO().selectOgrenci(ogrenciId);
				Sirket sirket = new SirketDAO().selectSirket(sirketId);
				stajbasvuru = new StajBasvuru(id, baslangicTarihi, bitisTarihi, ogrenci, danisman, sirket, kabulDurumu);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return stajbasvuru;
	}

	public List<StajBasvuru> selectAllStajBasvuru() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<StajBasvuru> stajBasvurulari = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STAJBASVURU);) {
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				Integer danismanId = Integer.valueOf(rs.getString("danisman"));
				Integer ogrenciId = Integer.valueOf(rs.getString("ogrenci"));
				Integer sirketId = Integer.valueOf(rs.getString("sirket"));
				Date baslangicTarihi = rs.getDate("baslangic_tarihi");
				Date bitisTarihi = rs.getDate("bitis_tarihi");
				Integer kabulDurumu = rs.getInt("kabul_durumu");
				
                              
				Danisman danisman = new DanismanDAO().selectDanisman(danismanId);
				Ogrenci ogrenci = new OgrenciDAO().selectOgrenci(ogrenciId);
				Sirket sirket = new SirketDAO().selectSirket(sirketId);
   
				stajBasvurulari.add(new StajBasvuru(id, baslangicTarihi, bitisTarihi, ogrenci, danisman, sirket, kabulDurumu));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return stajBasvurulari;
	}

	public boolean deleteStajBasvuru(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_STAJBASVURU_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}catch (Exception e) {
            rowDeleted = false;
        }
		return rowDeleted;
	}

	public boolean updateStajBasvuru(StajBasvuru stajbasvuru) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_STAJBASVURU_SQL);) {
			statement.setInt(1, stajbasvuru.getDanisman().getId());
			statement.setInt(2, stajbasvuru.getOgrenci().getId());
			statement.setInt(3, stajbasvuru.getSirket().getId());
			statement.setDate(4, stajbasvuru.getBaslangicTarihi());
			statement.setDate(5, stajbasvuru.getBitisTarihi());
			statement.setInt(6, stajbasvuru.getKabulDurumu());
			statement.setInt(7, stajbasvuru.getKabulEdilenGunSayisi());
			statement.setInt(8, stajbasvuru.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}catch (Exception e) {
            rowUpdated = false;
        }
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
