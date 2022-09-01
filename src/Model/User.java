/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import View.AgentMainForm;
import View.ClientMainForm;
import View.GemPolisherMainForm;
import View.GemPolisherMainForm1;
import View.OwnerMainForm;
import View.SignInUp;
import View.SupplierMainForm;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Hadaragama
 */
public class User {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

     String u_id;
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
     String u_type;
     JFrame jframe;
     JFrame frameAdmin;
     int o_id;



public void setInput(String u_email,String u_pwd,JFrame jFrame){// use for sign in
         this.u_email=u_email;
         this.u_pwd = u_pwd;
         this.jframe = jFrame;
        } 

    public void setInputUser(String u_id, String u_fname, String u_lname, String u_ad_1, String u_ad_2, String u_ad_3, String u_phone, String u_dob, String u_age, String u_nic, String u_jdate, String u_email, String u_pwd, String u_type, JFrame frameAdmin) {
        this.u_id = u_id;
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
        this.u_type = u_type;
        this.frameAdmin = frameAdmin;
    }
    public void inputUser(){
         try{ String query = "INSERT INTO `user`(`u_id`, `u_fname`, `u_lname`, `u_ad_1`, `u_ad_2`, `u_ad_3`, `u_phone`, `u_dob`, `u_age`, `u_nic`, `u_jdate`, `u_email`, `u_pwd`, `u_type`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_id);
           pst.setString(2,u_fname);
           pst.setString(3,u_lname);
           pst.setString(4,u_ad_1);
           pst.setString(5,u_ad_2);
           pst.setString(6,u_ad_3);
           pst.setString(7,u_phone);
           pst.setString(8,u_dob);
           pst.setString(9,u_age);
           pst.setString(10,u_nic);
           pst.setString(11,u_jdate);
           pst.setString(12,u_email);
           pst.setString(13,u_pwd);
           pst.setString(14,u_type);
           pst.executeUpdate();
             JOptionPane.showMessageDialog(null, "User Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    
}

 public void checkUserEmail(){
        try{String query = "select * from user where u_email = ?";
            pst=con.prepareStatement(query);
            pst.setString(1,u_email);
            rs=pst.executeQuery();
            if(rs.next()){
            checkUserPassword();
            }else{
            JOptionPane.showMessageDialog(null, "There isn't an account for this email");
            }
    } catch (SQLException ex) {
            Logger.getLogger(SignInUp.class.getName()).log(Level.SEVERE, null, ex);          
            }           
    }
    
    public void checkUserPassword(){
        try{String query = "select * from user where u_pwd = ?";
            pst=con.prepareStatement(query);
            pst.setString(1,u_pwd);
            rs=pst.executeQuery();
            if(rs.next()){
            getUserType();
            if(u_type.equals("Supplier"))
             {
               getSupplierID();
               jframe.setVisible(false);
               SupplierMainForm main = new SupplierMainForm(u_id);
               main.setVisible(true);
             }
             else if(u_type.equals("Client")){
                getClientID();
                jframe.setVisible(false);
                ClientMainForm main = new ClientMainForm(u_id);
                main.setVisible(true);
             }
             else if(u_type.equals("Agent")){
                getAgentID();
                jframe.setVisible(false);
                AgentMainForm main = new AgentMainForm(u_id);
                main.setVisible(true);                
             }
             else if(u_type.equals("GemPolisher")){
                getGemPolishertID();
                jframe.setVisible(false);
                GemPolisherMainForm main = new GemPolisherMainForm(u_id);
                main.setVisible(true);  
                
             }else{
            JOptionPane.showMessageDialog(null, "User Type Error");
            }
            
            }else{
            JOptionPane.showMessageDialog(null, "Your Password Incorrect");
            }
    } catch (SQLException ex) {
            Logger.getLogger(SignInUp.class.getName()).log(Level.SEVERE, null, ex);          
            }           
    }
        public void checkOwnerEmail(){
        try{String query = "select * from owner where o_email = ?";
            pst=con.prepareStatement(query);
            pst.setString(1,u_email);
            rs=pst.executeQuery();
            if(rs.next()){
            checkOwnerPassword();
            }else{
            checkUserEmail();
            }
    } catch (SQLException ex) {
            Logger.getLogger(SignInUp.class.getName()).log(Level.SEVERE, null, ex);          
            }           
    }
    
    public void checkOwnerPassword(){
        try{String query = "select * from owner where o_pwd = ?";
            pst=con.prepareStatement(query);
            pst.setString(1,u_pwd);
            rs=pst.executeQuery();
            if(rs.next()){
            getOwnerID();
            jframe.setVisible(false);         
            OwnerMainForm main = new OwnerMainForm(o_id);
            main.setVisible(true);
            }else{
            JOptionPane.showMessageDialog(null, "Your Password Incorrect");
            }
    } catch (SQLException ex) {
            Logger.getLogger(SignInUp.class.getName()).log(Level.SEVERE, null, ex);          
            }           
    }
    public void getSupplierID(){
       
       try{
           String query = "SELECT * FROM `supplier` WHERE s_email=? and s_pwd=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_email);
           pst.setString(2,u_pwd);
           rs =pst.executeQuery();
           if(rs.next()){
                u_id=rs.getString("s_id");
                System.out.println(u_id);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
    public void getClientID(){
       
       try{
           String query = "SELECT * FROM `client` WHERE c_email=? and c_pwd=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_email);
           pst.setString(2,u_pwd);
           rs =pst.executeQuery();
           if(rs.next()){
                u_id=rs.getString("c_id");
                System.out.println(u_id);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
    public void getAgentID(){
       
       try{
           String query = "SELECT * FROM `agent` WHERE a_email=? and a_pwd=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_email);
           pst.setString(2,u_pwd);
           rs =pst.executeQuery();
           if(rs.next()){
                u_id=rs.getString("a_id");
                System.out.println(u_id);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
    public void getGemPolishertID(){
       
       try{
           String query = "SELECT * FROM `gempolisher` WHERE e_email=? and e_pwd=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_email);
           pst.setString(2,u_pwd);
           rs =pst.executeQuery();
           if(rs.next()){
                u_id=rs.getString("e_id");
                System.out.println(u_id);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
    public void getOwnerID(){
       
       try{
           String query = "SELECT * FROM `owner` WHERE o_email=? and o_pwd=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_email);
           pst.setString(2,u_pwd);
           rs =pst.executeQuery();
           if(rs.next()){
                o_id=rs.getInt("o_id");
                System.out.println(o_id);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
    public void getUserType(){
       
       try{
           String query = "SELECT * FROM `user` WHERE u_email=? and u_pwd=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_email);
           pst.setString(2,u_pwd);
           rs =pst.executeQuery();
           if(rs.next()){
                u_type=rs.getString("u_type");
                System.out.println(u_type);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

    public void updateUser(){
          try{
           String sql = "UPDATE user SET u_fname=?,u_lname=?,u_ad_1=?,u_ad_2=?,u_ad_3=?,u_phone=?,u_nic=?,u_jdate=?,u_email=?,u_pwd=? ,u_type=? WHERE u_id=?";
           pst = con.prepareStatement(sql);
         
           pst.setString(12,u_id);
           pst.setString(1,u_fname);
           pst.setString(2,u_lname);
           pst.setString(3,u_ad_1);
           pst.setString(4,u_ad_2);
           pst.setString(5,u_ad_3);
           pst.setString(6,u_phone);
           pst.setString(7,u_nic);
           pst.setString(8,u_jdate);
           pst.setString(11,u_type);
           pst.setString(9,u_email);
           pst.setString(10,u_pwd);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "User Data Updated Successfully");
            }
                catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }


    }
    
 public void delete(){
        try{ String query = "DELETE FROM `user` WHERE u_id =?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,u_id);
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "User Data Deleted Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }


}
