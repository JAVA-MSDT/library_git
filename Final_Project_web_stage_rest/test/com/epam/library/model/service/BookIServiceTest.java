package com.epam.library.model.service;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.model.EntityHolder;
import com.epam.library.model.dao.BookDao;
import com.epam.library.model.dao.DaoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Transactional
public class BookIServiceTest {

    private static Book book;
    private static List<Book> books;
    private static List<Order> orders;

    @Mock
    private BookDao bookDao;
    @InjectMocks
    private BookIService bookIService;

    @Before
    public void setUp() throws Exception {
        EntityHolder entityHolder = new EntityHolder();
        book = entityHolder.books().get(0);
        books = entityHolder.books();
        orders = entityHolder.orders();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getById() throws DaoException, ServiceException {

        when(bookDao.getById(book.getId()))
                .thenReturn(Optional.of(book));

        Optional<Book> optionalBookFromService = bookIService.getById(book.getId());

        optionalBookFromService.ifPresent(expectedBook -> assertEquals(expectedBook, book));

    }

    @Test
    public void getAll() throws DaoException, ServiceException {

        when(bookDao.getAll()).thenReturn(books);

        List<Book> bookList = bookIService.getAll();

        int expectedListSize = 4;
        int actualListSize = bookList.size();

        assertEquals(actualListSize, expectedListSize);

    }

    @Test
    public void getAllByPage() throws DaoException, ServiceException {

        when(bookDao.getAllByPage(0, 10)).thenReturn(books);

        List<Book> bookList = bookIService.getAllByPage(0, 10);

        int expectedListSize = 4;
        int actualListSize = bookList.size();

        assertEquals(actualListSize, expectedListSize);
    }

    @Test
    public void save() throws DaoException, ServiceException {

        doNothing().when(bookDao).save(any(Book.class));
        bookIService.save(book);

        verify(bookDao).save(book);

    }

    @Test
    public void removeById() throws DaoException, ServiceException {

        bookIService.removeById(book.getId());

        verify(bookDao).removeById(book.getId());
    }

    @Test
    public void update() throws DaoException, ServiceException {
        doNothing().when(bookDao).update(any(Book.class));

        bookIService.update(book);
        verify(bookDao).update(book);
    }

    @Test
    public void updateQuantity() throws DaoException, ServiceException {

        doNothing().when(bookDao).updateQuantity(isA(Long.class), isA(Integer.class));
        bookIService.updateQuantity(book.getId(), 400);

        verify(bookDao, times(1)).updateQuantity(book.getId(), 400);
    }

    @Test
    public void findByName() throws DaoException, ServiceException {
        List<Book> bookList = new ArrayList<>();
        Book book1 = new Book(1, "White Squall", 40, 1);
        bookList.add(book1);

        when(bookDao.findByName(book.getName())).thenReturn(bookList);

        int actualListSize = 1;
        int expectedListSize = bookIService.findByName(book.getName()).size();

        assertEquals(expectedListSize, actualListSize);
    }

    @Test
    public void getOrderFromBook() throws DaoException {
        when(bookDao.getOrderFromBook(4)).thenReturn(orders);

        int expectedListSize = 14;
        int actualListSize = bookIService.getOrderFromBook(4).size();

        assertEquals(expectedListSize, actualListSize);
    }
}