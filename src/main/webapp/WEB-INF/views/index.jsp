<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <!-- http://localhost:9091/herb/ -->

<%@ include file ="inc/top.jsp"%>

<div id="centerCon">
	<img src="<c:url value='/images/herb.JPG'/>" alt="herb 이미지"/>
</div>
<div id="rightCon">
	<!-- 공지사항 -->
	<!-- include 액션태그 : 소스코드의 실행결과를 포함한다. -->
	<c:import url="/board/mainNotice"/>
</div>
<div id="listCon">
	<!-- 이벤트별 상품 목록 -->
	<!-- include 지시자 : 소스코드 자체를 포함한다. -->
	<%@ include file ="shop/product/productCatalog.jsp"%>
	
</div>




<%@ include file ="inc/bottom.jsp"%>