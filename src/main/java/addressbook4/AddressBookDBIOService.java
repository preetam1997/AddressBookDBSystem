package addressbook4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBIOService {

	private List<Contacts> getContactData(ResultSet resultSet) {
		List<Contacts> contactList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				String addressBookType = resultSet.getString("addressBookType");
				String addressBookName = resultSet.getString("addressBookName");

				String fName = resultSet.getString("firstName");
				String lName = resultSet.getString("lastName");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				String zip = resultSet.getString("zip");
				String phoneNumber = resultSet.getString("phoneNumber");
				String email = resultSet.getString("email");

				contactList.add(new Contacts(fName, lName, address, city, state, zip, phoneNumber, email));

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return contactList;

	}

	public Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AddressBook", "root", "Preetam@1997");
		return con;
	}

	public List<Contacts> retrieveData() {
		String sql = "select addressBookType,addressBookName,firstName,lastName,address,city,state,zip,phoneNumber,email from addressbooktype,addressbookname,contactaddressbookmap,contacts where addressbooktype.addressBookId = addressbookname.id and contactaddressbookmap.addressBookId = addressbookname.id and contactaddressbookmap.contactId = contacts.id";
		List<Contacts> contactList = new ArrayList<>();
		ResultSet resultSet = null;
		try (Connection connection = this.getConnection()) {

			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			contactList = this.getContactData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}
	
	public int updateData(String fname, String phoneNumber) {
		String sql = String.format("update contacts set phoneNumber = '%s' where firstName = '%s'", phoneNumber, fname);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public static void main(String[] args) throws SQLException {
		System.out.println(new AddressBookDBIOService().getConnection());
		List<Contacts> k = new AddressBookDBIOService().retrieveData();
		System.out.println(new AddressBookDBIOService().updateData("Preetam", "654321"));
	}

}
