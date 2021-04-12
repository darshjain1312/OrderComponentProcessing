package com.returnorder.componentprocessing.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="process_response")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResponse {
	@Id
	private String requestId;
	private long processingCharge;
	private long packagingAndDeliveryCharge;
	private String dateOfDelivery;
	private String userId;
	private long total;
}
