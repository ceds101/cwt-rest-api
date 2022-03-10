package com.cwt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cwt.controllers.CustomerController;
import com.cwt.entities.Customer;
import com.cwt.service.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerServiceImpl customerService;

	private ObjectMapper mapper = new ObjectMapper();

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
		public void testGetCustomerById_ExistingCustomer() throws JsonProcessingException, Exception {

			when(customerService.findById(1)).thenReturn(mockCustomer1);

			MvcResult result = mockMvc.perform(get("/customers/{custId}", 1).contentType("application/json")
					.content(mapper.writeValueAsString(mockCustomer1))).andExpect(status().isOk()).andReturn();

			System.out.println(result.getResponse().getContentAsString());

		}

		@Test
		@DisplayName("Get by Id Non-Existing Customer")
		public void testGetCustomerById_NonExistingCustomer() {

			System.out.println("test commit");

		}
	}

	@Nested
	@DisplayName("Get By Email")
	class GetByEmail {
		@Test
		@DisplayName("Get by Email Existing Customer")
		public void testGetCustomerByEmail_ExistingCustomer() {

			when(customerService.findByEmail("test1@email.com")).thenReturn(mockCustomer1);

		}

		@Test
		@DisplayName("Get by Email Non-Existing Customer")
		public void testGetCustomerByEmail_NonExistingCustomer() {

		}
	}

	@Nested
	@DisplayName("Get All")
	class GetAll {

		@Test
		@DisplayName("Get All With Customers")
		public void testGetCustomers_WithCustomers() {

			when(customerService.findAll()).thenReturn(new ArrayList<Customer>(mockCustomers.values()));
		}

		@Test
		@DisplayName("Get All No Customers")
		public void testGetCustomers_NoCustomers() {

		}

	}

	@Nested
	@DisplayName("Delete By Id")
	class DeleteById {

		@Test
		@DisplayName("Delete Existing Customer")
		public void testGetCustomers_WithCustomers() {

		}

		@Test
		@DisplayName("Delete Non-Existing Customer")
		public void testGetCustomers_NoCustomers() {

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
