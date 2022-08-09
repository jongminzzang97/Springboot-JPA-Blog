let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
	},
	
	save: function(){
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};
		
		// ajax호출 시 default가 비동기 호출
		$.ajax({
			// 회원가입 수행요청
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json" 
		}).done(function(resp){
			// 성공했을 시
			alert("글쓰기가 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error){
			// 실패했을 시
			alert(JSON.stringify(error));
		}); 
	}
	
}

index.init();