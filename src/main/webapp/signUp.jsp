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
      <form action="index.jsp" method="post">
        <div class="kontaktinfo-input">
          <label for="username">Brugernavn: </label>
          <input type="text" id="username" name="username"/>


          <label for="password">Kodeord: </label>
          <input type="password" id="password" name="password"/>

          <label for="password">Kodeord igen: </label>
          <input type="password" id="password2" name="password"/>

          <label for="adress">Adresse: </label>
          <input type="text" id="adress" name="adress"/>

          <label for="number">Nummer: </label>
          <input type="text" id="number" name="number"/>

          <label for="zipcode">Postnr: </label>
          <input type="text" id="zipcode" name="zipcode"/>
        </div>

        <input class="fortsaet-bestilling" type="submit" value="OPRET BRUGER">

      </form>
  </jsp:body>

</t:website>