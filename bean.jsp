<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<% request.setCharacterEncoding("UTF-8"); /* must run before any parameter is read */ %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Person (JSP + JavaBean)</title>
</head>
<body>
<main>
  <h1>Person saved in a JavaBean</h1>

  <%-- 1. create (or find) the bean in the request scope --%>
  <jsp:useBean id="person" class="isep.lab1.bean.Person" scope="request" />

  <%-- 2. copy every request parameter whose name matches a bean property --%>
  <jsp:setProperty name="person" property="*" />

  <%-- 3. read the values back from the bean --%>
  <table>
    <tr><th>First name</th><td><jsp:getProperty name="person" property="firstName" /></td></tr>
    <tr><th>Last name</th><td><jsp:getProperty name="person" property="lastName" /></td></tr>
    <tr><th>Birth date</th><td><jsp:getProperty name="person" property="birthDay" /></td></tr>
    <tr><th>Sex</th><td><jsp:getProperty name="person" property="sex" /></td></tr>
  </table>

  <p><a href="form-jsp.html">Back to the form</a></p>
  <p class="nav"><a href="index.html">&larr; Back to the lab index</a></p>
</main>
</body>
</html>
