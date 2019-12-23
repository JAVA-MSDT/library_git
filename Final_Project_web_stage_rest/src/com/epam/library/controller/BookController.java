package com.epam.library.controller;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.model.service.BookIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Slf4j
public class BookController {

	@Autowired
	private BookIService bookIService;

	@GetMapping(value = "/books", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Book>> getAllBooks() {
		if (!bookIService.getAll().isEmpty()) {
			return new ResponseEntity<>(bookIService.getAll(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	@GetMapping(value = "/book/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
		Optional<Book> optionalBook = bookIService.getById(id);

		return optionalBook.map(book -> new ResponseEntity<>(book, HttpStatus.FOUND))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

	@GetMapping(value = "/book/{id}/orders", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Order>> getOrdersFromBook(@PathVariable("id") long id) {

		Optional<Book> optionalBook = bookIService.getById(id);

		if (optionalBook.isPresent()) {
			return new ResponseEntity<>(bookIService.getOrderFromBook(id), HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(value = "/book")
	public ResponseEntity<?> addBook(@RequestBody Book book) {
		Optional<Book> optionalBook = bookIService.getById(book.getId());

		if (optionalBook.isPresent()) {
			bookIService.save(book);
			return new ResponseEntity(HttpStatus.CREATED);
		} else {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping(value = "/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable("id") long id) {

		Optional<Book> optionalBook = bookIService.getById(id);
		return optionalBook.map(book -> {
			bookIService.removeById(id);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElse(new ResponseEntity(HttpStatus.CONFLICT));

	}

	@PutMapping(value = "/book")
	public ResponseEntity<?> editBook(@RequestBody Book book) {
		Optional<Book> optionalBook = bookIService.getById(book.getId());

		if (optionalBook.isPresent()) {
			Book book1 = optionalBook.get();
			book1.setName(book.getName());
			book1.setQuantity(book.getQuantity());
			book1.setOrders(book.getOrders());

			log.info("Book Exists: " + book1);
			bookIService.update(book1);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		} else {
			log.info("Book Not Exists: " + book);
			bookIService.save(book);
			return new ResponseEntity(HttpStatus.CREATED);
		}
	}

/////////////////////////////// Book Functional ///////////////////////////////
	@GetMapping(value = "/books/sortName", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Book>> getBooksSortedByName() {
		if (!bookIService.sortBooksByName().isEmpty()) {
			return new ResponseEntity<>(bookIService.sortBooksByName(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	@GetMapping(value = "/books/sortQuantity", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Book>> getBooksSortedByQuantity() {
		if (!bookIService.sortBookByQuantity().isEmpty()) {
			return new ResponseEntity<>(bookIService.sortBookByQuantity(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

}
