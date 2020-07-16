<%@ include file="/public/foot.jsp" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>入/出库</title>
</head>
<body>
	<form action="InOrOutStoreServlet" method="post">
		<table border="1" cellspacing="0" cellpadding="15" align="center" width="60%">
			<tr>
				<td>操作类型</td>
				<td>
					<%
						String type =  request.getParameter("in_or_out_type");
						if(type == null) {
							type = "type null";
						}
					%>
					<input type="radio" name="in_or_out_type" value="IN_STORE" 
					<%= type.equals("IN_STORE") ? "checked" : ""%>
					>入库</input>
					<input type="radio" name="in_or_out_type" value="OUT_STORE"
					<%= type.equals("OUT_STORE") ? "checked" : ""%>
					>出库</input>
					<font color="red">${formBean.errors.in_or_out_type}</font>
				</td>
			</tr>
			<tr>
				<td>库房名称</td>
				<td>
					<input type="text" name="store_name" value="${formBean.store_name}" />
					<font color="red">${formBean.errors.store_name}</font>
				</td>
			</tr>
			<tr>
				<td>商品名称</td>
				<td>
					<input type="text" name="goods_name" value="${formBean.goods_name}" />
					<font color="red">${formBean.errors.goods_name}</font>
				</td>
			</tr>
			<tr>
				<td>入/出库数量 </td>
				<td>
					<input type="text" name="goods_num" value="${formBean.goods_num}" />
					<font color="red">${formBean.errors.goods_num}</font>
				</td>
			</tr>
			<tr>
				<td>单价</td>
				<td>
					<input type="text" name="goods_price" value="${formBean.goods_price}" />
					<font color="red">${formBean.errors.goods_price}</font>
				</td>
			</tr>
			<tr>
				<td>入/出库日期</td>
				<td>
					<input type="text" name="in_or_out_date" value="${formBean.in_or_out_date}" />格式，如：2015-07-05
					<font color="red">${formBean.errors.in_or_out_date}</font>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="保存" />
					<font color="blue">${message}</font>
				</td>
				<td>
					<input type="reset" value="清空" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>