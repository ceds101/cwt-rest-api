package com.cwt.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwt.entities.Customer;
import com.cwt.entities.Order;
import com.cwt.payload.CustomerPayload;
import com.cwt.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/{custId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable Integer custId) {
		Customer customer = customerService.findById(custId);
		return ResponseEntity.ok(customer);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
		Customer customer = customerService.findByEmail(email);
		return ResponseEntity.ok(customer);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Customer>> getCustomers() {
		List<Customer> customers = customerService.findAll();
		return ResponseEntity.ok(customers);
	}

	@PostMapping("/create")
	public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
		Customer newCustomer = customerService.create(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
	}

	@PostMapping("/{custId}/order")
	public ResponseEntity<Customer> addOrder(@PathVariable Integer custId, @Valid @RequestBody Order order) {
		Customer updatedCustomer = customerService.addOrder(custId, order);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedCustomer);
	}

	@DeleteMapping("/{custId}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer custId) {
		customerService.delete(custId);

		Map<String, Object> response = new HashMap<>();
		response.put("success", true);

		return ResponseEntity.ok(response);

	}

	@PutMapping("/{custId}")
	public ResponseEntity<Customer> update(@PathVariable Integer custId, @Valid @RequestBody Customer customer) {
		Customer updatedCustomer = customerService.update(custId, customer);
		return ResponseEntity.ok(updatedCustomer);
	}

	@PatchMapping("/{custId}")
	public ResponseEntity<Customer> patch(@PathVariable Integer custId, @Valid @RequestBody CustomerPayload customer) {
		Customer updatedCustomer = customerService.patch(custId, customer);
		return ResponseEntity.ok(updatedCustomer);
	}
}
