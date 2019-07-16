package com.bext.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bext.model.Employee;

@Service
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${bext.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${bext.rabbitmq.routingkey}")
	private String routingkey;
	
	public void send(Employee empleado) {
		rabbitTemplate.convertAndSend( exchange, routingkey, empleado);
		System.out.println("Send msg = " + empleado);
	}
}
