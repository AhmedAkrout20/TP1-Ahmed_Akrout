<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>JSP expression</title>
</head>
<body>
<main>
  <h1>JSP expression</h1>
  <%-- An expression is evaluated, converted to a String and inserted in the page --%>
  <p>A random number: <strong><%= Math.random() %></strong></p>
  <p><a href="expression.jsp">Reload</a> (the number changes at every request)</p>
  <p class="nav"><a href="index.html">&larr; Back to the lab index</a></p>
</main>
</body>
</html>
