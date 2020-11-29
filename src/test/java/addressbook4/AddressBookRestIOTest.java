package addressbook4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AddressBookRestIOTest {
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	private Response getContactList() {
		Response response = RestAssured.get("/contacts");
		return response;
	}
	
	private void updateName(JsonArray jArr, String id, String newName) {
		jArr.forEach(item->{
			JsonObject arrayItem = (JsonObject)item;
			if(arrayItem.get("id").getAsString().equals(id)) {
				arrayItem.addProperty("firstName",newName);
			}
		});
		
	}

	@Test
	public void onCallingList_ReturnContactList() {
		Response response = getContactList();
		System.out.println(response.asString());
		response.then().body("details.id", Matchers.hasItems("1"));
		List<Contacts> k = response.jsonPath().getList("details",Contacts.class);
		Contacts contact = new Contacts("1", "Christopher", "Columbus", "Spain", "Catalonia", "Barcelona", "123",
				"123", "abc@xyz.com",null);
		System.out.println();
		assertTrue(k.contains(contact));
		
		
	}

	@Test
	public void givenEmployee_OnPost_ShouldPassTest() {

		Response response = getContactList();
		Gson gson = new Gson();

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", "3");
		jsonObject.addProperty("addressBookName", "addressBook1");
		jsonObject.addProperty("firstName", "Virender");
		jsonObject.addProperty("lastName", "Singh");
		jsonObject.addProperty("city", "city1");
		jsonObject.addProperty("state", "state1");
		jsonObject.addProperty("zip", "123");
		jsonObject.addProperty("phoneNumber", "123");
		jsonObject.addProperty("email", "someuser@gmail.com");

		JsonObject jsonObj = gson.fromJson(response.asString(), JsonElement.class).getAsJsonObject();
		JsonArray jArr = jsonObj.get("details").getAsJsonArray();
		jArr.add(jsonObject);
		jsonObj.add("details", jArr);
		Response postResponse = RestAssured.given().contentType("application/json").body(jsonObj.toString())
				.post("/contacts");
		assertEquals(201, postResponse.getStatusCode());
		
	}
	
	@Test
	public void givenEmployee_OnUpdate_ShouldPassTest() {

		Response response = getContactList();
		Gson gson = new Gson();
		JsonObject jsonObj = gson.fromJson(response.asString(), JsonElement.class).getAsJsonObject();
		JsonArray jArr = jsonObj.get("details").getAsJsonArray();
		String id = "3";
		String newName = "Yuvraj";
		updateName(jArr,id,newName);
		Response updateResponse = RestAssured.given().contentType("application/json").body(jsonObj.toString())
				.put("/contacts");
		assertEquals(200, updateResponse.getStatusCode());
		
	}

	
	
	@Test
	public void givenEmployee_OnDelete_ShouldReturnAddedEmployee() {

		Response response = getContactList();
		Gson gson = new Gson();
		JsonObject jsonObj = gson.fromJson(response.asString(), JsonElement.class).getAsJsonObject();
		JsonArray jArr = jsonObj.get("details").getAsJsonArray();
		jArr.remove(Integer.parseInt("3")-1);
		Response updateResponse = RestAssured.given().contentType("application/json").body(jsonObj.toString())
				.put("/contacts");
		
		assertEquals(200, updateResponse.getStatusCode());
		
	}
}
