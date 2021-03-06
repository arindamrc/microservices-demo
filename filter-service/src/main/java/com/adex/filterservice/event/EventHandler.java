package com.adex.filterservice.event;

import javax.transaction.Transactional;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.adex.filterservice.repository.RequestStatisticsRepository;
import com.adex.filterservice.service.RequestStatisticsService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class receives the events and triggers the associated customer ids to be deleted.
 */
@Slf4j
@Component
class EventHandler {

    private final RequestStatisticsService rsService;

    @Autowired
    EventHandler(final RequestStatisticsService rsr) {
    	this.rsService = rsr;
    }

    @RabbitListener(queues = "${customer.deletion.queue}")
    void handleCustomerDeleted(final CustomerDeletionEvent event) {
        log.info("Customer Deletion Event received: {}", event.getCid());
        try {
            rsService.deleteCustomer(event.getCid());
        } catch (final Exception e) {
            log.error("Error when trying to process Customer Deletion Event", e);
            // Avoids the event to be re-queued and reprocessed.
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
