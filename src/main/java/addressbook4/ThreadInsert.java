package addressbook4;

import java.util.Set;

import org.apache.commons.collections.map.HashedMap;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ThreadInsert {

	public void InsertUsingThread(Set<Entry<AddressBook, Contacts>> entrySet) {
		Map<Integer, Boolean> contactAdditionStatus = new HashedMap();
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		entrySet.forEach(entry -> {
			Runnable task = () -> {
				try {
					contactAdditionStatus.put(entry.getValue().hashCode(), false);
					new AddressBookDBIOService().insertData(entry);
					contactAdditionStatus.put(entry.getValue().hashCode(), true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			};
			Thread thread = new Thread(task);
			thread.start();
		});
		System.out.println("from func" + " " + result);
		while (contactAdditionStatus.containsValue(false)) {
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		
	}

	public static void main(String[] args) {
		
	}
}
