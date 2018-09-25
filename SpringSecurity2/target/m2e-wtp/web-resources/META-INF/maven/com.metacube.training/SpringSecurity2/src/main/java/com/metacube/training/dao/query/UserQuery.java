package com.metacube.training.dao.query;

public interface UserQuery {

	String GET_ALL_USERS = "select u.username, u.password, r.role from "
			+ "users u left join role r on r.username = u.username where r.role is not null";
}
