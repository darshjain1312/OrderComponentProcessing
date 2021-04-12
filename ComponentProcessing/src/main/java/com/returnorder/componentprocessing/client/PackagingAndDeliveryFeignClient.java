package com.returnorder.componentprocessing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="packaging-delivery-service",url="http://orderpackaginganddelivery-env.eba-jwqfv6x4.us-east-2.elasticbeanstalk.com/getPackagingAndDeliveryCharge")
public interface PackagingAndDeliveryFeignClient {
	
	@GetMapping("/{componentType}/{count}")
	long processDetail(@PathVariable("componentType")String componentType,@PathVariable("count")long count);
}
