package com.returnorder.componentprocessing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.returnorder.componentprocessing.client.PaymentFeignClient;
import com.returnorder.componentprocessing.model.ProcessRequest;
import com.returnorder.componentprocessing.model.ProcessResponse;
import com.returnorder.componentprocessing.service.ProcessAccessoryService;
import com.returnorder.componentprocessing.service.ProcessIntegralService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
//@RequestMapping("/component-processing")
public class ComponentProcessingController {

	private Logger log = LoggerFactory.getLogger(ComponentProcessingController.class);

	@Autowired
	private PaymentFeignClient paymentFeignClient;

	@Autowired
	private ProcessAccessoryService processAccessoryService;

	@Autowired
	private ProcessIntegralService processIntegralService;

	public ComponentProcessingController(final ProcessAccessoryService processAccessoryService,
			final PaymentFeignClient paymentFeignClient) {
		this.processAccessoryService = processAccessoryService;
		this.paymentFeignClient = paymentFeignClient;
	}

	public ComponentProcessingController(final ProcessIntegralService processIntegralService,
			final PaymentFeignClient paymentFeignClient) {
		this.processIntegralService = processIntegralService;
		this.paymentFeignClient = paymentFeignClient;
	}

	public ComponentProcessingController() {
	}

	@PostMapping("/processDetail") // To be changed....to GetMapping
	@ApiOperation(value = "Process details according to the component type.", notes = "It gives the response based on the component type.")
	public ProcessResponse processDetail(@ApiParam(value = "User Token", required = true) @RequestHeader("token") String token,
			@ApiParam(value = "ProcessRequest Model", required = true) @RequestBody ProcessRequest processRequest) {
		if (token == null)
			return null;
		log.info("ComponentProcessController :: processDetail");

		String componentType = processRequest.getComponentType();
		ProcessResponse processResponse = null;
		if (componentType.equalsIgnoreCase("Integral")) {
			processResponse = processIntegralService.processService(processRequest);
		} else if (componentType.equalsIgnoreCase("Accessory")) {
			processResponse = processAccessoryService.processService(processRequest);
		}

		return processResponse;

	}

	@PostMapping("/completeProcessing/{requestId}/{creditCard}/{limit}/{charge}")
	@ApiOperation(value = "Completes the Processing gives the result as String", notes = "Based on inputs and calculation it gives output as 'success', 'error' & 'failure'")
	public String completeProcessing(@RequestHeader("token") String token, @PathVariable("requestId") String requestId,
			@ApiParam(value = "Credit Card Number", required = true) @PathVariable("creditCard") String creditCardNumber,
			@ApiParam(value = "Credit Card Limit", required = true) @PathVariable("limit") long creditLimit,
			@ApiParam(value = "Total Processing Charge", required = true) @PathVariable("charge") long processingCharge) {
		if (token == null)
			return null;
		log.info("ComponentProcessController :: completeProcessing");
		log.info(
				"Path Variables :: " + requestId + " " + creditCardNumber + " " + creditLimit + " " + processingCharge);
		try {
			long currentBalance = paymentFeignClient.processPayment(creditCardNumber, creditLimit, processingCharge);
			log.debug("currentBalance :: {}", currentBalance);
			if (currentBalance != -1L) {
				if (currentBalance > 0) {
					return "success";
				} else {
					return "failure";
				}
			} else {
				return "error";
			}

		} catch (Exception e) {
			return "error";
		}

	}

}
