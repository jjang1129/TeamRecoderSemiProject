<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>회원 정보 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/update.css" />
</head>
<body>
	<div class="container">
		<h1>회원 정보 수정</h1>
		<form action="/update" method="post" id="form">
			<div class="form-group">
				<label>비밀번호 변경</label> 
				<p>${mem.pwd}</p>
				<span id="pwdCheck"></span> 
				<input type="password" id="pwd" name="pwd" placeholder="pwd" required >
			</div>
			<div class="form-group">
				<label>이름 변경</label> <span id="nameCheck"></span> 
				<input type="text" id="name" name="name" placeholder="name" value="${mem.name}" required/>
			</div>
			<div class="form-group">
				<label>연락처 변경</label> 
				<span id="phoneCheck"></span>
				<input type="tel" id="phone" name="phone" placeholder="phone" value="${mem.phone}" required/>
			</div>
			<div class="form-group">
				<label>변경할 주소</label>
				<div id="addrDetail-box">
					
					<input type="text" id="sample5_address" name="addr" value="${mem.addr}" placeholder="주소" required > 
					<input type="button" id="addr-btn" onclick="sample5_execDaumPostcode()" value="주소 검색"> 
					<input type="text" id="addrDetail" name="addrDetail" value = "${mem.addr}" placeholder="상세주소를 입력해주세요" >
				</div>
			</div>
			<div class="form-group">
				<label>이메일 변경</label>
				<span id="emailCheck"></span> 
				<input type="email" id="email" name="email" placeholder="email" value="${mem.email}" required/>
			</div>
			<div class="form-group">
				<label>나이 변경</label> <span id="ageCheck"></span> <input type="text"
					id="age" name="age" placeholder="age" value="${mem.age}"required />
			</div>
			<button type="submit">제출</button>
		</form>
	</div>

	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="${pageContext.request.contextPath}/js/update.js"></script>
</body>
</html>
<!-- 
<%-- 주소를 분리하는 예제 --%>
<c:set var="addressParts" value="${fn:split(member.addr, '#')}" />
<c:choose>
    <c:when test="${fn:length(addressParts) == 2}">
        <p>일반 주소: ${addressParts[0]}</p>
        <p>상세 주소: ${addressParts[1]}</p>
    </c:when>
    <c:otherwise>
        <p>주소: ${member.addr}</p>
    </c:otherwise>
</c:choose>

 -->












