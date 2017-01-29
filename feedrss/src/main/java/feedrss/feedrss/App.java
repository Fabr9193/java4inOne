package feedrss.feedrss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/*@Configuration
@ComponentScan
@Controller*/
//@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
public class App 
{
	
	/*    	SpringApplication.run(App.class, args);
	}


	  public static void main(String[] args) throws Exception {
	        RssParser parser = new RssParser(
	                "http://www.bfmtv.com/rss/info/flux-rss/flux-toutes-les-actualites/");
	read read = parser.readFeed();
	//System.out.println(read);
	for (ReadRss message : read.getMessages()) {
	       System.out.println(message);	        
	    }
	    }*/
	 
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
		welcomeSocket = new ServerSocket(4011);
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "feedrss";
		String driver = "com.mysql.jdbc.Driver";
		String user = "root";
		String pass = "toto";
		String chose_inutile_recus;
		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept();

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
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
