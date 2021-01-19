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
import stajTakipBilgiSistemi.OBJ.Fakulte;
import stajTakipBilgiSistemi.OBJ.Ogrenci;

public class OgrenciDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/stajtakipsistemi?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "123456";

	private static final String INSERT_OGRENCI_SQL = "INSERT INTO ogrenci" + "  (ad, soyad, sifre, email, danisman, fakulte, bolum) VALUES (?, ?, ?, ?, ?, ?, ?);";

	private static final String SELECT_OGRENCI_BY_ID = "select id, ad, soyad, sifre, email, danisman, fakulte, bolum from ogrenci where id =?";
	private static final String SELECT_ALL_OGRENCI = "select * from ogrenci";
	private static final String DELETE_OGRENCI_SQL = "delete from ogrenci where id = ?;";
	private static final String UPDATE_OGRENCI_SQL = "update ogrenci set ad = ?, soyad = ?, sifre = ?, email = ?, danisman = ?, fakulte = ?, bolum = ? where id = ?;";
	private static final String LOGIN_OGRENCI_QUERY = "select * from ogrenci where email =? and sifre =?";
	
	public OgrenciDAO() {
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {
            String dbDriver = "com.mysql.jdbc.Driver"; 
            Class.forName(dbDriver);
            Class.forName ( "com.mysql.jdbc.Driver" );
            return(Connection) DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }


	public void insertOgrenci(Ogrenci ogrenci) throws SQLException , ClassNotFoundException {
		System.out.println(INSERT_OGRENCI_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_OGRENCI_SQL)) {
			statement.setString(1, ogrenci.getAd());
			statement.setString(2, ogrenci.getSoyad());
			statement.setInt(3, ogrenci.getSifre());
			statement.setString(4, ogrenci.getEmail());
			statement.setInt(5, ogrenci.getDanisman().getId());
			statement.setInt(6, ogrenci.getFakulte().getId());
			statement.setInt(7, ogrenci.getBolum().getId());
			System.out.println(statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
	
	public Ogrenci authtenticateUser(String e, String s) throws SQLException {
		Ogrenci ogrenci = null;
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_OGRENCI_QUERY)) {
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
				Integer danismanId = Integer.valueOf(rs.getString("danisman"));
				Integer fakulteId = Integer.valueOf(rs.getString("fakulte"));
				Integer bolumId = Integer.valueOf(rs.getString("bolum"));
				
				Danisman danisman = new DanismanDAO().selectDanisman(danismanId);
				Fakulte fakulte = new FakulteDAO().selectFakulte(fakulteId);
				Bolum bolum = new BolumDAO().selectBolum(bolumId);
				ogrenci = new Ogrenci(id, ad, soyad, sifre, email, bolum, fakulte, danisman);
			}
			return ogrenci;
		} catch (SQLException ex) {
			printSQLException(ex);
                }catch (Exception ex) {}
			return ogrenci;
	}

	public Ogrenci selectOgrenci(int id) {
		Ogrenci ogrenci = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement statement = connection.prepareStatement(SELECT_OGRENCI_BY_ID);) {
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
				Integer danismanId = Integer.valueOf(rs.getString("danisman"));
				Integer fakulteId = Integer.valueOf(rs.getString("fakulte"));
				Integer bolumId = Integer.valueOf(rs.getString("bolum"));
				
				Danisman danisman = new DanismanDAO().selectDanisman(danismanId);
				Fakulte fakulte = new FakulteDAO().selectFakulte(fakulteId);
				Bolum bolum = new BolumDAO().selectBolum(bolumId);
				ogrenci = new Ogrenci(id, ad, soyad, sifre, email, bolum, fakulte, danisman);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return ogrenci;
	}

	public List<Ogrenci> selectAllOgrenci() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Ogrenci> ogrenciler = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_OGRENCI);) {
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
				Integer danismanId = Integer.valueOf(rs.getString("danisman"));
				Integer fakulteId = Integer.valueOf(rs.getString("fakulte"));
				Integer bolumId = Integer.valueOf(rs.getString("bolum"));
				
				Danisman danisman = new DanismanDAO().selectDanisman(danismanId);
				Fakulte fakulte = new FakulteDAO().selectFakulte(fakulteId);
				Bolum bolum = new BolumDAO().selectBolum(bolumId);
				ogrenciler.add(new Ogrenci(id, ad, soyad, sifre, email, bolum, fakulte, danisman));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}catch (Exception e) {}
		return ogrenciler;
	}

	public boolean deleteOgrenci(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_OGRENCI_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}catch (Exception e) {
            rowDeleted = false;}
		return rowDeleted;
	}

	public boolean updateOgrenci(Ogrenci ogrenci) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_OGRENCI_SQL);) {
			statement.setString(1, ogrenci.getAd());
			statement.setString(2, ogrenci.getSoyad());
			statement.setInt(3, ogrenci.getSifre());
			statement.setString(4, ogrenci.getEmail());
			statement.setInt(5, ogrenci.getDanisman().getId());
			statement.setInt(6, ogrenci.getFakulte().getId());
			statement.setInt(7, ogrenci.getBolum().getId());
			statement.setInt(8, ogrenci.getId());

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
