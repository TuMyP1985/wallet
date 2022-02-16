<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <link type="text/css"
          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Edit purchase</title>

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit purchase</h2>

<form method="POST" action='purchases' name="frmAddUser">
    <table>
        <tr>
            <td>DateTime :</td>
            <td><input type="datetime-local" name="dateTime" value="<c:out value="${purchaseTo.dateTime}" />"/></td>
        </tr>
        <br/>
        <tr>
            <td>product :</td>
            <td><input type="text" name="product" value="<c:out value="${purchaseTo.product}" />"/></td>
        </tr>
        <br/>
        <tr>
            <td>price : </td>
            <td><input type="number" name="price" value="<c:out value="${purchaseTo.price}" />"/></td>
        </tr>
        <br/>
    </table>

    <button name="purchaseToId" value="<c:out value="${purchaseTo.id}" />" onclick="purchases">Save</button>
    <button name="purchaseToId" value="<c:out value="Cancel" />" onclick="purchases">Cancel</button>

</form>
</body>
</html>