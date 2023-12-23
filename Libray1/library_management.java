/************************************************************
 * 
 * Created By Ananya Mondal
 * 
 */
import java.util.Date; 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class library_management 
{
	
	private static Scanner input;
	
	public static void main(String[] args) throws Exception
	{ 
		
		input = new Scanner(System.in);
		//User Id and Password for login as librarian
		String id1="LibSys";
		String pwd1="Lib@123";
		

		
		book_master tree = new book_master();
	 	Member_Master Std_Mas = new Member_Master();
		Issue_Return Iss_Ret = new Issue_Return();
		
		
		boolean e1=false;
		
		
		
		
		while(e1==false)
		{
			System.out.println("\n....................................." );
			System.out.println("1. Book Master. ");
			System.out.println("2. Member Master. ");
			System.out.println("3. Issue/Return Book. ");
			System.out.println("4. Exit. ");
			System.out.println("\n....................................." );
			
			System.out.println("\nEnter Your choice:");
			int ch1 = Integer.parseInt(input.nextLine());
			
			
			switch(ch1) 
			{
			case 1:		//For Librarian login
				
				
				System.out.println("\nEnter UserId:" );
				String id2 = input.nextLine();
				
				System.out.println("\nEnter Password:" );
				String pwd2=input.nextLine();
				
				if(!id1.equals(id2))
					System.out.println("Invalid Userid.");
				else if(!pwd1.equals(pwd2))
					System.out.println("Invalid Password.");
				else 
				{
					System.out.println("Login succesfully.");
					boolean e2=false;
					while(e2==false)
					{
						System.out.println("\n....................................." );
						System.out.println("1. Add book. ");
						System.out.println("2. Delete book. ");
						System.out.println("3. Update book. ");
						System.out.println("4. Search Books Details. ");
						System.out.println("5. Print All Books Details. ");
						System.out.println("6. Return To Main Menu. ");
						
						System.out.println("\n....................................." );
						
						System.out.println("\nEnter Your choice:");
						int ch2 = Integer.parseInt(input.nextLine());
						
						switch(ch2)
						{
							case 1: 	//To add a book
									
									System.out.println("\nEnter name of book:");
									String BookName=input.nextLine();
									
							    	BookNode z1=tree.SearchNode(BookName);
									
									if(z1!=null)
									{
										System.out.println("\nIt is already exists:");
									}
									else
									{
										System.out.println("\nEnter name of Author:");
										String Auth_Name=input.nextLine();
										System.out.println("\nEnter quantity of book:");
										int quantity = Integer.parseInt(input.nextLine());
										Book b=new Book(BookName,Auth_Name,quantity,quantity);			
										tree.insert(b);
										System.out.println("\nBook is added.");
									}
							break;
							
							case 2:			//To delete a book
								
									System.out.println("\nEnter name of book:");
									String b1 = input.nextLine();
									
									BookNode x=tree.SearchNode(b1);
									if(x!=null)
									{
										tree.deleteKey(b1);
										System.out.println("\nBook is Deleted.");
									}
									else
									{
										System.out.println("\nBook not found.");
									}
							break;
							case 3:		//To update any book
								
									System.out.println("\nEnter name of book:");
									String b2 = input.nextLine();
									
									BookNode z=tree.SearchNode(b2);
									if(z!=null)
									{
										System.out.println("\nEnter name of Author:");
										String uAuth_Name=input.nextLine();
										System.out.println("\nEnter quantity of book to add more:");
										int q = Integer.parseInt(input.nextLine());
										Book bu=new Book(b2,uAuth_Name,z.key.Qty+q,z.key.AvlQty+q);
										tree.UpdateBook(z,bu);
										System.out.println("\nBook is Updated.");

									}
									else
									{
										System.out.println("\nBook not found.");
									}
							break;

							case 4:		//Search Books Details
								
								    System.out.println("\nEnter name of book:");
									String b3= input.nextLine();
									
									BookNode zs=tree.SearchNode(b3);
									if(zs!=null)
									{
										tree.printBook(zs);
																			
									}
									else
									{
                                        System.out.println("\nNo book found.");
									}


									 
									
							break;
							
							case 5:		//Print All Books Details
								
									 tree.printInorder();
									
							break;
							
							
							
							case 6:
								e2=true;
								break;
								
						}
					}
				}		
			break;

			case 2: //For Librarian login
	
								
				System.out.println("\nEnter UserId:" );
				String id4 = input.nextLine();
				
				System.out.println("\nEnter Password:" );
				String pwd4=input.nextLine();
				
				if(!id1.equals(id4))
					System.out.println("Invalid Userid.");
				else if(!pwd1.equals(pwd4))
					System.out.println("Invalid Password.");
				else 
				{
					System.out.println("Login succesfully.");
					boolean e2=false;
					while(e2==false)
					{
						System.out.println("\n....................................." );
						System.out.println("1. Add Member. ");
						System.out.println("2. Delete Member. ");
						System.out.println("3. Update Member. ");
						System.out.println("4. Search Member. ");
						System.out.println("5. Print All Member Details. ");
						System.out.println("6. Return To Main Menu. ");
						
						System.out.println("\n....................................." );
						
						System.out.println("\nEnter Your choice:");
						int ch2 = Integer.parseInt(input.nextLine());
						
						switch(ch2)
						{
							case 1: 	//To add a Member
									
									System.out.println("\nEnter Member Name:");
									String MemberName=input.nextLine();
									System.out.println("\nEnter Member Id:");
									int Memberid=Integer.parseInt(input.nextLine());

									MemberNode St;
									St=Std_Mas.SearchMember(Memberid);
									if(St==null)
									{
										System.out.println("\nEnter Member Stream:");
										String MemberStream=input.nextLine();
										Member Si=new Member(MemberName,Memberid,MemberStream);
										Std_Mas.insert(Si);
										System.out.println("\nMember is added.");
									}
									else
									{
										System.out.println("\nMember is already exists:");
									}
									

									
							break;
							
							case 2:			//To delete a Member
								
									System.out.println("\nEnter Member Id:");
									int Delid=Integer.parseInt(input.nextLine());
									
									MemberNode DSt;
									DSt=Std_Mas.SearchMember(Delid);
									if(DSt!=null)
									{
										Std_Mas.DeleteMember(DSt);
										System.out.println("\nMember is Deleted.");
									}
									else
									{
										System.out.println("\nMember Not Found");
									}
									
							break;
							case 3:		//To update any Member
								
									System.out.println("\nEnter Member Id:");
									int Upid=Integer.parseInt(input.nextLine());
									
									MemberNode USt;
									USt=Std_Mas.SearchMember(Upid);
									if(USt!=null)
									{
										System.out.println("\nEnter Member Name:");
										String uName=input.nextLine();
										System.out.println("\nEnter Member Stream:");
										String uStream=input.nextLine();
										Member Su=new Member(uName,Upid,uStream);

										Std_Mas.UpdateMember(USt,Su);
										System.out.println("\nMember is Updated.");
									}
									else
									{
										System.out.println("\nMember Not Found");
									}
							break;
							case 4:		//Search Member Details
									System.out.println("\nEnter Member Id:");
									int Memid=Integer.parseInt(input.nextLine());
									MemberNode St1;
									St1=Std_Mas.SearchMember(Memid);
									if(St1!=null)
									{
										Std_Mas.printSingleMember(St1);
									}
									else
									{
										System.out.println("\nNo Member Found.");
									}
									
									
							break;
							
							case 5:		//Print All Member Details
								
									 Std_Mas.printMember();
									
							break;
							
							
							
							case 6:
								e2=true;
								break;
								
						}
					}
				}		
			break;
			
			case 3:		//For User Login
				
				boolean e3=false;
				while(e3==false)
				{
				System.out.println("\n....................................." );
				System.out.println("1. Issue book. ");
				System.out.println("2. Return book. ");
				System.out.println("3. Return To Main Menu. ");
				System.out.println("\n....................................." );
				
				System.out.println("\nEnter Your choice:");
				int ch3 = Integer.parseInt(input.nextLine());
				
				switch(ch3)
				{
					case 1://To issue a book
						
							Iss_Ret.issue_book(Std_Mas,tree);
							break;
				
					case 2://to return a book
						
						Iss_Ret.return_book(Std_Mas,tree);
						break;
	
					case 3:
						e3=true;
	
					break;
				}
				}
			break;
			
			case 4:
				e1=true;
				
			break;
			}
			
		}
		
		
	}
}