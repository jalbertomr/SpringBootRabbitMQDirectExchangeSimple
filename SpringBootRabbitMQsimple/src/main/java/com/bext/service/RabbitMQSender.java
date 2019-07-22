package com.bext.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bext.model.Employee;

@Service
public class RabbitMQSender {
    Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${bext.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${bext.rabbitmq.routingkey}")
	private String routingkey;
	
	public void sendViaExchage() {
		String mensaje = "simple mensaje enviado por sendViaExchange";
	    rabbitTemplate.convertAndSend( exchange, routingkey, mensaje);	
	}
	
	public void sendEmployeeViaExchange(Employee empleado) {
		logger.info("-> sendEmployeeViaExchange   : " + empleado);
		rabbitTemplate.convertAndSend( exchange, routingkey, empleado, new MiMessagePostProcessor());
		logger.info("   sendEmployeeViaExchange ->: " + empleado);
	}
	
	
}
