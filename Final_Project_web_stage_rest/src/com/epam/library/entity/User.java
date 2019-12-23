package com.epam.library.entity;

import com.epam.library.entity.enumeration.Role;
import com.epam.library.util.constant.entityconstant.UserConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = UserConstant.USER_TABLE)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class User {

	@Id
	@Column(name = UserConstant.ID)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Min(1)
	private long id;

	@Column(name = UserConstant.NAME)
	@NotBlank
	private String name;

	@Column(name = UserConstant.LAST_NAME)
	@NotBlank
	private String lastName;

	@Column(name = UserConstant.EMAIL)
	@NotBlank
	private String email;

	@Column(name = UserConstant.LOGIN)
	@NotBlank
	private String login;

	@Column(name = UserConstant.PASSWORD)
	@NotBlank
	private String password;

	@Column(name = UserConstant.ROLE)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = UserConstant.BLOCKED)
	private boolean blocked;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<Order> orders = new ArrayList<>();

	public User(String name, String LastName, String email, String login, String password, Role role) {
		setName(name);
		setLastName(LastName);
		setEmail(email);
		setLogin(login);
		setPassword(password);
		setRole(role);
	}

	public User(String name, String LastName, String email, String login, String password) {
		setName(name);
		setLastName(LastName);
		setEmail(email);
		setLogin(login);
		setPassword(password);
	}

	public User(long id, String name, String lastName, String email, String login, String password, boolean blocked) {
		setId(id);
		setName(name);
		setLastName(lastName);
		setEmail(email);
		setLogin(login);
		setPassword(password);
		setBlocked(blocked);

	}

	public User(long id, String name, String lastName, String email, String login, String password, Role role,
			boolean blocked) {
		setId(id);
		setName(name);
		setLastName(lastName);
		setEmail(email);
		setLogin(login);
		setRole(role);
		setPassword(password);
		setBlocked(blocked);
	}

	public User(long id, String name, String lastName, String email, String login, Role role, boolean blocked) {
		setId(id);
		setName(name);
		setLastName(lastName);
		setEmail(email);
		setLogin(login);
		setRole(role);
		setBlocked(blocked);
	}

	public User(String name, String lastName, String email, String login, String password, Role role, boolean blocked) {
		setName(name);
		setLastName(lastName);
		setEmail(email);
		setLogin(login);
		setPassword(password);
		setRole(role);
		setBlocked(blocked);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (this == o) {
			return true;
		}
		User user = (User) o;
		return id == user.id && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName)
				&& Objects.equals(email, user.email) && Objects.equals(login, user.login)
				&& Objects.equals(password, user.password) && role == user.role && blocked == user.blocked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name != null) ? name.hashCode() : 0);
		result = prime * result + ((lastName != null) ? lastName.hashCode() : 0);
		result = prime * result + ((email != null) ? email.hashCode() : 0);
		result = prime * result + ((login != null) ? login.hashCode() : 0);
		result = prime * result + ((password != null) ? password.hashCode() : 0);
		result = prime * result + ((role != null) ? role.hashCode() : 0);
		result = prime * result + (blocked ? 1231 : 1237);
		return result;
	}

	@Override
	public String toString() {

		return "User{" + "id=" + id + ", name='" + name + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email
				+ '\'' + ", login='" + login + '\'' + ", password='" + password + '\'' + ", role=" + role + ", blocked="
				+ blocked + '}';
	}

}
