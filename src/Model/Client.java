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
 * @author Hadaragama
 */
public class Client {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

     String c_id;
     String u_id;
     String a_id;
     String u_fname;
     String u_lname;
     String u_ad_1;
     String u_ad_2;
     String u_ad_3;
     String u_phone;
     String u_dob;
     String u_age;
     String u_nic;
     String u_jdate;
     String u_email;
     String u_pwd;    
     String u_ctype;
     JFrame frameAdmin;

    public void setClient(String c_id, String u_id, String a_id, String u_fname, String u_lname, String u_ad_1, String u_ad_2, String u_ad_3, String u_phone, String u_dob, String u_age, String u_nic, String u_jdate, String u_email, String u_pwd, String u_ctype, JFrame frameAdmin) {
        this.c_id = c_id;
        this.u_id = u_id;
        this.a_id = a_id;
        this.u_fname = u_fname;
        this.u_lname = u_lname;
        this.u_ad_1 = u_ad_1;
        this.u_ad_2 = u_ad_2;
        this.u_ad_3 = u_ad_3;
        this.u_phone = u_phone;
        this.u_dob = u_dob;
        this.u_age = u_age;
        this.u_nic = u_nic;
        this.u_jdate = u_jdate;
        this.u_email = u_email;
        this.u_pwd = u_pwd;
        this.u_ctype = u_ctype;
        this.frameAdmin = frameAdmin;
    }
public void inputClient(){
         try{ String query = "INSERT INTO `client`(`c_id`, `u_no`, `a_no`, `c_fname`, `c_lname`, `c_ad1`, `c_ad2`, `c_ad3`, `c_dob`, `c_age`, `c_phone`, `c_nic`, `c_jdate`, `c_type`, `c_email`, `c_pwd`) VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,c_id);
           pst.setString(2,u_id);
           pst.setString(3,a_id);
           pst.setString(4,u_fname);
           pst.setString(5,u_lname);
           pst.setString(6,u_ad_1);
           pst.setString(7,u_ad_2);
           pst.setString(8,u_ad_3);
           pst.setString(9,u_dob);
           pst.setString(10,u_age);
           pst.setString(11,u_phone);
           pst.setString(12,u_nic);
           pst.setString(13,u_jdate);
           pst.setString(14,u_ctype);
           pst.setString(15,u_email);
           pst.setString(16,u_pwd);

           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Client Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    
}

public void updateClient(){
          try{
           String sql = "UPDATE client SET c_id=?, a_no=?,c_fname=?,c_lname=?,c_ad1=?,c_ad2=?,c_ad3=?,c_phone=?,c_nic=?,c_jdate=?,c_type=?,c_email=?,c_pwd=? WHERE u_no=?";
           pst = con.prepareStatement(sql);
         
          
           pst.setString(1,c_id);
           pst.setString(2,a_id);
           pst.setString(3,u_fname);
           pst.setString(4,u_lname);
           pst.setString(5,u_ad_1);
           pst.setString(6,u_ad_2);
           pst.setString(7,u_ad_3);
           
           pst.setString(8,u_phone);
           pst.setString(9,u_nic);
           pst.setString(10,u_jdate);
           pst.setString(11,u_ctype);
           pst.setString(12,u_email);
           pst.setString(13,u_pwd);
           pst.setString(14,u_id);
           
           pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Client Data Updated Successfully");
            }
                catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }

public void deleteClient(){
        try{ String query = "DELETE FROM `client` WHERE u_no =?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_id);
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Client Data Deleted Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    } 

    
}
