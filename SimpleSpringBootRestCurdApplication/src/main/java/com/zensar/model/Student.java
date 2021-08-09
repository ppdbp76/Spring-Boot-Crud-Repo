package com.zensar.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.poi.ss.usermodel.Cell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
	@Id
	@GeneratedValue
	@NotNull()
	private long stdId;
	@NotEmpty
	@Size(min = 2, max = 6, message = "name must be greaterthan two charactors")
	private String stdName;
	@NotEmpty
	private String stdCource;
	private double stdFee;

}
