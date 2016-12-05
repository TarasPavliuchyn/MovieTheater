<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Users</title>
</head>
<body>
<div>
    <center><a href="/">Home page</a>
    <a href="/upload/users">Back to uploading </a><br>
    <p3  align="center">All users are here<p3></center>
    <table border="1" align="center" style="width:50%">
        <thead>
            <tr>
                <th>User #</th>
                <th>Email</th>
                <th>Full Name</th>
                <th>Birthday</th>
                <th>Role</th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
                <tr>
                    <td>${user?counter}</td>
                    <td>${user.email}</td>
                    <td>${user.fullName}</td>
                    <td>${user.birthDay}</td>
                    <td>${user.role}</td>
                </tr>
            </#list>
        </tbody>
    </table>
</div>
</body>
</html>