<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Tickets</title>
</head>
<body>
<div>
<center><a href="tickets?eventName=Finding+Dory&eventDate=2016-03-18">One more ticket</a><center>
<#if ticket.booked != true>
    <div align="center">
    <#if ticket.discounted == true>
         <#assign action = 'reserve'>
         <#assign submit = 'Book ticket'>
         <#assign message = 'Please submit booking for'>
    <#else>
         <#assign action = 'discount'>
         <#assign submit = 'Check for discount'>
         <#assign message = 'Please enter your email to continue booking and check discounts for'>
    </#if>

    <center><p3  align="center">${message} '${ticket.event.name}'|seat:${ticket.seat}|price:${ticket.ticketPrice} hrn<p3></center><br/>
        <form name="booking" action="${action}" method="post" align="left: 10px;">
            <#if ticket.discounted != true >
                <input type="text" name="userEmail" placeholder="email"/>
            <#elseif userEmail??>
                <input type="hidden" name="userEmail" value='${userEmail}'/>
            <#else>
                <input type="text" name="userEmail" placeholder="email"/>
            </#if>
            <input type="hidden" name="ticketId" value='${ticket.ticketId}'/>
            <input type="submit" value="${submit}" />
            <br>
        </form>
    </div>
    <br><br>
    <center><p3  align="center"><b><font color="red">Please use 'Taras_Pavliuchyn@epam.com' or 'Anton_Yaskou@epam.com' for demo!!!</font<b><p3></center><br/>
<#else>
    <center><p3>Ticket was successfully booked '${ticket.event.name}'|seat:${ticket.seat}|price:${ticket.ticketPrice}hrn.<p3></center><br/>
    <center><p3><a href="ticket/pdf?userEmail=${userEmail}">Show all tickets for user ${userEmail}</a><p3></center><br/>
</#if>
</body>
</html>