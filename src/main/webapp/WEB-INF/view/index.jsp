<%
String header = "Welcome to the club, buddy!";
java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", new java.util.Locale("ru"));

%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Start page</title>
    </head>
    <body align="middle">
        <h2><%= header %></h2>
        <p>Today <%= dateFormat.format(new java.util.Date()) %></p>
    </body>
</html>