/************************************************************
 * 
 * Created By Ananya Mondal
 * 
 */

import java.util.Date; 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.sql.*;

public class Issue_Return
{
  

    private static Scanner input;
	Connection conn1 = null;
	String url1 = "jdbc:mysql://localhost:3306/librarymgm";
    String user = "root";
    String password = "Am@130304";

	public Issue_Return() 
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
	
	} 

  
    void return_book(Member_Master Std_Mas,book_master tree)
	{
        input = new Scanner(System.in);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd"); 
		Date Rday1 = null,Rday2 = null, Tday1=null;
		Calendar cal = Calendar.getInstance();
		try
						{
							
							System.out.println("\nEnter your member id:");
							int s_id = Integer.parseInt(input.nextLine());
							Boolean y=false;
							MemberNode RSt;							
							RSt=Std_Mas.SearchMember(s_id);
							if(RSt!=null)
							{
								System.out.println("\nEnter name of book:");
								String Rbook = input.nextLine();
								if(RSt.data.book1!=null && RSt.data.book1.equalsIgnoreCase(Rbook))
								{
									
										BookNode x=tree.SearchNode(Rbook);
										if(x!=null)
										{
											Rday1=RSt.data.duedt1;
											
											try
											{
												Statement statement =conn1.createStatement();  
												int sqlEx = statement.executeUpdate("Update Member Set book1=null,duedt1=null,book_no=book_no-1 where Member_id="+RSt.data.id_no);
												if (sqlEx>0)
												{
													RSt.data.book1=null;
													RSt.data.duedt1=null;
													RSt.data.book_no--;
												}
											}
											catch (SQLException ex) 
											{
												System.out.println("An error occurred. On Update Member data.");
												ex.printStackTrace();
											}

											try
											{
												Statement statement =conn1.createStatement();  
												int sqlEx = statement.executeUpdate("Update book set AvlQty=AvlQty+1 where Book_name='"+x.key.name+"'");
												if (sqlEx>0)
												{
													x.key.AvlQty++;
												}
											}
											catch (SQLException ex) 
											{
												System.out.println("An error occurred. On Update book data.");
												ex.printStackTrace();
											}	
											y=true;
										}
										else
										{
											System.out.println("\nBook is not found");

										}
									
									
								}
								else if(RSt.data.book2!=null && RSt.data.book2.equalsIgnoreCase(Rbook))
								{
									
										BookNode x=tree.SearchNode(Rbook);
										if(x!=null)
										{
											Rday1=RSt.data.duedt2;
											
											try
											{
												Statement statement =conn1.createStatement();  
												int sqlEx = statement.executeUpdate("Update Member Set book2=null,duedt2=null,book_no=book_no-1 where Member_id="+RSt.data.id_no);
												if (sqlEx>0)
												{
													RSt.data.book2=null;
													RSt.data.duedt2=null;
													RSt.data.book_no--;
												}
											}
											catch (SQLException ex) 
											{
												System.out.println("An error occurred. On Update Member data.");
												ex.printStackTrace();
											}

											try
											{
												Statement statement =conn1.createStatement();  
												int sqlEx = statement.executeUpdate("Update book set AvlQty=AvlQty+1 where Book_name='"+x.key.name+"'");
												if (sqlEx>0)
												{
													x.key.AvlQty++;
												}
											}
											catch (SQLException ex) 
											{
												System.out.println("An error occurred. On Update book data.");
												ex.printStackTrace();
											}	


											y=true;
										}
										else
										{
											System.out.println("\nBook is not found");

										}

										
									
								}
								
								else
								{
									System.out.println("Book is not issued to this member.");
								}


							}
							else
							{
								System.out.println("Invalid ID");
							}

							
							
							
						
							
							
							if(y==true)
							{
								
								
								
								cal = Calendar.getInstance();
								Rday2=cal.getTime();
								
								
								if(Rday2.after(Rday1))
								{
					                System.out.println("Book is overdue.");
					                long diff=Rday2.getTime()-Rday1.getTime(); //calculating differnce between due date and return date.
					                int noofdays=(int)(diff/(24*60*60)); //dividing by one day in second to get number of day book kept.
					                System.out.println("Due Date Time: " + formatter.format(Rday2));
					                System.out.println("book is delayed by " + noofdays + "days.");
					                double charge =noofdays*5; // Rs 5 is fine for each day after due date.
					                System.out.println("Your charge is: " + charge + "Rs." );
					            }
								else
								{
									System.out.println("Book is returned successfully.");
								}
								
								
								
							}
							
						}
						catch(Exception e)
						{
							System.out.println("Something is going to be wrong.");
						}

	}	

    void issue_book(Member_Master Std_Mas,book_master tree)
	{
        input = new Scanner(System.in);
		

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd"); 
		Date Rday1 = null,Rday2 = null, Tday1=null;
		Calendar cal = Calendar.getInstance();
		System.out.println("\nEnter your member id:");
							int id = Integer.parseInt(input.nextLine());

							MemberNode St;
							St=Std_Mas.SearchMember(id);
														
							if(St!=null)
							{
								if(St.data.book_no==2)
								{
									System.out.println("\nYou can't issue more than two books.");
								}
								else
								{
									System.out.println("\nEnter name of book:");
									String book = input.nextLine();
									BookNode x=tree.SearchNode(book);
									if(x!=null)
									{
										
										if(x.key.AvlQty>0)
										{
											Tday1=cal.getTime();
											System.out.println("Currunt Date Time : " + formatter.format(Tday1));
											cal.add(Calendar.SECOND, (24*7*60*60)); // Return date after 7 days calating 7 day in second.
											Rday1 = cal.getTime();
											System.out.println("Due Date Time: " + formatter.format(Rday1));
											String updateSql="Update Member Set ";
											if(St.data.book1==null)
											{
												St.data.book1=book;
												St.data.duedt1=Rday1;
												updateSql+=" book1='"+book+"', ";
												updateSql+=" duedt1='"+formatter1.format(Rday1)+"', ";
											}
											else
											{
												St.data.book2=book;
												St.data.duedt2=Rday1;
												updateSql+=" book2='"+book+"', ";
												updateSql+=" duedt2='"+formatter1.format(Rday1)+"', ";
											}
												
											
											updateSql+=" book_no=book_no+1 Where Member_id="+St.data.id_no;
											try
											{
												Statement statement =conn1.createStatement();  
												int sqlEx = statement.executeUpdate(updateSql);
												if (sqlEx>0)
												{
													St.data.book_no++;
												}
											}
											catch (SQLException ex) 
											{
												System.out.println("An error occurred. On Update Member data.");
												ex.printStackTrace();
											}

											try
											{
												Statement statement =conn1.createStatement();  
												int sqlEx = statement.executeUpdate("Update book set AvlQty=AvlQty-1 where Book_name='"+x.key.name+"'");
												if (sqlEx>0)
												{
													x.key.AvlQty--;
												}
											}
											catch (SQLException ex) 
											{
												System.out.println("An error occurred. On Update book data.");
												ex.printStackTrace();
											}	

											System.out.println("Book issued successfully.");
											
											
										}
										else
										{
											System.out.println("\nAll copy of this book is issued.\nYou can not issue book now.\nTry again after some days");
										}
											
									}
									else
									{
										System.out.println("\nBook is not available.");
									}
									
								}
							
							}
							else
							{
								System.out.println("\nInvalid ID");
							}
	}

}