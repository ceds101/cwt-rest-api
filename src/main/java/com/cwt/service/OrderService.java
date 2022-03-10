package com.cwt.service;

import java.util.List;

import com.cwt.entities.Order;

public interface OrderService {

	public Order findById(Integer id);

	public List<Order> findAll();

	public Order create(Order order);

	public void delete(Integer id);

	public Order update(Integer id, Order newOrder);

	public Order patch(Integer id, Order newOrder);

}
