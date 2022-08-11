let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});
	},

	save: function() {
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
		}).done(function(resp) {
			// 성공했을 시
			alert("글쓰기가 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			// 실패했을 시
			alert(JSON.stringify(error));
		});
	},

	deleteById: function() {
		let id = $("#id").text();

		$.ajax({
			// 회원가입 수행요청
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json"
		}).done(function(resp) {
			// 성공했을 시
			alert("삭제가 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			// 실패했을 시
			alert(JSON.stringify(error));
		});
	},
	
	update: function() {
		let id = $("#id").val();
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};

		// ajax호출 시 default가 비동기 호출
		$.ajax({
			// 회원가입 수행요청
			type: "PUT",
			url: "/api/board/"+id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			// 성공했을 시
			alert("글수정 완료되었습니다.");
			console.log(resp);
			location.href = "/";
		}).fail(function(error) {
			// 실패했을 시
			alert(JSON.stringify(error));
		});
	},
	
	replySave: function() {
		let data = {
			content: $("#reply-content").val(),
		};
		let boardId = $("#boardId").val();
		let urll= `/api/board/${boardId}/reply`;
		console.log(urll);
		// ajax호출 시 default가 비동기 호출
		$.ajax({
			// 회원가입 수행요청
			type: "POST",
			url: `/api/board/${boardId}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp) {
			// 성공했을 시
			alert("댓글작성 완료되었습니다.");
			console.log(resp);
			location.href = `/board/${boardId}`;
		}).fail(function(error) {
			// 실패했을 시
			alert(JSON.stringify(error));
		});
	},
}

index.init();