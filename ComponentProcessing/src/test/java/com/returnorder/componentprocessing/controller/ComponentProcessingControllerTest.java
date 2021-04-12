package com.returnorder.componentprocessing.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.returnorder.componentprocessing.client.PaymentFeignClient;
import com.returnorder.componentprocessing.controller.ComponentProcessingController;
import com.returnorder.componentprocessing.model.ProcessRequest;
import com.returnorder.componentprocessing.model.ProcessResponse;
import com.returnorder.componentprocessing.service.ProcessAccessoryService;
import com.returnorder.componentprocessing.service.ProcessIntegralService;

class ComponentProcessingControllerTest {

	private static ProcessResponse firstEntry;
	private static ProcessResponse secondEntry;
	
	private ComponentProcessingController componentProcessingController,componentProcessingController1;
	
	@Mock
	private ProcessAccessoryService processService;
	
	@Mock
	private ProcessIntegralService processIntegralService;
	
	@Mock
	private PaymentFeignClient paymentFeignClient;
	
	private MockMvc mockMvc;
	private MockMvc mockMvc1;
	
	@BeforeEach
	void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		componentProcessingController = new ComponentProcessingController(processService,paymentFeignClient);
		mockMvc = MockMvcBuilders.standaloneSetup(componentProcessingController).build();

		componentProcessingController1 = new ComponentProcessingController(processIntegralService,paymentFeignClient);
		mockMvc1 = MockMvcBuilders.standaloneSetup(componentProcessingController1).build();
		loadProcessResponse();
	}
	
	
	private static void loadProcessResponse() {
		firstEntry = new ProcessResponse("REQ123ASID1201", 300, 1000, "9/04/2021", "UID01G", 1300);
		secondEntry = new ProcessResponse("REQ156ASID1301", 300, 2000, "9/04/2021", "UID04D", 2300);
	}
	
	@Test
	void testProcessDetail() throws Exception {
		ProcessRequest processRequest = new ProcessRequest("UID01G", "Gaurav", "9912451245","123412341234", "Accessory", "Accessory", 5L, "XYZ", "false");
		Mockito.when(processService.processService(Mockito.any())).thenReturn(firstEntry);
		mockMvc.perform(MockMvcRequestBuilders.post("/processDetail").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString((Object)processRequest)).header("token", "dasdasdadasd"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.userId",Matchers.equalTo("UID01G")));
	}

	@Test
	void testProcessDetailIntegral() throws Exception {
		ProcessRequest processRequest = new ProcessRequest("UID01G", "Gaurav", "9912451245","123412341234", "Integral", "Integral", 5L, "XYZ", "false");
		Mockito.when(processIntegralService.processService(Mockito.any())).thenReturn(firstEntry);
		mockMvc1.perform(MockMvcRequestBuilders.post("/processDetail").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString((Object)processRequest)).header("token", "dasdasdadasd"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.userId",Matchers.equalTo("UID01G")));
	}
	
	private static String jsonString(Object object) {
		String jsonString = null;
		if(object!=null)
			try {
				jsonString=	new ObjectMapper().writeValueAsString(object);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		return jsonString;
	}
	
	@Test
	void testCompleteProcessing() throws Exception {
		//return -1// return 0 and any return -2
		mockMvc.perform(MockMvcRequestBuilders.post("/completeProcessing/REQ123ASID1201/123412341234/"+70000L+"/"+300L+"").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "dasdasdadasd"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testCompleteProcessingBalanceGreaterThanZero() throws Exception{
		Mockito.when(paymentFeignClient.processPayment(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(10L);
		mockMvc.perform(MockMvcRequestBuilders.post("/completeProcessing/REQ123ASID1201/123412341234/"+70000L+"/"+300L+"").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "dasdasdadasd"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	void testCompleteProcessingBalanceEqualsToMinusOne() throws Exception{
		Mockito.when(paymentFeignClient.processPayment(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(-1L);
		mockMvc.perform(MockMvcRequestBuilders.post("/completeProcessing/REQ123ASID1201/123412341234/"+70000L+"/"+300L+"").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).header("token", "dasdasdadasd"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
	}

}
