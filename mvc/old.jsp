<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<jsp:useBean id="person" type="lab.mvc.model.Person" scope="request" />
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Born before 1990</title>
</head>
<body>
<main>
  <h1>Welcome to the "born before 1990" page</h1>
  <p class="msg">You were born before 1990.</p>
  <table>
    <tr><th>First name</th><td><jsp:getProperty name="person" property="firstName" /></td></tr>
    <tr><th>Last name</th><td><jsp:getProperty name="person" property="lastName" /></td></tr>
    <tr><th>Birth date</th><td><jsp:getProperty name="person" property="birthDay" /></td></tr>
    <tr><th>Sex</th><td><jsp:getProperty name="person" property="sex" /></td></tr>
  </table>
  <p><a href="${pageContext.request.contextPath}/mvc/index.html">Back to the form</a></p>
</main>
</body>
</html>
