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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import stajTakipBilgiSistemi.OBJ.StajBasvuru;
import stajTakipBilgiSistemi.OBJ.StajDefteri;

public class StajDefteriDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/stajtakipsistemi?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

	private static final String INSERT_STAJDEFTERI_SQL = "INSERT INTO stajdefteri" + "  (stajbasvuru, defterPath, kabul_durumu, kabul_gun_sayisi) VALUES (?, ?, ?, ?);";

	private static final String SELECT_STAJDEFTERI_BY_ID = "select id, stajbasvuru, defterPath, kabul_durumu, kabul_gun_sayisi from stajdefteri where id =?";
	private static final String SELECT_ALL_STAJDEFTERI = "select * from stajdefteri";
	private static final String DELETE_STAJDEFTERI_SQL = "delete from stajdefteri where id = ?;";
	private static final String UPDATE_STAJDEFTERI_SQL = "update stajdefteri set stajbasvuru = ?, defterPath = ?, kabul_durumu = ?, kabul_gun_sayisi = ? where id = ?;";

	public StajDefteriDAO() {
	}

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        return (Connection) DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
	public void insertStajDefteri(StajDefteri stajdefteri) throws SQLException , ClassNotFoundException {
		System.out.println(INSERT_STAJDEFTERI_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_STAJDEFTERI_SQL)) {
			statement.setInt(1, stajdefteri.getStajBasvuru().getId());
			statement.setString(2, stajdefteri.getDefterPath());
			statement.setInt(3, stajdefteri.getKabulDurumu());
			statement.setInt(4, stajdefteri.getKabulEdilenGunSayisi());
			System.out.println(statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public StajDefteri selectStajDefteri(int id) {
		StajDefteri stajdefteri = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement statement = connection.prepareStatement(SELECT_STAJDEFTERI_BY_ID);) {
			statement.setInt(1, id);
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				Integer stajbasvuruId = Integer.valueOf(rs.getString("stajbasvuru"));
				String defterPath = rs.getString("defterPath");
				Integer kabulDurumu = rs.getInt("kabul_durumu");
				Integer kabulGunSayisi = Integer.valueOf(rs.getString("kabul_gun_sayisi"));
				
				StajBasvuru stajBasvuru = new StajBasvuruDAO().selectStajBasvuru(stajbasvuruId);
				stajdefteri = new StajDefteri(id, stajBasvuru, kabulDurumu, kabulGunSayisi, defterPath);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return stajdefteri;
	}

	public List<StajDefteri> selectAllStajDefteri() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<StajDefteri> stajDefterleri = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STAJDEFTERI);) {
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				Integer stajbasvuruId = Integer.valueOf(rs.getString("stajbasvuru"));
				String defterPath = rs.getString("defterPath");
				Integer kabulDurumu = rs.getInt("kabul_durumu");
				Integer kabulGunSayisi = Integer.valueOf(rs.getString("kabul_gun_sayisi"));
				
				StajBasvuru stajBasvuru = new StajBasvuruDAO().selectStajBasvuru(stajbasvuruId);
				stajDefterleri.add(new StajDefteri(id, stajBasvuru, kabulDurumu, kabulGunSayisi, defterPath));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return stajDefterleri;
	}

	public boolean deleteStajDefteri(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_STAJDEFTERI_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}catch (Exception e) {
            rowDeleted = false;
        }
		return rowDeleted;
	}

	public boolean updateStajDefteri(StajDefteri stajdefteri) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_STAJDEFTERI_SQL);) {
			statement.setInt(1, stajdefteri.getStajBasvuru().getId());
			statement.setString(2, stajdefteri.getDefterPath());
			statement.setInt(3, stajdefteri.getKabulDurumu());
			statement.setInt(4, stajdefteri.getKabulEdilenGunSayisi());
			statement.setInt(5, stajdefteri.getId());

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
