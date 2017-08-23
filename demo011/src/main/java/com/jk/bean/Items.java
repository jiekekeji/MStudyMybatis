package com.jk.bean;

public class Items {

	// 属性和数据库列名一一对应
	private Integer id;;
	private String itemsname;
	private Integer price;

	private String detail;
	private String pic;
	private String createtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemsname() {
		return itemsname;
	}

	public void setItemsname(String itemsname) {
		this.itemsname = itemsname;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "Items [id=" + id + ", itemsname=" + itemsname + ", price=" + price + ", detail=" + detail + ", pic="
				+ pic + ", createtime=" + createtime + "]";
	}
}
