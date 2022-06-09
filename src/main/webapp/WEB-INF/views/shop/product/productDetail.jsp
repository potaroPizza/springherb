<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../inc/top.jsp"%>

<style type="text/css">	
	#info, #desc{
		width:700px;		
	}	
	#viewImg{
		float:left;
		padding:10px 30px 30px 30px;
		width:30%;				
	}
	#viewPd{
		float:left;
		padding:0 10px 30px 20px;		
	}
	#desc{
		clear:both;
		padding:10px 0 5px 0;		
	}
	
	.line2{
		border-bottom:solid silver 2px;
		padding:0 0 10px 7px;
	}
	.line
	{
		border-bottom:solid 1px silver;
	}
	.boldF{
		font-size:1.2em;
		font-weight:bold;

	}
	.center{
		text-align:center;
	}
	span{
		font-size:0.9em;
	}
	.sp1{
		width:50%;
		float:left;		
	}
	
	form{
		width:350px;
	}
	
		
	p{
		padding:8px;
		font-size:1.0em;
	}
	
	input[type="text"]{
		width:20%;
	}
</style>
<h2>상품 상세 보기</h2>
<div id="info">
	<div id="viewImg">
		<!-- 상품 이미지 -->
		<p class="center">
		<a >
			<img src=""
		 		border="0" width="150">
		</a>	
		</p>
		<p class="center">
		<a>
		 	큰이미지 보기</a></p>
	</div>
	<div id="viewPd">
		<form name="frmPd">			
			<!-- 상품명 -->
			<p class="line2">
				<span class="boldF">
					상품명 출력
				</span>
			</p>
			<p class="line"><span class="sp1"><img src="../../images/dot2.JPG"> 판매가격</span>				
				<span>가격 출력 </span>
			</p>
			<p class="line"><span class="sp1"><img src="../../images/dot2.JPG"> 적립금</span>
				<span>적립금 출력</span>
			</p>
			<p class="line"><span class="sp1"><img src="../../images/dot2.JPG"> 제조사</span>
				<span>제조사 출력</span>
			</p>
		
			<p class="line"><span class="sp1"><img src="../../images/dot2.JPG"> 구매수량</span>
				<label for="qty"><input type="text" name="qty" id="qty" value="1" ></label>
			</p>
			<p class="center">
				<input type="button" value="바로구매" >
				<input type="button" value="장바구니담기">
			</p>
		</form>
	</div>
</div>
<div id="desc">
	<img src="../../images/dot6.JPG">
	<span style="font-size:12pt;font-weight:bold">
		상품상세정보</span>
	<br><br>
	<p>Explain</p>
	<p>Description</p>	
</div>


<%@ include file="../../inc/bottom.jsp"%>





