/**
 * member.js
 */
 
$(function(){
	var ctxPath="/herb";
	
	$('#btnChkId').click(function(){
		var id=$('#userid').val();
		
		window.open(ctxPath+"/member/checkUserid?userid="+id,"idcheck",
	"width=400,height=350,location=yes,resizable=yes,top=100,left=50");	
	});
	
	$('#btnZipcode').click(function(){
		open(ctxPath + "/zipcode/zipcode","zip",
	"width=500,height=500,location=yes,resizable=yes,top=100,left=50");
	});
	
	$('#email2').change(function(){
        if($(this).val() == "etc"){
        	$('#email3').val("");	
           	$('#email3').css("visibility", "visible");
           	$('#email3').focus();
        } else {
           	$('#email3').css("visibility", "hidden");
        }
     });	
});


function validate_userid(id){
	var pattern = new RegExp(/^[a-zA-Z0-9_]+$/g);
	return pattern.test(id);
	/*
	정규식 /^[a-zA-Z0-9_]+$/g
	a에서 z 사이의 문자, A~Z사이의 문자, 0 에서 9사이의 숫자나 _로 시작하거나 끝나야 한다는 의미
	닫기 대괄호(]) 뒤의 + 기호는 이 패턴이 한 번 또는 그 이상 반복된다는 의미
	*/
}

function validate_tel(tel){
	var pattern = new RegExp(/^[0-9]*$/g);
	return pattern.test(tel); ; //정규식과 일치하면 true
	/* 정규식 /^[0-9]*$/g
	0 에서 9사이의 숫자로 시작하거나 끝나야 한다는 의미 (^는 시작, $는 끝을 의미)
	닫기 대괄호(]) 뒤의 * 기호는 0번 이상 반복
	*/
}