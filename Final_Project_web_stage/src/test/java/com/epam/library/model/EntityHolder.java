package com.epam.library.model;

import com.epam.library.entity.Book;
import com.epam.library.entity.Order;
import com.epam.library.entity.User;
import com.epam.library.entity.enumeration.ReadingPlace;
import com.epam.library.entity.enumeration.Role;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EntityHolder {
    public List<Book> books() {
        return Arrays.asList(
                new Book(1, "White Squall", 40, 1),
                new Book(2, "Black Bail", 14, 1),
                new Book(3, "Book Done", 400, 1),
                new Book(4, "Books A lot", 44, 1)
        );
    }

    public List<Order> orders() {
        return Arrays.asList(
                new Order(1, new HashSet<>(books()), users().get(0), new Date(2018 - 12 - 13), new Date(2019 - 10 - 10), ReadingPlace.HALL, false),
                new Order(2, new HashSet<>(books()), users().get(1), new Date(2018 - 10 - 18), new Date(2019 - 6 - 14), ReadingPlace.HOME, false),
                new Order(3, new HashSet<>(books()), users().get(2), new Date(2019 - 3 - 26), new Date(2019 - 10 - 10), ReadingPlace.HALL, false),
                new Order(4, new HashSet<>(books()), users().get(3), new Date(2019 - 5 - 3), new Date(2019 - 9 - 26), ReadingPlace.HOME, false),
                new Order(5, new HashSet<>(books()), users().get(4), new Date(2018 - 11 - 10), new Date(2019 - 10 - 12), ReadingPlace.HALL, false),
                new Order(6, new HashSet<>(books()), users().get(5), new Date(2018 - 12 - 13), new Date(2019 - 10 - 10), ReadingPlace.HOME, false),
                new Order(7, new HashSet<>(books()), users().get(6), new Date(2018 - 1 - 1), new Date(2019 - 1 - 10), ReadingPlace.HALL, false),
                new Order(8, new HashSet<>(books()), users().get(7), new Date(2018 - 2 - 3), new Date(2019 - 10 - 15), ReadingPlace.HOME, false),
                new Order(9, new HashSet<>(books()), users().get(8), new Date(2018 - 10 - 23), new Date(2019 - 11 - 10), ReadingPlace.HALL, false),
                new Order(10, new HashSet<>(books()), users().get(9), new Date(2018 - 5 - 30), new Date(2019 - 10 - 16), ReadingPlace.HOME, false),
                new Order(11, new HashSet<>(books()), users().get(1), new Date(2018 - 10 - 19), new Date(2019 - 12 - 18), ReadingPlace.HOME, false),
                new Order(12, new HashSet<>(books()), users().get(0), new Date(2018 - 6 - 3), new Date(2019 - 4 - 1), ReadingPlace.HOME, false),
                new Order(13, new HashSet<>(books()), users().get(4), new Date(2018 - 12 - 4), new Date(2019 - 9 - 19), ReadingPlace.HALL, false),
                new Order(14, new HashSet<>(books()), users().get(8), new Date(2018 - 1 - 28), new Date(2019 - 8 - 20), ReadingPlace.HOME, false)
        );
    }


    public List<User> users() {
        return Arrays.asList(
                new User(1, "Friedrick", "Thews", "fthews14@europa.eu", "fthews14", "w4BQBWm26r", Role.READER, false),
                new User(2, "Elysha", "Wilford", "ewilfordy@sun.com", "ewilfordy", "vRFaVxKMQ", Role.READER, false),
                new User(3, "Friedrick", "Thews", "fthews14@europa.eu", "fthews14", "w4BQBWm26r", Role.READER, false),
                new User(4, "Mose", "Jankiewicz", "mjankiewicz6@booking.com", "mjankiewicz6", "kD8GJH8", Role.READER, false),
                new User(5, "Honoria", "Crookston", "hcrookston1a@howstuffworks.com", "hcrookston1a", "Fmipx2hXWjFu", Role.READER, false),
                new User(6, "Sigrid", "Podmore", "spodmored@ca.gov", "spodmored", "YuammAAV", Role.READER, false),
                new User(7, "Evy", "Buscombe", "ebuscombe2@example.com", "readerOne", "5ae4682530c105e58d85b3e915fd5bbc", Role.READER, false),
                new User(8, "Celestyn", "Bartholomew", "cbartholomew4@geocities.com", "cbartholomew4", "3HpTP8ecYWV9", Role.READER, false),
                new User(9, "Ellie", "Todd", "etodd1@mit.edu", "librarian", "35fa1bcb6fbfa7aa343aa7f253507176", Role.LIBRARIAN, false),
                new User(10, "Honoria", "Crookston", "hcrookston1a@howstuffworks.com", "hcrookston1a", "Fmipx2hXWjFu", Role.READER, false)
        );
    }

    public Set<Book> orderOneBooks() {
        return Set.of(
                new Book(27, "Natural, The", 82, 1),
                new Book(31, "Can't Help Singing", 76, 1),
                new Book(26, "Bionicle", 74, 1),
                new Book(17, "Fantasia", 29, 1),
                new Book(40, "Landet som icke är", 58, 1),
                new Book(25, "Internship, The", 98, 1),
                new Book(42, "Endangered Species", 35, 1),
                new Book(13, "Brother of Sleep (Schlafes Bruder)", 80, 1),
                new Book(8, "Barkleys of Broadway, The", 46, 1),
                new Book(49, "Ju-on: The Curse", 74, 1),
                new Book(6, "Liberty Stands Still", 41, 1),
                new Book(35, "Africa Screams", 62, 1)
        );
    }
}
