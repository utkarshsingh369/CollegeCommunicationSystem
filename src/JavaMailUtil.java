//coded by Utkarsh Singh
//this class will be used to send the mails wherever required in the project
//obviously not able to understand the 100% of this code and have taken a very little help of a tutorial...but the tutorial's code also didn't run succesfully and necessary ammendments have been made accordingly



import javax.mail.Session;          //this import represents a mail session
import javax.mail.Transport;        //models a message transport
import javax.mail.Authenticator;    //used to authenticate the connection...simply the mail id and the password
import javax.mail.Message;          //reference to actual message
import javax.mail.MessagingException;       //handles all the exceptions thrown by Messaging classes
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;    //validates the correct format of an email address e.g., 'xxxxxxxxx@xxxx.xxx'
import javax.mail.internet.MimeMessage;
import java.util.*;         //for Scanner class and Properties class    

public class JavaMailUtil {
    String myEmail;
    String myPassword;
    String subject;
    String message;
    public void setSender(String email, String password){       //setter for setting the sender's credantials
        this.myEmail = email;
        this.myPassword = password;
    }

    public void setEmailSubject(String subject){            //setter for setting the subject of the mail
        this.subject = subject;
    }

    public void setMessage(String message){             //setter for setting the message that is to be sent
        this.message = message;
    }

    public void sendMail(String recipient) throws Exception {           //this method sends the mail 
        System.out.println("Sending the mail...");
        Properties properties = new Properties();           //properties class is just like dictionaries in python...i.e., it sets the key-value pair
        //the following properties are mandatory to be set first
        properties.put("mail.smtp.auth","true");            //must be true
        properties.put("mail.smtp.starttls.enable","true");         //must be true
        properties.put("mail.smtp.host","smtp.gmail.com");          //the host for gmail..i.e., smtp.gmail.com
        properties.put("mail.smtp.port","587");         //the port for gmail is 587


        Session session = Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {          //overriden method of authenticator class, authenticates the sender's credantials 
                return new PasswordAuthentication(myEmail,myPassword);
            }
        });
        Message message = prepareMessage(session,myEmail,recipient);        //this prepares the mail that is to be sent...method description is later in the code
        Transport.send(message);                        //finally sending the mail
        System.out.println("Mail sent successfully!!!");
    }
    
    private Message prepareMessage(Session session, String myEmail, String recipient){       //return the Message type object, that is to be sent
        Message msg = new MimeMessage(session);         
        try {
            //setting from, recipient, subject and the text that is to be sent in mail
            msg.setFrom(new InternetAddress(myEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject(subject);
            msg.setText(message);
            return msg;

        }
        //exception will be thrown if recipient address is not found or the email address format is wrong
        catch (AddressException e) {                     
            System.out.println("\nEnter Valid Recipient Email Address!!!\n");           
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;  
    }
}






