package com.ez.herb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	private static final Logger logger
		= LoggerFactory.getLogger(IndexController.class);
	
	
	@RequestMapping("/")
	public String index() {
		logger.info("index 페이지");
		
		return "/index";
	}
	
	@RequestMapping("/kakaoLogin")
	public String kakaoLogin() {
		logger.info("카카오 로그인 페이지");
		
		return "/kakaoLogin";
	}
	
	@PostMapping("/kakaoLogin_ok")
	public String kakaoLogin_ok(@RequestParam String email,
			@RequestParam String name) {
		logger.info("카카오 로그인 처리 {}, {}", email, name);
		
		System.out.println(email + ", " + name);
		
		return "/index";
	}
	
	/*
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(@RequestParam String code) {	
		//@ResponseBody를 붙이면 : Data를 리턴해주는 컨트롤러 함수가 되는것이다.
		
		// POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
		// 이때 필요한 라이브러리가 RestTemplate라이브러리
		// 비슷한거로 Retrofit2라이브러리가 있는데, 안드로이드 쪽에서 많이쓰임
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		//=> 내가 전송할 http body데이터가 key=value의 형태라고 header에 알려주는것
		
		//body데이터를 담을 객체 생성, HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "a490ffff9dff2d6b129601d5fa0b211d");	// 이렇게 자주쓰는거 변수화하는게 좋음
		params.add("redirect_uri", "http://localhost:9091/auth/kakao/callback");
		params.add("code", code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest 
			= new HttpEntity<>(params, headers);	// body(params)값과 헤더값을 가진 엔티티를 만듬
		
		//Http 요청하기 - POST방식, response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",	//토큰발급 요청주소
				HttpMethod.POST,	//요청 메소드
				kakaoTokenRequest,	//body데이터, http헤더값
				String.class		//응답을 받을 타입
		);
		
		//Gson, Json Simple, ObjectMapper (json객체를 다루는 라이브러리들)
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {	// 파싱할때 이름 틀리면 오류남.
			e.printStackTrace();
		} catch (JsonProcessingException e) {	// @Data안붙이면 또 오류남.
			e.printStackTrace();
		}
		
		
		return response.getBody();
	}
	
	@RequestMapping("/kakao")
	public Map<String, Object> getUserInfo(String access_token) throws Exception {
        String host = "https://kapi.kakao.com/v2/user/me";
        Map<String, Object> result = new HashMap<>();
        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);


            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while((line=br.readLine())!=null)
            {
                res+=line;
            }

            System.out.println("res = " + res);


            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);
            JSONObject kakao_account = (JSONObject) obj.get("kakao_account");
            JSONObject properties = (JSONObject) obj.get("properties");


            //String id = obj.get("id").toString();
            String nickname = kakao_account.get("nickname").toString();
            //String age_range = kakao_account.get("age_range").toString();
            String email = kakao_account.get("email").toString();

            //result.put("id", id);
            result.put("nickname", nickname);
            //result.put("age_range", age_range);
            result.put("email", email);

            br.close();
            
            logger.info("{}, {}", email, nickname);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
	*/
	
	
	
	
	@PostMapping("/게시판어쩌구")
	public String board(@RequestParam String userCode,
			@RequestParam String boardCategory) {
		
		
		
		return "/index";
	}
	
	
	
	
	
	
	
	
}
