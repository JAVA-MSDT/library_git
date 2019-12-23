package com.epam.library.controller;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.model.service.OrderIService;
import com.epam.library.model.service.ServiceException;
import com.epam.library.model.service.UserIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

	@Autowired
	private UserIService userIService;

	@Autowired
	private OrderIService orderIService;

	@GetMapping(value = "/user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public User getUserById(@PathVariable("id") long id) throws ServiceException {
		Optional<User> userOptional = userIService.getById(id);

		return userOptional.orElse(null);
	}

	@GetMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<User> getAllUsers() throws ServiceException {
		return userIService.getAll();
	}

	@PostMapping(value = "/login", produces = { MediaType.APPLICATION_JSON_VALUE })
	public User getByLoginAndPassword(@RequestBody User user) throws ServiceException {

		Optional<User> optionalUser = userIService.findByLoginPassword(user.getLogin(), user.getPassword());
		return optionalUser.orElse(null);
	}

	@PostMapping(value = "/login/parameter", produces = { MediaType.APPLICATION_JSON_VALUE })
	public User getByLoginAndPasswordParameter(@RequestParam("login") String login,
			@RequestParam("password") String password) throws ServiceException {
		System.out.println("Inside Parameter method: " + login + " : " + password);
		Optional<User> optionalUser = userIService.findByLoginPassword(login, password);
		return optionalUser.orElse(null);
	}

	@GetMapping(value = "/user/{id}/orders", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Order> userOrders(@PathVariable("id") long id) throws ServiceException {
		Optional<User> userOptional = userIService.getById(id);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			return user.getOrders();
		} else {
			return Collections.emptyList();
		}
	}

	@GetMapping(value = "/user/orders/{id}/book", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Set<Book> books(@PathVariable("id") long id) throws ServiceException {
		return orderIService.booksFromOrder(id);
	}
}
