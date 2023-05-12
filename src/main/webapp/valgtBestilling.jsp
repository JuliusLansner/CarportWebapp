<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<!-- This needs to show the chosen carport with the width and length of the users choice
as of right now this is impossible to make as the information is unavaiable to be had,
however this will be a demonstration -->
<t:website>


    <jsp:body>
        <div class="quickbyg-top">
            <h1>Bestil Quick-Byg tilbud på en carport med fladt tag</h1>
            <p>Tak for din bestilling!
                Du modtager dit tilbud samt en stykliste via din brugerside snarest.
            </p>


            <p>Du har bestilt følgende:</p>
            ${sessionScope.height}
            ${sessionScope.length}
            ${sessionScope.roof}
            <p></p>
        </div>

    </jsp:body>

</t:website>