let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
		$("#btn-update").on("click", ()=>{
			this.update();
		});
	},
	
	
	save: function(){
		// alert('user의 save함수 실행됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		// ajax호출 시 default가 비동기 호출
		$.ajax({
			// 회원가입 수행요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청에 대한 응답 -> 기본적으로 스트링 (생긴게 json이라면)-> javascript object로 바꿔줌
		}).done(function(resp){
			if(resp.status == 500){
				alert("아이디가 중복된 회원이 존재합니다.")
			}else{
				alert("회원가입이 완료되었습니다.");
				location.href = "/";
			}
			
		}).fail(function(error){
			// 실패했을 시
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청 
	},
	
	update: function(){
		// alert('user의 save함수 실행됨');
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		$.ajax({
			// 회원 수정 요청
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청에 대한 응답 -> 기본적으로 스트링 (생긴게 json이라면)-> javascript object로 바꿔줌
		}).done(function(resp){
			// 성공했을 시
			alert("회원수정이 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){
			// 실패했을 시
			alert(JSON.stringify(error));
		}); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청 
	}
	
}

index.init();