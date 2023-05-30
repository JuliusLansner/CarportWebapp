<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:website>
    <jsp:attribute name="header">
        <h1 class="login-header">Velkommen</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
        Logged in area
    </jsp:attribute>

    <jsp:body>

        <p>Du er nu logget ind</p>

        <c:if test="${sessionScope.user != null}">
            <p style="padding-bottom: 100px;">Du er logget ind med rollen "${sessionScope.user.role}".</p>
        </c:if>

        <c:if test="${sessionScope.user == null}">
            <p>Du er ikke logget ind endnu. Du kan logge ind her: <a
                    href="../login.jsp">Login</a></p>
        </c:if>

    </jsp:body>

</t:website>