package com.cwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cwt.controllers.CustomerController;
import com.cwt.entities.Customer;
import com.cwt.exceptions.RecordNotFoundException;
import com.cwt.service.CustomerService;

@SpringBootTest
public class CustomerControllerTest {

	@Mock
	CustomerService customerService;

	@InjectMocks
	CustomerController customerController = new CustomerController();

	private Map<Integer, Customer> mockCustomers;

	private Customer mockCustomer1;
	private Customer mockCustomer2;

	@BeforeEach
	public void setUp() {
		mockCustomer1 = new Customer();
		mockCustomer1.setId(1);
		mockCustomer1.setEmail("test1@email.com");
		mockCustomer1.setFirstName("Test1");
		mockCustomer1.setLastName("Mockito1");
		mockCustomer1.setLocation("Location Test1");

		mockCustomer2 = new Customer();
		mockCustomer2.setId(2);
		mockCustomer2.setEmail("test2@email.com");
		mockCustomer2.setFirstName("Test2");
		mockCustomer2.setLastName("Mockito2");
		mockCustomer2.setLocation("Location Test2");

		mockCustomers = new HashMap<>();
		mockCustomers.put(mockCustomer1.getId(), mockCustomer1);
		mockCustomers.put(mockCustomer2.getId(), mockCustomer2);
	}

	@AfterEach
	public void tearDown() {
		mockCustomer1 = null;
		mockCustomer2 = null;
		mockCustomers = null;
	}

	@Nested
	@DisplayName("Get By Id")
	class GetById {
		@Test
		@DisplayName("Get by Id Existing Customer")
		public void testGetCustomerById_ExistingCustomer() {

			Mockito.when(customerService.findById(1)).thenReturn(mockCustomer1);

			ResponseEntity<Customer> actualResponse = customerController.getCustomer(1);

			Customer actualCustomer = actualResponse.getBody();

			assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
			assertEquals("test1@email.com", actualCustomer.getEmail());
			assertEquals("Test1", actualCustomer.getFirstName());
			assertEquals("Mockito1", actualCustomer.getLastName());
			assertEquals("Location Test1", actualCustomer.getLocation());

		}

		@Test
		@DisplayName("Get by Id Non-Existing Customer")
		public void testGetCustomerById_NonExistingCustomer() {

			RecordNotFoundException mockException = new RecordNotFoundException("Customer 2 Not Found");

			Mockito.when(customerService.findById(2)).thenThrow(mockException);

			RecordNotFoundException actualException = assertThrows(RecordNotFoundException.class,
					() -> customerController.getCustomer(2));

			assertEquals("Customer 2 Not Found", actualException.getMessage());

		}
	}

	@Nested
	@DisplayName("Get By Email")
	class GetByEmail {
		@Test
		@DisplayName("Get by Email Existing Customer")
		public void testGetCustomerByEmail_ExistingCustomer() {

			Mockito.when(customerService.findByEmail("test1@email.com")).thenReturn(mockCustomer1);

			ResponseEntity<Customer> actualResponse = customerController.getCustomerByEmail("test1@email.com");

			Customer actualCustomer = actualResponse.getBody();

			assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
			assertEquals("test1@email.com", actualCustomer.getEmail());
			assertEquals("Test1", actualCustomer.getFirstName());
			assertEquals("Mockito1", actualCustomer.getLastName());
			assertEquals("Location Test1", actualCustomer.getLocation());

		}

		@Test
		@DisplayName("Get by Email Non-Existing Customer")
		public void testGetCustomerByEmail_NonExistingCustomer() {

			RecordNotFoundException mockException = new RecordNotFoundException(
					"Customer with email test_nonexisting@email.com Not Found");

			Mockito.when(customerService.findByEmail("test_nonexisting@email.com")).thenThrow(mockException);

			RecordNotFoundException actualException = assertThrows(RecordNotFoundException.class,
					() -> customerController.getCustomerByEmail("test_nonexisting@email.com"));

			assertEquals("Customer with email test_nonexisting@email.com Not Found", actualException.getMessage());

		}
	}

	@Nested
	@DisplayName("Get All")
	class GetAll {

		@Test
		@DisplayName("Get All With Customers")
		public void testGetCustomers_WithCustomers() {

			Mockito.when(customerService.findAll()).thenReturn(new ArrayList<Customer>(mockCustomers.values()));

			ResponseEntity<List<Customer>> actualResponse = customerController.getCustomers();

			List<Customer> actualCustomers = actualResponse.getBody();

			assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
			assertEquals(2, actualCustomers.size());
		}

		@Test
		@DisplayName("Get All No Customers")
		public void testGetCustomers_NoCustomers() {

			Mockito.when(customerService.findAll()).thenReturn(Collections.emptyList());

			ResponseEntity<List<Customer>> actualResponse = customerController.getCustomers();

			List<Customer> actualCustomers = actualResponse.getBody();

			assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
			assertEquals(0, actualCustomers.size());

		}

	}

	@Nested
	@DisplayName("Delete By Id")
	class DeleteById {

		@Test
		@DisplayName("Delete Existing Customer")
		public void testGetCustomers_WithCustomers() {

			ResponseEntity<Map<String, Object>> actualResponse = customerController.delete(1);
			Map<String, Object> actualBody = actualResponse.getBody();

			assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
			assertTrue((Boolean) actualBody.get("success"));

			verify(customerService).delete(1);

		}

		@Test
		@DisplayName("Delete Non-Existing Customer")
		public void testGetCustomers_NoCustomers() {

			RecordNotFoundException mockException = new RecordNotFoundException("Customer 1 Not Found");

			Mockito.doThrow(mockException).when(customerService).delete(1);

			RecordNotFoundException actualException = assertThrows(RecordNotFoundException.class,
					() -> customerController.delete(1));

			assertEquals("Customer 1 Not Found", actualException.getMessage());

		}

	}

	@Nested
	@DisplayName("Create")
	class Create {

		@Test
		@DisplayName("Create Customer Success")
		public void testCreate() {

		}

	}

}
