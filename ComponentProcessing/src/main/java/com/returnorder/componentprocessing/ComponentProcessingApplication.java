package com.returnorder.componentprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class ComponentProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComponentProcessingApplication.class, args);
	}
	
	@Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.paths(PathSelectors.ant("/"))
                .apis(RequestHandlerSelectors.basePackage("com.returnorder"))
                .build()
                .apiInfo(apiDetails());
    }
    
    @SuppressWarnings("deprecation")
    private ApiInfo apiDetails() {
        return new ApiInfo(
                "ComponentProcessing", "This Microservice is used for processing. It uses data from 'packaging and delivery' and 'payment' and process the details", "1.0", "", " ", "", ""
                );
    }

}
