//coded by Saiyyed Khhizr
//Another child of Institute class
//having fucntionalities: 1. the inherited sendPersonalMail() method 2. to get result from the database on the student's mail 


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class Student extends Institute{
    String email;
    String password;
    public Student(String email,String password){
        super(email, password);             //calling parameterized constructor of the parent class
        this.email = email;
        this.password = password;
    }
    Connection con=null;
    Statement smt=null;
    ResultSet rs=null;
    String url = "jdbc:mysql://localhost:3306/ta";       //our database name is ta
    String username = "root";
    String upass = "";
    String str="CST254\t\tCST255\t\tCST256\t\tCST257\t\tCST258\n";
    public void getResult(int sid){                 //retreives the result from the database at ta.marks and is sent on the student's mail
        JavaMailUtil jmu=new JavaMailUtil();        //for sending the mail
        //there was this issue that.. if the students gets his result on the mail, then who will be the sender of the mail
        //hence we created an email address for the institute and the following object of Institute is created having no parameters to call the non parameterized constructor of the Institute class
        Institute i=new Institute();                
        jmu.setSender(i.email,i.password);          //setting the default email address and password of the Institute
        jmu.setEmailSubject("Your Result");         //setting subject
        try{
            con=DriverManager.getConnection(url,username,upass);
            smt=con.createStatement();
            rs=smt.executeQuery("SELECT CST254, CST255, CST256, CST257, CST258 FROM marks WHERE sid = "+sid);    //getting the marks of the student
            while(rs.next()){
                int cst254=rs.getInt("cst254");
                int cst255=rs.getInt("cst255");
                int cst256=rs.getInt("cst256");
                int cst257=rs.getInt("cst257");
                int cst258=rs.getInt("cst258"); 
                str=str+cst254+"                "+cst255+"                "+cst256+"                  "+cst257+"                "+cst258+"\n";          //str is updated such that it gets the marks of all subjects from the database
            }
            con.close();
        }catch (SQLException ex) {          //if failed to get the query from the database due to various possible mistakes
            System.out.println("OOPS!.. SOMETHING WENT WRONG!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        jmu.setMessage(str);        // setting the str as the text for the mail
        try {
            jmu.sendMail(this.email);//calling the function to send the mail
            System.out.println("Check Your GMail for the result");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}