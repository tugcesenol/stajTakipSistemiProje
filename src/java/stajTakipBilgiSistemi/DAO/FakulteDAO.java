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

import stajTakipBilgiSistemi.OBJ.Fakulte;

public class FakulteDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/stajtakipsistemi?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

	private static final String INSERT_FAKULTE_SQL = "INSERT INTO fakulte" + "  (ad, adres, email) VALUES (?, ?, ?);";

	private static final String SELECT_FAKULTE_BY_ID = "select id, ad, adres, email from fakulte where id =?";
	private static final String SELECT_ALL_FAKULTE = "select * from fakulte";
	private static final String DELETE_FAKULTE_SQL = "delete from fakulte where id = ?;";
	private static final String UPDATE_FAKULTE_SQL = "update fakulte set ad = ?, adres = ?, email = ? where id = ?;";

	public FakulteDAO() {
	}

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        return (Connection) DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

	public void insertFakulte(Fakulte fakulte) throws SQLException, ClassNotFoundException {
		System.out.println(INSERT_FAKULTE_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_FAKULTE_SQL)) {
			statement.setString(1, fakulte.getAd());
			statement.setString(2, fakulte.getAdres());
			statement.setString(3, fakulte.getEmail());
			System.out.println(statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Fakulte selectFakulte(int id) {
		Fakulte fakulte = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement statement = connection.prepareStatement(SELECT_FAKULTE_BY_ID);) {
			statement.setInt(1, id);
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String ad = rs.getString("ad");
				String adres = rs.getString("adres");
				String email = rs.getString("email");

				fakulte = new Fakulte(id, ad, adres, email);
			}
		} catch (SQLException e) {
			printSQLException(e);
		} catch (Exception e) {}
		return fakulte;
	}

	public List<Fakulte> selectAllFakulte() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Fakulte> fakulteler = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_FAKULTE);) {
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String ad = rs.getString("ad");
				String adres = rs.getString("adres");
				String email = rs.getString("email");

				fakulteler.add(new Fakulte(id, ad, adres, email));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return fakulteler;
	}

	public boolean deleteFakulte(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_FAKULTE_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}catch (Exception e) {
            rowDeleted = false;
        }
		return rowDeleted;
	}

	public boolean updateFakulte(Fakulte fakulte) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_FAKULTE_SQL);) {
			statement.setString(1, fakulte.getAd());
			statement.setString(2, fakulte.getAdres());
			statement.setString(3, fakulte.getEmail());
			statement.setInt(4, fakulte.getId());

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
