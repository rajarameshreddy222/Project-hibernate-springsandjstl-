<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ page isELIgnored="false" %> 
<!-- <head>
<style>
body{
background-image:url(webstuff/images/pic.jpg);
background-repeat:no-repeat;
background-size:100%

 }

</style>
</head>
 -->
 <body bgcolor=DACBC9>
<center>

<h2>
<a href="reqaddproductform">Add Products</a><br>
<a href="reqallproducts">Manage Products</a><br>
<a href="reqviewallcategories">Manage categories</a><br>
<a href="reqadminloginform">LogOut</a><br>
</h2>
</center>

<c:if test="${msg=='addproduct'}">

<center>
<table border=1 width=30%>
<tr><td colspan="2"><h2>Add Product Form</h2></td></tr>
<form action="reqaddproduct" method=post enctype="multipart/form-data"> 
<tr>
 <td>Categorys</td><td>
<select name="ctype"> 
<option>Select Category</option>
<c:forEach items="${clist}" var="clist">
<option>${clist.getCatname()}</option>
</c:forEach>
</select>
</td></tr> 
<tr><td>Product Name:</td><td> <input type=text name="pname"></td></tr>
<tr><td>FileName:</td><td><input type="file" name="filename"></td></tr>
<tr><td>Product info:</td><td> <textarea rows=2 cols=20 name="productinfo"></textarea></td></tr>
<tr><td>Quantity:</td><td><input type="text" name="pqty"></td></tr>
<tr><td>Price:</td><td><input type="text" name="pprice"></td></tr><br>
<tr><td colspan="2"><center><input type=submit value="Add"><input type=reset value="Clear"></td></tr>
</form></table>
</c:if>

<c:if test="${msg=='viewproducts' || msg=='success'}">
<center>
<body bgcolor=#86D55C>

<table border=2 width=80%>
<tr bgcolor=skyblue align="center">
<td>Pid</td><td>Ctype</td><td>FileName</td><td>Pname</td><td>PPrice</td><td>Pqty</td><td>Productinfo</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${products}" var="p"> 

<c:forEach items="${filenames}" var="f">   
  
<c:if test="${p.getFilename()==f.getName()}">

<tr align="center"><td>${p.getPid()}</td><td>${p.getCtype()}</td><td>${p.getFilename()}</td><td>${p.getPname()}</td><td>${p.getPprice()}</td><td>${p.getPqty()}</td><td>${p.getProductinfo()}</td><td><img src=${filepath}/${f.getName()} height=100 width=100></td><td><a href=reqdeleteproduct?pid=${p.getPid()}>Delete Product</a></td><td><a href="reqeditproduct?pid=${p.getPid()}">Edit Product</a></td></tr>
</c:if>

</c:forEach>
</c:forEach>


</table>
</c:if>

<c:if test="${msg=='editproduct'}">
<table>
<form action="requpdateproduct"><c:forEach items="${products}" var="product">
<tr><td>Product id: </td><td><input type=text name="pid"  readonly  value=${product.getPid()}></td></tr>
<tr><td>Product Name : </td><td><input type=text name=pname value=${product.getPname()}></td></tr>
<tr><td>prodcut Price :</td><td><input type=text name=pprice value=${product.getPprice()}></td></tr>
<tr><td>prodcut quantity :</td><td><input type=text name=pqty value=${product.getPqty()}></td></tr>
<tr><td>prodcutinfo:</td><td><input type=text name=productinfo value=${product.getProductinfo()}></td></tr>
<input type=hidden name=filename value=${product.getFilename()}>
<input type=hidden name=ctype value=${product.getCtype()}>

<tr><td><input type=submit value="Update Product"></td><td><input type=reset value="clear"></td></tr>
</form></c:forEach>
</c:if>










<c:if test="${msg=='viewcat'}">

<h3>Product Categories</h3>
To add a Category, <a href="reqaddcategoryform">click Here</a><br><br>

<table border=2 width=50%>
<tr align=center><td>Category ID</td><td>Category Name</td><td>Actions</td></tr>
<c:forEach items="${clist}" var="clist">
<tr align=center><td>${clist.getCid()}</td><td>${clist.getCatname()}</td><td><a href="reqdisplayproducts?catname=${clist.getCatname()}">Display Products</a>|<a href="reqdeletecategory?cid=${clist.getCid()}">Delete</a>|<a href="reqeditcat?cid=${clist.getCid()}">Edit</a></td></tr>

</c:forEach>
</table>
</c:if>

<c:if test="${msg=='addcategoryform' || msg1=='Category added Successfully'}">

<form action="reqaddcategory">
Enter Category Name: <input type=text name="catname" required><input type="submit" value="Add Category">
</form>
${msg1}
</c:if>



<c:if test="${msg=='viewcategorybyid'}"><table>

<form action=requpdatecat>

<tr><td>cid</td><td><input type=text name=cid value="${categroy.getCid()}"></td></tr>
<tr><td>CategoryName</td><td><input type=text name=catname value="${categroy.getCatname()}"></td></tr>


<tr><td><input type=submit value="Update Categroy"></td><td><input type=reset value="clear"></td></tr>

</from></table>
</c:if>

<c:if test="${msg=='productslist'}">

<table border=2 width=80%>
<tr bgcolor=yellow align="center"><td>Pid</td><td>Ctype</td><td>FileName</td><td>Pname</td><td>PPrice</td><td>Pqty</td><td>Productinfo</td><td>Image</td><td colspan=2 align=center>Actions</td></tr>
<c:forEach items="${plist}" var="p">  
<c:forEach items="${filenames}" var="f">   
<c:if test="${p.getFilename()==f.getName()}">


<tr align="center"><td>${p.getPid()}</td><td>${p.getCtype()}</td><td>${p.getFilename()}</td><td>${p.getPname()}</td><td>${p.getPprice()}</td><td>${p.getPqty()}</td><td>${p.getProductinfo()}</td><td><img src=${filepath}/${f.getName()} height=100 width=100></td><td><a href=reqdeleteproduct?pid=${p.getPid()}>Delete Product</a></td><td><a href=>Edit Product</a></td></tr>

</c:if>
</c:forEach>
</c:forEach>
</table>



</c:if>



