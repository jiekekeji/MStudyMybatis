package com.jk.result;

import com.jk.bean.Orders;

public class OrderUser extends Orders {

	// // 属性名和数据库表名一一对应
	// // private Integer id;
	private String username;
	private String sex;
	private String birthday;
	private String address;

	public OrderUser() {
		super();
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

	@Override
	public String toString() {
		return "OrderUser [username=" + username + ", sex=" + sex + ", birthday=" + birthday + ", address=" + address
				+ "]";
	}

}
