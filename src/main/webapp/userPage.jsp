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

    <div class="table1">
        <div class="tableTitles">
            <table class="titles">
                <tr>
                    <th class="td">OrdreID</th>
                    <th class="td">Status</th>
                    <th class="td">Pris</th>
                    <th class="td">Butik</th>
                    <th class="td">Dato</th>
                    <th class="td">Sælger</th>
                </tr>
            </table>
        </div>

        <div class="userOrderTable">
            <table class="table" style="width: 100%">
                <c:forEach var="item" items="${sessionScope.orderlist}">
                    <c:if test="${item.userId eq sessionScope.user.idUser && item.status eq 0}">
                        <tr>
                            <td class="td">
                                <a href="orderDetails.jsp?orderId=${item.orderId}&length=${item.lenght}&width=${item.width}&totalPrice=${item.totalPrice}&status=${item.status}&date=${item.date}">
                                        ${item.orderId}
                                </a>
                            </td>
                            <td style="color: ${item.status == 0 ? 'red' : 'green'}" class="td">
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
                            <td class="td">Martin</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </div>
    </div>

    <div class="table2">
        <div class="tableTitles">
            <table class="titles">
                <tr>
                    <th class="td">OrdreID</th>
                    <th class="td">Status</th>
                    <th class="td">Pris</th>
                    <th class="td">Butik</th>
                    <th class="td">Dato</th>
                    <th class="td">Betaling</th>
                </tr>
            </table>
        </div>

        <div class="userOrderTable">
            <table class="table" style="width: 100%">
                <c:forEach var="item" items="${sessionScope.orderlist}">
                    <c:if test="${item.userId eq sessionScope.user.idUser && item.status eq 1||item.userId eq sessionScope.user.idUser && item.status eq 2 }">
                        <tr>
                            <td class="td">
                                <a href="orderDetails.jsp?orderId=${item.orderId}&length=${item.lenght}&width=${item.width}&totalPrice=${item.totalPrice}&status=${item.status}&date=${item.date}">
                                        ${item.orderId}
                                </a>
                            </td>
                            <td style="color: ${item.status == 2 ? 'red' : 'green'}" class="td">
                                <c:choose>
                                    <c:when test="${item.status eq 2}">
                                        AFVIST
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
    </div>

    <br>
    <br>

    <div class="deleteOwnUserButton">
        <form action="ServletUserPage" method="post">
            <input type="hidden" name="deleteOwnAccount" value="true">
            <input type="hidden" id="email" name="email" value="${sessionScope.user.email}">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <button type="submit">Slet min konto</button>
        </form>
    </div>


    <br>
    <br>
    <br>
    </body>
    </html>
</t:website>
