package ecommerce.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import ecommerce.model.Address;
import ecommerce.model.AdminLogin;
import ecommerce.model.Cart;
import ecommerce.model.Category;
import ecommerce.model.Customer;
import ecommerce.model.Orders;
import ecommerce.model.Product;
import ecommerce.model.Transactions;



public class Daoimple 
 {
	SessionFactory sf=null;
	public Daoimple()
	{
		Configuration cfg= new Configuration().configure().addAnnotatedClass(AdminLogin.class).addAnnotatedClass(Product.class).addAnnotatedClass(Category.class).addAnnotatedClass(Customer.class).addAnnotatedClass(Cart.class).addAnnotatedClass(Orders.class).addAnnotatedClass(Address.class).addAnnotatedClass(Transactions.class);
		sf=cfg.buildSessionFactory();

	}
	public boolean adminLogin(AdminLogin al)
	{
		boolean b=false;
		Session ses=sf.openSession();
		Query q=ses.createQuery("from AdminLogin where uid=? and pwd=?");
		q.setParameter(0, al.getUid());
		q.setParameter(1, al.getPwd());
		if(q.uniqueResult()!=null)
		{
			b=true;
		}
		ses.close();
		return b;
	}
	public boolean customerRegister(Customer c)
	{
		boolean b=false;
		Session ses=sf.openSession();
		Transaction tx=ses.beginTransaction();
		ses.save(c);
		b=true;
		tx.commit();
        ses.close();
		
		return b;
	}
	public boolean customerLogin(Customer c)
	{
		boolean b=false;
		Session ses=sf.openSession();
		Query q=ses.createQuery("from Customer where email=? and pwd=?");
		q.setParameter(0, c.getEmail());
		q.setParameter(1, c.getPwd());
		if(q.uniqueResult()!=null)
		{
			b=true;
		}
		ses.close();
		return b;
	}
	
	public boolean addProduct(Product p)
	{  
		boolean b=false;
        Session ses=sf.openSession();
        Transaction tx=ses.beginTransaction(); 
        Object obj=ses.save(p);
        if(obj!=null)
        {
        	b=true;
        }
        tx.commit();
        ses.close();
		
		return b;
	}
	public List<Product> viewAllProducts() 
	{
		List<Product> al=new ArrayList<Product>();
		Session ses=sf.openSession();
		Query q=ses.createQuery("from Product");
		al=q.list();
		ses.close();
		return al;
	}
	public boolean deleteProduct(Product p)
	{
		boolean b=false;
		List<Product> al=viewAllProducts();
		Session session=sf.openSession();
		Transaction t=session.beginTransaction();
		session.delete(p);
		b=true;
		t.commit();
		session.close();
		File f=new File("F://cloud");
		File filenames[]=f.listFiles();
		for(File f1:filenames)
		{
			for(Product p1:al)
			{
				if(p1.getFilename().equals(f1.getName()) && p.getPid().toString().equals(p1.getPid().toString()))
				{
					f1.delete();
				}
			}
		}
		
		return b;
	}
	public boolean addCategory(Category cat) 
	{
		boolean b=false;
		Session session=sf.openSession();
		Transaction tx=session.beginTransaction();
		session.save(cat);
		tx.commit();
		b=true;
		session.close();
		
		return b;
	}
	public List<Category> viewAllCaterories()
	{
		Session session=sf.openSession();
		List<Category> clist;
		clist=session.createQuery("from Category").list();
		session.close();
		
		return clist;
	}
	public List<Product> displayProducts(String catname) 
	{
		List<Product> plist=null;
		Session session=sf.openSession();
		Query q=session.createQuery("from Product where ctype=?");
		q.setParameter(0, catname);
		plist=q.list();
		session.close();
		
		return plist;
		
	}
	public boolean deleteCategory(Category c) 
	{
		boolean b=false;
		Category c1=new Category();
		Session session1=sf.openSession();
		c1=(Category)session1.get(Category.class, c.getCid());    // categories info:  Cid , Cname
		session1.close();
		Session session2=sf.openSession();
		Transaction tx=session2.beginTransaction();
		session2.delete(c);
		
		Query q=session2.createQuery("delete from Product where ctype=?");
		q.setParameter(0, c1.getCatname());
		q.executeUpdate();
		tx.commit();
		b=true;
		session2.close();
		
		return b;
		
	}
	public boolean deleteCart(Cart c)
	{
		boolean b=false;
		Session ses=sf.openSession();
		c=(Cart)ses.get(Cart.class, c.getPid());
		Transaction tx=ses.beginTransaction();
		ses.delete(c);
		tx.commit();
		ses.close();
		return b;
	}
	public List<Product> searchProducts(String search) 
	{
		Product p=new Product();
		p.setPname(search);
		p.setCtype(search);
		List<Product> al=new ArrayList<Product>();
		Session ses=sf.openSession();
		Query q=ses.createQuery("from Product where pname like '%"+p.getPname()+"%' or ctype like '%"+p.getCtype()+"%'");
		al=q.list();
		ses.close();
		return al;
	}
	public List<Product> viewProductById(String pid)
	{
		Product p=new Product();
		p.setPid(pid);
		List<Product> al=new ArrayList<Product>();
		Session ses=sf.openSession();
		Query q=ses.createQuery("from Product where pid='"+p.getPid()+"'");
		al=q.list();
		ses.close();
		return al;
	}
	public boolean updateproduct(Product p)
	{
		boolean b=false;
		Session ses=sf.openSession();
	    Transaction tx=ses.beginTransaction(); 
	    ses.update(p);
	    tx.commit();
	    ses.close();
		return b;
	
	}
	public boolean addToCart(Product p)
	{
		boolean b=false;
		Cart c=new Cart();
		c.setPid(p.getPid());
		c.setPname(p.getPname());
		c.setPrice(p.getPprice());
		c.setFilename(p.getFilename());
		c.setQty(p.getPqty());
		Session ses=sf.openSession();
        Transaction tx=ses.beginTransaction(); 
        Object obj=ses.save(c);
        tx.commit();
        if(obj!=null)
        {
        	b=true;
        }
        ses.close();
		return b;
	}
	public List<Cart> viewCart()
	{
		List<Cart> lcart=null;
		Session ses=sf.openSession();
		Query q=ses.createQuery("from Cart");
		lcart=q.list();
		ses.close();
		return lcart;
	}
	public List<Cart> addToCart(String pid) 
	{
		List<Cart> lcart=null;
		boolean b=false;
		Product p=null;
		List<Product> lp=null;
		lp=viewProductById(pid);   // product info
		System.out.println("pid"+p.getPid());
		Cart  c=new Cart();
		c.setPid(p.getPid());
		c.setPname(p.getPname());
		c.setPrice(p.getPprice());
		c.setQty(1);
		c.setFilename(p.getFilename());
		
		Session session =sf.openSession();
		Transaction tx=session.beginTransaction();
		session.save(c);
		tx.commit();
		b=true;
		session.close();
		
		Session session1=sf.openSession();
		Query  q=session1.createQuery("from Cart");
		lcart=q.list();
		
		session1.close();
		return lcart;
	}
	public Category viewcatbyid(String cid)
	{
	  boolean b=false;
	  Category c=new Category();
	  
	  Session ses=sf.openSession();
	  c=(Category)ses.get(Category.class,cid);
	
	 
	  return c;
	}
	public boolean requpdatecat(Category category)
	{boolean b=false;
	 Session ses=sf.openSession();
     Transaction tx=ses.beginTransaction(); 
    ses.update(category);
     b=true;
     tx.commit();
     ses.close();
		return b;
		
		
	}
	public boolean updatecategory(Category c)
	{
		boolean b=false;
		Session ses=sf.openSession();
	    Transaction tx=ses.beginTransaction(); 
	    ses.update(c);
	    tx.commit();
	    ses.close();
		return b;
	
	}
	public boolean orders(Orders orders)
	{
		 boolean b=false;
         Session session=sf.openSession();
         Transaction tx=session.beginTransaction();
         session.save(orders);
         tx.commit();
         b=true;
         session.close();
	     return b;
	}
	public boolean transactions(Transactions transactions)
	{
		 boolean b=false;
        Session session=sf.openSession();
        Transaction tx=session.beginTransaction();
        session.save(transactions);
        tx.commit();
        b=true;
        session.close();
	     return b;
	}
	public boolean addaddress(Address address) 
	{
		boolean b=false;
		Session session=sf.openSession();
		Transaction tx=session.beginTransaction();
		session.save(address);
		tx.commit();
		b=true;
		session.close();
		return b;
	}
	public ArrayList<Product> sortingProducts(String search,String value)
	{
		Product p=new Product();
		p.setPname(search);
		p.setCtype(search);
		ArrayList<Product> al=new ArrayList<Product>();
		Session ses=sf.openSession();
		Query q=null;
		System.out.println("value:"+value);
	//	if(value.equals("lh"))
	//	{
			q=ses.createQuery("from Product where pname like '%"+p.getPname()+"%' or ctype like '%"+p.getCtype()+"%'  order by pprice");
	/*	}
		else if(value.equals("hl"))
		{
			q=ses.createQuery("from Product where pname like '%"+p.getPname()+"%' or ctype like '%"+p.getCtype()+"%'  order by pprice desc");
		}*/
		al=(ArrayList<Product>) q.list();
		ses.close();
		return al;
	
	}
	public List<Address> viewAddress()
	{
		List<Address> l=null;
		Session ses=sf.openSession();
		Query q=ses.createQuery("from Address");
		
		l=q.list();
		System.out.println("l:"+l);
		ses.close();
		return l;
	}
	public boolean myOrders(List<Cart> lc,Transactions tr)// oid,tid,odate,pname,filename.qty 
	{
		boolean b=false;
		Orders o=new Orders();
		
		Session ses=sf.openSession();
		
		tr=(Transactions)ses.get(Transactions.class,tr.getTid());
	
		for(Cart c:lc)
		{
			o.setTid(tr.getTid());
			o.setOdate(tr.getTdate());
			o.setFilename(c.getFilename());
			o.setPname(c.getPname());
			o.setPrice(c.getPrice());
			o.setQty(c.getQty());
			Session ses1=sf.openSession();
			Transaction tx=ses1.beginTransaction();
			ses1.save(o);
			tx.commit();
			Session ses2=sf.openSession();
			Transaction t2=ses2.beginTransaction();
			ses2.delete(c);
			t2.commit();
			ses2.close();
		}
		b=true;
		return b;
	}
	public List<Orders> viewOrders() 
	{
		List<Orders> l=null;
		Session ses=sf.openSession();
		Query q=ses.createQuery("from Orders");
		l=q.list();
		ses.close();
		return l;
	}
	
	
	
}
