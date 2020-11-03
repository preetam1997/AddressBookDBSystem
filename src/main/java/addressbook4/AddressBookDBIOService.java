package addressbook4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.HashedMap;

public class AddressBookDBIOService {

	private PreparedStatement contactDataStatement;
	public Map<String, Integer> CountMap = new HashedMap();

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

	private void prepareMap(ResultSet resultSet, String type) {
		try {
			CountMap.clear();
			while (resultSet.next()) {
				if (type.equals("city")) {
					String city = resultSet.getString("city");
					int count = resultSet.getInt("count(*)");
					CountMap.put(city, count);
				} else {
					String state = resultSet.getString("state");
					int count = resultSet.getInt("count(*)");
					CountMap.put(state, count);
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private void preparedStatementForContactData(String arg1, String arg2) {
		try {
			Connection connection = this.getConnection();
			String sql = String.format("Select %s,count(*) from contacts group by %s", arg1, arg2);
			contactDataStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AddressBook", "root", "Preetam@1997");
		return con;
	}

	public String getStatement(String type) {
		switch (type) {
		case "normal":
			return "select addressBookType,addressBookName,firstName,lastName,address,city,state,zip,phoneNumber,email from addressbooktype,addressbookname,contactaddressbookmap,contacts where addressbooktype.addressBookId = addressbookname.id and contactaddressbookmap.addressBookId = addressbookname.id and contactaddressbookmap.contactId = contacts.id";
		case "dateWise":
			return "select addressBookType,addressBookName,firstName,lastName,address,city,state,zip,phoneNumber,email from addressbooktype,addressbookname,contactaddressbookmap,contacts where addressbooktype.addressBookId = addressbookname.id and contactaddressbookmap.addressBookId = addressbookname.id and contactaddressbookmap.contactId = contacts.id and entryDate>='2019-02-10'and entryDate<='2020-07-01'";
		case "state":
			if (contactDataStatement == null) {
				this.preparedStatementForContactData("state", "state");
			}

			System.out.println(contactDataStatement.toString().split(":")[1]);
			return contactDataStatement.toString().split(":")[1];

		case "city":
			if (contactDataStatement == null) {
				this.preparedStatementForContactData("city", "city");
			}

			System.out.println(contactDataStatement.toString().split(":")[1]);
			return contactDataStatement.toString().split(":")[1];

		default:
			break;
		}
		return null;

	}

	public List<Contacts> retrieveData(String type) {
		String sql = getStatement(type);
		List<Contacts> contactList = new ArrayList<>();
		ResultSet resultSet = null;
		try (Connection connection = this.getConnection()) {

			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (type.equals("state") || type.equals("city")) {
				switch (type) {
				case "state":
					this.prepareMap(resultSet, "state");
					break;

				case "city":
					this.prepareMap(resultSet, "city");
					break;

				default:
					break;
				}

			} else {
				contactList = this.getContactData(resultSet);
			}

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

	public int insertData(Entry<AddressBook, Contacts> entry) throws SQLException {

		AddressBookDBIOService addressBookDBIOService = new AddressBookDBIOService();
		Contacts contact = entry.getValue();
		AddressBook addressBook = entry.getKey();
		int rowAffected = 0;
		Connection connection = null;
		try {

			connection = this.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (Statement statement = connection.createStatement()) {
			String sql = String.format("insert into contacts values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
					contact.id, contact.firstName, contact.lastName, contact.Address, contact.City, contact.State,
					contact.zip, contact.phoneNumber, contact.email, Date.valueOf(contact.date));
			rowAffected = statement.executeUpdate(sql);
			System.out.println(rowAffected);
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
			
		}
		if (!addressBookIsPresent(addressBook.id)) {

			try (Statement statement = connection.createStatement()) {
				String addressBookNameSql = String.format("insert into addressbookname values('%s','%s')",
						addressBook.id, addressBook.name);
				rowAffected = statement.executeUpdate(addressBookNameSql);
				System.out.println(rowAffected);
			} catch (SQLException e) {
				connection.rollback();
			}

			try (Statement statement = connection.createStatement()) {
				String addressBookTypeSql = String.format("insert into addressbookname values('%s','%s')",
						addressBook.id, addressBook.type);
				rowAffected = statement.executeUpdate(addressBookTypeSql);
				System.out.println(rowAffected);
			} catch (SQLException e) {
				connection.rollback();
			}
		}

		try (Statement statement = connection.createStatement()) {
			String contactaddressbookmapSql = String.format("insert into contactaddressbookmap values('%s','%s')",
					contact.id, addressBook.id);
			rowAffected = statement.executeUpdate(contactaddressbookmapSql);
			System.out.println(rowAffected);
		} catch (SQLException e) {
			connection.rollback();
		}
//		try {
//			try {
//				connection.commit();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} 
		finally {
			if (connection != null) {
				connection.close();
			}
		}
		return rowAffected;
	}

	private boolean addressBookIsPresent(String id) {
		String sql = "select id from addressbookname";
		ResultSet resultSet = null;
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				if (id.equals(resultSet.getString("id"))) {
					return true;
				}
			}
		} catch (SQLException e) {
		}
		return false;
	}

	
}
