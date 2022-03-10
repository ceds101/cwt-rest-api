package com.cwt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cwt.entities.Customer;
import com.cwt.entities.Order;
import com.cwt.exceptions.RecordNotFoundException;
import com.cwt.payload.CustomerPayload;
import com.cwt.persistence.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer findById(Integer custId) {
		log.info("inside findCustomerById");
		Optional<Customer> customer = customerRepository.findById(custId);
		if (customer.isEmpty()) {
			throw new RecordNotFoundException("Customer " + custId + " Not Found");
		}

		return customer.get();
	}

	@Override
	public Customer findByEmail(String email) {
		Customer customer = customerRepository.findByEmail(email);
		if (customer == null) {
			throw new RecordNotFoundException("Customer with email " + email + " Not Found");
		}
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		log.info("inside findAllCustomers");
		return customerRepository.findAll();
	}

	@Override
	public Customer create(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void delete(Integer custId) {
		try {
			customerRepository.deleteById(custId);
		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException("Customer " + custId + " Not Found");
		}
	}

	@Override
	public Customer update(Integer custId, Customer newCustomer) {
		if (findById(custId) == null) {
			throw new RecordNotFoundException("Customer " + custId + " Not Found");
		}

		newCustomer.setId(custId);
		return customerRepository.save(newCustomer);
	}

	@Override
	public Customer patch(Integer custId, CustomerPayload newCustomer) {
		Customer customer = findById(custId);

		if (customer == null) {
			throw new RecordNotFoundException("Customer " + custId + " Not Found");
		}

		String newFirstName = newCustomer.getFirstName();
		String newLastName = newCustomer.getLastName();
		String newEmail = newCustomer.getEmail();
		String newLocation = newCustomer.getLocation();

		log.info(newCustomer.toString());

		if (newFirstName != null) {
			customer.setFirstName(newFirstName);
		}

		if (newLastName != null) {
			customer.setLastName(newLastName);
		}

		if (newEmail != null) {
			customer.setEmail(newEmail);
		}

		if (newLocation != null) {
			customer.setLocation(newLocation);
		}

		return customerRepository.save(customer);
	}

	@Override
	public Customer addOrder(Integer custId, Order order) {
		Customer customer = findById(custId);
		customer.addOrder(order);
		return customerRepository.save(customer);
	}

}
