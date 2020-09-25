<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajax(js) Ex Main Page</title>
</head>
<body>
<div>
    <h1>Ajax 예제 메인페이지</h1>
</div>
<div>

    <h2>GET Request</h2>
    <form>
        <label>ID : </label>
        <input id="id" type="text">
    </form>
    <h3><button id="getInfo" onclick="javascript:getInfo();">멤버 정보 보기(JS)</button> </h3>
    
</div>
<div>
    <h2>POST Request</h2>
    <form>
        <label>Name : </label>
        <input id="name" type="text"><br>
        <label>Price : </label>
        <input id="price" type="text"><br>
        <label>ImgPath : </label>
        <input id="imgpath" type="text"><br>
    </form>
    <h3><button id="postInfo" onclick="javascript:postInfo();">멤버 등록 하기(JS)</button> </h3>
</div>
<div>
    <h2>PUT Request</h2>
    <form>
        <label>ID : </label>
        <input id="u_id" type="text"><br>
        <label>Name : </label>
        <input id="u_name" type="text"><br>
        <label>Price : </label>
        <input id="u_price" type="text"><br>
        <label>ImgPath : </label>
        <input id="u_imgpath" type="text"><br>
    </form>
    <h3><button id="putInfo" onclick="javascript:putInfo();">멤버 정보 수정 (JS)</button> </h3>
</div>
<div>
    <h2>DELETE Request</h2>
    <form>
        <label>ID : </label>
        <input id="d_id" type="text"><br>
    </form>
    <h3><button id="deleteInfo" onclick="javascript:deleteInfo();">멤버 정보 수정 (JS)</button> </h3>
</div>



</body>
<script type="text/javascript">

var url = "http://localhost:8080/api/items"

function getInfo() {
	var reg = new RegExp('^[0-9]+');
	
	id = document.getElementById("id").value;
	if (!reg.test(id)) {
		alert("please input only number type");
		return
	}
	
	var xhreq = new XMLHttpRequest();
	
	xhreq.onreadystatechange = function() {
		if (xhreq.readyState === xhreq.DONE) {
			if (xhreq.status === 200 || xhreq.status === 201 ) {
				console.log(xhreq.responseText);
				var responseJson = JSON.parse(xhreq.responseText);
				console.log(responseJson);
				
			} else {
				console.error(xhreq.responseText);
			}
		}
	}
	xhreq.open("GET", url, true);
	xhreq.send();
}


function postInfo() {
    nameVal = document.getElementById("name").value;
	priceVal = document.getElementById("price").value;
	imgpathVal = document.getElementById("imgpath").value;
	
	var reqData = {
		name : nameVal,
		price : priceVal,
		imgPath : imgpathVal
	};
	
	console.log(reqData);
	
	var xhreq = new XMLHttpRequest();
	
	//xhreq.onreadystatechange = function() {
	xhreq.load = function() {
		if (xhreq.status === 200 || xhreq.status === 201 ) {
			console.log(xhreq.responseText);
			var responseJson = JSON.parse(xhreq.responseText);
			console.log(responseJson);
			
		} else {
			console.error(xhreq.responseText);
		}

	}
	xhreq.open("POST", url, true);
	xhreq.setRequestHeader("Content-Type", "application/json");
	xhreq.send(JSON.stringify(reqData));
}
	
function putInfo() {
	idVal = document.getElementById("u_id").value;
	nameVal = document.getElementById("u_name").value;
	priceVal = document.getElementById("u_price").value;
	imgpathVal = document.getElementById("u_imgpath").value;
	
	var reqData = {
		id : idVal,
		name : nameVal,
		price : priceVal,
		imgPath : imgpathVal
	};
	
	console.log(reqData);
	
	var xhreq = new XMLHttpRequest();
	
	xhreq.onreadystatechange = function() {
		if (xhreq.readyState === xhreq.DONE) {
		    if (xhreq.status === 200 || xhreq.status === 201 ) {
			    console.log(xhreq.responseText);
			    var responseJson = JSON.parse(xhreq.responseText);
			    console.log(responseJson);
			
		    } else {
			    console.error(xhreq.responseText);
		    }
		}
	}
	xhreq.open("PUT", url+"/"+idVal, true);
	xhreq.setRequestHeader("Content-Type", "application/json");
	xhreq.send(JSON.stringify(reqData));
}

function deleteInfo() {
	var reg = new RegExp('^[0-9]+');
	
	id = document.getElementById("d_id").value;
	if (!reg.test(id)) {
		alert("please input only number type");
		return
	}
	
	var xhreq = new XMLHttpRequest();
	
	xhreq.onreadystatechange = function() {
		if (xhreq.readyState === xhreq.DONE) {
			if (xhreq.status === 200 || xhreq.status === 201 ) {
				console.log("Delete Success");
				
			} else {
				console.error("Delete Failure");
			}
		}
	}
	xhreq.open("DELETE", url+"/"+id, true);
	xhreq.send();
}

</script>
</html>