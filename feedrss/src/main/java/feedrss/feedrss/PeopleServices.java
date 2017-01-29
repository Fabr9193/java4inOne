package feedrss.feedrss;

import java.util.Collection;

public interface PeopleServices {

Collection<people> getAllPeople();
	
	people getPeopleById(Long id);
	
	people createPeople(people people);
	
	people updatePeople(people people); void deletePeople(Long id);
	
	people getPeopleByName(String Name);
	
	
}
