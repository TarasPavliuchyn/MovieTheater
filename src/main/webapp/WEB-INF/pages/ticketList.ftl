<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Tickets</title>
</head>
<body>
<div>
    <div align="center">
        <form name="search" action="tickets" method="get" align="left: 10px;">
          	Event Name<br>
          	<input type="text" name="eventName" placeholder="e.g. Doom"/> <br/>
            Event Date<br>
            <input type="text" name="eventDate" placeholder="e.g. 1990-03-18" title="yyyy-MM-dd"/> <br/>
            <input type="submit" value="Search" />
        </form>
    </div>
    <center><p3  align="center">Search results for event "${eventName}" and date '${(eventDate!.now)?date}'<p3></center>
    <table border="1" align="center" style="width:50%">
        <thead>
            <tr>
                <th>Ticket #</th>
                <th>Event Name</th>
                <th>Seat</th>
                <th>Ticket Price</th>
                <th>Discounted</th>
                <th>Purchased</th>
                <th>Booked</th>
            </tr>
        </thead>
        <tbody>
            <#list tickets as ticket>
                <tr>
                    <td>${ticket?counter}</td>
                    <td>${ticket.eventName}</td>
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
    <center><p3  align="center"><b>Available films are 'The Hateful Eight', 'Finding Dory' and 'Doom' for date '2016-03-18' (DEMO)!!!<b><p3></center><br/>
    </div>
</body>
</html>