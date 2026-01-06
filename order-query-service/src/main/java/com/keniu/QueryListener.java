package com.keniu;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class QueryListener {

    private final OrderViewRepository orderViewRepository;

    public QueryListener(OrderViewRepository orderViewRepository) {
        this.orderViewRepository = orderViewRepository;
    }

    @KafkaListener(topics = "orders", groupId = "order-query")
    public void handleOrderEvent(OrderEvent orderEvent) {
        OrderView view = new OrderView();
        view.setId(orderEvent.id());
        view.setDescription(orderEvent.description());
        view.setStatus(orderEvent.status());
        orderViewRepository.save(view);
    }
}
