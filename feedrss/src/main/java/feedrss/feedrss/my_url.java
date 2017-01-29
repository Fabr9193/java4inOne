package feedrss.feedrss;

import javax.persistence.*;

public class my_url {
	
	@Id
   	@GeneratedValue(strategy = GenerationType.AUTO) private
   	Long id;
 
   	@Column(unique = true, nullable = false) private
   	String url;
 
   	@Column(unique = true, nullable = false) private
   	long id_client;
 
}
