<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	
</script>

<article class="simpleForm">
	<form name="frmLogin" method="post" 
		action="<c:url value='/login/login'/>">
		<fieldset>
			<legend>로그인</legend>
			<div>
				<label for="userid" class="label">아이디</label>
				<input type="text" name="userid" id="userid"
					value="${cookie.ckUserid.value}">
			</div>			
			<div>
				<label for="pwd" class="label">비밀번호</label>
				<input type="password" name="pwd" id="pwd">
			</div>			
			<div class="align_center">
				<input type="submit" value="로그인">
				
				<input type="checkbox" name="chkSaveId" id="saveId"
					<c:if test="${!empty cookie.ckUserid}">
						checked="checked"
					</c:if>
						>
					
				<label for="saveId">아이디 저장하기</label>
			</div>			
		</fieldset>
	</form>
</article>

<%@ include file="../inc/bottom.jsp" %>









