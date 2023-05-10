<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="error.jsp" isErrorPage="false" %>

<t:website>

  <jsp:body>
    <form action="ServletSignup" method="post">
      <input type="text" id="email" name="email" placeholder="Email:"/>
      <input type="text" id="address" name="address" placeholder="Address:"/>
      <input type="text" id="zipcode" name="zipcode" placeholder="Zipcode:"/>
      <input type="text" id="phoneNumber" name="phoneNumber" placeholder="Phone number:"/>
      <input type="password" id="password" name="password" placeholder="Password:"/>
      <input type="password" id="passwordCheck" name="passwordCheck" placeholder="Password:"/>
      <input type="submit"  value="sign up"/>
    </form>

    <c:if test="${not empty error}">
      <p> <c:out value="${error}" /></p>
    </c:if>
    
  </jsp:body>

</t:website>