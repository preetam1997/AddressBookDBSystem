package addressbook4;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.json.simple.parser.ParseException;

interface Command {
	void invoke();
}

public class DriverClass {

	private static Map<String, AddressBook> AddressBookMap = new HashMap<String, AddressBook>();
	public static Scanner myObj = new Scanner(System.in);
	private static LinkedList<String> addressList = new LinkedList<String>();
	private static Map<String, Command> commands = new HashMap<String, Command>();

	public static void CommandMapping(List<Contacts> list) {

		commands.put("City", new Command() {
			public void invoke() {
				list.stream().sorted(Comparator.comparing(Contacts::get_City)).collect(Collectors.toList()).stream()
						.forEach(i -> i.toString());
			}
		});

		commands.put("State", new Command() {
			public void invoke() {
				list.stream().sorted(Comparator.comparing(Contacts::get_State)).collect(Collectors.toList()).stream()
						.forEach(i -> i.toString());
			}
		});

		commands.put("Zip", new Command() {
			public void invoke() {
				list.stream().sorted(Comparator.comparing(Contacts::get_Zip)).collect(Collectors.toList()).stream()
						.forEach(i -> i.toString());
			}
		});
	}

	public static void MapAddress(AddressBook e) {

		System.out.println("Enter Address Book Name");
		String AddressBookName = myObj.nextLine();
		addressList.add(AddressBookName);

		AddressBookMap.put(AddressBookName, e);

	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, ParseException {

		System.out.println("Welcome to AddressBook Assignment");
		Scanner myObj = new Scanner(System.in);

		while (true) {
			System.out.println("1.UC1 Create Your Own Address Book");
			System.out.println("2.UC2 Add Address");
			System.out.println("3.UC3 Edit Entry Based on Name");
			System.out.println("4.UC4 Delete Entry Based on Name");
			System.out.println("5.Display Contacts");
			System.out.println("6.UC8 Search by City Name");
			System.out.println("7.UC8 Search by State Name");
			System.out.println("8.UC9 Search by City Name (Person Mapping)");
			System.out.println("9.UC9 Search by State Name (Person Mapping)");
			System.out.println("10.UC10 Count Persons in a City");
			System.out.println("11.UC10 Count Persons in a State");
			System.out.println("12.UC11 Sort based on name");
			System.out.println("13.UC12 Sort based on State, City or Zip");
			System.out.println("14.UC13 Write contacts to file");
			System.out.println("15.UC13 Read contacts from file");
			System.out.println("16.UC14 Write contacts to csv file");
			System.out.println("17.UC14 Read contacts from csv file");
			System.out.println("18.UC15 Write contacts to json file");
			System.out.println("19.UC15 Read contacts from json file");
			System.out.println("20.Exit");
			System.out.println("Enter your choice:");
			int choice = myObj.nextInt();
			switch (choice) {

			case 1:
				AddressBook addressbook = new AddressBook();
				MapAddress(addressbook);
				break;

			case 2:
				System.out.println("Enter Address Book name");
				Scanner myObj3 = new Scanner(System.in);
				String addressBookName = myObj3.nextLine();

				AddressBook e = AddressBookMap.get(addressBookName);
				if (e == null) {
					System.out.println("AddressBook Not Found");
					System.out.println("Creating New AddressBook");
					AddressBook addressBook = new AddressBook();
					MapAddress(addressBook);
					continue;

				}
				Contacts c = e.addAdress();
				break;

			case 3:
				System.out.println("Enter Address Book name");
				Scanner myObj1 = new Scanner(System.in);
				String addressBookName1 = myObj1.nextLine();
				AddressBook e1 = AddressBookMap.get(addressBookName1);
				if (e1 == null) {
					System.out.println("AddressBook Not Found");
					continue;
				}
				e1.editUsingName();

				break;

			case 4:
				System.out.println("Enter Address Book name");
				Scanner myObj2 = new Scanner(System.in);
				String addressBookName2 = myObj2.nextLine();
				AddressBook e2 = AddressBookMap.get(addressBookName2);
				if (e2 == null) {
					System.out.println("AddressBook Not Found");
					continue;
				}
				e2.delete();
				break;

			case 5:
				System.out.println("Enter Address Book name");
				Scanner myObj4 = new Scanner(System.in);
				String addressBookName4 = myObj4.nextLine();
				AddressBook e3 = AddressBookMap.get(addressBookName4);
				if (e3 == null) {
					System.out.println("AddressBook Not Found");
					continue;
				}
				e3.displayAllContacts();
				break;

			case 6:
				System.out.println("Enter City name");
				Scanner myObj6 = new Scanner(System.in);
				String city = myObj6.nextLine();
				addressList.stream().forEach(i -> AddressBookMap.get(i).SearchNameByCity(city));
				break;

			case 7:
				System.out.println("Enter State name");
				Scanner myObj7 = new Scanner(System.in);
				String State = myObj7.nextLine();
				addressList.stream().forEach(i -> AddressBookMap.get(i).SearchNameByState(State));
				break;

			case 8:
				System.out.println("Enter City Name (Person Mapping)");
				Scanner myObj8 = new Scanner(System.in);
				String City = myObj8.nextLine();
				addressList.stream().forEach(i -> AddressBookMap.get(i).getMappingByCity(City).stream()
						.forEach(p -> System.out.println(p.firstName + " " + p.lastName)));
				break;

			case 9:
				System.out.println("Enter State Name (Person Mapping)");
				Scanner myObj9 = new Scanner(System.in);
				String state = myObj9.nextLine();
				addressList.stream().forEach(i -> AddressBookMap.get(i).getMappingByState(state).stream()
						.forEach(p -> System.out.println(p.firstName + " " + p.lastName)));
				break;

			case 10:
				System.out.println("Enter State Name (Person Mapping) for Number of Persons in it");
				Scanner myObj10 = new Scanner(System.in);
				String City1 = myObj10.nextLine();

				System.out.println(AddressBook.PersonToCity.get(City1).size());
				break;

			case 11:
				System.out.println("Enter State Name (Person Mapping) for Number of Persons in it");
				Scanner myObj11 = new Scanner(System.in);
				String State1 = myObj11.nextLine();

				System.out.println(AddressBook.PersonToState.get(State1).size());
				break;

			case 12:
				System.out.println("Enter Address Book name");
				Scanner myObj12 = new Scanner(System.in);
				String addressBookName5 = myObj12.nextLine();
				AddressBook e7 = AddressBookMap.get(addressBookName5);
				if (e7 == null) {
					System.out.println("AddressBook Not Found");
					continue;

				}
				List<Contacts> l = e7.SortbyFirstName();
				l.stream().forEach(i -> i.toString());
				break;

			case 13:
				System.out.println("Enter Address Book name");
				Scanner myObj13 = new Scanner(System.in);
				String addressBookName6 = myObj13.nextLine();
				AddressBook e8 = AddressBookMap.get(addressBookName6);
				if (e8 == null) {
					System.out.println("AddressBook Not Found");
					continue;

				}
				System.out.println("Enter Choice");
				CommandMapping(e8.contactList);
				String Choice = myObj13.nextLine();
				commands.get(Choice).invoke();
				break;

			case 14:
				AddressBookFileIOService abfis = new AddressBookFileIOService();
				abfis.writeData(AddressBookMap);
				break;

			case 15:
				new AddressBookFileIOService().readData(AddressBookMap);
				break;
			case 16:
				new AddressBookCSVIOService().writeContactData(AddressBookMap);
				break;
			case 17:
				new AddressBookCSVIOService().readContactData(AddressBookMap);
				break;
			case 18:
				new AddressBookJSONIOService().writeData(AddressBookMap);
				break;
			case 19:
				new AddressBookJSONIOService().readData(AddressBookMap);
				break;
			case 20:
				return;
			}

		}

	}

}