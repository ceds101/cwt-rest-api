package com.cwt.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.entities.Order;
import com.cwt.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/{id}")
	public Order get(@PathVariable Integer id) {
		Order order = orderService.findById(id);
		return order;
	}

	@GetMapping("/all")
	public List<Order> getAll() {
		return orderService.findAll();
	}

	@PostMapping("/create")
	public ResponseEntity<Order> create(@Valid @RequestBody Order order) {
		Order newOrder = orderService.create(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
	}

}
