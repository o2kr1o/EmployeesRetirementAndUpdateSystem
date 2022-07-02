package com.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Areas;
import com.example.entity.Benefits;
import com.example.entity.Departments;
import com.example.entity.Employees;
import com.example.model.InformationsParam;
import com.example.repository.AreasRepository;
import com.example.repository.BenefitsRepository;
import com.example.repository.DepartmentsRepository;
import com.example.repository.EmployeesRepository;

@Service
public class DepartmentsService {
	@Autowired
	private EmployeesRepository employeesRepository;

	@Autowired
	private DepartmentsRepository departmentsRepository;

	@Autowired
	private AreasRepository areasRepository;

	@Autowired
	private BenefitsRepository benefitsRepository;

	public List<InformationsParam> info(InformationsParam iParam, Long id) {
		List<InformationsParam> ipList = new ArrayList<>();
		List<Employees> empList = employeesRepository.findAll();

		for(Employees emp :empList) {
			if(emp.getDept_id() == id) {
				if("在籍".equals(iParam.getStatus()) && "退職".equals(emp.getStatus())) {
					continue;
				} else if("退職".equals(iParam.getStatus()) && "在籍".equals(emp.getStatus())) {
					continue;
				}

				InformationsParam infoParam = new InformationsParam();

				infoParam.setId(emp.getId());
				infoParam.setDept_id(emp.getDept_id());
				infoParam.setName(emp.getName());
				infoParam.setStatus(emp.getStatus());
				infoParam.setUpdate_date(emp.getUpdate_date());

				List<Areas> areasList = areasRepository.findAll();

				for(Areas areas : areasList) {
					if(areas.getArea_id() == emp.getArea_id()) {
						infoParam.setTerritories(areas.getTerritories());
					}
				}

				List<Benefits> benefitsList = benefitsRepository.findAll();

				for(Benefits benefits : benefitsList) {
					if(benefits.getId() == emp.getId()) {
						infoParam.setClients(benefits.getClients());
						infoParam.setProfits(benefits.getProfits());
					}
				}
				ipList.add(infoParam);
			}
		}
		return ipList;
	}

	public Departments deptNo(Long id) {
		List<Departments> drList = departmentsRepository.findAll();
		Departments dept = new Departments();
		for(Departments dr : drList) {
			if(dr.getDept_id() == id) {
				dept.setDivisions(dr.getDivisions());
			}
		}
		return dept;
	}

	public Employees getEmpId(Long id) {
		return employeesRepository.getById(id);
	}

	public Long retire(Long id){
		Employees empInfo = employeesRepository.getById(id);
		employeesRepository.save(empInfo);

		Date utilDate = new Date();
		Long timeInMilliSeconds = utilDate.getTime();
        java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
        empInfo.setUpdate_date(sqlDate);

        return empInfo.getDept_id();
    }
}
