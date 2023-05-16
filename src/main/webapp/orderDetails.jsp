<%--
  Created by IntelliJ IDEA.
  User: mikkel
  Date: 15/05/2023
  Time: 18.16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<t:website>

    <html>
    <head>
        <title>Ordredetaljer</title>
    </head>
    <body>


    <br>
    <br>
    <br>


    <h2>Ordredetaljer</h2>
    <div class="orderDetails-table">
    <table>
        <tr>
            <th>Order ID</th>
            <td>${param.orderId}</td>
        </tr>
        <tr>
            <th>Status</th>
            <td style="color:${param.status == 2 ? 'green' : 'red'}"><c:choose>
                <c:when test="${param.status eq 0}">
                    AFVENTER
                </c:when>
                <c:when test="${param.status eq 1}">
                    AFVIST
                </c:when>
                <c:when test="${param.status eq 2}">
                    GODKENDT
                </c:when>
            </c:choose>
            </td>
        </tr>

        <tr>
            <th>Længde</th>
            <td>${param.length}</td>
        </tr>
        <tr>
            <th>Brede</th>
            <td>${param.width}</td>
        </tr>
        <tr>
            <th>Pris</th>
            <td>${param.totalPrice}</td>
        </tr>
        <tr>
            <th>Ordredato</th>
            <td>${param.date}</td>
        </tr>

    </table>
    </div>

    <button onclick="history.back()">Tilbage</button>
    </body>

    <br>
    <br>
    <br>
    </html>
</t:website>