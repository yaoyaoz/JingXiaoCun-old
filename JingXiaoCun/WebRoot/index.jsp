<%@ include file="/public/foot.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>首页</title>
</head>
<body>
	<form action="#" method="post">
		
		<div style="text-align: center;">
			库房名称：<input type="text" name="store_name" value="${store_name}" /> &nbsp
			商品名称：<input type="text" name="goods_name" value="${goods_name}" /> &nbsp
			<input type="submit" value="查找" />
		</div>
		
		<br />
		
		<table border="1" cellspacing="0" cellpadding="3" align="center" width="80%">
			<tr>
				<td>库房名称</td>
				<td>商品名称</td>
				<td>入库数量</td>
				<td>出库数量</td>
				<td>库存数量</td>
			</tr>
			
			<c:forEach var="goodsStoreNum" items="${list}">
				<tr>
					<td><input value="${goodsStoreNum.store_name}"
							   readonly="readonly"
							   style="border:none;
							   width: 100%;
							   font-size: 100%;
							   color: black;
							   background-color: white" disabled/></td>
					<td><input value="${goodsStoreNum.goods_name}"
							   readonly="readonly"
							   style="border:none;
							   width: 100%;
							   font-size: 100%;
							   color: black;
							   background-color: white" disabled/></td>
					<td>${goodsStoreNum.in_good_num}</td>
					<td>${goodsStoreNum.out_good_num}</td>
					<td>${goodsStoreNum.now_goods_num}</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>