<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
.divPd {
	float: left;
	padding: 20px;
	margin: 0 10px 10px 0;
	width: 140px;
	text-align: center;
	border: 1px solid silver;
}

.line {
	border: 1px solid silver;
	padding: 20px;
}
</style>

<div style="width: 780px; text-align: center;">
		<c:if test="${empty list}">
			<span>신상품이 존재하지 않습니다.</span>
		</c:if>
		<c:if test="${!empty list}">
		<!-- 반복 시작 -->
		<c:forEach var="vo" items="${list}">
		<div class="divPd">
			<a href="<c:url value='/shop/product/productDetail?productNo=${vo.productNo}'/>">
				<img src="<c:url value='/pd_images/${vo.imageURL}'/>"
					border="0" alt="상품이미지">
			</a> 
			<br>
				${vo.productName}
			<br>
				<fmt:formatNumber value="${vo.sellPrice}" pattern="#,###" />원
		</div>
		</c:forEach>
		<!-- 반복 끝 -->
		</c:if>
	</div>
</body>
</html>