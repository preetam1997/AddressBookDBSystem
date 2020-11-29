package addressbook4;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AddressBookJSONIOService {
	public static String OUTPUT_FILE_NAME = "C:\\Users\\Preetam\\eclipse-workspace\\addressbook3\\resource\\output_to.json";
	public static String INPUT_FILE_NAME = "C:\\Users\\Preetam\\eclipse-workspace\\addressbook3\\resource\\input_from.json";

	
	@SuppressWarnings({ "unchecked", "resource" })
	public void writeData(Map<String, AddressBook> AddressBookMap) throws IOException {
		FileWriter fileWriter = new FileWriter(OUTPUT_FILE_NAME);
		JSONArray contactList = new JSONArray();
		AddressBookMap.entrySet().stream().forEach(entry -> {
			entry.getValue().contactList.stream().forEach(contact -> {
				JSONObject contactDetails = new JSONObject();
				contactDetails.put("AddressBook", entry.getKey());
				contactDetails.put("FirstName", contact.firstName);
				contactDetails.put("LastName", contact.lastName);
				contactDetails.put("Address", contact.Address);
				contactDetails.put("City", contact.City);
				contactDetails.put("State", contact.State);
				contactDetails.put("Zip", contact.zip);
				contactDetails.put("PhoneNumber", contact.phoneNumber);
				contactDetails.put("E-mail", contact.email);

				JSONObject contactObject = new JSONObject();
				contactObject.put("contact", contactDetails);

				contactList.add(contactObject);

			});
		});
		fileWriter.write(contactList.toJSONString());
		fileWriter.flush();
	}

	@SuppressWarnings("unchecked")
	public void readData(Map<String, AddressBook> AddressBookMap) throws IOException, ParseException {
		FileReader fileReader = new FileReader(INPUT_FILE_NAME);
		JSONParser jsonParser = new JSONParser();

		Object obj = jsonParser.parse(fileReader);
		JSONArray contactList = (JSONArray) obj;
		contactList.forEach(contact -> parseContactObject((JSONObject) contact, AddressBookMap));
	}

	private void parseContactObject(JSONObject contact, Map<String, AddressBook> AddressBookMap) {
		JSONObject contactObject = (JSONObject) contact.get("contact");

		String firstName = (String) contactObject.get("FirstName");
		String lastName = (String) contactObject.get("LastName");
		String address = (String) contactObject.get("Address");
		String city = (String) contactObject.get("City");
		String state = (String) contactObject.get("State");
		String zip = (String) contactObject.get("Zip");
		String phoneNumber = (String) contactObject.get("PhoneNumber");
		String email = (String) contactObject.get("E-mail");
		String addressBookName = (String) contactObject.get("AddressBook");

		AddressBook addressBook = AddressBookMap.get(addressBookName);
		if (addressBook == null) {
			AddressBookMap.put(addressBookName, new AddressBook());
			AddressBookMap.get(addressBookName).contactList
					.add(new Contacts(firstName, lastName, address, city, state, zip, phoneNumber, email));
		}

		else {
			AddressBookMap.get(addressBookName).contactList
					.add(new Contacts(firstName, lastName, address, city, state, zip, phoneNumber, email));
		}

	}
}
