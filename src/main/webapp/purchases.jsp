<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://wallet.test.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Purchase list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Purchases</h2>

    <form method="post" action="purchases">
    <fieldset>
        <table align="left">
            <th><dt>От даты (включая):<dt><input type="Date" value="${startDate}" name="startDate"></th>
            <th><dt>От даты (включая):<dt><input type="Date" value="${endDate}" name="endDate"></th>
        </table>
        <table align="right">
            <th><dt>От времени (включая):<dt><input type="time" value="${startTime}" name="startTime"></th>
            <th><dt>До времени (исключая):<dt><input type="time" value="${endTime}" name="endTime"></th>
        </table>
    </fieldset>

           <input  type="submit" value="Отменить" name="Esc">
           <input type="submit" value="Отфильтровать"  name="Filtr">

    </form>

    <a href="purchases?action=create">Add Purchase</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Product</th>
            <th>Price</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${purchases}" var="purchase">
            <jsp:useBean id="purchase" type="ru.test.wallet.to.PurchaseTo"/>
            <tr class="${purchase.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${purchase.dateTime.toLocalDate()} ${purchase.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(purchase.getDateTime())%>--%>
                        <%--${fn:replace(purchase.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(purchase.dateTime)}
                </td>
                <td>${purchase.product}</td>
                <td>${purchase.price}</td>
                <td><a href="purchases?action=update&id=${purchase.id}">Update</a></td>
                <td><a href="purchases?action=delete&id=${purchase.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>