package com.bext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bext.model.Employee;
import com.bext.service.RabbitMQSender;

@RestController
@RequestMapping(value = "/bext-rabbitmq/")
public class RabbitMQWebController {
	
	@Autowired
	RabbitMQSender rabbitMQSender;
	
	@GetMapping("/simpleSend")
	public String simpleSend() {
		rabbitMQSender.sendViaExchage();
		return "Mensaje simple enviado por RabbitMQ Exitoso!";
	}
	
	@GetMapping("/producer")
	public String producer( @RequestParam("empName") String empName, @RequestParam("empId") String empId) {
		
		Employee emp = new Employee();
		emp.setEmpName(empName);
		emp.setEmpId(empId);
		
		rabbitMQSender.sendEmployeeViaExchange(emp);
		   
		return "Mensaje Empleado enviado por RabbitMQ Exitoso!";
	}

}
