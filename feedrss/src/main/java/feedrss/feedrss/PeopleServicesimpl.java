package feedrss.feedrss;

import org.springframework.stereotype.Service;
import javax.annotation.Resource; 
import java.util.Collection;


@Service(value = "PeopleService")

public class PeopleServicesimpl implements PeopleServices {
		@Resource
		private PeopleRepositery PeopleRepository;

		/*@Override
		public Collection<people> getAllPeoples() {
	 		return IteratorUtils.toList(this.PeopleRepository.findAll().iterator());
		}*/

		@Override
	 	public people getPeopleById(Long id) {
	 		return this.PeopleRepository.findOne(id);
	 	}

		@Override
	 	public people createPeople(people people) {
	 		return this.PeopleRepository.save(people);
	 	}

		@Override
	 	public people updatePeople(people people) {
	 		return this.PeopleRepository.save(people);
	 	}

		@Override
	 	public void deletePeople(Long id) {
	 		this.PeopleRepository.delete(id);
	 	}

		@Override
	 	public people getPeopleByName(String Name) {
	 		return this.PeopleRepository.FindByName(Name);
	 	}

		public PeopleRepositery getPeopleRepository() {
	 		return PeopleRepository;
	 	}

		public void setPeopleRepository(PeopleRepositery PeopleRepository) {
	 		this.PeopleRepository = PeopleRepository;	
		}

		@Override
		public Collection<people> getAllPeople() {
			// TODO Auto-generated method stub
			return null;
		}
	}

