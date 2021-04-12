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
@Table(name="process_request")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProcessRequest {
	@Id
	private String userId;
	private String name;
	private String contactNumber;
	private String creditCardNumber;
	private String componentType;
	private String componentName;
	private long quantity;
	private String details;
	private String isPriorityRequest;
}
