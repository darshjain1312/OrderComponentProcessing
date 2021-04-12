package com.returnorder.componentprocessing.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.returnorder.componentprocessing.exception.ProcessResponseNotFoundException;

class ProcessResponseNotFoundExceptionTest {

	@Test
	void testProcessResponseNotFoundException() {
		ProcessResponseNotFoundException prnfe = new ProcessResponseNotFoundException("User Not Found");
		assertEquals("User Not Found", prnfe.getMessage());
	}

}
