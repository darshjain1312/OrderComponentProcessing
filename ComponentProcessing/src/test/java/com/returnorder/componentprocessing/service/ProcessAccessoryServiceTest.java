package com.returnorder.componentprocessing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.returnorder.componentprocessing.client.PackagingAndDeliveryFeignClient;
import com.returnorder.componentprocessing.model.ProcessRequest;
import com.returnorder.componentprocessing.model.ProcessResponse;
import com.returnorder.componentprocessing.repository.ProcessRequestRepository;
import com.returnorder.componentprocessing.repository.ProcessResponseRepository;
import com.returnorder.componentprocessing.service.ProcessAccessoryService;
import com.returnorder.componentprocessing.service.ProcessService;

class ProcessAccessoryServiceTest {
	
	private static ProcessResponse firstEntry;
	private static ProcessResponse secondEntry;
	
	@Mock
	private ProcessResponseRepository processResponseRepository;
	
	@Mock
	private ProcessRequestRepository processRequestRepository;
	
	@Mock
	private PackagingAndDeliveryFeignClient packagingAndDeliveryFeignClient;
	
	
	private static ProcessService processService;
	
	Random random = new Random();
	
	@BeforeEach
	public void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		processService = new ProcessAccessoryService(processResponseRepository,processRequestRepository,packagingAndDeliveryFeignClient);
		loadProcessResponse();
	}
	
	private static void loadProcessResponse() {
		firstEntry = new ProcessResponse("REQ123ASID1201", 300, 1000, "9/04/2021", "UID01G", 1300);
		secondEntry = new ProcessResponse("REQ156ASID1301", 300, 2000, "9/04/2021", "UID04D", 2300);
	}
	
	@Test
	void testProcessService() {
		ProcessRequest processRequest = new ProcessRequest("UID05R", "Ritansh Bangre", "9912451245","123412341234", "Integral", "Integral", 5L, "XYZ", "false");
		ProcessResponse entryFromDB = new ProcessResponse("REQ1ASID1", 300, 1700, "9/04/2021", "UID02S", 2000);
		Mockito.when(processRequestRepository.save(Mockito.any())).thenReturn(processRequest);
		Mockito.when(processResponseRepository.save(Mockito.any())).thenReturn(entryFromDB);
		ProcessResponse process = processService.processService(processRequest);
		assertNotNull(process);
		assertEquals("UID05R", process.getUserId());
		assertEquals(300, process.getProcessingCharge());
	
	}

	@Test
	void testGetAllProcessResponse() {
		Mockito.when(processResponseRepository.findAll()).thenReturn(Arrays.asList(firstEntry,secondEntry));
		List<ProcessResponse> list = processService.getAllProcessResponse();
		assertNotNull(list);
		assertEquals(2, list.size());
	}

	@Test
	void testGetResponseById(){
		Mockito.when(processResponseRepository.findById(Mockito.anyString())).thenReturn(Optional.of(firstEntry));
		ProcessResponse processRes = processService.getResponseById("REQ123ASID1201");
		assertNotNull(processRes);
		assertEquals(processRes.getTotal(), 1300);
	}

	@Test
	void testCreateResponse() {
		ProcessResponse entryToCreate = new ProcessResponse("REQ145ASID1501", 300, 1700, "9/04/2021", "UID02S", 2000);
		ProcessResponse entryFromDB = new ProcessResponse("REQ145ASID1501", 300, 1700, "9/04/2021", "UID02S", 2000);
		Mockito.when(processResponseRepository.save(Mockito.any())).thenReturn(entryFromDB);
		ProcessResponse process = processService.createResponse(entryToCreate);
		assertNotNull(process);
		assertEquals("REQ145ASID1501", process.getRequestId());
		assertEquals(300, process.getProcessingCharge());
	}

	@Test
	void testUpdateResponse() {
		ProcessResponse entryToUpdate = new ProcessResponse("REQ145ASID1601", 300, 2000, "9/04/2021", "UID03A", 2300);
		ProcessResponse entryFromDB = new ProcessResponse("REQ145ASID1601", 300, 2000, "9/04/2021", "UID03A", 2300);
		Mockito.when(processResponseRepository.findById(Mockito.anyString())).thenReturn(Optional.of(entryFromDB));
		
		Mockito.when(processResponseRepository.save(Mockito.any())).thenReturn(entryFromDB);
		ProcessResponse updatedprocess = processService.updateResponse(entryToUpdate);
		assertNotNull(updatedprocess);
		assertEquals("REQ145ASID1601", updatedprocess.getRequestId());
		assertEquals(300, updatedprocess.getProcessingCharge());
	}

	@Test
	void testDeleteResponseById() {
		processService.deleteResponseById("REQ123ASID1201");
		Mockito.verify(processResponseRepository,Mockito.times(1)).deleteById("REQ123ASID1201");
	}
}
