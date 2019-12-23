package com.epam.library.entity;


import com.epam.library.util.constant.entityconstant.BookConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = BookConstant.BOOK_TABLE)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book {


    @Min(value = 1)
    @Column(name = BookConstant.BOOK_ID)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 4)
    @Column(name = BookConstant.BOOK_NAME)
    private String name;

    @Min(value = 0)
    @Column(name = BookConstant.BOOK_QUANTITY)
    private int quantity;

    @Version
    private long version;

    @ManyToMany(mappedBy = "book",
            fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<Order>();

    public Book(String name, int quantity) {
        setName(name);
        setQuantity(quantity);
    }

    public Book(long id, String name, int quantity) {
        setId(id);
        setName(name);
        setQuantity(quantity);
    }

    public Book(String name, int quantity, long version) {
        setName(name);
        setQuantity(quantity);
        setVersion(version);
    }

    public Book(long id, String name, int quantity, long version) {
        setId(id);
        setName(name);
        setQuantity(quantity);
        setVersion(version);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Book book = (Book) o;
        return quantity == book.quantity &&
                id == book.id &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((name != null) ? name.hashCode() : 0);
        result = prime * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", version=" + version +
                '}';
    }
}
