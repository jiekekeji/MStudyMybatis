package com.jk.result;

import java.util.List;

import com.jk.bean.Items;

public class UserItems {

	private Integer id;
	private String username;
	private String sex;
	private String birthday;
	private String address;

	private List<Items> items;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "UserItems [id=" + id + ", username=" + username + ", sex=" + sex + ", birthday=" + birthday
				+ ", address=" + address + ", items=" + items + "]";
	}

}
