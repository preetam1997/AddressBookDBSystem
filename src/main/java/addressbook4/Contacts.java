package addressbook4;

import java.time.LocalDate;
import java.util.Objects;

public class Contacts {
	//parameters
	public String id;
	public String firstName;
	public String lastName;
	public String Address;
	public String City;
	public String State;
	public String zip;
	public String phoneNumber;
	public String email;
	public LocalDate date;
	
	//constructor1
	public Contacts(String firstName, String lastName, String Address, String City, String State, String zip,
			String phoneNumber, String email) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.Address = Address;
		this.City = City;
		this.State = State;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.email = email;

	}
	
	//constructor2
	public Contacts(String id, String firstName, String lastName, String Address, String City, String State, String zip,
			String phoneNumber, String email, LocalDate date) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.Address = Address;
		this.City = City;
		this.State = State;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, Address, City, State, zip, phoneNumber, email);

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Contacts)) {
			return false;
		}
		Contacts c = (Contacts) obj;
		return firstName.equals(c.firstName) && lastName.equals(c.lastName);
	}

	//getters
	public String get_firstName() {

		return firstName;
	}

	public String get_City() {

		return City;
	}

	public String get_State() {

		return State;
	}

	public String get_Zip() {

		return zip;
	}
	
	//toString() method
	public String toString() {
		return firstName + " " + lastName + " " + Address + " " + City + " " + State + " " + zip + " " + phoneNumber
				+ " " + email;
	}
}