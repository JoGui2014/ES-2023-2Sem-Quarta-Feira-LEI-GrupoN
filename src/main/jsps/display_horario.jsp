<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Horario Display</title>
</head>
<body>
<h1>Horario Display</h1>
<% Horario horario = (Horario) request.getAttribute("horario"); %>
<% if (horario != null) { %>
<h2>Schedule:</h2>
<table>
    <tr>
        <th>Course</th>
        <th>Subject</th>
        <!-- Add more table headers for other attributes -->
    </tr>
    <% for (Aula aula : horario.getAulas()) { %>
    <tr>
        <td><%= aula.getCurso() %></td>
        <td><%= aula.getUc() %></td>
        <!-- Add more table cells for other attributes -->
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No schedule loaded.</p>
<% } %>
</body>
</html>