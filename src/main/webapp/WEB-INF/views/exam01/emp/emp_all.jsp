<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<%-- header --%>
<jsp:include page="../../common/header.jsp"/>

<div class="container">
    <%-- TODO : 검색어 시작 --%>
    <%--    form 안에 input 태그의 value 값들이 -> Springboot 함수로 전달됨 --%>
    <form class="row g-3 justify-content-center" action="/exam01/emp" method="get">
        <div class="col-auto">
            <%--            todo : 부서명 라벨 --%>
            <label for="ename" class="visually-hidden">Ename</label>
            <%--            todo : 검색창 --%>
            <input type="text" class="form-control" id="ename" placeholder="ename" name="ename">
            <%--            todo : hidden(숨김) page = 0, size = 3 --%>
            <input type="hidden" class="form-control" id="page" name="page" value="0">
            <input type="hidden" class="form-control" id="size" name="size" value="3">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-3">Search</button>
        </div>
    </form>
    <%-- TODO : 검색어 끝--%>

    <%-- TODO : 테이블 반복문 시작--%>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ENO</th>
            <th scope="col">Name</th>
            <th scope="col">JOB</th>
            <th scope="col">MANAGER</th>
            <th scope="col">HIREDATE</th>
            <th scope="col">SALARY</th>
            <th scope="col">COMMISSION</th>
            <th scope="col">추가일자</th>
            <th scope="col">수정일자</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="data" items="${emp}">
            <tr>
                <td><a href="/exam01/emp/edition/${data.eno}">${data.eno}</a></td>
                <td>${data.ename}</td>
                <td>${data.job}</td>
                <td>${data.manager}</td>
                <td>${data.hiredate}</td>
                <td>${data.salary}</td>
                <td>${data.commission}</td>
                <td>${data.insertTime}</td>
                <td>${data.updateTime}</td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
    <%-- TODO : 테이블 반복문 끝--%>

    <%-- TODO : 페이지 번호 시작 --%>
    <div class="d-flex justify-content-center">
        <ul class="pagination">
            <%--            todo : 첫페이지 번호 --%>
            <%--             startpage : 1부터 시작 --%>
            <%--             currentPage : 0부터 시작 --%>
            <li class="page-item ${(startPage==1)? 'disabled': ''}">
                <a class="page-link" href="/exam01/emp?page=${startPage-2}&size=${3}">Previous</a>
            </li>
            <%--            todo : 실제 페이지 번호들 --%>
            <%--                사용법 : <c:forEach var="data" begin="${시작값}" end="${끝값}">반복문</c:forEach> --%>
            <c:forEach var="data" begin="${startPage}" end="${endPage}">
                <li class="page-item ${(currentPage+1==data)? 'active': ''}">
                    <a class="page-link" href="/exam01/emp?page=${data-1}&size=${3}">
                            ${data}
                    </a>
                </li>
            </c:forEach>
            <li class="page-item ${(endPage==totalPages)? 'disabled': ''}">
                <a class="page-link" href="/exam01/emp?page=${endPage}&size=${3}">Next</a>
            </li>
        </ul>
    </div>
    <%-- TODO : 페이지 번호 끝--%>

        <%-- todo : add 버튼 추가 --%>
        <div class="text-center">
            <a href="/exam01/emp/addition" class="btn btn-primary center">Add</a>
        </div>
</div>

<script>
    let obj = "${emp}";
    let obj2 = "${currentPage}";
    let obj3 = "${totalItems}";
    let obj4 = "${totalPages}";
    let obj5 = "${startPage}";
    let obj6 = "${endPage}";
    console.log("obj", obj);
    console.log("obj2", obj2);
    console.log("obj3", obj3);
    console.log("obj4", obj4);
    console.log("obj5", obj5);
    console.log("obj6", obj6);
</script>

<%-- footer --%>
<jsp:include page="../../common/footer.jsp"/>
</body>
</html>