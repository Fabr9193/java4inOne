package feedrss.feedrss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;


@Controller
@EnableAutoConfiguration
public class App 
{

	/*    public static void main(String[] args) throws Exception {
	        RssParser parser = new RssParser(
	                "http://www.bfmtv.com/rss/info/flux-rss/flux-toutes-les-actualites/");
	read read = parser.readFeed();
	//System.out.println(read);
	for (ReadRss message : read.getMessages()) {
	       System.out.println(message);	        
	    }
	    }
	 */
	private static ServerSocket welcomeSocket;

	public static void getRss(Statement st, String user_id, DataOutputStream outToClient) throws SQLException, IOException
	{
		String query = "SELECT * FROM url WHERE user_id='" + user_id + "';";
		ResultSet res = (ResultSet) st.executeQuery(query);
		if (res.first())
		{
			do {
				RssParser parser = new RssParser(res.getString("myurl"));
				read read = parser.readFeed();
				for (ReadRss message : read.getMessages()) {
					outToClient.writeBytes(message.toString());
				}
			} while (res.next());
		}

	}

	public static void main( String[] args ) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{

		String clientSentence,clientpassword;
		String capitalizedSentence;
		welcomeSocket = new ServerSocket(4010);
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "feedrss";
		String driver = "com.mysql.jdbc.Driver";
		String user = "root";
		String pass = "root";
		System.out.println("bordel");
		while(true)
		{
			System.out.println("bordel2");
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("bordel99");
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			System.out.println("bordel3");
			BufferedReader inFromClient1 = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			System.out.println("bordel4");
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			System.out.println("bordelde merde ");
			clientSentence = inFromClient.readLine(); 
			clientpassword = inFromClient.readLine();

			System.out.println("Received User Name: " + clientSentence);
			System.out.println("Received Password: " + clientpassword);
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url+db, user, pass);
			Statement st = (Statement) con.createStatement();
			String query = "SELECT * FROM people WHERE login='" + clientSentence + "' AND password='" + clientSentence + "';";
			ResultSet res = (ResultSet) st.executeQuery(query);
			if (res.first())
			{
				String u = res.getString("login");
				String p = res.getString("password");
				String user_id = res.getString("id");
				if (clientSentence.equals(u) && clientpassword.equals(p)){
					capitalizedSentence = "Welcome "+clientSentence+" \n";
					outToClient.writeBytes(capitalizedSentence);
					boolean isUp = true;
					while (isUp) //read cmd here
					{
						String cmd;
						cmd = inFromClient.readLine();
						System.out.println("Received cmd: " + cmd);
						if (cmd.compareTo("/rss") == 0)
						{
							getRss(st, user_id, outToClient);
						}
						else if (cmd.compareTo("/logout") == 0)
						{
							capitalizedSentence = "200 - OK\n";
							outToClient.writeBytes(capitalizedSentence); 							
							res.close();
							st.close();
							con.close();
							inFromClient.close();
							outToClient.close();
							connectionSocket.close();
							isUp = false;
						}
						else
						{
							capitalizedSentence = "Bad Request!\n";
							outToClient.writeBytes(capitalizedSentence); 							
						}
					}
				}else{  
					capitalizedSentence = "Sorry, not authorized \n";
					outToClient.writeBytes(capitalizedSentence); 
					res.close();
					st.close();
					con.close();
					inFromClient.close();
					outToClient.close();
					connectionSocket.close();
				} 
			}
			else
			{
				capitalizedSentence = "Sorry, not authorized \n";
				outToClient.writeBytes(capitalizedSentence); 
				res.close();
				st.close();
				con.close();
				inFromClient.close();
				outToClient.close();
				connectionSocket.close();
			}
		}
	}
}

