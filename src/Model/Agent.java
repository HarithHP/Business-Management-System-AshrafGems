
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Chamod Dilushanka
 */
public class Agent {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

    
     String a_id;
     String u_no;
     String a_fname;
     String a_lname;
     String a_ad_1;
     String a_ad_2;
     String a_ad_3;
     String a_dob;
     String a_age;
     String a_phone;
     String a_email;
     String a_nic;
     String a_jdate;
     String a_bankacc;
     String a_worktype;
     String a_pwd;    
     JFrame frameAdmin;
     
      public void setAgent(String a_id, String u_no, String a_fname, String a_lname, String a_ad_1, String a_ad_2, String a_ad_3, String a_dob, String a_age,  String a_phone, String a_email, String a_nic, String a_jdate,String a_bankacc, String a_worktype, String u_pwd, JFrame frameAdmin) {
        this.a_id = a_id;
        this.u_no = u_no;
        this.a_fname = a_fname;
        this.a_lname = a_lname;
        this.a_ad_1 = a_ad_1;
        this.a_ad_2 = a_ad_2;
        this.a_ad_3 = a_ad_3;
        this.a_dob = a_dob;
        this.a_age = a_age;
        this.a_phone = a_phone;
        this.a_email = a_email;
        this.a_nic = a_nic;
        this.a_jdate = a_jdate;
        this.a_bankacc=a_bankacc;
        this.a_worktype = a_worktype;
        this.a_pwd = u_pwd;
        
        
    }
     
      
      
      public void inputAgent(){
         try{ String query = "INSERT INTO `agent`(`a_id`, `u_no`, `a_fname`, `a_lname`, `a_ad1`, `a_ad2`, `a_ad3`, `a_dob`, `a_age`, `a_phone`, `a_email`, `a_nic`, `a_jdate`, `a_bankacc`, `a_worktype`, `a_pwd`) VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,a_id);
           pst.setString(2,u_no);
           pst.setString(3,a_fname);
           pst.setString(4,a_lname);
           pst.setString(5,a_ad_1);
           pst.setString(6,a_ad_2);
           pst.setString(7,a_ad_3);
           pst.setString(8,a_dob);
           pst.setString(9,a_age);
           pst.setString(10,a_phone);
           pst.setString(11,a_email);
           pst.setString(12,a_nic);
           pst.setString(13,a_jdate);
           pst.setString(14,a_bankacc);
           pst.setString(15,a_worktype);
           pst.setString(16,a_pwd);

           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Agent Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    
}

public void updateAgent(){
          try{
           String sql = "UPDATE agent SET a_id=?,a_fname=?,a_lname=?,a_ad1=?,a_ad2=?,a_ad3=?,a_phone=?,a_email=?,a_nic=?,a_bankacc=?,a_worktype=?,a_pwd=? WHERE u_no=?";
           pst = con.prepareStatement(sql);
         
          
           pst.setString(1,a_id);
           
           pst.setString(2,a_fname);
           pst.setString(3,a_lname);
           pst.setString(4,a_ad_1);
           pst.setString(5,a_ad_2);
           pst.setString(6,a_ad_3);
           
           pst.setString(7,a_phone);
           pst.setString(8,a_email);
           pst.setString(9,a_nic);
           pst.setString(10, a_bankacc);
     
          
           pst.setString(11,a_worktype);
          pst.setString(12,a_pwd);
           pst.setString(13,u_no);
           
           pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Agent Data Updated Successfully");
            }
                catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }

public void deleteAgent(){
        try{ String query = "DELETE FROM `agent` WHERE u_no =?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_no);
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Agent Data Deleted Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    } 


}
