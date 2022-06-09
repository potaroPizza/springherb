<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
    
<%@ include file ="../../inc/top.jsp"%>

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

<h2>${param.categoryName}</h2>
<div style="width: 780px; text-align: center;">
	<!-- 해당 카테고리의 상품이 없을때 -->
	<c:if test="${empty list }">
		<div class="line">해당 카테고리에 상품이 없습니다.</div>
	</c:if>

	<c:if test="${!empty list }">
		<!-- 반복 시작 -->
		<c:forEach var="vo" items="${list }">
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

