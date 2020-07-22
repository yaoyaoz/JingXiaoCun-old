package dal.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import dal.GoodsDao;
import domain.Goods;
import domain.GoodsStoreNum;
import domain.InOrOutType;
import exception.NoGoodsNumException;
import utils.JdbcUtils;

public class GoodsDaoImpl implements GoodsDao {

	private JdbcUtils db = new JdbcUtils();
	private Connection conn = null;
	private PreparedStatement st = null;
	private ResultSet rs = null;

	protected Logger logger = Logger.getLogger(getClass());

	@Override
	public void insertGoods(Goods goods) throws Exception {

		logger.info("插入商品，入参：" + goods);

		checkOut_goods_num(goods);

		try {
			conn = db.getConnection();

			conn.setAutoCommit(false); // start transaction;

			String sql = "insert into goods(id,in_or_out_date,store_name,goods_name,goods_num,goods_price,in_or_out_type,raw_add_time) "
					+ "values" + "(?,?,?,?,?,?,?,now())";

			st = conn.prepareStatement(sql);
			st.setString(1, goods.getId());
			st.setDate(2, new Date(goods.getIn_or_out_date().getTime()));
			st.setString(3, goods.getStore_name());
			st.setString(4, goods.getGoods_name());
			st.setInt(5, goods.getGoods_num());
			st.setDouble(6, goods.getGoods_price());
			st.setString(7, goods.getIn_or_out_type());

			int num = st.executeUpdate();

			logger.info("插入商品，出参：num=" + num);

			conn.commit(); // commit;

		} catch (Exception e) {
			try {
				conn.rollback();
				logger.info("插入商品，回滚完成！");
			} catch (SQLException e1) {
				logger.info("插入商品，回滚异常！" + e1);
				throw new Exception(e1);
			}
			logger.info("插入商品，异常！" + e);
			throw new Exception(e);

		} finally {
			db.release(conn, st, rs);
		}

	}

	/**
	 * 出库前，先判断出库数量是否大于库存数量
	 * 
	 * @param goods
	 * @throws Exception
	 * @throws NoGoodsNumException
	 */
	private void checkOut_goods_num(Goods goods) throws Exception, NoGoodsNumException {
		if (goods.getIn_or_out_type().equals(InOrOutType.OUT_STORE)) {

			List<GoodsStoreNum> list = getGoodsStoreNumList(goods);

			if (list != null && list.size() == 1) {
				GoodsStoreNum goodsStoreNum = list.get(0);

				// 库存数量
				int now_goods_num = goodsStoreNum.getNow_goods_num();
				logger.info("库存数量:" + now_goods_num);

				// 打算出库数量
				int out_goods_num = goods.getGoods_num();
				logger.info("打算出库数量:" + out_goods_num);

				// 比较
				if (out_goods_num > now_goods_num) {
					throw new Exception(goods.getStore_name() + " 中 " + goods.getGoods_name() + " 库存为：" + now_goods_num
							+ "，不能满足出库数量：" + out_goods_num);
				}
			} else {
				logger.info(goods.getStore_name() + " 中不存在 " + goods.getGoods_name() + "，无法出库！");
				throw new NoGoodsNumException(goods.getStore_name() + " 中不存在 " + goods.getGoods_name() + "，无法出库！");
			}
		}                               
	}

	private List<GoodsStoreNum> getGoodsStoreNumList(Goods goods) throws Exception {
		GoodsStoreNum queryGoodsStoreNum = new GoodsStoreNum();
		queryGoodsStoreNum.setStore_name(goods.getStore_name());
		queryGoodsStoreNum.setStore_name(goods.getStore_name());
		queryGoodsStoreNum.setGoods_name(goods.getGoods_name());
		queryGoodsStoreNum.setGoods_name(goods.getGoods_name());
		List<GoodsStoreNum> list = queryStoreNum(queryGoodsStoreNum);
		return list;
	}

	@Override
	public List<GoodsStoreNum> queryStoreNum(GoodsStoreNum goodsStoreNum) throws Exception {

		logger.info("查询库存，开始：");
		List<GoodsStoreNum> goodsStoreNumlist = new ArrayList<GoodsStoreNum>();

		try {
			conn = db.getConnection();
			String sql = "select" + " a.store_name," + "	a.goods_name," + " a.goods_num as in_good_num,"
					+ "	ifNull(b.goods_num, 0) as out_good_num,"
					+ "	a.goods_num-ifNull(b.goods_num, 0) as now_goods_num " + "from" + "	(" + "		select "
					+ "			store_name," + "			goods_name," + "			sum(goods_num) as goods_num,"
					+ "			in_or_out_type"
					+ "		from goods group by store_name,goods_name,in_or_out_type having in_or_out_type='IN_STORE'"
					+ "	) a" + "	left join" + "	(" + "		select " + "			store_name,"
					+ "			goods_name," + "			sum(goods_num) as goods_num," + "			in_or_out_type"
					+ "		from goods group by store_name,goods_name,in_or_out_type having in_or_out_type='OUT_STORE'"
					+ "	) b" + "	on a.goods_name=b.goods_name" + "	and a.store_name=b.store_name where 1=1";

			if (goodsStoreNum.getStore_name() != null && !goodsStoreNum.getStore_name().equals("")) {
				sql = sql + " and a.store_name = '" + goodsStoreNum.getStore_name() + "' ";
			}
			if (goodsStoreNum.getGoods_name() != null && !goodsStoreNum.getGoods_name().equals("")) {
				sql = sql + " and a.goods_name = '" + goodsStoreNum.getGoods_name() + "' ";
			}

			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			while (rs.next()) {
				GoodsStoreNum goodsStoreNum1 = new GoodsStoreNum();

				goodsStoreNum1.setStore_name(rs.getString("store_name"));
				goodsStoreNum1.setGoods_name(rs.getString("goods_name"));
				goodsStoreNum1.setIn_good_num(rs.getInt("in_good_num"));
				goodsStoreNum1.setOut_good_num(rs.getInt("out_good_num"));
				goodsStoreNum1.setNow_goods_num(rs.getInt("now_goods_num"));

				goodsStoreNumlist.add(goodsStoreNum1);
			}
			logger.info("查询库存，结束，出参：size=" + goodsStoreNumlist.size());
			return goodsStoreNumlist;

		} catch (SQLException e) {
			logger.info("查询库存，异常！" + e);
			throw new Exception(e);

		} finally {
			db.release(conn, st, rs);
		}

	}

	@Override
	public List<Goods> queryGoodsList(Goods goods) throws Exception {

		logger.info("查询库存记录，开始：");
		List<Goods> goodsList = new ArrayList<Goods>();

		try {
			conn = db.getConnection();
			String sql = " select id,in_or_out_date,store_name,goods_name,goods_num,goods_price,"
					+ "in_or_out_type,raw_add_time,raw_update_time " + " from goods " + " where goods_num != 0 ";
			if (goods.getId() != null && !goods.getId().equals("")) {
				sql = sql + " and id = '" + goods.getId() + "'";
			}
			if (goods.getStore_name() != null && !goods.getStore_name().equals("")) {
				sql = sql + " and store_name = '" + goods.getStore_name() + "' ";
			}
			if (goods.getGoods_name() != null && !goods.getGoods_name().equals("")) {
				sql = sql + " and goods_name = '" + goods.getGoods_name() + "' ";
			}
			if (goods.getIn_or_out_type() != null && !goods.getIn_or_out_type().equals("")) {
				sql = sql + " and in_or_out_type = '" + goods.getIn_or_out_type() + "' ";
			}

			sql = sql + " order by raw_add_time desc";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();

			while (rs.next()) {
				Goods goods1 = new Goods();

				goods1.setGoods_name(rs.getString("goods_name"));
				goods1.setGoods_num(rs.getInt("goods_num"));
				goods1.setGoods_price(rs.getDouble("goods_price"));
				goods1.setId(rs.getString("id"));
				goods1.setIn_or_out_date(rs.getDate("in_or_out_date"));
				goods1.setIn_or_out_type(rs.getString("in_or_out_type"));
				goods1.setRaw_add_time(rs.getDate("raw_add_time"));
				goods1.setRaw_update_time(rs.getDate("raw_update_time"));
				goods1.setStore_name(rs.getString("store_name"));

				goodsList.add(goods1);
			}
			logger.info("查询库存记录，结束，出参：size=" + goodsList.size());
			return goodsList;

		} catch (SQLException e) {
			logger.info("查询库存，异常！" + e);
			throw new Exception(e);

		} finally {
			db.release(conn, st, rs);
		}

	}

	@Override
	public void updateStore(Goods goods) throws Exception {

		logger.info("修改商品，入参：" + goods);
		
		checkGoodsNumByUpdate(goods);

		try {
			conn = db.getConnection();

			conn.setAutoCommit(false); // start transaction;

			String sql = "update goods set raw_update_time=now() ";

			sql = sql + ", in_or_out_type = '" + goods.getIn_or_out_type() + "'";
			sql = sql + ", store_name = '" + goods.getStore_name() + "'";
			sql = sql + ", goods_name = '" + goods.getGoods_name() + "'";
			sql = sql + ", goods_num = '" + goods.getGoods_num() + "'";
			sql = sql + ", goods_price = '" + goods.getGoods_price() + "'";
			sql = sql + ", in_or_out_date = '" + new Date(goods.getIn_or_out_date().getTime()) + "'";

			sql = sql + " where id=?";

			st = conn.prepareStatement(sql);
			st.setString(1, goods.getId());

			int num = st.executeUpdate();

			logger.info("更新商品，出参：num=" + num);

			conn.commit(); // commit;

		} catch (Exception e) {
			try {
				conn.rollback();
				logger.info("更新商品，回滚完成！");
			} catch (SQLException e1) {
				logger.info("更新商品，回滚异常！" + e1);
				throw new Exception(e1);
			}
			logger.info("更新商品，异常！" + e);
			throw new Exception(e);

		} finally {
			db.release(conn, st, rs);
		}

	}

	/**
	 * 校验 欲修改的数量
	 * 
	 * @param goods
	 * @throws Exception
	 */
	private void checkGoodsNumByUpdate(Goods goods) throws Exception {

		// 查询库存
		List<GoodsStoreNum> list = getGoodsStoreNumList(goods);

		if (list != null && list.size() == 1) {

			// 查询该条记录未修改前的数量
			Goods goodsBeforeQuery = new Goods();
			goodsBeforeQuery.setId(goods.getId());
			List<Goods> goodsList = queryGoodsList(goodsBeforeQuery);
			Goods goodsBefore = goodsList.get(0);

			// 库存
			GoodsStoreNum goodsStoreNum = list.get(0);

			if (goodsBefore.getIn_or_out_type().equals("OUT_STORE")) {
				if (goods.getIn_or_out_type().equals("OUT_STORE")
						&& goodsStoreNum.getNow_goods_num() + goodsBefore.getGoods_num() - goods.getGoods_num() < 0) {
					throw new Exception("出库修改为出库的数量无法满足库存");
				} else if (goods.getIn_or_out_type().equals("IN_STORE")
						&& goodsStoreNum.getNow_goods_num() + goodsBefore.getGoods_num() + goods.getGoods_num() < 0) {
					throw new Exception("出库修改为入库的数量无法满足库存");
				}
			} else if (goodsBefore.getIn_or_out_type().equals("IN_STORE")) {
				if ((goods.getIn_or_out_type().equals("IN_STORE"))
						&& goodsStoreNum.getNow_goods_num() - goodsBefore.getGoods_num() + goods.getGoods_num() < 0) {
					throw new Exception("入库修改为出库的数量无法满足库存");
				} else if (goods.getIn_or_out_type().equals("OUT_STORE")
						&& goodsStoreNum.getNow_goods_num() - goodsBefore.getGoods_num() - goods.getGoods_num() < 0) {
					throw new Exception("入库修改为入库的数量无法满足库存");
				}
			}

		} else {
			if (goods.getIn_or_out_type().equals("OUT_STORE")) {
				logger.info(goods.getStore_name() + " 中不存在 " + goods.getGoods_name() + "，无法修改！");
				throw new NoGoodsNumException(goods.getStore_name() + " 中不存在 " + goods.getGoods_name() + "，无法修改！");
			}
		}
	}

	@Override
	public void deleteStore(String id) throws Exception {
		
		logger.info("删除商品，入参：id=" + id);

		checkDelete_goods_num(id);

		try {
			conn = db.getConnection();

			conn.setAutoCommit(false); // start transaction;

			String sql = "delete from goods where id = ?";

			st = conn.prepareStatement(sql);
			st.setString(1, id);

			int num = st.executeUpdate();

			logger.info("删除商品，出参：num=" + num);

			conn.commit(); // commit;

		} catch (Exception e) {
			try {
				conn.rollback();
				logger.info("删除商品，回滚完成！");
			} catch (SQLException e1) {
				logger.info("删除商品，回滚异常！" + e1);
				throw new Exception(e1);
			}
			logger.info("删除商品，异常！" + e);
			throw new Exception(e);

		} finally {
			db.release(conn, st, rs);
		}
		
	}

	private void checkDelete_goods_num(String id) throws Exception {
		Goods goods = new Goods();
		goods.setId(id);

		// 查询该条记录
		List<Goods> goodsList = queryGoodsList(goods);
		if (goodsList != null && goodsList.size() == 1) {
			Goods goodsDelete = goodsList.get(0);
			if (goodsDelete.getIn_or_out_type().equals("IN_STORE")) {
				// 查询库存
				Goods queryGoodsStoreNum = new Goods();
				queryGoodsStoreNum.setStore_name(goodsDelete.getStore_name());
				queryGoodsStoreNum.setStore_name(goodsDelete.getStore_name());
				queryGoodsStoreNum.setGoods_name(goodsDelete.getGoods_name());
				queryGoodsStoreNum.setGoods_name(goodsDelete.getGoods_name());
				List<GoodsStoreNum> goodsStoreNumlist = getGoodsStoreNumList(queryGoodsStoreNum);
				if (goodsStoreNumlist != null && goodsStoreNumlist.size() == 1) {
					// 库存
					GoodsStoreNum goodsStoreNum = goodsStoreNumlist.get(0);
					if (goodsStoreNum.getNow_goods_num() - goodsDelete.getGoods_num() < 0) {
						throw new Exception("删除该条记录后不能满足库存");
					}
				}
			}
		} else {
			throw new Exception("根据id没有查询到想要删除的数据或者不止一条。id=" + id);
		}
	}

}
