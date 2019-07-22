package com.bext.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig { // Sender

	@Value("${bext.rabbitmq.queueName}")
	String queueName;
	@Value("${bext.rabbitmq.exchange}")
	String exchange;
	@Value("${bext.rabbitmq.routingkey}")
	private String routingkey;
	
	@Bean
	Queue queue() {
		return new Queue(queueName, true);
	}
	
	/* DirectExchange */
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}
	
	/* TopicExchange */
/*	
	@Bean
    TopicExchange miTopicExchange(AmqpAdmin amqpAdmin){
        TopicExchange topicExchange = new TopicExchange("bext-exchange",true,true);
        amqpAdmin.declareExchange(topicExchange);
        return  topicExchange;
    }
*/	
	/* Bind x DirectExchange */
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}
	
	/* Bind x TopicExchange */
/*	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}
*/	
	@Bean
	public MessageConverter jsonMessageConverter() {
	   return new Jackson2JsonMessageConverter();	
	}
	
	@Bean
	public AmqpTemplate rabbitTemplate( ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
