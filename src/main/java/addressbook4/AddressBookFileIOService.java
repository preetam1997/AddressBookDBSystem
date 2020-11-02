package addressbook4;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;

public class AddressBookFileIOService {
	public static String INPUT_FROM_FILE_NAME = "address-input-file.txt";
	public static String OUTPUT_TO_FILE_NAME = "address-output-file.txt";

	

	public void readData(Map<String, AddressBook> AddressBookMap) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(INPUT_FROM_FILE_NAME));

			for (String line : lines) {
				String[] parameter = line.split(" ");
				System.out.println(parameter.length);
				AddressBook newAddressBook = AddressBookMap.get(parameter[0]);
				if (newAddressBook == null) {
					AddressBookMap.put(parameter[0], new AddressBook());
					AddressBookMap.get(parameter[0]).contactList
							.add(new Contacts(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7], parameter[8]));
				} else {
					AddressBookMap.get(parameter[0]).contactList
							.add(new Contacts(parameter[1], parameter[2], parameter[3], parameter[4], parameter[5], parameter[6], parameter[7], parameter[8]));
				}

			}

		} catch (IOException e) {

		}
	}

	@SuppressWarnings("unused")
	public void writeData(Map<String, AddressBook> AddressBookMap) {
		StringBuffer empBuffer = new StringBuffer();

		AddressBookMap.entrySet().forEach(entry -> {
			String contactString = entry.getKey().concat("\n");
			empBuffer.append(contactString);
			entry.getValue().contactList.forEach(contact -> {
				String contacts = contact.toString().concat("\n");
				empBuffer.append(contacts);
			});
		});

		try {
			Files.write(Paths.get(OUTPUT_TO_FILE_NAME), empBuffer.toString().getBytes());
		} catch (IOException e) {

		}

	}

}
