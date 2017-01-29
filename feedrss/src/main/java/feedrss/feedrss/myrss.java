package feedrss.feedrss;
import javax.persistence.*;


@Entity
public class myrss {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@Column(unique = true, nullable = false) private
String Url;

	
}
