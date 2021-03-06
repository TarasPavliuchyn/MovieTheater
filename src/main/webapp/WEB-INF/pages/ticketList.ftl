<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Tickets</title>
</head>
<body>
<div>
    <div align="center">
        <a href="/">Home page</a><br>
        <form name="search" action="tickets" method="get" align="left: 10px;">
          	Event Name<br>
          	<input type="text" name="eventName" placeholder="e.g. Doom"/> <br/>
            Event Date<br>
            <input type="text" name="eventDate" placeholder="e.g. 2016-03-18" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])" title="yyyy-MM-dd"/> <br/>
            <input type="submit" value="Search" />
        </form>
    </div>
    <center><p3  align="center">Search results for event "${eventName}" and date '${(eventDate!.now)?date}'<p3></center>
    <table border="1" align="center" style="width:50%">
        <thead>
            <tr>
                <th>Ticket #</th>
                <th>Name</th>
                <th>Auditorium</th>
                <th>Rate</th>
                <th>Seat</th>
                <th>Base Price</th>
                <th>Discounted</th>
                <th>Purchased</th>
                <th>Booked</th>
            </tr>
        </thead>
        <tbody>
            <#list tickets as ticket>
                <tr>
                    <td>${ticket?counter}</td>
                    <td>${ticket.event.name}</td>
                    <td>${ticket.auditoriumName}</td>
                    <td>${ticket.event.rating}</td>
                    <td>${ticket.seat}</td>
                    <td>${ticket.ticketPrice}</td>
                    <td><#if ticket.discounted>Yes<#else>No</#if></td>
                    <td><#if ticket.purchased>Yes<#else>No</a></#if></td>
                    <td><#if ticket.booked>Yes<#else><a href="ticket?ticketId=${ticket.ticketId}">Book</a></#if></td>
                </tr>
            </#list>
        </tbody>
    </table>
    <br><br>
    <center><p3 align="center"><b><font color="red">Available films are 'The Hateful Eight', 'Finding Dory' and 'Doom' for date '2016-03-18' (DEMO)!!!</font><b><p3></center><br/>
    </div>
</body>
</html>