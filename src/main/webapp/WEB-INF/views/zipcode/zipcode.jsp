<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>zipcode.jsp</title>
<link rel="stylesheet" type="text/css" 
	href="<c:url value='/css/mainstyle.css'/> "/>
<style type="text/css">
	.error{
		color: red;
		display: none;
	}
</style>

<script type="text/javascript" src="<c:url value='/js/jquery-3.6.0.min.js'/>"></script>
<script type="text/javascript">
	$(function(){
		$('#submit').click(function(){
			if($('#dong').val().length<1){
				$('form[name=frmZipcode] .error').show();
				event.preventDefault();
			}
		});		
	});
	
	function setZipcode(zipcode, address){
		$(opener.document).find("#zipcode").val(zipcode);
		$(opener.document).find("input[name=address]").val(address);
		
		self.close();
	}
	
	//페이지 번호 클릭시 실행할 함수
	function pageProc(curPage){
		$('input[name=currentPage]').val(curPage);
		$('form[name=frmPage]').submit();
	}
</script>


</head>
<body>
	<h2>우편번호 검색</h2>
	<p style="font-size: 1em">찾고 싶으신 주소의 동(읍/면)을 입력하세요</p>
	<form name="frmZipcode" method="post" 
		action="<c:url value='/zipcode/zipcode'/>">
		<label for="dong">지역명 : </label> 
		<input type="text" name="dong" id="dong" 
			style="ime-mode: active" value="${param.dong}"> 
		<input type="submit" id="submit" value="찾기">
		<span class="error">지역명을 입력하세요</span>
	</form>
	
	<!-- 페이징 처리를 위한 form -->
	<form action="<c:url value='/zipcode/zipcode'/>"
		method="post" name="frmPage">
		<input type="text" name="dong" value="${param.dong}">
		<input type="text" name="currentPage" >	
	</form>
	
	<br>
	
	<!-- 조건이 list가 null이 아닐때나, param.dong이 없을때 테이블을 보여주면된다. -->
	<!-- null과 empty는 내부적으로 차이가 있음. DB작업을 하고 결과가 없다면 empty로 걸러야한다. -->
	<c:if test="${list != null}">
	<table style="width: 470px" class="box2"
		summary="우편번호 검색 결과에 관한 표로써, 우편번호, 주소에 대한 정보를 제공합니다.">
		<colgroup>
			<col style="width: 20%" />
			<col style="width: *" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">우편번호</th>
				<th scope="col">주소</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty list}">
				<tr>
					<td colspan="2" class="align_center">
						해당 주소가 존재하지 않습니다.
					</td>
				</tr>	
			</c:if>
			<c:if test="${!empty list}">
				<c:forEach var="vo" items="${list}">
					<c:set var="bunji" value="${vo.startbunji}"/>
					<c:set var="endBunji" value="${vo.endbunji}"/>
					
					<c:if test="${!empty endBunji}">
						<c:set var="bunji" value="${bunji} ~ ${endBunji}"/>
					</c:if>
					
					<c:set var="address" 
						value="${vo.sido} ${vo.gugun} ${vo.dong}"/>

					<tr>
						<td>${vo.zipcode}</td>
						<td>
							<a href="#" onclick
					="setZipcode('${vo.zipcode}','${address}')">
								${address} ${bunji}
							</a>	
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	</c:if>
	
	<!-- 페이징처리 -->
	<div class="divPage">
		<!-- 이전블럭으로 이동 -->
		<c:if test="${pagingInfo.firstPage>1 }">	
			<a href="#" onclick="pageProc(${pagingInfo.firstPage-1})">
				<img src='<c:url value="/images/first.JPG"/>'>
			</a>	
		</c:if>
		<!-- 페이지 번호 추가 -->						
		<!-- [1][2][3][4][5][6][7][8][9][10] -->
		<c:forEach var="i" begin="${pagingInfo.firstPage }" 
			end="${pagingInfo.lastPage }">		
			<c:if test="${i==pagingInfo.currentPage }">
				<span style="color: blue;font-weight: bold;font-size: 1em">
					${i }</span>
			</c:if>
			<c:if test="${i!=pagingInfo.currentPage }">			
				<a href="#" onclick="pageProc(${i})">	
					[${i}]
				</a>
			</c:if>		
		</c:forEach>
		<!--  페이지 번호 끝 -->
		
		<!-- 다음 블럭으로 이동 -->
		<c:if test="${pagingInfo.lastPage < pagingInfo.totalPage }">	
			<a href="#" onclick="pageProc(${pagingInfo.lastPage+1})">
				<img src='<c:url value="/images/last.JPG"/>'>
			</a>	
		</c:if>
	</div>
</body>
</html>