<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
      <img src="<%=request.getContextPath() %>/images/notice2.JPG" alt="공지사항">      
      <span>
      <a href="<%=request.getContextPath()%>/board/list.jsp">
          <img src="<%=request.getContextPath() %>/images/more.JPG" 
               border="0" alt="more - 더보기">
      </a>
      </span>
   </div>
   <div>
      <img src ="<%=request.getContextPath() %>/images/Line.JPG" alt="" class="img1">
   </div>
   <div>   <!-- 공지사항 -->
      <table summary="최근 공지사항 6건을 보여주는 표입니다.">
         <tbody>
         <!-- 반복시작 -->
          <%for(BoardVO vo : list){ %>
            <tr>
               <td>
                  <img src="<%=request.getContextPath() %>/images/dot.JPG">
                  <a href
            ="<%=request.getContextPath()%>/board/detail.jsp?no=<%=vo.getNo()%>">
                     <%=vo.getTitle() %>
                  </a>
               </td>
            </tr>             
          <%} %>
          <!-- 반복 끝 -->
         </tbody>
      </table>   
   </div>
</div>
    
