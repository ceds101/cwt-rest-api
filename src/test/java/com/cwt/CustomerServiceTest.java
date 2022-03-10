package com.cwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cwt.entities.Customer;
import com.cwt.exceptions.RecordNotFoundException;
import com.cwt.persistence.CustomerRepository;
import com.cwt.service.CustomerService;
import com.cwt.service.CustomerServiceImpl;

@SpringBootTest
public class CustomerServiceTest {

	@Mock
	CustomerRepository customerRepository;

	@InjectMocks
	CustomerService customerService = new CustomerServiceImpl();

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
	@DisplayName("Find by ID")
	class FindById {
		@Test
		@DisplayName("Find by ID Exsiting Customer")
		public void testFindById_ExistingCustomer() {

			when(customerRepository.findById(1)).thenReturn(Optional.of(mockCustomer1));

			Customer actualCustomer = customerService.findById(1);

			assertEquals("test1@email.com", actualCustomer.getEmail());
			assertEquals("Test1", actualCustomer.getFirstName());
			assertEquals("Mockito1", actualCustomer.getLastName());
			assertEquals("Location Test1", actualCustomer.getLocation());

		}

		@Test
		@DisplayName("Find by ID Non-Exsiting Customer")
		public void testFindById_NonExistingCustomer() {

			when(customerRepository.findById(1)).thenReturn(Optional.empty());

			RecordNotFoundException exception = assertThrows(RecordNotFoundException.class,
					() -> customerService.findById(1));

			assertEquals("Customer 1 Not Found", exception.getMessage());
		}

	}

	@Nested
	@DisplayName("Find by Email")
	class FindByEmail {
		@Test
		@DisplayName("Find by Email Existing Customer")
		public void testFindByEmail_ExistingCustomer() {

			when(customerRepository.findByEmail("test@email.com")).thenReturn(mockCustomer1);

			Customer actualCustomer = customerService.findByEmail("test@email.com");

			assertEquals("test1@email.com", actualCustomer.getEmail());
			assertEquals("Test1", actualCustomer.getFirstName());
			assertEquals("Mockito1", actualCustomer.getLastName());
			assertEquals("Location Test1", actualCustomer.getLocation());

		}

		@Test
		@DisplayName("Find by Email Non-Existing Customer")
		public void testFindByEmail_NonExistingCustomer() {
			RecordNotFoundException exception = assertThrows(RecordNotFoundException.class,
					() -> customerService.findByEmail("test_nonexisting@email.com"));

			assertEquals("Customer with email test_nonexisting@email.com Not Found", exception.getMessage());

		}
	}

	@Nested
	@DisplayName("Find All")
	class FindAll {
		@Test
		@DisplayName("Find All has Customers")
		public void testFindAll_ExistingCustomers() {

			when(customerRepository.findAll()).thenReturn(new ArrayList<Customer>(mockCustomers.values()));

			List<Customer> actualCustomers = customerService.findAll();

			assertEquals(2, actualCustomers.size());

		}

		@Test
		@DisplayName("Find All no Customers")
		public void testFindAll_NonExistingCustomers() {

			when(customerRepository.findAll()).thenReturn(Collections.emptyList());

			List<Customer> actualCustomers = customerService.findAll();

			assertEquals(0, actualCustomers.size());

		}
	}

	@Nested
	@DisplayName("Create")
	class Create {
		@Test
		@DisplayName("Create customer")
		public void testCreate() {

			Customer inputCustomer = new Customer();
			inputCustomer.setId(1);
			inputCustomer.setEmail("test1@email.com");
			inputCustomer.setFirstName("Test1");
			inputCustomer.setLastName("Mockito1");
			inputCustomer.setLocation("Location Test1");

			when(customerRepository.save(inputCustomer)).thenReturn(mockCustomer1);

			Customer actualCustomer = customerService.create(inputCustomer);

			assertEquals("test1@email.com", actualCustomer.getEmail());
			assertEquals("Test1", actualCustomer.getFirstName());
			assertEquals("Mockito1", actualCustomer.getLastName());
			assertEquals("Location Test1", actualCustomer.getLocation());

		}
	}

	@Nested
	@DisplayName("Delete")
	class Delete {
		@Test
		@DisplayName("Delete customer")
		public void testDelete() {

			doNothing().when(customerRepository).deleteById(1);

			customerService.delete(1);

			verify(customerRepository, times(1)).deleteById(1);

		}
	}

	@Nested
	@DisplayName("Update")
	class Update {
		@Test
		@DisplayName("Update customer")
		public void testUpdate() {

			Customer newCustomer = new Customer();
			newCustomer.setEmail("test_updated@email.com");
			newCustomer.setFirstName("Test1Update");
			newCustomer.setLastName("Mockito1Update");
			newCustomer.setLocation("Location Test1Update");

			mockCustomer1.setEmail("test_updated@email.com");
			mockCustomer1.setFirstName("Test1Update");
			mockCustomer1.setLastName("Mockito1Update");
			mockCustomer1.setLocation("Location Test1Update");

			when(customerRepository.save(newCustomer)).thenReturn(mockCustomer1);

			Customer actualCustomer = customerService.update(1, newCustomer);

		}
	}

}
