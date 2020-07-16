package domain;

import java.util.Date;

public class Goods {
	
	/** 序列 */
	private String id;
	/** 入/出库时间 */
	private Date in_or_out_date;
	/** 库房名称 */
	private String store_name;
	/** 商品名称 */
	private String goods_name;
	/** 入/出库数量 */
	private int goods_num;
	/** 单价 */
	private double goods_price;
	/** 操作类型 */
	private String in_or_out_type;
	/** 添加时间 */
	private Date raw_add_time;
	/** 更新时间 */
	private Date raw_update_time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getIn_or_out_date() {
		return in_or_out_date;
	}
	public void setIn_or_out_date(Date in_or_out_date) {
		this.in_or_out_date = in_or_out_date;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public int getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
	}
	public double getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}
	public String getIn_or_out_type() {
		return in_or_out_type;
	}
	public void setIn_or_out_type(String in_or_out_type) {
		this.in_or_out_type = in_or_out_type;
	}
	public Date getRaw_add_time() {
		return raw_add_time;
	}
	public void setRaw_add_time(Date raw_add_time) {
		this.raw_add_time = raw_add_time;
	}
	public Date getRaw_update_time() {
		return raw_update_time;
	}
	public void setRaw_update_time(Date raw_update_time) {
		this.raw_update_time = raw_update_time;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Goods[");
		sb.append("id=");
		sb.append(id);
		sb.append(", in_or_out_date=");
		sb.append(in_or_out_date);
		sb.append(", store_name=");
		sb.append(store_name);
		sb.append(", goods_name=");
		sb.append(goods_name);
		sb.append(", goods_num=");
		sb.append(goods_num);
		sb.append(", goods_price=");
		sb.append(goods_price);
		sb.append(", in_or_out_type=");
		sb.append(in_or_out_type);
		sb.append(", raw_add_time=");
		sb.append(raw_add_time);
		sb.append(", raw_update_time=");
		sb.append(raw_update_time);
		sb.append("]");
		return sb.toString();
	}
	
}
