package com.example.model;

import java.sql.Date;

import lombok.Data;

@Data
public class EditingsParam {
	private Long id;
	private Long dept_id;
	private Long area_id;
	private String name;
	private Date update_date;
	private String territories;
	private Long clients;
	private Long profits;
}
