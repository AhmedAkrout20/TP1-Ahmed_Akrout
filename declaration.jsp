<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>JSP declaration</title>
</head>
<body>
<main>
<%! private int numEntries = 10;
    private int randomInt(int range) {
        return(1 + ((int)(Math.random() * range)));
    } %>
<h1>A random list from 1 to 100:</h1>
<ul>
<% for(int i=0; i<numEntries; i++) {
    out.println("<li>" + randomInt(100));
} %>
</ul>
  <p class="nav"><a href="index.html">&larr; Back to the lab index</a></p>
</main>
</body>
</html>
