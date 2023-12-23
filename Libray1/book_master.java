/************************************************************
 * 
 * Created By Satyabrat Sahoo 
 * 
 */
import java.sql.*;


public class book_master
	{

	 BookNode root;
	 Connection conn1 = null;
	 String url1 = "jdbc:mysql://localhost:3306/librarymgm";
     String user = "root";
     String password = "Am@130304";
 
            
	void LoadData()
	{
		try
		{
			Statement statement =conn1.createStatement();  
        	ResultSet resultSet = statement.executeQuery("Select * from book order by Book_id asc");
			while(resultSet.next())  
			{
				String name1=resultSet.getString("Book_name");
				String Author1=resultSet.getString("Author_name");
				int Qty1=resultSet.getInt("Qty");
				int AvlQty1=resultSet.getInt("AvlQty");
                Book key=new Book(name1,Author1,Qty1,AvlQty1) ;
				insert(key);
				
				
            }  
			resultSet.close();   
		}
		catch (SQLException ex) 
		{
            System.out.println("An error occurred. On fecthing data.");
            ex.printStackTrace();
        }
		 

	}
		
	public book_master() 
	{
		try 
		{
			conn1 = DriverManager.getConnection(url1, user, password);
		} 
		catch (SQLException ex) 
		{
            System.out.println("An error occurred. Maybe db user/password is invalid");
            ex.printStackTrace();
        }
		root = null;  
		LoadData();
	} 

	//Insert Book
	void insert(Book key) 
	{ 
		try
		{
			int flag=0;
			Statement statement =conn1.createStatement();  
			ResultSet resultSet = statement.executeQuery("Select Count(*) from book where Book_name='"+key.name+"'");
			while(resultSet.next())  
			{
				flag=resultSet.getInt(1);
				
			}
			resultSet.close();
			if(flag==0)
			{
				statement.executeUpdate("Insert into book(Book_name,Author_name,Qty,AvlQty) values('"+key.name+"','"+key.Author+"',"+key.Qty+","+key.AvlQty+")");
			}

			root = insertRec(root, key);
        	
		}
		catch (SQLException ex) 
		{
            System.out.println("An error occurred. On Insert data.");
            ex.printStackTrace();
        }

		
	} 

	BookNode insertRec(BookNode root, Book key) 
	{ 
		if(root == null) 
		{ 
			root = new BookNode(key); 
			return root; 
		} 
		
		if (key.name.compareTo(root.key.name)<0) //If book name < root then place it as left child
			root.left = insertRec(root.left, key); 
		else if (key.name.compareTo(root.key.name)>0) //If book name > root then place it as Right child
			root.right = insertRec(root.right, key); 
		else
			System.out.println("error.");

		return root; 
	} 
	
	
	//Search Book
	public BookNode SearchNode(String value) 
	{
		return SearchNodeRecursive(root, value);
	}

	private BookNode SearchNodeRecursive(BookNode current, String key) 
	{
		if (current == null) 
		{
			return null;
		} 
		//If book name < root then place it as left child
		if (key.equalsIgnoreCase(current.key.name)) 
		{
			return current;
		} 
		
		

		//If book name < root then search at left side of root else right side
		return key.compareTo(current.key.name)<0
		? SearchNodeRecursive(current.left, key)
		: SearchNodeRecursive(current.right, key);
	}

    void UpdateBook(BookNode node,Book newData)
        {
            try
            {
                Statement statement =conn1.createStatement();  
                int sqlEx = statement.executeUpdate("Update BooK set Author_name='"+newData.Author+"',Qty="+newData.Qty+",AvlQty="+newData.AvlQty+" where Book_name='"+node.key.name+"'");
                if (sqlEx>0)
                {
                    node.key=newData;
                }
            }
            catch (SQLException ex) 
            {
                System.out.println("An error occurred. On Update data.");
                ex.printStackTrace();
            }

            
        }
	
	void deleteKey(String key) 
    {
		try
		{
			Statement statement =conn1.createStatement();  
        	int sqlEx = statement.executeUpdate("Delete from book where Book_name='"+key+"'");
			if (sqlEx>0)
			{
				 root = deleteRec(root, key); 
			}
		}
		catch (SQLException ex) 
		{
            System.out.println("An error occurred. On delete data.");
            ex.printStackTrace();
        }

    } 
  
    BookNode deleteRec(BookNode root, String key) 
    { 
        if (root == null)  return root; 
  
      //If book name < root then search it at left side and delete
        if (key.compareTo(root.key.name)<0) 
            root.left = deleteRec(root.left, key); 
      //If book name > root then search it at right side and delete
        else if (key.compareTo(root.key.name)>0) 
            root.right = deleteRec(root.right, key); 
  
        else
        { 
            if (root.left == null) 
                return root.right; 
            else if (root.right == null) 
                return root.left; 
  
            root.key = minValue(root.right); 
  
            root.right = deleteRec(root.right, root.key.name); 
        } 
  
        return root; 
    } 
  
    Book minValue(BookNode root) 
    { 
        Book minv = root.key; 
        while (root.left != null) 
        { 
            minv = root.left.key; 
            root = root.left; 
        } 
        return minv; 
    } 
	
	void printInorder(BookNode node) 
	{ 
		if (node == null) 
			return; 

		printInorder(node.left); 

		System.out.println("Name of book is: " + node.key.name);
		System.out.println("Name of Author is: " + node.key.Author);
		System.out.println("Total Quantity of book is: " + node.key.Qty);
		System.out.println("Available Quantity of book is: " +  node.key.AvlQty);
		System.out.println();
		

		printInorder(node.right); 
	} 

	void printInorder()    
	{
		printInorder(root);  
	} 

	void printBook(BookNode node) 
	{ 
		if (node == null) 
			return; 

		

		System.out.println("Name of book is: " + node.key.name);
		System.out.println("Name of Author is: " + node.key.Author);
		System.out.println("Total Quantity of book is: " + node.key.Qty);
		System.out.println("Available Quantity of book is: " +  node.key.AvlQty);
		System.out.println();
		

		
	}


	}