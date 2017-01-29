package feedrss.feedrss;

import org.springframework.data.repository.CrudRepository;

public interface PeopleRepositery extends CrudRepository<people, Long> {
	people FindByName(String name);
}
