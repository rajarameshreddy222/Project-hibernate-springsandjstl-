<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ page isELIgnored="false" %> 

 <body bgcolor=DACBC9>
<center>

<h2>
<form action=reqsearchproducts>
<input type=text name="search">
<input type=submit value="search">
</form>

<a href="reqviewcart">View Cart</a>
<h2><a href="reqordersform">My orders</a></h2>



</h2>
</center>


<c:if test="${msg=='searchproducts' || msg=='search'}">

<center>
<body bgcolor=#86D55C>
<a href=reqsorting?value=lh>Sort products</a>

<table border=1 width=80%>
<tr bgcolor=skybule align="center">
<td>Pname</td><td>PPrice</td><td>Productinfo</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${products}" var="p">   
<c:forEach items="${filenames}" var="f">   
<c:if test="${p.getFilename()==f.getName()}">

<tr align="center"><td>${p.getPname()}</td><td>${p.getPprice()}</td><td>${p.getProductinfo()}</td><td><img src=${filepath}/${f.getName()} height=100 width=100></td><td><a href=reqviewproductbyid?pid=${p.getPid()}>Add to cart</a></td><td><a href="">Buy now</a></td></tr>

</c:if>

</c:forEach>
</c:forEach>


</table>
</c:if>



<c:if test="${msg=='viewbyid' || msg=='success'}">
<center>
<body bgcolor=#86D55C>
<form action="reqaddtocart2">
<table border=1 width=80%>
<tr bgcolor=skybule align="center">
<td>Pname</td><td>PPrice</td><td>Pqty</td><td>Productinfo</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${products}" var="p">   
<c:forEach items="${filenames}" var="f">   
<c:if test="${p.getFilename()==f.getName()}">
<td><input type=text value="${p.getPname()}" name="pname"></td><td><input type="text" value="${p.getPprice()}" name="pprice"></td>

<td><select name="pqty">
<option>1</option>
<option>2</option>
<option>3</option>
<option>4</option>
<option>5</option>
<input type=hidden value="${p.getPid() }" name="pid">
<input type=hidden value="${p.getFilename() }" name="filename">
<td> <input type="text" value="${p.getProductinfo()}" name="productinfo"></td><td><img src=${filepath}/${f.getName()} height=100 width=100></td><td><input type=submit value=Add> </td></tr>
</c:if>
</c:forEach>
</c:forEach>
</table></form>
</c:if>



<c:if test="${msg=='viewcartlist' || msg=='total'}">
<center>
<body bgcolor=#86D55C>
<form action="reqaddtocart2">
<table border=1 width=80%>
<tr bgcolor=skybule align="center">
<td>Pname</td><td>Price</td><td>Quantity</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${cart}" var="c">   
<c:forEach items="${filenames}" var="f">   
<c:if test="${c.getFilename()==f.getName()}">
<td>${c.getPname()}</td>
<td>${c.getPrice()}</td>
<td><input type="text" value="${c.getQty()}" name="qty"></td>
<td><img src=${filepath}/${f.getName()} height=100 width=100 name=filename></td><td><a href=reqdeletecart?pid=${c.getPid()}>Remove</a> </td></tr>
<c:set var="total" value="${total + c.getPrice()*c.getQty()}" />
</c:if>

</c:forEach>
</c:forEach>
</table></form>
Total Bill: ${total}
<h2><a href="reqaddaddress">Buy Now</a></h2>
<!--  <h2><a href="reqbuy?oamt=${total}">Buy Now</a></h2>-->
</c:if>

<c:if test="${msg=='newaddress'}">
<form action="reqaddress">
Enter name: <input type=text name="name"><br>
Enter mobile: <input type=text name="mobile"><br>
Enter pincode: <input type=text name="pincode"><br>
Enter Address :<textarea rows=2 cols=15 name="address"></textarea><br>
Enter city: <input type=text name="city"><br>
Enter state: <input type=text name="state"><br>
<!--  <input type=submit value="Submit">-->
<tr><td><input type=submit value="Add"><input type=reset value="Clear"></td></tr>
</form>
</c:if>


<c:if test="${msg=='addressinfo' }">
<!--  
<tr><td>Address</td><td>
<select name="address"> 
<option>Select Address</option>
<c:forEach items="${address}" var="l"> 
<option>${l.getName()},
${l.getAddress()},  ${l.getCity()},   ${l.getPincode()},   ${l.getState()}<br></option>
</c:forEach>
</select>
</td></tr>-->
<a href=reqaddnewaddress>Click here </a>to add new address
<br><br><br><br><br><br><br>
<center>
<h1>OR</h1>
<a href=reqexistingaddress>Click here </a>to use existing address
<!--  <h2><a href=reqtransactionform?tamt=${total}>Continue</a></h2>-->

</c:if>

<c:if test="${msg=='viewaddress'}">
<center>
<body bgcolor=#86D55C>
<table border=2 width=80%>
<tr bgcolor=skybule align="center">
 Select address
<c:forEach items="${address}" var="l"> 
<a href=selectaddress?address=${l.getName()},${l.getAddress()},${l.getCity()},${l.getState()},${l.getPincode()}>${l.getName()}${l.getAddress()}${l.getCity()}${l.getPincode()}${l.getState()}</a>

</c:forEach>


</table>
</c:if>



<c:if test="${msg=='tran'}">
<c:forEach items="${cart}" var="c">   
<c:set var="total" value="${total + c.getPrice()*c.getQty()}" />
</c:forEach>
<form action="reqtransaction">
<tr><td>Address</td><td>
<select name="address"> 
<option>Select Address</option>
<c:forEach items="${address}" var="l"> 
<option>${l.getName()},
${l.getAddress()},  ${l.getCity()},   ${l.getPincode()},   ${l.getState()}<br></option>
</c:forEach>
</select>
</td></tr>
<tr><td>Transaction Date:</td><td><input type="text" value="${odate}" name="tdate"></td></tr><br>
<tr><td>Transaction Amount:</td><td><input type="text" value="${total}" name="tamt"></td></tr><br>
<tr><td>Transaction type</td><td>
<select name="ttype"> 
<option>Select payment mode</option>
<option>COD</option>
<option>Credit card</option>
<option>Debit card</option>
<option>EMI</option>
</select>
</td></tr><br>
<tr><td><input type=submit value="Pay Now"></td></tr>
</form>
<!--  <h2><a href=reqtransactionform?tamt=${total}>Continue</a></h2>-->
</c:if>


<c:if test="${msg=='orderinfo' }">
<center>
<body bgcolor=#86D55C>
<table border=1 width=80%>
<tr bgcolor=green align="center"><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ page isELIgnored="false" %> 

 <body bgcolor=DACBC9>
<center>

<h2>
<form action=reqsearchproducts>
<input type=text name="search">
<input type=submit value="search">
</form>

<!--  <a href="reqeditcustomerdetails">Account settings</a><br>-->
<a href="reqviewcart">View Cart</a>
<h2><a href="reqordersform">My orders</a></h2>



</h2>
</center>


<c:if test="${msg=='searchproducts' || msg=='search'}">

<center>
<body bgcolor=#86D55C>
<a href=reqsorting?value=lh>Sort products</a>

<table border=1 width=80%>
<tr bgcolor=skybule align="center">
<td>Pname</td><td>PPrice</td><td>Productinfo</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${products}" var="p">   
<c:forEach items="${filenames}" var="f">   
<c:if test="${p.getFilename()==f.getName()}">

<tr align="center"><td>${p.getPname()}</td><td>${p.getPprice()}</td><td>${p.getProductinfo()}</td><td><img src=${filepath}/${f.getName()} height=100 width=100></td><td><a href=reqviewproductbyid?pid=${p.getPid()}>Add to cart</a></td><td><a href="">Buy now</a></td></tr>

</c:if>

</c:forEach>
</c:forEach>


</table>
</c:if>



<c:if test="${msg=='viewbyid' || msg=='success'}">
<center>
<body bgcolor=#86D55C>
<form action="reqaddtocart2">
<table border=1 width=80%>
<tr bgcolor=skybule align="center">
<td>Pname</td><td>PPrice</td><td>Pqty</td><td>Productinfo</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${products}" var="p">   
<c:forEach items="${filenames}" var="f">   
<c:if test="${p.getFilename()==f.getName()}">
<td><input type=text value="${p.getPname()}" name="pname"></td><td><input type="text" value="${p.getPprice()}" name="pprice"></td>

<td><select name="pqty">
<option>1</option>
<option>2</option>
<option>3</option>
<option>4</option>
<option>5</option>
<input type=hidden value="${p.getPid() }" name="pid">
<input type=hidden value="${p.getFilename() }" name="filename">
<td> <input type="text" value="${p.getProductinfo()}" name="productinfo"></td><td><img src=${filepath}/${f.getName()} height=100 width=100></td><td><input type=submit value=Add> </td></tr>
</c:if>
</c:forEach>
</c:forEach>
</table></form>
</c:if>



<c:if test="${msg=='viewcartlist' || msg=='total'}">
<center>
<body bgcolor=#86D55C>
<form action="reqaddtocart2">
<table border=1 width=80%>
<tr bgcolor=skybule align="center">
<td>Pname</td><td>Price</td><td>Quantity</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${cart}" var="c">   
<c:forEach items="${filenames}" var="f">   
<c:if test="${c.getFilename()==f.getName()}">
<td>${c.getPname()}</td>
<td>${c.getPrice()}</td>
<td><input type="text" value="${c.getQty()}" name="qty"></td>
<td><img src=${filepath}/${f.getName()} height=100 width=100 name=filename></td><td><a href=reqdeletecart?pid=${c.getPid()}>Remove</a> </td></tr>
<c:set var="total" value="${total + c.getPrice()*c.getQty()}" />
</c:if>

</c:forEach>
</c:forEach>
</table></form>
Total Bill: ${total}
<h2><a href="reqaddaddress">Buy Now</a></h2>
<!--  <h2><a href="reqbuy?oamt=${total}">Buy Now</a></h2>-->
</c:if>

<c:if test="${msg=='newaddress'}">
<form action="reqaddress">
Enter name: <input type=text name="name"><br>
Enter mobile: <input type=text name="mobile"><br>
Enter pincode: <input type=text name="pincode"><br>
Enter Address :<textarea rows=2 cols=15 name="address"></textarea><br>
Enter city: <input type=text name="city"><br>
Enter state: <input type=text name="state"><br>
<!--  <input type=submit value="Submit">-->
<tr><td><input type=submit value="Add"><input type=reset value="Clear"></td></tr>
</form>
</c:if>


<c:if test="${msg=='addressinfo' }">
<!--  
<tr><td>Address</td><td>
<select name="address"> 
<option>Select Address</option>
<c:forEach items="${address}" var="l"> 
<option>${l.getName()},
${l.getAddress()},  ${l.getCity()},   ${l.getPincode()},   ${l.getState()}<br></option>
</c:forEach>
</select>
</td></tr>-->
<a href=reqaddnewaddress>Click here </a>to add new address
<br><br><br><br><br><br><br>
<center>
<h1>OR</h1>
<a href=reqexistingaddress>Click here </a>to use existing address
<!--  <h2><a href=reqtransactionform?tamt=${total}>Continue</a></h2>-->

</c:if>

<c:if test="${msg=='viewaddress'}">
<center>
<body bgcolor=#86D55C>
<table border=2 width=80%>
<tr bgcolor=skybule align="center">
 Select address
<c:forEach items="${address}" var="l"> 
<a href=selectaddress?address=${l.getName()},${l.getAddress()},${l.getCity()},${l.getState()},${l.getPincode()}>${l.getName()}${l.getAddress()}${l.getCity()}${l.getPincode()}${l.getState()}</a>

</c:forEach>


</table>
</c:if>



<c:if test="${msg=='tran'}">
<c:forEach items="${cart}" var="c">   
<c:set var="total" value="${total + c.getPrice()*c.getQty()}" />
</c:forEach>
<form action="reqtransaction">
<tr><td>Address</td><td>
<select name="address"> 
<option>Select Address</option>
<c:forEach items="${address}" var="l"> 
<option>${l.getName()},
${l.getAddress()},  ${l.getCity()},   ${l.getPincode()},   ${l.getState()}<br></option>
</c:forEach>
</select>
</td></tr>
<tr><td>Transaction Date:</td><td><input type="text" value="${odate}" name="tdate"></td></tr><br>
<tr><td>Transaction Amount:</td><td><input type="text" value="${total}" name="tamt"></td></tr><br>
<tr><td>Transaction type</td><td>
<select name="ttype"> 
<option>Select payment mode</option>
<option>COD</option>
<option>Credit card</option>
<option>Debit card</option>
<option>EMI</option>
</select>
</td></tr><br>
<tr><td><input type=submit value="Pay Now"></td></tr>
</form>
<!--  <h2><a href=reqtransactionform?tamt=${total}>Continue</a></h2>-->
</c:if>


<c:if test="${msg=='orderinfo' }">
<center>
<body bgcolor=#86D55C>
<table border=1 width=80%>
<tr bgcolor=skybule align="center">
<td>Order ID</td><td>Transaction ID</td><td>Product name</td><td>Price</td><td>Quantity</td><td>Image</td><td>Date</td></tr>
<c:forEach items="${orders}" var="c">
   
<c:forEach items="${filenames}" var="f">   
<c:if test="${c.getFilename()==f.getName()}">
<td>${c.getOid()}</td>
<td>${c.getTid()}</td>
<td>${c.getPname()}</td>
<td>${c.getPrice()}</td>
<td>${c.getQty()}</td>
<td><img src=${filepath}/${f.getName()} height=100 width=100 name=filename></td><td>${c.getOdate()} </td></tr>
</c:if>

</c:forEach>
</c:forEach>
</table></form>
</c:if>


<c:if test="${msg=='order'}">
Your order has been successfully placed<br>
Click here<a href="reqordersform"></a>to check your orders.
</c:if>


<c:if test="${msg=='success'}">
Success
</c:if>


<td>Order ID</td><td>Transaction ID</td><td>Product name</td><td>Price</td><td>Quantity</td><td>Image</td><td>Date</td></tr>
<c:forEach items="${orders}" var="c">
   
<c:forEach items="${filenames}" var="f">   
<c:if test="${c.getFilename()==f.getName()}">
<td>${c.getOid()}</td>
<td>${c.getTid()}</td>
<td>${c.getPname()}</td>
<td>${c.getPrice()}</td>
<td>${c.getQty()}</td>
<td><img src=${filepath}/${f.getName()} height=100 width=100 name=filename></td><td>${c.getOdate()} </td></tr>
</c:if>

</c:forEach>
</c:forEach>
</table></form>
</c:if>


<c:if test="${msg=='order'}">
Your order has been successfully placed<br>
Click here<a href="reqordersform"></a>to check your orders.
</c:if>


<c:if test="${msg=='success'}">
Success
</c:if>







<c:if test="${msg=='viewbyid' || msg=='success'}">
<center>
<body bgcolor=#86D55C>
<form action="reqaddtocart">
<table border=1 width=80%>
<tr bgcolor=pink align="center">
<td>Pname</td><td>PPrice</td><td>Pqty</td><td>Productinfo</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${products}" var="p">   
<c:forEach items="${filenames}" var="f">   
<c:if test="${p.getFilename()==f.getName()}">

<td><input type=text value="${p.getPname()}" name="pname"></td><td><input type="text" value="${p.getPprice()}" name="pprice"></td><td>

<select name="pqty">
<option>1</option>
<option>2</option>
<option>3</option>
<option>4</option>
<option>5</option>
<input type=hidden value="${p.getPid() }" name="pid">
<input type=hidden value="${p.getFilename() }" name="filename">

<td> <input type="text" value="${p.getProductinfo()}" name="productinfo"></td><td><img src=${filepath}/${f.getName()} height=100 width=100></td><td><input type=submit value=Add> </td></tr>
</c:if>

</c:forEach>
</c:forEach>


</table></form>
</c:if>





<c:if test="${msg=='viewcartlist'}">
<center>
<body bgcolor=#86D55C>
<form action="reqaddtocart">
<table border=1 width=80%>
<tr bgcolor=pink align="center">
<td>Pname</td><td>Price</td><td>Quantity</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${cart}" var="c">   

<c:forEach items="${filenames}" var="f">   
<c:if test="${c.getFilename()==f.getName()}">
<td>${c.getPname()}</td>
<td>${c.getPrice()}</td>

<td><input type="text" value="${c.getQty()}" name="qty"></td>

<td><img src=${filepath}/${f.getName()} height=100 width=100 name=filename></td><td><a href=reqdeletecart?pid=${c.getPid()}>Remove</a> </td></tr>
<c:set var="total" value="${total + c.getPrice()*c.getQty()}" />
</c:if>

</c:forEach>
</c:forEach>
</table></form>
Total Bill: ${total}
<h2><a href="reqbuy?oamt=${total}">Buy Now</a></h2>
</c:if>

<c:if test="${msg=='orderinfo'}">

<form action="reqorder">
Order Amount: <input type="text" value=${oamt} name="oamt"><br>
Order Date: <input type=text value=${odate} name=odate><br>
Select Address
<select name="address"> 
<option>Select address</option>
<c:forEach items="${alist}" var="alist">
<option>${clist.getCatname()}
</option>
</c:forEach>
</select>
<a href=reqaddress>Add Address </a>
<input type=submit value="Submit">
</form>
</c:if>


<c:if test="${msg=='success'}">

Success

</c:if>
