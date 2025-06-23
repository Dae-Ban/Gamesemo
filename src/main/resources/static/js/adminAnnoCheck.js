function admin_check() {
	if($.trim($("#anTitle").val()) == "") {
		alert("제목을 입력하세요");
		$("#anTitle").val("").focus();
		return false;
	}
	if($.trim($("#anContent").val()) == "") {
		alert("내용을 입력하세요");
		$("#anContent").val("").focus();
		return false;
	}
}