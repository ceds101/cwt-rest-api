package com.cwt.service;

import java.util.List;

import com.cwt.entities.Customer;
import com.cwt.entities.Order;
import com.cwt.payload.CustomerPayload;

public interface CustomerService {

	public Customer findById(Integer id);

	public Customer findByEmail(String email);

	public List<Customer> findAll();

	public Customer create(Customer customer);

	public void delete(Integer id);

	public Customer update(Integer id, Customer newCustomer);

	public Customer patch(Integer id, CustomerPayload newCustomer);

	public Customer addOrder(Integer custId, Order order);

}
