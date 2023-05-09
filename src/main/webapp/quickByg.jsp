<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:website>
    <jsp:body>

        <div class="quickbyg-top">
            <h1>Bestil Quick-Byg tilbud på en carport med fladt tag</h1>
            <p>Her kan du bestille en carport efter egne mål.
                Når du har bestilt en carport, modtager du hurtigst muligt et tilbud samt en skitsetegning.
            </p>
        </div>

        <div class="quickbyg-body">
            <div class="carport-dropdowns">
                <h2>Carport med fladt tag</h2>

                <p>Carport bredde</p>
                <form name="antal" id="antal">
                    <select name="antal">
                        <option value="1">Vælg ting</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                    </select>
                    <br><br>
                </form>

                <p>Carport længde</p>
                <form name="antal" id="antal">
                    <select name="antal">
                        <option value="1">Vælg ting</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                    </select>
                    <br><br>
                </form>

                <p>Carport trapeztag</p>
                <form name="antal" id="antal">
                    <select name="antal">
                        <option value="1">Vælg ting</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                    </select>
                    <br><br>
                </form>

                <form action="contactInfo.jsp">
                    <input class="fortsaet-bestilling" type="submit" value="FORTSÆT BESTILLING">
                </form>

            </div>
            <div class="quickbygbillede">
                <img src="${pageContext.request.contextPath}/images/quickbygbillede.png" height="500px"/>
            </div>
        </div>
    </jsp:body>
</t:website>