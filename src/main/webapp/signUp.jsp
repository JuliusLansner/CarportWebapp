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
          <label for="email">email: </label>
          <input type="text" id="email" name="email"/>


          <label for="password">Kodeord: </label>
          <input type="password" id="password" name="password"/>

          <label for="password">Kodeord igen: </label>
          <input type="password" id="password2" name="password"/>

          <label for="address">Adresse: </label>
          <input type="text" id="address" name="adress"/>

          <label for="phoneNumber">Nummer: </label>
          <input type="text" id="phoneNumber" name="phoneNumber"/>

          <label for="zipcode">Postnr: </label>
          <input type="text" id="zipcode" name="zipcode"/>
        </div>

        <input class="fortsaet-bestilling" type="submit" value="OPRET BRUGER">

      </form>
  </jsp:body>

</t:website>