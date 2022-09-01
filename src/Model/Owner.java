/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class Owner {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;


     
     String o_id;
     String o_fname;
     String o_lname;
     String o_ad_1;
     String o_ad_2;
     String o_ad_3;
     String o_phone;
     String o_nic;
     String o_email;
     String o_pwd;    
     JFrame frameAdmin;


 public void setOwner( String o_id, String o_fname, String o_lname, String o_ad_1, String o_ad_2, String o_ad_3, String o_phone, String o_nic, String o_email, String o_pwd, JFrame frameAdmin) {
        
        this. o_id =  o_id;
        this.o_fname = o_fname;
        this.o_lname = o_lname;
        this.o_ad_1 = o_ad_1;
        this.o_ad_2 = o_ad_2;
        this.o_ad_3 = o_ad_3;
        this.o_phone = o_phone;
        this.o_nic = o_nic;
        this.o_email = o_email;
        this.o_pwd = o_pwd;
     
        this.frameAdmin = frameAdmin;
    }

public void inputOwner(){
         try{ String query = "INSERT INTO `owner`(`o_id`, `o_fname`, `o_lanme`, `o_ad1`, `o_ad2`, `o_ad3`,`o_phone`, `o_email`,`o_nic`,`o_pwd`) VALUES  (?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
          
           pst.setString(1,o_id);
           pst.setString(2,o_fname);
           pst.setString(3,o_lname);
           pst.setString(4,o_ad_1);
           pst.setString(5,o_ad_2);
           pst.setString(6,o_ad_3);
           pst.setString(7,o_phone);
           pst.setString(8,o_email);
           
           pst.setString(9,o_nic);
         
          
           pst.setString(10,o_pwd);

           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Owner Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    
}

public void updateOwner(){
          try{
           String sql = "UPDATE owner SET o_fname=?,o_lanme=?,o_ad1=?,o_ad2=?,o_ad3=?,o_phone=?,o_email=?,o_nic=?,o_pwd=? WHERE o_id=?";
           pst = con.prepareStatement(sql);
        
           pst.setString(1,o_fname);
           pst.setString(2,o_lname);
           pst.setString(3,o_ad_1);
           pst.setString(4,o_ad_2);
           pst.setString(5,o_ad_3);
           
           pst.setString(6,o_phone);
           pst.setString(7,o_email);
           pst.setString(8,o_nic);
           
           
           pst.setString(9,o_pwd);
           pst.setString(10,o_id);
           
           pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Owner Data Updated Successfully");
            }
                catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }

public void deleteOwner(){
        try{ String query = "DELETE FROM `owner` WHERE o_id =?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,o_id);
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Owner Data Deleted Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    } 


}
