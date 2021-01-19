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

import stajTakipBilgiSistemi.OBJ.Bolum;
import stajTakipBilgiSistemi.OBJ.Fakulte;

public class BolumDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/stajtakipsistemi?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

	private static final String INSERT_BOLUM_SQL = "INSERT INTO bolum" + "  (ad, email, fakulte) VALUES (?, ?, ?);";

	private static final String SELECT_BOLUM_BY_ID = "select id, ad, email, fakulte from bolum where id =?";
	private static final String SELECT_ALL_BOLUM = "select * from bolum";
	private static final String DELETE_BOLUM_SQL = "delete from bolum where id = ?;";
	private static final String UPDATE_BOLUM_SQL = "update bolum set ad = ?, email = ?, fakulte = ? where id = ?;";

	public BolumDAO() {
	}

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        return (Connection) DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

	public void insertBolum(Bolum bolum) throws SQLException, ClassNotFoundException {
		System.out.println(INSERT_BOLUM_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_BOLUM_SQL)) {
			statement.setString(1, bolum.getAd());
			statement.setString(2, bolum.getEmail());
			statement.setInt(3, bolum.getFakulte().getId());
			System.out.println(statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Bolum selectBolum(int id) {
		Bolum bolum = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement statement = connection.prepareStatement(SELECT_BOLUM_BY_ID);) {
			statement.setInt(1, id);
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String ad = rs.getString("ad");
				String email = rs.getString("email");
				Integer fakulteId = Integer.valueOf(rs.getString("fakulte"));
				
				Fakulte fakulte = new FakulteDAO().selectFakulte(fakulteId);
				bolum = new Bolum(id, ad, email, fakulte);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return bolum;
	}

	public List<Bolum> selectAllBolum() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Bolum> fakulteler = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOLUM);) {
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String ad = rs.getString("ad");
				String email = rs.getString("email");
				Integer fakulteId = Integer.valueOf(rs.getString("fakulte"));
				
				Fakulte fakulte = new FakulteDAO().selectFakulte(fakulteId);

				fakulteler.add(new Bolum(id, ad, email, fakulte));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return fakulteler;
	}

	public boolean deleteBolum(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_BOLUM_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}catch (Exception e) {
            rowDeleted = false;
        }
		return rowDeleted;
	}

	public boolean updateBolum(Bolum bolum) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_BOLUM_SQL);) {
			statement.setString(1, bolum.getAd());
			statement.setString(2, bolum.getEmail());
			statement.setInt(3, bolum.getFakulte().getId());
			statement.setInt(4, bolum.getId());

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
