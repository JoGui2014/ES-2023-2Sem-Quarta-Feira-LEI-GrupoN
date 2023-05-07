<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>com.webpage.Horario Form</title>
</head>
<body>
<h1>com.webpage.Horario Form</h1>
<form action="HorarioServlet" method="post">
    <label for="fileName">File Name:</label>
    <input type="text" name="fileName" id="fileName" required><br><br>
    <input type="submit" value="Load Schedule">
</form>
</body>
</html>