package feedrss.feedrss;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class readbdd {
	
	public static void savefeedrss(String Title, String Link, String Language, String Copiright, String PubDate) {

		// Information d'accès à la base de données
		String url = "jdbc:mysql://localhost/feedrss";
		String login = "root";
		String passwd = "toto";
		Connection cn =null;
		Statement st =null;

		try {

			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.jdbc.Driver");

			// Etape 2 : récupération de la connexion
			cn = (Connection) DriverManager.getConnection(url, login, passwd);

			// Etape 3 : Création d'un statement
			st = (Statement) cn.createStatement();

			String sql = "INSERT INTO Rss + VALUE (Title, Link, Language, copiright, Pubdate )";

			// Etape 4 : exécution requête
			st.executeUpdate(sql);

			// Si récup données alors étapes 5 (parcours Resultset)

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
			// Etape 6 : libérer ressources de la mémoire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void lireEnBase() {

		// Information d'accès à la base de données
		String url = "jdbc:mysql://localhost/formation";
		String login = "root";
		String passwd = "";
		Connection cn =null;
		Statement st =null;
		ResultSet rs =null;
		
		try {

			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.jdbc.Driver");

			// Etape 2 : récupération de la connexion
			cn = (Connection) DriverManager.getConnection(url, login, passwd);

			// Etape 3 : Création d'un statement
			st = (Statement) cn.createStatement();

			String sql = "SELECT * FROM javadb";

			// Etape 4 : exécution requête
			rs = (ResultSet) st.executeQuery(sql);

			// Si récup données alors étapes 5 (parcours Resultset)

			while (rs.next()) {
				System.out.println(rs.getString("personne"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
			// Etape 6 : libérer ressources de la mémoire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	}
