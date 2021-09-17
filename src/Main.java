//coded by Utkarsh Singh
//Main class calling all the functionalities
import java.util.*;
public class Main {
    public static void main(String[] args) {
        char flag;
        Scanner sc=new Scanner(System.in);
        try{                                    //enclosed the whole code in try & finally because of the resource leak warning for not closing Scanner object
            while(true){
                System.out.println("Enter::\n1. Teacher\n2. Student\n3. Exit");   //asking whether you are student or a teacher
                int choice=sc.nextInt();
                switch(choice){
                    case 1:
                        System.out.print("Enter Your Email Sir/Maam:\t\t");
                        String e=sc.next();
                        System.out.print("Enter Password:\t\t");                     // asking for credantials for teacher login, will match it from the ta.faculty in the created database
                        String p=sc.next();
                        Faculty t1=new Faculty(e,p);
                        flag=t1.login(t1);  
                        if(flag=='t'){                                          //'t' is returned if login successful & hence, all teacher functionality is accesible then
                            System.out.println("Teacher Login Successful!!");
                            while(true){
                                System.out.println("\n\tEnter::\n\t\t1. Send Personal Mail\n\t\t2. Make An Announcement To Students\n\t\t3. Exit");
                                int choicet=sc.nextInt();
                                switch(choicet){
                                    case 1:
                                        t1.sendPersonalMail();              //can send personal mail to any student or teacher
                                        break;
                                    case 2:
                                        t1.makeAnnouncement();              //can send a mail to all the students in the database at once...as an announcement
                                        break;
                                    case 3:
                                        System.exit(0);
                                }
                            }
                        }
                        else{
                            System.out.println("Invalid Credantials!!");       //will again have to enter the creadantials if any invalid email or password is entered
                        }
                        break;
                    
                    case 2:
                        System.out.print("Enter Your Email:\t\t");  
                        String e1=sc.next();
                        System.out.print("Enter Password:\t\t");
                        String p1=sc.next();
                        Student s1=new Student(e1,p1);
                        flag=s1.login(s1);
                        if(flag=='s'){                      //'s' will be returned from login() if student login is successful & hence the student functionalities are accesible
                            System.out.println("Student Login Successful!!");
                            while(true){
                                System.out.println("\n\tEnter::\n\t\t1. Send Personal Mail\n\t\t2. Get Result\n\t\t3. Exit");
                                int choices=sc.nextInt();
                                switch(choices){
                                    case 1:
                                        s1.sendPersonalMail();      //can send personal mail to any student or teacher
                                        break;
                                    case 2:
                                        System.out.println("Enter Your Student ID:  ");     //asks for the Student Id, & hence the marks of the same ID is returned from the database
                                        int sid =sc.nextInt();
                                        s1.getResult(sid);
                                    case 3:
                                        System.exit(0);
                                }
                            }
                        }
                        else{
                            System.out.println("Invalid Credantials!!");
                        }
                        break;
                    
                    case 3:
                        System.exit(0);             //stops the execution
                }
            }
        }finally{
            sc.close();             //closing the Scanner object
        }
    }
}