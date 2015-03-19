package cm.service;

import cm.dao.PersonDao;
import cm.model.PersonInfo;

public class PersonService {

	private static PersonDao personDao = new PersonDao();


	public static void addPerson(PersonInfo personInfo) {
		if( (getPerson(personInfo)) == null) {
			personDao.add(personInfo);
		} else {
			System.out.println("the user has exited");
		}
	}
	
	public static PersonInfo getPerson(PersonInfo personInfo) {
		return personDao.search(personInfo.getUsername());
	}
	public static PersonInfo getPerson(String userName) {
		return personDao.search(userName);
	}
	public static PersonInfo getPerson(int rank) {
		return personDao.search(rank);
	}
}
