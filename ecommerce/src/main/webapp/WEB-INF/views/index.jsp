<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ page isELIgnored="false" %>
<head>
<style>
body{
background-image:
background-repeat:no-repeat;
background-size:100%

 }

</style>
</head>
<body>
<center>
<a href="reqadminloginform">Admin</a> <br>  
<a href="reqcustomerloginform">Customer</a>

<br>

<form action=reqsearch>
<input type=text name="search">
<input type=submit value="search">
</form>

<br>
<c:if test="${msg=='admin'}">
     AdminLogin <br>
   <form action="reqadminlogin" method="post">
<table border=0><h2>
<tr><td><b>Username:</td><td><input type="text" name="uid"></td></tr>
<tr><td><b>Password:</td><td><input type="password" name="pwd"><br></td></tr>
<tr><td><input type="submit" value=Login></td><td><b><input type="reset" value=Clear></b></td></h2></td></tr></table></form>

   </c:if>


<c:if test="${msg=='customer'}">
  <table border=0><h2>  
  <tr><td colspan="1"> Customer Login 
  <form action="reqcustomerlogin" method="post">
<tr><td><b>Username:</td><td><input type="text" name="email"></td></tr>
<tr><td><b>Password:</td><td><input type="password" name="pwd"><br></td></tr>
<tr><td><input type="submit" value=Login></td><td><b><input type="reset" value=Clear></b></td></h2></td></tr></table></form>
Click here to <a href="reqcustomerregisterform">Register ?</a>
</c:if>




<c:if test="${msg=='addcustomer' || msg=='registercustomer' || msg=='addingcustomerform'}">

<table border=0 width=30%>
<tr><td colspan="2"> <center><h2>Registration form</h2></center></td></tr>
<form action="reqcustomerregister" method=post>
<tr><td>Customer Name:</td><td><input type="text" name="name"></td></tr>
<tr><td>Email:</td><td><input type="text" name="email"></td></tr>
<tr><td>Password: </td><td><input type=text name="pwd"></td></tr>
<tr><td>City:</td><td><input type="text" name="city"></td></tr>
<tr><td>Mobile:</td><td><input type="text" name="mobile"></td></tr>
<tr><td><input type=submit value="register"><input type=reset value="Clear"></td></tr>
</table>
</c:if>
