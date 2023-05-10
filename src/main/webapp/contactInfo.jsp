<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:website>
    <jsp:body>
        <div class="quickbyg-top">
            <h1>Bestil Quick-Byg tilbud på en carport med fladt tag</h1>
            <p class="quickbyg-top-text">Her kan du indtaste dine kontakt oplysninger.
                Alle felter skal udfyldes.
                Hvis du ikke har en konto, så bruges email og password til at lave en konto.
                Hvis du har en konto, bliver det brugt til at behandle ordren via din konto.
            </p>
        </div>
        <div class="kontaktinfo">
            <form action="valgtBestilling.jsp" method="post">
                <div class="kontaktinfo-input">
                    <label for="name">Navn: </label>
                    <input type="text" id="name" name="name"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="adress">Adresse: </label>
                    <input type="text" id="adress" name="adress"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="postcode">Postnummer: </label>
                    <input type="text" id="postcode" name="postcode"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="by">By: </label>
                    <input type="by" id="by" name="by"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="telefon">Telefon: </label>
                    <input type="text" id="telefon" name="telefon"/>
                </div>
                <div class="kontaktinfo-input">
                <br/><br/>
                    <p>
                        Hvis du ikke har en konto, så bruges email og password til at lave en konto.
                        Hvis du har en konto, bliver det brugt til at behandle ordren via din konto.
                    </p>
                    <br/>
                    <label for="username">Brugernavn: </label>
                    <input type="text" id="username" name="username"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="password">Kodeord: </label>
                    <input type="password" id="password" name="password"/>
                </div>

                <input class="fortsaet-bestilling" type="submit" value="BESTIL">

            </form>
        </div>


    </jsp:body>

</t:website>