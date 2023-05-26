
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
    <br>
    <br>
    <h1>Velkommen bruger</h1>
    <h3 style="margin-top: 30px;">Her kan du administrere dine bestillinger</h3>

    <div class="table1">
        <h3 style="text-align: left; font-size: 18px; font-weight: bold; margin-bottom: 20px;">Afventer godkendelse</h3>
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
                <c:forEach var="item" items="${sessionScope.userOrders}">
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
        <h3 style="text-align: left; font-size: 18px; font-weight: bold; margin-bottom: 20px;">Godkendte og afviste ordrer</h3>
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
                <c:forEach var="item" items="${sessionScope.userOrders}">
                    <c:if test="${item.userId eq sessionScope.user.idUser && item.status eq 1||item.userId eq sessionScope.user.idUser && item.status eq 2 }">
                        <tr>
                            <td class="td">
                                <a href="orderDetails.jsp?orderId=${item.orderId}&length=${item.lenght}&width=${item.width}&totalPrice=${item.totalPrice}&status=${item.status}&date=${item.date}">
                                        ${item.orderId}
                                </a>
                            </td>
                            <td style="color: ${item.status == 2 ? 'green' : 'red'}" class="td">
                                <c:choose>
                                    <c:when test="${item.status eq 1}">
                                        AFVIST
                                    </c:when>
                                    <c:when test="${item.status eq 2}">
                                        GODKENDT
                                    </c:when>
                                </c:choose>
                            </td>
                            <td class="td">${item.totalPrice}Kr.</td>
                            <td class="td">Værebro Fog</td>
                            <td class="td">${item.date}</td>
                            <td class="td">
                                <c:if test="${item.status eq 2}">
                                    <form action="ServletBuyOrder" method="post">
                                        <input type="hidden" name="orderId" value="${item.orderId}">
                                        <button class="pay-button" type="submit">Betal</button>
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
                <label for="password">Indtast dit password for at slette din konto:</label><br>
                <input type="password" id="password" name="password" required><br>
                <button type="submit">Slet min konto</button>
            </form>
        </div>
    <br>
    <br>
    <br>
    </body>
    </html>
</t:website>