package com.keniu;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class CommandController {

    private final OrderRepository repository;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public CommandController(OrderRepository repository, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody Order order) {
        order.setStatus("CREATED");
        repository.save(order);

        OrderEvent orderEvent = new OrderEvent(order.getId(), order.getDescription(), order.getStatus());
        kafkaTemplate.send("orders", orderEvent);

        return ResponseEntity.ok().build();
    }
}
