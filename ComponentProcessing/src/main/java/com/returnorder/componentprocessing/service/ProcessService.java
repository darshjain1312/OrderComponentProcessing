package com.returnorder.componentprocessing.service;

import java.util.List;

import com.returnorder.componentprocessing.model.ProcessRequest;
import com.returnorder.componentprocessing.model.ProcessResponse;

public interface ProcessService {
	public ProcessResponse processService(ProcessRequest processRequest);
	public List<ProcessResponse> getAllProcessResponse();
	public ProcessResponse getResponseById(String requestId);
	public ProcessResponse createResponse(ProcessResponse processResponse);
	public ProcessResponse updateResponse(ProcessResponse processResponse);
	public void deleteResponseById(String requestId);
}
