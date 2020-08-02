/**
 * 
 */
package com.adex.customerservice.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for dispatching events to RabbitMQ.
 * 
 * @author arc
 *
 */
@Component
public class EventDispatcher {

	
	/**
	 * This handles communication with RabbitMQ. 
	 */
	private RabbitTemplate rabbitTemplate;

	/**
	 * The name of the customer deletion exchange.
	 */
	private String customerDeletionExchange;

	/**
	 * The customer deletion routing key.
	 */
	private String customerDeletionRoutingKey;

	@Autowired
	public EventDispatcher(
			final RabbitTemplate rabbitTemplate, 
			@Value("${customer.deletion.exchange}") final String customerDeletionExchange, 
			@Value("${customer.deletion.key}") final String customerDeletionRoutingKey) {
		this.rabbitTemplate = rabbitTemplate;
		this.customerDeletionExchange = customerDeletionExchange;
		this.customerDeletionRoutingKey = customerDeletionRoutingKey;
	}
	
	
	/**
	 * Dispatch the customer deletion event.
	 */
	public void sendCustomerDeletionEvent(final CustomerDeletionEvent event) {
		rabbitTemplate.convertAndSend(customerDeletionExchange, customerDeletionRoutingKey, event);
	}
}
