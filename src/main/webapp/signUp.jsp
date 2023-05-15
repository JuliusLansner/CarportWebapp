<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:website>

  <jsp:body>
    <div class="quickbyg-top">
      <h1>Her kan du oprette en bruger</h1>
      <p class="quickbyg-top-text">

      </p>
    </div>
      <form action="ServletSignup" method="post">
        <div class="kontaktinfo-input">
          <label for="username">email: </label>
          <input type="text" id="username" name="username"/>


          <label for="password">Kodeord: </label>
          <input type="password" id="password" name="password"/>

          <label for="password">Kodeord igen: </label>
          <input type="password" id="password2" name="password"/>

          <label for="adress">Adresse: </label>
          <input type="text" id="adress" name="adress"/>

          <label for="phoneNumber">Nummer: </label>
          <input type="text" id="phoneNumber" name="phoneNumber"/>

          <label for="zipcode">Postnr: </label>
          <input type="text" id="zipcode" name="zipcode"/>
        </div>

        <input class="fortsaet-bestilling" type="submit" value="OPRET BRUGER">

      </form>

    <c:if test="${not empty error}">
      <p> <c:out value="${error}" /></p>
    </c:if>


  </jsp:body>

</t:website>