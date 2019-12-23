package com.epam.library.entity;

import com.epam.library.entity.enumeration.ReadingPlace;
import com.epam.library.util.constant.entityconstant.BookConstant;
import com.epam.library.util.constant.entityconstant.OrderConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = OrderConstant.ORDER_TABLE)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = OrderConstant.ORDER_ID)
	@Min(1)
	private long orderId;

	@ManyToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "order_books", joinColumns = @JoinColumn(name = OrderConstant.ORDER_ID), inverseJoinColumns = @JoinColumn(name = BookConstant.BOOK_ID))
	@JsonIgnore
	private Set<Book> book = new HashSet<Book>();

	@ManyToOne
	@JoinColumn(name = OrderConstant.USER_ID)
	private User user;

	@Column(name = OrderConstant.ORDER_DATE)
	@Temporal(TemporalType.DATE)
	private Date orderDate;

	@Column(name = OrderConstant.RETURNING_DATE)
	@Temporal(TemporalType.DATE)
	private Date returningDate;

	@Column(name = OrderConstant.READING_PLACE)
	@Enumerated(EnumType.STRING)
	private ReadingPlace readingPlace;

	@Column(name = OrderConstant.BOOK_RETURNED)
	private boolean bookReturned;

	public Order(long orderId) {
		this.orderId = orderId;
	}

	public Order(User userId, Date orderDate, Date returningDate, ReadingPlace readingPlace) {
		setUser(userId);
		setOrderDate(orderDate);
		setReturningDate(returningDate);
		setReadingPlace(readingPlace);
	}

	public Order(Set<Book> bookId, User userId, Date orderDate, Date returningDate, ReadingPlace readingPlace) {
		setBook(bookId);
		setUser(userId);
		setOrderDate(orderDate);
		setReturningDate(returningDate);
		setReadingPlace(readingPlace);
	}

	public Order(long orderId, Set<Book> bookId, User userId, Date orderDate, Date returningDate,
			ReadingPlace readingPlace) {
		setOrderId(orderId);
		setBook(bookId);
		setUser(userId);
		setOrderDate(orderDate);
		setReturningDate(returningDate);
		setReadingPlace(readingPlace);
	}

	public Order(Set<Book> bookId, User userId, Date orderDate, Date returningDate, ReadingPlace readingPlace,
			boolean bookReturned) {
		setBook(bookId);
		setUser(userId);
		setOrderDate(orderDate);
		setReturningDate(returningDate);
		setReadingPlace(readingPlace);
		setBookReturned(bookReturned);
	}

	// For Testing
	public Order(long orderId, User userId, Date orderDate, Date returningDate, ReadingPlace readingPlace,
			boolean bookReturned) {
		setOrderId(orderId);
		setUser(userId);
		setOrderDate(orderDate);
		setReturningDate(returningDate);
		setReadingPlace(readingPlace);
		setBookReturned(bookReturned);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (this == o) {
			return true;
		}
		Order order = (Order) o;
		return bookReturned == order.bookReturned && orderId == order.orderId && Objects.equals(user, order.user)
				&& Objects.equals(orderDate, order.orderDate) && Objects.equals(returningDate, order.returningDate)
				&& readingPlace == order.readingPlace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((user != null) ? user.hashCode() : 0);
		result = prime * result + ((orderDate != null) ? orderDate.hashCode() : 0);
		result = prime * result + ((returningDate != null) ? returningDate.hashCode() : 0);
		result = prime * result + ((readingPlace != null) ? readingPlace.hashCode() : 0);
		result = prime * result + (bookReturned ? 1231 : 1237);
		return result;
	}

	@Override
	public String toString() {
		return "Order{" + "orderId=" + orderId + ", userId=" + user + ", orderDate=" + orderDate + ", returningDate="
				+ returningDate + ", readingPlace=" + readingPlace + ", Books=" + book + ", bookReturned="
				+ bookReturned + '}';
	}
}
