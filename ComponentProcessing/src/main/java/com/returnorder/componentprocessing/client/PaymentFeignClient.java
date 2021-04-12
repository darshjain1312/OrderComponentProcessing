package com.returnorder.componentprocessing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="payment-service",url="http://orderpay-env.eba-k38wafyn.us-east-2.elasticbeanstalk.com/processPayment")
public interface PaymentFeignClient {

	@GetMapping("/{creditCard}/{creditLimit}/{charge}")
	long processPayment(@PathVariable("creditCard") String creditCardNumber,
			@PathVariable("creditLimit") long creditLimit, @PathVariable("charge") long processingCharge);
	
}
