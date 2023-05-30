<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:website>
    <jsp:attribute name="header">
        <h1 class="login-header">Login</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
            Login
    </jsp:attribute>

    <jsp:body>

        <h3 class="login-text">Du kan logge ind her</h3>

        <form class="login-form" action="login" method="post">
            <input class="login-input" type="text" id="email" name="email" placeholder="Email:"/>
            <input class="login-input" type="password" id="password" name="password" placeholder="Password"/>
            <input class="login-button" type="submit"  value="Log ind"/>
        </form>

    </jsp:body>
</t:website>