package com.epam.library.controller;

import com.epam.library.entity.Order;
import com.epam.library.model.service.BookIService;
import com.epam.library.model.service.OrderIService;
import com.epam.library.model.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

	private final Order emptyOrder = new Order(0);
	@Autowired
	private OrderIService orderIService;

	@Autowired
	private BookIService bookIService;

	@GetMapping(value = "/order/{id}")
	public Order getOrderById(@PathVariable("id") long id) throws ServiceException {
		Optional<Order> optionalOrder = orderIService.getById(id);
		return optionalOrder.orElse(emptyOrder);
	}

	@GetMapping(value = "/orders", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Order> getAllOrders() throws ServiceException {
		return orderIService.getAll();
	}

	@PostMapping(value = "/order", produces = { MediaType.APPLICATION_JSON_VALUE })
	public void saveOrder(@RequestBody Order order) throws ServiceException {
		orderIService.save(order);
	}

	@PutMapping(value = "/order", produces = { MediaType.APPLICATION_JSON_VALUE })
	public void editOrder(@RequestBody Order order) throws ServiceException {
		Optional<Order> optionalOrder = orderIService.getById(order.getOrderId());

		if (optionalOrder.isPresent()) {
			Order order1 = new Order();
			order1.setOrderId(order.getOrderId());
			order1.setUser(order.getUser());
			order1.setBook(order.getBook());
			order1.setOrderDate(order.getOrderDate());
			order1.setReturningDate(order.getReturningDate());
			order1.setReadingPlace(order.getReadingPlace());
			order1.setBookReturned(order.isBookReturned());

			orderIService.update(order1);

		} else {
			orderIService.save(order);
		}
	}

	@DeleteMapping(value = "/order/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public void removeOrder(@PathVariable("id") long id) throws ServiceException {
		orderIService.administrationOrderRemoval(id, bookIService);
	}

	@PostMapping(value = "/user/confirmOrder", produces = { MediaType.APPLICATION_JSON_VALUE })
	public void confirmUserOrder(@RequestBody Order order) throws ServiceException {
		orderIService.confirmUserOrder(order, bookIService);
	}

}
