<%--
  Created by IntelliJ IDEA.
  User: mikkel
  Date: 10/05/2023
  Time: 17.12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:website>
    <html>
    <body>

    <br>
    <br>

    <div id="admin-welcome" class="admin-welcome">
        <h1>Velkommen ${sessionScope.user.email}</h1>
    </div>

    <br>

    <div id="order-overview" class="order-overview">
        <h2>Her kan du administrere ordrer</h2>

        <br>
        <br>

        <table id="order-table" class="order-table">
            <thead>
            <tr>
                <th>Ordre-id</th>
                <th>Status</th>
                <th>Pris</th>
                <th>Butik</th>
                <th>Dato</th>
                <th>Godkend</th>
                <th>Afvis</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${sessionScope.userOrders}">
                <tr>
                    <td>${item.orderId}</td>
                    <td style="color:${item.status == 0 ? 'red' : 'green'}"><c:choose>
                        <c:when test="${item.status eq 0}">
                            AFVENTER
                        </c:when>
                        <c:when test="${item.status eq 1}">
                            GODKENDT
                        </c:when>
                    </c:choose>
                    </td>
                    <td>${item.totalPrice}</td>
                    <td>Værebro fog</td>
                    <td>${item.date}</td>
                    <td>
                        <button type="button" class="approve-button">Godkend</button>
                    </td>
                    <td>
                        <button type="button" class="declined-button">Afvis</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <br>
    <br>

    <div id="price-overview" class="price-overview">
        <h2>Her kan du administrere priser</h2>

        <br>
        <br>

        <table id="price-table" class="price-table">
            <thead>
            <tr>
                <th>Vare-id</th>
                <th>Nuværende pris pr. enhed</th>
                <th>Fremtidige pris pr. enhed</th>
                <th>Butik</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="material" items="${sessionScope.materialList}">
                <tr>
                    <td>${material.idMaterial}</td>
                    <td>${material.pricePerUnit}</td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/ServletAdminPage">
                            <input type="hidden" name="materialId" value="${material.idMaterial}">
                            <input type="number" name="updatedPricePrUnit" style="width: 80px">
                            <button type="submit" class="priceApprove-button">Godkend</button>
                        </form>
                    </td>
                    <td>Værebro fog</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


    </body>

    </html>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
</t:website>