/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package addressbook4;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;

public class AddressBookTest {
	@Test
	public void givenDatabase_WhenDataRetrieved_ShouldPassTest() {
		assertEquals("Lionel", new AddressBookDBIOService().retrieveData("normal").get(5).firstName);
	}

	@Test
	public void givenDatabase_WhenDataUpdated_ShouldPassTest() {
		assertEquals(1, new AddressBookDBIOService().updateData("Preetam", "123456"));
	}

	@Test
	public void givenDatabase_WhenDataRetrievedWithinADateRange_ShouldPassTest() {
		assertEquals(4, new AddressBookDBIOService().retrieveData("dateWise").size());
	}

	@Test
	public void givenDatabase_WhenDataRetrievedCountByCityOrState_ShouldPassTest() {

		AddressBookDBIOService addressBook = new AddressBookDBIOService();
		addressBook.retrieveData("state");
		assertEquals(2, (int) addressBook.CountMap.get("Bihar"));
	}

	@Test
	public void givenDatabase_WhenInsertedData_ShouldPassTest() {
		Map<AddressBook, Contacts> map = new HashedMap();

		AddressBook addressBook = new AddressBook("103", "addressBook3", "profession");
		Contacts contact = new Contacts("011", "Xavi", "Hernandes", "Spain", "Catalonia", "Barcelona", "123", "123",
				"abc@xyz.com", LocalDate.parse("2020-10-10"));

		map.put(addressBook, contact);
		map.entrySet().forEach(entry -> {
			try {
				int result = new AddressBookDBIOService().insertData(entry);
				assertEquals(1, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void givenDatabase_WhenInsertedDataUsingThreads_ShouldPassTest() {
		Map<AddressBook, Contacts> map = new HashedMap();
		
		AddressBook addressBook = new AddressBook("103", "addressBook3", "profession");
		AddressBook addressBook1 = new AddressBook("104", "addressBook4", "friend");
		Contacts contact = new Contacts("012", "Christopher", "Columbus", "Spain", "Catalonia", "Barcelona", "123",
				"123", "abc@xyz.com", LocalDate.parse("2020-10-10"));
		Contacts contact1 = new Contacts("013", "Gallileo", "Gallilei", "Spain", "Catalonia", "Barcelona", "123", "123",
				"abc@xyz.com", LocalDate.parse("2020-10-10"));
		map.put(addressBook, contact);
		map.put(addressBook1, contact1);
		new ThreadInsert().InsertUsingThread(map.entrySet());
		List<Contacts> contacts= new AddressBookDBIOService().retrieveData("normal");
		boolean result = contacts.contains(contact1);
		assertEquals(true, result);
	} 
	
//	delete from addressbookname where id = '104';delete from contacts where id = '013';delete from contacts where id = '012';delete from contacts where id = '011';
}
