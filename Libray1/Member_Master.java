/************************************************************
 * 
 * Created By Apoorva Gaurav Tiwari 
 * 
 */
import java.sql.*;

public class Member_Master 
    {

        MemberNode root;
        Connection conn1 = null;
	    String url1 = "jdbc:mysql://localhost:3306/librarymgm";
        String user = "root";
        String password = "Am@130304";

        void LoadMember()
        {
            try
            {
                Statement statement =conn1.createStatement();  
                ResultSet resultSet = statement.executeQuery("Select * from member order by Id asc");
                while(resultSet.next())  
                {
                    String name=resultSet.getString("Member_Name");
                    String Stream=resultSet.getString("Member_Stream");
                    int id_no=resultSet.getInt("Member_id");
                    Member key=new Member(name,id_no,Stream);
                    key.book1=resultSet.getString("book1");
                    key.book2=resultSet.getString("book2");
                    key.duedt1=resultSet.getDate("duedt1");
                    key.duedt2=resultSet.getDate("duedt2");
                    key.book_no=resultSet.getInt("book_no");
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
                
        public Member_Master() 
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
            LoadMember();
	    }   
        
    
        //Insert Member
        void insert(Member key) 
        { 
            try
            {
                int flag=0;
                Statement statement =conn1.createStatement();  
                ResultSet resultSet = statement.executeQuery("Select Count(*) from member where Member_id='"+key.id_no+"'");
                while(resultSet.next())  
                {
                    flag=resultSet.getInt(1);
                    
                }
                resultSet.close();
                if(flag==0)
                {
                    statement.executeUpdate("Insert into member(Member_Name,Member_Stream,Member_id) values('"+key.name+"','"+key.Stream+"',"+key.id_no+")");
                }

                root = insertRec(root, key);
                
            }
            catch (SQLException ex) 
            {
                System.out.println("An error occurred. On Insert data.");
                ex.printStackTrace();
            }

            
        } 
    
        MemberNode insertRec(MemberNode root, Member key) 
        { 
            if(root == null) 
            { 
                root = new MemberNode(key); 
                return root; 
            }
            else
            {
                root.Next= insertRec(root.Next, key); 
            } 
            
            
    
            return root; 
        } 

        MemberNode SearchMember(int Roll) 
        { 
            MemberNode home=root;
            if(home == null) 
            { 
               return home; 
            }
            else
            {
                for(home=root;home!=null;home=home.Next)
                {
                    if(home.data.id_no==Roll)
                    {
                        break;
                    }
                }
                                
            } 
            
            
    
            return home; 
        } 
        
        void UpdateMember(MemberNode node,Member newData)
        {
            try
            {
                Statement statement =conn1.createStatement();  
                int sqlEx = statement.executeUpdate("Update Member set Member_Name='"+newData.name+"',Member_Stream='"+newData.Stream+"' where Member_id='"+node.data.id_no+"'");
                if (sqlEx>0)
                {
                    node.data=newData;
                }
            }
            catch (SQLException ex) 
            {
                System.out.println("An error occurred. On Update data.");
                ex.printStackTrace();
            }

            
        }

        void DeleteMember(MemberNode node)
        {
            
            try
            {
                Statement statement =conn1.createStatement();
                statement.executeUpdate("Delete from Member where Member_id='"+node.data.id_no+"'");
            }
            catch (SQLException ex) 
            {
                System.out.println("An error occurred. On delete data.");
                ex.printStackTrace();
            }    

           if (root == node) 
            {
                root=node.Next;            
                return;
            }

            MemberNode prevNode;
            MemberNode nextNode=node.Next;
            for(prevNode=root;prevNode.Next!=null;prevNode=prevNode.Next)
                {
                    if(prevNode.Next==node)
                    {
                            prevNode.Next=nextNode;
                            break;
                    }
                } 
                
              
            
            return;
        }
        
        void printSingleMember(MemberNode node) 
        { 
            if (node == null) 
                return; 
                            
            System.out.println("Name of Member is: " + node.data.name);
            System.out.println("Member Id is: " + node.data.id_no);
            System.out.println("Member Stream is: " +  node.data.Stream);
            System.out.println("Number of book issued: " +  node.data.book_no);
            System.out.println("Book1 Name: " +  node.data.book1);
            System.out.println("Book2 Name: " +  node.data.book2);
            System.out.println();

            
    
            
        } 

        void printMember(MemberNode node) 
        { 
            if (node == null) 
                return; 
                            
            System.out.println("Name of Member is: " + node.data.name);
            System.out.println("Member Id is: " + node.data.id_no);
            System.out.println("Member Stream is: " +  node.data.Stream);
            System.out.println("Number of book issued: " +  node.data.book_no);
            System.out.println("Book1 Name: " +  node.data.book1);
            System.out.println("Book2 Name: " +  node.data.book2);
            System.out.println();

            printMember(node.Next); 
    
            
        } 
    
        void printMember()    
        {
            printMember(root);  
        } 
}
