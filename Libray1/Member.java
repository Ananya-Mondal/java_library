/************************************************************
 * 
 * Created By Shreya  
 * 
 */
import java.util.Date; 
public class Member
{
	String name;
	int id_no;
	String Stream;
	String book1,book2;
	Date duedt1,duedt2;
	int book_no;
	
	Member(String name,int id_no,String Stream)
	{
		this.name=name;
		this.id_no=id_no;
		this.Stream=Stream;
		
	}
}