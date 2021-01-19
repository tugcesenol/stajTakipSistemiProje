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
import stajTakipBilgiSistemi.OBJ.Danisman;

public class DanismanDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/stajtakipsistemi?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

	private static final String INSERT_DANISMAN_SQL = "INSERT INTO danisman" + "  (ad, soyad, sifre, email, bolum) VALUES (?, ?, ?, ?, ?);";

	private static final String SELECT_DANISMAN_BY_ID = "select id, ad, soyad, sifre, email, bolum from danisman where id =?";
	private static final String SELECT_ALL_DANISMAN = "select * from danisman";
	private static final String DELETE_DANISMAN_SQL = "delete from danisman where id = ?;";
	private static final String UPDATE_DANISMAN_SQL = "update danisman set ad = ?, soyad = ?, sifre = ?, email = ?, bolum = ? where id = ?;";
	private static final String LOGIN_DANISMAN_QUERY = "select * from danisman where email =? and sifre =?";
	
	public DanismanDAO() {
	}

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        return (Connection) DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

	public void insertDanisman(Danisman danisman) throws SQLException, ClassNotFoundException {
		System.out.println(INSERT_DANISMAN_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_DANISMAN_SQL)) {
			statement.setString(1, danisman.getAd());
			statement.setString(2, danisman.getSoyad());
			statement.setInt(3, danisman.getSifre());
			statement.setString(4, danisman.getEmail());
			statement.setInt(5, danisman.getBolum().getId());
			System.out.println(statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	public Danisman authtenticateUser(String e, String s) throws SQLException {
		Danisman danisman = null;
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_DANISMAN_QUERY)) {
			preparedStatement.setString(1, e);
			preparedStatement.setString(2, s);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Integer id = Integer.valueOf(rs.getString("id"));
				String ad = rs.getString("ad");
				String soyad = rs.getString("soyad");
				Integer sifre = Integer.valueOf(rs.getString("sifre"));
				String email = rs.getString("email");
				Integer bolumId = Integer.valueOf(rs.getString("bolum"));
				
				Bolum bolum = new BolumDAO().selectBolum(bolumId);
				danisman = new Danisman(id, ad, soyad, sifre, email, bolum);
			}
			return danisman;
		} catch (SQLException ex) {
			printSQLException(ex);
                }catch (Exception ex) {}
		return danisman;
	}

	public Danisman selectDanisman(int id) {
		Danisman danisman = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement statement = connection.prepareStatement(SELECT_DANISMAN_BY_ID);) {
			statement.setInt(1, id);
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String ad = rs.getString("ad");
				String soyad = rs.getString("soyad");
				Integer sifre = Integer.valueOf(rs.getString("sifre"));
				String email = rs.getString("email");
				Integer bolumId = Integer.valueOf(rs.getString("bolum"));
				
				Bolum bolum = new BolumDAO().selectBolum(bolumId);
				danisman = new Danisman(id, ad, soyad, sifre, email, bolum);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return danisman;
	}

	public List<Danisman> selectAllDanisman() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Danisman> danismanlar = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DANISMAN);) {
			System.out.println(statement);
			// Step 3: Execute the query or update query
			ResultSet rs = statement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String ad = rs.getString("ad");
				String soyad = rs.getString("soyad");
				Integer sifre = Integer.valueOf(rs.getString("sifre"));
				String email = rs.getString("email");
				Integer bolumId = Integer.valueOf(rs.getString("bolum"));
				
				Bolum bolum = new BolumDAO().selectBolum(bolumId);

				danismanlar.add(new Danisman(id, ad, soyad, sifre, email, bolum));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return danismanlar;
	}

	public boolean deleteDanisman(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_DANISMAN_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}catch (Exception e) {
            rowDeleted = false;}
		return rowDeleted;
	}

	public boolean updateDanisman(Danisman danisman) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_DANISMAN_SQL);) {
			statement.setString(1, danisman.getAd());
			statement.setString(2, danisman.getSoyad());
			statement.setInt(3, danisman.getSifre());
			statement.setString(4, danisman.getEmail());
			statement.setInt(5, danisman.getBolum().getId());
			statement.setInt(6, danisman.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}catch (Exception e) {
            rowUpdated = false;}
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
