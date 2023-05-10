<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:website>
    <jsp:body>
        <div class="quickbyg-top">
            <h1>Bestil Quick-Byg tilbud på en carport med fladt tag</h1>
            <p>Her kan du indtaste dine kontakt oplysninger.
                Alle felter skal udfyldes.
                Hvis du ikke har en konto, så bruges email og password til at lave en konto.
                Hvis du har en konto, bliver det brugt til at behandle ordren via din konto.
            </p>
        </div>
        <div class="kontaktinfo">
            <form action="valgtBestilling.jsp" method="post">
                <label for="name">Navn: </label>
                <input type="text" id="name" name="name"/>
                <label for="adress">Adresse: </label>
                <input type="text" id="adress" name="adress"/>
                <label for="postcode">Postnummer: </label>
                <input type="text" id="postcode" name="postcode"/>
                <label for="by">By: </label>
                <input type="by" id="by" name="by"/>
                <label for="telefon">Telefon: </label>
                <input type="text" id="telefon" name="telefon"/>
                <br/>
                <label for="username">Brugernavn: </label>
                <input type="text" id="username" name="username"/>
                <label for="password">Kodeord: </label>
                <input type="password" id="password" name="password"/>


                <input class="fortsaet-bestilling" type="submit" value="BESTIL">

            </form>
        </div>


    </jsp:body>

</t:website>