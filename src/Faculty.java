//coded by Chetan Pardhi
//inherited by Institute.java
//to make announcement....to send personal mails....
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class Faculty extends Institute {
    Scanner sc=new Scanner(System.in);	
    Scanner sc1=new Scanner(System.in);								//made two scanner class objects, because taking nextLine() input after taking nextInt() in the same Scanner objects, create problems
    String email;
    String password;
    Faculty(String email, String password){
        super(email, password);					//goes to parent, Institute.java's constructor
        this.email = email;
        this.password = password;
    }
    public void sendPersonalMail(){			
        super.sendPersonalMail();			//calls the method of Institute.java
    }
    Connection con=null;
    Connection con2=null;
    Statement smt1=null;
    Statement smt2=null;
    ResultSet rs=null;
    ResultSet rs2=null;
    String url = "jdbc:mysql://localhost:3306/ta";			//database name is ta
    String username = "root";
    String upass = "";
    String temp;
    public void makeAnnouncement(){				//sends a mail to all the students in the database
        JavaMailUtil jmu=new JavaMailUtil();
        jmu.setSender(this.email, this.password);
        System.out.println("\n\t\t\t\tEnter::\n\t\t\t\t1. Custom Announcement\n\t\t\t\t2. Announce Result");
        int choice=sc1.nextInt();
        String str="SId\t\tCST254\t\tCST255\t\tCST256\t\tCST257\t\tCST258\n";
        switch(choice){
            case 1:
                System.out.println("Enter the subject of mail:     ");
                String sub=sc.nextLine();
                jmu.setEmailSubject(sub);
                System.out.println("Enter the message:     ");
                String msg=sc.nextLine();
                jmu.setMessage(msg);
                break;
            case 2:
                jmu.setEmailSubject("Result Announcement!!!");						//setting the subject for result announcement
                try{
                    con2 = DriverManager.getConnection(url,username,upass);
                    smt2 = con2.createStatement();
                    rs2 = smt2.executeQuery("SELECT * FROM marks");
                    while(rs2.next()){
                        int sid=rs2.getInt("sid");
                        int cst254=rs2.getInt("cst254");
                        int cst255=rs2.getInt("cst255");
                        int cst256=rs2.getInt("cst256");
                        int cst257=rs2.getInt("cst257");
                        int cst258=rs2.getInt("cst258");
                        str=str+sid+"         "+cst254+"         "+cst255+"          "+cst256+"          "+cst257+"          "+cst258+"\n";			//adding all the rows in str for setting str as the message for the mail later.
                    }
                    con2.close();
                }catch (SQLException ex) {
                    System.out.println("OOPS..SOMETHING WENT WRONG!!");
                    System.out.println("SQLException: " + ex.getMessage());
                    System.out.println("SQLState: " + ex.getSQLState());
                    System.out.println("VendorError: " + ex.getErrorCode());
                }
                jmu.setMessage(str);							//set the message..
                break;
        }
        
        try{
            con = DriverManager.getConnection(url,username,upass);
            smt1 = con.createStatement();
            rs = smt1.executeQuery("SELECT email FROM student");
            while(rs.next()){
                temp=rs.getString("email");				//getting recepient mail one by one from the database
                System.out.println("To :: "+temp);
                try{
                    jmu.sendMail(temp);					//sendinng the mail..
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            con.close();
        }
        catch (SQLException ex) {
            System.out.println("OOPS..SOMETHING WENT WRONG!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
