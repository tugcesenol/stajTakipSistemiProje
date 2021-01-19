/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stajTakipBilgiSistemi.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import stajTakipBilgiSistemi.OBJ.Sirket;

/**
 *
 * @author tuğçe
 */
public class SirketDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/stajtakipsistemi?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

	private static final String INSERT_SIRKET_SQL = "INSERT INTO sirket" + "  (ad, adres) VALUES (?, ?);";

	private static final String SELECT_SIRKET_BY_ID = "select id, ad, adres from sirket where id =?";
	private static final String SELECT_ALL_SIRKET = "select * from sirket";
	private static final String DELETE_SIRKET_SQL = "delete from sirket where id = ?;";
	private static final String UPDATE_SIRKET_SQL = "update sirket set ad = ?, adres = ? where id = ?;";

	public SirketDAO() {
	}

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        return (Connection) DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

	public void insertSirket(Sirket sirket) throws SQLException, ClassNotFoundException {
		System.out.println(INSERT_SIRKET_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_SIRKET_SQL)) {
			statement.setString(1, sirket.getAd());
			statement.setString(2, sirket.getAdres());
			System.out.println(statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Sirket selectSirket(int id) {
		Sirket sirket = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement statement = connection.prepareStatement(SELECT_SIRKET_BY_ID);) {
			statement.setInt(1, id);
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String ad = rs.getString("ad");
				String adres = rs.getString("adres");

				sirket = new Sirket(id, ad, adres);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return sirket;
	}

	public List<Sirket> selectAllSirket() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Sirket> sirketler = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SIRKET);) {
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String ad = rs.getString("ad");
				String adres = rs.getString("adres");

				sirketler.add(new Sirket(id, ad, adres));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return sirketler;
	}

	public boolean deleteSirket(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_SIRKET_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		} catch (Exception e) {
            rowDeleted = false;
        }
		return rowDeleted;
	}

	public boolean updateSirket(Sirket sirket) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_SIRKET_SQL);) {
			statement.setString(1, sirket.getAd());
			statement.setString(2, sirket.getAdres());
			statement.setInt(3, sirket.getId());

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
