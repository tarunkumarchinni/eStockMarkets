package com.estockmarket.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Consumer {

	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "estock_markets_topic", groupId = "group_id")
    public void consume(String message) {
        logger.info(String.format("$$$$ => Consumed message: %s", message));
    }
}
