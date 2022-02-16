<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>purchase</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create purchase' : 'Edit purchase'}</h2>
    <jsp:useBean id="purchase" type="ru.test.wallet.model.Purchase" scope="request"/>
    <form method="post" action="purchases">
        <input type="hidden" name="id" value="${purchase.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd><input type="datetime-local" value="${purchase.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt>Product:</dt>
            <dd><input type="text" value="${purchase.product}" size=40 name="product" required></dd>
        </dl>
        <dl>
            <dt>Price:</dt>
            <dd><input type="number" value="${purchase.price}" name="price" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
