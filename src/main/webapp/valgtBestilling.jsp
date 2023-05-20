<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
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
            <p> En Carport, med:</p>
                Bredde:
                ${sessionScope.width}<br><br>
                Længde:
                ${sessionScope.length}<br><br>

            <p>Din bestilling skal godkendes af os. Når den er blevet godkendt, kan du se det på din bruger side.</p><br><br>
            <p>Din bruger side finder du oppe i navigationsbaren, hvor du blot skal logge ind og derefter skal du clicke på "Bruger side".</p>

        </div>

    </jsp:body>

</t:website>