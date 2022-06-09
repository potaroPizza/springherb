<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">
   .divNotice{
      width:310px
   }
   .divNotice div table{
      width:300px
   }
   .divNotice div span{
      padding:0 0 0 160px;
   }
   .divNotice div .img1{
      width:310px;height:6px
   }
</style>
<div class="divNotice">
   <div>
   		<%-- url태그나 ${pageContext.request.contextPath}나 아무거나 써도됨 --%>
      <img src="${pageContext.request.contextPath}/images/notice2.JPG" alt="공지사항">      
      <span>
      <a href="<c:url value='/board/list.do'/>">
          <img src="<c:url value='/images/more.JPG'/>" 
               border="0" alt="more - 더보기">
      </a>
      </span>
   </div>
   <div>
      <img src="<c:url value='/images/Line.JPG'/>" alt="" class="img1">
   </div>
   <div>   <!-- 공지사항 -->
      <table summary="최근 공지사항 6건을 보여주는 표입니다.">
         <tbody>
         <!-- 반복시작 -->
          <c:forEach var="vo" items="${noticeList}">
            <tr>
               <td>
                  <img src="<c:url value='/images/dot.JPG'/>">
                  <a href
            ="<c:url value='/board/detail.do?no=${vo.no}'/>">
                     ${vo.title}
                  </a>
               </td>
            </tr>             
          </c:forEach>
          <!-- 반복 끝 -->
         </tbody>
      </table>   
   </div>
</div>
    
