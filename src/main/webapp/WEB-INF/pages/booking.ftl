<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Tickets</title>
</head>
<body>
<div>
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

    <center><p3  align="center">${message} '${ticket.eventName}'|seat:${ticket.seat}|price:${ticket.ticketPrice} hrn<p3></center><br/>
        <form name="booking" action="${action}" method="post" align="left: 10px;">
            <#if ticket.discounted != true>
                <input type="text" name="userEmail" placeholder="email"/>
            <#else>
                <input type="hidden" name="userEmail" value='${userEmail}'/>
            </#if>
            <input type="hidden" name="ticketId" value='${ticket.ticketId}'/>
            <input type="submit" value="${submit}" />
            <br>
        </form>
    </div>
<#else>
    <center><p3  align="center">Ticket was successfully booked '${ticket.eventName}'|seat:${ticket.seat}|price:${ticket.ticketPrice}hrn by user with email ${userEmail}<p3></center><br/>
</#if>
</body>
</html>