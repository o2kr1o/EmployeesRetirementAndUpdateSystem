package com.example.model;

import java.sql.Date;

import lombok.Data;

@Data
public class EmployeesParam {
	private Long id;
	private Long dept_id;
	private Long area_id;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String gender = "男";
	private String status = "在籍";
	private Date start_date;
	private Date update_date;
}
