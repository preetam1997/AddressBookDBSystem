package addressbook4;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;




public class AddressBook {
	public LinkedList<Contacts>  contactList;
	private LinkedList<String>  fNamelName;
	public static Map<String, List<Contacts>> PersonToCity = new HashMap<String,List<Contacts>>();
	public static Map<String, List<Contacts>> PersonToState = new HashMap<String,List<Contacts>>();
	Scanner myObj = new Scanner(System.in);
	
	public AddressBook() {
		
		contactList = new LinkedList<>();
		fNamelName = new LinkedList<>();
	}
	
	public Contacts addAdress() {
		
		System.out.println("Enter First Name");
		String firstName = myObj.nextLine();
		
		
		
		
		System.out.println("Enter Last Name");
		String lastName = myObj.nextLine();
		
		fNamelName.add(firstName+" "+lastName);
		System.out.println("Enter Address");
		String Address = myObj.nextLine();
		System.out.println("Enter City");
		String City = myObj.nextLine(); 
		System.out.println("Enter State");
		String State = myObj.nextLine();
		System.out.println("Enter zip Code");
		String zip = myObj.nextLine();
		System.out.println("Enter Phone Number");
		String phoneNumber = myObj.nextLine();
		System.out.println("Enter Email Address");
		String email = myObj.nextLine();
		Contacts contacts = new Contacts(firstName,lastName,Address,City,State,zip,phoneNumber,email);
		contactList.add(contacts);
		if(findDuplicateBySetAdd(contactList))
		{
			System.out.println("Contact Already Exists");
			return null;
		}
		else 
		{
			
			
			return contacts;
		}
		
		
		
	}
	
	public void delete() {
		
		System.out.println("Enter First Name");
		String firstName = myObj.nextLine();
		System.out.println("Enter Last Name");
		String lastName = myObj.nextLine();
		
		
		for(int i=0;i<contactList.size();i++) {
			
			if((contactList.get(i).firstName.equals(firstName) ) && (contactList.get(i).lastName.equals(lastName) )) {
				
				contactList.remove(i);				
				System.out.println("Contact Deleted");
			}
		}
		
		
		
	}
	
	
	
	
	public void displayAllContacts() {
		
		for(int i = 0;i<contactList.size();i++) {
			
			Contacts e = contactList.get(i);
			System.out.println("Printing Details for "+contactList.get(i).firstName+ " " + contactList.get(i).lastName);
			
			System.out.println(e.Address);
			System.out.println(e.City);
			System.out.println(e.State);
			System.out.println(e.zip);
			System.out.println(e.phoneNumber);
			System.out.println(e.email);
			System.out.println("========================");
			
		}
		
	}
	
	public void editUsingName() {
		
		System.out.println("Enter First Name");
		String firstName = myObj.nextLine();
		System.out.println("Enter Last Name");
		String lastName = myObj.nextLine();
		
		
		for(int i=0;i<contactList.size();i++) {
			
			if((contactList.get(i).firstName.equals(firstName) ) && (contactList.get(i).lastName.equals(lastName) )) {
				
				System.out.println("Enter New First Name");
				contactList.get(i).firstName = myObj.nextLine();
				System.out.println("Enter New Last Name");
				contactList.get(i).lastName = myObj.nextLine();
				System.out.println("Enter New Address");
				contactList.get(i).Address = myObj.nextLine();
				System.out.println("Enter New City");
				contactList.get(i).City = myObj.nextLine(); 
				System.out.println("Enter New State");
				contactList.get(i).State = myObj.nextLine();
				System.out.println("Enter New zip Code");
				contactList.get(i).zip = myObj.nextLine();
				System.out.println("Enter New Phone Number");
				contactList.get(i).phoneNumber = myObj.nextLine();
				System.out.println("Enter New Email Address");
				contactList.get(i).email = myObj.nextLine();
				
			}
			else
				System.out.println("Entry Not Found");
			
		}
		
		
		
	}
	
	public boolean findDuplicateBySetAdd(LinkedList<Contacts> list) {

        Set<Contacts> items = new HashSet<>();
        return (list.stream()
                .filter(n -> !items.add(n)) 
                .collect(Collectors.toSet()).size())>=1;
	}
	
	public void SearchNameByCity(String city) {
		
		contactList.stream().
				filter(i->i.City.matches(city)).
				collect(Collectors.toList()).
				forEach(p->System.out.println(p.firstName+" "+p.lastName));
		
	}
	
	public void SearchNameByState(String State) {
		
		contactList.stream().
			filter(i->i.State.matches(State)).
			collect(Collectors.toList()).
			forEach(p->System.out.println(p.firstName+" "+p.lastName));
			
		
		
	}
	
	public List<Contacts> getMappingByCity(String City) {
		PersonToCity = contactList.stream()
				.collect(Collectors.groupingBy(i->i.City));
		return PersonToCity.get(City);
	}
	
	public List<Contacts> getMappingByState(String State) {
		PersonToState = contactList.stream()
				.collect(Collectors.groupingBy(i->i.State));
		return PersonToState.get(State);
	}
	
	public List<Contacts> SortbyFirstName(){
	
		return contactList.stream().sorted(Comparator.comparing(Contacts::get_firstName)).collect(Collectors.toList());
	} 
	

		
		
}
	
	
	

