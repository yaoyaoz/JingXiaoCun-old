package web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import service.GoodsService;
import service.impl.GoodsServiceImpl;

/**
 * Servlet implementation class DeleteStoreServlet
 */
@WebServlet("/DeleteStoreServlet")
public class DeleteStoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(getClass());

	GoodsService service = new GoodsServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		try {
			if (id != null && !id.equals("")) {
				service.deleteStore(id);
				request.setAttribute("message", "恭喜您，删除成功！");
				request.getRequestDispatcher("/QueryStoreNumRecordServlet").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("message", "删除失败！" + e);
			logger.error(e.getMessage(), e);
			request.getRequestDispatcher("/QueryStoreNumRecordServlet").forward(request, response);
		}

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
