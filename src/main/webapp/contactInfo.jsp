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
            <c:if test="${not empty requestScope.Fejl}">
                <!-- Show the error div with message-->
               <div class="contacterrormsg">
                      <i> ${requestScope.Fejl} </i>
               </div>

            </c:if>
        </div>
        <div class="kontaktinfo">
            <form action="ServletContactinfo" method="post">
                <div class="kontaktinfo-input">
                    <label for="name">Navn: </label>
                    <input type="text" id="name" name="name"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="adress">Adresse: </label>
                    <input type="text" id="adress" name="adress"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="zipcode">Postnummer: </label>
                    <input type="text" id="zipcode" name="zipcode"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="by">By: </label>
                    <input type="by" id="by" name="by"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="phoneNumber">Telefon: </label>
                    <input type="text" id="phoneNumber" name="phoneNumber"/>
                </div>
                <div class="kontaktinfo-input">
                <br/><br/>
                    <p>
                        Hvis du ikke har en konto, så bruges email og password til at lave en konto.
                        Hvis du har en konto, bliver det brugt til at behandle ordren via din konto.
                    </p>
                    <br/>
                    <label for="email">Email: </label>
                    <input type="text" id="email" name="email"/>
                </div>
                <div class="kontaktinfo-input">
                    <label for="password">Kodeord: </label>
                    <input type="password" id="password" name="password"/>
                    <label for="passwordCheck">Kodeord igen: </label>
                    <input type="password" id="passwordCheck" name="passwordCheck"/>
                </div>
                <c:if test="${not empty requestScope.error}">
                    <!-- Show the error div with message-->
                    <div> <p> Fejl! alle felter skal udfyldes.</p></div>
                </c:if>
                <button type="submit" class="fortsaet-bestilling">Bestil</button>

            </form>

        </div>


    </jsp:body>

</t:website>