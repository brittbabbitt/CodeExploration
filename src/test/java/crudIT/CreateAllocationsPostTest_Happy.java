package crudIT;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import constants.AllocationConstants;
import io.restassured.response.ValidatableResponse;
import serviceOpsUtils.PostRequest;

public class CreateAllocationsPostTest_Happy extends Assert {

	PostRequest postRequest = null;
	ValidatableResponse response = null;

	JsonObject jsonObject = null;
	JsonObject dc5412 = null;
	JsonObject dc5413 = null;
	JsonArray transloadAllocationDcDTOs = null;

	@Before
	public void setUp() {

		jsonObject = new JsonObject();
		//TODO: GET DATA file for JSON request sample


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

		postRequest = new PostRequest(AllocationConstants.ALLOCATION_URL, jsonObject.toString());
		response = postRequest.getResponse();

	}

	@Test
	public void returnResponseStatusCodeCreated() throws Exception {
		response.assertThat().statusCode(201);
	}

	@Test
	public void checkResponseBodyCreatedHeaderProperties() throws Exception {
		response.body("asnNumber", equalTo(10024589));
		response.body("allocationDate", equalTo(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
		response.body("poNumber", equalTo("PO1"));
		response.body("transloadDcNumber", equalTo("5097"));

		response.body("poTypeCode", equalTo(1));
		response.body("vendorNumber", equalTo(567346597));

		response.body("departmentNumber", equalTo(10));
		response.body("expectedArrivalDate", equalTo("2017-02-06"));
		response.body("poCreationDate", equalTo("2017-02-06"));
		response.body("bolNumber", equalTo("10024589"));
		response.body("statusCode", equalTo(2));// placeholder may change
	}

	@Test
	public void checkResponseBodyCreatedDetailProperties() throws Exception {
		ArrayList<Integer> skuNumberResultList = new ArrayList<>();
		skuNumberResultList.add(123456);
		skuNumberResultList.add(123456);
		response.body("allocationDetails.skuNumber", response -> equalTo(skuNumberResultList));

		ArrayList<String> dcNumberResultList = new ArrayList<>();
		dcNumberResultList.add("5412");
		dcNumberResultList.add("5413");
		response.body("allocationDetails.dcNumber", response -> equalTo(dcNumberResultList));

		ArrayList<String> lastUpdatedUserIdResultList = new ArrayList<>();
		lastUpdatedUserIdResultList.add("TL_ALLOC");
		lastUpdatedUserIdResultList.add("TL_ALLOC");
		response.body("allocationDetails.lastUpdatedUserId", response -> equalTo(lastUpdatedUserIdResultList));

		ArrayList<Timestamp> lastUpdatedTimestampResultList = new ArrayList<>();
		lastUpdatedTimestampResultList.add(new Timestamp(System.currentTimeMillis()));
		lastUpdatedTimestampResultList.add(new Timestamp(System.currentTimeMillis()));
		response.body("allocationDetails.lastUpdatedTimestamp", response -> notNullValue());
		response.body("allocationDetails.lastUpdatedTimestamp", response -> not(equalTo(0)));

		ArrayList<String> transloadNumberResultList = new ArrayList<>();
		transloadNumberResultList.add("5097");
		transloadNumberResultList.add("5097");
		response.body("allocationDetails.transloadNumber", response -> equalTo(transloadNumberResultList));

		ArrayList<String> expectedArrivalDateResultList = new ArrayList<>();
		expectedArrivalDateResultList.add("2017-02-06");
		expectedArrivalDateResultList.add("2017-02-06");
		response.body("allocationDetails.expectedArrivalDate", response -> equalTo(expectedArrivalDateResultList));

		ArrayList<Integer> shippedQuantityResultList = new ArrayList<>();
		shippedQuantityResultList.add(100);
		shippedQuantityResultList.add(100);
		response.body("allocationDetails.shippedQuantity", response -> equalTo(shippedQuantityResultList));

		ArrayList<Integer> skuTypeCodeResultList = new ArrayList<>();
		skuTypeCodeResultList.add(1);
		skuTypeCodeResultList.add(1);
		response.body("allocationDetails.skuTypeCode", response -> equalTo(skuTypeCodeResultList));

		ArrayList<Integer> buyUomQtyResultList = new ArrayList<>();
		buyUomQtyResultList.add(5);
		buyUomQtyResultList.add(5);
		response.body("allocationDetails.buyUomQty", response -> equalTo(buyUomQtyResultList));

		ArrayList<Integer> buyPackCodeResultList = new ArrayList<>();
		buyPackCodeResultList.add(1);
		buyPackCodeResultList.add(1);
		response.body("allocationDetails.buyPackCode", response -> equalTo(buyPackCodeResultList));

		ArrayList<String> containerIdResultList = new ArrayList<>();
		containerIdResultList.add("100001");
		containerIdResultList.add("100001");
		response.body("allocationDetails.containerId", response -> equalTo(containerIdResultList));

		ArrayList<Integer> allocatedQuantityResultList = new ArrayList<>();
		allocatedQuantityResultList.add(50);
		allocatedQuantityResultList.add(50);
		response.body("allocationDetails.allocatedQuantity", response -> equalTo(allocatedQuantityResultList));

	}

	@Test
	public void checkResponseTimeTest() {
		response.time(lessThan(2L), TimeUnit.SECONDS);
	}
}