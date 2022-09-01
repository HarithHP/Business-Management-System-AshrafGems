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
public class Supplier {

    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

     String s_id;
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
     String u_scid;
     String u_sbacc;
     JFrame frameAdmin;

    public void setSupplier(String s_id, String u_id, String a_id, String u_fname, String u_lname, String u_ad_1, String u_ad_2, String u_ad_3, String u_phone, String u_dob, String u_age, String u_nic, String u_jdate, String u_email, String u_pwd, String u_scid, String u_sbacc, JFrame frameAdmin) {
        this.s_id = s_id;
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
        this.u_scid = u_scid;
        this.u_sbacc = u_sbacc;
        this.frameAdmin = frameAdmin;
        
    }

    
public void inputSupplier(){
         try{ String query = "INSERT INTO `supplier`(`s_id`, `u_no`, `a_no`, `s_fname`, `s_lname`, `s_ad1`, `s_ad2`, `s_ad3`, `s_dob`, `s_age`, `s_phone`, `s_nic`, `s_jdate`, `s_bankacc`, `s_glid`, `s_email`, `s_pwd`) VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,s_id);
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
           pst.setString(14,u_sbacc);
           pst.setString(15,u_scid);
           pst.setString(16,u_email);
           pst.setString(17,u_pwd);

           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Supplier Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    
}
public void updateSupplier(){
          try{
           String sql = "UPDATE supplier SET a_no=?,s_fname=?,s_lname=?,s_ad1=?,s_ad2=?,s_ad3=?,s_phone=?,s_nic=?,s_jdate=?,s_bankacc=?,s_glid=?,s_email=?,s_pwd=? WHERE u_no=?";
           pst = con.prepareStatement(sql);
         
           
           pst.setString(1,a_id);
           pst.setString(2,u_fname);
           pst.setString(3,u_lname);
           pst.setString(4,u_ad_1);
           pst.setString(5,u_ad_2);
           pst.setString(6,u_ad_3);
       
           pst.setString(7,u_phone);
           pst.setString(8,u_nic);
           pst.setString(9,u_jdate);
           pst.setString(10,u_sbacc);
           pst.setString(11,u_scid);
           pst.setString(12,u_email);
           pst.setString(13,u_pwd);
           pst.setString(14, u_id);
           pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Supplier Data Updated Successfully");
            }
                catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }

     

public void deleteSupplier(){
        try{ String query = "DELETE FROM `supplier` WHERE u_no =?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_id);
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Supplier Data Deleted Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }    
    
}
