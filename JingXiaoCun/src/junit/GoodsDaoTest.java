package junit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.log4j.Logger;
import org.junit.Test;

import dal.impl.GoodsDaoImpl;
import domain.Goods;
import domain.GoodsStoreNum;
import domain.InOrOutType;
import utils.WebUtils;

public class GoodsDaoTest {

	GoodsDaoImpl dao = new GoodsDaoImpl();
	protected Logger logger = Logger.getLogger(getClass());
	
	@Test
	public void insertGoodsTest() {
		
		Goods goods = new Goods();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		goods.setGoods_name("芒果");
		goods.setGoods_num(25);
		goods.setGoods_price(5.0);
		goods.setId(WebUtils.makeId());
		try {
			goods.setIn_or_out_date(dateFormat.parse("2015-07-04"));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return;
		}
		goods.setStore_name("小库房");
		goods.setIn_or_out_type(InOrOutType.IN_STORE);
		try {
			dao.insertGoods(goods);
//			msg:商品插入成功！
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return;
		}
		
	}
	
	@Test
	public void queryStoreNumTest() {
		try {
			GoodsStoreNum goodsStoreNum = new GoodsStoreNum();
			List<GoodsStoreNum> list = dao.queryStoreNum(goodsStoreNum );
			System.out.println(list);
//			msg:查询库存成功
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return;
		}
	}
	
	@Test
	public void t() {
		 try {
             DateLocaleConverter conver = new DateLocaleConverter();
             Object r = conver.convert("1992-10-13");
             System.out.println(r);

         } catch (Exception e) {
             System.out.println("时间不合法");
         }
	}
	
}
