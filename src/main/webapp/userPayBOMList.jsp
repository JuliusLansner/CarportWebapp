
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<t:website>
    <html>
    <body>
    <br>
    <br>
    <h1>Her kan du se din stykliste</h1>
    <br>
    <br>
    <table class="userPayBOMList">
        <thead>
        <tr>
            <th>Materiale</th>
            <th>Længde</th>
            <th>Enhed</th>
            <th>Beskrivelse</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${sessionScope.bomList}">
            <c:forEach var="materialVariant" items="${sessionScope.materialVariantList}">
                    <c:if test="${materialVariant.partslistID eq item.id}">
                        <tr>
                            <td>
                                <c:if test="${materialVariant.materialeID eq 1}">
                                    45x195 mm. spærtræ ubh.
                                </c:if>
                                <c:if test="${materialVariant.materialeID eq 2}">
                                    97x97 mm. trykimp Stolpe
                                </c:if>
                            </td>
                            <td>${materialVariant.length}</td>
                            <td>cm</td>
                            <td>${materialVariant.description}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <br>
    <button class="orderDetails-backButton" onclick="history.back()">Tilbage</button>
    <br>
    <br>
    </body>
    </html>
</t:website>