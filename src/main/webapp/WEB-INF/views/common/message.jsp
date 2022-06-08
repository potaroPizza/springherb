<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>message.jsp</title>
</head>
<body>
<%
	//String msg = (String)request.getAttribute("msg");
	//String url = (String)request.getAttribute("url");
	//=> /pd/pdEdit.do
	
	// redirect 방식일 때, url에 컨텍스트패스를 추가해줘야한다!
	// 아래에서 location.href을 통해 redirect로 이동하므로,
	// 컨텍스트패스를 추가해줘야만한다
	//url = request.getContextPath() + url;
	//=> /mymvc + /pd/pdEdit.do
%>
	<script type="text/javascript">
            alert("${msg}");
            location.href="<c:url value='${url}'/> ";
	</script>
</body>
</html>