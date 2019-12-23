package com.epam.library;

import com.epam.library.model.service.ServiceException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

public class Main {

	private final static String SERVER_URI = "http://localhost:8284/library/";

	public static void main(String[] args) throws ServiceException {
		getAllBooks();
	}

	private static void getAllBooks() {
		RestTemplate restTemplate = new RestTemplate();

		List<LinkedHashMap> bookList = restTemplate.getForObject(SERVER_URI + "books", List.class);
		assert bookList != null;
		System.out.println(bookList.size());

	}
}
