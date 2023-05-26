
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<t:website>
    <html>
    <body>
    <br>
    <h1>Ordredetaljer</h1>
    <div class="orderDetails-body">
        <br>
        <div class="orderDetails">
            <table class="orderDetails-table">
                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                    <tr>
                        <th>Kunde</th>
                        <td>
                            <c:forEach var="orderUser" items="${sessionScope.allUsersList}">
                                <c:if test="${orderUser.idUser eq param.orderUserId}">
                                    ${orderUser.email}
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                </c:if>
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
    </div>
    <br>
    <button class="orderDetails-backButton" onclick="history.back()">Tilbage</button>
    <br>
    </body>
    <br>
    </html>
</t:website>