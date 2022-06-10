<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
	<!-- http://localhost:9091/herb/kakaoLogin -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

    <a href="javascript:kakaoLogin();">
    	<img src="<c:url value='/images/kakao_login_medium_narrow.png'/>" alt="카카오계정 로그인" style="height: 100px;"/>
    </a>
	<script type="text/javascript" 
		src="<c:url value='/js/jquery-3.6.0.min.js'/>"></script>
    <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
    <script>
        window.Kakao.init('8dcda3f46be3a86b7823ce582dc201aa');	// 인증요청
        
        alert("인증요청");

        function kakaoLogin() {
            window.Kakao.Auth.login({	// 토큰 요청
                scope: 'profile_nickname, account_email', //동의항목 페이지에 있는 개인정보 보호 테이블의 활성화된 ID값을 넣습니다.
                success: function(response) {
                    console.log(response) // 로그인 성공하면 받아오는 데이터
                    window.Kakao.API.request({ // 사용자 정보 가져오기 
                        url: '/v2/user/me',
                        success: (res) => {
                            const kakao_account = res.kakao_account;
                            console.log(kakao_account);
                            
                            var email = kakao_account.email;
                            var name = res.properties.nickname;
                            
                            $('#frm input[name=email]').val(email);
                            alert("이메일폼에담기"+ email);
                            $('#frm input[name=name]').val(name);
                            alert("닉네임폼에담기");
                            alert("폼전송");
                            $('form[name=frm]').submit();
                        }
                    });
                    //window.location.href='/herb/kakao' //리다이렉트 되는 코드
                },
                fail: function(error) {
                    console.log(error);
                }
            });
        }
        
    	function kakaoLogout() {
        	if (!Kakao.Auth.getAccessToken()) {
    		    console.log('Not logged in.');
    		    return;
    	    }
    	    Kakao.Auth.logout(function(response) {
        		alert(response +' logout');
    		    window.location.href='/'
    	    });
    	};
    	
    	function secession() {
    		Kakao.API.request({
    	    	url: '/v1/user/unlink',
    	    	success: function(response) {
    	    		console.log(response);
    	    		//callback(); //연결끊기(탈퇴)성공시 서버에서 처리할 함수
    	    		window.location.href='/'
    	    	},
    	    	fail: function(error) {
    	    		console.log('탈퇴 미완료')
    	    		console.log(error);
    	    	},
    		});
    	};
    </script>
    
    <form name="frm" method="post" id="frm"
    	action="<c:url value='/kakaoLogin_ok'/>">
    	<input type="text" name="email" id="email"/>
    	<input type="text" name="name" id="name"/>
    </form>

</body>
</html>