<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:website>


    <jsp:body>
        <div id="index-body">
            <div class="welcome">
                <h1>BESTIL DIN CARPORT IDAG</h1>

                <form action="quickByg.jsp">

                    <input class="bestilcarport" type="submit" value="BESTIL CARPORT">

                </form>
            </div>
        </div>
    </jsp:body>

</t:website>