package com.epam.library.model.dto.orderservice;

import com.epam.library.entity.enumeration.ReadingPlace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AdministrationOrderDisplay {

    private long id;
    private String bookName;
    private String userName;
    private String userEmail;
    private Date orderDate;
    private Date returningDate;
    private ReadingPlace readingPlace;
    private boolean bookReturned;


    private AdministrationOrderDisplay(AdministrationOrderDisplayBuilder builder) {
        this.id = builder.id;
        this.bookName = builder.bookName;
        this.userName = builder.userName;
        this.userEmail = builder.userEmail;
        this.orderDate = builder.orderDate;
        this.returningDate = builder.returningDate;
        this.readingPlace = builder.readingPlace;
        this.bookReturned = builder.bookReturned;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this == o) {
            return true;
        }
        AdministrationOrderDisplay orderDisplay = (AdministrationOrderDisplay) o;
        return id == orderDisplay.id &&
                bookReturned == orderDisplay.bookReturned &&
                Objects.equals(bookName, orderDisplay.bookName) &&
                Objects.equals(userName, orderDisplay.userName) &&
                Objects.equals(userEmail, orderDisplay.userEmail) &&
                Objects.equals(orderDate, orderDisplay.orderDate) &&
                Objects.equals(returningDate, orderDisplay.returningDate) &&
                readingPlace == orderDisplay.readingPlace;
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
        return "AdministrationOrderDisplay{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", orderDate=" + orderDate +
                ", returningDate=" + returningDate +
                ", readingPlace=" + readingPlace +
                ", bookReturned=" + bookReturned +
                '}';
    }

    // Static Class Builder
    public static class AdministrationOrderDisplayBuilder {

        private long id;
        private String bookName;
        private String userName;
        private String userEmail;
        private Date orderDate;
        private Date returningDate;
        private ReadingPlace readingPlace;
        private boolean bookReturned;

        public AdministrationOrderDisplayBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public AdministrationOrderDisplayBuilder setBookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public AdministrationOrderDisplayBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public AdministrationOrderDisplayBuilder setUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public AdministrationOrderDisplayBuilder setOrderDate(Date orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public AdministrationOrderDisplayBuilder setReturningDate(Date returningDate) {
            this.returningDate = returningDate;
            return this;
        }

        public AdministrationOrderDisplayBuilder setReadingPlace(ReadingPlace readingPlace) {
            this.readingPlace = readingPlace;
            return this;
        }

        public AdministrationOrderDisplayBuilder setBookReturned(boolean bookReturned) {
            this.bookReturned = bookReturned;
            return this;
        }

        public AdministrationOrderDisplay build() {
            return new AdministrationOrderDisplay(this);
        }

    } // End of Static Class Builder!
}
