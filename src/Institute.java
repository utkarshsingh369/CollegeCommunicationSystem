//coded by Hardik Sunil Dharmik
//parent class for Student.java and Faculty.java for giving a common functionality for login() and sending personal mails for both the child classes
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class Institute {

    Scanner sc=new Scanner(System.in);
    Scanner sc1=new Scanner(System.in);

    String email;//emailid of user
    String password;//password of user
    public Institute(){//Default constructor(Non parameterized constructor is called)
        this.email= "********@gmail.com";//Default ID
        this.password= "*******";//Default Password
    }
    public Institute(String email, String password) {//If arguments are passed, then this constructor is called
        this.email = email;
        this.password = password;
    }


    Connection con=null;//Connection class
    Statement state=null;//Statement Class
    ResultSet rs=null;//ResultSet Class
    String url = "jdbc:mysql://localhost:3306/ta";
    String username = "root";
    String upass = "";
    String temp;
    char flag;
  	//The login method is overloaded with the two types of the parameters
    public char login(Faculty t){		//login for teacher
        flag ='x';					//to store the login status (by default x)
        try{
            con = DriverManager.getConnection(url,username,upass);
            state = con.createStatement();
            rs = state.executeQuery("SELECT password FROM faculty WHERE email='"+this.email+"'");   //find the password for the entered email id in the database
            while(rs.next()){
                temp=rs.getString("password");
            }
            if(this.password.equals(temp)){
                flag='t';				//will be 't', if the teacher login is succesful
            }
            con.close();
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }  
        return flag;
    }
    public char login(Student s){		//login for student
        flag ='x';
        try{
            con = DriverManager.getConnection(url,username,upass);
            state = con.createStatement();
            rs = state.executeQuery("SELECT password FROM student WHERE email='"+this.email+"'");	//find the password for the email id entered by the student in the database
            while(rs.next()){
                temp=rs.getString("password");
            }
            if(this.password.equals(temp)){     //matches the password
                flag='s';                   //sets the flag to 's'
            }
            con.close();
        }
        catch (SQLException ex) {
            System.out.println("OOPS!..SOMETHING WENT WRONG");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }  
        return flag;
    }
    public void sendPersonalMail(){					//this method is to be used by both the child class
        JavaMailUtil jmu=new JavaMailUtil();
        jmu.setSender(this.email, this.password);//Setting Email & Password
        System.out.println("Enter the email address of the recipient:     ");		
        String recEmail=sc1.nextLine();
        System.out.println("Enter the subject of mail:     ");
        String sub=sc1.nextLine();
        jmu.setEmailSubject(sub);//Setting Email Subject
        System.out.println("Enter the message:     ");
        String msg=sc1.nextLine();
        jmu.setMessage(msg);//Setting message contents
        try {
            jmu.sendMail(recEmail);//Sending mail to recEmail
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
