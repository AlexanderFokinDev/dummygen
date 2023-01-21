<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Form</title>
</head>
<body>
<h2>Dummy Table Form</h2>
<form action="/dummy-table" method="post">
    <label for="rows">Rows:</label>
    <input type="text" id="rows" name="rows">
    <br>
    <label for="columns">Columns:</label>
    <input type="text" id="columns" name="columns">
    <br>
    <label for="email">Email:</label>
    <input type="text" id="email" name="email">
    <br><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>