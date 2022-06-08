<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>checkUserid.jsp</title>
<link rel="stylesheet" type="text/css" 
	href="<c:url value='/css/mainstyle.css'/>"/>
<script type="text/javascript" src="../js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#btUse').click(function(){
			$(opener.document).find('#userid').val("${param.userid}");
			$(opener.document).find('#chkId').val("Y");
			
			self.close();
		});
	});
</script>
</head>
<body>
	<h2>아이디 중복 검사</h2>
	<form name="frmId" method="post" 
		action="<c:url value='/member/checkUserid'/>">
		<input type="text" name="userid" id="userid"
										value="${param.userid}" title="아이디입력">
		<input type="submit" id="submit" value="아이디 확인">
		
		<c:if test="${result==UNUSABLE_ID }">
			<p>이미 등록된 아이디입니다. 다른 아이디를 입력하세요</p>
		</c:if>	
		<c:if test="${result==USABLE_ID }">
			<input type="button" value="사용하기" id="btUse">
			<p>사용가능한 아이디입니다. [사용하기]버튼을 클릭하세요</p>
		</c:if>
	</form>
</body>
</html>