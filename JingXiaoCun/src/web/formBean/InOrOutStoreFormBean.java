package web.formBean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class InOrOutStoreFormBean {

	/** 序列 */
	private String id;
	/** 操作类型 */
	private String in_or_out_type;
	/** 库房名称 */
	private String store_name;
	/** 商品名称 */
	private String goods_name;
	/** 入/出库数量 */
	private String goods_num;
	/** 单价 */
	private String goods_price;
	/** 入/出库时间 */
	private String in_or_out_date;

	private Map<String, String> errors = new HashMap<String, String>(); // 保存校验失败信息。

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIn_or_out_type() {
		return in_or_out_type;
	}

	public void setIn_or_out_type(String in_or_out_type) {
		this.in_or_out_type = in_or_out_type;
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

	public String getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(String goods_num) {
		this.goods_num = goods_num;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public String getIn_or_out_date() {
		return in_or_out_date;
	}

	public void setIn_or_out_date(String in_or_out_date) {
		this.in_or_out_date = in_or_out_date;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	/*
	 * in_or_out_type 操作类型：不为空（入库||出库） store_name 库房名称：不为空 goods_name 商品名称：不为空
	 * goods_num 入/出库数量：不为空，int类型 goods_price 单价：不为空，double类型 in_or_out_date
	 * 入/出库时间：不为空，格式，如：2015-07-05
	 */
	public boolean validate() {

		boolean isOk = true;

		if (this.in_or_out_type == null || this.in_or_out_type.trim().equals("")) {
			isOk = false;
			errors.put("in_or_out_type", "请选择操作类型！");
		}

		if (this.store_name == null || this.store_name.trim().equals("")) {
			isOk = false;
			errors.put("store_name", "请输入库房名称！");
		}

		if (this.goods_name == null || this.goods_name.trim().equals("")) {
			isOk = false;
			errors.put("goods_name", "请输入商品名称！");
		}

		if (this.goods_num == null && this.goods_num.trim().equals("")) {
			isOk = false;
			errors.put("goods_num", "请输入商品数量！");
		} else if (!this.goods_num.matches("[1-9]{1}[0-9]*")) { // {n}恰好n次，*
																// 零次或多次
			isOk = false;
			errors.put("goods_num", "请输入整数！");
		}

		if (this.goods_price == null && this.goods_price.trim().equals("")) {
			isOk = false;
			errors.put("goods_price", "请输入单价！");
		} else {
			if (!this.goods_price.matches("([1-9]+[0-9]*|0)(\\.[\\d]+)?")) { // +
																				// 一次或多次，*
																				// 零次或多次，?
																				// 一次或一次也没有
				isOk = false;
				errors.put("goods_price", "请输入正确的数字！");
			}
		}

		if (this.in_or_out_date == null && this.in_or_out_date.trim().equals("")) {
			isOk = false;
			errors.put("goods_price", "请输入日期！");
		} else {
			try {
				DateLocaleConverter conver = new DateLocaleConverter();
				conver.convert(this.in_or_out_date);

			} catch (Exception e) {
				isOk = false;
				errors.put("in_or_out_date", "请按格式输入时间！");
			}
		}

		return isOk;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("InOrOutStoreFormBean[");
		sb.append("in_or_out_type=");
		sb.append(in_or_out_type);
		sb.append(", store_name=");
		sb.append(store_name);
		sb.append(", goods_name=");
		sb.append(goods_name);
		sb.append(", goods_num=");
		sb.append(goods_num);
		sb.append(", goods_price=");
		sb.append(goods_price);
		sb.append(", in_or_out_date=");
		sb.append(in_or_out_date);
		sb.append("]");
		return sb.toString();
	}

}
