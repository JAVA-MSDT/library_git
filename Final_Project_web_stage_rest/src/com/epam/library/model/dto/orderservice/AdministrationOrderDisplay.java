package com.epam.library.model.dto.orderservice;

import com.epam.library.entity.enumeration.ReadingPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AdministrationOrderDisplay {

	@Min(1)
	private long id;
	@NotEmpty
	private String bookName;
	@NotEmpty
	private String userName;
	@NotEmpty
	private String userEmail;
	@NotEmpty
	private Date orderDate;
	@NotEmpty
	private Date returningDate;
	@NotEmpty
	private ReadingPlace readingPlace;
	@NotEmpty
	private boolean bookReturned;

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (this == o) {
			return true;
		}
		AdministrationOrderDisplay orderDisplay = (AdministrationOrderDisplay) o;
		return id == orderDisplay.id && bookReturned == orderDisplay.bookReturned
				&& Objects.equals(bookName, orderDisplay.bookName) && Objects.equals(userName, orderDisplay.userName)
				&& Objects.equals(userEmail, orderDisplay.userEmail)
				&& Objects.equals(orderDate, orderDisplay.orderDate)
				&& Objects.equals(returningDate, orderDisplay.returningDate)
				&& readingPlace == orderDisplay.readingPlace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((bookName != null) ? bookName.hashCode() : 0);
		result = prime * result + ((userName != null) ? userName.hashCode() : 0);
		result = prime * result + ((userEmail != null) ? userEmail.hashCode() : 0);
		result = prime * result + ((orderDate != null) ? orderDate.hashCode() : 0);
		result = prime * result + ((returningDate != null) ? returningDate.hashCode() : 0);
		result = prime * result + ((readingPlace != null) ? readingPlace.hashCode() : 0);
		result = prime * result + ((bookReturned) ? 1231 : 1237);
		return result;
	}

	@Override
	public String toString() {
		return "AdministrationOrderDisplay{" + "id=" + id + ", bookName='" + bookName + '\'' + ", userName='" + userName
				+ '\'' + ", userEmail='" + userEmail + '\'' + ", orderDate=" + orderDate + ", returningDate="
				+ returningDate + ", readingPlace=" + readingPlace + ", bookReturned=" + bookReturned + '}';
	}

}
