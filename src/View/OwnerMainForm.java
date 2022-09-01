
package View;

import AppPackage.AnimationClass;
import Model.Client;
import Model.Expense;
import Model.ExpenseGemPolisher;
import Model.FinishedGem;
import Model.Gem;
import Model.Job;
import Model.RawGem;
import Model.SqlConnection;
import Model.Supplier;
import Model.User;
import Model.Agent;
import Model.Gempolisher;
import Model.MapMain;
import Model.Owner;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;



public class OwnerMainForm extends javax.swing.JFrame {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

                String u_id;
                String s_id;
                String c_id;
                String a_id;
                String u_dob;
                String e_id;
                String o_id;
                String  rrg_id;// Gem Polisher job 
                int gg_no;
                
                int uu_no;
                String ee_name;
                String gg_name;
                String jj_exEndDate;

              int uu_id;
              int ss_id;
              int cc_id;
              int aa_id;
              String ee_id;
              int gg_id;
              int oo_id;
               
                String u_fname;
                String u_lname;
                String u_ad_1;
                String u_ad_2;
                String u_ad_3;
                String u_phone;
                int u_age;
                String u_nic;
                String u_jdate;
                String u_email;
                String u_pwd;
                String u_type;
                String u_ctype;
                String u_scid;
                String u_sbacc;
     
            
                String status = "Paid";

               int ggg_no;//use in clientorder internal frame

               int ex_id;// use in gempolisherExpenses frame
               String gex_id;

               int sup_gno;//use in supplier Order frame
               String sup_id;//supplier number
               String sup_oid;//supplier order number
               String sup_spano;//suplier refferal agent number

               String exs_id;//use in supplier expense(Suppler expense id)
               int exss_id;//use in supplier expense(expense id)
               String pmethod;//use in supplier expense(expense pay method)

               String aex_id;//use in Agent expense(Agent Expense Id)
               int ex_ID;//use in Agent expense(Expense Id)
               String ex_ano;//use in Agent expense(Agent Id)
               String ex_sono;//use in Agent expense(supplier order Id)
               String ex_cono;//use in Agent expense(supplier order Id)

               String imagePathStr;//Manage Raw Gem Frame(Image Path)
               String imagePathStrF;//Manage Finished Gem Frame(Image Path)
               Integer g_id;//Manage gem(Gem ID)
               String g_rrid;//Manage Raw Gem Frame(Raw Gem ID)
               String rg_adate;//Manage Raw Gems (Raw gem Added Date)
               String g_ffid;//Manage Gem (Finished Gem ID)
               
               String invoice_s_sran;
               String invoice_s_sid;
               String invoice_s_fname;
               String invoice_s_lname;
               String invoice_s_email;
               String invoice_s_phone;
               String invoice_s_oid;
               String invoice_s_eid;
               String invoice_s_seid;
               String invoice_s_pm;
               String invoice_s_amount;

               String invoice_g_gpid;
               String invoice_g_fname;
               String invoice_g_lname;
               String invoice_g_email;
               String invoice_g_phone;
               String invoice_g_fsalary;
               String invoice_g_bonus;
               String invoice_g_total;

               String invoice_a_aid;
               String invoice_a_fname;
               String invoice_a_lname;
               String invoice_a_email;
               String invoice_a_phone;
               String invoice_a_aeid;
               String invoice_a_eid;
               String invoice_a_soid;
               String invoice_a_coid;
               String invoice_a_oamount;
               String invoice_a_crate;
               String invoice_a_camount;
 
              String expencesCount;
              String expencesTotal;
              String incomeCount;
              String incomeTotal;

    AnimationClass ac = new AnimationClass();
    public int code;//Owner ID
    String today;//Today date
    public OwnerMainForm(int code) {
        initComponents();
        this.code = code;
        getID();//Getting Owner ID
        calToday();//Calculating today Date
        calUserID();
        calSupID();
        getID();
        calCliID();
        calAgentID();
        calownerID();
       
        calGemPolisherID();
        btnfilter.setVisible(false);
        fetchAllSuppliers();
        fetchAllClients();
        fetchAllAgents();
        fetchAllGemPolishers();
        fetchAllOwners();
        
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        today = sdf.format(date);
        System.out.println(""+today);


     try {
            String QrCodeData= "www.ashrafgems.blogspot.com/";
            String filePath= "D:\\AshrafGems\\Ashraf Gems\\QR.png";
            String charset= "UTF-8";
            Map <EncodeHintType,ErrorCorrectionLevel> hintMap= new HashMap <EncodeHintType,ErrorCorrectionLevel> ();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix  matrix= new MultiFormatWriter().encode(
                new String (QrCodeData.getBytes(charset),charset),
                BarcodeFormat.QR_CODE,200,200,hintMap);

            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.')+1),new File(filePath));
            System.out.println("Qr code has been generated at the location "+filePath);

             
           jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
            ImageIcon icon = new ImageIcon("D:\\AshrafGems\\Ashraf Gems\\QR.png");
            JLabel label = new JLabel(icon);
            jPanel2.setLayout(new BorderLayout());
            jPanel2.add(label);
            jPanel2.setVisible(true);



        } catch (Exception e) {
         System.err.println(e);
        }
            



    }
    private void getID(){//Getting Owner ID

         try{
            lblUserName.setText(""+code);
           }catch(Exception e)
           {
            JOptionPane.showMessageDialog(null, e);
           }
    }

private void calUserID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(u_id) from user");
            
            if(rs.next()){
                
                uu_id = rs.getInt(1);
                uu_id =uu_id+1;   
                u_id=String.valueOf(uu_id);                   
            }else
            {
                uu_id = 1;
                u_id=String.valueOf(uu_id); 
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+u_id);
       
    }

private void calownerID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(o_id) from owner");
            
            if(rs.next()){
                
                oo_id = rs.getInt(1);
                oo_id =oo_id+1;   
                o_id=String.valueOf(oo_id);                   
            }else
            {
                oo_id = 1;
                o_id=String.valueOf(oo_id); 
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+o_id);
       
    }


private void calSupID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(s_id) from supplier");
            
            if(rs.next()){
                
                ss_id = rs.getInt(1);
                ss_id =ss_id+1;
                s_id = String.valueOf("s"+ss_id);                      
            }else
            {
                ss_id = 1;
                s_id = String.valueOf("s"+ss_id); 
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+s_id);
       
    }

   private void calCliID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(c_id) from client");
            
            if(rs.next()){
                
                cc_id = rs.getInt(1);
                cc_id =cc_id+1;
                c_id = String.valueOf("c"+cc_id);                      
            }else
            {
                cc_id = 1;
                c_id = String.valueOf("c"+cc_id); 
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+c_id);
       
    }


    private void calAgentID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(a_id) from agent");
            
            if(rs.next()){
                
                aa_id = rs.getInt(1);
                aa_id =aa_id+1;
                a_id = String.valueOf("a"+aa_id);                      
            }else
            {
                aa_id = 1;
                a_id = String.valueOf("a"+aa_id); 
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+a_id);
       
    }

private void calGemPolisherID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(e_id) from gempolisher");
            
            if(rs.next()){
                
                gg_id = rs.getInt(1);
                gg_id =gg_id+1;
                e_id = String.valueOf("e"+""+gg_id);                      
            }else
            {
                gg_id = 1;
                e_id = String.valueOf("e"+gg_id); 
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println("GempolisherID"+e_id);
       
    }


  private void calToday(){//CALCULATE TODAY DATE
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        today = sdf.format(date);
        datechooser_udob.setDate(date);
        datechooser_udob1.setDate(date);
        datechooser_udob2.setDate(date);
        datechooser_udob3.setDate(date);
        datechooser_jdate.setDate(date);
}

private void ageCalc(){
   
    Date date1 = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    int YearNow = Integer.parseInt(sdf.format(date1));
    int YearSel = Integer.parseInt(sdf.format(datechooser_udob.getDate()));
    int age;
    u_age = YearNow - YearSel;
    System.out.println(""+u_age);
}



    private void calExpensesID(){//CALCULATE EXPENSES ID
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(ex_id) from expense");
            
            if(rs.next()){
                
                ex_id = rs.getInt(1);
                ex_id =ex_id+1;   
                                  
            }else
            {
                ex_id = 1;
                 
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+ex_id);
       
    }
private void calGempolisherExpenseID(){//CALCULATE GEM POLISHER'S EXPENSES ID
        int id;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(gex_id) from expensegempolisher");
            
            if(rs.next()){
                
                id = rs.getInt(1);
                id =id+1;   
                gex_id=String.valueOf("GE"+id);                   
            }else
            {
                id = 1;
                gex_id=String.valueOf("GE"+id); 
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+gex_id);
       
    }
private void calGemID(){//CALCULATE NEXT GEM ID
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(g_id) from gem");
            
            if(rs.next()){
                
                g_id = rs.getInt(1);
                g_id =g_id+1;                      
            }else
            {
                g_id = 1;
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
    }
private void calRGemID(){//CALCULATE NEXT RAW GEM ID
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(rg_id) from raw");
            int g_rid;
            if(rs.next()){
                
                g_rid = rs.getInt(1);
                g_rid =g_rid+1;
                g_rrid = String.valueOf("r"+g_rid);                      
            }else
            {
                g_rid = 1;
                g_rrid = String.valueOf("r"+g_rid);  
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
        System.out.println(""+g_rrid);
       
    }
public void calFGemID(){//CALCULATE NEXT FINISHED GEM ID
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(fg_id) from finish");
            int g_fid;
            if(rs.next()){
                
                g_fid = rs.getInt(1);
                g_fid =g_fid+1;
                g_ffid = String.valueOf("f"+g_fid);                      
            }else
            {
                g_fid = 1;
                g_ffid = String.valueOf("f"+g_fid);  
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }       
    }

private void ageCalCustomer(){
   
    Date date1 = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    int YearNow = Integer.parseInt(sdf.format(date1));
    int YearSel = Integer.parseInt(sdf.format(datechooser_udob1.getDate()));
    int age;
    u_age = YearNow - YearSel;
    System.out.println(""+u_age);
}

private void ageCalAgent(){
   
    Date date1 = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    int YearNow = Integer.parseInt(sdf.format(date1));
    int YearSel = Integer.parseInt(sdf.format(datechooser_udob2.getDate()));
    int age;
    u_age = YearNow - YearSel;
    System.out.println(""+u_age);
}

private void ageCalGempolisher(){
   
    Date date1 = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    int YearNow = Integer.parseInt(sdf.format(date1));
    int YearSel = Integer.parseInt(sdf.format(datechooser_udob3.getDate()));
    int age;
    u_age = YearNow - YearSel;
    System.out.println(""+u_age);
}


private void fetchRawGems(){//FETCH RAW GEM TABLE IN MANAGE RAW GEM FRAME
        Connection con = SqlConnection.getCon();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{String query = "SELECT * FROM raw";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tble_ManageRawGem.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchClickRawGemManage(){//FETCH CLICK RAW GEM TABLE IN MANAGE RAW GEM FRAME
       int row = tble_ManageRawGem.getSelectedRow();
       String tc =tble_ManageRawGem.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `raw` where rg_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                 g_rrid=rs.getString("rg_id");
                 g_id=rs.getInt("g_no");
                 rg_adate=rs.getString("rg_adate");          
                 String g_name=rs.getString("rg_name");
                 String g_type=rs.getString("rg_type");
                 String g_color=rs.getString("rg_color");
                 String g_weight=rs.getString("rg_weight");
                 String g_bprice=rs.getString("rg_bprice");
                 String g_sprice=rs.getString("rg_sprice");
                 String g_origin=rs.getString("rg_orgin");
                 String g_nature=rs.getString("rg_nature");
                 String g_status=rs.getString("rg_status");

                 byte[] img = rs.getBytes("rg_image");
                 ImageIcon image = new ImageIcon(img);
                 Image im = image.getImage();
                 Image myImg = im.getScaledInstance(lbl_gemImage.getWidth(), lbl_gemImage.getHeight(),Image.SCALE_DEFAULT);
                 ImageIcon newImage =new ImageIcon(myImg);
                 ImageIcon newImage1 =new ImageIcon(myImg);
                 lbl_rawgemimage.setIcon(newImage);

                 txt_rg_name.setText(g_name);
                 txt_rg_type.setText(g_type);
                 txt_rg_color.setText(g_color);
                 txt_rg_weight.setText(g_weight);
                 txt_rg_buyPrice.setText(g_bprice);
                 txt_rg_sellPrice.setText(g_sprice);
                 txt_rg_origin.setText(g_origin);
                 txt_rg_nature.setText(g_nature);
                 txt_rg_Status.setText(g_status);
 
                 btn_addRawGem.setVisible(false);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

private void fetchAllSuppliers(){

        try{String query = "SELECT * FROM supplier WHERE `u_no` !='0'";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_supplier.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }

private void fetchAllSupplierClick(){
       int row = tbl_supplier.getSelectedRow();
       String tc =tbl_supplier.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `supplier` where s_id =? "; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                u_id =rs.getString("u_no");
                 String fname=rs.getString("s_fname");
                 String lname=rs.getString("s_lname");
                 String address1=rs.getString("s_ad1");
                 String address2=rs.getString("s_ad2");
                 String address3=rs.getString("s_ad3");
                 u_dob=rs.getString("s_dob");
                 String nic=rs.getString("s_nic");
                 String email=rs.getString("s_email");
                 String contact=rs.getString("s_phone");
                 String password=rs.getString("s_pwd");
         
                 String lnumber=rs.getString("s_glid");
                 String Bnumber=rs.getString("s_bankacc");
          

                 text_ufname.setText(fname);
                 text_ulname.setText(lname);
                 text_uadd1.setText(address1);
                 text_uadd2.setText(address2);
                 text_uadd3.setText(address3);
                 //((JTextField)datechooser_udob.getDateEditor().getUiComponent()).setText(u_dob);
                 java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(u_dob);
                 datechooser_udob.setDate(date);
      
                 text_unic.setText(nic);
                 text_uemail.setText(email);
                 text_uphone.setText(contact);
                 text_upwd.setText(password);
                 text_ulno.setText(lnumber);
                 text_ubacc.setText(Bnumber);
                 
              
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
    
private void fetchAllClients(){

        try{String query = "SELECT * FROM client where u_no !='0' ";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_client.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }


private void fetchAllClientClick(){
       int row = tbl_client.getSelectedRow();
       String tc =tbl_client.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `client` where c_id =? "; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
               
               u_id =rs.getString("u_no");
               c_id=rs.getString("c_id");
                 System.out.println(u_id);
                 System.out.println(c_id);
                 String fname=rs.getString("c_fname");
                 String lname=rs.getString("c_lname");
                 String address1=rs.getString("c_ad1");
                 String address2=rs.getString("c_ad2");
                 String address3=rs.getString("c_ad3");
                u_dob=rs.getString("c_dob");
                 String nic=rs.getString("c_nic");
                 String email=rs.getString("c_email");
                 String contact=rs.getString("c_phone");
                 String password=rs.getString("c_pwd");
                 String region=rs.getString("c_type");
                 
          

                 text_ufname1.setText(fname);
                 text_ulname1.setText(lname);
                 text_uadd4.setText(address1);
                 text_uadd5.setText(address2);
                 text_uadd6.setText(address3);
                 //((JTextField)datechooser_udob1.getDateEditor().getUiComponent()).setText(dob);
                 java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(u_dob);
                 datechooser_udob1.setDate(date);

                 text_unic1.setText(nic);
                 text_uemail1.setText(email);
                 text_uphone1.setText(contact);
                 text_upwd1.setText(password);
                 comboURegion.setSelectedItem(region);
                 
              
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

 private void fetchAllAgents(){

        try{String query = "SELECT * FROM agent where u_no !='0' ";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_agent.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }

private void fetchAllAgentClick(){
       int row = tbl_agent.getSelectedRow();
       String tc =tbl_agent.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `agent` where a_id =? "; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                 u_id =rs.getString("u_no");
                 String fname=rs.getString("a_fname");
                 String lname=rs.getString("a_lname");
                 String address1=rs.getString("a_ad1");
                 String address2=rs.getString("a_ad2");
                 String address3=rs.getString("a_ad3");
                 String dob=rs.getString("a_dob");
                 String nic=rs.getString("a_nic");
                 String email=rs.getString("a_email");
                 String contact=rs.getString("a_phone");
                 String password=rs.getString("a_pwd");
                 String date = rs.getString("a_jdate");
                 String worktype=rs.getString("a_worktype");
                 String Bnumber=rs.getString("a_bankacc");
          

                 text_ufname2.setText(fname);
                 text_ulname2.setText(lname);
                 text_uadd7.setText(address1);
                 text_uadd8.setText(address2);
                 text_uadd9.setText(address3);
                 //((JTextField)datechooser_udob2.getDateEditor().getUiComponent()).setText(dob);
                 java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
                 datechooser_udob2.setDate(date1);
                 text_unic2.setText(nic);
                 text_uemail2.setText(email);
                 text_uphone2.setText(contact);
                 text_upwd2.setText(password);
                 //((JTextField)datechooser_jdate.getDateEditor().getUiComponent()).setText(date);
                 java.util.Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                 datechooser_jdate.setDate(date3);
                 text_ubacc1.setText(Bnumber);
                 cmb_worktype.setSelectedItem(worktype);
              
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

private void fetchAllGemPolishers(){
        Connection con = SqlConnection.getCon();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{String query = "SELECT * FROM gempolisher";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_gempolisher.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }

private void fetchClickGemPolisher(){
       int row = tbl_gempolisher.getSelectedRow();
       String tc =tbl_gempolisher.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `gempolisher` where e_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
               
                uu_id=rs.getInt("u_no");
                ee_id=rs.getString("e_id");
                String gp_fname=rs.getString("e_fname");
                String gp_lname=rs.getString("e_lname");
                String gp_ad1=rs.getString("e_ad1");
                String gp_ad2=rs.getString("e_ad2");
                String gp_ad3=rs.getString("e_ad3");
                String gp_phone=rs.getString("e_phone");
                String gp_dob=rs.getString("e_dob");
                String gp_email=rs.getString("e_email");
                String gp_nic=rs.getString("e_nic");
                String gp_fsalary=rs.getString("e_salary");
                String gp_bank=rs.getString("e_acc");
               String gp_date = rs.getString("e_jdate");
                String gp_position=rs.getString("e_wposition");
                String gp_password=rs.getString("e_pwd");

                 //text_exGPid.setText(ee_id);
                 text_ufname3.setText(gp_fname);
                 text_ulname3.setText(gp_lname);
                 java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(gp_dob);
                 datechooser_udob3.setDate(date);

                 text_unic3.setText(gp_nic);
                 text_uemail3.setText(gp_email);
                 text_uadd10.setText(gp_ad1);
                 text_uadd11.setText(gp_ad2);
                 text_uadd12.setText(gp_ad3);
                 text_uphone3.setText(gp_phone);
                 text_upwd3.setText(gp_password);
                 text_salary.setText(gp_fsalary);
                 text_ubacc2.setText(gp_bank);
                 
                 java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(gp_date);
                 datechooser_jdate2.setDate(date1);

                 cmb_worktype2.setSelectedItem(gp_position);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

    
private void fetchAllOwners(){

        try{String query = "SELECT * FROM owner WHERE `o_id` !='0'";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_owner.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }


private void fetchAllOwnerClick(){
       int row = tbl_owner.getSelectedRow();
       String tc =tbl_owner.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `owner` where o_id =? "; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
               
               o_id =rs.getString("o_id");
             
                 String fname=rs.getString("o_fname");
                 String lname=rs.getString("o_lanme");
                 String address1=rs.getString("o_ad1");
                 String address2=rs.getString("o_ad2");
                 String address3=rs.getString("o_ad3");
                 String contact=rs.getString("o_phone");
                 String email=rs.getString("o_email");
                 String nic=rs.getString("o_nic");
                 String password=rs.getString("o_pwd");
                 
                 text_ufname4.setText(fname);
                 text_ulname4.setText(lname);
                 text_uadd13.setText(address1);
                 text_uadd14.setText(address2);
                 text_uadd15.setText(address3);
                 text_uphone4.setText(contact);
                 text_uemail4.setText(email);
                 text_unic4.setText(nic);
                 text_upwd4.setText(password);
                
                 
              
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }



//-------------------------------------------------------------------------------------------

private void setUserData(){
     
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_fname =text_ufname.getText();
     u_lname =text_ulname.getText();
     u_ad_1 = text_uadd1.getText();
     u_ad_2 = text_uadd2.getText();
     u_ad_3 = text_uadd3.getText();
     u_phone= text_uphone.getText();      
     u_dob= sdf.format(datechooser_udob.getDate());
     ageCalc();

     u_nic= text_unic.getText();
     u_email= text_uemail.getText();
     u_pwd= text_upwd.getText();
     u_scid = text_ulno.getText();
     u_sbacc = text_ubacc.getText();
                         
    }

private void setUserDataToUpdate(){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_fname =text_ufname.getText();
     u_lname =text_ulname.getText();
     u_ad_1 = text_uadd1.getText();
     u_ad_2 = text_uadd2.getText();
     u_ad_3 = text_uadd3.getText();
     u_phone= text_uphone.getText();
     u_dob = sdf.format(datechooser_udob.getDate());
     ageCalc();
     u_nic= text_unic.getText();
     u_email= text_uemail.getText();
     u_pwd= text_upwd.getText();
     u_scid = text_ulno.getText();
     u_sbacc = text_ubacc.getText();
                         
    }

private void setCusUserData(){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_fname =text_ufname1.getText();
     u_lname =text_ulname1.getText();
     u_ad_1 = text_uadd4.getText();
     u_ad_2 = text_uadd5.getText();
     u_ad_3 = text_uadd6.getText();
     u_phone= text_uphone1.getText();
     u_dob= sdf.format(datechooser_udob1.getDate());
     ageCalc();
     u_nic= text_unic1.getText();
     u_email= text_uemail1.getText();
     u_pwd= text_upwd1.getText();
     u_ctype = comboURegion.getSelectedItem().toString();
    
  
    }

private void setCusUserDataToupdate(){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_fname =text_ufname1.getText();
     u_lname =text_ulname1.getText();
     u_ad_1 = text_uadd4.getText();
     u_ad_2 = text_uadd5.getText();
     u_ad_3 = text_uadd6.getText();
     u_phone= text_uphone1.getText();
     u_dob = today;
     u_nic= text_unic1.getText();
     u_email= text_uemail1.getText();
     u_pwd= text_upwd1.getText();
     u_ctype = comboURegion.getSelectedItem().toString();
    
  
    }
    
private void setAgentUserData(){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_fname =text_ufname2.getText();
     u_lname =text_ulname2.getText();
     u_ad_1 = text_uadd7.getText();
     u_ad_2 = text_uadd8.getText();
     u_ad_3 = text_uadd9.getText();
     u_phone= text_uphone2.getText();
    if (datechooser_udob2.getDate() == null) {
          JOptionPane.showMessageDialog(null, "Please type birthday", "Warning!", JOptionPane.ERROR_MESSAGE);
        }else{u_dob= sdf.format(datechooser_udob2.getDate());
         ageCalAgent();
       }
     u_nic= text_unic2.getText();
    u_email= text_uemail2.getText();
     u_pwd= text_upwd2.getText();
     u_ctype = cmb_worktype.getSelectedItem().toString();
    u_sbacc = text_ubacc1.getText();
  
    if (datechooser_jdate.getDate() == null) {
          JOptionPane.showMessageDialog(null, "Please type Joined date", "Warning!", JOptionPane.ERROR_MESSAGE);
        }else{u_jdate= sdf.format(datechooser_jdate.getDate());
        
       }
}

private void setAgentUserDataToupdate(){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    u_fname =text_ufname2.getText();
     u_lname =text_ulname2.getText();
     u_ad_1 = text_uadd7.getText();
     u_ad_2 = text_uadd8.getText();
     u_ad_3 = text_uadd9.getText();
     u_phone= text_uphone2.getText();
     u_dob = today;
     u_jdate = today;
      u_nic= text_unic2.getText();
    u_email= text_uemail2.getText();
     u_pwd= text_upwd2.getText();
     u_ctype = cmb_worktype.getSelectedItem().toString();
    u_sbacc = text_ubacc1.getText();
 
    }
    


private void setGempolisherUserData(){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_fname =text_ufname3.getText();
     u_lname =text_ulname3.getText();
     u_ad_1 = text_uadd10.getText();
     u_ad_2 = text_uadd11.getText();
     u_ad_3 = text_uadd12.getText();
     u_phone= text_uphone3.getText();
    if (datechooser_udob3.getDate() == null) {
          JOptionPane.showMessageDialog(null, "Please type birthday", "Warning!", JOptionPane.ERROR_MESSAGE);
        }else{u_dob= sdf.format(datechooser_udob3.getDate());
         ageCalGempolisher();
       }

     
     u_nic= text_unic3.getText();
     u_email= text_uemail3.getText();
     u_pwd= text_upwd3.getText();
     u_ctype = cmb_worktype2.getSelectedItem().toString();
     u_scid=text_salary.getText();
     u_sbacc=text_ubacc2.getText();

if (datechooser_jdate2.getDate() == null) {
          JOptionPane.showMessageDialog(null, "Please Enter Joined date", "Warning!", JOptionPane.ERROR_MESSAGE);
        }else{u_jdate= sdf.format(datechooser_jdate2.getDate());
       
       }
  
    }

private void setGempolisherUserDataToupdate(){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_fname =text_ufname3.getText();
     u_lname =text_ulname3.getText();
     u_ad_1 = text_uadd10.getText();
     u_ad_2 = text_uadd11.getText();
     u_ad_3 = text_uadd12.getText();
     u_phone= text_uphone3.getText();
     u_dob = sdf.format(datechooser_udob3.getDate());
     ageCalGempolisher();
     u_nic= text_unic3.getText();
     u_email= text_uemail3.getText();
     u_pwd= text_upwd3.getText();
     u_ctype = cmb_worktype2.getSelectedItem().toString();
     u_scid=text_salary.getText();
     u_sbacc=text_ubacc2.getText();
      u_jdate = sdf.format(datechooser_jdate2.getDate());
    }

private void setOwnerData(){

     u_fname =text_ufname4.getText();
     u_lname =text_ulname4.getText();
     u_ad_1 = text_uadd13.getText();
     u_ad_2 = text_uadd14.getText();
     u_ad_3 = text_uadd15.getText();
     u_phone= text_uphone4.getText();
     u_nic= text_unic4.getText();
     u_email= text_uemail4.getText();
     u_pwd= text_upwd4.getText();
     
   
  
    }

//-----------------------------------------------------------

  
    private void validateSupplier(){
   
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

       if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Last Name should have at least Three Characters");
        }else if(datechooser_udob.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please Enter BirthDay!");
        }else if(u_dob.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Date of Birth!");
        }else if(u_age<18){
            JOptionPane.showMessageDialog(this,"You should be at Least 18 Years old!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please EnterAddress Line 3!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please EnterContact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        }else if (u_sbacc.equals("")) {
         JOptionPane.showMessageDialog(this,"Please Enter Bank Account Number");
    }else if(u_sbacc.length()<8||u_sbacc.length()>15){
         JOptionPane.showMessageDialog(this,"Please Valid Bank Account Number");
    }else if(u_scid.equals("")){
         JOptionPane.showMessageDialog(this,"Please Enter Your Gem Selling License Number");
    }else if(u_scid.length()<8||u_scid.length()>15){
         JOptionPane.showMessageDialog(this,"Please Enter Valid Gem Selling License Number");
    }else {

     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_dob= sdf.format(datechooser_udob.getDate());
   
     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"Supplier", this);
     main.inputUser();
     
     Supplier mains = new Supplier();
     mains.setSupplier(s_id, u_id, "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd, u_scid, u_sbacc, this);
     mains.inputSupplier();
     ownerUserInput();
     clear();
     fetchAllSuppliers();
     
    }
}

private void validateSupplierandUpdate(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

       if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Last Name should have at least Three Characters");
        }else if(datechooser_udob.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please Enter BirthDay!");
        }else if(u_dob.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Date of Birth!");
        }else if(u_age<18){
            JOptionPane.showMessageDialog(this,"You should be at Least 18 Years old!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please EnterAddress Line 3!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please EnterContact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        }else if (u_sbacc.equals("")) {
         JOptionPane.showMessageDialog(this,"Please Enter Bank Account Number");
    }else if(u_sbacc.length()<8||u_sbacc.length()>15){
         JOptionPane.showMessageDialog(this,"Please Valid Bank Account Number");
    }else if(u_scid.equals("")){
         JOptionPane.showMessageDialog(this,"Please Enter Your Gem Selling License Number");
    }else if(u_scid.length()<8||u_scid.length()>15){
         JOptionPane.showMessageDialog(this,"Please Enter Valid Gem Selling License Number");
    }else {

     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"Supplier", this);
     main.updateUser();
     
     Supplier mains = new Supplier();
     mains.setSupplier(s_id, u_id, "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd, u_scid, u_sbacc, this);
     mains.updateSupplier();
     clear();
     fetchAllSuppliers();
     
    }
}
    

private void deleteSupplier(){


       if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_ad_3.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if (u_sbacc.equals("")) {
         JOptionPane.showMessageDialog(this,"Please Select User!");
    }else if(u_sbacc.length()<8||u_sbacc.length()>15){
         JOptionPane.showMessageDialog(this,"Please Select User!");
    }else if(u_scid.equals("")){
         JOptionPane.showMessageDialog(this,"Please Select User!");
    }else if(u_scid.length()<8||u_scid.length()>15){
         JOptionPane.showMessageDialog(this,"Please Select User!");
    }else 
    {
     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"Supplier", this);
     main.delete();
     
     Supplier mains = new Supplier();
     mains.setSupplier(s_id, u_id, "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd, u_scid, u_sbacc, this);
     mains.deleteSupplier();
     ownerUserDelete();
     clear();
     fetchAllSuppliers();
    }
}

private void validateClient(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

       if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Last Name should have at least Three Characters");
        }else if(datechooser_udob1.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please Enter BirthDay!");
        }else if(u_dob.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Date of Birth!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please EnterAddress Line 3!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please EnterContact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        }else  if (u_ctype.equals("Select")) {
         JOptionPane.showMessageDialog(this,"Please Select Your Region");
    } else {

     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"Client", this);
     System.out.println(u_id);
     main.inputUser();
     ownerUserInput();

    Client mainc = new Client();
    mainc.setClient(c_id, u_id, "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd, u_ctype, this);
    mainc.inputClient();
   
    clearclient();
    fetchAllClients();
    }
}

private void validateClientandUpdate(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

     if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Last Name should have at least Three Characters");
        }else if(datechooser_udob1.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please Enter BirthDay!");
        }else if(u_dob.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Date of Birth!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please EnterAddress Line 3!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please EnterContact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        }else if (u_ctype.equals("Select")) {
         JOptionPane.showMessageDialog(this,"Please Select Your Region");
    } else {
     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"Client", this);
     main.updateUser();
     
     Client mains = new Client();
     mains.setClient(c_id, u_id, "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd, u_ctype, this);
     mains.updateClient();
     clear();
     fetchAllClients();
     
    }    
}

 private void deleteClient(){
    
     if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_ad_3.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else{
         User main = new User();
        main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"Client", this);
        main.delete();
     
         Client mains = new Client();
         mains.setClient(c_id, u_id, "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd, u_ctype, this);
         mains.deleteClient();
         ownerUserDelete();
            clear();
            fetchAllClients();
        }
    
    }

private void validateAgent(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

       if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Last Name should have at least Three Characters");
        }else if(datechooser_udob2.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please Enter BirthDay!");
        }else if(u_dob.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Date of Birth!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please EnterAddress Line 3!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please EnterContact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        }else if(u_sbacc.isEmpty()){
              JOptionPane.showMessageDialog(this,"Please Enter bank Account Number");
         }else if(u_sbacc.length()< 8 || u_sbacc.length()>12){
            JOptionPane.showMessageDialog(this,"Input Valid Bank Account Number!");
        }else if(datechooser_jdate.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please Enter Joined Date");
        }else if(u_jdate.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Joined Date!");
        }else if (u_ctype.equals("Select")) {
         JOptionPane.showMessageDialog(this,"Please Select the Region");
        } else{

     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_dob= sdf.format(datechooser_udob2.getDate());

    User main = new User();
    main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"Agent", this);
    main.inputUser();
    ownerUserInput();
    Agent maina = new Agent();
    maina.setAgent(a_id, u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_dob, String.valueOf(u_age), u_phone, u_email,  u_nic, u_jdate, u_sbacc, u_ctype, u_pwd, this);
    maina.inputAgent();
    clearAgent();
    fetchAllAgents();

    }
}

private void validateAgentandUpdate(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

     if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Last Name should have at least Three Characters");
        }else if(datechooser_udob2.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please Enter BirthDay!");
        }else if(u_dob.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Date of Birth!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please EnterAddress Line 3!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please EnterContact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        }else if(u_sbacc.isEmpty()){
              JOptionPane.showMessageDialog(this,"Please Enter bank Account Number");
         }else if(u_sbacc.length()< 8 || u_sbacc.length()>12){
            JOptionPane.showMessageDialog(this,"Input Valid Bank Account Number!");
        }else if(datechooser_jdate.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please Enter Joined Date");
        }else if(u_jdate.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Joined Date!");
        }else if (u_ctype.equals("Select")) {
         JOptionPane.showMessageDialog(this,"Please Select the Region");
        } else {
     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"Agent", this);
     main.updateUser();
     
     Agent mains = new Agent();
     mains.setAgent(a_id, u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_dob, String.valueOf(u_age), u_phone, u_email,  u_nic, u_jdate, u_sbacc, u_ctype, u_pwd, this);
     mains. updateAgent();
     clearAgent();
     fetchAllAgents();
     
    }    
}

 private void deleteAgent(){
    
      if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_ad_3.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else{
     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"Agent", this);
     main.delete();
     ownerUserDelete();
     Agent mains = new Agent();
     mains.setAgent(a_id, u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_dob, String.valueOf(u_age), u_phone, u_email,  u_nic, u_jdate, u_sbacc, u_ctype, u_pwd, this);
     mains.deleteAgent();
     clearAgent();
     fetchAllAgents();
    }
    }



private void validateGempolisher(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

       if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"Your First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Your Last Name should have at least Three Characters");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Your Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Your Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Your Address Line 3!");
        }else if(u_ad_3.length()<3){
            JOptionPane.showMessageDialog(this,"Please Enter Valid Address Line 3!");
        }else if (u_ctype.equals("Select")) {
         JOptionPane.showMessageDialog(this,"Please Select Your Region");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Contact Number!");
        }else if(u_phone.length()< 8){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Password!");
        }else if(u_pwd.length()<6){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        }else if(u_scid.isEmpty()||u_scid.equals("0")){
        JOptionPane.showMessageDialog(this,"Please Enter Salary");
        }else if(u_sbacc.isEmpty()||u_sbacc.length()<8){
         JOptionPane.showMessageDialog(this,"Please Enter Correct Bank Account");
        }else{

     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"GemPolisher", this);
     System.out.println(u_id);
     main.inputUser();
     ownerUserInput();
    Gempolisher mainc = new Gempolisher();
    mainc.setGempolisher(e_id, u_id, "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_ctype,u_scid,u_sbacc, this);
    mainc.inputGempolisher();
    clearGempolisher();
    fetchAllGemPolishers();
    }
}

private void validateGempolisherToUpdate(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

     if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"Your First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Your Last Name should have at least Three Characters");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Your Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Your Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Your Address Line 3!");
        }else if(u_ad_3.length()<3){
            JOptionPane.showMessageDialog(this,"Please Enter Valid Address Line 3!");
        }else if (u_ctype.equals("Select")) {
         JOptionPane.showMessageDialog(this,"Please Select Your Region");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Contact Number!");
        }else if(u_phone.length()< 8){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Password!");
        }else if(u_pwd.length()<6){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        }else if(u_scid.isEmpty()||u_scid.equals("0")){
        JOptionPane.showMessageDialog(this,"Please Enter Salary");
        }else if(u_sbacc.isEmpty()||u_sbacc.length()<8){
         JOptionPane.showMessageDialog(this,"Please Enter Correct Bank Account");
        } else {
     User main = new User();
     main.setInputUser(String.valueOf(uu_id), u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"GemPolisher", this);
     main.updateUser();
     
     Gempolisher maing = new Gempolisher();
     maing.setGempolisher(ee_id, String.valueOf(uu_id), "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_ctype,u_scid,u_sbacc, this);
     maing.updateGempolisher();
     clearGempolisher();
     fetchAllGemPolishers();
     
    }    
}

private void deleteGempolisher(){
    
     if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_ad_3.length()<3){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Select User!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Please Select User!");
        }else{
     User main = new User();
     main.setInputUser(String.valueOf(uu_id), u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_dob, String.valueOf(u_age), u_nic, today, u_email, u_pwd,"GemPolisher", this);
     main.delete();
     gownerUserDelete();
     Gempolisher maing = new Gempolisher();
     maing.setGempolisher(e_id, String.valueOf(uu_id), "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_ctype,u_scid,u_sbacc, this);
     maing.deleteGempolisher();
     clearGempolisher();
     fetchAllGemPolishers();}
    
    }

private void validateOwner(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

       if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Last Name should have at least Three Characters");
        }else if(u_ad_3.length()<3){
            JOptionPane.showMessageDialog(this,"Please Enter Valid Address Line 3!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please EnterAddress Line 3!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please EnterContact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        } else {

     Owner main = new Owner();
     main.setOwner(o_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_nic,u_email,  u_pwd, this);
     main.inputOwner();
     clearOwner();
   
    }
}

private void validateOwnertoUpdate(){
 

       Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

       if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Last Name should have at least Three Characters");
        }else if(u_ad_3.length()<3){
            JOptionPane.showMessageDialog(this,"Please Enter Valid Address Line 3!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please EnterAddress Line 3!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please EnterContact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        } else {

     Owner main = new Owner();
     main.setOwner(o_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_nic,u_email, u_pwd, this);
     main.updateOwner();
     clearOwner();
   
    }
}

private void deleteOwner(){
    if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Last Name should have at least Three Characters");
        }else if(u_ad_3.length()<3){
            JOptionPane.showMessageDialog(this,"Please Enter Valid Address Line 3!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Email Address!");
        }else if(u_ad_1.equals("Address Line 1") ||  u_ad_1.equals("")){ 
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 1!");
        }else if(u_ad_1.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 1!");
        }else if(u_ad_2.equals("Address Line 2")|| u_ad_2.equals("")){
            JOptionPane.showMessageDialog(this, "Please Enter Address Line 2!");
        }else if(u_ad_2.length()<3){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Address Line 2!");
        }else if(u_ad_3.equals("Address Line 3")|| u_ad_3.equals("")){
            JOptionPane.showMessageDialog(this,"Please EnterAddress Line 3!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please EnterContact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Password must have more than 6 Characters");
        }else{

     Owner main = new Owner();
     main.setOwner(o_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone,u_email, u_nic,  u_pwd, this);
     main.deleteOwner();
     clearOwner();
     }
     
    
    
    }

//------------------------------------------------------------------------------------------------

private void validateRawGemInput(){//VALIDATIONS FOR RAW GEMS ADD
       if(txt_rg_name.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Name");
        }else if(txt_rg_name.getText().length()<3 || txt_rg_name.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Name");
        }else if(txt_rg_type.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Type");
        }else if(txt_rg_type.getText().length()<3 || txt_rg_type.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Type");
        }else if(txt_rg_color.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem color");
        }else if(txt_rg_color.getText().length()<3 || txt_rg_color.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Color");
        }else if(txt_rg_weight.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Weight");
        }else if(txt_rg_weight.getText().equals("0") || txt_rg_weight.getText().length()>8){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Weight");
        }else if(txt_rg_buyPrice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Buying Price");
        }else if(txt_rg_buyPrice.getText().equals("0") || txt_rg_buyPrice.getText().length()<2|| txt_rg_buyPrice.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Buy Price");
        }
        else if(txt_rg_origin.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Origin Of Gem");
        }else if(txt_rg_origin.getText().length()<3 || txt_rg_origin.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Origin");
        }
        else if(txt_rg_nature.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Nature");
        }else if(txt_rg_nature.getText().length()<3 || txt_rg_nature.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Nature");
        }
        else if(txt_rg_Status.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Status");
        }else if(txt_rg_Status.getText().length()<3 || txt_rg_Status.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Status");
        }
        else if(txt_rg_sellPrice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Sell Price");
        }else if(txt_rg_sellPrice.getText().equals("0")||txt_rg_sellPrice.getText().length()<2|| txt_rg_sellPrice.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Sell Price");
        }
        else if(lbl_rawgemimage.getIcon()==null){
            JOptionPane.showMessageDialog(this,"Please Upload your gem Image!");
        }
        
        else{
                      Gem gem = new Gem();
                      gem.setInputOwner(g_id, txt_rg_name.getText(), txt_rg_type.getText(), txt_rg_color.getText(), txt_rg_weight.getText(), txt_rg_buyPrice.getText(), txt_rg_sellPrice.getText(), imagePathStr, today, txt_rg_Status.getText());
                      gem.inputOwner();
                      RawGem gemr = new RawGem();
                      gemr.setRawGemOwner(g_id,txt_rg_name.getText(), txt_rg_type.getText(), txt_rg_color.getText(), txt_rg_weight.getText(), txt_rg_buyPrice.getText(), txt_rg_sellPrice.getText(), imagePathStr,today,g_rrid,txt_rg_origin.getText(),txt_rg_nature.getText(),txt_rg_Status.getText() );
                      gemr.ownerInput();
                      ownerGemInput();
                      clearRawGemManage();
                      fetchRawGems();
                      g_id = null;
                      g_rrid = null;
                      
                      
                      
           }
}
private void validateRawGemUpdate(){//VALIDATIONS FOR RAW GEMS UPDATE
        if(txt_rg_name.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Name");
        }else if(txt_rg_name.getText().length()<3 || txt_rg_name.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Name");
        }else if(txt_rg_type.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Type");
        }else if(txt_rg_type.getText().length()<3 || txt_rg_type.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Type");
        }else if(txt_rg_color.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem color");
        }else if(txt_rg_color.getText().length()<3 || txt_rg_color.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Color");
        }else if(txt_rg_weight.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Weight");
        }else if(txt_rg_weight.getText().equals("0") || txt_rg_weight.getText().length()>8){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Weight");
        }else if(txt_rg_buyPrice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Buying Price");
        }else if(txt_rg_buyPrice.getText().equals("0") || txt_rg_buyPrice.getText().length()<2|| txt_rg_buyPrice.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Buy Price");
        }
        else if(txt_rg_origin.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Origin Of Gem");
        }else if(txt_rg_origin.getText().length()<3 || txt_rg_origin.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Origin");
        }
        else if(txt_rg_nature.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Nature");
        }else if(txt_rg_nature.getText().length()<3 || txt_rg_nature.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Nature");
        }
        else if(txt_rg_Status.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Status");
        }else if(txt_rg_Status.getText().length()<3 || txt_rg_Status.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Status");
        }
        else if(txt_rg_sellPrice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Sell Price");
        }else if(txt_rg_sellPrice.getText().equals("0")||txt_rg_sellPrice.getText().length()<2|| txt_rg_sellPrice.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Sell Price");
        }
        else if(lbl_rawgemimage.getIcon()==null){
            JOptionPane.showMessageDialog(this,"Please Upload your gem Image!");
        }
        
        
        else{
                      Gem gem = new Gem();
                      gem.setInputOwner(g_id, txt_rg_name.getText(), txt_rg_type.getText(), txt_rg_color.getText(), txt_rg_weight.getText(), txt_rg_buyPrice.getText(), txt_rg_sellPrice.getText(), imagePathStr, rg_adate, txt_rg_Status.getText());
                      gem.update();
                      RawGem gemr = new RawGem();
                      gemr.setRawGemOwner(g_id,txt_rg_name.getText(), txt_rg_type.getText(), txt_rg_color.getText(), txt_rg_weight.getText(), txt_rg_buyPrice.getText(), txt_rg_sellPrice.getText(), imagePathStr,rg_adate,g_rrid,txt_rg_origin.getText(),txt_rg_nature.getText(),txt_rg_Status.getText() );
                      gemr.update();
                      clearRawGemManage();
                      fetchRawGems();
                      g_id = null;
                      g_rrid = null;
                      
                      
           }
}
private void validateRawGemDelete(){//VALIDATIONS FOR RAW GEMS DELETE

        if(txt_rg_name.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select Gem First!");
        }else if(txt_rg_type.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select Gem First");
        }else if(txt_rg_color.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select Gem First");
        }else if(txt_rg_weight.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select Gem First");
        }else if(txt_rg_buyPrice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select Gem First");
        }
        else if(txt_rg_origin.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select Gem First");
        }
        else if(txt_rg_nature.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select Gem First");
        }
        else if(txt_rg_Status.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select Gem First");
        }
        else if(txt_rg_sellPrice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select Gem First");
        }
        
        else{
                      Gem gem = new Gem();
                      gem.setInputOwner(g_id, txt_rg_name.getText(), txt_rg_type.getText(), txt_rg_color.getText(), txt_rg_weight.getText(), txt_rg_buyPrice.getText(), txt_rg_sellPrice.getText(), today, rg_adate, txt_rg_Status.getText());
                      gem.delete();
                      RawGem gemr = new RawGem();
                      gemr.setRawGemOwner(g_id,txt_rg_name.getText(), txt_rg_type.getText(), txt_rg_color.getText(), txt_rg_weight.getText(), txt_rg_buyPrice.getText(), txt_rg_sellPrice.getText(), today,rg_adate,g_rrid,txt_rg_origin.getText(),txt_rg_nature.getText(),txt_rg_Status.getText() );
                      gemr.delete();
                      ownerGemDelete();
                      clearRawGemManage();
                      fetchRawGems();
                      g_id = null;
                      g_rrid = null;
                      
                      
           }
}
private void fetchFinishedGems(){//FETCH FINISHED GEM TABLE IN MANAGE FINISHED GEM FRAME
        try{String query = "SELECT * FROM finish";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tble_ManageFinishedGems.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchClickFinishedGemManage(){//FETCH CLICK FINISHED GEM TABLE IN MANAGE FINISHED GEM FRAME
       int row = tble_ManageFinishedGems.getSelectedRow();
       String tc =tble_ManageFinishedGems.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `finish` where fg_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                 g_ffid=rs.getString("fg_id");
                 g_id=rs.getInt("g_no");
                 rg_adate=rs.getString("fg_adate");          
                 String g_name=rs.getString("fg_name");
                 String g_type=rs.getString("fg_type");
                 String g_color=rs.getString("fg_color");
                 String g_weight=rs.getString("fg_weight");
                 String g_bprice=rs.getString("fg_bprice");
                 String g_sprice=rs.getString("fg_sprice");
                 String g_cid=rs.getString("fg_cid");
                 String g_shape=rs.getString("fg_shape");
                 String g_status=rs.getString("fg_status");
                 String g_pieces=rs.getString("fg_pieces");

                 byte[] img = rs.getBytes("fg_image");
                 ImageIcon image = new ImageIcon(img);
                 Image im = image.getImage();
                 Image myImg = im.getScaledInstance(lbl_gemImage.getWidth(), lbl_gemImage.getHeight(),Image.SCALE_DEFAULT);
                 ImageIcon newImage =new ImageIcon(myImg);
                 lbl_finishedgemimage.setIcon(newImage);

                 txt_fg_name.setText(g_name);
                 txt_fg_type.setText(g_type);
                 txt_fg_color.setText(g_color);
                 txt_fg_weight.setText(g_weight);
                 txt_fg_buyprice.setText(g_bprice);
                 txt_fg_sellprice.setText(g_sprice);
                 txt_fg_LicenseID.setText(g_cid);
                 txt_fg_shape.setText(g_shape);
                 txt_fg_status.setText(g_status);
                 txt_fg_pieces.setText(g_pieces);
              btn_addFinishedGem.setVisible(false);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
private void ownerUserInput(){
        try{ String query = "INSERT INTO `owneruser`(`o_no`, `u_no`) VALUES (?,?)"; 
           pst = con.prepareStatement(query);
           pst.setInt(1,code);
           pst.setInt(2,Integer.valueOf(u_id));
           
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
private void ownerUserDelete(){
        try{ String query = "DELETE FROM `owneruser` WHERE o_no =? and u_no =?"; 
           pst = con.prepareStatement(query);
           pst.setInt(1,code);
           pst.setInt(2,Integer.valueOf(u_id));
           
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Delete Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
private void gownerUserDelete(){
        try{ String query = "DELETE FROM `owneruser` WHERE o_no =? and u_no =?"; 
           pst = con.prepareStatement(query);
           pst.setInt(1,code);
           pst.setInt(2,uu_id);
           
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Delete Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
private void ownerGemInput(){
        try{ String query = "INSERT INTO `ownergem`(`o_no`,`g_no`) VALUES (?,?)"; 
           pst = con.prepareStatement(query);
           pst.setInt(1,code);
           pst.setInt(2,g_id);
           
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Registered Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
private void ownerGemDelete(){
        try{ String query = "DELETE FROM `ownergem` WHERE o_no =? and g_no =?"; 
           pst = con.prepareStatement(query);
           pst.setInt(1,code);
           pst.setInt(2,g_id);
           
           pst.executeUpdate();
             //JOptionPane.showMessageDialog(null, "Delete Successfully");
         }catch(Exception ex){
             JOptionPane.showMessageDialog(null, ex);
         }
    }
private void validateFinishedGemInput(){//VALIDATIONS FOR FINISHED GEMS ADD
       if(txt_fg_name.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Name");
        }else if(txt_fg_name.getText().length()<3 || txt_fg_name.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Name");
        }else if(txt_fg_type.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Type");
        }else if(txt_fg_type.getText().length()<3 || txt_fg_type.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Type");
        }else if(txt_fg_color.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem color");
        }else if(txt_fg_color.getText().length()<3 || txt_fg_color.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Color");
        }else if(txt_fg_weight.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Weight");
        }else if(txt_fg_weight.getText().equals("0") || txt_fg_weight.getText().length()>8){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Weight");
        }else if(txt_fg_buyprice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Buying Price");
        }else if(txt_fg_buyprice.getText().equals("0") || txt_fg_buyprice.getText().length()<2|| txt_fg_buyprice.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Buy Price");
        }
        else if(txt_fg_LicenseID.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Liecense ID Of the Gem");
        }else if(txt_fg_LicenseID.getText().length()<8 || txt_fg_LicenseID.getText().length()>15){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem License Number");
        }
        else if(txt_fg_shape.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Shape");
        }else if(txt_fg_shape.getText().length()<3 || txt_fg_shape.getText().length()>15){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Shape!");
        }
        else if(txt_fg_pieces.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Pieces Amount");
        }else if(txt_fg_pieces.getText().length()>2 ||txt_fg_pieces.getText().equals("0")){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Gem Pieces Amount!");
        }
        else if(txt_fg_status.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Status");
        }else if(txt_fg_status.getText().length()<3 || txt_fg_status.getText().length()>15){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Status!");
        }
        else if(txt_fg_sellprice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Sell Price");
        }else if(txt_fg_sellprice.getText().equals("0") || txt_fg_sellprice.getText().length()<2|| txt_fg_sellprice.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Sell Price");
        }
        else if(lbl_finishedgemimage.getIcon()==null){
            JOptionPane.showMessageDialog(this,"Please Upload your gem Image!");
        }
        
        else{
                      Gem gem = new Gem();
                      gem.setInputOwner(g_id, txt_fg_name.getText(), txt_fg_type.getText(), txt_fg_color.getText(), txt_fg_weight.getText(), txt_fg_buyprice.getText(), txt_fg_sellprice.getText(), imagePathStrF, today, txt_fg_status.getText());
                      gem.inputOwner();
                      FinishedGem gemf = new FinishedGem();
                      gemf.setInputOwner(g_id,txt_fg_name.getText(), txt_fg_type.getText(), txt_fg_color.getText(), txt_fg_weight.getText(), txt_fg_buyprice.getText(), txt_fg_sellprice.getText(), imagePathStrF, today,g_ffid,  txt_fg_LicenseID.getText(),  txt_fg_shape.getText(),  txt_fg_pieces.getText(),  txt_fg_status.getText());
                      gemf.ownerInput();
                      ownerGemInput();
                      clearFinishedGemManage();
                      fetchFinishedGems();
                      g_id = null;
                      g_ffid = null;
                      
                      
                      
           }
}
private void validateFinishedGemUpdate(){//VALIDATIONS FOR FINISHED GEMS UPDATE
    if(txt_fg_name.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Name");
        }else if(txt_fg_name.getText().length()<3 || txt_fg_name.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Name");
        }else if(txt_fg_type.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Type");
        }else if(txt_fg_type.getText().length()<3 || txt_fg_type.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Type");
        }else if(txt_fg_color.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem color");
        }else if(txt_fg_color.getText().length()<3 || txt_fg_color.getText().length()>29){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Color");
        }else if(txt_fg_weight.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Gem Weight");
        }else if(txt_fg_weight.getText().equals("0") || txt_fg_weight.getText().length()>8){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Weight");
        }else if(txt_fg_buyprice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Buying Price");
        }else if(txt_fg_buyprice.getText().equals("0") || txt_fg_buyprice.getText().length()<2|| txt_fg_buyprice.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Buy Price");
        }
        else if(txt_fg_LicenseID.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Liecense ID Of the Gem");
        }else if(txt_fg_LicenseID.getText().length()<8 || txt_fg_LicenseID.getText().length()>15){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem License Number");
        }
        else if(txt_fg_shape.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Shape");
        }else if(txt_fg_shape.getText().length()<3 || txt_fg_shape.getText().length()>15){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Shape!");
        }
        else if(txt_fg_pieces.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Pieces Amount");
        }else if(txt_fg_pieces.getText().length()>2 ||txt_fg_pieces.getText().equals("0")){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Gem Pieces Amount!");
        }
        else if(txt_fg_status.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Gem Status");
        }else if(txt_fg_status.getText().length()<3 || txt_fg_status.getText().length()>15){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Gem Status!");
        }
        else if(txt_fg_sellprice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Sell Price");
        }else if(txt_fg_sellprice.getText().equals("0") || txt_fg_sellprice.getText().length()<2|| txt_fg_sellprice.getText().length()>10){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Sell Price");
        }
        else if(lbl_finishedgemimage.getIcon()==null){
            JOptionPane.showMessageDialog(this,"Please Upload your gem Image!");
        }
        
        else{
                      Gem gem = new Gem();
                      gem.setInputOwner(g_id, txt_fg_name.getText(), txt_fg_type.getText(), txt_fg_color.getText(), txt_fg_weight.getText(), txt_fg_buyprice.getText(), txt_fg_sellprice.getText(), imagePathStrF, rg_adate, txt_fg_status.getText());
                      gem.update();
                      FinishedGem gemf = new FinishedGem();
                      gemf.setInputOwner(g_id,txt_fg_name.getText(), txt_fg_type.getText(), txt_fg_color.getText(), txt_fg_weight.getText(), txt_fg_buyprice.getText(), txt_fg_sellprice.getText(), imagePathStrF, rg_adate,g_ffid,  txt_fg_LicenseID.getText(),  txt_fg_shape.getText(),  txt_fg_pieces.getText(),  txt_fg_status.getText());
                      gemf.update();
                      clearFinishedGemManage();
                      fetchFinishedGems();
                      g_id = null;
                      g_ffid = null;
                      imagePathStrF = null;
                      
                      
           }
}
private void validateFinishedGemDelete(){//VALIDATIONS FOR FINISHED GEMS DELETE
       if(txt_fg_name.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select A gem First!");
        }else if(txt_fg_type.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select A gem First!");
        }else if(txt_fg_color.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select A gem First!");
        }else if(txt_fg_weight.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select A gem First!");
        }else if(txt_fg_buyprice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select A gem First!");
        }
        else if(txt_fg_LicenseID.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select A gem First!");
        }
        else if(txt_fg_shape.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select A gem First!");
        }
        else if(txt_fg_pieces.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select A gem First!");
        }
        else if(txt_fg_status.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select A gem First!");
        }
        else if(txt_fg_sellprice.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Select A gem First!");
        }
        
        else{
                      Gem gem = new Gem();
                      gem.setInputOwner(g_id, txt_fg_name.getText(), txt_fg_type.getText(), txt_fg_color.getText(), txt_fg_weight.getText(), txt_fg_buyprice.getText(), txt_fg_sellprice.getText(),today , today, txt_fg_status.getText());
                      gem.delete();
                      FinishedGem gemf = new FinishedGem();
                      gemf.setInputOwner(g_id,txt_fg_name.getText(), txt_fg_type.getText(), txt_fg_color.getText(), txt_fg_weight.getText(), txt_fg_buyprice.getText(), txt_fg_sellprice.getText(), today, today,g_ffid,  txt_fg_LicenseID.getText(),  txt_fg_shape.getText(),  txt_fg_pieces.getText(),  txt_fg_status.getText());
                      gemf.delete();
                      ownerGemDelete();
                      clearFinishedGemManage();
                      fetchFinishedGems();
                      g_id = null;
                      g_ffid = null;
                      imagePathStrF = null;
                      
                      
           }
}
private void searchGempolisherEx(){//SEARCH GEMPOLISHER IN GEM POLISHER'S EXPENSE FRAME
        try{String query = "SELECT * FROM expensegempolisher where ex_eno =?";
           pst = con.prepareStatement(query);
           pst.setString(1,textex_gempolisherSeach.getText());
           rs=pst.executeQuery();
           tbl_EXGempolisherExpenses.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchAllJobs(){//FETCH JOBS TABLE IN GEM POLISHER'S JOBS FRAME
        
        try{String query = "SELECT * FROM job";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_jobsGemPolisher.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchAssignedJobs(){//ASSIGNED WORK FOR GEMPOLISHER IN GEM POLISHER'S JOBS FRAME
        
        try{String query = "SELECT * FROM job where j_status =?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Assigned");
           rs=pst.executeQuery();
           tbl_jobsGemPolisher.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchCompletedJobs(){//FETCH COMPLETED JOBS IN GEM POLISHER'S JOBS FRAME
        
        try{String query = "SELECT * FROM job where j_status =?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Completed");
           rs=pst.executeQuery();
           tbl_jobsGemPolisher.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchAvailableRawGems(){//FETCH AVAILABLE RAW GEMS IN GEM POLISHER'S JOBS FRAME
        Connection con = SqlConnection.getCon();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{String query = "SELECT * FROM raw where rg_status =?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Finished");
           rs=pst.executeQuery();
           tbl_availaleRGems.setModel(DbUtils.resultSetToTableModel(rs));              
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchAgentExpenses(){//FETCH APPROVED AGENT EXPENSES IN AGENT EXPENSES FRAME
        try{String query = "SELECT * FROM expenseagent where ex_status =?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Approved");
           rs=pst.executeQuery();
           tbl_AgentExpenses.setModel(DbUtils.resultSetToTableModel(rs));              
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchClickAgentExpense(){//FETCH CLICK APPROVED AGENT EXPENSES IN AGENT EXPENSES FRAME
       int row = tbl_AgentExpenses.getSelectedRow();
       String tc =tbl_AgentExpenses.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM expenseagent where ex_status=? and aex_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,"Approved");
           pst.setString(2,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                aex_id=rs.getString("aex_id");
                ex_ID=rs.getInt("ex_no");
                ex_sono=rs.getString("ex_sono");
                ex_cono=rs.getString("ex_cono");
                ex_ano=rs.getString("ex_ano");
                String order_amount=rs.getString("ex_otot");
               
                 text_AEXaexid.setText(aex_id);
                 text_AEXexid.setText(""+ex_ID);
                 text_AEXsoid.setText(ex_sono);
                 text_AEXcoid.setText(ex_cono);
                 text_AEXoamount.setText(order_amount);
                 text_AEXaid.setText(ex_ano);
                 getAgentData();
                 
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
private void getAgentData(){//GET AGENT DATA IN AGENT EXPENSES FRAME
try{
           String query = "SELECT * FROM `agent` where a_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,ex_ano);
           rs =pst.executeQuery();
           if(rs.next()){            
                String s_fname=rs.getString("a_fname");
                String s_lname=rs.getString("a_lname");
                String s_email=rs.getString("a_email");
                String s_phone=rs.getString("a_phone");
                 text_AEXfname.setText(s_fname);
                 text_AEXlname.setText(s_lname);
                 text_AEXemail.setText(s_email);
                 text_AEXphone.setText(s_phone);              
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
}
private void calAgentCommission(){//VALIDATIONS AND CALUCLATE AGENT COMMISSION IN AGENT EXPENSES FRAME
            
        if (text_AEXoamount.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Please select Agent Expense First!");
        }  else if(text_AEXrate.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Please Enter Commission Rate!");
        }else if((text_AEXrate.getText().length()>2)) {
            JOptionPane.showMessageDialog(this,"Please Enter Valid Commission Rate!");
        }else{
                double orderAmount = Double.parseDouble(text_AEXoamount.getText());                
                double rate = Double.parseDouble(text_AEXrate.getText());
                double tot = (orderAmount*(rate/100));
                text_AEXtot.setText(""+tot);
              }
}
public void updateAgentExpensePayment(){//UPDATE AGENT EXPENSES IN AGENT EXPENSES FRAME
          try{
           String sql = "UPDATE expenseagent SET ex_ono=?,ex_rate=?,ex_tot=?,ex_pmethod=?,ex_pdate=?,ex_status=? WHERE aex_id =?";
           pst = con.prepareStatement(sql);
           
           pst.setInt(1,code);
           pst.setString(2,text_AEXrate.getText());
           pst.setString(3,text_AEXtot.getText());
           pst.setString(4,Combobox_pmethod2.getSelectedItem().toString());
           pst.setString(5,today);
           pst.setString(6,"Paid");
           pst.setString(7,aex_id);
           pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Agent Expense Paid Successfully");
            updateExpensePaymentAgent();
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
public void updateExpensePaymentAgent(){//UPDATE EXPENSES IN AGENT EXPENSES FRAME
          try{
           String sql = "UPDATE expense SET ex_ono=?,ex_tot=?,ex_pmethod=?,ex_pdate=?,ex_status=? WHERE ex_id =?";
           pst = con.prepareStatement(sql);
           
           pst.setInt(1,code);
           pst.setString(2,text_AEXtot.getText());
           pst.setString(3,Combobox_pmethod2.getSelectedItem().toString());
           pst.setString(4,today);
           pst.setString(5,"Paid");
           pst.setString(6,text_AEXexid.getText());
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Expense Updated Successfully");

            jInternalFrameAgentExpenses.setVisible(false);
            jInternalFrameInvoice.setVisible(true);
            loadInvoiceAgentExpences();

           clearAgentExpense();
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
private void fetchAllClientOrders(){//FETCH CLIENT ORDERS IN CLIENT ORDERS FRAME
        
        try{String query = "SELECT * FROM cliorder WHERE c_oid !='0' ";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_operationClientOrders.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchAllSupplierOrders(){//FETCH SUPPLIER ORDERS IN SUPPLIER'S ORDERS FRAME
        
        try{String query = "SELECT * FROM `suporder` where s_ostatus=?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Processing");
           rs=pst.executeQuery();
           tbl_SupplierOrders.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchSupplierExpenses(){//FETCH APPROVED SUPPLIER ORDERS IN SUPPLIER'S EXPENSES FRAME
        
        try{String query = "SELECT * FROM `expensesupplier` where ex_status=?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Approved");
           rs=pst.executeQuery();
           tbl_SupplierExpenses.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchClickSupplierOrder(){//FETCH CLICK APPROVED SUPPLIER ORDERS IN SUPPLIER ORDERS FRAME
       int row = tbl_SupplierOrders.getSelectedRow();
       String tc =tbl_SupplierOrders.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `suporder` where s_ostatus=? and s_oid=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,"Processing");
           pst.setString(2,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                sup_oid=rs.getString("s_oid");
                sup_gno=rs.getInt("g_no");
                sup_id=rs.getString("s_no");
                String sup_odate=rs.getString("s_odate");
                String sup_tot=rs.getString("s_ocost");
               
                 textSO_soid.setText(sup_oid);
                 textSO_soDate.setText(sup_odate);
                 textSO_soStatus.setText("Processing");
                 textSO_sotot.setText(sup_tot);
                 getSupplierData();
                 getSupplierOrderGemData();
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
private void fetchClickSupplierExpense(){//FETCH CLICK APPROVED SUPPLIER ORDERS IN SUPPLIER'S EXPENSES FRAME
       int row = tbl_SupplierExpenses.getSelectedRow();
       String tc =tbl_SupplierExpenses.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `expensesupplier` where ex_status=? and exs_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,"Approved");
           pst.setString(2,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                sup_oid=rs.getString("ex_sono");
                exss_id=rs.getInt("ex_no");
                sup_id=rs.getString("ex_sno");
                exs_id=rs.getString("exs_id");
                String sup_tot=rs.getString("ex_tot");
               
                 textSO_soid1.setText(sup_oid);
                 textSO_soexid1.setText(""+exss_id);
                 textSO_soSexid1.setText(exs_id);
                 textSO_sotot1.setText(sup_tot);
                 getSupplierDataExpense();
                 getSupplierOrderDataExpense();             
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
private void getSupplierDataExpense(){//GET SUPPLIER DATA IN SUPPLIER EXPENSES FRAME
try{
           String query = "SELECT * FROM `supplier` where s_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,sup_id);
           rs =pst.executeQuery();
           if(rs.next()){
                sup_spano=rs.getString("a_no");
                String s_fname=rs.getString("s_fname");
                String s_lname=rs.getString("s_lname");
                String s_email=rs.getString("s_email");
                String s_phone=rs.getString("s_phone");
               
                 textSO_ano1.setText(sup_spano);
                 textSO_sno1.setText(sup_id);
                 textSO_sfname1.setText(s_fname);
                 textSO_slname1.setText(s_lname);
                 textSO_semail1.setText(s_email);
                 textSO_sphone1.setText(s_phone);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
}
private void getSupplierOrderDataExpense(){//GET SUPPLIER ORDERS DATA IN SUPPLIER'S EXPENSES FRAME
try{
           String query = "SELECT * FROM `suporder` where s_oid=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,sup_oid);
           rs =pst.executeQuery();
           if(rs.next()){
                sup_gno=rs.getInt("g_no");
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
}
private void getSupplierData(){//GET SUPPLIER DATA IN SUPPLIER ORDERS FRAME
try{
           String query = "SELECT * FROM `supplier` where s_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,sup_id);
           rs =pst.executeQuery();
           if(rs.next()){
                sup_spano=rs.getString("a_no");
                String s_fname=rs.getString("s_fname");
                String s_lname=rs.getString("s_lname");
                String s_email=rs.getString("s_email");
                String s_phone=rs.getString("s_phone");
               
                 textSO_ano.setText(sup_spano);
                 textSO_sno.setText(sup_id);
                 textSO_sfname.setText(s_fname);
                 textSO_slname.setText(s_lname);
                 textSO_semail.setText(s_email);
                 textSO_sphone.setText(s_phone);

                
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
}
private void getSupplierOrderGemData(){//GET GEM DATA IN SUPPLIER ORDERS FRAME
       try{
           String query = "SELECT * FROM `gem` where g_id=? "; 
           pst = con.prepareStatement(query);
           pst.setInt(1,sup_gno);
           rs =pst.executeQuery();
           if(rs.next()){
                String g_name=rs.getString("g_name");
                String g_type=rs.getString("g_type");
                String g_color=rs.getString("g_color");
                String g_weight=rs.getString("g_weight");


                 byte[] img = rs.getBytes("g_image");
                 ImageIcon image = new ImageIcon(img);
                 Image im = image.getImage();
                 Image myImg = im.getScaledInstance(lbl_gemImage.getWidth(), lbl_gemImage.getHeight(),Image.SCALE_DEFAULT);
                 ImageIcon newImage =new ImageIcon(myImg);
                 textSO_GemImage.setIcon(newImage);
                 

                 textSO_Gname.setText(g_name);
                 textSO_Gtype.setText(g_type);
                 textSO_Gcolor.setText(g_color);
                 textSO_Gweight.setText(g_weight);
                 
               
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
      
    }
private void fetchClickAvailablerawGems(){//FETCH CLICK AVAILABLE RAW GEMS IN GEM POLISHER JOBS FRAME
       int row = tbl_availaleRGems.getSelectedRow();
       String tc =tbl_availaleRGems.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM raw where rg_status =? and rg_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,"Finished");
           pst.setString(2,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                rrg_id=rs.getString("rg_id");
                gg_no=rs.getInt("g_no");;
                String rg_name=rs.getString("rg_name");
                String rg_type=rs.getString("rg_type");
                String rg_color=rs.getString("rg_color");
                String rg_weight=rs.getString("rg_weight");
                String rg_origin=rs.getString("rg_orgin"); 
                String rg_nature=rs.getString("rg_nature");

                 txt_gname.setText(rg_name);
                 txt_gtype.setText(rg_type);
                 txt_gcolor.setText(rg_color);
                 txt_gweight.setText(rg_weight);
                 txt_gOrigin.setText(rg_origin);
                 txt_gnature.setText(rg_nature);

           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
private void fetchClickJobGemPolisher(){//FETCH CLICK GEMPOLISHERS IN GEM POLISHER JOBS FRAME
       int row = tbl_jGempolishers.getSelectedRow();
       String tc =tbl_jGempolishers.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `gempolisher` where e_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                ee_id=rs.getString("e_id");
                uu_no=rs.getInt("u_no");
                String gp_fname=rs.getString("e_fname");
                String gp_lname=rs.getString("e_lname");
                String gp_phone=rs.getString("e_phone");
                String gp_email=rs.getString("e_email");
                
                 text_ugemail.setText(gp_email);
                 text_ugfname.setText(gp_fname);
                 text_uglname.setText(gp_lname);
                 text_ugphone.setText(gp_phone);
                 
                 

           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
private void fetchClickJobGemPolisherEx(){//FETCH CLICK GEM POLISHERS IN GEM POLISHER EXPENSE FRAME
       int row = tbl_EXGempolishers.getSelectedRow();
       String tc =tbl_EXGempolishers.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `gempolisher` where e_id=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                ee_id=rs.getString("e_id");
                uu_no=rs.getInt("u_no");
                String gp_fname=rs.getString("e_fname");
                String gp_lname=rs.getString("e_lname");
                String gp_phone=rs.getString("e_phone");
                String gp_email=rs.getString("e_email");
                String gp_fsalary=rs.getString("e_salary");
                 text_exGPid.setText(ee_id);
                 text_exGPemail.setText(gp_email);
                 text_exGPfname.setText(gp_fname);
                 text_exGPlname.setText(gp_lname);
                 text_exGPphone.setText(gp_phone);
                 text_exGPfsalary.setText(gp_fsalary);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
private void fetchJobsGemPolishers(){//FETCH GEM POLISHER TABLE IN GEM POLISHER JOBS FRAME
  
        try{String query = "SELECT * FROM gempolisher ";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_jGempolishers.setModel(DbUtils.resultSetToTableModel(rs));              
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchJobsGemPolishersEx(){//FETCH GEM POLISHER TABLE IN GEM POLISHER EXPENSES FRAME
  
        try{String query = "SELECT * FROM gempolisher ";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_EXGempolishers.setModel(DbUtils.resultSetToTableModel(rs));              
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchJobsGemPolishersExpenses(){//FETCH GEM POLISHER EXPENSES TABLE IN GEM POLISHER EXPENSES FRAME
  
        try{String query = "SELECT * FROM expensegempolisher ";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_EXGempolisherExpenses.setModel(DbUtils.resultSetToTableModel(rs));              
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchClientOrders(){//FETCH GEM CLIENT ORDERS TABLE IN CLIENT ORDERS FRAME
        try{String query = "SELECT * FROM cliorder";
           pst = con.prepareStatement(query);
           rs=pst.executeQuery();
           tbl_operationClientOrders.setModel(DbUtils.resultSetToTableModel(rs));              
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
    private void setJobData(){//SET JOB DATA IN GEM POLISHER JOBS FRAME
     
     ee_name =text_ugfname.getText();
     gg_name =txt_gname.getText();
     validateJobUpdate();                     
    }
    private void validateJobUpdate(){//VALIDATE JOB DATA IN GEM POLISHER JOBS FRAME
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     if(ee_name.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select Gem Polisher");
        }else if(gg_name.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Select A Gem");
        }else{
                      if (jDateChooser_JEndDate.getDate() == null) {
                           JOptionPane.showMessageDialog(null, "Please Enter Expected Job End Date", "Warning!", JOptionPane.ERROR_MESSAGE);
                        }else{
                         jj_exEndDate= sdf.format(jDateChooser_JEndDate.getDate());   
                        Job main = new Job();
                        main.ownerSetJob(ee_id, rrg_id, code, "Assigned", today, jj_exEndDate);
                        main.inputJob();
                        updateGemStatus();
                        updateRawGemStatus();
                        clearJobData();
                        fetchClickAvailablerawGems();
                       }    
        }
}
public void updateGemStatus(){//UPDATE GEM STATUS IN GEM POLISHER JOBS FRAME
          try{
           String sql = "UPDATE gem SET g_status=? WHERE g_id =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,"JobAssigned");
           pst.setInt(2,gg_no);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Gem Status Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
 public void updateRawGemStatus(){//UPDATE FINISHED GEM STATUS IN GEM POLISHER JOBS FRAME
          try{
           String sql = "UPDATE raw SET rg_status=? WHERE rg_id =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,"JobAssigned");
           pst.setString(2,rrg_id);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Raw Gem Status Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
private void updateSupplierOrderStatus(String status){//UPDATE SUPPLIER ORDER STATUS IN SUPPLIER ORDERS FRAME
          try{
           String sql = "UPDATE suporder SET s_ostatus=? WHERE s_oid =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,status);
           pst.setString(2,sup_oid);
           pst.executeUpdate();
           JOptionPane.showMessageDialog(null, "Supplier Order "+status+" Successfully");
           clearSupplierOrder();
           updateGemSupplierOrderStatus(status);
          
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
private void updateGemSupplierOrderStatus(String status){//UPDATE GEM STATUS IN SUPPLIER ORDERS FRAME
          try{
           String sql = "UPDATE gem SET g_status=? WHERE g_id =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,status);
           pst.setInt(2,sup_gno);
           pst.executeUpdate();
           //JOptionPane.showMessageDialog(null, "Gem Status Updated Successfully");
           updateRawGemSupplierOrderStatus(status);
           updateSupplierExpense(status);
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
private void updateRawGemSupplierOrderStatus(String status){//UPDATE RAW GEM STATUS IN SUPPLIER ORDERS FRAME
          try{
           String sql = "UPDATE raw SET rg_status=? WHERE g_no =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,status);
           pst.setInt(2,sup_gno);
           pst.executeUpdate();
            updatefinishedGemSupplierOrderStatus(status);
            }
                catch(SQLException | HeadlessException ex){
              
            }
    }
private void updatefinishedGemSupplierOrderStatus(String status){//UPDATE FINISHED GEM STATUS IN SUPPLIER ORDERS FRAME
          try{
           String sql = "UPDATE finish SET fg_status=? WHERE g_no =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,status);
           pst.setInt(2,sup_gno);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, " Finish Gem Status Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
private void updateSupplierExpense(String status){//UPDATE SUPPPLIER EXPENSES STATUS IN SUPPLIER ORDERS FRAME
          try{
           String sql = "UPDATE expensesupplier SET ex_status=? WHERE ex_sono =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,status);
           pst.setString(2,sup_oid);
           pst.executeUpdate();
           updateExpense(status);
           // JOptionPane.showMessageDialog(null, "Supplier Expense Status Updated Successfully");
          
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
private void updateExpense(String status){//UPDATE  EXPENSES STATUS IN SUPPLIER ORDERS FRAME
          try{
           String sql = "UPDATE expense SET ex_status=? WHERE ex_id =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,status);
           pst.setString(2,sup_oid);
           pst.executeUpdate();
           // JOptionPane.showMessageDialog(null, "Supplier Expense Status Updated Successfully");
          
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }

private void updateSupplierExpensePayment(){//UPDATE SUPPPLIER EXPENSES PAYMENT DATA IN SUPPLIER EXPENSES FRAME
          try{
           String sql = "UPDATE expensesupplier SET ex_pdate=?,ex_pmethod=?,ex_ono=? WHERE ex_no =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,today);
           pst.setString(2,pmethod);
           pst.setInt(3,code);
           pst.setInt(4,exss_id);
           pst.executeUpdate();
           updateExpensePayment();
           // JOptionPane.showMessageDialog(null, "Supplier Expense Status Updated Successfully");
          
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
private void updateExpensePayment(){//UPDATE EXPENSES PAYMENT DATA 
          try{
           String sql = "UPDATE expense SET ex_pdate=?,ex_pmethod=?,ex_ono=?,ex_status=? WHERE ex_id =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,today);
           pst.setString(2,pmethod);
           pst.setInt(3,code);
           pst.setString(4,"Paid");
           pst.setInt(5,exss_id);

           pst.executeUpdate();

               jInternalFrameSupplierExpenses.setVisible(false);
               jInternalFrameInvoice.setVisible(true);
               loadInvoiceSupplierExpences();

           // JOptionPane.showMessageDialog(null, "Expense Status Updated Successfully");
          
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }
private void updateAgentExpense(String status){//UPDATE AGENT EXPENSES PAYMENT DATA IN SUPPLIER EXPENSES FRAME
          try{
           String sql = "UPDATE expenseagent SET ex_status=? WHERE ex_sono =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,status);
           pst.setString(2,sup_oid);
           pst.executeUpdate();
           // JOptionPane.showMessageDialog(null, "Agent Expense Status Updated Successfully");
          
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
    }

private void fetchClickClientOrders(){//FETCH CLIENT ORDERS DATA IN CLIENT ORDERS FRAME
       int row = tbl_operationClientOrders.getSelectedRow();
       String tc =tbl_operationClientOrders.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `cliorder` where c_oid=?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                int g_no=rs.getInt("g_no");;
                 txt_gid.setText(""+g_no);
                ggg_no =  Integer.parseInt(txt_gid.getText());
                fetchSearchOrderClick();
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

     private void fetchSearchOrderClick(){//FETCH FINISH GEMS DATA IN CLIENT ORDERS FRAME
       try{
           String query = "SELECT * FROM `finish` where g_no=? "; 
           pst = con.prepareStatement(query);
           pst.setInt(1,ggg_no);
           rs =pst.executeQuery();
           if(rs.next()){
                String fg_id=rs.getString("fg_id");
                int g_no=rs.getInt("g_no");;
                String fg_name=rs.getString("fg_name");
                String fg_type=rs.getString("fg_type");
                String fg_color=rs.getString("fg_color");
                String fg_weight=rs.getString("fg_weight");
                String fg_sprice=rs.getString("fg_sprice");
                String fg_cid=rs.getString("fg_cid");
                String fg_shape=rs.getString("fg_shape");
                String fg_pieces=rs.getString("fg_pieces");

                 byte[] img = rs.getBytes("fg_image");
                 ImageIcon image = new ImageIcon(img);
                 Image im = image.getImage();
                 Image myImg = im.getScaledInstance(lbl_gemImage.getWidth(), lbl_gemImage.getHeight(),Image.SCALE_DEFAULT);
                 ImageIcon newImage =new ImageIcon(myImg);
                 ImageIcon newImage1 =new ImageIcon(myImg);
                 lbl_gemImage.setIcon(newImage);
                 

                 txt_gname1.setText(fg_name);
                 txt_gtype1.setText(fg_type);
                 txt_gcolor1.setText(fg_color);
                 txt_gweight1.setText(fg_weight);
                 txt_gsprice1.setText(fg_sprice);
                 txt_cid1.setText(fg_cid);
                 txt_gshape1.setText(fg_shape);
                 txt_gpieces1.setText(fg_pieces);
               
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
      
    }
private void validateGempolisherEx(){//VALIDATE GEM POLISHER SALARY IN GEM POLISHER EXPENSES
if (text_exGPid.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Please select a Gem Polisher First!");
        }  else if(text_exGPbonus.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Please Enter Bonus Amount!");
        }else if(text_exGPtot.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please click Calculate the Total Salary!");
        }else if((Combobox_pmethod.getSelectedItem().toString()).equals("Select")){
            JOptionPane.showMessageDialog(this,"Please Select payment method!");
        }else
        {
            Expense main = new Expense(String.valueOf(ex_id), String.valueOf(code),text_exGPtot.getText(), Combobox_pmethod.getSelectedItem().toString(), today, "Paid","GPolisherExpense");
            main.input();
            ExpenseGemPolisher maine =new ExpenseGemPolisher(String.valueOf(ex_id), String.valueOf(code),text_exGPtot.getText(), Combobox_pmethod.getSelectedItem().toString(), today, "Paid","GPolisherExpense", gex_id, text_exGPfsalary.getText(), text_exGPbonus.getText(),text_exGPid.getText());
            maine.input();

        jInternalFrameGemPolisherExpenses.setVisible(false);
        jInternalFrameInvoice.setVisible(true);
        loadInvoiceGempolisherExpences();

            clearGemPolisherExpenses();
           fetchJobsGemPolishersExpenses();
            
       
        }
}
private void calGemPolisherSalary(){//CALCULATE GEM POLISHER SALARY IN GEM MPOLISHER EXPENSES
            double  bonusSalary;
        if (text_exGPid.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Please select a Gem Polisher First!");
        }  else if(text_exGPbonus.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Please Enter Bonus Amount!");
        }else{
                double fixedSalary = Double.parseDouble(text_exGPfsalary.getText());                
                bonusSalary = Double.parseDouble(text_exGPbonus.getText());
                double tot = fixedSalary+bonusSalary;
                text_exGPtot.setText(""+tot);
              }
}

public void clear(){
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      text_ufname.setText("");
      text_ulname.setText("");
      text_uadd1.setText("");
      text_uadd2.setText("");
      text_uadd3.setText("");
      text_uphone.setText("");
      datechooser_udob.setDate(date);
      text_unic.setText("");
      text_uemail.setText("");
      text_upwd.setText("");
      
      text_ulno.setText("");
      text_ubacc.setText("");
}


public void clearclient(){
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      u_jdate = sdf.format(date);
      text_ufname1.setText("");
      text_ulname1.setText("");
      text_uadd4.setText("");
      text_uadd5.setText("");
      text_uadd6.setText("");
      text_uphone1.setText("");
      datechooser_udob1.setDate(date);
      text_unic1.setText("");
      text_uemail1.setText("");
      text_upwd1.setText("");
      
      comboURegion.setSelectedIndex(0);
}

public void clearAgent(){
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      text_ufname2.setText("");
      text_ulname2.setText("");
      text_uadd7.setText("");
      text_uadd8.setText("");
      text_uadd9.setText("");
      text_uphone2.setText("");
      datechooser_udob2.setDate(date);
      text_unic2.setText("");
      text_uemail2.setText("");
      text_upwd2.setText("");
      datechooser_jdate.setDate(date);
      text_ulno.setText("");
      text_ubacc1.setText("");
      cmb_worktype.setSelectedIndex(0);
}

private void clearOwner(){
                 text_ufname4.setText("");
                 text_ulname4.setText("");
                 text_uadd13.setText("");
                 text_uadd14.setText("");
                 text_uadd15.setText("");
                 text_uphone4.setText("");
                 text_uemail4.setText("");
                 text_unic4.setText("");
                 text_upwd4.setText("");
                 btnAddowner.setVisible(true);
}
    
private void clearJobData()//CLEAR FIELDS IN GEM POLISHER'S JOBS
{
      text_ugemail.setText("");
      text_ugfname.setText("");
      text_uglname.setText("");
      text_ugphone.setText("");
      txt_gOrigin.setText("");
      txt_gcolor.setText("");
      jDateChooser_JEndDate.setDate(null);
      txt_gname.setText("");
      txt_gnature.setText("");
      txt_gtype.setText("");
      txt_gweight.setText("");
}


private void clearClientOrdersGemDetails(){//CLEAR FIELDS IN CLIENT ORDERS
                 txt_gid.setText("");
                 txt_gname1.setText("");
                 txt_gtype1.setText("");
                 txt_gcolor1.setText("");
                 txt_gweight1.setText("");
                 txt_gsprice1.setText("");
                 txt_cid1.setText("");
                 txt_gshape1.setText("");
                 txt_gpieces1.setText(""); 
                 lbl_gemImage.setIcon(null);
    }
private void clearGemPolisherExpenses(){ //CLEAR FIELDS IN GEM POLISHER EXPENSES
                 text_exGPid.setText("");
                 text_exGPbonus.setText("");
                 text_exGPemail.setText("");
                 text_exGPfname.setText("");
                 text_exGPfsalary.setText("");
                 text_exGPlname.setText("");
                 text_exGPphone.setText("");
                 text_exGPtot.setText("");
                 Combobox_pmethod.setSelectedIndex(0);
                 textex_gempolisherSeach.setText("");
    }
private void clearSupplierOrder(){ //CLEAR FIELDS IN SUPPLIER ORDERS
        textSO_Gcolor.setText(""); 
        textSO_GemImage.setIcon(null);
        textSO_Gname.setText(""); 
        textSO_Gtype.setText(""); 
        textSO_Gweight.setText(""); 
        textSO_ano.setText(""); 
        textSO_semail.setText(""); 
        textSO_sfname.setText(""); 
        textSO_slname.setText(""); 
        textSO_sno.setText(""); 
        textSO_soDate.setText(""); 
        textSO_soStatus.setText(""); 
        textSO_soid.setText(""); 
        textSO_sotot.setText(""); 
        textSO_sphone.setText(""); 
}
private void clearSupplierExpense(){ //CLEAR FIELDS IN SUPPLIER EXPENSES
         
        
        textSO_ano1.setText(""); 
        textSO_semail1.setText(""); 
        textSO_sfname1.setText(""); 
        textSO_slname1.setText(""); 
        textSO_sno1.setText(""); 
        textSO_soSexid1.setText("");  
        textSO_soexid1.setText("");       
        textSO_soid1.setText(""); 
        textSO_sotot1.setText(""); 
        textSO_sphone1.setText(""); 
        Combobox_pmethod1.setSelectedIndex(0);
}
private void clearAgentExpense(){//CLEAR FIELDS IN AGENT EXPENSES
         
        
        text_AEXaexid.setText(""); 
        text_AEXaid.setText(""); 
        text_AEXcoid.setText(""); 
        text_AEXemail.setText(""); 
        text_AEXexid.setText(""); 
        text_AEXfname.setText("");  
        text_AEXlname.setText("");       
        text_AEXoamount.setText(""); 
        text_AEXphone.setText(""); 
        text_AEXrate.setText(""); 
        text_AEXsoid.setText(""); 
        text_AEXtot.setText("");
        Combobox_pmethod2.setSelectedIndex(0);
}
private void clearRawGemManage(){//CLEAR FIELDS IN RAW GEM MANAGEMENT
        txt_rg_name.setText(""); 
        lbl_rawgemimage.setIcon(null);
        txt_rg_Status.setText(""); 
        txt_rg_buyPrice.setText(""); 
        txt_rg_color.setText(""); 
        txt_rg_nature.setText(""); 
        txt_rg_sellPrice.setText(""); 
        txt_rg_origin.setText(""); 
        txt_rg_type.setText(""); 
        txt_rg_weight.setText(""); 
        btn_addRawGem.setVisible(true);
}
private void clearFinishedGemManage(){//CLEAR FIELDS IN FINISHED GEM MANAGEMENT
        txt_fg_name.setText(""); 
        lbl_finishedgemimage.setIcon(null);
        txt_fg_LicenseID.setText(""); 
        txt_fg_color.setText(""); 
        txt_fg_buyprice.setText(""); 
        txt_fg_pieces.setText(""); 
        txt_fg_sellprice.setText(""); 
        txt_fg_shape.setText(""); 
        txt_fg_status.setText(""); 
        txt_fg_type.setText(""); 
        txt_fg_weight.setText("");
       btn_addFinishedGem.setVisible(true);
}


private  void clearGempolisher(){
                  Date date = new Date();
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                  text_ufname3.setText("");
                 text_ulname3.setText("");
                 datechooser_udob3.setDate(date);
                 text_unic3.setText("");
                 text_uemail3.setText("");
                 text_uadd10.setText("");
                 text_uadd11.setText("");
                 text_uadd12.setText("");
                 text_uphone3.setText("");
                 text_upwd3.setText("");
                 text_salary.setText("");
                 text_ubacc2.setText("");
                 datechooser_jdate2.setDate(date);
                 cmb_worktype2.setSelectedIndex(0);

}



public void loadIncomereport() {
        HashMap a = new HashMap();
        a.put("oid", ""+code);
        a.put("date", today);
        
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\TotalIncome.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad.setLayout(new BorderLayout());
              panelLoad.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}

public void loadFilteredIncomereport() {
       SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
       String fdate=date1.format(datechooser_from.getDate());
       SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd");
       String tdate=date2.format(datechooser_to.getDate());


        HashMap a = new HashMap();
        a.put("oid", ""+code);
        a.put("date", today);
        a.put("from", fdate);
        a.put("to", tdate);

        panelLoad1.removeAll();
        panelLoad1.repaint();
        panelLoad1.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\FilterIncome.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad1.setLayout(new BorderLayout());
              panelLoad1.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}

public void loadExpencereport() {
        HashMap a = new HashMap();
        a.put("oid", ""+code);
        a.put("date", today);
        a.put("status", status);
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\AllExpences.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad.setLayout(new BorderLayout());
              panelLoad.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}

public void loadFilteredExpencesreport() {
       SimpleDateFormat date3 = new SimpleDateFormat("yyyy-MM-dd");
       String frdate=date3.format(datechooser_from.getDate());
       SimpleDateFormat date4 = new SimpleDateFormat("yyyy-MM-dd");
       String todate=date4.format(datechooser_to.getDate());


        HashMap a = new HashMap();
        a.put("oid", ""+code);
        a.put("date", today);
        a.put("from", frdate);
        a.put("to", todate);
        a.put("status", status);
        panelLoad1.removeAll();
        panelLoad1.repaint();
        panelLoad1.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\FilterExpences.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad1.setLayout(new BorderLayout());
              panelLoad1.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}

public void loadFilteredSupplierExpencesreport() {
       SimpleDateFormat date3 = new SimpleDateFormat("yyyy-MM-dd");
       String frdate=date3.format(datechooser_from.getDate());
       SimpleDateFormat date4 = new SimpleDateFormat("yyyy-MM-dd");
       String todate=date4.format(datechooser_to.getDate());


        HashMap a = new HashMap();
        a.put("oid", ""+code);
        a.put("date", today);
        a.put("from", frdate);
        a.put("to", todate);
        a.put("status", status);
        panelLoad1.removeAll();
        panelLoad1.repaint();
        panelLoad1.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\totalsupplierExpenses.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad1.setLayout(new BorderLayout());
              panelLoad1.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}

public void loadFilteredAgentExpencesreport() {
       SimpleDateFormat date3 = new SimpleDateFormat("yyyy-MM-dd");
       String frdate=date3.format(datechooser_from.getDate());
       SimpleDateFormat date4 = new SimpleDateFormat("yyyy-MM-dd");
       String todate=date4.format(datechooser_to.getDate());


        HashMap a = new HashMap();
        a.put("oid", ""+code);
        a.put("date", today);
        a.put("from", frdate);
        a.put("to", todate);
        a.put("status", status);
        panelLoad1.removeAll();
        panelLoad1.repaint();
        panelLoad1.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\totalAgentExpenses.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad1.setLayout(new BorderLayout());
              panelLoad1.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}

public void loadFilteredGempolisherExpencesreport() {
       SimpleDateFormat date3 = new SimpleDateFormat("yyyy-MM-dd");
       String frdate=date3.format(datechooser_from.getDate());
       SimpleDateFormat date4 = new SimpleDateFormat("yyyy-MM-dd");
       String todate=date4.format(datechooser_to.getDate());


        HashMap a = new HashMap();
        a.put("oid", ""+code);
        a.put("date", today);
        a.put("from", frdate);
        a.put("to", todate);
        a.put("status", status);
        panelLoad1.removeAll();
        panelLoad1.repaint();
        panelLoad1.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\totalGempolisherExpenses.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad1.setLayout(new BorderLayout());
              panelLoad1.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}

public void loadInvoiceSupplierExpences() {


        HashMap a = new HashMap();
        a.put("SRAID",invoice_s_sran);
        a.put("SID", invoice_s_sid);
        a.put("Fname", invoice_s_fname);
        a.put("Lname", invoice_s_lname);
        a.put("Email", invoice_s_email);
        a.put("contact", invoice_s_phone);
        a.put("Oid",invoice_s_oid);
        a.put("EID",invoice_s_eid );
        a.put("SEID", invoice_s_seid);
        a.put("Ramount", invoice_s_amount);
        a.put("date",today);
      
        panelLoad2.removeAll();
        panelLoad2.repaint();
        panelLoad2.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\InvoiceSupplierExpences.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad2.setLayout(new BorderLayout());
              panelLoad2.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}

public void loadInvoiceGempolisherExpences() {


        HashMap a = new HashMap();
        a.put("gpid",invoice_g_gpid);
        a.put("fname", invoice_g_fname);
        a.put("lname", invoice_g_lname);
        a.put("email", invoice_g_email);
        a.put("contact", invoice_g_phone);
        a.put("fsalary",invoice_g_fsalary);
        a.put("bonus",invoice_g_bonus );
        a.put("total", invoice_g_total);
        a.put("date",today);
      
        panelLoad2.removeAll();
        panelLoad2.repaint();
        panelLoad2.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\InvoiceGemPolisherExpences.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad2.setLayout(new BorderLayout());
              panelLoad2.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}

public void loadInvoiceAgentExpences() {


        HashMap a = new HashMap();
        a.put("aid",invoice_a_aid);
        a.put("fname", invoice_a_fname);
        a.put("lname", invoice_a_lname);
        a.put("email", invoice_a_email);
        a.put("contact",  invoice_a_phone);
        a.put("aeid", invoice_a_aeid);
        a.put("eid",invoice_a_eid );
        a.put("soid", invoice_a_soid);
        a.put("coid", invoice_a_coid);
        a.put("oamount",invoice_a_oamount);
        a.put("crate", invoice_a_crate);
        a.put("camount",invoice_a_camount);
        a.put("date", today);


        panelLoad2.removeAll();
        panelLoad2.repaint();
        panelLoad2.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\InvoiceAgentExpences.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad2.setLayout(new BorderLayout());
              panelLoad2.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        btnManageGems = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblBar3 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        buttonHome = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblBar1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnManageUsers = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblBar2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnOrderDetails = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblBar4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnReports = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblBar5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnHelp = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        lblBar6 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnLogOut = new javax.swing.JLabel();
        btnProfile = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        btnExit = new javax.swing.JLabel();
        btnMiniMize = new javax.swing.JLabel();
        lblUserName1 = new javax.swing.JLabel();
        form1 = new javax.swing.JPanel();
        jInternalFrameHome = new javax.swing.JInternalFrame();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jInternalFrameManageUsers = new javax.swing.JInternalFrame();
        btn_ManageSuppliers = new javax.swing.JPanel();
        lblBar18 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        btn_ManageClients = new javax.swing.JPanel();
        lblBar19 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        btn_ManageAgents = new javax.swing.JPanel();
        lblBar20 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        btn_GemPolishers = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        btn_ManageOwners = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jInternalFrameManageGems = new javax.swing.JInternalFrame();
        btn_ManageRawGems = new javax.swing.JPanel();
        lblBar16 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        btn_ManageFinishedGems = new javax.swing.JPanel();
        lblBar17 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jInternalFrameOrderDetails = new javax.swing.JInternalFrame();
        label_orders = new javax.swing.JPanel();
        lblBar7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btn_gemPolisherExpenses = new javax.swing.JPanel();
        lblBar8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        label_expenses = new javax.swing.JPanel();
        lblBar9 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        label_jobs = new javax.swing.JPanel();
        lblBar10 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btn_ClientOrders = new javax.swing.JPanel();
        lblBar11 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        btn_AgentExpenses = new javax.swing.JPanel();
        lblBar12 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        btn_SuppilerOrders = new javax.swing.JPanel();
        lblBar13 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btn_SupplierExpenses = new javax.swing.JPanel();
        lblBar14 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        btn_GempolisherWork = new javax.swing.JPanel();
        lblBar15 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jInternalFrameReports = new javax.swing.JInternalFrame();
        btnOwnerExpencesReport = new View.MyButton();
        btnOwnerIncomeReport = new View.MyButton();
        panelLoad = new javax.swing.JPanel();
        btnfilter = new View.MyButton();
        jInternalFrameOwnerrHelp = new javax.swing.JInternalFrame();
        jLabel86 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        btnMap = new View.MyButton();
        jLabel75 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jInternalFrameProfile = new javax.swing.JInternalFrame();
        jInternalFrameJobs = new javax.swing.JInternalFrame();
        btn_back = new View.MyButton();
        lbl_rawfinishedGems = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_availaleRGems = new javax.swing.JTable();
        lbl_gname1 = new javax.swing.JLabel();
        txt_gname = new javax.swing.JTextField();
        txt_gtype = new javax.swing.JTextField();
        lbl_gtype = new javax.swing.JLabel();
        lbl_gcolor = new javax.swing.JLabel();
        txt_gcolor = new javax.swing.JTextField();
        txt_gweight = new javax.swing.JTextField();
        lbl_gweight = new javax.swing.JLabel();
        lbl_weight = new javax.swing.JLabel();
        lbl_gname2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_jGempolishers = new javax.swing.JTable();
        lbl_gOrigin = new javax.swing.JLabel();
        txt_gOrigin = new javax.swing.JTextField();
        txt_gnature = new javax.swing.JTextField();
        lbl_gemNature = new javax.swing.JLabel();
        label_ufname = new javax.swing.JLabel();
        text_ugfname = new javax.swing.JTextField();
        label_ulname = new javax.swing.JLabel();
        text_uglname = new javax.swing.JTextField();
        label_uemail = new javax.swing.JLabel();
        label_uphone = new javax.swing.JLabel();
        text_ugphone = new javax.swing.JTextField();
        text_ugemail = new javax.swing.JTextField();
        btn_showGJobs = new View.MyButton();
        myButton2 = new View.MyButton();
        btn_gjobClear = new View.MyButton();
        jDateChooser_JEndDate = new com.toedter.calendar.JDateChooser();
        lbl_jobenddate = new javax.swing.JLabel();
        jInternalFrameSupplierOrders = new javax.swing.JInternalFrame();
        btn_back1 = new View.MyButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_SupplierOrders = new javax.swing.JTable();
        lbl_supGemInfoTitle = new javax.swing.JLabel();
        label_supOrderAgentNo = new javax.swing.JLabel();
        label_supOrderSuplname = new javax.swing.JLabel();
        textSO_ano = new javax.swing.JTextField();
        label_supOrderSupEmail = new javax.swing.JLabel();
        label_supOrderSupPhone = new javax.swing.JLabel();
        label_supOrderSupID = new javax.swing.JLabel();
        textSO_sno = new javax.swing.JTextField();
        label_supOrderSupfanme = new javax.swing.JLabel();
        textSO_slname = new javax.swing.JTextField();
        textSO_sotot = new javax.swing.JTextField();
        textSO_semail = new javax.swing.JTextField();
        textSO_sfname = new javax.swing.JTextField();
        lbl_supplersOrderTitle = new javax.swing.JLabel();
        lbl_supOrderInfotitle = new javax.swing.JLabel();
        label_supOrderGname = new javax.swing.JLabel();
        textSO_Gname = new javax.swing.JTextField();
        textSO_Gtype = new javax.swing.JTextField();
        textSO_Gcolor = new javax.swing.JTextField();
        textSO_Gweight = new javax.swing.JTextField();
        label_supOrderImage = new javax.swing.JLabel();
        label_supOrderGweight = new javax.swing.JLabel();
        label_supOrderGcolor = new javax.swing.JLabel();
        label_supOrderGtype = new javax.swing.JLabel();
        lbl_weight2 = new javax.swing.JLabel();
        lbl_supOrderInfoTitle = new javax.swing.JLabel();
        label_supOrderID = new javax.swing.JLabel();
        label_supOrderDate = new javax.swing.JLabel();
        label_supOrderStatus = new javax.swing.JLabel();
        label_supOrderValue = new javax.swing.JLabel();
        textSO_sphone = new javax.swing.JTextField();
        textSO_soid = new javax.swing.JTextField();
        textSO_soDate = new javax.swing.JTextField();
        textSO_soStatus = new javax.swing.JTextField();
        textSO_GemImage = new javax.swing.JLabel();
        btn_RejectOrder = new View.MyButton();
        btn_SupOrderClear = new View.MyButton();
        btn_ApproveOrder = new View.MyButton();
        jInternalFrameClientOrders = new javax.swing.JInternalFrame();
        btn_back2 = new View.MyButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_operationClientOrders = new javax.swing.JTable();
        lbl_gname5 = new javax.swing.JLabel();
        txt_gname1 = new javax.swing.JTextField();
        lbl_gname = new javax.swing.JLabel();
        lbl_gtype1 = new javax.swing.JLabel();
        txt_gtype1 = new javax.swing.JTextField();
        txt_gcolor1 = new javax.swing.JTextField();
        lbl_gcolor1 = new javax.swing.JLabel();
        lbl_gweight1 = new javax.swing.JLabel();
        lbl_weight1 = new javax.swing.JLabel();
        txt_gweight1 = new javax.swing.JTextField();
        lbl_gpieces = new javax.swing.JLabel();
        lbl_certiID1 = new javax.swing.JLabel();
        lbl_gcid = new javax.swing.JLabel();
        lbl_gshape = new javax.swing.JLabel();
        lbl_gsprice = new javax.swing.JLabel();
        txt_gsprice1 = new javax.swing.JTextField();
        txt_gshape1 = new javax.swing.JTextField();
        txt_cid1 = new javax.swing.JTextField();
        txt_gpieces1 = new javax.swing.JTextField();
        lbl_gemImage = new javax.swing.JLabel();
        lbl_searchGemName = new javax.swing.JLabel();
        txt_gid = new javax.swing.JTextField();
        btn_reset = new View.MyButton();
        btn_search = new View.MyButton();
        jInternalFrameSupplierExpenses = new javax.swing.JInternalFrame();
        btn_back3 = new View.MyButton();
        lbl_supplersExpensesTitle = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_SupplierExpenses = new javax.swing.JTable();
        lbl_supOrderInfoTitle1 = new javax.swing.JLabel();
        label_supOrderAgentNo1 = new javax.swing.JLabel();
        label_supOrderSupID1 = new javax.swing.JLabel();
        label_supOrderSupfanme1 = new javax.swing.JLabel();
        label_supOrderSuplname1 = new javax.swing.JLabel();
        label_supOrderSupEmail1 = new javax.swing.JLabel();
        label_supOrderSupPhone1 = new javax.swing.JLabel();
        textSO_sphone1 = new javax.swing.JTextField();
        textSO_semail1 = new javax.swing.JTextField();
        textSO_slname1 = new javax.swing.JTextField();
        textSO_sfname1 = new javax.swing.JTextField();
        textSO_sno1 = new javax.swing.JTextField();
        textSO_ano1 = new javax.swing.JTextField();
        lbl_supOrderInfotitle1 = new javax.swing.JLabel();
        label_supOrderID1 = new javax.swing.JLabel();
        label_supOrderDate1 = new javax.swing.JLabel();
        label_supOrderStatus1 = new javax.swing.JLabel();
        label_supOrderValue1 = new javax.swing.JLabel();
        textSO_sotot1 = new javax.swing.JTextField();
        textSO_soSexid1 = new javax.swing.JTextField();
        textSO_soexid1 = new javax.swing.JTextField();
        textSO_soid1 = new javax.swing.JTextField();
        btn_SupOrderClear1 = new View.MyButton();
        btn_payGPex1 = new View.MyButton();
        Combobox_pmethod1 = new javax.swing.JComboBox<>();
        label_exGPpmethod1 = new javax.swing.JLabel();
        jInternalFrameGemPolisherExpenses = new javax.swing.JInternalFrame();
        btn_back4 = new View.MyButton();
        lbl_gname3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_EXGempolishers = new javax.swing.JTable();
        text_exGPfname = new javax.swing.JTextField();
        text_exGPlname = new javax.swing.JTextField();
        text_exGPemail = new javax.swing.JTextField();
        text_exGPphone = new javax.swing.JTextField();
        label_exGPphone = new javax.swing.JLabel();
        label_exGPemail = new javax.swing.JLabel();
        label_exGPlname = new javax.swing.JLabel();
        label_exGPfname = new javax.swing.JLabel();
        lbl_gname4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_EXGempolisherExpenses = new javax.swing.JTable();
        label_exGempolisherID = new javax.swing.JLabel();
        text_exGPid = new javax.swing.JTextField();
        label_exGPfixedSalary = new javax.swing.JLabel();
        text_exGPfsalary = new javax.swing.JTextField();
        text_exGPbonus = new javax.swing.JTextField();
        label_exGPbonus = new javax.swing.JLabel();
        label_exGPpmethod = new javax.swing.JLabel();
        text_exGPtot = new javax.swing.JTextField();
        btn_cleanGPex = new View.MyButton();
        btn_exGPsalaryCal = new View.MyButton();
        label_exGPtot = new javax.swing.JLabel();
        Combobox_pmethod = new javax.swing.JComboBox<>();
        btn_payGPex = new View.MyButton();
        textex_gempolisherSeach = new javax.swing.JTextField();
        label_serachgemplosher2 = new javax.swing.JLabel();
        btn_searchGempolsherEX = new View.MyButton();
        btn_resetGemPolsiherEX = new View.MyButton();
        label_serachgemplosher1 = new javax.swing.JLabel();
        jInternalFrameAgentExpenses = new javax.swing.JInternalFrame();
        btn_back5 = new View.MyButton();
        label_AEX = new javax.swing.JLabel();
        text_AEXaid = new javax.swing.JTextField();
        lbl_AEXsubtitle = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbl_AgentExpenses = new javax.swing.JTable();
        lbl_AgentExpensesTitle = new javax.swing.JLabel();
        label_AEX1 = new javax.swing.JLabel();
        text_AEXfname = new javax.swing.JTextField();
        label_AEX2 = new javax.swing.JLabel();
        text_AEXlname = new javax.swing.JTextField();
        text_AEXemail = new javax.swing.JTextField();
        label_AEX3 = new javax.swing.JLabel();
        label_AEX4 = new javax.swing.JLabel();
        text_AEXphone = new javax.swing.JTextField();
        label_AEX5 = new javax.swing.JLabel();
        text_AEXaexid = new javax.swing.JTextField();
        lbl_AEXsubtitle1 = new javax.swing.JLabel();
        label_AEX6 = new javax.swing.JLabel();
        text_AEXexid = new javax.swing.JTextField();
        label_AEX7 = new javax.swing.JLabel();
        text_AEXsoid = new javax.swing.JTextField();
        label_AEX8 = new javax.swing.JLabel();
        text_AEXcoid = new javax.swing.JTextField();
        label_AEX9 = new javax.swing.JLabel();
        text_AEXoamount = new javax.swing.JTextField();
        label_AEX10 = new javax.swing.JLabel();
        Combobox_pmethod2 = new javax.swing.JComboBox<>();
        btn_SupOrderClear2 = new View.MyButton();
        btn_payGPex2 = new View.MyButton();
        label_AEX11 = new javax.swing.JLabel();
        text_AEXrate = new javax.swing.JTextField();
        label_AEX12 = new javax.swing.JLabel();
        text_AEXtot = new javax.swing.JTextField();
        btn_exGPsalaryCal1 = new View.MyButton();
        label_AEX13 = new javax.swing.JLabel();
        jInternalFrameJobsShow = new javax.swing.JInternalFrame();
        btn_back6 = new View.MyButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_jobsGemPolisher = new javax.swing.JTable();
        lbl_rawfinishedGems1 = new javax.swing.JLabel();
        btn_showAssignedJobs = new View.MyButton();
        btn_showAllJobs = new View.MyButton();
        btn_showCompletedJobs = new View.MyButton();
        jInternalFrameManageRawGems = new javax.swing.JInternalFrame();
        btn_ManageRawGem = new View.MyButton();
        lbl_rg_gname = new javax.swing.JLabel();
        txt_rg_name = new javax.swing.JTextField();
        lbl_rg_gtype = new javax.swing.JLabel();
        txt_rg_type = new javax.swing.JTextField();
        txt_rg_color = new javax.swing.JTextField();
        txt_rg_weight = new javax.swing.JTextField();
        txt_rg_buyPrice = new javax.swing.JTextField();
        lbl_rg_gBuyPrice = new javax.swing.JLabel();
        lbl_rg_carats = new javax.swing.JLabel();
        lbl_rg_gweight = new javax.swing.JLabel();
        lbl_rg_gcolor = new javax.swing.JLabel();
        lbl_rawgemimage = new javax.swing.JLabel();
        btn_rg_imageUpload = new View.MyButton();
        lbl_rg_gimage = new javax.swing.JLabel();
        txt_rg_origin = new javax.swing.JTextField();
        lbl_rg_gOrigin = new javax.swing.JLabel();
        lbl_rg_gStatus = new javax.swing.JLabel();
        txt_rg_Status = new javax.swing.JTextField();
        lbl_rg_gNature = new javax.swing.JLabel();
        txt_rg_nature = new javax.swing.JTextField();
        txt_rg_sellPrice = new javax.swing.JTextField();
        lbl_rg_gSellingPrice = new javax.swing.JLabel();
        myButton1 = new View.MyButton();
        btn_addRawGem = new View.MyButton();
        btn_rawGemUpdate = new View.MyButton();
        btn_deleteRawGem = new View.MyButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tble_ManageRawGem = new javax.swing.JTable();
        lbl_ManageRawGemTitle = new javax.swing.JLabel();
        jInternalFrameManageFinishedGems = new javax.swing.JInternalFrame();
        btn_back8 = new View.MyButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        tble_ManageFinishedGems = new javax.swing.JTable();
        lbl_ManageRawGemTitle1 = new javax.swing.JLabel();
        lbl_finishedgemimage = new javax.swing.JLabel();
        btn_fg_imageUpload = new View.MyButton();
        lbl_fg_gimage = new javax.swing.JLabel();
        lbl_fg_pieces = new javax.swing.JLabel();
        lbl_fg_status = new javax.swing.JLabel();
        txt_fg_status = new javax.swing.JTextField();
        txt_fg_pieces = new javax.swing.JTextField();
        btn_clearFinishedGem = new View.MyButton();
        btn_deleteFinishedGem = new View.MyButton();
        btn_FinishedGemUpdate = new View.MyButton();
        btn_addFinishedGem = new View.MyButton();
        lbl_fg_shape = new javax.swing.JLabel();
        lbl_fg_licenseID = new javax.swing.JLabel();
        lbl_fg_buyprice = new javax.swing.JLabel();
        lbl_fg_carats = new javax.swing.JLabel();
        lbl_fg_weight = new javax.swing.JLabel();
        lbl_fg_color = new javax.swing.JLabel();
        lbl_fg_type = new javax.swing.JLabel();
        lbl_fg_gname = new javax.swing.JLabel();
        txt_fg_name = new javax.swing.JTextField();
        txt_fg_type = new javax.swing.JTextField();
        txt_fg_color = new javax.swing.JTextField();
        txt_fg_weight = new javax.swing.JTextField();
        txt_fg_buyprice = new javax.swing.JTextField();
        txt_fg_LicenseID = new javax.swing.JTextField();
        txt_fg_shape = new javax.swing.JTextField();
        lbl_fg_sellprice = new javax.swing.JLabel();
        txt_fg_sellprice = new javax.swing.JTextField();
        jInternalFrameReports1 = new javax.swing.JInternalFrame();
        panelLoad1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbReportType = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnBack6 = new View.MyButton();
        btnGenerate = new View.MyButton();
        datechooser_to = new com.toedter.calendar.JDateChooser();
        datechooser_from = new com.toedter.calendar.JDateChooser();
        jInternalFrameInvoice = new javax.swing.JInternalFrame();
        panelLoad2 = new javax.swing.JPanel();
        btnDone2 = new View.MyButton();
        jInternalFrameManageSuppliers = new javax.swing.JInternalFrame();
        btnBack = new View.MyButton();
        label_ufname1 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tbl_supplier = new javax.swing.JTable();
        label_ufname2 = new javax.swing.JLabel();
        label_ulname1 = new javax.swing.JLabel();
        label_uaddress = new javax.swing.JLabel();
        label_udob = new javax.swing.JLabel();
        label_unic = new javax.swing.JLabel();
        label_uemail1 = new javax.swing.JLabel();
        label_uphone1 = new javax.swing.JLabel();
        label_upwd = new javax.swing.JLabel();
        label_ulno = new javax.swing.JLabel();
        label_ubacc = new javax.swing.JLabel();
        btnAdd = new View.MyButton();
        btnUpdate = new View.MyButton();
        btndelete = new View.MyButton();
        btnClear = new View.MyButton();
        text_ufname = new javax.swing.JTextField();
        text_ulname = new javax.swing.JTextField();
        text_uadd1 = new javax.swing.JTextField();
        datechooser_udob = new com.toedter.calendar.JDateChooser();
        text_unic = new javax.swing.JTextField();
        text_uemail = new javax.swing.JTextField();
        text_uadd2 = new javax.swing.JTextField();
        text_uadd3 = new javax.swing.JTextField();
        text_upwd = new javax.swing.JTextField();
        text_uphone = new javax.swing.JTextField();
        text_ulno = new javax.swing.JTextField();
        text_ubacc = new javax.swing.JTextField();
        jInternalFrameManageClients = new javax.swing.JInternalFrame();
        btnBack1 = new View.MyButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        tbl_client = new javax.swing.JTable();
        label_ufname3 = new javax.swing.JLabel();
        label_ufname4 = new javax.swing.JLabel();
        label_ulname2 = new javax.swing.JLabel();
        label_uaddress1 = new javax.swing.JLabel();
        label_udob1 = new javax.swing.JLabel();
        label_unic1 = new javax.swing.JLabel();
        label_uemail2 = new javax.swing.JLabel();
        label_uphone2 = new javax.swing.JLabel();
        label_upwd1 = new javax.swing.JLabel();
        label_uregion = new javax.swing.JLabel();
        btnAdd1 = new View.MyButton();
        btnupdate1 = new View.MyButton();
        btndelete1 = new View.MyButton();
        btnClear1 = new View.MyButton();
        text_ufname1 = new javax.swing.JTextField();
        text_ulname1 = new javax.swing.JTextField();
        text_uadd4 = new javax.swing.JTextField();
        text_uadd5 = new javax.swing.JTextField();
        text_uadd6 = new javax.swing.JTextField();
        text_upwd1 = new javax.swing.JTextField();
        text_uphone1 = new javax.swing.JTextField();
        datechooser_udob1 = new com.toedter.calendar.JDateChooser();
        text_unic1 = new javax.swing.JTextField();
        text_uemail1 = new javax.swing.JTextField();
        comboURegion = new javax.swing.JComboBox<>();
        jInternalFrameManageAgent = new javax.swing.JInternalFrame();
        btnBack2 = new View.MyButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        tbl_agent = new javax.swing.JTable();
        label_ufname5 = new javax.swing.JLabel();
        label_ufname6 = new javax.swing.JLabel();
        label_ulname3 = new javax.swing.JLabel();
        label_uaddress2 = new javax.swing.JLabel();
        label_udob3 = new javax.swing.JLabel();
        label_unic2 = new javax.swing.JLabel();
        label_uemail3 = new javax.swing.JLabel();
        label_ubacc1 = new javax.swing.JLabel();
        label_udob2 = new javax.swing.JLabel();
        label_ubacc2 = new javax.swing.JLabel();
        label_upwd2 = new javax.swing.JLabel();
        label_uphone3 = new javax.swing.JLabel();
        btnAdd2 = new View.MyButton();
        myButton6 = new View.MyButton();
        Agentdelete = new View.MyButton();
        btnClear2 = new View.MyButton();
        text_ufname2 = new javax.swing.JTextField();
        text_ulname2 = new javax.swing.JTextField();
        text_uadd7 = new javax.swing.JTextField();
        datechooser_udob2 = new com.toedter.calendar.JDateChooser();
        text_unic2 = new javax.swing.JTextField();
        text_uemail2 = new javax.swing.JTextField();
        text_uadd8 = new javax.swing.JTextField();
        text_uadd9 = new javax.swing.JTextField();
        text_upwd2 = new javax.swing.JTextField();
        text_uphone2 = new javax.swing.JTextField();
        text_ubacc1 = new javax.swing.JTextField();
        datechooser_jdate = new com.toedter.calendar.JDateChooser();
        cmb_worktype = new javax.swing.JComboBox<>();
        jInternalFrameManageGemPolisher = new javax.swing.JInternalFrame();
        btnBack3 = new View.MyButton();
        jScrollPane16 = new javax.swing.JScrollPane();
        tbl_gempolisher = new javax.swing.JTable();
        label_ufname9 = new javax.swing.JLabel();
        label_uphone5 = new javax.swing.JLabel();
        cmb_worktype2 = new javax.swing.JComboBox<>();
        label_ufname7 = new javax.swing.JLabel();
        label_ulname4 = new javax.swing.JLabel();
        label_uaddress3 = new javax.swing.JLabel();
        label_udob4 = new javax.swing.JLabel();
        label_unic3 = new javax.swing.JLabel();
        label_uemail4 = new javax.swing.JLabel();
        label_ubacc3 = new javax.swing.JLabel();
        label_ubacc4 = new javax.swing.JLabel();
        label_ulno1 = new javax.swing.JLabel();
        label_upwd3 = new javax.swing.JLabel();
        label_uphone4 = new javax.swing.JLabel();
        btnAddgempolisher = new View.MyButton();
        btnUpdategempolisher = new View.MyButton();
        btndeletegempolisher = new View.MyButton();
        btnClear3 = new View.MyButton();
        text_ufname3 = new javax.swing.JTextField();
        text_ulname3 = new javax.swing.JTextField();
        text_uadd10 = new javax.swing.JTextField();
        datechooser_udob3 = new com.toedter.calendar.JDateChooser();
        text_unic3 = new javax.swing.JTextField();
        text_uemail3 = new javax.swing.JTextField();
        datechooser_jdate2 = new com.toedter.calendar.JDateChooser();
        text_ubacc2 = new javax.swing.JTextField();
        text_salary = new javax.swing.JTextField();
        text_uadd11 = new javax.swing.JTextField();
        text_uadd12 = new javax.swing.JTextField();
        text_upwd3 = new javax.swing.JTextField();
        text_uphone3 = new javax.swing.JTextField();
        jInternalFrameManageOwners = new javax.swing.JInternalFrame();
        btnBack4 = new View.MyButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        tbl_owner = new javax.swing.JTable();
        label_ufname8 = new javax.swing.JLabel();
        label_ulname5 = new javax.swing.JLabel();
        label_uaddress4 = new javax.swing.JLabel();
        label_unic4 = new javax.swing.JLabel();
        label_uemail5 = new javax.swing.JLabel();
        label_uphone6 = new javax.swing.JLabel();
        label_upwd4 = new javax.swing.JLabel();
        text_ufname4 = new javax.swing.JTextField();
        text_ulname4 = new javax.swing.JTextField();
        text_uadd13 = new javax.swing.JTextField();
        text_uadd14 = new javax.swing.JTextField();
        text_uadd15 = new javax.swing.JTextField();
        btnClear4 = new View.MyButton();
        btnAddowner = new View.MyButton();
        btnUpdateowner = new View.MyButton();
        text_unic4 = new javax.swing.JTextField();
        text_uemail4 = new javax.swing.JTextField();
        text_uphone4 = new javax.swing.JTextField();
        text_upwd4 = new javax.swing.JTextField();
        btndeleteowner = new View.MyButton();
        label_ufname10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(46, 43, 43));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnManageGems.setBackground(new java.awt.Color(46, 43, 43));
        btnManageGems.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManageGems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnManageGemsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnManageGemsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnManageGemsMouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-jewel-30 (1).png"))); // NOI18N

        lblBar3.setBackground(new java.awt.Color(255, 51, 51));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Gems");

        javax.swing.GroupLayout btnManageGemsLayout = new javax.swing.GroupLayout(btnManageGems);
        btnManageGems.setLayout(btnManageGemsLayout);
        btnManageGemsLayout.setHorizontalGroup(
            btnManageGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnManageGemsLayout.createSequentialGroup()
                .addComponent(lblBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnManageGemsLayout.setVerticalGroup(
            btnManageGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnManageGemsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnManageGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnManageGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 170, 60));

        buttonHome.setBackground(new java.awt.Color(46, 43, 43));
        buttonHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonHomeMouseExited(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/home.png"))); // NOI18N

        lblBar1.setBackground(new java.awt.Color(255, 51, 51));
        lblBar1.setOpaque(true);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Home");

        javax.swing.GroupLayout buttonHomeLayout = new javax.swing.GroupLayout(buttonHome);
        buttonHome.setLayout(buttonHomeLayout);
        buttonHomeLayout.setHorizontalGroup(
            buttonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonHomeLayout.createSequentialGroup()
                .addComponent(lblBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        buttonHomeLayout.setVerticalGroup(
            buttonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(buttonHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 170, 60));

        btnManageUsers.setBackground(new java.awt.Color(46, 43, 43));
        btnManageUsers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnManageUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnManageUsersMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnManageUsersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnManageUsersMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-conference-30 (1).png"))); // NOI18N

        lblBar2.setBackground(new java.awt.Color(255, 51, 51));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Users");

        javax.swing.GroupLayout btnManageUsersLayout = new javax.swing.GroupLayout(btnManageUsers);
        btnManageUsers.setLayout(btnManageUsersLayout);
        btnManageUsersLayout.setHorizontalGroup(
            btnManageUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnManageUsersLayout.createSequentialGroup()
                .addComponent(lblBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        btnManageUsersLayout.setVerticalGroup(
            btnManageUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnManageUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnManageUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnManageUsers, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 170, 60));

        btnOrderDetails.setBackground(new java.awt.Color(46, 43, 43));
        btnOrderDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOrderDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOrderDetailsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOrderDetailsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOrderDetailsMouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delivery.png"))); // NOI18N

        lblBar4.setBackground(new java.awt.Color(255, 51, 51));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText(" Operations");

        javax.swing.GroupLayout btnOrderDetailsLayout = new javax.swing.GroupLayout(btnOrderDetails);
        btnOrderDetails.setLayout(btnOrderDetailsLayout);
        btnOrderDetailsLayout.setHorizontalGroup(
            btnOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnOrderDetailsLayout.createSequentialGroup()
                .addComponent(lblBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );
        btnOrderDetailsLayout.setVerticalGroup(
            btnOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnOrderDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnOrderDetailsLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        menu.add(btnOrderDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 170, -1));

        btnReports.setBackground(new java.awt.Color(46, 43, 43));
        btnReports.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReportsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReportsMouseExited(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/007.png"))); // NOI18N

        lblBar5.setBackground(new java.awt.Color(255, 51, 51));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Reports");

        javax.swing.GroupLayout btnReportsLayout = new javax.swing.GroupLayout(btnReports);
        btnReports.setLayout(btnReportsLayout);
        btnReportsLayout.setHorizontalGroup(
            btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReportsLayout.createSequentialGroup()
                .addComponent(lblBar5, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        btnReportsLayout.setVerticalGroup(
            btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
            .addComponent(lblBar5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 170, -1));

        btnHelp.setBackground(new java.awt.Color(46, 43, 43));
        btnHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHelpMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHelpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHelpMouseExited(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/006.png"))); // NOI18N

        lblBar6.setBackground(new java.awt.Color(255, 51, 51));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Help");

        javax.swing.GroupLayout btnHelpLayout = new javax.swing.GroupLayout(btnHelp);
        btnHelp.setLayout(btnHelpLayout);
        btnHelpLayout.setHorizontalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHelpLayout.createSequentialGroup()
                .addComponent(lblBar6, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        btnHelpLayout.setVerticalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHelpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
            .addComponent(lblBar6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 170, -1));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bbrdbb.png"))); // NOI18N
        jLabel34.setText("jLabel34");
        menu.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(-170, 350, 340, 450));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/sub logo.png"))); // NOI18N
        menu.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 90));

        bg.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 610));

        jPanel1.setBackground(new java.awt.Color(46, 43, 43));

        btnLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/closing.png"))); // NOI18N
        btnLogOut.setToolTipText("Log Out");
        btnLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogOutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogOutMouseEntered(evt);
            }
        });

        btnProfile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/profile.png"))); // NOI18N
        btnProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProfileMouseClicked(evt);
            }
        });

        lblUserName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName.setText("ID");

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-close-window-48.png"))); // NOI18N
        btnExit.setToolTipText("Exit");
        btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });

        btnMiniMize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-minimize-window-48.png"))); // NOI18N
        btnMiniMize.setToolTipText("Minimize");
        btnMiniMize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMiniMize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMiniMizeMouseClicked(evt);
            }
        });

        lblUserName1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUserName1.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName1.setText("Owner ID :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(434, Short.MAX_VALUE)
                .addComponent(btnProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogOut)
                .addGap(18, 18, 18)
                .addComponent(btnMiniMize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit)
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMiniMize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserName1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnProfile)))
                .addGap(592, 592, 592))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 840, 50));

        form1.setBackground(new java.awt.Color(255, 255, 255));
        form1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrameHome.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameHome.setBorder(null);
        jInternalFrameHome.setVisible(true);
        jInternalFrameHome.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111_1.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, -20, 380, 430));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 340, 480));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/360_F_251407347_aBmnWrp3B8tTPgnKO3l2Y4tBUkPe6g5X-removebg-preview.png"))); // NOI18N
        jLabel42.setText("jLabel41");
        jInternalFrameHome.getContentPane().add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 370, -1, -1));

        jLabel43.setBackground(new java.awt.Color(255, 255, 255));
        jLabel43.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 48)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("WELCOME !");
        jInternalFrameHome.getContentPane().add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, 290, -1));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 50, -1));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 50, -1));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 50, -1));

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 50, -1));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 50, -1));

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Quick User Guide :");
        jInternalFrameHome.getContentPane().add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 160, 30));

        jLabel49.setBackground(new java.awt.Color(255, 255, 255));
        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Manage(View/Register/Update/Delete) All the users (Clients/Suppliers/Agents/Gem polishers/owners).");
        jInternalFrameHome.getContentPane().add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 720, 30));

        jLabel50.setBackground(new java.awt.Color(255, 255, 255));
        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText(" Manage(View/Insert/Update/Delete) All the Gem Categories (Raw Gems/Finished Gems).");
        jInternalFrameHome.getContentPane().add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 630, 30));

        jLabel51.setBackground(new java.awt.Color(255, 255, 255));
        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("gem polisher) and assign work to Gem polisher.");
        jInternalFrameHome.getContentPane().add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 430, 30));

        jLabel52.setBackground(new java.awt.Color(255, 255, 255));
        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Generate Income Report / Expencess Reports.");
        jInternalFrameHome.getContentPane().add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 340, 30));

        jLabel53.setBackground(new java.awt.Color(255, 255, 255));
        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 0));
        jLabel53.setText("Manage(Approve/Reject) Supplier orders, View Client orders, Manage(pay) Expences(Supplier,Agent,");
        jInternalFrameHome.getContentPane().add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 710, 30));

        jLabel54.setBackground(new java.awt.Color(255, 255, 255));
        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(0, 0, 0));
        jLabel54.setText("Find more about Ashraf Gems");
        jInternalFrameHome.getContentPane().add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 300, 30));

        form1.add(jInternalFrameHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        jInternalFrameManageUsers.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageUsers.setBorder(null);
        jInternalFrameManageUsers.setVisible(false);
        jInternalFrameManageUsers.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ManageSuppliers.setBackground(new java.awt.Color(46, 43, 43));
        btn_ManageSuppliers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ManageSuppliers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ManageSuppliersMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ManageSuppliersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ManageSuppliersMouseExited(evt);
            }
        });

        lblBar18.setBackground(new java.awt.Color(255, 51, 51));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Manage Suppliers");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_ManageSuppliersLayout = new javax.swing.GroupLayout(btn_ManageSuppliers);
        btn_ManageSuppliers.setLayout(btn_ManageSuppliersLayout);
        btn_ManageSuppliersLayout.setHorizontalGroup(
            btn_ManageSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_ManageSuppliersLayout.createSequentialGroup()
                .addComponent(lblBar18, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
        btn_ManageSuppliersLayout.setVerticalGroup(
            btn_ManageSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar18, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_ManageSuppliersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jInternalFrameManageUsers.getContentPane().add(btn_ManageSuppliers, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 170, 70));

        btn_ManageClients.setBackground(new java.awt.Color(46, 43, 43));
        btn_ManageClients.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ManageClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ManageClientsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ManageClientsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ManageClientsMouseExited(evt);
            }
        });

        lblBar19.setBackground(new java.awt.Color(255, 51, 51));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Manage Clients");
        jLabel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel35MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_ManageClientsLayout = new javax.swing.GroupLayout(btn_ManageClients);
        btn_ManageClients.setLayout(btn_ManageClientsLayout);
        btn_ManageClientsLayout.setHorizontalGroup(
            btn_ManageClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_ManageClientsLayout.createSequentialGroup()
                .addComponent(lblBar19, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel35)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        btn_ManageClientsLayout.setVerticalGroup(
            btn_ManageClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_ManageClientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jInternalFrameManageUsers.getContentPane().add(btn_ManageClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 180, -1));

        btn_ManageAgents.setBackground(new java.awt.Color(46, 43, 43));
        btn_ManageAgents.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ManageAgents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ManageAgentsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ManageAgentsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ManageAgentsMouseExited(evt);
            }
        });

        lblBar20.setBackground(new java.awt.Color(255, 51, 51));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Manage Agents");
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_ManageAgentsLayout = new javax.swing.GroupLayout(btn_ManageAgents);
        btn_ManageAgents.setLayout(btn_ManageAgentsLayout);
        btn_ManageAgentsLayout.setHorizontalGroup(
            btn_ManageAgentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_ManageAgentsLayout.createSequentialGroup()
                .addComponent(lblBar20, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        btn_ManageAgentsLayout.setVerticalGroup(
            btn_ManageAgentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_ManageAgentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jInternalFrameManageUsers.getContentPane().add(btn_ManageAgents, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, -1, -1));

        btn_GemPolishers.setBackground(new java.awt.Color(46, 43, 43));
        btn_GemPolishers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_GemPolishers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_GemPolishersMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_GemPolishersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_GemPolishersMouseExited(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Manage GemPolisher");
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btn_GemPolishersLayout = new javax.swing.GroupLayout(btn_GemPolishers);
        btn_GemPolishers.setLayout(btn_GemPolishersLayout);
        btn_GemPolishersLayout.setHorizontalGroup(
            btn_GemPolishersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_GemPolishersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_GemPolishersLayout.setVerticalGroup(
            btn_GemPolishersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_GemPolishersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrameManageUsers.getContentPane().add(btn_GemPolishers, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, -1, -1));

        btn_ManageOwners.setBackground(new java.awt.Color(46, 43, 43));
        btn_ManageOwners.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ManageOwners.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ManageOwnersMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ManageOwnersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ManageOwnersMouseExited(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Manage Owners");

        javax.swing.GroupLayout btn_ManageOwnersLayout = new javax.swing.GroupLayout(btn_ManageOwners);
        btn_ManageOwners.setLayout(btn_ManageOwnersLayout);
        btn_ManageOwnersLayout.setHorizontalGroup(
            btn_ManageOwnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_ManageOwnersLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel38)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        btn_ManageOwnersLayout.setVerticalGroup(
            btn_ManageOwnersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_ManageOwnersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrameManageUsers.getContentPane().add(btn_ManageOwners, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 390, 170, -1));

        form1.add(jInternalFrameManageUsers, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        jInternalFrameManageGems.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageGems.setVisible(false);
        jInternalFrameManageGems.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ManageRawGems.setBackground(new java.awt.Color(46, 43, 43));
        btn_ManageRawGems.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ManageRawGems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ManageRawGemsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ManageRawGemsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ManageRawGemsMouseExited(evt);
            }
        });

        lblBar16.setBackground(new java.awt.Color(255, 51, 51));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Manage Raw Gems");

        javax.swing.GroupLayout btn_ManageRawGemsLayout = new javax.swing.GroupLayout(btn_ManageRawGems);
        btn_ManageRawGems.setLayout(btn_ManageRawGemsLayout);
        btn_ManageRawGemsLayout.setHorizontalGroup(
            btn_ManageRawGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_ManageRawGemsLayout.createSequentialGroup()
                .addComponent(lblBar16, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        btn_ManageRawGemsLayout.setVerticalGroup(
            btn_ManageRawGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btn_ManageRawGemsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrameManageGems.getContentPane().add(btn_ManageRawGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 220, 60));

        btn_ManageFinishedGems.setBackground(new java.awt.Color(46, 43, 43));
        btn_ManageFinishedGems.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ManageFinishedGems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ManageFinishedGemsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ManageFinishedGemsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ManageFinishedGemsMouseExited(evt);
            }
        });

        lblBar17.setBackground(new java.awt.Color(255, 51, 51));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Manage Finished Gems");

        javax.swing.GroupLayout btn_ManageFinishedGemsLayout = new javax.swing.GroupLayout(btn_ManageFinishedGems);
        btn_ManageFinishedGems.setLayout(btn_ManageFinishedGemsLayout);
        btn_ManageFinishedGemsLayout.setHorizontalGroup(
            btn_ManageFinishedGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_ManageFinishedGemsLayout.createSequentialGroup()
                .addComponent(lblBar17, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        btn_ManageFinishedGemsLayout.setVerticalGroup(
            btn_ManageFinishedGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btn_ManageFinishedGemsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrameManageGems.getContentPane().add(btn_ManageFinishedGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 250, -1, -1));

        form1.add(jInternalFrameManageGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 880, 600));

        jInternalFrameOrderDetails.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameOrderDetails.setVisible(false);
        jInternalFrameOrderDetails.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_orders.setBackground(new java.awt.Color(46, 43, 43));
        label_orders.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_ordersMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_ordersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_ordersMouseExited(evt);
            }
        });

        lblBar7.setBackground(new java.awt.Color(255, 51, 51));
        lblBar7.setOpaque(true);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Orders");

        jLabel11.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delivery.png"))); // NOI18N

        javax.swing.GroupLayout label_ordersLayout = new javax.swing.GroupLayout(label_orders);
        label_orders.setLayout(label_ordersLayout);
        label_ordersLayout.setHorizontalGroup(
            label_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(label_ordersLayout.createSequentialGroup()
                .addComponent(lblBar7, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(55, Short.MAX_VALUE))
        );
        label_ordersLayout.setVerticalGroup(
            label_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, label_ordersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(label_ordersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jInternalFrameOrderDetails.getContentPane().add(label_orders, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 170, 60));

        btn_gemPolisherExpenses.setBackground(new java.awt.Color(46, 43, 43));
        btn_gemPolisherExpenses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_gemPolisherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_gemPolisherExpensesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_gemPolisherExpensesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_gemPolisherExpensesMouseExited(evt);
            }
        });

        lblBar8.setBackground(new java.awt.Color(255, 51, 51));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Gem Polisher ");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Expenses");

        javax.swing.GroupLayout btn_gemPolisherExpensesLayout = new javax.swing.GroupLayout(btn_gemPolisherExpenses);
        btn_gemPolisherExpenses.setLayout(btn_gemPolisherExpensesLayout);
        btn_gemPolisherExpensesLayout.setHorizontalGroup(
            btn_gemPolisherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_gemPolisherExpensesLayout.createSequentialGroup()
                .addComponent(lblBar8, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(btn_gemPolisherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel23))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        btn_gemPolisherExpensesLayout.setVerticalGroup(
            btn_gemPolisherExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btn_gemPolisherExpensesLayout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addContainerGap())
        );

        jInternalFrameOrderDetails.getContentPane().add(btn_gemPolisherExpenses, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 330, 170, 60));

        label_expenses.setBackground(new java.awt.Color(46, 43, 43));
        label_expenses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_expenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_expensesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_expensesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_expensesMouseExited(evt);
            }
        });

        lblBar9.setBackground(new java.awt.Color(255, 51, 51));
        lblBar9.setOpaque(true);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Expenses");

        jLabel29.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/007.png"))); // NOI18N

        javax.swing.GroupLayout label_expensesLayout = new javax.swing.GroupLayout(label_expenses);
        label_expenses.setLayout(label_expensesLayout);
        label_expensesLayout.setHorizontalGroup(
            label_expensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(label_expensesLayout.createSequentialGroup()
                .addComponent(lblBar9, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        label_expensesLayout.setVerticalGroup(
            label_expensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(label_expensesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(label_expensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrameOrderDetails.getContentPane().add(label_expenses, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));

        label_jobs.setBackground(new java.awt.Color(46, 43, 43));
        label_jobs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_jobs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_jobsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_jobsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_jobsMouseExited(evt);
            }
        });

        lblBar10.setBackground(new java.awt.Color(255, 51, 51));
        lblBar10.setOpaque(true);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Jobs");

        jLabel17.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-conference-30 (1).png"))); // NOI18N

        javax.swing.GroupLayout label_jobsLayout = new javax.swing.GroupLayout(label_jobs);
        label_jobs.setLayout(label_jobsLayout);
        label_jobsLayout.setHorizontalGroup(
            label_jobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(label_jobsLayout.createSequentialGroup()
                .addComponent(lblBar10, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        label_jobsLayout.setVerticalGroup(
            label_jobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(label_jobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(label_jobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrameOrderDetails.getContentPane().add(label_jobs, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, -1, -1));

        btn_ClientOrders.setBackground(new java.awt.Color(46, 43, 43));
        btn_ClientOrders.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ClientOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_ClientOrdersMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_ClientOrdersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_ClientOrdersMouseExited(evt);
            }
        });

        lblBar11.setBackground(new java.awt.Color(255, 51, 51));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Client Orders");

        javax.swing.GroupLayout btn_ClientOrdersLayout = new javax.swing.GroupLayout(btn_ClientOrders);
        btn_ClientOrders.setLayout(btn_ClientOrdersLayout);
        btn_ClientOrdersLayout.setHorizontalGroup(
            btn_ClientOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_ClientOrdersLayout.createSequentialGroup()
                .addComponent(lblBar11, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        btn_ClientOrdersLayout.setVerticalGroup(
            btn_ClientOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_ClientOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jInternalFrameOrderDetails.getContentPane().add(btn_ClientOrders, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 170, -1));

        btn_AgentExpenses.setBackground(new java.awt.Color(46, 43, 43));
        btn_AgentExpenses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_AgentExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_AgentExpensesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_AgentExpensesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_AgentExpensesMouseExited(evt);
            }
        });

        lblBar12.setBackground(new java.awt.Color(255, 51, 51));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Agent Expenses");

        javax.swing.GroupLayout btn_AgentExpensesLayout = new javax.swing.GroupLayout(btn_AgentExpenses);
        btn_AgentExpenses.setLayout(btn_AgentExpensesLayout);
        btn_AgentExpensesLayout.setHorizontalGroup(
            btn_AgentExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_AgentExpensesLayout.createSequentialGroup()
                .addComponent(lblBar12, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_AgentExpensesLayout.setVerticalGroup(
            btn_AgentExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar12, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_AgentExpensesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jInternalFrameOrderDetails.getContentPane().add(btn_AgentExpenses, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, 170, -1));

        btn_SuppilerOrders.setBackground(new java.awt.Color(46, 43, 43));
        btn_SuppilerOrders.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_SuppilerOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SuppilerOrdersMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_SuppilerOrdersMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_SuppilerOrdersMouseExited(evt);
            }
        });

        lblBar13.setBackground(new java.awt.Color(255, 51, 51));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Supplier Orders");

        javax.swing.GroupLayout btn_SuppilerOrdersLayout = new javax.swing.GroupLayout(btn_SuppilerOrders);
        btn_SuppilerOrders.setLayout(btn_SuppilerOrdersLayout);
        btn_SuppilerOrdersLayout.setHorizontalGroup(
            btn_SuppilerOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_SuppilerOrdersLayout.createSequentialGroup()
                .addComponent(lblBar13, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        btn_SuppilerOrdersLayout.setVerticalGroup(
            btn_SuppilerOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_SuppilerOrdersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jInternalFrameOrderDetails.getContentPane().add(btn_SuppilerOrders, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 170, 60));

        btn_SupplierExpenses.setBackground(new java.awt.Color(46, 43, 43));
        btn_SupplierExpenses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_SupplierExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_SupplierExpensesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_SupplierExpensesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_SupplierExpensesMouseExited(evt);
            }
        });

        lblBar14.setBackground(new java.awt.Color(255, 51, 51));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Supplier Expenses");

        javax.swing.GroupLayout btn_SupplierExpensesLayout = new javax.swing.GroupLayout(btn_SupplierExpenses);
        btn_SupplierExpenses.setLayout(btn_SupplierExpensesLayout);
        btn_SupplierExpensesLayout.setHorizontalGroup(
            btn_SupplierExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_SupplierExpensesLayout.createSequentialGroup()
                .addComponent(lblBar14, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        btn_SupplierExpensesLayout.setVerticalGroup(
            btn_SupplierExpensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_SupplierExpensesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jInternalFrameOrderDetails.getContentPane().add(btn_SupplierExpenses, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, 170, 60));

        btn_GempolisherWork.setBackground(new java.awt.Color(46, 43, 43));
        btn_GempolisherWork.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_GempolisherWork.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_GempolisherWorkMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_GempolisherWorkMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_GempolisherWorkMouseExited(evt);
            }
        });

        lblBar15.setBackground(new java.awt.Color(255, 51, 51));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Gem Polisher");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Work");

        javax.swing.GroupLayout btn_GempolisherWorkLayout = new javax.swing.GroupLayout(btn_GempolisherWork);
        btn_GempolisherWork.setLayout(btn_GempolisherWorkLayout);
        btn_GempolisherWorkLayout.setHorizontalGroup(
            btn_GempolisherWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_GempolisherWorkLayout.createSequentialGroup()
                .addComponent(lblBar15, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(btn_GempolisherWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btn_GempolisherWorkLayout.setVerticalGroup(
            btn_GempolisherWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btn_GempolisherWorkLayout.createSequentialGroup()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addContainerGap())
        );

        jInternalFrameOrderDetails.getContentPane().add(btn_GempolisherWork, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 190, 160, 60));

        form1.add(jInternalFrameOrderDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameReports.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameReports.setVisible(false);
        jInternalFrameReports.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnOwnerExpencesReport.setText("View Expences Report");
        btnOwnerExpencesReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOwnerExpencesReportActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnOwnerExpencesReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 180, 30));

        btnOwnerIncomeReport.setText("View Income Report");
        btnOwnerIncomeReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOwnerIncomeReportActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnOwnerIncomeReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 180, 30));

        panelLoad.setBackground(new java.awt.Color(255, 255, 255));
        panelLoad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelLoadLayout = new javax.swing.GroupLayout(panelLoad);
        panelLoad.setLayout(panelLoadLayout);
        panelLoadLayout.setHorizontalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 814, Short.MAX_VALUE)
        );
        panelLoadLayout.setVerticalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );

        jInternalFrameReports.getContentPane().add(panelLoad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 820, 500));

        btnfilter.setText("Filter Reports By Date");
        btnfilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfilterActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnfilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, 180, 30));

        form1.add(jInternalFrameReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, 880, 610));

        jInternalFrameOwnerrHelp.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameOwnerrHelp.setVisible(false);
        jInternalFrameOwnerrHelp.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel86.setBackground(new java.awt.Color(255, 255, 255));
        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel86.setText("Explore and Learn More About Gem Varieties and Ashraf Gems");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, 30));

        jLabel77.setBackground(new java.awt.Color(255, 255, 255));
        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel77.setText("Scan the QR Code Using Your Mobile Phone");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        jInternalFrameOwnerrHelp.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 220, 200));

        jLabel76.setBackground(new java.awt.Color(255, 255, 255));
        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel76.setText("Find us one Map");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 30));

        btnMap.setText("Click Here");
        btnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapActionPerformed(evt);
            }
        });
        jInternalFrameOwnerrHelp.getContentPane().add(btnMap, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 100, 40));

        jLabel75.setBackground(new java.awt.Color(255, 255, 255));
        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel75.setText("Contact us :");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        jLabel82.setBackground(new java.awt.Color(255, 255, 255));
        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-call-30.png"))); // NOI18N
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, -1, 30));

        jLabel83.setBackground(new java.awt.Color(255, 255, 255));
        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-mail-30.png"))); // NOI18N
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 40, 20));

        jLabel74.setBackground(new java.awt.Color(255, 255, 255));
        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-address-30.png"))); // NOI18N
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 40, 30));

        jLabel87.setBackground(new java.awt.Color(255, 255, 255));
        jLabel87.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel87.setText("NO 68/10 DHARGAROAD CHINAFORT BERUWALA");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, -1, 30));

        jLabel88.setBackground(new java.awt.Color(255, 255, 255));
        jLabel88.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel88.setText("ASHRAFGEMS@GMAIL.COM");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, 30));

        jLabel85.setBackground(new java.awt.Color(255, 255, 255));
        jLabel85.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel85.setText("0773431901 /  0112874534");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, -1, 30));

        jLabel79.setBackground(new java.awt.Color(255, 255, 255));
        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-facebook-30.png"))); // NOI18N
        jLabel79.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, -1, 30));

        jLabel80.setBackground(new java.awt.Color(255, 255, 255));
        jLabel80.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-instagram-30.png"))); // NOI18N
        jLabel80.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 320, -1, 30));

        jLabel81.setBackground(new java.awt.Color(255, 255, 255));
        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-wechat-30.png"))); // NOI18N
        jLabel81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 320, -1, 30));

        jLabel78.setBackground(new java.awt.Color(255, 255, 255));
        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel78.setText("---------------------------------------------------------------------------------------------------------");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, 30));

        jLabel89.setBackground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Licence Dealer of National Gem and Jewellery Authority");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, 30));

        jLabel90.setBackground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Member of Chinafort Gem and Jewellery Trade Association (CGJTA)");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, -1, 30));

        jLabel91.setBackground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Registered Exporter of Sri Lanka Export Development Board");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, -1, 30));

        jLabel84.setBackground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Copyright © Ashraf Gems 2022");
        jInternalFrameOwnerrHelp.getContentPane().add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 490, -1, 30));

        form1.add(jInternalFrameOwnerrHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameProfile.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameProfile.setVisible(false);
        jInternalFrameProfile.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        form1.add(jInternalFrameProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(-22, -34, 870, 600));

        jInternalFrameJobs.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameJobs.setVisible(false);
        jInternalFrameJobs.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back.setText("<<Back");
        btn_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_backActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, -1));

        lbl_rawfinishedGems.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rawfinishedGems.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_rawfinishedGems.setText("Available Raw Gems");
        jInternalFrameJobs.getContentPane().add(lbl_rawfinishedGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, -1, -1));

        tbl_availaleRGems.setBackground(new java.awt.Color(102, 153, 255));
        tbl_availaleRGems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_availaleRGems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_availaleRGemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_availaleRGems);

        jInternalFrameJobs.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 820, 110));

        lbl_gname1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gname1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_gname1.setText("Gem Name");
        jInternalFrameJobs.getContentPane().add(lbl_gname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 340, -1, -1));

        txt_gname.setEditable(false);
        txt_gname.setBackground(new java.awt.Color(255, 255, 255));
        txt_gname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gnameActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(txt_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 160, 20));

        txt_gtype.setEditable(false);
        txt_gtype.setBackground(new java.awt.Color(255, 255, 255));
        txt_gtype.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gtypeActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(txt_gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 160, 20));

        lbl_gtype.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gtype.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_gtype.setText("Gem Type");
        jInternalFrameJobs.getContentPane().add(lbl_gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, -1, -1));

        lbl_gcolor.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gcolor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_gcolor.setText("Gem Color");
        jInternalFrameJobs.getContentPane().add(lbl_gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 380, -1, -1));

        txt_gcolor.setEditable(false);
        txt_gcolor.setBackground(new java.awt.Color(255, 255, 255));
        txt_gcolor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gcolor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gcolorActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(txt_gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 160, 20));

        txt_gweight.setEditable(false);
        txt_gweight.setBackground(new java.awt.Color(255, 255, 255));
        txt_gweight.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gweightActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(txt_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 160, 20));

        lbl_gweight.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gweight.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_gweight.setText("Gem Weight");
        jInternalFrameJobs.getContentPane().add(lbl_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, -1, -1));

        lbl_weight.setText("(Carats)");
        jInternalFrameJobs.getContentPane().add(lbl_weight, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 50, 20));

        lbl_gname2.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gname2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gname2.setText("Gem Polisher Details");
        jInternalFrameJobs.getContentPane().add(lbl_gname2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, -1, -1));

        tbl_jGempolishers.setBackground(new java.awt.Color(102, 153, 255));
        tbl_jGempolishers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_jGempolishers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_jGempolishersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_jGempolishers);

        jInternalFrameJobs.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 820, 110));

        lbl_gOrigin.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gOrigin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_gOrigin.setText("Gem Origin");
        jInternalFrameJobs.getContentPane().add(lbl_gOrigin, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, -1, -1));

        txt_gOrigin.setEditable(false);
        txt_gOrigin.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gOrigin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gOriginActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(txt_gOrigin, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 160, 20));

        txt_gnature.setEditable(false);
        txt_gnature.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gnature.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gnatureActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(txt_gnature, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 440, 160, 20));

        lbl_gemNature.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gemNature.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_gemNature.setText("Gem Nature");
        jInternalFrameJobs.getContentPane().add(lbl_gemNature, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 440, -1, -1));

        label_ufname.setBackground(new java.awt.Color(255, 255, 255));
        label_ufname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_ufname.setText("First Name     :");
        jInternalFrameJobs.getContentPane().add(label_ufname, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, 130, -1));

        text_ugfname.setEditable(false);
        text_ugfname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ugfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ugfnameActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(text_ugfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 350, 180, 20));

        label_ulname.setBackground(new java.awt.Color(255, 255, 255));
        label_ulname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_ulname.setText("Last Name      :");
        jInternalFrameJobs.getContentPane().add(label_ulname, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 140, -1));

        text_uglname.setEditable(false);
        text_uglname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uglname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uglnameActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(text_uglname, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 370, 180, 20));

        label_uemail.setBackground(new java.awt.Color(255, 255, 255));
        label_uemail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_uemail.setText("Email               :");
        jInternalFrameJobs.getContentPane().add(label_uemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 390, 130, -1));

        label_uphone.setBackground(new java.awt.Color(255, 255, 255));
        label_uphone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_uphone.setText("Contact No     :");
        jInternalFrameJobs.getContentPane().add(label_uphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, -1, -1));

        text_ugphone.setEditable(false);
        text_ugphone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ugphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ugphoneActionPerformed(evt);
            }
        });
        text_ugphone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ugphoneKeyPressed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(text_ugphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 410, 180, 20));

        text_ugemail.setEditable(false);
        text_ugemail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ugemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ugemailActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(text_ugemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 390, 180, 20));

        btn_showGJobs.setText("Show Jobs");
        btn_showGJobs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showGJobsActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(btn_showGJobs, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 490, 100, 40));

        myButton2.setText("Add Job");
        myButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton2ActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(myButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 490, 90, 40));

        btn_gjobClear.setText("Clear");
        btn_gjobClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gjobClearActionPerformed(evt);
            }
        });
        jInternalFrameJobs.getContentPane().add(btn_gjobClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 490, 90, 40));
        jInternalFrameJobs.getContentPane().add(jDateChooser_JEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 440, 180, -1));

        lbl_jobenddate.setBackground(new java.awt.Color(255, 255, 255));
        lbl_jobenddate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_jobenddate.setText("Expected Job End Date :");
        jInternalFrameJobs.getContentPane().add(lbl_jobenddate, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 440, -1, -1));

        form1.add(jInternalFrameJobs, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameSupplierOrders.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameSupplierOrders.setVisible(false);
        jInternalFrameSupplierOrders.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back1.setText("<<Back");
        btn_back1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_back1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(btn_back1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 80, -1));

        tbl_SupplierOrders.setBackground(new java.awt.Color(102, 153, 255));
        tbl_SupplierOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_SupplierOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_SupplierOrdersMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tbl_SupplierOrders);

        jInternalFrameSupplierOrders.getContentPane().add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 820, 120));

        lbl_supGemInfoTitle.setBackground(new java.awt.Color(255, 255, 255));
        lbl_supGemInfoTitle.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        lbl_supGemInfoTitle.setText("Gem's Information");
        jInternalFrameSupplierOrders.getContentPane().add(lbl_supGemInfoTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, -1, -1));

        label_supOrderAgentNo.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderAgentNo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderAgentNo.setText("Suppiler Refferal Agent No ");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderAgentNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 190, 20));

        label_supOrderSuplname.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSuplname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSuplname.setText("Last Name                             ");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderSuplname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 190, -1));

        textSO_ano.setEditable(false);
        textSO_ano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_ano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_anoActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_ano, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, 120, 20));

        label_supOrderSupEmail.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSupEmail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSupEmail.setText("Email                                      ");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderSupEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 190, -1));

        label_supOrderSupPhone.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSupPhone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSupPhone.setText("Contact Number                   ");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderSupPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        label_supOrderSupID.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSupID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSupID.setText("Supplier ID                            ");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderSupID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 190, -1));

        textSO_sno.setEditable(false);
        textSO_sno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_sno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_snoActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_sno, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, 120, 20));

        label_supOrderSupfanme.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSupfanme.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSupfanme.setText("First Name                            ");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderSupfanme, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 190, -1));

        textSO_slname.setEditable(false);
        textSO_slname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_slname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_slnameActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_slname, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 120, 20));

        textSO_sotot.setEditable(false);
        textSO_sotot.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_sotot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_sototActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_sotot, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 450, 120, 20));

        textSO_semail.setEditable(false);
        textSO_semail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_semail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_semailActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_semail, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 290, 120, 20));

        textSO_sfname.setEditable(false);
        textSO_sfname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_sfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_sfnameActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_sfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 120, 20));

        lbl_supplersOrderTitle.setBackground(new java.awt.Color(255, 255, 255));
        lbl_supplersOrderTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_supplersOrderTitle.setText("Supplier's Orders");
        jInternalFrameSupplierOrders.getContentPane().add(lbl_supplersOrderTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, -1, -1));

        lbl_supOrderInfotitle.setBackground(new java.awt.Color(255, 255, 255));
        lbl_supOrderInfotitle.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        lbl_supOrderInfotitle.setText("Supplier's Order Information");
        jInternalFrameSupplierOrders.getContentPane().add(lbl_supOrderInfotitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        label_supOrderGname.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderGname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderGname.setText("Gem Name");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderGname, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, -1, -1));

        textSO_Gname.setEditable(false);
        textSO_Gname.setBackground(new java.awt.Color(255, 255, 255));
        textSO_Gname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_Gname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_GnameActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_Gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 160, 20));

        textSO_Gtype.setEditable(false);
        textSO_Gtype.setBackground(new java.awt.Color(255, 255, 255));
        textSO_Gtype.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_Gtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_GtypeActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_Gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, 160, 20));

        textSO_Gcolor.setEditable(false);
        textSO_Gcolor.setBackground(new java.awt.Color(255, 255, 255));
        textSO_Gcolor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_Gcolor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_GcolorActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_Gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 250, 160, 20));

        textSO_Gweight.setEditable(false);
        textSO_Gweight.setBackground(new java.awt.Color(255, 255, 255));
        textSO_Gweight.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_Gweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_GweightActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_Gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 270, 160, 20));

        label_supOrderImage.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderImage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderImage.setText("Gem Image");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 360, -1, -1));

        label_supOrderGweight.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderGweight.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderGweight.setText("Gem Weight");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderGweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 270, -1, -1));

        label_supOrderGcolor.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderGcolor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderGcolor.setText("Gem Color");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderGcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, -1, -1));

        label_supOrderGtype.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderGtype.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderGtype.setText("Gem Type");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderGtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, -1, -1));

        lbl_weight2.setText("(Carats)");
        jInternalFrameSupplierOrders.getContentPane().add(lbl_weight2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 270, 50, 20));

        lbl_supOrderInfoTitle.setBackground(new java.awt.Color(255, 255, 255));
        lbl_supOrderInfoTitle.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        lbl_supOrderInfoTitle.setText("Supplier's Information");
        jInternalFrameSupplierOrders.getContentPane().add(lbl_supOrderInfoTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        label_supOrderID.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderID.setText("Order ID");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        label_supOrderDate.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderDate.setText("Order Date");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        label_supOrderStatus.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderStatus.setText("Order Status");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, -1, -1));

        label_supOrderValue.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderValue.setText("Request Amount");
        jInternalFrameSupplierOrders.getContentPane().add(label_supOrderValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, -1, -1));

        textSO_sphone.setEditable(false);
        textSO_sphone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_sphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_sphoneActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_sphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, 120, 20));

        textSO_soid.setEditable(false);
        textSO_soid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_soid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_soidActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_soid, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, 120, 20));

        textSO_soDate.setEditable(false);
        textSO_soDate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_soDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_soDateActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_soDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 410, 120, 20));

        textSO_soStatus.setEditable(false);
        textSO_soStatus.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_soStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_soStatusActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(textSO_soStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 430, 120, 20));

        textSO_GemImage.setText("                        Image");
        jInternalFrameSupplierOrders.getContentPane().add(textSO_GemImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 300, 180, 160));

        btn_RejectOrder.setText("Reject Order");
        btn_RejectOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_RejectOrderActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(btn_RejectOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 500, 130, 40));

        btn_SupOrderClear.setText("Clear");
        btn_SupOrderClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SupOrderClearActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(btn_SupOrderClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 500, 130, 40));

        btn_ApproveOrder.setText("Approve Order");
        btn_ApproveOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ApproveOrderActionPerformed(evt);
            }
        });
        jInternalFrameSupplierOrders.getContentPane().add(btn_ApproveOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 500, 130, 40));

        form1.add(jInternalFrameSupplierOrders, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameClientOrders.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameClientOrders.setVisible(false);
        jInternalFrameClientOrders.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back2.setText("<<Back");
        btn_back2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_back2ActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(btn_back2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 80, -1));

        tbl_operationClientOrders.setBackground(new java.awt.Color(102, 153, 255));
        tbl_operationClientOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_operationClientOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_operationClientOrdersMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_operationClientOrders);

        jInternalFrameClientOrders.getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 820, 220));

        lbl_gname5.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gname5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gname5.setText("Client Orders");
        jInternalFrameClientOrders.getContentPane().add(lbl_gname5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));

        txt_gname1.setEditable(false);
        txt_gname1.setBackground(new java.awt.Color(255, 255, 255));
        txt_gname1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gname1ActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(txt_gname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 160, 40));

        lbl_gname.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gname.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gname.setText("Gem Name");
        jInternalFrameClientOrders.getContentPane().add(lbl_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        lbl_gtype1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gtype1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gtype1.setText("Gem Type");
        jInternalFrameClientOrders.getContentPane().add(lbl_gtype1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, -1, -1));

        txt_gtype1.setEditable(false);
        txt_gtype1.setBackground(new java.awt.Color(255, 255, 255));
        txt_gtype1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gtype1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gtype1ActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(txt_gtype1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 160, 40));

        txt_gcolor1.setEditable(false);
        txt_gcolor1.setBackground(new java.awt.Color(255, 255, 255));
        txt_gcolor1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gcolor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gcolor1ActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(txt_gcolor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 160, 40));

        lbl_gcolor1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gcolor1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gcolor1.setText("Gem Color");
        jInternalFrameClientOrders.getContentPane().add(lbl_gcolor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 470, -1, -1));

        lbl_gweight1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gweight1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gweight1.setText("Gem Weight");
        jInternalFrameClientOrders.getContentPane().add(lbl_gweight1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, -1, -1));

        lbl_weight1.setText("Carats");
        jInternalFrameClientOrders.getContentPane().add(lbl_weight1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 530, -1, 20));

        txt_gweight1.setEditable(false);
        txt_gweight1.setBackground(new java.awt.Color(255, 255, 255));
        txt_gweight1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gweight1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gweight1ActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(txt_gweight1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, 160, 40));

        lbl_gpieces.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gpieces.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gpieces.setText("Gem pieces");
        jInternalFrameClientOrders.getContentPane().add(lbl_gpieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 510, -1, -1));

        lbl_certiID1.setText("Gem Certificate ID");
        jInternalFrameClientOrders.getContentPane().add(lbl_certiID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 490, -1, -1));

        lbl_gcid.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gcid.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gcid.setText("Gem ID");
        jInternalFrameClientOrders.getContentPane().add(lbl_gcid, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, -1, -1));

        lbl_gshape.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gshape.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gshape.setText("Gem shape");
        jInternalFrameClientOrders.getContentPane().add(lbl_gshape, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 430, -1, -1));

        lbl_gsprice.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gsprice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gsprice.setText("Gem price");
        jInternalFrameClientOrders.getContentPane().add(lbl_gsprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 390, -1, -1));

        txt_gsprice1.setEditable(false);
        txt_gsprice1.setBackground(new java.awt.Color(255, 255, 255));
        txt_gsprice1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gsprice1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gsprice1ActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(txt_gsprice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, 160, 40));

        txt_gshape1.setEditable(false);
        txt_gshape1.setBackground(new java.awt.Color(255, 255, 255));
        txt_gshape1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gshape1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gshape1ActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(txt_gshape1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 410, 160, 40));

        txt_cid1.setEditable(false);
        txt_cid1.setBackground(new java.awt.Color(255, 255, 255));
        txt_cid1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_cid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cid1ActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(txt_cid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 450, 160, 40));

        txt_gpieces1.setEditable(false);
        txt_gpieces1.setBackground(new java.awt.Color(255, 255, 255));
        txt_gpieces1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gpieces1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gpieces1ActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(txt_gpieces1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 490, 160, 40));

        lbl_gemImage.setText("                        Image");
        jInternalFrameClientOrders.getContentPane().add(lbl_gemImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 370, 180, 160));

        lbl_searchGemName.setBackground(new java.awt.Color(255, 255, 255));
        lbl_searchGemName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_searchGemName.setText(" Gem ID :");
        jInternalFrameClientOrders.getContentPane().add(lbl_searchGemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        txt_gid.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_gid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gid.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_gid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gidActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(txt_gid, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 170, 30));

        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 320, 130, 30));

        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Webp.net-resizeimage (2).gif"))); // NOI18N
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jInternalFrameClientOrders.getContentPane().add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, 120, 30));

        form1.add(jInternalFrameClientOrders, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameSupplierExpenses.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameSupplierExpenses.setVisible(false);
        jInternalFrameSupplierExpenses.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back3.setText("<<Back");
        btn_back3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_back3ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(btn_back3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 80, -1));

        lbl_supplersExpensesTitle.setBackground(new java.awt.Color(255, 255, 255));
        lbl_supplersExpensesTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_supplersExpensesTitle.setText("Supplier's Expenses");
        jInternalFrameSupplierExpenses.getContentPane().add(lbl_supplersExpensesTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, -1, -1));

        tbl_SupplierExpenses.setBackground(new java.awt.Color(102, 153, 255));
        tbl_SupplierExpenses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_SupplierExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_SupplierExpensesMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tbl_SupplierExpenses);

        jInternalFrameSupplierExpenses.getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 820, 190));

        lbl_supOrderInfoTitle1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_supOrderInfoTitle1.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        lbl_supOrderInfoTitle1.setText("Supplier's Information");
        jInternalFrameSupplierExpenses.getContentPane().add(lbl_supOrderInfoTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, -1));

        label_supOrderAgentNo1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderAgentNo1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderAgentNo1.setText("Suppiler Refferal Agent No ");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderAgentNo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 190, 20));

        label_supOrderSupID1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSupID1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSupID1.setText("Supplier ID                            ");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderSupID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 190, -1));

        label_supOrderSupfanme1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSupfanme1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSupfanme1.setText("First Name                            ");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderSupfanme1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 190, -1));

        label_supOrderSuplname1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSuplname1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSuplname1.setText("Last Name                             ");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderSuplname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 190, -1));

        label_supOrderSupEmail1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSupEmail1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSupEmail1.setText("Email                                      ");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderSupEmail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 190, -1));

        label_supOrderSupPhone1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderSupPhone1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderSupPhone1.setText("Contact Number                   ");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderSupPhone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        textSO_sphone1.setEditable(false);
        textSO_sphone1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_sphone1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_sphone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_sphone1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_sphone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, 120, 20));

        textSO_semail1.setEditable(false);
        textSO_semail1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_semail1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_semail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_semail1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_semail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 370, 120, 20));

        textSO_slname1.setEditable(false);
        textSO_slname1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_slname1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_slname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_slname1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_slname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 120, 20));

        textSO_sfname1.setEditable(false);
        textSO_sfname1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_sfname1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_sfname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_sfname1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_sfname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, 120, 20));

        textSO_sno1.setEditable(false);
        textSO_sno1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_sno1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_sno1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_sno1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_sno1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 310, 120, 20));

        textSO_ano1.setEditable(false);
        textSO_ano1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_ano1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_ano1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_ano1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_ano1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 290, 120, 20));

        lbl_supOrderInfotitle1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_supOrderInfotitle1.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        lbl_supOrderInfotitle1.setText("Order Information");
        jInternalFrameSupplierExpenses.getContentPane().add(lbl_supOrderInfotitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 260, -1, -1));

        label_supOrderID1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderID1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderID1.setText("Order ID");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, -1, -1));

        label_supOrderDate1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderDate1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderDate1.setText("Expenses ID");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderDate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 310, -1, -1));

        label_supOrderStatus1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderStatus1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderStatus1.setText("Supplier Expense ID");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderStatus1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 330, -1, -1));

        label_supOrderValue1.setBackground(new java.awt.Color(255, 255, 255));
        label_supOrderValue1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_supOrderValue1.setText("Request Amount");
        jInternalFrameSupplierExpenses.getContentPane().add(label_supOrderValue1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 350, -1, -1));

        textSO_sotot1.setEditable(false);
        textSO_sotot1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_sotot1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_sotot1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_sotot1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_sotot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 350, 120, 20));

        textSO_soSexid1.setEditable(false);
        textSO_soSexid1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_soSexid1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_soSexid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_soSexid1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_soSexid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 330, 120, 20));

        textSO_soexid1.setEditable(false);
        textSO_soexid1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_soexid1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_soexid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_soexid1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_soexid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 310, 120, 20));

        textSO_soid1.setEditable(false);
        textSO_soid1.setBackground(new java.awt.Color(255, 255, 255));
        textSO_soid1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textSO_soid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSO_soid1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(textSO_soid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 290, 120, 20));

        btn_SupOrderClear1.setText("Clear");
        btn_SupOrderClear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SupOrderClear1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(btn_SupOrderClear1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 460, 130, 40));

        btn_payGPex1.setText("Pay");
        btn_payGPex1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_payGPex1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(btn_payGPex1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 460, 120, 40));

        Combobox_pmethod1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cash", "BankAccount" }));
        Combobox_pmethod1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combobox_pmethod1ActionPerformed(evt);
            }
        });
        jInternalFrameSupplierExpenses.getContentPane().add(Combobox_pmethod1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 370, 120, -1));

        label_exGPpmethod1.setBackground(new java.awt.Color(255, 255, 255));
        label_exGPpmethod1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGPpmethod1.setText("Payment Method    ");
        jInternalFrameSupplierExpenses.getContentPane().add(label_exGPpmethod1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 370, 150, -1));

        form1.add(jInternalFrameSupplierExpenses, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameGemPolisherExpenses.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameGemPolisherExpenses.setVisible(false);
        jInternalFrameGemPolisherExpenses.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back4.setText("<<Back");
        btn_back4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_back4ActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(btn_back4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 80, -1));

        lbl_gname3.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gname3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gname3.setText("Gem Polisher Details");
        jInternalFrameGemPolisherExpenses.getContentPane().add(lbl_gname3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));

        tbl_EXGempolishers.setBackground(new java.awt.Color(102, 153, 255));
        tbl_EXGempolishers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_EXGempolishers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_EXGempolishersMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_EXGempolishers);

        jInternalFrameGemPolisherExpenses.getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 820, 110));

        text_exGPfname.setEditable(false);
        text_exGPfname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_exGPfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_exGPfnameActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(text_exGPfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, 180, 20));

        text_exGPlname.setEditable(false);
        text_exGPlname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_exGPlname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_exGPlnameActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(text_exGPlname, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 410, 180, 20));

        text_exGPemail.setEditable(false);
        text_exGPemail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_exGPemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_exGPemailActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(text_exGPemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 430, 180, 20));

        text_exGPphone.setEditable(false);
        text_exGPphone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_exGPphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_exGPphoneActionPerformed(evt);
            }
        });
        text_exGPphone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_exGPphoneKeyPressed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(text_exGPphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, 180, 20));

        label_exGPphone.setBackground(new java.awt.Color(255, 255, 255));
        label_exGPphone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGPphone.setText("Contact No             :");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_exGPphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, -1, -1));

        label_exGPemail.setBackground(new java.awt.Color(255, 255, 255));
        label_exGPemail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGPemail.setText("Email                       :");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_exGPemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, 150, -1));

        label_exGPlname.setBackground(new java.awt.Color(255, 255, 255));
        label_exGPlname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGPlname.setText("Last Name              :");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_exGPlname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 140, -1));

        label_exGPfname.setBackground(new java.awt.Color(255, 255, 255));
        label_exGPfname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGPfname.setText("First Name              :");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_exGPfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 140, -1));

        lbl_gname4.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gname4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gname4.setText("Gem Polisher Expenses");
        jInternalFrameGemPolisherExpenses.getContentPane().add(lbl_gname4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, -1, -1));

        tbl_EXGempolisherExpenses.setBackground(new java.awt.Color(102, 153, 255));
        tbl_EXGempolisherExpenses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_EXGempolisherExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_EXGempolisherExpensesMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_EXGempolisherExpenses);

        jInternalFrameGemPolisherExpenses.getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 820, 110));

        label_exGempolisherID.setBackground(new java.awt.Color(255, 255, 255));
        label_exGempolisherID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGempolisherID.setText("Gem polisher ID     :");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_exGempolisherID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, -1));

        text_exGPid.setEditable(false);
        text_exGPid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_exGPid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_exGPidActionPerformed(evt);
            }
        });
        text_exGPid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_exGPidKeyPressed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(text_exGPid, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, 180, 20));

        label_exGPfixedSalary.setBackground(new java.awt.Color(255, 255, 255));
        label_exGPfixedSalary.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGPfixedSalary.setText("Fixed Salary            :");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_exGPfixedSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, -1, -1));

        text_exGPfsalary.setEditable(false);
        text_exGPfsalary.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_exGPfsalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_exGPfsalaryActionPerformed(evt);
            }
        });
        text_exGPfsalary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_exGPfsalaryKeyPressed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(text_exGPfsalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 370, 180, 20));

        text_exGPbonus.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_exGPbonus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_exGPbonusActionPerformed(evt);
            }
        });
        text_exGPbonus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_exGPbonusKeyPressed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(text_exGPbonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 390, 180, 20));

        label_exGPbonus.setBackground(new java.awt.Color(255, 255, 255));
        label_exGPbonus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGPbonus.setText("Bonus Amount        :");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_exGPbonus, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 390, 140, -1));

        label_exGPpmethod.setBackground(new java.awt.Color(255, 255, 255));
        label_exGPpmethod.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGPpmethod.setText("Payment Method    :");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_exGPpmethod, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 430, 170, -1));

        text_exGPtot.setEditable(false);
        text_exGPtot.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_exGPtot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_exGPtotActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(text_exGPtot, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, 180, 20));

        btn_cleanGPex.setText("Clear");
        btn_cleanGPex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanGPexActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(btn_cleanGPex, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 510, 120, 40));

        btn_exGPsalaryCal.setText("Calculate Salary");
        btn_exGPsalaryCal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exGPsalaryCalActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(btn_exGPsalaryCal, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 450, 120, 30));

        label_exGPtot.setBackground(new java.awt.Color(255, 255, 255));
        label_exGPtot.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_exGPtot.setText("Total Salary             :");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_exGPtot, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 410, 140, -1));

        Combobox_pmethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cash", "BankAccount" }));
        jInternalFrameGemPolisherExpenses.getContentPane().add(Combobox_pmethod, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 430, 180, -1));

        btn_payGPex.setText("Pay");
        btn_payGPex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_payGPexActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(btn_payGPex, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 510, 120, 40));

        textex_gempolisherSeach.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        textex_gempolisherSeach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textex_gempolisherSeachActionPerformed(evt);
            }
        });
        textex_gempolisherSeach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textex_gempolisherSeachKeyPressed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(textex_gempolisherSeach, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 120, 30));

        label_serachgemplosher2.setBackground(new java.awt.Color(255, 255, 255));
        label_serachgemplosher2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_serachgemplosher2.setText("Expenses By GPolisher ID");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_serachgemplosher2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 170, 20));

        btn_searchGempolsherEX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Webp.net-resizeimage (2).gif"))); // NOI18N
        btn_searchGempolsherEX.setText("Search");
        btn_searchGempolsherEX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchGempolsherEXActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(btn_searchGempolsherEX, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 120, 30));

        btn_resetGemPolsiherEX.setText("Reset");
        btn_resetGemPolsiherEX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetGemPolsiherEXActionPerformed(evt);
            }
        });
        jInternalFrameGemPolisherExpenses.getContentPane().add(btn_resetGemPolsiherEX, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, 130, 30));

        label_serachgemplosher1.setBackground(new java.awt.Color(255, 255, 255));
        label_serachgemplosher1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_serachgemplosher1.setText("Search Gem Polisher ");
        jInternalFrameGemPolisherExpenses.getContentPane().add(label_serachgemplosher1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 150, 20));

        form1.add(jInternalFrameGemPolisherExpenses, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameAgentExpenses.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameAgentExpenses.setVisible(false);
        jInternalFrameAgentExpenses.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back5.setText("<<Back");
        btn_back5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_back5ActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(btn_back5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 80, -1));

        label_AEX.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX.setText("Agent ID                            ");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 190, -1));

        text_AEXaid.setEditable(false);
        text_AEXaid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXaidActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXaid, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 280, 120, 20));

        lbl_AEXsubtitle.setBackground(new java.awt.Color(255, 255, 255));
        lbl_AEXsubtitle.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        lbl_AEXsubtitle.setText("Expense Information");
        jInternalFrameAgentExpenses.getContentPane().add(lbl_AEXsubtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, -1, -1));

        tbl_AgentExpenses.setBackground(new java.awt.Color(102, 153, 255));
        tbl_AgentExpenses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_AgentExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_AgentExpensesMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tbl_AgentExpenses);

        jInternalFrameAgentExpenses.getContentPane().add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 820, 160));

        lbl_AgentExpensesTitle.setBackground(new java.awt.Color(255, 255, 255));
        lbl_AgentExpensesTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_AgentExpensesTitle.setText("Agent's Expenses");
        jInternalFrameAgentExpenses.getContentPane().add(lbl_AgentExpensesTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, -1, -1));

        label_AEX1.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX1.setText("First Name                            ");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 190, -1));

        text_AEXfname.setEditable(false);
        text_AEXfname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXfnameActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, 120, 20));

        label_AEX2.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX2.setText("Last Name                            ");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 190, -1));

        text_AEXlname.setEditable(false);
        text_AEXlname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXlname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXlnameActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXlname, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 320, 120, 20));

        text_AEXemail.setEditable(false);
        text_AEXemail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXemailActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, 120, 20));

        label_AEX3.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX3.setText("Email");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 190, -1));

        label_AEX4.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX4.setText("Contact Number");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 190, -1));

        text_AEXphone.setEditable(false);
        text_AEXphone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXphoneActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, 120, 20));

        label_AEX5.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX5.setText("Agent Expense ID");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 280, 190, -1));

        text_AEXaexid.setEditable(false);
        text_AEXaexid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXaexid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXaexidActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXaexid, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 280, 120, 20));

        lbl_AEXsubtitle1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_AEXsubtitle1.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        lbl_AEXsubtitle1.setText("Agent's Information");
        jInternalFrameAgentExpenses.getContentPane().add(lbl_AEXsubtitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));

        label_AEX6.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX6.setText("Expense ID                            ");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX6, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 190, -1));

        text_AEXexid.setEditable(false);
        text_AEXexid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXexid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXexidActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXexid, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 300, 120, 20));

        label_AEX7.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX7.setText("Supplier Order ID");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 190, -1));

        text_AEXsoid.setEditable(false);
        text_AEXsoid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXsoid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXsoidActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXsoid, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, 120, 20));

        label_AEX8.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX8.setText("Client Order ID");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX8, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 340, 190, -1));

        text_AEXcoid.setEditable(false);
        text_AEXcoid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXcoid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXcoidActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXcoid, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 340, 120, 20));

        label_AEX9.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX9.setText("Payment Method");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX9, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 420, 190, -1));

        text_AEXoamount.setEditable(false);
        text_AEXoamount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXoamount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXoamountActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXoamount, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 360, 120, 20));

        label_AEX10.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX10.setText("Order Amount                    ");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX10, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 360, 190, -1));

        Combobox_pmethod2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cash", "BankAccount" }));
        Combobox_pmethod2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combobox_pmethod2ActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(Combobox_pmethod2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 420, 120, -1));

        btn_SupOrderClear2.setText("Clear");
        btn_SupOrderClear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SupOrderClear2ActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(btn_SupOrderClear2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 500, 130, 40));

        btn_payGPex2.setText("Pay");
        btn_payGPex2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_payGPex2ActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(btn_payGPex2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 500, 120, 40));

        label_AEX11.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX11.setText("%");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX11, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 380, 20, -1));

        text_AEXrate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXrate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXrateActionPerformed(evt);
            }
        });
        text_AEXrate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_AEXrateKeyPressed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXrate, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 380, 120, 20));

        label_AEX12.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX12.setText("Commission Amount");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX12, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, 190, -1));

        text_AEXtot.setEditable(false);
        text_AEXtot.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_AEXtot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_AEXtotActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(text_AEXtot, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 400, 120, 20));

        btn_exGPsalaryCal1.setText("Calculate Salary");
        btn_exGPsalaryCal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exGPsalaryCal1ActionPerformed(evt);
            }
        });
        jInternalFrameAgentExpenses.getContentPane().add(btn_exGPsalaryCal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 440, 120, 30));

        label_AEX13.setBackground(new java.awt.Color(255, 255, 255));
        label_AEX13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_AEX13.setText("Commission Rate");
        jInternalFrameAgentExpenses.getContentPane().add(label_AEX13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 380, 190, -1));

        form1.add(jInternalFrameAgentExpenses, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameJobsShow.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameJobsShow.setVisible(false);
        jInternalFrameJobsShow.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back6.setText("<<Back");
        btn_back6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_back6ActionPerformed(evt);
            }
        });
        jInternalFrameJobsShow.getContentPane().add(btn_back6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 80, -1));

        tbl_jobsGemPolisher.setBackground(new java.awt.Color(102, 153, 255));
        tbl_jobsGemPolisher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_jobsGemPolisher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_jobsGemPolisherMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_jobsGemPolisher);

        jInternalFrameJobsShow.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 720, 220));

        lbl_rawfinishedGems1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rawfinishedGems1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_rawfinishedGems1.setText("Gem Polisher Jobs");
        jInternalFrameJobsShow.getContentPane().add(lbl_rawfinishedGems1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, -1, -1));

        btn_showAssignedJobs.setText("Show Assigned Gems");
        btn_showAssignedJobs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showAssignedJobsActionPerformed(evt);
            }
        });
        jInternalFrameJobsShow.getContentPane().add(btn_showAssignedJobs, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 450, 180, 40));

        btn_showAllJobs.setText("Show All Jobs");
        btn_showAllJobs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showAllJobsActionPerformed(evt);
            }
        });
        jInternalFrameJobsShow.getContentPane().add(btn_showAllJobs, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 180, 40));

        btn_showCompletedJobs.setText("Show Completed Jobs");
        btn_showCompletedJobs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showCompletedJobsActionPerformed(evt);
            }
        });
        jInternalFrameJobsShow.getContentPane().add(btn_showCompletedJobs, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 450, 180, 40));

        form1.add(jInternalFrameJobsShow, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameManageRawGems.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageRawGems.setVisible(false);
        jInternalFrameManageRawGems.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ManageRawGem.setForeground(new java.awt.Color(0, 0, 0));
        btn_ManageRawGem.setText("<<Back");
        btn_ManageRawGem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ManageRawGemActionPerformed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(btn_ManageRawGem, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, -1));

        lbl_rg_gname.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gname.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gname.setText("Gem Name");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        txt_rg_name.setBackground(new java.awt.Color(255, 255, 255));
        txt_rg_name.setForeground(new java.awt.Color(0, 0, 0));
        txt_rg_name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_rg_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rg_nameActionPerformed(evt);
            }
        });
        txt_rg_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rg_nameKeyPressed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(txt_rg_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 160, 30));

        lbl_rg_gtype.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gtype.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gtype.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gtype.setText("Gem Type");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, -1, -1));

        txt_rg_type.setBackground(new java.awt.Color(255, 255, 255));
        txt_rg_type.setForeground(new java.awt.Color(0, 0, 0));
        txt_rg_type.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_rg_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rg_typeActionPerformed(evt);
            }
        });
        txt_rg_type.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rg_typeKeyPressed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(txt_rg_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 160, 30));

        txt_rg_color.setBackground(new java.awt.Color(255, 255, 255));
        txt_rg_color.setForeground(new java.awt.Color(0, 0, 0));
        txt_rg_color.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_rg_color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rg_colorActionPerformed(evt);
            }
        });
        txt_rg_color.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rg_colorKeyPressed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(txt_rg_color, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 160, 30));

        txt_rg_weight.setBackground(new java.awt.Color(255, 255, 255));
        txt_rg_weight.setForeground(new java.awt.Color(0, 0, 0));
        txt_rg_weight.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_rg_weight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rg_weightActionPerformed(evt);
            }
        });
        txt_rg_weight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rg_weightKeyPressed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(txt_rg_weight, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 160, 30));

        txt_rg_buyPrice.setBackground(new java.awt.Color(255, 255, 255));
        txt_rg_buyPrice.setForeground(new java.awt.Color(0, 0, 0));
        txt_rg_buyPrice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_rg_buyPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rg_buyPriceActionPerformed(evt);
            }
        });
        txt_rg_buyPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rg_buyPriceKeyPressed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(txt_rg_buyPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 160, 30));

        lbl_rg_gBuyPrice.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gBuyPrice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gBuyPrice.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gBuyPrice.setText("Buy Price");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gBuyPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        lbl_rg_carats.setText("Carats");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_carats, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 40, 20));

        lbl_rg_gweight.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gweight.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gweight.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gweight.setText("Gem Weight");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, -1, -1));

        lbl_rg_gcolor.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gcolor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gcolor.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gcolor.setText("Gem Color");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        lbl_rawgemimage.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_rawgemimage.setForeground(new java.awt.Color(204, 204, 255));
        lbl_rawgemimage.setText("Gem Image");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rawgemimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 340, 150, 120));

        btn_rg_imageUpload.setForeground(new java.awt.Color(0, 0, 0));
        btn_rg_imageUpload.setText("Upload");
        btn_rg_imageUpload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_rg_imageUploadMouseClicked(evt);
            }
        });
        btn_rg_imageUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rg_imageUploadActionPerformed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(btn_rg_imageUpload, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 410, 80, 30));

        lbl_rg_gimage.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gimage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gimage.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gimage.setText("Gem Image");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 390, -1, -1));

        txt_rg_origin.setBackground(new java.awt.Color(255, 255, 255));
        txt_rg_origin.setForeground(new java.awt.Color(0, 0, 0));
        txt_rg_origin.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_rg_origin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rg_originActionPerformed(evt);
            }
        });
        txt_rg_origin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rg_originKeyPressed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(txt_rg_origin, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 160, 30));

        lbl_rg_gOrigin.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gOrigin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gOrigin.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gOrigin.setText("Gem Origin");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gOrigin, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        lbl_rg_gStatus.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gStatus.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gStatus.setText("Gem Status");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, -1, -1));

        txt_rg_Status.setBackground(new java.awt.Color(255, 255, 255));
        txt_rg_Status.setForeground(new java.awt.Color(0, 0, 0));
        txt_rg_Status.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_rg_Status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rg_StatusActionPerformed(evt);
            }
        });
        txt_rg_Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rg_StatusKeyPressed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(txt_rg_Status, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 280, 160, 30));

        lbl_rg_gNature.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gNature.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gNature.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gNature.setText("Gem Nature");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gNature, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, -1, -1));

        txt_rg_nature.setBackground(new java.awt.Color(255, 255, 255));
        txt_rg_nature.setForeground(new java.awt.Color(0, 0, 0));
        txt_rg_nature.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_rg_nature.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rg_natureActionPerformed(evt);
            }
        });
        txt_rg_nature.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rg_natureKeyPressed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(txt_rg_nature, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 160, 30));

        txt_rg_sellPrice.setBackground(new java.awt.Color(255, 255, 255));
        txt_rg_sellPrice.setForeground(new java.awt.Color(0, 0, 0));
        txt_rg_sellPrice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_rg_sellPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rg_sellPriceActionPerformed(evt);
            }
        });
        txt_rg_sellPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_rg_sellPriceKeyPressed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(txt_rg_sellPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 310, 160, 30));

        lbl_rg_gSellingPrice.setBackground(new java.awt.Color(255, 255, 255));
        lbl_rg_gSellingPrice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_rg_gSellingPrice.setForeground(new java.awt.Color(0, 0, 0));
        lbl_rg_gSellingPrice.setText("Selling Price");
        jInternalFrameManageRawGems.getContentPane().add(lbl_rg_gSellingPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 320, -1, -1));

        myButton1.setForeground(new java.awt.Color(0, 0, 0));
        myButton1.setText("CLEAR");
        myButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton1ActionPerformed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(myButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 510, 110, 30));

        btn_addRawGem.setForeground(new java.awt.Color(0, 0, 0));
        btn_addRawGem.setText("ADD");
        btn_addRawGem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addRawGemActionPerformed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(btn_addRawGem, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 510, 110, 30));

        btn_rawGemUpdate.setForeground(new java.awt.Color(0, 0, 0));
        btn_rawGemUpdate.setText("UPDATE");
        btn_rawGemUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rawGemUpdateActionPerformed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(btn_rawGemUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 510, 110, 30));

        btn_deleteRawGem.setForeground(new java.awt.Color(0, 0, 0));
        btn_deleteRawGem.setText("DELETE");
        btn_deleteRawGem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteRawGemActionPerformed(evt);
            }
        });
        jInternalFrameManageRawGems.getContentPane().add(btn_deleteRawGem, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 510, 110, 30));

        tble_ManageRawGem.setBackground(new java.awt.Color(102, 153, 255));
        tble_ManageRawGem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tble_ManageRawGem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tble_ManageRawGemMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tble_ManageRawGem);

        jInternalFrameManageRawGems.getContentPane().add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 770, 190));

        lbl_ManageRawGemTitle.setBackground(new java.awt.Color(255, 255, 255));
        lbl_ManageRawGemTitle.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        lbl_ManageRawGemTitle.setForeground(new java.awt.Color(0, 0, 0));
        lbl_ManageRawGemTitle.setText("Raw Gems");
        jInternalFrameManageRawGems.getContentPane().add(lbl_ManageRawGemTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        form1.add(jInternalFrameManageRawGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 880, 600));

        jInternalFrameManageFinishedGems.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageFinishedGems.setVisible(false);
        jInternalFrameManageFinishedGems.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back8.setForeground(new java.awt.Color(0, 0, 0));
        btn_back8.setText("<<Back");
        btn_back8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_back8ActionPerformed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(btn_back8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 80, -1));

        tble_ManageFinishedGems.setBackground(new java.awt.Color(102, 153, 255));
        tble_ManageFinishedGems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tble_ManageFinishedGems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tble_ManageFinishedGemsMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tble_ManageFinishedGems);

        jInternalFrameManageFinishedGems.getContentPane().add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 770, 190));

        lbl_ManageRawGemTitle1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_ManageRawGemTitle1.setFont(new java.awt.Font("Segoe UI", 3, 16)); // NOI18N
        lbl_ManageRawGemTitle1.setForeground(new java.awt.Color(0, 0, 0));
        lbl_ManageRawGemTitle1.setText("Finished Gems");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_ManageRawGemTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        lbl_finishedgemimage.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_finishedgemimage.setForeground(new java.awt.Color(204, 204, 255));
        lbl_finishedgemimage.setText("Gem Image");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_finishedgemimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 380, 150, 120));

        btn_fg_imageUpload.setForeground(new java.awt.Color(0, 0, 0));
        btn_fg_imageUpload.setText("Upload");
        btn_fg_imageUpload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_fg_imageUploadMouseClicked(evt);
            }
        });
        btn_fg_imageUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_fg_imageUploadActionPerformed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(btn_fg_imageUpload, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 450, 80, 30));

        lbl_fg_gimage.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_gimage.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_gimage.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_gimage.setText("Gem Image");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_gimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 430, -1, -1));

        lbl_fg_pieces.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_pieces.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_pieces.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_pieces.setText("Gem Pieces");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_pieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, -1, -1));

        lbl_fg_status.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_status.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_status.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_status.setText("Gem Status");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 320, -1, -1));

        txt_fg_status.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_status.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_status.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_statusActionPerformed(evt);
            }
        });
        txt_fg_status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_statusKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 310, 160, 30));

        txt_fg_pieces.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_pieces.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_pieces.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_pieces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_piecesActionPerformed(evt);
            }
        });
        txt_fg_pieces.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_piecesKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_pieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 280, 160, 30));

        btn_clearFinishedGem.setForeground(new java.awt.Color(0, 0, 0));
        btn_clearFinishedGem.setText("CLEAR");
        btn_clearFinishedGem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearFinishedGemActionPerformed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(btn_clearFinishedGem, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 510, 110, 30));

        btn_deleteFinishedGem.setForeground(new java.awt.Color(0, 0, 0));
        btn_deleteFinishedGem.setText("DELETE");
        btn_deleteFinishedGem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteFinishedGemActionPerformed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(btn_deleteFinishedGem, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 510, 110, 30));

        btn_FinishedGemUpdate.setForeground(new java.awt.Color(0, 0, 0));
        btn_FinishedGemUpdate.setText("UPDATE");
        btn_FinishedGemUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FinishedGemUpdateActionPerformed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(btn_FinishedGemUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 510, 110, 30));

        btn_addFinishedGem.setForeground(new java.awt.Color(0, 0, 0));
        btn_addFinishedGem.setText("ADD");
        btn_addFinishedGem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addFinishedGemActionPerformed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(btn_addFinishedGem, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 510, 110, 30));

        lbl_fg_shape.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_shape.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_shape.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_shape.setText("Gem Shape");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_shape, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, -1, -1));

        lbl_fg_licenseID.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_licenseID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_licenseID.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_licenseID.setText("Gem License ID");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_licenseID, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        lbl_fg_buyprice.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_buyprice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_buyprice.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_buyprice.setText("Buy Price");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_buyprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        lbl_fg_carats.setText("Carats");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_carats, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 40, 20));

        lbl_fg_weight.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_weight.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_weight.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_weight.setText("Gem Weight");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_weight, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, -1, -1));

        lbl_fg_color.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_color.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_color.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_color.setText("Gem Color");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_color, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        lbl_fg_type.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_type.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_type.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_type.setText("Gem Type");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, -1, -1));

        lbl_fg_gname.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_gname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_gname.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_gname.setText("Gem Name");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        txt_fg_name.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_name.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_nameActionPerformed(evt);
            }
        });
        txt_fg_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_nameKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 160, 30));

        txt_fg_type.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_type.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_type.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_typeActionPerformed(evt);
            }
        });
        txt_fg_type.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_typeKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 160, 30));

        txt_fg_color.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_color.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_color.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_colorActionPerformed(evt);
            }
        });
        txt_fg_color.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_colorKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_color, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 160, 30));

        txt_fg_weight.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_weight.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_weight.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_weight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_weightActionPerformed(evt);
            }
        });
        txt_fg_weight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_weightKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_weight, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 160, 30));

        txt_fg_buyprice.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_buyprice.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_buyprice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_buyprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_buypriceActionPerformed(evt);
            }
        });
        txt_fg_buyprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_buypriceKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_buyprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 160, 30));

        txt_fg_LicenseID.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_LicenseID.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_LicenseID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_LicenseID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_LicenseIDActionPerformed(evt);
            }
        });
        txt_fg_LicenseID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_LicenseIDKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_LicenseID, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 160, 30));

        txt_fg_shape.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_shape.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_shape.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_shape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_shapeActionPerformed(evt);
            }
        });
        txt_fg_shape.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_shapeKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_shape, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 450, 160, 30));

        lbl_fg_sellprice.setBackground(new java.awt.Color(255, 255, 255));
        lbl_fg_sellprice.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_fg_sellprice.setForeground(new java.awt.Color(0, 0, 0));
        lbl_fg_sellprice.setText("Selling Price");
        jInternalFrameManageFinishedGems.getContentPane().add(lbl_fg_sellprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, -1, -1));

        txt_fg_sellprice.setBackground(new java.awt.Color(255, 255, 255));
        txt_fg_sellprice.setForeground(new java.awt.Color(0, 0, 0));
        txt_fg_sellprice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_fg_sellprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fg_sellpriceActionPerformed(evt);
            }
        });
        txt_fg_sellprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_fg_sellpriceKeyPressed(evt);
            }
        });
        jInternalFrameManageFinishedGems.getContentPane().add(txt_fg_sellprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 340, 160, 30));

        form1.add(jInternalFrameManageFinishedGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 880, 600));

        jInternalFrameReports1.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameReports1.setVisible(false);
        jInternalFrameReports1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLoad1.setBackground(new java.awt.Color(255, 255, 255));
        panelLoad1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelLoad1Layout = new javax.swing.GroupLayout(panelLoad1);
        panelLoad1.setLayout(panelLoad1Layout);
        panelLoad1Layout.setHorizontalGroup(
            panelLoad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLoad1Layout.setVerticalGroup(
            panelLoad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jInternalFrameReports1.getContentPane().add(panelLoad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 820, 500));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("To :");
        jInternalFrameReports1.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, 30));

        cmbReportType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Income Report", "All Expenses Report", "Supplier Expenses Report", "Agent Expenses Report", "Gempolisher Expenses Report" }));
        cmbReportType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbReportTypeActionPerformed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(cmbReportType, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 140, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Report Type :");
        jInternalFrameReports1.getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Date From :");
        jInternalFrameReports1.getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, 30));

        btnBack6.setText("Back");
        btnBack6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack6ActionPerformed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(btnBack6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 80, 30));

        btnGenerate.setText("Generate");
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(btnGenerate, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 80, 30));

        datechooser_to.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_toAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_to.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_toMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_toMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_toMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_toMousePressed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(datechooser_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 120, 30));

        datechooser_from.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_fromAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_from.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_fromMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_fromMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_fromMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_fromMousePressed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(datechooser_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 120, 30));

        form1.add(jInternalFrameReports1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, 880, 610));

        jInternalFrameInvoice.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameInvoice.setBorder(null);
        jInternalFrameInvoice.setVisible(false);
        jInternalFrameInvoice.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLoad2.setBackground(new java.awt.Color(255, 255, 255));
        panelLoad2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelLoad2Layout = new javax.swing.GroupLayout(panelLoad2);
        panelLoad2.setLayout(panelLoad2Layout);
        panelLoad2Layout.setHorizontalGroup(
            panelLoad2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 814, Short.MAX_VALUE)
        );
        panelLoad2Layout.setVerticalGroup(
            panelLoad2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 484, Short.MAX_VALUE)
        );

        jInternalFrameInvoice.getContentPane().add(panelLoad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 820, 490));

        btnDone2.setText("Done");
        btnDone2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDone2ActionPerformed(evt);
            }
        });
        jInternalFrameInvoice.getContentPane().add(btnDone2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 520, 90, 40));

        form1.add(jInternalFrameInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        jInternalFrameManageSuppliers.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageSuppliers.setVisible(false);
        jInternalFrameManageSuppliers.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack.setText("Back");
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackMouseClicked(evt);
            }
        });
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, 30));

        label_ufname1.setBackground(new java.awt.Color(46, 43, 43));
        label_ufname1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname1.setText("Manage Suppliers");
        jInternalFrameManageSuppliers.getContentPane().add(label_ufname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 170, 50));

        tbl_supplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_supplierMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tbl_supplier);

        jInternalFrameManageSuppliers.getContentPane().add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 830, 160));

        label_ufname2.setBackground(new java.awt.Color(46, 43, 43));
        label_ufname2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname2.setText("First Name     :");
        jInternalFrameManageSuppliers.getContentPane().add(label_ufname2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 130, -1));

        label_ulname1.setBackground(new java.awt.Color(46, 43, 43));
        label_ulname1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ulname1.setText("Last Name     :");
        jInternalFrameManageSuppliers.getContentPane().add(label_ulname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 120, -1));

        label_uaddress.setBackground(new java.awt.Color(46, 43, 43));
        label_uaddress.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uaddress.setText("Address         :");
        jInternalFrameManageSuppliers.getContentPane().add(label_uaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 130, -1));

        label_udob.setBackground(new java.awt.Color(46, 43, 43));
        label_udob.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_udob.setText("Date of Birth :");
        jInternalFrameManageSuppliers.getContentPane().add(label_udob, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 150, -1));

        label_unic.setBackground(new java.awt.Color(46, 43, 43));
        label_unic.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_unic.setText("NIC Number  :");
        jInternalFrameManageSuppliers.getContentPane().add(label_unic, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

        label_uemail1.setBackground(new java.awt.Color(46, 43, 43));
        label_uemail1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uemail1.setText("Email              :");
        jInternalFrameManageSuppliers.getContentPane().add(label_uemail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        label_uphone1.setBackground(new java.awt.Color(46, 43, 43));
        label_uphone1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uphone1.setText("Contact Number :");
        jInternalFrameManageSuppliers.getContentPane().add(label_uphone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, -1, 30));

        label_upwd.setBackground(new java.awt.Color(46, 43, 43));
        label_upwd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_upwd.setText("Password             :");
        jInternalFrameManageSuppliers.getContentPane().add(label_upwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, 150, -1));

        label_ulno.setBackground(new java.awt.Color(46, 43, 43));
        label_ulno.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ulno.setText("License Number :");
        jInternalFrameManageSuppliers.getContentPane().add(label_ulno, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 360, -1, -1));

        label_ubacc.setBackground(new java.awt.Color(46, 43, 43));
        label_ubacc.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ubacc.setText("Bank Number     :");
        jInternalFrameManageSuppliers.getContentPane().add(label_ubacc, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 320, -1, -1));

        btnAdd.setText("ADD");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, -1, 30));

        btnUpdate.setText("UPDATE");
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, -1, 30));

        btndelete.setText("DELETE");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 520, -1, 30));

        btnClear.setText("CLEAR");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 520, -1, 30));

        text_ufname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ufname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ufnameActionPerformed(evt);
            }
        });
        text_ufname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ufnameKeyPressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_ufname, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 240, 30));

        text_ulname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ulname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ulnameActionPerformed(evt);
            }
        });
        text_ulname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ulnameKeyPressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_ulname, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 240, 30));

        text_uadd1.setText("Address Line 1");
        text_uadd1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd1MouseClicked(evt);
            }
        });
        text_uadd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd1ActionPerformed(evt);
            }
        });
        text_uadd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd1KeyPressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_uadd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 450, 190, 30));

        datechooser_udob.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_udobAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_udob.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_udobMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_udobMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_udobMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_udobMousePressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(datechooser_udob, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 240, 30));

        text_unic.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_unic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_unicActionPerformed(evt);
            }
        });
        text_unic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_unicKeyPressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_unic, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, 240, 30));

        text_uemail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uemailActionPerformed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_uemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 240, 30));

        text_uadd2.setText("Address Line 2");
        text_uadd2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd2MouseClicked(evt);
            }
        });
        text_uadd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd2ActionPerformed(evt);
            }
        });
        text_uadd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd2KeyPressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_uadd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 450, 200, 30));

        text_uadd3.setText("Address Line 3");
        text_uadd3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd3MouseClicked(evt);
            }
        });
        text_uadd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd3ActionPerformed(evt);
            }
        });
        text_uadd3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd3KeyPressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_uadd3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 450, 190, 30));

        text_upwd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_upwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_upwdActionPerformed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_upwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 270, 240, 30));

        text_uphone.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uphoneActionPerformed(evt);
            }
        });
        text_uphone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uphoneKeyPressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_uphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 230, 240, 30));

        text_ulno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ulno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ulnoActionPerformed(evt);
            }
        });
        text_ulno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ulnoKeyPressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_ulno, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 350, 240, 30));

        text_ubacc.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ubacc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ubaccActionPerformed(evt);
            }
        });
        text_ubacc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ubaccKeyPressed(evt);
            }
        });
        jInternalFrameManageSuppliers.getContentPane().add(text_ubacc, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 310, 240, 30));

        form1.add(jInternalFrameManageSuppliers, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameManageClients.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageClients.setVisible(false);
        jInternalFrameManageClients.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack1.setText("Back");
        btnBack1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBack1MouseClicked(evt);
            }
        });
        btnBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack1ActionPerformed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(btnBack1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, 30));

        tbl_client.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_client.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_clientMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tbl_client);

        jInternalFrameManageClients.getContentPane().add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 820, 160));

        label_ufname3.setBackground(new java.awt.Color(46, 43, 43));
        label_ufname3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname3.setText("Manage Clients");
        jInternalFrameManageClients.getContentPane().add(label_ufname3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 170, 50));

        label_ufname4.setBackground(new java.awt.Color(46, 43, 43));
        label_ufname4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname4.setText("First Name     :");
        jInternalFrameManageClients.getContentPane().add(label_ufname4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 130, -1));

        label_ulname2.setBackground(new java.awt.Color(46, 43, 43));
        label_ulname2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ulname2.setText("Last Name     :");
        jInternalFrameManageClients.getContentPane().add(label_ulname2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 120, -1));

        label_uaddress1.setBackground(new java.awt.Color(46, 43, 43));
        label_uaddress1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uaddress1.setText("Address         :");
        jInternalFrameManageClients.getContentPane().add(label_uaddress1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, 130, -1));

        label_udob1.setBackground(new java.awt.Color(46, 43, 43));
        label_udob1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_udob1.setText("Date of Birth :");
        jInternalFrameManageClients.getContentPane().add(label_udob1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 150, 50));

        label_unic1.setBackground(new java.awt.Color(46, 43, 43));
        label_unic1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_unic1.setText("NIC Number  :");
        jInternalFrameManageClients.getContentPane().add(label_unic1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, -1, -1));

        label_uemail2.setBackground(new java.awt.Color(46, 43, 43));
        label_uemail2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uemail2.setText("Email              :");
        jInternalFrameManageClients.getContentPane().add(label_uemail2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        label_uphone2.setBackground(new java.awt.Color(46, 43, 43));
        label_uphone2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uphone2.setText("Contact Number:");
        jInternalFrameManageClients.getContentPane().add(label_uphone2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, -1, -1));

        label_upwd1.setBackground(new java.awt.Color(46, 43, 43));
        label_upwd1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_upwd1.setText("Password            :");
        jInternalFrameManageClients.getContentPane().add(label_upwd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, -1, -1));

        label_uregion.setBackground(new java.awt.Color(46, 43, 43));
        label_uregion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uregion.setText("Region                :");
        jInternalFrameManageClients.getContentPane().add(label_uregion, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 320, -1, -1));

        btnAdd1.setText("ADD");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(btnAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, -1, 30));

        btnupdate1.setText("UPDATE");
        btnupdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdate1ActionPerformed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(btnupdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, -1, 30));

        btndelete1.setText("DELETE");
        btndelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelete1ActionPerformed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(btndelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 520, -1, 30));

        btnClear1.setText("CLEAR");
        btnClear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear1ActionPerformed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(btnClear1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 520, -1, 30));

        text_ufname1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ufname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ufname1ActionPerformed(evt);
            }
        });
        text_ufname1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ufname1KeyPressed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(text_ufname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 230, 30));

        text_ulname1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ulname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ulname1ActionPerformed(evt);
            }
        });
        text_ulname1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ulname1KeyPressed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(text_ulname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 230, 30));

        text_uadd4.setText("Address Line 1");
        text_uadd4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd4MouseClicked(evt);
            }
        });
        text_uadd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd4ActionPerformed(evt);
            }
        });
        text_uadd4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd4KeyPressed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(text_uadd4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 460, 220, 30));

        text_uadd5.setText("Address Line 2");
        text_uadd5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd5MouseClicked(evt);
            }
        });
        text_uadd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd5ActionPerformed(evt);
            }
        });
        text_uadd5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd5KeyPressed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(text_uadd5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 460, 200, 30));

        text_uadd6.setText("Address Line 3");
        text_uadd6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd6MouseClicked(evt);
            }
        });
        text_uadd6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd6ActionPerformed(evt);
            }
        });
        text_uadd6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd6KeyPressed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(text_uadd6, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 460, 190, 30));

        text_upwd1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_upwd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_upwd1ActionPerformed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(text_upwd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 270, 250, 30));

        text_uphone1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uphone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uphone1ActionPerformed(evt);
            }
        });
        text_uphone1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uphone1KeyPressed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(text_uphone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 250, 30));

        datechooser_udob1.setBackground(new java.awt.Color(255, 255, 255));
        datechooser_udob1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_udob1AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_udob1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_udob1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_udob1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_udob1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_udob1MousePressed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(datechooser_udob1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 230, 30));

        text_unic1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_unic1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_unic1ActionPerformed(evt);
            }
        });
        text_unic1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_unic1KeyPressed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(text_unic1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 230, 40));

        text_uemail1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uemail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uemail1ActionPerformed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(text_uemail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 230, 40));

        comboURegion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboURegion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "SriLanka", "China" }));
        comboURegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboURegionActionPerformed(evt);
            }
        });
        jInternalFrameManageClients.getContentPane().add(comboURegion, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 320, 250, 30));

        form1.add(jInternalFrameManageClients, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameManageAgent.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageAgent.setVisible(false);
        jInternalFrameManageAgent.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack2.setText("Back");
        btnBack2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBack2MouseClicked(evt);
            }
        });
        btnBack2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack2ActionPerformed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(btnBack2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, 30));

        tbl_agent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_agent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_agentMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tbl_agent);

        jInternalFrameManageAgent.getContentPane().add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 820, 160));

        label_ufname5.setBackground(new java.awt.Color(46, 43, 43));
        label_ufname5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname5.setText("Manage Agents");
        jInternalFrameManageAgent.getContentPane().add(label_ufname5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 170, 50));

        label_ufname6.setBackground(new java.awt.Color(255, 255, 255));
        label_ufname6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname6.setText("First Name     :");
        jInternalFrameManageAgent.getContentPane().add(label_ufname6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 130, -1));

        label_ulname3.setBackground(new java.awt.Color(255, 255, 255));
        label_ulname3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ulname3.setText("Last Name     :");
        jInternalFrameManageAgent.getContentPane().add(label_ulname3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 120, -1));

        label_uaddress2.setBackground(new java.awt.Color(255, 255, 255));
        label_uaddress2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uaddress2.setText("Address         :");
        jInternalFrameManageAgent.getContentPane().add(label_uaddress2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, 130, -1));

        label_udob3.setBackground(new java.awt.Color(255, 255, 255));
        label_udob3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_udob3.setText("Date of Birth :");
        jInternalFrameManageAgent.getContentPane().add(label_udob3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 150, -1));

        label_unic2.setBackground(new java.awt.Color(255, 255, 255));
        label_unic2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_unic2.setText("NIC Number  :");
        jInternalFrameManageAgent.getContentPane().add(label_unic2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, -1, -1));

        label_uemail3.setBackground(new java.awt.Color(255, 255, 255));
        label_uemail3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uemail3.setText("Email              :");
        jInternalFrameManageAgent.getContentPane().add(label_uemail3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, -1, -1));

        label_ubacc1.setBackground(new java.awt.Color(255, 255, 255));
        label_ubacc1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ubacc1.setText("Work type          :");
        jInternalFrameManageAgent.getContentPane().add(label_ubacc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 420, -1, -1));

        label_udob2.setBackground(new java.awt.Color(255, 255, 255));
        label_udob2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_udob2.setText("Joined date        :");
        jInternalFrameManageAgent.getContentPane().add(label_udob2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 365, 150, 40));

        label_ubacc2.setBackground(new java.awt.Color(255, 255, 255));
        label_ubacc2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ubacc2.setText("Bank Number     :");
        jInternalFrameManageAgent.getContentPane().add(label_ubacc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 320, -1, 40));

        label_upwd2.setBackground(new java.awt.Color(255, 255, 255));
        label_upwd2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_upwd2.setText("Password            :");
        jInternalFrameManageAgent.getContentPane().add(label_upwd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, -1, -1));

        label_uphone3.setBackground(new java.awt.Color(255, 255, 255));
        label_uphone3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uphone3.setText("Contact Number:");
        jInternalFrameManageAgent.getContentPane().add(label_uphone3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 240, -1, -1));

        btnAdd2.setText("ADD");
        btnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd2ActionPerformed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(btnAdd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, -1, 30));

        myButton6.setText("UPDATE");
        myButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton6ActionPerformed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(myButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, -1, 30));

        Agentdelete.setText("DELETE");
        Agentdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgentdeleteActionPerformed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(Agentdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 520, -1, 30));

        btnClear2.setText("CLEAR");
        btnClear2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClear2MouseClicked(evt);
            }
        });
        btnClear2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear2ActionPerformed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(btnClear2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 520, -1, 30));

        text_ufname2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ufname2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ufname2ActionPerformed(evt);
            }
        });
        text_ufname2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ufname2KeyPressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_ufname2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 200, 30));

        text_ulname2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ulname2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ulname2ActionPerformed(evt);
            }
        });
        text_ulname2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ulname2KeyPressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_ulname2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 200, 30));

        text_uadd7.setText("Address Line 1");
        text_uadd7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd7MouseClicked(evt);
            }
        });
        text_uadd7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd7ActionPerformed(evt);
            }
        });
        text_uadd7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd7KeyPressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_uadd7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 470, 180, 30));

        datechooser_udob2.setBackground(new java.awt.Color(255, 255, 255));
        datechooser_udob2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_udob2AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_udob2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_udob2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_udob2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_udob2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_udob2MousePressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(datechooser_udob2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 200, 30));

        text_unic2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_unic2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_unic2ActionPerformed(evt);
            }
        });
        text_unic2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_unic2KeyPressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_unic2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 200, 30));

        text_uemail2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uemail2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uemail2ActionPerformed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_uemail2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 200, 30));

        text_uadd8.setText("Address Line 2");
        text_uadd8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd8MouseClicked(evt);
            }
        });
        text_uadd8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd8ActionPerformed(evt);
            }
        });
        text_uadd8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd8KeyPressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_uadd8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 470, 210, 30));

        text_uadd9.setText("Address Line 3");
        text_uadd9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd9MouseClicked(evt);
            }
        });
        text_uadd9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd9ActionPerformed(evt);
            }
        });
        text_uadd9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd9KeyPressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_uadd9, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 470, 200, 30));

        text_upwd2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_upwd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_upwd2ActionPerformed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_upwd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 270, 250, 30));

        text_uphone2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uphone2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uphone2ActionPerformed(evt);
            }
        });
        text_uphone2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uphone2KeyPressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_uphone2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 250, 30));

        text_ubacc1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ubacc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ubacc1ActionPerformed(evt);
            }
        });
        text_ubacc1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ubacc1KeyPressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(text_ubacc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 320, 250, 30));

        datechooser_jdate.setBackground(new java.awt.Color(255, 255, 255));
        datechooser_jdate.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_jdateAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_jdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_jdateMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_jdateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_jdateMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_jdateMousePressed(evt);
            }
        });
        jInternalFrameManageAgent.getContentPane().add(datechooser_jdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 370, 250, 30));

        cmb_worktype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Full time", "Part time", " " }));
        jInternalFrameManageAgent.getContentPane().add(cmb_worktype, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 420, 250, 30));

        form1.add(jInternalFrameManageAgent, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameManageGemPolisher.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageGemPolisher.setVisible(false);
        jInternalFrameManageGemPolisher.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack3.setText("Back");
        btnBack3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBack3MouseClicked(evt);
            }
        });
        btnBack3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack3ActionPerformed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(btnBack3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, 30));

        tbl_gempolisher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_gempolisher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_gempolisherMouseClicked(evt);
            }
        });
        jScrollPane16.setViewportView(tbl_gempolisher);

        jInternalFrameManageGemPolisher.getContentPane().add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 820, 130));

        label_ufname9.setBackground(new java.awt.Color(46, 43, 43));
        label_ufname9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname9.setText("Manage Gem Polishers");
        jInternalFrameManageGemPolisher.getContentPane().add(label_ufname9, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 210, 50));

        label_uphone5.setBackground(new java.awt.Color(255, 255, 255));
        label_uphone5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uphone5.setText("WorkingType :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_uphone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 130, 30));

        cmb_worktype2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Junior", "Senior" }));
        jInternalFrameManageGemPolisher.getContentPane().add(cmb_worktype2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 450, 200, 30));

        label_ufname7.setBackground(new java.awt.Color(255, 255, 255));
        label_ufname7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname7.setText("First Name     :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_ufname7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 130, -1));

        label_ulname4.setBackground(new java.awt.Color(255, 255, 255));
        label_ulname4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ulname4.setText("Last Name     :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_ulname4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 120, -1));

        label_uaddress3.setBackground(new java.awt.Color(255, 255, 255));
        label_uaddress3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uaddress3.setText("Address          :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_uaddress3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 130, -1));

        label_udob4.setBackground(new java.awt.Color(255, 255, 255));
        label_udob4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_udob4.setText("Date of Birth :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_udob4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 150, -1));

        label_unic3.setBackground(new java.awt.Color(255, 255, 255));
        label_unic3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_unic3.setText("NIC Number  :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_unic3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));

        label_uemail4.setBackground(new java.awt.Color(255, 255, 255));
        label_uemail4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uemail4.setText("Email              :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_uemail4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, -1, -1));

        label_ubacc3.setBackground(new java.awt.Color(255, 255, 255));
        label_ubacc3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ubacc3.setText("Joined date         :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_ubacc3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 360, -1, -1));

        label_ubacc4.setBackground(new java.awt.Color(255, 255, 255));
        label_ubacc4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ubacc4.setText("Bank Number     :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_ubacc4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 310, -1, -1));

        label_ulno1.setBackground(new java.awt.Color(255, 255, 255));
        label_ulno1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ulno1.setText("Salary                  :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_ulno1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 270, -1, -1));

        label_upwd3.setBackground(new java.awt.Color(255, 255, 255));
        label_upwd3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_upwd3.setText("Password            :");
        jInternalFrameManageGemPolisher.getContentPane().add(label_upwd3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 230, -1, -1));

        label_uphone4.setBackground(new java.awt.Color(255, 255, 255));
        label_uphone4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uphone4.setText("Contact Number:");
        jInternalFrameManageGemPolisher.getContentPane().add(label_uphone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 190, -1, -1));

        btnAddgempolisher.setText("ADD");
        btnAddgempolisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddgempolisherActionPerformed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(btnAddgempolisher, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, -1, 30));

        btnUpdategempolisher.setText("UPDATE");
        btnUpdategempolisher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdategempolisherMouseClicked(evt);
            }
        });
        btnUpdategempolisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdategempolisherActionPerformed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(btnUpdategempolisher, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, -1, 30));

        btndeletegempolisher.setText("DELETE");
        btndeletegempolisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeletegempolisherActionPerformed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(btndeletegempolisher, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 520, -1, 30));

        btnClear3.setText("CLEAR");
        btnClear3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear3ActionPerformed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(btnClear3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 520, -1, 30));

        text_ufname3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ufname3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ufname3ActionPerformed(evt);
            }
        });
        text_ufname3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ufname3KeyPressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_ufname3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 220, 30));

        text_ulname3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ulname3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ulname3ActionPerformed(evt);
            }
        });
        text_ulname3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ulname3KeyPressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_ulname3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 220, 30));

        text_uadd10.setText("Address Line 1");
        text_uadd10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd10MouseClicked(evt);
            }
        });
        text_uadd10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd10ActionPerformed(evt);
            }
        });
        text_uadd10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd10KeyPressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_uadd10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, 220, 30));

        datechooser_udob3.setBackground(new java.awt.Color(255, 255, 255));
        datechooser_udob3.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_udob3AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_udob3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_udob3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_udob3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_udob3MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_udob3MousePressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(datechooser_udob3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 220, 30));

        text_unic3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_unic3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_unic3ActionPerformed(evt);
            }
        });
        text_unic3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_unic3KeyPressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_unic3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 220, 30));

        text_uemail3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uemail3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uemail3ActionPerformed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_uemail3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, 220, 30));

        datechooser_jdate2.setBackground(new java.awt.Color(255, 255, 255));
        datechooser_jdate2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                datechooser_jdate2AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        datechooser_jdate2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datechooser_jdate2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                datechooser_jdate2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                datechooser_jdate2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                datechooser_jdate2MousePressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(datechooser_jdate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 350, 250, 30));

        text_ubacc2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ubacc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ubacc2ActionPerformed(evt);
            }
        });
        text_ubacc2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ubacc2KeyPressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_ubacc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 310, 250, 30));

        text_salary.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_salary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_salaryActionPerformed(evt);
            }
        });
        text_salary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_salaryKeyPressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_salary, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 270, 250, 30));

        text_uadd11.setText("Address Line 2");
        text_uadd11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd11MouseClicked(evt);
            }
        });
        text_uadd11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd11ActionPerformed(evt);
            }
        });
        text_uadd11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd11KeyPressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_uadd11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 140, 30));

        text_uadd12.setText("Address Line 3");
        text_uadd12.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd12MouseClicked(evt);
            }
        });
        text_uadd12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd12ActionPerformed(evt);
            }
        });
        text_uadd12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd12KeyPressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_uadd12, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 410, 250, 30));

        text_upwd3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_upwd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_upwd3ActionPerformed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_upwd3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 230, 250, 30));

        text_uphone3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uphone3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uphone3ActionPerformed(evt);
            }
        });
        text_uphone3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uphone3KeyPressed(evt);
            }
        });
        jInternalFrameManageGemPolisher.getContentPane().add(text_uphone3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 190, 250, 30));

        form1.add(jInternalFrameManageGemPolisher, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameManageOwners.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageOwners.setBorder(null);
        jInternalFrameManageOwners.setVisible(false);
        jInternalFrameManageOwners.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack4.setText("Back");
        btnBack4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBack4MouseClicked(evt);
            }
        });
        btnBack4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack4ActionPerformed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(btnBack4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, 30));

        tbl_owner.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_owner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ownerMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tbl_owner);

        jInternalFrameManageOwners.getContentPane().add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 820, 160));

        label_ufname8.setBackground(new java.awt.Color(46, 43, 43));
        label_ufname8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname8.setText("Manage Owners");
        jInternalFrameManageOwners.getContentPane().add(label_ufname8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 170, 50));

        label_ulname5.setBackground(new java.awt.Color(255, 255, 255));
        label_ulname5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ulname5.setText("Last Name     :");
        jInternalFrameManageOwners.getContentPane().add(label_ulname5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, 120, -1));

        label_uaddress4.setBackground(new java.awt.Color(255, 255, 255));
        label_uaddress4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uaddress4.setText("Address         :");
        jInternalFrameManageOwners.getContentPane().add(label_uaddress4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 440, 130, -1));

        label_unic4.setBackground(new java.awt.Color(255, 255, 255));
        label_unic4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_unic4.setText("NIC Number  :");
        jInternalFrameManageOwners.getContentPane().add(label_unic4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, -1, -1));

        label_uemail5.setBackground(new java.awt.Color(255, 255, 255));
        label_uemail5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uemail5.setText("Email              :");
        jInternalFrameManageOwners.getContentPane().add(label_uemail5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, -1, -1));

        label_uphone6.setBackground(new java.awt.Color(255, 255, 255));
        label_uphone6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uphone6.setText("Contact Number:");
        jInternalFrameManageOwners.getContentPane().add(label_uphone6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, -1, -1));

        label_upwd4.setBackground(new java.awt.Color(255, 255, 255));
        label_upwd4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_upwd4.setText("Password            :");
        jInternalFrameManageOwners.getContentPane().add(label_upwd4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, -1, -1));

        text_ufname4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ufname4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ufname4ActionPerformed(evt);
            }
        });
        text_ufname4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ufname4KeyPressed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(text_ufname4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 210, 30));

        text_ulname4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_ulname4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_ulname4ActionPerformed(evt);
            }
        });
        text_ulname4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_ulname4KeyPressed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(text_ulname4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 210, 30));

        text_uadd13.setText("Address Line 1");
        text_uadd13.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd13MouseClicked(evt);
            }
        });
        text_uadd13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd13ActionPerformed(evt);
            }
        });
        text_uadd13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd13KeyPressed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(text_uadd13, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, 160, 30));

        text_uadd14.setText("Address Line 2");
        text_uadd14.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd14MouseClicked(evt);
            }
        });
        text_uadd14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd14ActionPerformed(evt);
            }
        });
        text_uadd14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd14KeyPressed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(text_uadd14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 440, 200, 30));

        text_uadd15.setText("Address Line 3");
        text_uadd15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uadd15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                text_uadd15MouseClicked(evt);
            }
        });
        text_uadd15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uadd15ActionPerformed(evt);
            }
        });
        text_uadd15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uadd15KeyPressed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(text_uadd15, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 440, 180, 30));

        btnClear4.setText("CLEAR");
        btnClear4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClear4ActionPerformed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(btnClear4, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 520, -1, 30));

        btnAddowner.setText("ADD");
        btnAddowner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddownerActionPerformed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(btnAddowner, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, -1, 30));

        btnUpdateowner.setText("UPDATE");
        btnUpdateowner.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateownerMouseClicked(evt);
            }
        });
        btnUpdateowner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateownerActionPerformed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(btnUpdateowner, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 520, -1, 30));

        text_unic4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_unic4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_unic4ActionPerformed(evt);
            }
        });
        text_unic4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_unic4KeyPressed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(text_unic4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 210, 30));

        text_uemail4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uemail4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uemail4ActionPerformed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(text_uemail4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 210, 30));

        text_uphone4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uphone4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uphone4ActionPerformed(evt);
            }
        });
        text_uphone4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uphone4KeyPressed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(text_uphone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 240, 220, 30));

        text_upwd4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_upwd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_upwd4ActionPerformed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(text_upwd4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 300, 220, 30));

        btndeleteowner.setText("DELETE");
        btndeleteowner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteownerActionPerformed(evt);
            }
        });
        jInternalFrameManageOwners.getContentPane().add(btndeleteowner, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 520, -1, 30));

        label_ufname10.setBackground(new java.awt.Color(255, 255, 255));
        label_ufname10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname10.setText("First Name     :");
        jInternalFrameManageOwners.getContentPane().add(label_ufname10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 130, -1));

        form1.add(jInternalFrameManageOwners, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        bg.add(form1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 840, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogOutMouseClicked
        this.setVisible(false);
        SignInUp main = new SignInUp();
        main.setVisible(true);
    }//GEN-LAST:event_btnLogOutMouseClicked

    private void buttonHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseClicked
       bar(lblBar1);
       jInternalFrameHome.setVisible(true);
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGems.setVisible(false);
         btnfilter.setVisible(false);

        jInternalFrameOrderDetails.setVisible(false);
        jInternalFrameReports.setVisible(false);
        jInternalFrameOwnerrHelp.setVisible(false);
        jInternalFrameProfile.setVisible(false);
      
    }//GEN-LAST:event_buttonHomeMouseClicked

    private void buttonHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseEntered
        buttonHome.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_buttonHomeMouseEntered

    private void buttonHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseExited
        buttonHome.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_buttonHomeMouseExited

    private void btnManageUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManageUsersMouseClicked
       bar(lblBar2);
       jInternalFrameHome.setVisible(false);
       jInternalFrameManageUsers.setVisible(true);
       jInternalFrameManageGems.setVisible(false);
         btnfilter.setVisible(false);

        jInternalFrameOrderDetails.setVisible(false);
        jInternalFrameReports.setVisible(false);
        jInternalFrameOwnerrHelp.setVisible(false);
        jInternalFrameProfile.setVisible(false);
    }//GEN-LAST:event_btnManageUsersMouseClicked

    private void btnManageUsersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManageUsersMouseEntered
        btnManageUsers.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnManageUsersMouseEntered

    private void btnManageUsersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManageUsersMouseExited
         btnManageUsers.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnManageUsersMouseExited

    private void btnManageGemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManageGemsMouseClicked
        bar(lblBar3);
       jInternalFrameHome.setVisible(false);
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGems.setVisible(true);
         btnfilter.setVisible(false);

        jInternalFrameOrderDetails.setVisible(false);
        jInternalFrameReports.setVisible(false);
        jInternalFrameOwnerrHelp.setVisible(false);
        jInternalFrameProfile.setVisible(false);
    }//GEN-LAST:event_btnManageGemsMouseClicked

    private void btnManageGemsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManageGemsMouseEntered
          btnManageGems.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnManageGemsMouseEntered

    private void btnManageGemsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManageGemsMouseExited
        btnManageGems.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnManageGemsMouseExited

    private void btnOrderDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderDetailsMouseClicked
        bar(lblBar4);
        jInternalFrameHome.setVisible(false);
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGems.setVisible(false);
              btnfilter.setVisible(false);

       jInternalFrameOrderDetails.setVisible(true);
       jInternalFrameReports.setVisible(false);
       jInternalFrameOwnerrHelp.setVisible(false);
       jInternalFrameProfile.setVisible(false);
    }//GEN-LAST:event_btnOrderDetailsMouseClicked

    private void btnOrderDetailsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderDetailsMouseEntered
       btnOrderDetails.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnOrderDetailsMouseEntered

    private void btnOrderDetailsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderDetailsMouseExited
        btnOrderDetails.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnOrderDetailsMouseExited

    private void btnReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseClicked
       bar(lblBar5);
       jInternalFrameHome.setVisible(false);
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGems.setVisible(false);
       btnfilter.setVisible(true);
       jInternalFrameOrderDetails.setVisible(false);
       jInternalFrameReports.setVisible(true);
       jInternalFrameOwnerrHelp.setVisible(false);
       jInternalFrameProfile.setVisible(false);
    }//GEN-LAST:event_btnReportsMouseClicked

    private void btnReportsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseEntered
        btnReports.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnReportsMouseEntered

    private void btnReportsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseExited
         btnReports.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnReportsMouseExited

    private void btnHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseClicked
       bar(lblBar6);
       jInternalFrameHome.setVisible(false);
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGems.setVisible(false);
        btnfilter.setVisible(false);

       jInternalFrameOrderDetails.setVisible(false);
       jInternalFrameReports.setVisible(false);
       jInternalFrameOwnerrHelp.setVisible(true);
       jInternalFrameProfile.setVisible(false);
    }//GEN-LAST:event_btnHelpMouseClicked

    private void btnHelpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseEntered
        btnHelp.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnHelpMouseEntered

    private void btnHelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseExited
        btnHelp.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnHelpMouseExited

    private void btnProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileMouseClicked
       jInternalFrameHome.setVisible(false);
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGems.setVisible(false);
       
       jInternalFrameOrderDetails.setVisible(false);
       jInternalFrameReports.setVisible(false);
       jInternalFrameOwnerrHelp.setVisible(false);
       jInternalFrameProfile.setVisible(true);
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
        lblBar3.setOpaque(false);
        lblBar4.setOpaque(false);
        lblBar5.setOpaque(false);
        lblBar6.setOpaque(false);
        menu.repaint();
    }//GEN-LAST:event_btnProfileMouseClicked

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnMiniMizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMiniMizeMouseClicked
       this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMiniMizeMouseClicked

    private void btnLogOutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogOutMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogOutMouseEntered

    private void label_ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ordersMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_label_ordersMouseClicked

    private void label_ordersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ordersMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_ordersMouseEntered

    private void label_ordersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_ordersMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_ordersMouseExited

    private void btn_gemPolisherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_gemPolisherExpensesMouseClicked
              fetchJobsGemPolishersEx();
              fetchJobsGemPolishersExpenses();
              jInternalFrameOrderDetails.setVisible(false); 
              jInternalFrameGemPolisherExpenses.setVisible(true); 
    }//GEN-LAST:event_btn_gemPolisherExpensesMouseClicked

    private void btn_gemPolisherExpensesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_gemPolisherExpensesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_gemPolisherExpensesMouseEntered

    private void btn_gemPolisherExpensesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_gemPolisherExpensesMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_gemPolisherExpensesMouseExited

    private void label_expensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_expensesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_label_expensesMouseClicked

    private void label_expensesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_expensesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_expensesMouseEntered

    private void label_expensesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_expensesMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_expensesMouseExited

    private void label_jobsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_jobsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_label_jobsMouseClicked

    private void label_jobsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_jobsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_label_jobsMouseEntered

    private void label_jobsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_jobsMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_label_jobsMouseExited

    private void btn_ClientOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ClientOrdersMouseClicked
         fetchAllClientOrders();
         jInternalFrameOrderDetails.setVisible(false); 
        jInternalFrameClientOrders.setVisible(true); 
    }//GEN-LAST:event_btn_ClientOrdersMouseClicked

    private void btn_ClientOrdersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ClientOrdersMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ClientOrdersMouseEntered

    private void btn_ClientOrdersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ClientOrdersMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ClientOrdersMouseExited

    private void btn_AgentExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_AgentExpensesMouseClicked
        fetchAgentExpenses();
        jInternalFrameOrderDetails.setVisible(false); 
        jInternalFrameAgentExpenses.setVisible(true);        
    }//GEN-LAST:event_btn_AgentExpensesMouseClicked

    private void btn_AgentExpensesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_AgentExpensesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_AgentExpensesMouseEntered

    private void btn_AgentExpensesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_AgentExpensesMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_AgentExpensesMouseExited

    private void btn_SuppilerOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SuppilerOrdersMouseClicked
        fetchAllSupplierOrders();
        jInternalFrameOrderDetails.setVisible(false); 
        jInternalFrameSupplierOrders.setVisible(true); 
    }//GEN-LAST:event_btn_SuppilerOrdersMouseClicked

    private void btn_SuppilerOrdersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SuppilerOrdersMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_SuppilerOrdersMouseEntered

    private void btn_SuppilerOrdersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SuppilerOrdersMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_SuppilerOrdersMouseExited

    private void btn_SupplierExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SupplierExpensesMouseClicked
                fetchSupplierExpenses();
                jInternalFrameOrderDetails.setVisible(false); 
                jInternalFrameSupplierExpenses.setVisible(true); 
    }//GEN-LAST:event_btn_SupplierExpensesMouseClicked

    private void btn_SupplierExpensesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SupplierExpensesMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_SupplierExpensesMouseEntered

    private void btn_SupplierExpensesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_SupplierExpensesMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_SupplierExpensesMouseExited

    private void btn_GempolisherWorkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_GempolisherWorkMouseClicked
        fetchAvailableRawGems();
        fetchJobsGemPolishers();       
        jInternalFrameOrderDetails.setVisible(false); 
        jInternalFrameJobs.setVisible(true); 
    }//GEN-LAST:event_btn_GempolisherWorkMouseClicked

    private void btn_GempolisherWorkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_GempolisherWorkMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_GempolisherWorkMouseEntered

    private void btn_GempolisherWorkMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_GempolisherWorkMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_GempolisherWorkMouseExited

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        clearJobData();
        jInternalFrameJobs.setVisible(false);
        jInternalFrameOrderDetails.setVisible(true);
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_back1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_back1ActionPerformed
        clearSupplierOrder();
        jInternalFrameSupplierOrders.setVisible(false);
        jInternalFrameOrderDetails.setVisible(true);
    }//GEN-LAST:event_btn_back1ActionPerformed

    private void btn_back2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_back2ActionPerformed
        clearClientOrdersGemDetails();   
       jInternalFrameClientOrders.setVisible(false);
        jInternalFrameOrderDetails.setVisible(true);
    }//GEN-LAST:event_btn_back2ActionPerformed

    private void btn_back3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_back3ActionPerformed
        clearSupplierExpense();
        jInternalFrameSupplierExpenses.setVisible(false);
        jInternalFrameOrderDetails.setVisible(true);

    }//GEN-LAST:event_btn_back3ActionPerformed

    private void btn_back4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_back4ActionPerformed
        clearGemPolisherExpenses();   
        jInternalFrameGemPolisherExpenses.setVisible(false);
        jInternalFrameOrderDetails.setVisible(true);
    }//GEN-LAST:event_btn_back4ActionPerformed

    private void btn_back5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_back5ActionPerformed
            clearAgentExpense();
            jInternalFrameAgentExpenses.setVisible(false);
            jInternalFrameOrderDetails.setVisible(true);
    }//GEN-LAST:event_btn_back5ActionPerformed

    private void tbl_availaleRGemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_availaleRGemsMouseClicked
           fetchClickAvailablerawGems();       
    }//GEN-LAST:event_tbl_availaleRGemsMouseClicked

    private void txt_gnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gnameActionPerformed

    private void txt_gtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gtypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gtypeActionPerformed

    private void txt_gcolorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gcolorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gcolorActionPerformed

    private void txt_gweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gweightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gweightActionPerformed

    private void tbl_jGempolishersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_jGempolishersMouseClicked
        fetchClickJobGemPolisher();
    }//GEN-LAST:event_tbl_jGempolishersMouseClicked

    private void txt_gOriginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gOriginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gOriginActionPerformed

    private void txt_gnatureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gnatureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gnatureActionPerformed

    private void text_ugfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ugfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ugfnameActionPerformed

    private void text_uglnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uglnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uglnameActionPerformed

    private void text_ugphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ugphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ugphoneActionPerformed

    private void text_ugphoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ugphoneKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c))
        {
            JOptionPane.showMessageDialog(this,"This Field must have Numbers");
            text_ugphone.setText("");
        }
    }//GEN-LAST:event_text_ugphoneKeyPressed

    private void text_ugemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ugemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ugemailActionPerformed

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton2ActionPerformed
        setJobData();
    }//GEN-LAST:event_myButton2ActionPerformed

    private void btn_showGJobsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showGJobsActionPerformed
        fetchAllJobs();
        jInternalFrameJobs.setVisible(false);
        jInternalFrameJobsShow.setVisible(true);
    }//GEN-LAST:event_btn_showGJobsActionPerformed

    private void btn_gjobClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gjobClearActionPerformed
        clearJobData();
    }//GEN-LAST:event_btn_gjobClearActionPerformed

    private void btn_back6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_back6ActionPerformed
   jInternalFrameJobsShow.setVisible(false);      
 jInternalFrameJobs.setVisible(true);
       
    }//GEN-LAST:event_btn_back6ActionPerformed

    private void tbl_jobsGemPolisherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_jobsGemPolisherMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_jobsGemPolisherMouseClicked

    private void btn_showAllJobsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showAllJobsActionPerformed
        fetchAllJobs();
    }//GEN-LAST:event_btn_showAllJobsActionPerformed

    private void btn_showCompletedJobsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showCompletedJobsActionPerformed
        fetchCompletedJobs();
    }//GEN-LAST:event_btn_showCompletedJobsActionPerformed

    private void btn_showAssignedJobsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showAssignedJobsActionPerformed
        fetchAssignedJobs();
    }//GEN-LAST:event_btn_showAssignedJobsActionPerformed

    private void tbl_EXGempolishersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_EXGempolishersMouseClicked
       fetchClickJobGemPolisherEx();
    }//GEN-LAST:event_tbl_EXGempolishersMouseClicked

    private void text_exGPfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_exGPfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPfnameActionPerformed

    private void text_exGPlnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_exGPlnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPlnameActionPerformed

    private void text_exGPemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_exGPemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPemailActionPerformed

    private void text_exGPphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_exGPphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPphoneActionPerformed

    private void text_exGPphoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_exGPphoneKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPphoneKeyPressed

    private void tbl_EXGempolisherExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_EXGempolisherExpensesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_EXGempolisherExpensesMouseClicked

    private void text_exGPidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_exGPidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPidActionPerformed

    private void text_exGPidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_exGPidKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPidKeyPressed

    private void text_exGPfsalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_exGPfsalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPfsalaryActionPerformed

    private void text_exGPfsalaryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_exGPfsalaryKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPfsalaryKeyPressed

    private void text_exGPbonusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_exGPbonusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPbonusActionPerformed

    private void text_exGPtotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_exGPtotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_exGPtotActionPerformed

    private void btn_exGPsalaryCalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exGPsalaryCalActionPerformed
        calGemPolisherSalary();        
    }//GEN-LAST:event_btn_exGPsalaryCalActionPerformed

    private void textex_gempolisherSeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textex_gempolisherSeachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textex_gempolisherSeachActionPerformed

    private void textex_gempolisherSeachKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textex_gempolisherSeachKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_textex_gempolisherSeachKeyPressed

    private void btn_searchGempolsherEXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchGempolsherEXActionPerformed
        
        
        if(textex_gempolisherSeach.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Please Enter Gem polisher ID First!");
        }else{

            searchGempolisherEx();

        }
    }//GEN-LAST:event_btn_searchGempolsherEXActionPerformed

    private void btn_resetGemPolsiherEXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetGemPolsiherEXActionPerformed
       clearGemPolisherExpenses();
       fetchJobsGemPolishersExpenses();
       
    }//GEN-LAST:event_btn_resetGemPolsiherEXActionPerformed

    private void tbl_operationClientOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_operationClientOrdersMouseClicked
         fetchClickClientOrders();        
    }//GEN-LAST:event_tbl_operationClientOrdersMouseClicked

    private void txt_gname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gname1ActionPerformed

    private void txt_gtype1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gtype1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gtype1ActionPerformed

    private void txt_gcolor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gcolor1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gcolor1ActionPerformed

    private void txt_gweight1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gweight1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gweight1ActionPerformed

    private void txt_gsprice1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gsprice1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gsprice1ActionPerformed

    private void txt_gshape1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gshape1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gshape1ActionPerformed

    private void txt_cid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cid1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cid1ActionPerformed

    private void txt_gpieces1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gpieces1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gpieces1ActionPerformed

    private void txt_gidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gidActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        clearClientOrdersGemDetails();
        
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed

    fetchSearchOrderClick();
    }//GEN-LAST:event_btn_searchActionPerformed

    private void text_exGPbonusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_exGPbonusKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c))
        {
            JOptionPane.showMessageDialog(this,"This Field must have Numbers");
            text_exGPbonus.setText("");
        }
    }//GEN-LAST:event_text_exGPbonusKeyPressed

    private void btn_cleanGPexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanGPexActionPerformed
        clearGemPolisherExpenses();
    }//GEN-LAST:event_btn_cleanGPexActionPerformed

    private void btn_payGPexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_payGPexActionPerformed
        invoice_g_gpid=text_exGPid.getText();
        invoice_g_fname=text_exGPfname.getText();
        invoice_g_lname=text_exGPlname.getText();
        invoice_g_email=text_exGPemail.getText();
        invoice_g_phone=text_exGPphone.getText();
        invoice_g_fsalary=text_exGPfsalary.getText();
        invoice_g_bonus=text_exGPbonus.getText();
        invoice_g_total=text_exGPtot.getText();
        calExpensesID();
        calGempolisherExpenseID();
        validateGempolisherEx();

    }//GEN-LAST:event_btn_payGPexActionPerformed

    private void tbl_SupplierOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_SupplierOrdersMouseClicked
        fetchClickSupplierOrder();
    }//GEN-LAST:event_tbl_SupplierOrdersMouseClicked

    private void textSO_anoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_anoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_anoActionPerformed

    private void textSO_snoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_snoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_snoActionPerformed

    private void textSO_slnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_slnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_slnameActionPerformed

    private void textSO_sototActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_sototActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_sototActionPerformed

    private void textSO_semailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_semailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_semailActionPerformed

    private void textSO_sfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_sfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_sfnameActionPerformed

    private void textSO_GnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_GnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_GnameActionPerformed

    private void textSO_GtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_GtypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_GtypeActionPerformed

    private void textSO_GcolorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_GcolorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_GcolorActionPerformed

    private void textSO_GweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_GweightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_GweightActionPerformed

    private void textSO_sphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_sphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_sphoneActionPerformed

    private void textSO_soidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_soidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_soidActionPerformed

    private void textSO_soDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_soDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_soDateActionPerformed

    private void textSO_soStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_soStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_soStatusActionPerformed

    private void btn_RejectOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_RejectOrderActionPerformed
        if (textSO_sno.getText().equals("")) {
          JOptionPane.showMessageDialog(null,"Please Select Supplier Order First!");
            
        } else {
               
               updateSupplierOrderStatus("Rejected");
               if (!sup_spano.equals("0")) {
                updateAgentExpense("Rejected");
            } 
               fetchAllSupplierOrders();
        }
    }//GEN-LAST:event_btn_RejectOrderActionPerformed

    private void btn_SupOrderClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SupOrderClearActionPerformed
     clearSupplierOrder();        
    }//GEN-LAST:event_btn_SupOrderClearActionPerformed

    private void btn_ApproveOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ApproveOrderActionPerformed
        if (textSO_sno.getText().equals("")) {
          JOptionPane.showMessageDialog(null,"Please Select Supplier Order First!");
            
        } else {
               updateSupplierOrderStatus("Approved");
               if (!sup_spano.equals("0")) {
                updateAgentExpense("Approved");
            } 
            fetchAllSupplierOrders();
        }
    }//GEN-LAST:event_btn_ApproveOrderActionPerformed

    private void tbl_SupplierExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_SupplierExpensesMouseClicked
        fetchClickSupplierExpense();
    }//GEN-LAST:event_tbl_SupplierExpensesMouseClicked

    private void textSO_sphone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_sphone1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_sphone1ActionPerformed

    private void textSO_semail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_semail1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_semail1ActionPerformed

    private void textSO_slname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_slname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_slname1ActionPerformed

    private void textSO_sfname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_sfname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_sfname1ActionPerformed

    private void textSO_sno1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_sno1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_sno1ActionPerformed

    private void textSO_ano1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_ano1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_ano1ActionPerformed

    private void textSO_sotot1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_sotot1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_sotot1ActionPerformed

    private void textSO_soSexid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_soSexid1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_soSexid1ActionPerformed

    private void textSO_soexid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_soexid1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_soexid1ActionPerformed

    private void textSO_soid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSO_soid1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSO_soid1ActionPerformed

    private void btn_SupOrderClear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SupOrderClear1ActionPerformed
        clearSupplierExpense();
    }//GEN-LAST:event_btn_SupOrderClear1ActionPerformed

    private void btn_payGPex1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_payGPex1ActionPerformed
       if (textSO_soid1.getText().equals("")) {
          JOptionPane.showMessageDialog(null,"Please Select Supplier Expense First!");
            
        } else if ((Combobox_pmethod1.getSelectedItem().toString()).equals("Select")){
          JOptionPane.showMessageDialog(null,"Please Select Payment Method!");   
        }
        else{
             invoice_s_sran=textSO_ano1.getText();
             invoice_s_sid=textSO_sno1.getText();
             invoice_s_fname=textSO_sfname1.getText();
             invoice_s_lname=textSO_slname1.getText();
             invoice_s_email=textSO_semail1.getText();
             invoice_s_phone=textSO_sphone1.getText();
             invoice_s_oid=textSO_soid1.getText();
             invoice_s_eid=textSO_soexid1.getText();
             invoice_s_seid=textSO_soSexid1.getText();
             invoice_s_amount=textSO_sotot1.getText();
           
             updateSupplierOrderStatus("Finished");
             updateGemSupplierOrderStatus("Finished");
             updateSupplierExpense("Paid");
             updateSupplierExpensePayment();
             fetchSupplierExpenses();
             clearSupplierExpense();


            }
    }//GEN-LAST:event_btn_payGPex1ActionPerformed

    private void Combobox_pmethod1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combobox_pmethod1ActionPerformed
        pmethod=Combobox_pmethod1.getSelectedItem().toString();
    }//GEN-LAST:event_Combobox_pmethod1ActionPerformed

    private void text_AEXaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXaidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXaidActionPerformed

    private void tbl_AgentExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_AgentExpensesMouseClicked
        fetchClickAgentExpense();
    }//GEN-LAST:event_tbl_AgentExpensesMouseClicked

    private void text_AEXfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXfnameActionPerformed

    private void text_AEXlnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXlnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXlnameActionPerformed

    private void text_AEXemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXemailActionPerformed

    private void text_AEXphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXphoneActionPerformed

    private void text_AEXaexidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXaexidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXaexidActionPerformed

    private void text_AEXexidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXexidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXexidActionPerformed

    private void text_AEXsoidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXsoidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXsoidActionPerformed

    private void text_AEXcoidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXcoidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXcoidActionPerformed

    private void text_AEXoamountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXoamountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXoamountActionPerformed

    private void btn_payGPex2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_payGPex2ActionPerformed
        if (text_AEXaid.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Please select Agent Expense First!");
        }  else if(text_AEXrate.getText().equals("")) {
            JOptionPane.showMessageDialog(this,"Please Enter Commission Rate!");
        }else if((text_AEXrate.getText().length()>2)) {
            JOptionPane.showMessageDialog(this,"Please Enter Valid Commission Rate!");
        }else if(text_AEXtot.getText().equals("")){ 
            JOptionPane.showMessageDialog(this,"Please Calculate Commission!");
        }else if((Combobox_pmethod2.getSelectedItem().toString()).equals("Select")){ 
            JOptionPane.showMessageDialog(this,"Please Select Payment Method!");
        }
         else{

            invoice_a_aid=text_AEXaid.getText();
            invoice_a_fname=text_AEXfname.getText();
            invoice_a_lname=text_AEXlname.getText();
            invoice_a_email=text_AEXemail.getText();
            invoice_a_phone=text_AEXphone.getText();
            invoice_a_aeid=text_AEXaexid.getText();
            invoice_a_eid=text_AEXexid.getText();
            invoice_a_soid=text_AEXsoid.getText();
            invoice_a_coid=text_AEXcoid.getText();
            invoice_a_oamount=text_AEXoamount.getText();
            invoice_a_crate=text_AEXrate.getText();
            invoice_a_camount=text_AEXtot.getText();

            updateAgentExpensePayment();            
            fetchAgentExpenses();

        }    
    }//GEN-LAST:event_btn_payGPex2ActionPerformed

    private void btn_SupOrderClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SupOrderClear2ActionPerformed
        clearAgentExpense();
    }//GEN-LAST:event_btn_SupOrderClear2ActionPerformed

    private void text_AEXrateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXrateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXrateActionPerformed

    private void text_AEXtotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_AEXtotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_AEXtotActionPerformed

    private void text_AEXrateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_AEXrateKeyPressed
        char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_AEXrate.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_AEXrate.setText("");
            
        }

    }//GEN-LAST:event_text_AEXrateKeyPressed

    private void btn_exGPsalaryCal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exGPsalaryCal1ActionPerformed
        calAgentCommission();
    }//GEN-LAST:event_btn_exGPsalaryCal1ActionPerformed

    private void Combobox_pmethod2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combobox_pmethod2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Combobox_pmethod2ActionPerformed

    private void btn_ManageRawGemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageRawGemsMouseClicked
       
       fetchRawGems();
        jInternalFrameManageGems.setVisible(false); 
        jInternalFrameManageRawGems.setVisible(true); 
    }//GEN-LAST:event_btn_ManageRawGemsMouseClicked

    private void btn_ManageRawGemsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageRawGemsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageRawGemsMouseEntered

    private void btn_ManageRawGemsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageRawGemsMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageRawGemsMouseExited

    private void btn_ManageFinishedGemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageFinishedGemsMouseClicked
        fetchFinishedGems();
        jInternalFrameManageGems.setVisible(false); 
        jInternalFrameManageFinishedGems.setVisible(true);
    }//GEN-LAST:event_btn_ManageFinishedGemsMouseClicked

    private void btn_ManageFinishedGemsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageFinishedGemsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageFinishedGemsMouseEntered

    private void btn_ManageFinishedGemsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageFinishedGemsMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageFinishedGemsMouseExited

    private void btn_ManageRawGemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ManageRawGemActionPerformed
        clearRawGemManage();       
       jInternalFrameManageRawGems.setVisible(false);
        jInternalFrameManageGems.setVisible(true);
    }//GEN-LAST:event_btn_ManageRawGemActionPerformed

    private void btn_back8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_back8ActionPerformed
         clearFinishedGemManage();   
       jInternalFrameManageFinishedGems.setVisible(false);
        jInternalFrameManageGems.setVisible(true);
    }//GEN-LAST:event_btn_back8ActionPerformed

    private void txt_rg_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rg_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rg_nameActionPerformed

    private void txt_rg_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rg_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rg_typeActionPerformed

    private void txt_rg_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rg_colorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rg_colorActionPerformed

    private void txt_rg_weightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rg_weightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rg_weightActionPerformed

    private void txt_rg_weightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rg_weightKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c))
        {
            JOptionPane.showMessageDialog(this,"This Field must have Numbers");
            txt_rg_weight.setText("");
        }
    }//GEN-LAST:event_txt_rg_weightKeyPressed

    private void txt_rg_buyPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rg_buyPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rg_buyPriceActionPerformed

    private void txt_rg_buyPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rg_buyPriceKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c))
        {
            JOptionPane.showMessageDialog(this,"This Field must have Numbers");
            txt_rg_buyPrice.setText("");
        }
    }//GEN-LAST:event_txt_rg_buyPriceKeyPressed

    private void btn_rg_imageUploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_rg_imageUploadMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btn_rg_imageUploadMouseClicked

    private void btn_rg_imageUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rg_imageUploadActionPerformed
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE","jpg","png","gif");
        filechooser.addChoosableFileFilter(filter);
        int result = filechooser.showSaveDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectImage = filechooser.getSelectedFile();
            String imagepath = selectImage.getAbsolutePath();
            try{
                lbl_rawgemimage.setIcon(ResizeImage(imagepath));
                imagePathStr = imagepath;
            }catch(Exception exception){
                JOptionPane.showMessageDialog(this,"Image Error: "+ exception.getMessage());
            }
        }
    }//GEN-LAST:event_btn_rg_imageUploadActionPerformed

    private void txt_rg_originActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rg_originActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rg_originActionPerformed

    private void txt_rg_StatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rg_StatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rg_StatusActionPerformed

    private void txt_rg_natureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rg_natureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rg_natureActionPerformed

    private void txt_rg_sellPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rg_sellPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rg_sellPriceActionPerformed

    private void tble_ManageRawGemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tble_ManageRawGemMouseClicked
      fetchClickRawGemManage();        
    }//GEN-LAST:event_tble_ManageRawGemMouseClicked

    private void btn_rawGemUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rawGemUpdateActionPerformed
        validateRawGemUpdate();
    }//GEN-LAST:event_btn_rawGemUpdateActionPerformed

    private void txt_rg_sellPriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rg_sellPriceKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c))
        {
            JOptionPane.showMessageDialog(this,"This Field must have Numbers");
            txt_rg_sellPrice.setText("");
        }
    }//GEN-LAST:event_txt_rg_sellPriceKeyPressed

    private void myButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton1ActionPerformed
           clearRawGemManage();
        if (!g_rrid.equals(null)) {
        g_rrid.equals(null);
        }
        if (!rg_adate.equals(null)) {
        rg_adate.equals(null);
        }
    }//GEN-LAST:event_myButton1ActionPerformed

    private void btn_addRawGemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addRawGemActionPerformed
        calGemID();
        calRGemID();
        validateRawGemInput();
    }//GEN-LAST:event_btn_addRawGemActionPerformed

    private void btn_deleteRawGemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteRawGemActionPerformed
        validateRawGemDelete();
    }//GEN-LAST:event_btn_deleteRawGemActionPerformed

    private void tble_ManageFinishedGemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tble_ManageFinishedGemsMouseClicked
        fetchClickFinishedGemManage();
    }//GEN-LAST:event_tble_ManageFinishedGemsMouseClicked

    private void btn_fg_imageUploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_fg_imageUploadMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_fg_imageUploadMouseClicked

    private void btn_fg_imageUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_fg_imageUploadActionPerformed
        JFileChooser filechooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE","jpg","png","gif");
        filechooser.addChoosableFileFilter(filter);
        int result = filechooser.showSaveDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectImage = filechooser.getSelectedFile();
            String imagepath = selectImage.getAbsolutePath();
            try{
                lbl_finishedgemimage.setIcon(ResizeImage(imagepath));
                imagePathStrF = imagepath;
            }catch(Exception exception){
                JOptionPane.showMessageDialog(this,"Image Error: "+ exception.getMessage());
            }
        }
    }//GEN-LAST:event_btn_fg_imageUploadActionPerformed

    private void txt_fg_statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_statusActionPerformed

    private void txt_fg_piecesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_piecesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_piecesActionPerformed

    private void txt_fg_piecesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_piecesKeyPressed
         char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_fg_pieces.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            txt_fg_pieces.setText("");
            
        }

    }//GEN-LAST:event_txt_fg_piecesKeyPressed

    private void btn_clearFinishedGemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearFinishedGemActionPerformed
        clearFinishedGemManage();
    }//GEN-LAST:event_btn_clearFinishedGemActionPerformed

    private void btn_deleteFinishedGemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteFinishedGemActionPerformed
       validateFinishedGemDelete();
    }//GEN-LAST:event_btn_deleteFinishedGemActionPerformed

    private void btn_FinishedGemUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FinishedGemUpdateActionPerformed
       validateFinishedGemUpdate();
    }//GEN-LAST:event_btn_FinishedGemUpdateActionPerformed

    private void btn_addFinishedGemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addFinishedGemActionPerformed
      calGemID();
      calFGemID();
      validateFinishedGemInput();
    }//GEN-LAST:event_btn_addFinishedGemActionPerformed

    private void txt_fg_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_nameActionPerformed

    private void txt_fg_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_typeActionPerformed

    private void txt_fg_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_colorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_colorActionPerformed

    private void txt_fg_weightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_weightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_weightActionPerformed

    private void txt_fg_weightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_weightKeyPressed
       char c = evt.getKeyChar();
        if(Character.isLetter(c))
        {
            JOptionPane.showMessageDialog(this,"This Field must have Numbers");
            txt_fg_weight.setText("");
        }
    }//GEN-LAST:event_txt_fg_weightKeyPressed

    private void txt_fg_buypriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_buypriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_buypriceActionPerformed

    private void txt_fg_buypriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_buypriceKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c))
        {
            JOptionPane.showMessageDialog(this,"This Field must have Numbers");
            txt_fg_buyprice.setText("");
        }
    }//GEN-LAST:event_txt_fg_buypriceKeyPressed

    private void txt_fg_LicenseIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_LicenseIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_LicenseIDActionPerformed

    private void txt_fg_shapeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_shapeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_shapeActionPerformed

    private void txt_fg_sellpriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fg_sellpriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fg_sellpriceActionPerformed

    private void txt_fg_sellpriceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_sellpriceKeyPressed
        char c = evt.getKeyChar();
        if(Character.isLetter(c))
        {
            JOptionPane.showMessageDialog(this,"This Field must have Numbers");
            txt_fg_sellprice.setText("");
        }
    }//GEN-LAST:event_txt_fg_sellpriceKeyPressed

    private void btnOwnerExpencesReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOwnerExpencesReportActionPerformed
        loadExpencereport();
    }//GEN-LAST:event_btnOwnerExpencesReportActionPerformed

    private void btnOwnerIncomeReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOwnerIncomeReportActionPerformed
        // TODO add your handling code here:
      loadIncomereport();
            
    }//GEN-LAST:event_btnOwnerIncomeReportActionPerformed

    private void btnfilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfilterActionPerformed
       jInternalFrameReports.setVisible(false);
       jInternalFrameReports1.setVisible(true);
    }//GEN-LAST:event_btnfilterActionPerformed

    private void cmbReportTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbReportTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbReportTypeActionPerformed

    private void btnBack6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack6ActionPerformed
        // TODO add your handling code here:
        jInternalFrameReports1.setVisible(false);
        jInternalFrameReports.setVisible(true);
    }//GEN-LAST:event_btnBack6ActionPerformed

    private void datechooser_toAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_toAncestorAdded

    }//GEN-LAST:event_datechooser_toAncestorAdded

    private void datechooser_toMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_toMouseClicked

    }//GEN-LAST:event_datechooser_toMouseClicked

    private void datechooser_toMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_toMouseEntered

    }//GEN-LAST:event_datechooser_toMouseEntered

    private void datechooser_toMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_toMouseExited

    }//GEN-LAST:event_datechooser_toMouseExited

    private void datechooser_toMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_toMousePressed

    }//GEN-LAST:event_datechooser_toMousePressed

    private void datechooser_fromAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_fromAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromAncestorAdded

    private void datechooser_fromMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_fromMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromMouseClicked

    private void datechooser_fromMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_fromMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromMouseEntered

    private void datechooser_fromMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_fromMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromMouseExited

    private void datechooser_fromMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_fromMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_fromMousePressed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        if(cmbReportType.getSelectedIndex()==0){
           JOptionPane.showMessageDialog(this,"Select Report Type");
        }else if(cmbReportType.getSelectedIndex()==1){
           if(datechooser_from.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select Start date");
            }else if(datechooser_to.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select End date");
        }else{

      loadFilteredIncomereport();
       }
          
        }else if(cmbReportType.getSelectedIndex()==2){
           if(datechooser_from.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select Start date");
            }else if(datechooser_to.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select End date");
        }else{

      loadFilteredExpencesreport();
       }
          }else if(cmbReportType.getSelectedIndex()==3){
           if(datechooser_from.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select Start date");
            }else if(datechooser_to.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select End date");
        }else{

          loadFilteredSupplierExpencesreport();

       }
         }else if(cmbReportType.getSelectedIndex()==4){
           if(datechooser_from.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select Start date");
            }else if(datechooser_to.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select End date");
        }else{

         
loadFilteredAgentExpencesreport();
       }
       }else if(cmbReportType.getSelectedIndex()==5){
           if(datechooser_from.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select Start date");
            }else if(datechooser_to.getDate()==null){
           JOptionPane.showMessageDialog(this,"Select End date");
        }else{

          loadFilteredGempolisherExpencesreport();
       }


}

    }//GEN-LAST:event_btnGenerateActionPerformed

    private void btnDone2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDone2ActionPerformed
        // TODO add your handling code here:
        jInternalFrameInvoice.setVisible(false);
        jInternalFrameOrderDetails.setVisible(true);
    }//GEN-LAST:event_btnDone2ActionPerformed

    private void btn_ManageSuppliersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageSuppliersMouseClicked
        // TODO add your handling code here:
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageSuppliers.setVisible(true);
    }//GEN-LAST:event_btn_ManageSuppliersMouseClicked

    private void btn_ManageSuppliersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageSuppliersMouseEntered
        // TODO add your handling code here:
 
    }//GEN-LAST:event_btn_ManageSuppliersMouseEntered

    private void btn_ManageSuppliersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageSuppliersMouseExited
        // TODO add your handling code here:
       
    }//GEN-LAST:event_btn_ManageSuppliersMouseExited

    private void btn_ManageClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageClientsMouseClicked
        // TODO add your handling code here:
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageClients.setVisible(true);
    }//GEN-LAST:event_btn_ManageClientsMouseClicked

    private void btn_ManageClientsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageClientsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageClientsMouseEntered

    private void btn_ManageClientsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageClientsMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageClientsMouseExited

    private void btn_ManageAgentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageAgentsMouseClicked
        // TODO add your handling code here:
      jInternalFrameManageUsers.setVisible(false);
      jInternalFrameManageAgent.setVisible(true);
    }//GEN-LAST:event_btn_ManageAgentsMouseClicked

    private void btn_ManageAgentsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageAgentsMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageAgentsMouseEntered

    private void btn_ManageAgentsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageAgentsMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageAgentsMouseExited

    private void btn_GemPolishersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_GemPolishersMouseClicked
        // TODO add your handling code here:
  jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGemPolisher.setVisible(true);
    }//GEN-LAST:event_btn_GemPolishersMouseClicked

    private void btn_GemPolishersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_GemPolishersMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_GemPolishersMouseEntered

    private void btn_GemPolishersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_GemPolishersMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_GemPolishersMouseExited

    private void btn_ManageOwnersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageOwnersMouseClicked
        // TODO add your handling code here:
        jInternalFrameManageUsers.setVisible(false);
        jInternalFrameManageOwners.setVisible(true);
    }//GEN-LAST:event_btn_ManageOwnersMouseClicked

    private void btn_ManageOwnersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageOwnersMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageOwnersMouseEntered

    private void btn_ManageOwnersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_ManageOwnersMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ManageOwnersMouseExited

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        // TODO add your handling code here:
         jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageSuppliers.setVisible(true);
    }//GEN-LAST:event_jLabel27MouseClicked

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        clear();
        jInternalFrameManageSuppliers.setVisible(false);
        jInternalFrameManageUsers.setVisible(true);
    }//GEN-LAST:event_btnBackMouseClicked

    private void tbl_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_supplierMouseClicked
       fetchAllSupplierClick();
        //btnAdd.setVisible(false);
    }//GEN-LAST:event_tbl_supplierMouseClicked

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
       calUserID();
       calSupID();
       setUserData();
       validateSupplier();
       u_id=null;
       u_dob =null;
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked

        setUserDataToUpdate();
        validateSupplierandUpdate();
        u_id=null;
        u_dob =null;

    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
       setUserDataToUpdate();
       deleteSupplier();
       u_id=null;
       u_dob =null;
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clear();
        btnAdd.setVisible(true);
    }//GEN-LAST:event_btnClearActionPerformed

    private void text_ufnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ufnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ufnameActionPerformed

    private void text_ulnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ulnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ulnameActionPerformed

    private void text_uadd1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd1MouseClicked
        text_uadd1.setText("");
    }//GEN-LAST:event_text_uadd1MouseClicked

    private void text_uadd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd1ActionPerformed

    private void text_uadd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd1KeyPressed
    char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd1.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd1.setText("");
        }

    }//GEN-LAST:event_text_uadd1KeyPressed

    private void datechooser_udobAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_udobAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udobAncestorAdded

    private void datechooser_udobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udobMouseClicked

    private void datechooser_udobMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udobMouseEntered

    private void datechooser_udobMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udobMouseExited

    private void datechooser_udobMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udobMousePressed

    private void text_unicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_unicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_unicActionPerformed

    private void text_uemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uemailActionPerformed

    private void text_uadd2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd2MouseClicked
        text_uadd2.setText("");
    }//GEN-LAST:event_text_uadd2MouseClicked

    private void text_uadd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd2ActionPerformed

    private void text_uadd3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd3MouseClicked
        text_uadd3.setText("");
    }//GEN-LAST:event_text_uadd3MouseClicked

    private void text_uadd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd3ActionPerformed

    private void text_uadd3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd3KeyPressed
    char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd3.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd3.setText("");
        }

    }//GEN-LAST:event_text_uadd3KeyPressed

    private void text_upwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_upwdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_upwdActionPerformed

    private void text_uphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uphoneActionPerformed

    private void text_uphoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uphoneKeyPressed
        char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_uphone.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_uphone.setText("");
            
        }

    }//GEN-LAST:event_text_uphoneKeyPressed

    private void text_ulnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ulnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ulnoActionPerformed

    private void text_ubaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ubaccActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ubaccActionPerformed

    private void text_ubaccKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ubaccKeyPressed
         char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_ubacc.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_ubacc.setText("");
            
        }

    }//GEN-LAST:event_text_ubaccKeyPressed

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseClicked
        // TODO add your handling code here:
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageClients.setVisible(true);
    }//GEN-LAST:event_jLabel35MouseClicked

    private void btnBack1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBack1MouseClicked
        clearclient();
        jInternalFrameManageClients.setVisible(false);
        jInternalFrameManageUsers.setVisible(true);
    }//GEN-LAST:event_btnBack1MouseClicked

    private void tbl_clientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_clientMouseClicked
        fetchAllClientClick();
        //btnAdd1.setVisible(false);
    }//GEN-LAST:event_tbl_clientMouseClicked

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        // TODO add your handling code here:
        calUserID();
        calCliID();
        setCusUserData();
        validateClient();
        fetchAllClients();
        

        u_id=null;
        u_dob =null;
        c_id=null;
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void btnupdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdate1ActionPerformed
        // TODO add your handling code here:

        setCusUserDataToupdate();
        validateClientandUpdate();
        fetchAllClients();
        u_id=null;
        u_dob =null;

        
    }//GEN-LAST:event_btnupdate1ActionPerformed

    private void btndelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelete1ActionPerformed
        // TODO add your handling code here:
        setCusUserDataToupdate();
        deleteClient();
        fetchAllClients();
        clearclient();
        u_id=null;
        u_dob =null;
    }//GEN-LAST:event_btndelete1ActionPerformed

    private void btnClear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear1ActionPerformed
        btnAdd1.setVisible(true);
        clearclient();        
    }//GEN-LAST:event_btnClear1ActionPerformed

    private void text_ufname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ufname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ufname1ActionPerformed

    private void text_ulname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ulname1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ulname1ActionPerformed

    private void text_uadd4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd4MouseClicked
        // TODO add your handling code here:
        text_uadd4.setText("");
    }//GEN-LAST:event_text_uadd4MouseClicked

    private void text_uadd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd4ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_text_uadd4ActionPerformed

    private void text_uadd4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd4KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd4.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd4.setText("");
        }

    }//GEN-LAST:event_text_uadd4KeyPressed

    private void text_uadd5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd5MouseClicked
        // TODO add your handling code here:
        text_uadd5.setText("");
    }//GEN-LAST:event_text_uadd5MouseClicked

    private void text_uadd5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd5ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_text_uadd5ActionPerformed

    private void text_uadd6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd6MouseClicked
        // TODO add your handling code here:
        text_uadd6.setText("");
    }//GEN-LAST:event_text_uadd6MouseClicked

    private void text_uadd6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd6ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_text_uadd6ActionPerformed

    private void text_uadd6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd6KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd6.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd6.setText("");
        }

    }//GEN-LAST:event_text_uadd6KeyPressed

    private void text_upwd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_upwd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_upwd1ActionPerformed

    private void text_uphone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uphone1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_text_uphone1ActionPerformed

    private void text_uphone1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uphone1KeyPressed
         char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_uphone1.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_uphone1.setText("");
            
        }

    }//GEN-LAST:event_text_uphone1KeyPressed

    private void datechooser_udob1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_udob1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob1AncestorAdded

    private void datechooser_udob1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob1MouseClicked

    private void datechooser_udob1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob1MouseEntered

    private void datechooser_udob1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob1MouseExited

    private void datechooser_udob1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob1MousePressed

    private void text_unic1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_unic1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_unic1ActionPerformed

    private void text_uemail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uemail1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uemail1ActionPerformed

    private void comboURegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboURegionActionPerformed

    }//GEN-LAST:event_comboURegionActionPerformed

    private void btnBack2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBack2MouseClicked
        // TODO add your handling code here:
        jInternalFrameManageAgent.setVisible(false);
        jInternalFrameManageUsers.setVisible(true);
    }//GEN-LAST:event_btnBack2MouseClicked

    private void tbl_agentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_agentMouseClicked
        // TODO add your handling code here:
        fetchAllAgentClick();
       // btnAdd2.setVisible(false);
    }//GEN-LAST:event_tbl_agentMouseClicked

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed
        // TODO add your handling code here:
        calUserID();    
        calAgentID();
        setAgentUserData();
        validateAgent();
       u_id=null;
       u_dob =null;

    }//GEN-LAST:event_btnAdd2ActionPerformed

    private void myButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton6ActionPerformed
        // TODO add your handling code here:
       setAgentUserDataToupdate();
       validateAgentandUpdate();
       fetchAllAgents();
        u_id=null;
        u_dob =null;
    }//GEN-LAST:event_myButton6ActionPerformed

    private void AgentdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgentdeleteActionPerformed
        // TODO add your handling code here:
        setAgentUserDataToupdate();
        deleteAgent();
       fetchAllAgents();
      clearAgent();
        u_id=null;
        u_dob =null;

    }//GEN-LAST:event_AgentdeleteActionPerformed

    private void btnClear2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClear2MouseClicked
        // TODO add your handling code here:
        clearAgent();
        btnAdd2.setVisible(true);
    }//GEN-LAST:event_btnClear2MouseClicked

    private void btnClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClear2ActionPerformed

    private void text_ufname2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ufname2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ufname2ActionPerformed

    private void text_ulname2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ulname2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ulname2ActionPerformed

    private void text_uadd7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd7MouseClicked
        // TODO add your handling code here:
        text_uadd7.setText("");
    }//GEN-LAST:event_text_uadd7MouseClicked

    private void text_uadd7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd7ActionPerformed

    private void text_uadd7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd7KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd7.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd7.setText("");
        }

    }//GEN-LAST:event_text_uadd7KeyPressed

    private void datechooser_udob2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_udob2AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob2AncestorAdded

    private void datechooser_udob2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob2MouseClicked

    private void datechooser_udob2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob2MouseEntered

    private void datechooser_udob2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob2MouseExited

    private void datechooser_udob2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob2MousePressed

    private void text_unic2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_unic2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_unic2ActionPerformed

    private void text_uemail2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uemail2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uemail2ActionPerformed

    private void text_uadd8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd8MouseClicked
        // TODO add your handling code here:
        text_uadd8.setText("");
    }//GEN-LAST:event_text_uadd8MouseClicked

    private void text_uadd8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd8ActionPerformed

    private void text_uadd9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd9MouseClicked
        // TODO add your handling code here:
        text_uadd9.setText("");
    }//GEN-LAST:event_text_uadd9MouseClicked

    private void text_uadd9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd9ActionPerformed

    private void text_uadd9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd9KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd9.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd9.setText("");
        }
    }//GEN-LAST:event_text_uadd9KeyPressed

    private void text_upwd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_upwd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_upwd2ActionPerformed

    private void text_uphone2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uphone2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uphone2ActionPerformed

    private void text_uphone2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uphone2KeyPressed
         char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_uphone2.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_uphone2.setText("");
            
        }

    }//GEN-LAST:event_text_uphone2KeyPressed

    private void text_ubacc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ubacc1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ubacc1ActionPerformed

    private void text_ubacc1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ubacc1KeyPressed
       char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_ubacc1.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_ubacc1.setText("");
            
        }
    }//GEN-LAST:event_text_ubacc1KeyPressed

    private void datechooser_jdateAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_jdateAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdateAncestorAdded

    private void datechooser_jdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_jdateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdateMouseClicked

    private void datechooser_jdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_jdateMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdateMouseEntered

    private void datechooser_jdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_jdateMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdateMouseExited

    private void datechooser_jdateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_jdateMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdateMousePressed

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        // TODO add your handling code here:
       jInternalFrameManageUsers.setVisible(false);
      jInternalFrameManageAgent.setVisible(true);
    }//GEN-LAST:event_jLabel36MouseClicked

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        // TODO add your handling code here:
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGemPolisher.setVisible(true);
    }//GEN-LAST:event_jLabel37MouseClicked

    private void btnBack3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBack3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBack3MouseClicked

    private void btnBack3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack3ActionPerformed
        // TODO add your handling code here:
        clearGempolisher();
        jInternalFrameManageGemPolisher.setVisible(false);
        jInternalFrameManageUsers.setVisible(true);

    }//GEN-LAST:event_btnBack3ActionPerformed

    private void tbl_gempolisherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_gempolisherMouseClicked
        // TODO add your handling code here:
          fetchClickGemPolisher();
          //btnAddgempolisher.setVisible(false);
    }//GEN-LAST:event_tbl_gempolisherMouseClicked

    private void btnAddgempolisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddgempolisherActionPerformed
        // TODO add your handling code here:
        calUserID();
        calGemPolisherID();
        setGempolisherUserData();
        validateGempolisher();
        fetchAllGemPolishers();
        


        u_id=null;
        u_dob =null;
        e_id=null;
        
    }//GEN-LAST:event_btnAddgempolisherActionPerformed

    private void btnUpdategempolisherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdategempolisherMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdategempolisherMouseClicked

    private void btnUpdategempolisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdategempolisherActionPerformed
        // TODO add your handling code here:
        setGempolisherUserData();
        validateGempolisherToUpdate();
        fetchAllGemPolishers();


    }//GEN-LAST:event_btnUpdategempolisherActionPerformed

    private void btndeletegempolisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeletegempolisherActionPerformed
        // TODO add your handling code here:
        setGempolisherUserData();
       deleteGempolisher();
       fetchAllGemPolishers();
       

        u_id=null;
        u_dob =null;


    }//GEN-LAST:event_btndeletegempolisherActionPerformed

    private void btnClear3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear3ActionPerformed
        // TODO add your handling code here:
       clearGempolisher();
       btnAddgempolisher.setVisible(true);
    }//GEN-LAST:event_btnClear3ActionPerformed

    private void text_ufname3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ufname3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ufname3ActionPerformed

    private void text_ulname3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ulname3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ulname3ActionPerformed

    private void text_uadd10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd10MouseClicked
        // TODO add your handling code here:
        text_uadd10.setText("");
    }//GEN-LAST:event_text_uadd10MouseClicked

    private void text_uadd10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd10ActionPerformed

    private void text_uadd10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd10KeyPressed
char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd10.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd10.setText("");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd10KeyPressed

    private void datechooser_udob3AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_udob3AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob3AncestorAdded

    private void datechooser_udob3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob3MouseClicked

    private void datechooser_udob3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob3MouseEntered

    private void datechooser_udob3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob3MouseExited

    private void datechooser_udob3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udob3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_udob3MousePressed

    private void text_unic3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_unic3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_unic3ActionPerformed

    private void text_uemail3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uemail3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uemail3ActionPerformed

    private void datechooser_jdate2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_jdate2AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdate2AncestorAdded

    private void datechooser_jdate2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_jdate2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdate2MouseClicked

    private void datechooser_jdate2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_jdate2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdate2MouseEntered

    private void datechooser_jdate2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_jdate2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdate2MouseExited

    private void datechooser_jdate2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_jdate2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_datechooser_jdate2MousePressed

    private void text_ubacc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ubacc2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ubacc2ActionPerformed

    private void text_ubacc2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ubacc2KeyPressed
        char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_ubacc2.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_ubacc2.setText("");
            
        }

    }//GEN-LAST:event_text_ubacc2KeyPressed

    private void text_salaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_salaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_salaryActionPerformed

    private void text_uadd11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd11MouseClicked
        text_uadd11.setText("");
    }//GEN-LAST:event_text_uadd11MouseClicked

    private void text_uadd11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd11ActionPerformed

    private void text_uadd12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd12MouseClicked
        text_uadd12.setText("");
    }//GEN-LAST:event_text_uadd12MouseClicked

    private void text_uadd12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd12ActionPerformed

    private void text_uadd12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd12KeyPressed
char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd12.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd12.setText("");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd12KeyPressed

    private void text_upwd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_upwd3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_upwd3ActionPerformed

    private void text_uphone3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uphone3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uphone3ActionPerformed

    private void text_uphone3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uphone3KeyPressed
        char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_uphone3.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_uphone3.setText("");
            
        }

    }//GEN-LAST:event_text_uphone3KeyPressed

    private void btnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapActionPerformed
        // TODO add your handling code here:
        MapMain map = new MapMain();
        map.setVisible(true);
    }//GEN-LAST:event_btnMapActionPerformed

    private void btnBack4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBack4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBack4MouseClicked

    private void btnBack4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack4ActionPerformed
       clearOwner();
        jInternalFrameManageOwners.setVisible(false);
        jInternalFrameManageUsers.setVisible(true);
    }//GEN-LAST:event_btnBack4ActionPerformed

    private void tbl_ownerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ownerMouseClicked
        // TODO add your handling code here:
       fetchAllOwnerClick();
       btnAddowner.setVisible(false);
    }//GEN-LAST:event_tbl_ownerMouseClicked

    private void text_ufname4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ufname4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ufname4ActionPerformed

    private void text_ulname4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ulname4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ulname4ActionPerformed

    private void text_uadd13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd13MouseClicked
        text_uadd13.setText("");
    }//GEN-LAST:event_text_uadd13MouseClicked

    private void text_uadd13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd13ActionPerformed

    private void text_uadd13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd13KeyPressed
       char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd13.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd13.setText("");
        }

    }//GEN-LAST:event_text_uadd13KeyPressed

    private void text_uadd14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd14MouseClicked
        text_uadd14.setText("");
    }//GEN-LAST:event_text_uadd14MouseClicked

    private void text_uadd14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd14ActionPerformed

    private void text_uadd15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd15MouseClicked
        text_uadd15.setText("");
    }//GEN-LAST:event_text_uadd15MouseClicked

    private void text_uadd15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd15ActionPerformed

    private void text_uadd15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd15KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd15.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd15.setText("");
        }

    }//GEN-LAST:event_text_uadd15KeyPressed

    private void btnClear4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear4ActionPerformed
        // TODO add your handling code here:
       clearOwner();
    }//GEN-LAST:event_btnClear4ActionPerformed

    private void btnAddownerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddownerActionPerformed
        // TODO add your handling code here:

      calownerID();
      setOwnerData();
      validateOwner();
      fetchAllOwners();
      
        

        o_id=null;
     
    }//GEN-LAST:event_btnAddownerActionPerformed

    private void btnUpdateownerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateownerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateownerMouseClicked

    private void btnUpdateownerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateownerActionPerformed
        // TODO add your handling code here:
        setOwnerData();
        validateOwnertoUpdate();
        fetchAllOwners();
        clearOwner();
       
        o_id=null;
      

    }//GEN-LAST:event_btnUpdateownerActionPerformed

    private void text_unic4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_unic4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_unic4ActionPerformed

    private void text_uemail4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uemail4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uemail4ActionPerformed

    private void text_uphone4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uphone4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uphone4ActionPerformed

    private void text_uphone4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uphone4KeyPressed
        char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_uphone4.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_uphone4.setText("");
            
        }

    }//GEN-LAST:event_text_uphone4KeyPressed

    private void text_upwd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_upwd4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_upwd4ActionPerformed

    private void btndeleteownerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteownerActionPerformed
        // TODO add your handling code here:
         setOwnerData();
         deleteOwner();
         fetchAllOwners();
         
     
        o_id=null;
       
    }//GEN-LAST:event_btndeleteownerActionPerformed

    private void txt_rg_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rg_nameKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            txt_rg_name.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            txt_rg_name.setText("");
        }

    }//GEN-LAST:event_txt_rg_nameKeyPressed

    private void txt_rg_typeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rg_typeKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_rg_type.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_rg_type.setText("");
        }
    }//GEN-LAST:event_txt_rg_typeKeyPressed

    private void txt_rg_colorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rg_colorKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_rg_color.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_rg_color.setText("");
        }
    }//GEN-LAST:event_txt_rg_colorKeyPressed

    private void txt_rg_originKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rg_originKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_rg_origin.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_rg_origin.setText("");
        }
    }//GEN-LAST:event_txt_rg_originKeyPressed

    private void txt_rg_natureKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rg_natureKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_rg_nature.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_rg_nature.setText("");
        }
    }//GEN-LAST:event_txt_rg_natureKeyPressed

    private void txt_rg_StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_rg_StatusKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_rg_Status.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_rg_Status.setText("");
        }
    }//GEN-LAST:event_txt_rg_StatusKeyPressed

    private void txt_fg_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_nameKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_fg_name.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_fg_name.setText("");
        }
    }//GEN-LAST:event_txt_fg_nameKeyPressed

    private void txt_fg_typeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_typeKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_fg_type.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_fg_type.setText("");
        }
    }//GEN-LAST:event_txt_fg_typeKeyPressed

    private void txt_fg_colorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_colorKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_fg_color.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_fg_color.setText("");
        }
    }//GEN-LAST:event_txt_fg_colorKeyPressed

    private void txt_fg_LicenseIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_LicenseIDKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            txt_fg_LicenseID.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            txt_fg_LicenseID.setText("");
        }

    }//GEN-LAST:event_txt_fg_LicenseIDKeyPressed

    private void txt_fg_shapeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_shapeKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_fg_shape.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_fg_shape.setText("");
        }
    }//GEN-LAST:event_txt_fg_shapeKeyPressed

    private void txt_fg_statusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_fg_statusKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_fg_status.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
             txt_fg_status.setText("");
        }
    }//GEN-LAST:event_txt_fg_statusKeyPressed

    private void text_ufname4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ufname4KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ufname4.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ufname4.setText("");
        }

    }//GEN-LAST:event_text_ufname4KeyPressed

    private void text_ulname4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ulname4KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ulname4.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ulname4.setText("");
        }
    }//GEN-LAST:event_text_ulname4KeyPressed

    private void text_unic4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_unic4KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_unic4.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_unic4.setText("");
        }

    }//GEN-LAST:event_text_unic4KeyPressed

    private void text_uadd14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd14KeyPressed
       char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd14.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd14.setText("");
        }

    }//GEN-LAST:event_text_uadd14KeyPressed

    private void text_ufnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ufnameKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ufname.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ufname.setText("");
        }

    }//GEN-LAST:event_text_ufnameKeyPressed

    private void text_ulnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ulnameKeyPressed
         char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ulname.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ulname.setText("");
        }
    }//GEN-LAST:event_text_ulnameKeyPressed

    private void text_unicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_unicKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_unic.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_unic.setText("");
        }

    }//GEN-LAST:event_text_unicKeyPressed

    private void text_uadd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd2KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd2.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd2.setText("");
        }

    }//GEN-LAST:event_text_uadd2KeyPressed

    private void text_ulnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ulnoKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ulno.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_ulno.setText("");
        }

    }//GEN-LAST:event_text_ulnoKeyPressed

    private void text_ufname1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ufname1KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ufname1.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ufname1.setText("");
        }

    }//GEN-LAST:event_text_ufname1KeyPressed

    private void text_ulname1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ulname1KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ulname1.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ulname1.setText("");
        }
    }//GEN-LAST:event_text_ulname1KeyPressed

    private void text_unic1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_unic1KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_unic1.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_unic1.setText("");
        }

    }//GEN-LAST:event_text_unic1KeyPressed

    private void text_uadd5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd5KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd5.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd5.setText("");
        }

    }//GEN-LAST:event_text_uadd5KeyPressed

    private void btnBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack1ActionPerformed
       clearclient();
    }//GEN-LAST:event_btnBack1ActionPerformed

    private void btnBack2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack2ActionPerformed
         clearAgent();
    }//GEN-LAST:event_btnBack2ActionPerformed

    private void text_ufname2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ufname2KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ufname2.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ufname2.setText("");
        }

    }//GEN-LAST:event_text_ufname2KeyPressed

    private void text_ulname2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ulname2KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ulname2.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ulname2.setText("");
        }
    }//GEN-LAST:event_text_ulname2KeyPressed

    private void text_unic2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_unic2KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_unic2.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_unic2.setText("");
        }

    }//GEN-LAST:event_text_unic2KeyPressed

    private void text_uadd8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd8KeyPressed
       char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd8.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd8.setText("");
        }
    }//GEN-LAST:event_text_uadd8KeyPressed

    private void text_ufname3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ufname3KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ufname3.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ufname3.setText("");
        }

    }//GEN-LAST:event_text_ufname3KeyPressed

    private void text_ulname3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ulname3KeyPressed
char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ulname3.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have Text only!");
            text_ulname3.setText("");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ulname3KeyPressed

    private void text_unic3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_unic3KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_unic3.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_unic3.setText("");
        }

    }//GEN-LAST:event_text_unic3KeyPressed

    private void text_uadd11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd11KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd11.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd11.setText("");
        }
    }//GEN-LAST:event_text_uadd11KeyPressed

    private void text_salaryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_salaryKeyPressed
char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
             text_salary.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_salary.setText("");
            
        }        // TODO add your handling code here:
    }//GEN-LAST:event_text_salaryKeyPressed
    
    public void bar(JLabel lab){
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
        lblBar3.setOpaque(false);
        lblBar4.setOpaque(false);
        lblBar5.setOpaque(false);
        lblBar6.setOpaque(false);
        lab.setOpaque(true);
        menu.repaint();
    }
    
    
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private View.MyButton Agentdelete;
    private javax.swing.JComboBox<String> Combobox_pmethod;
    private javax.swing.JComboBox<String> Combobox_pmethod1;
    private javax.swing.JComboBox<String> Combobox_pmethod2;
    private javax.swing.JPanel bg;
    private View.MyButton btnAdd;
    private View.MyButton btnAdd1;
    private View.MyButton btnAdd2;
    private View.MyButton btnAddgempolisher;
    private View.MyButton btnAddowner;
    private View.MyButton btnBack;
    private View.MyButton btnBack1;
    private View.MyButton btnBack2;
    private View.MyButton btnBack3;
    private View.MyButton btnBack4;
    private View.MyButton btnBack6;
    private View.MyButton btnClear;
    private View.MyButton btnClear1;
    private View.MyButton btnClear2;
    private View.MyButton btnClear3;
    private View.MyButton btnClear4;
    private View.MyButton btnDone2;
    private javax.swing.JLabel btnExit;
    private View.MyButton btnGenerate;
    private javax.swing.JPanel btnHelp;
    private javax.swing.JLabel btnLogOut;
    private javax.swing.JPanel btnManageGems;
    private javax.swing.JPanel btnManageUsers;
    private View.MyButton btnMap;
    private javax.swing.JLabel btnMiniMize;
    private javax.swing.JPanel btnOrderDetails;
    private View.MyButton btnOwnerExpencesReport;
    private View.MyButton btnOwnerIncomeReport;
    private javax.swing.JLabel btnProfile;
    private javax.swing.JPanel btnReports;
    private View.MyButton btnUpdate;
    private View.MyButton btnUpdategempolisher;
    private View.MyButton btnUpdateowner;
    private javax.swing.JPanel btn_AgentExpenses;
    private View.MyButton btn_ApproveOrder;
    private javax.swing.JPanel btn_ClientOrders;
    private View.MyButton btn_FinishedGemUpdate;
    private javax.swing.JPanel btn_GemPolishers;
    private javax.swing.JPanel btn_GempolisherWork;
    private javax.swing.JPanel btn_ManageAgents;
    private javax.swing.JPanel btn_ManageClients;
    private javax.swing.JPanel btn_ManageFinishedGems;
    private javax.swing.JPanel btn_ManageOwners;
    private View.MyButton btn_ManageRawGem;
    private javax.swing.JPanel btn_ManageRawGems;
    private javax.swing.JPanel btn_ManageSuppliers;
    private View.MyButton btn_RejectOrder;
    private View.MyButton btn_SupOrderClear;
    private View.MyButton btn_SupOrderClear1;
    private View.MyButton btn_SupOrderClear2;
    private javax.swing.JPanel btn_SuppilerOrders;
    private javax.swing.JPanel btn_SupplierExpenses;
    private View.MyButton btn_addFinishedGem;
    private View.MyButton btn_addRawGem;
    private View.MyButton btn_back;
    private View.MyButton btn_back1;
    private View.MyButton btn_back2;
    private View.MyButton btn_back3;
    private View.MyButton btn_back4;
    private View.MyButton btn_back5;
    private View.MyButton btn_back6;
    private View.MyButton btn_back8;
    private View.MyButton btn_cleanGPex;
    private View.MyButton btn_clearFinishedGem;
    private View.MyButton btn_deleteFinishedGem;
    private View.MyButton btn_deleteRawGem;
    private View.MyButton btn_exGPsalaryCal;
    private View.MyButton btn_exGPsalaryCal1;
    private View.MyButton btn_fg_imageUpload;
    private javax.swing.JPanel btn_gemPolisherExpenses;
    private View.MyButton btn_gjobClear;
    private View.MyButton btn_payGPex;
    private View.MyButton btn_payGPex1;
    private View.MyButton btn_payGPex2;
    private View.MyButton btn_rawGemUpdate;
    private View.MyButton btn_reset;
    private View.MyButton btn_resetGemPolsiherEX;
    private View.MyButton btn_rg_imageUpload;
    private View.MyButton btn_search;
    private View.MyButton btn_searchGempolsherEX;
    private View.MyButton btn_showAllJobs;
    private View.MyButton btn_showAssignedJobs;
    private View.MyButton btn_showCompletedJobs;
    private View.MyButton btn_showGJobs;
    private View.MyButton btndelete;
    private View.MyButton btndelete1;
    private View.MyButton btndeletegempolisher;
    private View.MyButton btndeleteowner;
    private View.MyButton btnfilter;
    private View.MyButton btnupdate1;
    private javax.swing.JPanel buttonHome;
    private javax.swing.JComboBox<String> cmbReportType;
    private javax.swing.JComboBox<String> cmb_worktype;
    private javax.swing.JComboBox<String> cmb_worktype2;
    private javax.swing.JComboBox<String> comboURegion;
    private com.toedter.calendar.JDateChooser datechooser_from;
    private com.toedter.calendar.JDateChooser datechooser_jdate;
    private com.toedter.calendar.JDateChooser datechooser_jdate2;
    private com.toedter.calendar.JDateChooser datechooser_to;
    private com.toedter.calendar.JDateChooser datechooser_udob;
    private com.toedter.calendar.JDateChooser datechooser_udob1;
    private com.toedter.calendar.JDateChooser datechooser_udob2;
    private com.toedter.calendar.JDateChooser datechooser_udob3;
    private javax.swing.JPanel form1;
    private com.toedter.calendar.JDateChooser jDateChooser_JEndDate;
    private javax.swing.JInternalFrame jInternalFrameAgentExpenses;
    private javax.swing.JInternalFrame jInternalFrameClientOrders;
    private javax.swing.JInternalFrame jInternalFrameGemPolisherExpenses;
    private javax.swing.JInternalFrame jInternalFrameHome;
    private javax.swing.JInternalFrame jInternalFrameInvoice;
    private javax.swing.JInternalFrame jInternalFrameJobs;
    private javax.swing.JInternalFrame jInternalFrameJobsShow;
    private javax.swing.JInternalFrame jInternalFrameManageAgent;
    private javax.swing.JInternalFrame jInternalFrameManageClients;
    private javax.swing.JInternalFrame jInternalFrameManageFinishedGems;
    private javax.swing.JInternalFrame jInternalFrameManageGemPolisher;
    private javax.swing.JInternalFrame jInternalFrameManageGems;
    private javax.swing.JInternalFrame jInternalFrameManageOwners;
    private javax.swing.JInternalFrame jInternalFrameManageRawGems;
    private javax.swing.JInternalFrame jInternalFrameManageSuppliers;
    private javax.swing.JInternalFrame jInternalFrameManageUsers;
    private javax.swing.JInternalFrame jInternalFrameOrderDetails;
    private javax.swing.JInternalFrame jInternalFrameOwnerrHelp;
    private javax.swing.JInternalFrame jInternalFrameProfile;
    private javax.swing.JInternalFrame jInternalFrameReports;
    private javax.swing.JInternalFrame jInternalFrameReports1;
    private javax.swing.JInternalFrame jInternalFrameSupplierExpenses;
    private javax.swing.JInternalFrame jInternalFrameSupplierOrders;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel label_AEX;
    private javax.swing.JLabel label_AEX1;
    private javax.swing.JLabel label_AEX10;
    private javax.swing.JLabel label_AEX11;
    private javax.swing.JLabel label_AEX12;
    private javax.swing.JLabel label_AEX13;
    private javax.swing.JLabel label_AEX2;
    private javax.swing.JLabel label_AEX3;
    private javax.swing.JLabel label_AEX4;
    private javax.swing.JLabel label_AEX5;
    private javax.swing.JLabel label_AEX6;
    private javax.swing.JLabel label_AEX7;
    private javax.swing.JLabel label_AEX8;
    private javax.swing.JLabel label_AEX9;
    private javax.swing.JLabel label_exGPbonus;
    private javax.swing.JLabel label_exGPemail;
    private javax.swing.JLabel label_exGPfixedSalary;
    private javax.swing.JLabel label_exGPfname;
    private javax.swing.JLabel label_exGPlname;
    private javax.swing.JLabel label_exGPphone;
    private javax.swing.JLabel label_exGPpmethod;
    private javax.swing.JLabel label_exGPpmethod1;
    private javax.swing.JLabel label_exGPtot;
    private javax.swing.JLabel label_exGempolisherID;
    private javax.swing.JPanel label_expenses;
    private javax.swing.JPanel label_jobs;
    private javax.swing.JPanel label_orders;
    private javax.swing.JLabel label_serachgemplosher1;
    private javax.swing.JLabel label_serachgemplosher2;
    private javax.swing.JLabel label_supOrderAgentNo;
    private javax.swing.JLabel label_supOrderAgentNo1;
    private javax.swing.JLabel label_supOrderDate;
    private javax.swing.JLabel label_supOrderDate1;
    private javax.swing.JLabel label_supOrderGcolor;
    private javax.swing.JLabel label_supOrderGname;
    private javax.swing.JLabel label_supOrderGtype;
    private javax.swing.JLabel label_supOrderGweight;
    private javax.swing.JLabel label_supOrderID;
    private javax.swing.JLabel label_supOrderID1;
    private javax.swing.JLabel label_supOrderImage;
    private javax.swing.JLabel label_supOrderStatus;
    private javax.swing.JLabel label_supOrderStatus1;
    private javax.swing.JLabel label_supOrderSupEmail;
    private javax.swing.JLabel label_supOrderSupEmail1;
    private javax.swing.JLabel label_supOrderSupID;
    private javax.swing.JLabel label_supOrderSupID1;
    private javax.swing.JLabel label_supOrderSupPhone;
    private javax.swing.JLabel label_supOrderSupPhone1;
    private javax.swing.JLabel label_supOrderSupfanme;
    private javax.swing.JLabel label_supOrderSupfanme1;
    private javax.swing.JLabel label_supOrderSuplname;
    private javax.swing.JLabel label_supOrderSuplname1;
    private javax.swing.JLabel label_supOrderValue;
    private javax.swing.JLabel label_supOrderValue1;
    private javax.swing.JLabel label_uaddress;
    private javax.swing.JLabel label_uaddress1;
    private javax.swing.JLabel label_uaddress2;
    private javax.swing.JLabel label_uaddress3;
    private javax.swing.JLabel label_uaddress4;
    private javax.swing.JLabel label_ubacc;
    private javax.swing.JLabel label_ubacc1;
    private javax.swing.JLabel label_ubacc2;
    private javax.swing.JLabel label_ubacc3;
    private javax.swing.JLabel label_ubacc4;
    private javax.swing.JLabel label_udob;
    private javax.swing.JLabel label_udob1;
    private javax.swing.JLabel label_udob2;
    private javax.swing.JLabel label_udob3;
    private javax.swing.JLabel label_udob4;
    private javax.swing.JLabel label_uemail;
    private javax.swing.JLabel label_uemail1;
    private javax.swing.JLabel label_uemail2;
    private javax.swing.JLabel label_uemail3;
    private javax.swing.JLabel label_uemail4;
    private javax.swing.JLabel label_uemail5;
    private javax.swing.JLabel label_ufname;
    private javax.swing.JLabel label_ufname1;
    private javax.swing.JLabel label_ufname10;
    private javax.swing.JLabel label_ufname2;
    private javax.swing.JLabel label_ufname3;
    private javax.swing.JLabel label_ufname4;
    private javax.swing.JLabel label_ufname5;
    private javax.swing.JLabel label_ufname6;
    private javax.swing.JLabel label_ufname7;
    private javax.swing.JLabel label_ufname8;
    private javax.swing.JLabel label_ufname9;
    private javax.swing.JLabel label_ulname;
    private javax.swing.JLabel label_ulname1;
    private javax.swing.JLabel label_ulname2;
    private javax.swing.JLabel label_ulname3;
    private javax.swing.JLabel label_ulname4;
    private javax.swing.JLabel label_ulname5;
    private javax.swing.JLabel label_ulno;
    private javax.swing.JLabel label_ulno1;
    private javax.swing.JLabel label_unic;
    private javax.swing.JLabel label_unic1;
    private javax.swing.JLabel label_unic2;
    private javax.swing.JLabel label_unic3;
    private javax.swing.JLabel label_unic4;
    private javax.swing.JLabel label_uphone;
    private javax.swing.JLabel label_uphone1;
    private javax.swing.JLabel label_uphone2;
    private javax.swing.JLabel label_uphone3;
    private javax.swing.JLabel label_uphone4;
    private javax.swing.JLabel label_uphone5;
    private javax.swing.JLabel label_uphone6;
    private javax.swing.JLabel label_upwd;
    private javax.swing.JLabel label_upwd1;
    private javax.swing.JLabel label_upwd2;
    private javax.swing.JLabel label_upwd3;
    private javax.swing.JLabel label_upwd4;
    private javax.swing.JLabel label_uregion;
    private javax.swing.JLabel lblBar1;
    private javax.swing.JLabel lblBar10;
    private javax.swing.JLabel lblBar11;
    private javax.swing.JLabel lblBar12;
    private javax.swing.JLabel lblBar13;
    private javax.swing.JLabel lblBar14;
    private javax.swing.JLabel lblBar15;
    private javax.swing.JLabel lblBar16;
    private javax.swing.JLabel lblBar17;
    private javax.swing.JLabel lblBar18;
    private javax.swing.JLabel lblBar19;
    private javax.swing.JLabel lblBar2;
    private javax.swing.JLabel lblBar20;
    private javax.swing.JLabel lblBar3;
    private javax.swing.JLabel lblBar4;
    private javax.swing.JLabel lblBar5;
    private javax.swing.JLabel lblBar6;
    private javax.swing.JLabel lblBar7;
    private javax.swing.JLabel lblBar8;
    private javax.swing.JLabel lblBar9;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserName1;
    private javax.swing.JLabel lbl_AEXsubtitle;
    private javax.swing.JLabel lbl_AEXsubtitle1;
    private javax.swing.JLabel lbl_AgentExpensesTitle;
    private javax.swing.JLabel lbl_ManageRawGemTitle;
    private javax.swing.JLabel lbl_ManageRawGemTitle1;
    private javax.swing.JLabel lbl_certiID1;
    private javax.swing.JLabel lbl_fg_buyprice;
    private javax.swing.JLabel lbl_fg_carats;
    private javax.swing.JLabel lbl_fg_color;
    private javax.swing.JLabel lbl_fg_gimage;
    private javax.swing.JLabel lbl_fg_gname;
    private javax.swing.JLabel lbl_fg_licenseID;
    private javax.swing.JLabel lbl_fg_pieces;
    private javax.swing.JLabel lbl_fg_sellprice;
    private javax.swing.JLabel lbl_fg_shape;
    private javax.swing.JLabel lbl_fg_status;
    private javax.swing.JLabel lbl_fg_type;
    private javax.swing.JLabel lbl_fg_weight;
    private javax.swing.JLabel lbl_finishedgemimage;
    private javax.swing.JLabel lbl_gOrigin;
    private javax.swing.JLabel lbl_gcid;
    private javax.swing.JLabel lbl_gcolor;
    private javax.swing.JLabel lbl_gcolor1;
    private javax.swing.JLabel lbl_gemImage;
    private javax.swing.JLabel lbl_gemNature;
    private javax.swing.JLabel lbl_gname;
    private javax.swing.JLabel lbl_gname1;
    private javax.swing.JLabel lbl_gname2;
    private javax.swing.JLabel lbl_gname3;
    private javax.swing.JLabel lbl_gname4;
    private javax.swing.JLabel lbl_gname5;
    private javax.swing.JLabel lbl_gpieces;
    private javax.swing.JLabel lbl_gshape;
    private javax.swing.JLabel lbl_gsprice;
    private javax.swing.JLabel lbl_gtype;
    private javax.swing.JLabel lbl_gtype1;
    private javax.swing.JLabel lbl_gweight;
    private javax.swing.JLabel lbl_gweight1;
    private javax.swing.JLabel lbl_jobenddate;
    private javax.swing.JLabel lbl_rawfinishedGems;
    private javax.swing.JLabel lbl_rawfinishedGems1;
    private javax.swing.JLabel lbl_rawgemimage;
    private javax.swing.JLabel lbl_rg_carats;
    private javax.swing.JLabel lbl_rg_gBuyPrice;
    private javax.swing.JLabel lbl_rg_gNature;
    private javax.swing.JLabel lbl_rg_gOrigin;
    private javax.swing.JLabel lbl_rg_gSellingPrice;
    private javax.swing.JLabel lbl_rg_gStatus;
    private javax.swing.JLabel lbl_rg_gcolor;
    private javax.swing.JLabel lbl_rg_gimage;
    private javax.swing.JLabel lbl_rg_gname;
    private javax.swing.JLabel lbl_rg_gtype;
    private javax.swing.JLabel lbl_rg_gweight;
    private javax.swing.JLabel lbl_searchGemName;
    private javax.swing.JLabel lbl_supGemInfoTitle;
    private javax.swing.JLabel lbl_supOrderInfoTitle;
    private javax.swing.JLabel lbl_supOrderInfoTitle1;
    private javax.swing.JLabel lbl_supOrderInfotitle;
    private javax.swing.JLabel lbl_supOrderInfotitle1;
    private javax.swing.JLabel lbl_supplersExpensesTitle;
    private javax.swing.JLabel lbl_supplersOrderTitle;
    private javax.swing.JLabel lbl_weight;
    private javax.swing.JLabel lbl_weight1;
    private javax.swing.JLabel lbl_weight2;
    private javax.swing.JPanel menu;
    private View.MyButton myButton1;
    private View.MyButton myButton2;
    private View.MyButton myButton6;
    private javax.swing.JPanel panelLoad;
    private javax.swing.JPanel panelLoad1;
    private javax.swing.JPanel panelLoad2;
    private javax.swing.JTable tbl_AgentExpenses;
    private javax.swing.JTable tbl_EXGempolisherExpenses;
    private javax.swing.JTable tbl_EXGempolishers;
    private javax.swing.JTable tbl_SupplierExpenses;
    private javax.swing.JTable tbl_SupplierOrders;
    private javax.swing.JTable tbl_agent;
    private javax.swing.JTable tbl_availaleRGems;
    private javax.swing.JTable tbl_client;
    private javax.swing.JTable tbl_gempolisher;
    private javax.swing.JTable tbl_jGempolishers;
    private javax.swing.JTable tbl_jobsGemPolisher;
    private javax.swing.JTable tbl_operationClientOrders;
    private javax.swing.JTable tbl_owner;
    private javax.swing.JTable tbl_supplier;
    private javax.swing.JTable tble_ManageFinishedGems;
    private javax.swing.JTable tble_ManageRawGem;
    private javax.swing.JTextField textSO_Gcolor;
    private javax.swing.JLabel textSO_GemImage;
    private javax.swing.JTextField textSO_Gname;
    private javax.swing.JTextField textSO_Gtype;
    private javax.swing.JTextField textSO_Gweight;
    private javax.swing.JTextField textSO_ano;
    private javax.swing.JTextField textSO_ano1;
    private javax.swing.JTextField textSO_semail;
    private javax.swing.JTextField textSO_semail1;
    private javax.swing.JTextField textSO_sfname;
    private javax.swing.JTextField textSO_sfname1;
    private javax.swing.JTextField textSO_slname;
    private javax.swing.JTextField textSO_slname1;
    private javax.swing.JTextField textSO_sno;
    private javax.swing.JTextField textSO_sno1;
    private javax.swing.JTextField textSO_soDate;
    private javax.swing.JTextField textSO_soSexid1;
    private javax.swing.JTextField textSO_soStatus;
    private javax.swing.JTextField textSO_soexid1;
    private javax.swing.JTextField textSO_soid;
    private javax.swing.JTextField textSO_soid1;
    private javax.swing.JTextField textSO_sotot;
    private javax.swing.JTextField textSO_sotot1;
    private javax.swing.JTextField textSO_sphone;
    private javax.swing.JTextField textSO_sphone1;
    private javax.swing.JTextField text_AEXaexid;
    private javax.swing.JTextField text_AEXaid;
    private javax.swing.JTextField text_AEXcoid;
    private javax.swing.JTextField text_AEXemail;
    private javax.swing.JTextField text_AEXexid;
    private javax.swing.JTextField text_AEXfname;
    private javax.swing.JTextField text_AEXlname;
    private javax.swing.JTextField text_AEXoamount;
    private javax.swing.JTextField text_AEXphone;
    private javax.swing.JTextField text_AEXrate;
    private javax.swing.JTextField text_AEXsoid;
    private javax.swing.JTextField text_AEXtot;
    private javax.swing.JTextField text_exGPbonus;
    private javax.swing.JTextField text_exGPemail;
    private javax.swing.JTextField text_exGPfname;
    private javax.swing.JTextField text_exGPfsalary;
    private javax.swing.JTextField text_exGPid;
    private javax.swing.JTextField text_exGPlname;
    private javax.swing.JTextField text_exGPphone;
    private javax.swing.JTextField text_exGPtot;
    private javax.swing.JTextField text_salary;
    private javax.swing.JTextField text_uadd1;
    private javax.swing.JTextField text_uadd10;
    private javax.swing.JTextField text_uadd11;
    private javax.swing.JTextField text_uadd12;
    private javax.swing.JTextField text_uadd13;
    private javax.swing.JTextField text_uadd14;
    private javax.swing.JTextField text_uadd15;
    private javax.swing.JTextField text_uadd2;
    private javax.swing.JTextField text_uadd3;
    private javax.swing.JTextField text_uadd4;
    private javax.swing.JTextField text_uadd5;
    private javax.swing.JTextField text_uadd6;
    private javax.swing.JTextField text_uadd7;
    private javax.swing.JTextField text_uadd8;
    private javax.swing.JTextField text_uadd9;
    private javax.swing.JTextField text_ubacc;
    private javax.swing.JTextField text_ubacc1;
    private javax.swing.JTextField text_ubacc2;
    private javax.swing.JTextField text_uemail;
    private javax.swing.JTextField text_uemail1;
    private javax.swing.JTextField text_uemail2;
    private javax.swing.JTextField text_uemail3;
    private javax.swing.JTextField text_uemail4;
    private javax.swing.JTextField text_ufname;
    private javax.swing.JTextField text_ufname1;
    private javax.swing.JTextField text_ufname2;
    private javax.swing.JTextField text_ufname3;
    private javax.swing.JTextField text_ufname4;
    private javax.swing.JTextField text_ugemail;
    private javax.swing.JTextField text_ugfname;
    private javax.swing.JTextField text_uglname;
    private javax.swing.JTextField text_ugphone;
    private javax.swing.JTextField text_ulname;
    private javax.swing.JTextField text_ulname1;
    private javax.swing.JTextField text_ulname2;
    private javax.swing.JTextField text_ulname3;
    private javax.swing.JTextField text_ulname4;
    private javax.swing.JTextField text_ulno;
    private javax.swing.JTextField text_unic;
    private javax.swing.JTextField text_unic1;
    private javax.swing.JTextField text_unic2;
    private javax.swing.JTextField text_unic3;
    private javax.swing.JTextField text_unic4;
    private javax.swing.JTextField text_uphone;
    private javax.swing.JTextField text_uphone1;
    private javax.swing.JTextField text_uphone2;
    private javax.swing.JTextField text_uphone3;
    private javax.swing.JTextField text_uphone4;
    private javax.swing.JTextField text_upwd;
    private javax.swing.JTextField text_upwd1;
    private javax.swing.JTextField text_upwd2;
    private javax.swing.JTextField text_upwd3;
    private javax.swing.JTextField text_upwd4;
    private javax.swing.JTextField textex_gempolisherSeach;
    private javax.swing.JTextField txt_cid1;
    private javax.swing.JTextField txt_fg_LicenseID;
    private javax.swing.JTextField txt_fg_buyprice;
    private javax.swing.JTextField txt_fg_color;
    private javax.swing.JTextField txt_fg_name;
    private javax.swing.JTextField txt_fg_pieces;
    private javax.swing.JTextField txt_fg_sellprice;
    private javax.swing.JTextField txt_fg_shape;
    private javax.swing.JTextField txt_fg_status;
    private javax.swing.JTextField txt_fg_type;
    private javax.swing.JTextField txt_fg_weight;
    private javax.swing.JTextField txt_gOrigin;
    private javax.swing.JTextField txt_gcolor;
    private javax.swing.JTextField txt_gcolor1;
    private javax.swing.JTextField txt_gid;
    private javax.swing.JTextField txt_gname;
    private javax.swing.JTextField txt_gname1;
    private javax.swing.JTextField txt_gnature;
    private javax.swing.JTextField txt_gpieces1;
    private javax.swing.JTextField txt_gshape1;
    private javax.swing.JTextField txt_gsprice1;
    private javax.swing.JTextField txt_gtype;
    private javax.swing.JTextField txt_gtype1;
    private javax.swing.JTextField txt_gweight;
    private javax.swing.JTextField txt_gweight1;
    private javax.swing.JTextField txt_rg_Status;
    private javax.swing.JTextField txt_rg_buyPrice;
    private javax.swing.JTextField txt_rg_color;
    private javax.swing.JTextField txt_rg_name;
    private javax.swing.JTextField txt_rg_nature;
    private javax.swing.JTextField txt_rg_origin;
    private javax.swing.JTextField txt_rg_sellPrice;
    private javax.swing.JTextField txt_rg_type;
    private javax.swing.JTextField txt_rg_weight;
    // End of variables declaration//GEN-END:variables
private ImageIcon ResizeImage(String imgPath){
   int imageX = 177;
   int imageY = 202;
   lbl_rawgemimage.setSize(imageX,imageY);
    
   ImageIcon myImage = new ImageIcon(imgPath);
   Image img = myImage.getImage();
   Image newImage = img.getScaledInstance(lbl_rawgemimage.getWidth(), lbl_rawgemimage.getHeight(), Image.SCALE_SMOOTH);
   ImageIcon image = new ImageIcon(newImage);
   return image;
}
}
