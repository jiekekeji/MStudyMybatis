package com.jk;

import java.io.IOException;

import com.jk.dao.UserDao;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		UserDao user = new UserDao();
		try {
			user.findUserById();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
