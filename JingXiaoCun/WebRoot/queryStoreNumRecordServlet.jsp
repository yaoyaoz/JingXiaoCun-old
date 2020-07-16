<%@ include file="/public/foot.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="QueryStoreNumRecordServlet" method="post">
	
		<%
			String type =  request.getParameter("in_or_out_type");
			if(type == null) {
				type = "type null";
			}
		%>
		
		<div style="text-align: center;">
			库房名称：<input type="text" name="store_name" value="${store_name}" /> &nbsp
			商品名称：<input type="text" name="goods_name" value="${goods_name}" /> &nbsp
			入/出库：
			<select name="in_or_out_type" value="${in_or_out_type}">
				<option value="">请选择</option>
				<option value="IN_STORE" 
				<%= type.equals("IN_STORE") ? "selected" : ""%>
				>入库商品</option>
				<option value="OUT_STORE"
				<%= type.equals("OUT_STORE") ? "selected" : ""%>
				>出库商品</option>
			</select> &nbsp
			<input type="submit" value="查找" />
		</div>
		
		<div>
			<font color="red" > &nbsp &nbsp ${message}</font>
		</div>
		
		<br />
		
		<table border="1" cellspacing="0" cellpadding="3" align="center"
			width="80%">
			<tr>
				<td>入/出库存时间</td>
				<td>库房名称</td>
				<td>商品名称</td>
				<td>入/出库数量</td>
				<td>单价</td>
				<td>操作类型</td>
				<td>操作</td>
			</tr>

			<c:forEach var="goods" items="${goodsList}">
				<tr>
					<td>${goods.in_or_out_date}</td>
					<td><input value="${goods.store_name}"
							   readonly="readonly"
							   style="border:none;
							   width: 100%;
							   font-size: 100%;
							   color: black;
							   background-color: white" disabled/></td>
					<td><input value="${goods.goods_name}"
							   readonly="readonly"
							   style="border:none;
							   width: 100%;
							   font-size: 100%;
							   color: black;
							   background-color: white" disabled/></td>
					<td>${goods.goods_num}</td>
					<td>${goods.goods_price}</td>
					<c:if test="${goods.in_or_out_type == 'IN_STORE'}">
						<td>入库</td>
					</c:if>
					<c:if test="${goods.in_or_out_type == 'OUT_STORE'}">
						<td>出库</td>
					</c:if>
					<td>
						<a href="UpdateStorePageServlet?id=${goods.id}">修改</a>
						|
						<a href="DeleteStoreServlet?id=${goods.id}">删除</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>
