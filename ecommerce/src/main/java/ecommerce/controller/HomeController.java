package ecommerce.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ecommerce.dao.Daoimple;
import ecommerce.model.Address;
import ecommerce.model.AdminLogin;
import ecommerce.model.Cart;
import ecommerce.model.Category;
import ecommerce.model.Customer;
import ecommerce.model.Orders;
import ecommerce.model.Product;
import ecommerce.model.Transactions;


@Controller
public class HomeController
{
	public static final String cloud="D://cloud";
	////Daoimple()
//	public static final String cloud="D://cloud";
	@RequestMapping("/")
	public String homePage()
	{
		return "index";
	}
	@RequestMapping("/reqadminloginform")
	public ModelAndView adminLoginForm()
	{
		ModelAndView mav=new ModelAndView("index");
		mav.addObject("msg","admin");
		return mav;
	}
	@RequestMapping("/reqcustomerloginform")
	public ModelAndView customerLoginForm()
	{
		ModelAndView mav=new ModelAndView("index");
		mav.addObject("msg","customer");
		return mav;
	}
	@RequestMapping("/reqcustomerregisterform")
	public ModelAndView customerRegisterForm()
	{
		ModelAndView mav=new ModelAndView("index");
		mav.addObject("msg","addingcustomerform");
		return mav;
	}

	@RequestMapping("/reqadminlogin")
	public ModelAndView adminLogin(@ModelAttribute("adminlogin") AdminLogin adminlogin)
	{
		ModelAndView mav=null;
		boolean b=false;
		b=new Daoimple().adminLogin(adminlogin);
		if(b)
		{
			mav=new ModelAndView("adminoperations");
		}
		else
		{
			mav=new ModelAndView("index");
		}
		return mav;
	}
	@RequestMapping("/reqcustomerlogin")
	public ModelAndView customerLogin(@ModelAttribute("customerlogin") Customer customerlogin)
	{
		ModelAndView mav=null;
		boolean b=false;
		b=new Daoimple().customerLogin(customerlogin);
		if(b)
		{
			mav=new ModelAndView("welcomecustomer");
		}
		else
		{
			mav=new ModelAndView("index");
			System.out.println("Failed");
		}
		return mav;
	}
	
	@RequestMapping("/reqaddproductform")
	public ModelAndView addProductForm()
	{
		 List<Category> clist=null;
		 clist=new Daoimple().viewAllCaterories();
		 ModelAndView mav=new ModelAndView("adminoperations");
		 mav.addObject("msg","addproduct");
		 mav.addObject("clist",clist);
	return mav;
	}
	@RequestMapping("/reqaddproduct")
	public ModelAndView addProduct(HttpServletRequest req)
	{
		Product p=new Product();
		ModelAndView mav=new ModelAndView();
		if(ServletFileUpload.isMultipartContent(req))
		{
			try
			{
				List<FileItem> multiparts=new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
				System.out.println(multiparts);
				for(FileItem item:multiparts)
				{
					if(!item.isFormField())
					{
						p.setFilename(new File(item.getName()).getName());
						item.write(new File(cloud+File.separator+p.getFilename()));
					}
					else
					{
						if(item.getFieldName().equals("ctype"))
						{
							p.setCtype(item.getString());
						}
						if(item.getFieldName().equals("pname"))
						{
							p.setPname(item.getString());
						}
						if(item.getFieldName().equals("productinfo"))
						{
							p.setProductinfo(item.getString());
						}
						if(item.getFieldName().equals("pqty"))
						{
							p.setPqty(Integer.parseInt(item.getString()));
						}
						if(item.getFieldName().equals("pprice"))
						{
							p.setPprice(Double.parseDouble(item.getString()));
						}
						
					}
				}
				boolean b=false;
				b=new Daoimple().addProduct(p);
				if(b)
				{
					mav=new ModelAndView("adminoperations");
					mav.addObject("msg1","success");
					
				}
				}
					catch(Exception e)
					{
						System.out.println(e);
					}
				
				
			        } 
			 return mav;	
	}
	@RequestMapping("/reqcustomerregister")
	public ModelAndView customerRegister(Customer c)
	{
		ModelAndView mav=new ModelAndView();
		boolean b=false;
		b=new Daoimple().customerRegister(c);
		if(b)
		{
			mav=new ModelAndView("index");
			mav.addObject("msg","addcustomer");
		}
		return mav;
	}
	
	@RequestMapping("/reqallproducts")
	 public ModelAndView  viewAllProducts()
	 {
		 List<Product> al=null;
		 al=new Daoimple().viewAllProducts();
	     File f=new File("D://cloud");
	    File filenames[]=f.listFiles();
		 ModelAndView mav=new ModelAndView("adminoperations");
		 mav.addObject("products",al); 
		 mav.addObject("filenames",filenames);  // adding imges info
		 mav.addObject("filepath",f);            // adding cloud folder path
		 mav.addObject("msg","viewproducts");
		 return mav;
	 }
	@RequestMapping("/reqdeleteproduct")
	public ModelAndView deleteProduct(@RequestParam("pid") String pid)
	{
		ModelAndView mav=new ModelAndView();
		boolean b=false;
		Product p=new Product();
		p.setPid(pid);
		b=new Daoimple().deleteProduct(p);
		if(b)
		{
			mav=new ModelAndView();
			mav.setViewName("redirect:reqallproducts");
			mav.addObject("msg","Success");
		}
		return mav;
	}
	 @RequestMapping("/reqviewallcategories")
	 public ModelAndView viewAllCategories()
	 {
		 List<Category> clist=null;
		 clist=new Daoimple().viewAllCaterories();
		 
		 ModelAndView mav=new ModelAndView("adminoperations");
		 mav.addObject("msg","viewcat");
		 mav.addObject("clist",clist);
		 
		 return mav;
	 }
	 @RequestMapping("/reqaddcategoryform")
	 public ModelAndView addCategoryForm()
	 {
		 ModelAndView mav=new ModelAndView("adminoperations");
		 mav.addObject("msg","addcategoryform");
		 
		 return mav;
	 }
	 @RequestMapping("/reqaddcategory")
	 public ModelAndView addCategory(@ModelAttribute("categories") Category catergories)
	 {
		 boolean b=false;
		 ModelAndView mav=null;
		
         b=new Daoimple().addCategory(catergories);	 
		 if(b)
		 {
          mav=new ModelAndView("adminoperations");
          mav.addObject("msg1","Category added Successfully");
		 }
		 else
		 {
			 mav=new ModelAndView("adminoperations");
	          mav.addObject("msg1","fail"); 
		 }
		

		 return mav;
		 
	 }
	 @RequestMapping("/reqdisplayproducts")
		public ModelAndView displayProducts(@RequestParam("catname") String catname)
		{
			List<Product> plist=null;
			plist=new Daoimple().displayProducts(catname);
			
			for(Product p:plist)
			{
				System.out.println("h: "+p.getPname()+" : "+p.getFilename());
			}
			
			ModelAndView mav=new ModelAndView("adminoperations");
			File f=new File("D://cloud");
		    File filenames[]=f.listFiles();
			mav.addObject("plist",plist);     // adding product info
			mav.addObject("filenames",filenames);  // adding img filenames
		     mav.addObject("filepath",f);
			mav.addObject("msg","productslist");
			
			return mav;
		}
	 	
		@RequestMapping("/reqeditproduct")
		public ModelAndView editproduct(@RequestParam("pid") String pid)
		{
		List<Product> list;
		list=new Daoimple().viewProductById(pid);
		 File f=new File("D://cloud");
	     File filenames[]=f.listFiles();
		 ModelAndView mav=new ModelAndView("adminoperations");
		 mav.addObject("products",list); 
		 mav.addObject("filenames",filenames);
		 mav.addObject("filepath",f);
		 mav.addObject("msg","editproduct");
		 return mav;
			
		}
	
		@RequestMapping("/requpdateproduct")
		public ModelAndView updateproduct(@ModelAttribute("product") Product product)
		{    boolean b=false;
		     b=new Daoimple().updateproduct(product);
		     List<Product> al=null;
			 al=new Daoimple().viewAllProducts();
		     File f=new File("D://cloud");
		     File filenames[]=f.listFiles();
			 ModelAndView mav=new ModelAndView("adminoperations");
			 mav.addObject("products",al); 
			 mav.addObject("filenames",filenames);
			 mav.addObject("filepath",f);
			 mav.addObject("msg","viewproducts");
			 return mav;
		}
		@RequestMapping("/requpdatecategory")
		public ModelAndView updatecategory(@ModelAttribute("category") Category category)
		{
			boolean b=false;
			Category c=new Category();
			b=new Daoimple().updatecategory(category);
			ModelAndView mav=new ModelAndView("adminoperations");
			mav.addObject("msg","viewcat");	 
			return mav;
		}
		
		@RequestMapping("/reqeditcategory")
		public ModelAndView editcategory(@RequestParam("cid") String cid)
		{
	 		boolean b= false;
	 		Category c=new Category();
	 		b=new Daoimple().updatecategory(c);
	 		ModelAndView mav=new ModelAndView("adminoperations");
	 		mav.addObject("msg", "editcategory");
	 		return mav;
		}
		@RequestMapping("/reqdeletecategory")
		public ModelAndView deleteCategory(@RequestParam("cid") String cid)
		{
			boolean b=false;
			ModelAndView mav=null;
			Category c=new Category();
			c.setCid(cid);
			b=new Daoimple().deleteCategory(c);
			if(b)
			{
			 mav=new ModelAndView();
			 mav.addObject("msg","Category deleted successfully");
			 mav.setViewName("redirect:reqviewallcategories");
			}
			return mav;
		}
		@RequestMapping("/reqdeletecart")
		public ModelAndView deleteCart(@RequestParam("pid") String pid)
		{
			boolean b=false;
			Cart c=new Cart();
			c.setPid(pid);
			b=new Daoimple().deleteCart(c);
			List<Cart> lcart=null;
			 lcart=new Daoimple().viewCart();
			 File f=new File("D://cloud");
			 File filenames[]=f.listFiles();
			 ModelAndView mav=new ModelAndView("welcomecustomer");
			 mav.addObject("cart",lcart); 
			 mav.addObject("filenames",filenames);
			 mav.addObject("filepath",f);
			 mav.addObject("msg","viewcartlist");
			 return mav;
		 
		}
		@RequestMapping("/reqsearchproducts")
		 public ModelAndView  searchProducts(@RequestParam("search") String search,HttpSession ses)
		 {
			 List<Product> al=null;
			 ses.setAttribute("search", search);
			 al=new Daoimple().searchProducts(search);
			
		     File f=new File("D://cloud");
		     File filenames[]=f.listFiles();
			 ModelAndView mav=new ModelAndView("welcomecustomer");
			 mav.addObject("products",al); 
			 mav.addObject("filenames",filenames);
			 mav.addObject("filepath",f);
			 mav.addObject("msg","search");
			 return mav;
		 }
		@RequestMapping("/reqviewproductbyid")
		public ModelAndView viewProductByID(@RequestParam("pid") String pid)
		{
			List<Product> al=null;
			 al=new Daoimple().viewProductById(pid);
		     File f=new File("D://cloud");
		     File filenames[]=f.listFiles();
			 ModelAndView mav=new ModelAndView("welcomecustomer");
			 mav.addObject("products",al); 
			 mav.addObject("filenames",filenames);
			 mav.addObject("filepath",f);
			 mav.addObject("msg","viewbyid");
			 return mav;
		}
		@RequestMapping("/reqaddtocart2")
		 public ModelAndView addToCart(@ModelAttribute("product") Product product)  
		 {
			 boolean b=false;
			 b=new Daoimple().addToCart(product);
			 List<Cart> lcart=null;
			 lcart=new Daoimple().viewCart();
			 File f=new File("D://cloud");
			 File filenames[]=f.listFiles();
			 ModelAndView mav=new ModelAndView("welcomecustomer");
			 mav.addObject("cart",lcart); 
			 mav.addObject("filenames",filenames);
			 mav.addObject("filepath",f);
			 mav.addObject("msg","viewcartlist");
			 return mav;
		 }
		@RequestMapping("/reqviewcart")
		public ModelAndView viewCart(@ModelAttribute("cart") Cart cart)
		{
			 List<Cart> lcart=null;
			 lcart=new Daoimple().viewCart();
			 File f=new File("D://cloud");
			 File filenames[]=f.listFiles();
			 ModelAndView mav=new ModelAndView("welcomecustomer");
			 mav.addObject("cart",lcart); 
			 mav.addObject("filenames",filenames);
			 mav.addObject("filepath",f);
			 mav.addObject("msg","viewcartlist");
			 mav.addObject("msg","total");
			 return mav;
		 
		}
		 @RequestMapping("/reqeditcat")
			public ModelAndView viewcatbyid(@ModelAttribute("cid") String cid)
			{ Category c=new Category();
			 
			   c=new Daoimple().viewcatbyid(cid);
			
			   ModelAndView mav=new ModelAndView("adminoperations");
			   mav.addObject("categroy",c);
			   mav.addObject("msg","viewcategorybyid");
		 	   return mav;
			}
			@RequestMapping("/requpdatecat")
			public ModelAndView requpdatecat(@ModelAttribute("category") Category category)
			{
			 boolean b=false;
			 b=new Daoimple().requpdatecat(category);
			 List<Category> clist=null;
			 clist=new Daoimple().viewAllCaterories();
			 
			 ModelAndView mav=new ModelAndView("adminoperations");
			 mav.addObject("msg","viewcat");
			 mav.addObject("clist",clist);
			 return mav;				
			}
	    @RequestMapping("/reqbuy")
	    public ModelAndView buy(@RequestParam("oamt") Double oamt)
	    {
	    	Date d=new Date();
	     	ModelAndView mav=new ModelAndView("welcomecustomer");
	    	mav.addObject("msg","orderinfo");
	    	mav.addObject("oamt",oamt);
	    	mav.addObject("odate",d.toString());
	    	return mav;
	    }
	    @RequestMapping("/reqaddaddress")
	    public ModelAndView addAddress()
	    {
	    	List<Address> l=new Daoimple().viewAddress();
	    	ModelAndView mav=new ModelAndView("welcomecustomer");
	    	mav.addObject("address",l);
    	   	mav.addObject("msg","addressinfo");
	    	return mav;
	    }
	    @RequestMapping("/reqaddress")
	    public ModelAndView address(@ModelAttribute("address") Address address)
	    {
	    	boolean b=false;
	    	List<Cart> lcart=null;
			 lcart=new Daoimple().viewCart();
			 Date d=new Date();
			 List<Address> l=new Daoimple().viewAddress();	
	    	ModelAndView mav=null;
	    	b=new Daoimple().addaddress(address);
	    	if(b)
	    	{
	    	 mav=new ModelAndView("welcomecustomer");
	    	mav.addObject("address",l);
	    
	    	mav.addObject("cart",lcart); 
	    	mav.addObject("odate",d.toString());
	    	mav.addObject("msg","tran");
	    	
	    	}
	    	return mav;
	    }
	    @RequestMapping("/reqexistingaddress")
	    public ModelAndView reqexistingaddress()
	    {
	    	boolean b=false;
	    	List<Cart> lcart=null;
	    	lcart=new Daoimple().viewCart();
			 Date d=new Date();
			 List<Address> l=new Daoimple().viewAddress();	
	    	ModelAndView mav=null;
	    	 mav=new ModelAndView("welcomecustomer");
	    	mav.addObject("address",l);
	    
	    	mav.addObject("cart",lcart); 
			 
	    	mav.addObject("odate",d.toString());
	    	mav.addObject("msg","tran");
	    	return mav;
	    }
	    
	    
	    @RequestMapping("/reqaddnewaddress")
	    public ModelAndView addnewaddress()
	    {
	    	
	    	ModelAndView mav=new ModelAndView("welcomecustomer");
	    	
	    	mav.addObject("msg","newaddress");
	    	
	    	return mav;
	    }
	    
	    @RequestMapping("/reqviewaddress")
	    public ModelAndView viewAddress()
	    {
	    	List<Address> l=new Daoimple().viewAddress();
	    	ModelAndView mav=new ModelAndView("welcomecustomer");
	    	mav.addObject("address",l);
	    	mav.addObject("msg","viewaddress");
	    	return mav;
	    }
	    @RequestMapping("/reqordersform")
	    public ModelAndView placeOrder()
	    {
	    	List<Orders> l=new Daoimple().viewOrders();
	    	 File f=new File("D://cloud");
			 File filenames[]=f.listFiles();
			 ModelAndView mav=new ModelAndView("welcomecustomer");
			 mav.addObject("orders",l); 
			 mav.addObject("filenames",filenames);
			 mav.addObject("filepath",f);
			mav.addObject("msg","orderinfo");
	    	return mav;
	    }
	    @RequestMapping("/reqorder")
	    public ModelAndView orders(@ModelAttribute("orders") Orders orders)
	    {
	    	boolean b=false;
	    	ModelAndView  mav=null;
	    	b=new Daoimple().orders(orders);
	    	if(b)
	    	{
	    	mav=new ModelAndView("welcomecustomer");
	    	mav.addObject("msg","vieworder");
	    	}
	    	return mav;
	    }
	    @RequestMapping("/reqtransactionform")
	    public ModelAndView startTransaction()
	    {
	    	List<Cart> lcart=null;
	    	lcart=new Daoimple().viewCart();
	    	ModelAndView mav=new ModelAndView("welcomecustomer");
	    	mav.addObject("cart",lcart); 
			mav.addObject("msg","transactions");
	    	return mav;
	    }
	    @RequestMapping("/reqtransaction")
	    public ModelAndView transactions(@ModelAttribute("transactions") Transactions transactions,@ModelAttribute("cart") Cart cart)
	    {
	    	boolean b=false;
	    	ModelAndView  mav=null;
	    	Orders o=new Orders();
	    	
	    	b=new Daoimple().transactions(transactions);
	    	Transactions tr=new Transactions();
	    	tr.setTid(transactions.getTid()); 
	    	List<Cart> lc=new Daoimple().viewCart();
	    	boolean b1=new Daoimple().myOrders(lc,tr);
	    	if(b && b1)
	    	{
	    	mav=new ModelAndView("welcomecustomer");
	    	mav.addObject("msg","order");
	    	}
	    	return mav;
	    }
	    
	    @RequestMapping("/reqsorting")
	    public ModelAndView sorting(@RequestParam("value") String value,HttpSession session)
	    {
	    	List<Product> al=null;
	    	
	    	System.out.println("value:"+value);
	    	String search=session.getAttribute("search").toString();
			al=new Daoimple().sortingProducts(search,value);
			 
			 File f=new File("D://cloud");
		     File filenames[]=f.listFiles();
		     System.out.println("value:"+value);
				
			 ModelAndView mav=new ModelAndView("welcomecustomer");
			 mav.addObject("products",al); 
			 mav.addObject("filenames",filenames);
			 mav.addObject("filepath",f);
			 mav.addObject("msg","search");
			 return mav;
		 
	    }
}
