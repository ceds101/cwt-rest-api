package com.cwt.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUSTOMER")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "first_name")
	@NotBlank(message = "First Name should not be null or blank.")
	@Size(min = 2, max = 20)
	private String firstName;

	@Column(name = "last_name")
	@NotBlank(message = "Last Name should not be null or blank.")
	@Size(min = 2, max = 20)
	private String lastName;

	@Column(name = "email", unique = true, nullable = false)
	@NotBlank(message = "Email should not be null or blank.")
	@Email(message = "Email is not valid")
	private String email;

	private String location;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Order> orders;

	public void addOrder(@Valid Order order) {
		if (orders == null) {
			orders = new HashSet<Order>();
		}
		orders.add(order);
	}

}
