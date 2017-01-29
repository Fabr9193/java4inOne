package feedrss.feedrss;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Entity

public class people {

	@Id
   	@GeneratedValue(strategy = GenerationType.AUTO) private
   	Long id;
 
   	@Column(unique = true, nullable = false) private
   	String name;
 
   	@Column(unique = true, nullable = false) private
   	String password;
 
   	public void setId(Long id2) {
		// TODO Auto-generated method stub
		
	}
	
}
