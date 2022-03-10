package com.cwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwt.entities.Order;
import com.cwt.persistence.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Override
	public Order findById(Integer id) {
		return orderRepository.findById(id).get();
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public Order create(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public void delete(Integer id) {
		orderRepository.deleteById(id);
	}

	@Override
	public Order update(Integer id, Order newOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order patch(Integer id, Order newOrder) {
		// TODO Auto-generated method stub
		return null;
	}

}
