<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>JSP scriptlet</title>
</head>
<body>
<main>
  <h1>JSP scriptlet</h1>
  <%
      // A scriptlet is plain Java code executed inside _jspService()
      out.println("<p>Current time: <strong>" + new java.util.Date() + "</strong></p>");

      // The lab asks for request.getRemoteHost(). Note that it returns the host name
      // (or IP) of the CLIENT that sent the request, not of the server.
      out.println("<p>request.getRemoteHost(): <strong>" + request.getRemoteHost() + "</strong></p>");

      // The address of the server itself is given by getLocalAddr().
      out.println("<p>Server IP address (request.getLocalAddr()): <strong>" + request.getLocalAddr() + "</strong></p>");
  %>
  <p class="nav"><a href="index.html">&larr; Back to the lab index</a></p>
</main>
</body>
</html>
