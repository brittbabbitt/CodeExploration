package crudIT;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import constants.APITestingConstants;
import io.restassured.response.ValidatableResponse;
import serviceOpsUtils.PostRequest;

public class CreateAllocationsPostTest_Unhappy {

	PostRequest postRequest = null;
	ValidatableResponse response = null;

	JsonObject jsonObject = null;
	JsonObject dc5412 = null;
	JsonObject dc5413 = null;
	JsonArray transloadAllocationDcDTOs = null;

	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

	Integer beforeAllocId = null;
	Integer afterAllocId = null;

	@Before
	public void setUp() {

		try {

			connection = DriverManager.getConnection(APITestingConstants.TLD_ALLOC_URL,
					APITestingConstants.TLD_ALLOC_USERNAME, APITestingConstants.TLD_ALLOC_PASSWORD);

			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT MAX(ALLOC_ID) FROM TLD_ALLOC_HDR");

			resultSet.next();
			beforeAllocId = resultSet.getInt("MAX(ALLOC_ID)");

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		jsonObject = new JsonObject();

		// Header Properties
		jsonObject.addProperty("transloadNumber", "5097");
		jsonObject.addProperty("etaDate", "2017-02-06");
		jsonObject.addProperty("asnNumber", 10024589);
		jsonObject.addProperty("poNumber", "PO1");
		jsonObject.addProperty("poCreationDate", "2017-02-06");
		jsonObject.addProperty("poTypeCode", 1);
		jsonObject.addProperty("vendorNumber", 567346597);
		jsonObject.addProperty("departmentNumber", 10);
		jsonObject.addProperty("bolNumber", "10024589");
		jsonObject.addProperty("statusCode", 2);

		// Detail Properties
		jsonObject.addProperty("skuNumber", 123456);
		jsonObject.addProperty("shippedQty", 100);
		jsonObject.addProperty("skuTypeCode", 1);
		jsonObject.addProperty("buyUomQty", 5);
		jsonObject.addProperty("buyPackUoiCode", 1);
		jsonObject.addProperty("containerId", "100001");

		// Multiple DC and Alloc Properties
		dc5412 = new JsonObject();
		dc5412.addProperty("dcNumber", 5412);
		dc5412.addProperty("allocQty", 50);

		dc5413 = new JsonObject();
		dc5413.addProperty("dcNumber", 5413);
		dc5413.addProperty("allocQty", 50);

		// Array for DC and Alloc Properties
		transloadAllocationDcDTOs = new JsonArray();
		transloadAllocationDcDTOs.add(dc5412);
		transloadAllocationDcDTOs.add(dc5413);
		jsonObject.add("transloadAllocationDcDTOs", transloadAllocationDcDTOs);

	}

	@Test
	public void requestBadDateInformation() {
		jsonObject.addProperty("poCreationDate", "2017-13-06");

		postRequest = new PostRequest(APITestingConstants.CREATE_ALLOCATION_URL, jsonObject.toString());
		//response = postRequest.postRequestResponse();

		response.assertThat().statusCode(500);
	}

	@Test
	public void requestIncorrectValue() {
		jsonObject.addProperty("statusCode", 'a');

		postRequest = new PostRequest(APITestingConstants.CREATE_ALLOCATION_URL, jsonObject.toString());
		//response = postRequest.postRequestResponse();

		response.assertThat().statusCode(500);
	}

	@Test
	public void checkHdrRowRemovedOnInvalidDtl() {
		jsonObject.addProperty("statusCode", 'a');

		postRequest = new PostRequest(APITestingConstants.CREATE_ALLOCATION_URL, jsonObject.toString());
		//response = postRequest.postRequestResponse();

		try {
			connection = DriverManager.getConnection(APITestingConstants.TLD_ALLOC_URL,
					APITestingConstants.TLD_ALLOC_USERNAME, APITestingConstants.TLD_ALLOC_PASSWORD);

			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT MAX(ALLOC_ID) FROM TLD_ALLOC_HDR");

			resultSet.next();
			afterAllocId = resultSet.getInt("MAX(ALLOC_ID)");

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertEquals(beforeAllocId, afterAllocId);
	}
}
