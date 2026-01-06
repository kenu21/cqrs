package com.keniu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class QueryController {
    private final OrderViewRepository orderViewRepository;

    public QueryController(OrderViewRepository orderViewRepository) {
        this.orderViewRepository = orderViewRepository;
    }

    @GetMapping
    public List<OrderView> getOrders() {
        return orderViewRepository.findAll();
    }
}
