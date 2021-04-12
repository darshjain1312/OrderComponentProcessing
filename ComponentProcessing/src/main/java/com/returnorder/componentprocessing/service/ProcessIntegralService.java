package com.returnorder.componentprocessing.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.returnorder.componentprocessing.client.PackagingAndDeliveryFeignClient;
import com.returnorder.componentprocessing.exception.ProcessResponseNotFoundException;
import com.returnorder.componentprocessing.model.ProcessRequest;
import com.returnorder.componentprocessing.model.ProcessResponse;
import com.returnorder.componentprocessing.repository.ProcessRequestRepository;
import com.returnorder.componentprocessing.repository.ProcessResponseRepository;

@Service
public class ProcessIntegralService implements ProcessService {

	private Logger log = LoggerFactory.getLogger(ProcessIntegralService.class);

	@Autowired
	private PackagingAndDeliveryFeignClient packagingAndDeliveryFeignClient;

	@Autowired
	private ProcessRequestRepository processRequestRepository;

	@Autowired
	private ProcessResponseRepository processResponseRepository;

	public ProcessIntegralService(final ProcessResponseRepository processResponseRepository,final ProcessRequestRepository processRequestRepository,final PackagingAndDeliveryFeignClient packagingAndDeliveryFeignClient) {
		this.processResponseRepository = processResponseRepository;
		this.processRequestRepository = processRequestRepository;
		this.packagingAndDeliveryFeignClient = packagingAndDeliveryFeignClient;
}
	
	@Override
	public ProcessResponse processService(ProcessRequest processRequest) {

		log.info("ProcessIntegralService :: processService");

		long processingDays = 5;
		long processingCharge = 500;

		processRequestRepository.save(processRequest);

		DateFormat format = new SimpleDateFormat("dd/MM//yyyy");
		Calendar calendar = Calendar.getInstance();
		String isPriorityRequest = processRequest.getIsPriorityRequest();

		if (isPriorityRequest != null) {
			processingDays = 2;
			processingCharge += 200;
		}

		calendar.add(Calendar.DATE, (int) processingDays);
		String dateOfDelivery = format.format(calendar.getTime());

		String componentType = processRequest.getComponentType();
		long count = processRequest.getQuantity();

		log.info("Calling PackagingAndDelivery microservice");

		long packagingAndDeliveryCharge = packagingAndDeliveryFeignClient.processDetail(componentType, count);
		log.debug("packageAndDeliveryCharge : {}", packagingAndDeliveryCharge);
		Random random = new Random();
		String requestId = "REQ" + random.nextInt(20) + "INID" + random.nextInt(1000);

		ProcessResponse response = new ProcessResponse();
		response.setUserId(processRequest.getUserId());
		response.setProcessingCharge(processingCharge);
		response.setDateOfDelivery(dateOfDelivery);
		response.setPackagingAndDeliveryCharge(packagingAndDeliveryCharge);
		response.setRequestId(requestId);
		response.setTotal(processingCharge + packagingAndDeliveryCharge);
		processResponseRepository.save(response);

		return response;
	}

	@Override
	public List<ProcessResponse> getAllProcessResponse() {
		List<ProcessResponse> list = processResponseRepository.findAll();
		return list;
	}

	@Override
	public ProcessResponse getResponseById(String requestId) {
		ProcessResponse response = null;		
		if(requestId != null) {
			Optional<ProcessResponse> optional = processResponseRepository.findById(requestId);
			try {
				if(!optional.isPresent()) {
					throw new ProcessResponseNotFoundException("Invalid RequestId");
				}else {
					response = optional.get();
				}
			}catch(ProcessResponseNotFoundException e) {
				e.getMessage();
			}
		}
		
		return response;
	}

	@Override
	public ProcessResponse createResponse(ProcessResponse processResponse) {
		if(processResponse!=null) {
			processResponseRepository.save(processResponse);
		}
		return processResponse;
	}

	@Override
	public ProcessResponse updateResponse(ProcessResponse processResponse) {
		if(processResponse!=null) {
			ProcessResponse response = getResponseById(processResponse.getRequestId());
			response = processResponse;
			
			return createResponse(response);
		}
		return null;
	}

	@Override
	public void deleteResponseById(String requestId) {
		if(requestId!=null) {
			processResponseRepository.deleteById(requestId);
		}

	}

}
