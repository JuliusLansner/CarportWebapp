<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:website>
     <jsp:attribute name="header">

    </jsp:attribute>
    <jsp:body>

        <div class="quickbyg-top">
            <h1>Bestil Quick-Byg tilbud på en carport med fladt tag</h1>
            <p>Her kan du bestille en carport efter egne mål.
                Når du har bestilt en carport, modtager du hurtigst muligt et tilbud samt en stykliste.
            </p>
        </div>

        <div class="quickbyg-body">
            <div class="carport-dropdowns">
                <h2>Carport med fladt tag</h2>

                <p>Carport bredde</p>
                <form name="wlr" action="ServletQuickbyg" method="post" id="widthlengthroof">
                    <select class="quickbyg-select" name="width">
                        <option value="240">240cm</option>
                        <option value="270">270cm</option>
                        <option value="400">400cm</option>
                        <option value="600">600cm</option>
                    </select>
                    <br><br>


                <p>Carport længde</p>

                    <select class="quickbyg-select" name="length">
                        <option value="240">240cm</option>
                        <option value="270">270cm</option>
                        <option value="400">400cm</option>
                        <option value="600">600cm</option>
                    </select>
                    <br><br>
                    <button type="submit" class="fortsaet-bestilling">Fortsæt bestilling</button>
                </form>

            </div>
            <div class="quickbygbillede">
                <img src="${pageContext.request.contextPath}/images/quickbygbillede.png" height="500px"/>
            </div>
        </div>
    </jsp:body>
</t:website>