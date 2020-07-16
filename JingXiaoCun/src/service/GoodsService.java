package service;

import java.util.List;

import domain.Goods;
import domain.GoodsStoreNum;

public interface GoodsService {

	// 库存查看
	public List<GoodsStoreNum> queryStoreNum(GoodsStoreNum goodsStoreNum) throws Exception;

	// 入/出库
	public void inOrOutStore(Goods goods) throws Exception;

	// 库存记录查看
	public List<Goods> queryGoodsList(Goods goods) throws Exception;

	// 修改库存记录
	public void updateStore(Goods goods) throws Exception;
	
	//根据id删除记录
	public void deleteStore(String id) throws Exception;
}
