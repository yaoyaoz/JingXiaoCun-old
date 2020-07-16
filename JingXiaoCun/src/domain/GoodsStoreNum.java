package domain;

public class GoodsStoreNum extends Goods {
	/** 入库数量 */
	private int in_good_num;
	/** 出库数量 */
	private int out_good_num;
	/** 库存数量 */
	private int now_goods_num;
	
	public int getIn_good_num() {
		return in_good_num;
	}
	public void setIn_good_num(int in_good_num) {
		this.in_good_num = in_good_num;
	}
	public int getOut_good_num() {
		return out_good_num;
	}
	public void setOut_good_num(int out_good_num) {
		this.out_good_num = out_good_num;
	}
	public int getNow_goods_num() {
		return now_goods_num;
	}
	public void setNow_goods_num(int now_goods_num) {
		this.now_goods_num = now_goods_num;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GoodsStoreNum[");
		sb.append("in_good_num=");
		sb.append(in_good_num);
		sb.append(", out_good_num=");
		sb.append(out_good_num);
		sb.append(", now_goods_num=");
		sb.append(now_goods_num);
		return sb.toString() + super.toString() + "]";
	}
	
}
