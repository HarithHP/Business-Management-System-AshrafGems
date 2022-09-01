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
public class Gempolisher {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;


     String ge_id;
     String u_id;
     String o_id;
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
      String u_salary;
      String u_bacc;
     JFrame frameAdmin;


 public void setGempolisher(String ge_id, String u_id, String o_id, String u_fname, String u_lname, String u_ad_1, String u_ad_2, String u_ad_3, String u_phone, String u_dob, String u_age, String u_nic, String u_jdate, String u_email, String u_pwd, String u_ctype,String u_salary, String u_bacc, JFrame frameAdmin) {
        this.ge_id = ge_id;
        this.u_id = u_id;
        this. o_id =  o_id;
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
        this.u_salary= u_salary;
        this.u_bacc = u_bacc;
        this.frameAdmin = frameAdmin;
    }

public void inputGempolisher(){
         try{ String query = "INSERT INTO `gempolisher`(`e_id`, `u_no`, `e_fname`, `e_lname`, `e_ad1`, `e_ad2`, `e_ad3`, `e_phone`, `e_dob`, `e_age`, `e_email`, `e_nic`, `e_salary`, `e_acc`, `e_jdate`, `e_wposition`, `e_pwd`) VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,ge_id);
           pst.setString(2,u_id);         
           pst.setString(3,u_fname);
           pst.setString(4,u_lname);
           pst.setString(5,u_ad_1);
           pst.setString(6,u_ad_2);
           pst.setString(7,u_ad_3);
           pst.setString(8,u_phone);
           pst.setString(9,u_dob);
           pst.setString(10,u_age);
           pst.setString(11,u_email);          
           pst.setString(12,u_nic);
           pst.setString(13, u_salary);
           pst.setString(14, u_bacc);
           pst.setString(15,u_jdate);
           pst.setString(16,u_ctype);         
           pst.setString(17,u_pwd);

           pst.executeUpdate();
            // JOptionPane.showMessageDialog(null, "GemPolisher Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    
}

public void updateGempolisher(){
          try{
           String sql = "UPDATE gempolisher SET e_id=?,e_fname=?,e_lname=?,e_ad1=?,e_ad2=?,e_ad3=?,e_phone=?,e_age=?,e_email=?,e_nic=?,e_salary=?,e_acc=?,e_wposition=?,e_pwd=? WHERE u_no=?";
           pst = con.prepareStatement(sql);
         
          
           pst.setString(1,ge_id);           
           pst.setString(2,u_fname);
           pst.setString(3,u_lname);
           pst.setString(4,u_ad_1);
           pst.setString(5,u_ad_2);
           pst.setString(6,u_ad_3);           
           pst.setString(7,u_phone);
           pst.setString(8,u_age);
           pst.setString(9,u_email);
           pst.setString(10,u_nic);
           pst.setString(11, u_salary);
           pst.setString(12, u_bacc);         
           pst.setString(13,u_ctype);
           pst.setString(14,u_pwd);
           pst.setString(15,u_id);
           
           pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "GemPolisher Data Updated Successfully");
            }
                catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }

public void deleteGempolisher(){
        try{ String query = "DELETE FROM `gempolisher` WHERE u_no =?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_id);
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "Gempolisher Data Deleted Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    } 

}
