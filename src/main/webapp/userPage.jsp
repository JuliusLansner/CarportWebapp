<%--
  Created by IntelliJ IDEA.
  User: malde
  Date: 5/11/2023
  Time: 11:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<t:website>
    <html>
    <head>
        <title>userpage</title>
    </head>
    <body>
    <h1>Velkommen bruger</h1>
    <h2>Her kan du administrere dine bestillinger</h2>

    <div class="userOrderTable">
        <table class="table" style="width: 100%">
            <tr>
                <th class="td">OrdreID</th>
                <th class="td">Status</th>
                <th class="td">Pris</th>
                <th class="td">Butik</th>
                <th class="td">Dato</th>
            </tr>
            <c:forEach var="item" items="${sessionScope.orderlist}">
                <c:if test="${item.userId eq sessionScope.user.idUser}">
                    <tr>
                        <td class="td">${item.orderId}</td>
                        <td class="td">
                            <c:choose>
                                <c:when test="${item.status eq 0}">
                                    AFVENTER
                                </c:when>
                                <c:when test="${item.status eq 1}">
                                    GODKENDT
                                </c:when>
                            </c:choose>
                        </td>
                        <td class="td">${item.totalPrice}Kr.</td>
                        <td class="td">Værebro Fog</td>
                        <td class="td">${item.date}</td>
                        <td class="td">
                            <c:if test="${item.status eq 1}">
                                <form action="ServletBuyOrder" method="post">
                                    <button type="submit">Betal</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </div>

    </body>
    </html>
</t:website>
