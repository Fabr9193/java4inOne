package feedrss.feedrss;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;

@RestController @RequestMapping(value ="/people") 
public class PeopleController {

	@Resource
	private PeopleServices peopleService;
  
	@RequestMapping(method = RequestMethod.POST)
	public people createPlace(@RequestBody people people) {
 		return this.peopleService.createPeople(people);	
	}
 
	@RequestMapping(method = RequestMethod.GET)
	public Collection<people> getAllPeople() {
 	  	return this.peopleService.getAllPeople();	
	}
 
	@RequestMapping(value = "/{shortName}", method = RequestMethod.GET)
	public people getPlaceForShortName(@PathVariable(value = "Name") String Name) {
 		//find place by shortname
   		return this.peopleService.getPeopleByName(Name);
	}
 	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public void deletePlace(@PathVariable(value = "id") Long id) {
		this.peopleService.deletePeople(id);
	}
 
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public people updatePeople(@PathVariable(value = "id") Long id, @RequestBody people people) {
		people.setId(id);
 
 		return this.peopleService.updatePeople(people);
 	}
 
	public PeopleServices getPeopleService() { 
		return peopleService;
	}
 
	public void setPlaceService(PeopleServices peopleService) {
   		this.peopleService = peopleService;
   	} 
}
