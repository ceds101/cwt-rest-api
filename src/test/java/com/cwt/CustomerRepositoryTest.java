package com.cwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.cwt.entities.Customer;
import com.cwt.persistence.CustomerRepository;

@SpringBootTest
public class CustomerRepositoryTest {

	@Mock
	private CustomerRepository customerRepository;

	@Test
	@DisplayName("Find by Email Existing Customer")
	public void testFindByEmail_ExistingCustomer() {

		Customer mockCustomer = new Customer();
		mockCustomer.setId(1);
		mockCustomer.setEmail("test@email.com");
		mockCustomer.setFirstName("Test");
		mockCustomer.setLastName("Mockito");
		mockCustomer.setLocation("Location Test");

		Mockito.when(customerRepository.findByEmail("test@email.com")).thenReturn(mockCustomer);

		Customer actualCustomer = customerRepository.findByEmail("test@email.com");

		assertEquals("test@email.com", actualCustomer.getEmail());
		assertEquals("Test", actualCustomer.getFirstName());
		assertEquals("Mockito", actualCustomer.getLastName());
		assertEquals("Location Test", actualCustomer.getLocation());

		// Testttttt
	}

	@Test
	@DisplayName("Find by Email Non-Existing Customer")
	public void testFindByEmail_NonExistingCustomer() {
		Customer actualCustomer = customerRepository.findByEmail("test_nonexisting@email.com");

		assertNull(actualCustomer);
	}
}
