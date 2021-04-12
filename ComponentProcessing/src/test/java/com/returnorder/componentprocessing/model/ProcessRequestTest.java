package com.returnorder.componentprocessing.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.returnorder.componentprocessing.model.ProcessRequest;

class ProcessRequestTest {

	private ProcessRequest processRequest = new ProcessRequest();

	@Test
	void testGetUserId() {
		processRequest.setUserId("UID04D");
		assertEquals("UID04D", processRequest.getUserId());
	}

	@Test
	void testGetName() {
		processRequest.setName("Darsh");
		assertEquals("Darsh", processRequest.getName());
	}

	@Test
	void testGetContactNumber() {
		processRequest.setContactNumber("9926345124");
		assertEquals("9926345124", processRequest.getContactNumber());
	}

	@Test
	void testGetCreditCardNumber() {
		processRequest.setCreditCardNumber("123478451245");
		assertEquals("123478451245", processRequest.getCreditCardNumber());
	}

	@Test
	void testGetComponentType() {
		processRequest.setComponentName("Integral");
		assertEquals("Integral", processRequest.getComponentName());
	}

	@Test
	void testGetComponentName() {
		processRequest.setComponentType("Integral");
		assertEquals("Integral", processRequest.getComponentType());
	}

	@Test
	void testGetQuantity() {
		processRequest.setQuantity(5L);
		assertEquals(5L, processRequest.getQuantity());
	}

	@Test
	void testGetDetails() {
		processRequest.setDetails("Brok");
		assertEquals("Brok", processRequest.getDetails());
	}

	@Test
	void testGetIsPriorityRequest() {
		processRequest.setIsPriorityRequest("true");
		assertEquals("true", processRequest.getIsPriorityRequest());
	}

	@Test
	void testSetUserId() {
		processRequest.setUserId("UID04D");
		assertEquals("UID04D", processRequest.getUserId());
	}

	@Test
	void testSetName() {
		processRequest.setName("Darsh");
		assertEquals("Darsh", processRequest.getName());
	}

	@Test
	void testSetContactNumber() {
		processRequest.setContactNumber("9926345124");
		assertEquals("9926345124", processRequest.getContactNumber());
	}

	@Test
	void testSetCreditCardNumber() {
		processRequest.setCreditCardNumber("123478451245");
		assertEquals("123478451245", processRequest.getCreditCardNumber());
	}

	@Test
	void testSetComponentType() {
		processRequest.setComponentType("Integral");
		assertEquals("Integral", processRequest.getComponentType());
	}

	@Test
	void testSetComponentName() {
		processRequest.setComponentName("Integral");
		assertEquals("Integral", processRequest.getComponentName());
	}

	@Test
	void testSetQuantity() {
		processRequest.setQuantity(5L);
		assertEquals(5L, processRequest.getQuantity());
	}

	@Test
	void testSetDetails() {
		processRequest.setDetails("Brok");
		assertEquals("Brok", processRequest.getDetails());
	}

	@Test
	void testSetIsPriorityRequest() {
		processRequest.setIsPriorityRequest("true");
		assertEquals("true", processRequest.getIsPriorityRequest());
	}

	@Test
	void testToString() {
		ProcessRequest process = new ProcessRequest();
		String processString = "ProcessRequest(userId=null, name=null, contactNumber=null, creditCardNumber=null, componentType=null, componentName=null, quantity=0, details=null, isPriorityRequest=null)";
		assertEquals(processString, process.toString());
	}

	@Test
	void testProcessRequest() {
		ProcessRequest process = new ProcessRequest();
		assertNotNull(process);
	}

	@Test
	void testProcessRequestStringStringStringStringStringStringLongStringString() {
		ProcessRequest process = new ProcessRequest("UID04D", "Darsh", "9926345124", "123478451245", "Integral", "Integral", 5L, "Brok", "true");
		assertEquals("UID04D", process.getUserId());
		assertEquals("Darsh", process.getName());
		assertEquals("9926345124", process.getContactNumber());
		assertEquals("123478451245", process.getCreditCardNumber());
		assertEquals("Integral", process.getComponentType());
		assertEquals("Integral", process.getComponentName());
		assertEquals(5L, process.getQuantity());
		assertEquals("Brok", process.getDetails());
		assertEquals("true", process.getIsPriorityRequest());
	}

}
