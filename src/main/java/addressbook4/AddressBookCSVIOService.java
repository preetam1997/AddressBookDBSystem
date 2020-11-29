package addressbook4;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class AddressBookCSVIOService {

	// fields
	public static String OUTPUT_FILE_NAME = "C:\\Users\\Preetam\\eclipse-workspace\\addressbook3\\resource\\output_to.csv";
	public static String INPUT_FILE_NAME = "C:\\Users\\Preetam\\eclipse-workspace\\addressbook3\\resource\\input_from.csv";

	// method to write to csv file
	public void writeContactData(Map<String, AddressBook> AddressBookMap) throws IOException {
		FileWriter fileWriter = new FileWriter(OUTPUT_FILE_NAME);
		CSVWriter writer = new CSVWriter(fileWriter);
		List<String[]> contacts = new ArrayList<String[]>();
		contacts.add(new String[] { "AddressBook", "FirstName", "LastName", "Address", "City", "State", "Zip",
				"PhoneNumber", "E-mail" });

		AddressBookMap.entrySet().stream().forEach(entry -> {
			entry.getValue().contactList.stream().forEach(contact -> {
				String[] contact_ = { entry.getKey(), contact.firstName, contact.lastName, contact.Address,
						contact.City, contact.State, contact.zip, contact.phoneNumber, contact.email };
				contacts.add(contact_);
			});
		});
		writer.writeAll(contacts);
		writer.close();
	}

	// method to read to csv file
	public void readContactData(Map<String, AddressBook> AddressBookMap) throws IOException {
		FileReader fileReader = new FileReader(INPUT_FILE_NAME);
		CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
		String[] nextRecord;
		while ((nextRecord = csvReader.readNext()) != null) {
			AddressBook addressBook = AddressBookMap.get(nextRecord[0]);
			if (addressBook == null) {
				AddressBookMap.put(nextRecord[0], new AddressBook());
				AddressBookMap.get(nextRecord[0]).contactList.add(new Contacts(nextRecord[1], nextRecord[2],
						nextRecord[3], nextRecord[4], nextRecord[5], nextRecord[6], nextRecord[7], nextRecord[8]));
			} else {
				AddressBookMap.get(nextRecord[0]).contactList.add(new Contacts(nextRecord[1], nextRecord[2],
						nextRecord[3], nextRecord[4], nextRecord[5], nextRecord[6], nextRecord[7], nextRecord[8]));
			}
		}
	}
}
