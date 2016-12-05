<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Events</title>
</head>
<body>
<div>
    <center><a href="/">Home page</a>
    <a href="/upload/events">Back to uploading </a><br>
    <p3  align="center">All events are here<p3></center>
    <table border="1" align="center" style="width:50%">
        <thead>
            <tr>
                <th>Event #</th>
                <th>Name</th>
                <th>Price</th>
                <th>Rating</th>
            </tr>
        </thead>
        <tbody>
            <#list events as event>
                <tr>
                    <td>${event?counter}</td>
                    <td>${event.name}</td>
                    <td>${event.basePrice}</td>
                    <td>${event.rating}</td>
                </tr>
            </#list>
        </tbody>
    </table>
</div>
</body>
</html>