/**
 * 修改页面
 */
package web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import domain.Goods;
import service.GoodsService;
import service.impl.GoodsServiceImpl;
import web.formBean.InOrOutStoreFormBean;

/**
 * Servlet implementation class UpdateStoreServletPageServlet
 */
@WebServlet("/UpdateStorePageServlet")
public class UpdateStorePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(getClass());

	GoodsService service = new GoodsServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String id = request.getParameter("id");
			if (id != null && !id.equals("")) {
				Goods goods = new Goods();
				goods.setId(id);
				List<Goods> list = service.queryGoodsList(goods);
				if (list != null && list.size() == 1) {
					request.setAttribute("formBean", setFormBean(list.get(0)));
					request.getRequestDispatcher("/updateStoreServlet.jsp").forward(request, response);
				}
			}

		} catch (Exception e) {
			request.setAttribute("message", "跳转到修改页面失败！" + e);
			logger.error(e.getMessage(), e);
			request.getRequestDispatcher("/updateStoreServlet.jsp").forward(request, response);
		}
	}

	private InOrOutStoreFormBean setFormBean(Goods goods) {
		InOrOutStoreFormBean formBean = new InOrOutStoreFormBean();
		formBean.setId(goods.getId());
		formBean.setIn_or_out_type(goods.getIn_or_out_type());
		formBean.setStore_name(goods.getStore_name());
		formBean.setGoods_name(goods.getGoods_name());
		formBean.setGoods_num(String.valueOf(goods.getGoods_num()));
		formBean.setGoods_price(String.valueOf(goods.getGoods_price()));
		if (goods.getIn_or_out_date() != null) {
			formBean.setIn_or_out_date(String.valueOf(goods.getIn_or_out_date()));
		}
		return formBean;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
