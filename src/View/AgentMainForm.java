
package View;

import AppPackage.AnimationClass;
import Model.Client;
import Model.FinishedGem;
import Model.Gem;
import Model.MapMain;
import Model.RawGem;
import Model.SqlConnection;
import Model.Supplier;
import Model.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;


public class AgentMainForm extends javax.swing.JFrame {

    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;
     public String code;
    AnimationClass ac = new AnimationClass();

     String u_id;
     String s_id;
     String c_id;
     String u_fname;
     String u_lname;
     String u_ad_1;
     String u_ad_2;
     String u_ad_3;
     String u_phone;
     String u_dob;
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
    
    int uu_id;
    int ss_id;
    int cc_id;


       public int g_id;
       public int g_fid;
       public int g_rid;
       public String today;
       public String g_name;
       public String g_type;
       public String g_color;
       public String g_weight;
       public String g_bprice;
       public String g_sprice;
       public String g_status;
       public String g_adate;
       public String g_variety;

       public String g_ffid;
       public String g_cid;
       public String g_shape;
       public String g_pieces;

       public String g_rrid;
       public String g_origin;
       public String g_nature;
       public String sup_id;

      public int s_oid;
      public String s_ooid;

    
    public AgentMainForm(String code) {
        this.code = code;  
        initComponents();
        getID(); 
        calUserID();
        calSupID();
        calCliID();
        calToday();
        getDate();
        fetchSupplier();
        fetch();
  

        label_ubacc.setVisible(false);
        label_ulno.setVisible(false);
        text_ubacc.setVisible(false);
        text_ulno.setVisible(false);
        label_uregion.setVisible(false);
        comboURegion.setVisible(false);
 
          

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
     private void getID(){

         try{
            lblUserName.setText(code);
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

    private void getDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        today = sdf.format(date);
    }

    private void setUserData(){
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     u_fname =text_ufname.getText();
     u_lname =text_ulname.getText();
     u_ad_1 = text_uadd1.getText();
     u_ad_2 = text_uadd2.getText();
     u_ad_3 = text_uadd3.getText();
     u_phone= text_uphone.getText();
    if (datechooser_udob.getDate() == null) {
         Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        u_jdate = sdf1.format(date);
        datechooser_udob.setDate(date); JOptionPane.showMessageDialog(null, "Please type birthday", "Warning!", JOptionPane.ERROR_MESSAGE);
        }else{u_dob= sdf.format(datechooser_udob.getDate());
         ageCalc();
       }

     
     u_nic= text_unic.getText();
     u_email= text_uemail.getText();
     u_pwd= text_upwd.getText();
     u_type= comboUType.getSelectedItem().toString();
     u_ctype = comboURegion.getSelectedItem().toString();
     u_scid = text_ulno.getText();
     u_sbacc = text_ubacc.getText();
     validateUser();                     
    }

private void validateUser(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(u_email); 

       if(u_type.equals("Select")){
            JOptionPane.showMessageDialog(this, "Please Select User Type!");
        }else if(u_fname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your First Name!");
        }else if(u_fname.length()<3){
            JOptionPane.showMessageDialog(this,"Your First Name should have at least Three Characters");
        }else if(u_lname.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Your Last Name!");
        }else if(u_lname.length()<3){
            JOptionPane.showMessageDialog(this,"Your Last Name should have at least Three Characters");
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
        }else if(datechooser_udob.getDate() == null){
            JOptionPane.showMessageDialog(this,"Please Enter Your BirthDay!");
        }else if(u_dob.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Date of Birth!");
        }else if(u_age<18){
            JOptionPane.showMessageDialog(this,"You should be at Least 18 Years old!");
        }else if(u_nic.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your NIC or Passport Number!");
        }else if(u_nic.length()<8||u_nic.length()>10){
            JOptionPane.showMessageDialog(this,"Please Enter Valid NIC or Passport Number!");
        }else if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Email Address!");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this,"Please Enter Correct Email!");
        }else if(u_phone.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Contact Number!");
        }else if(u_phone.length()< 8 || u_phone.length()>10){
            JOptionPane.showMessageDialog(this,"Input Valid Contact Number!");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please Enter Your Password!");
        }else if(u_pwd.length()<6 || u_pwd.length()>14){
            JOptionPane.showMessageDialog(this,"Tour Password must have more than 6 Characters");
        }else if(u_type.equals("Client")){
            validateClient();
        }else if(u_type.equals("Supplier")){
            validateSupplier();
        }
        else{
              JOptionPane.showMessageDialog(this," System Error");
        }
}


public void loadreport() {


        HashMap a = new HashMap();
        a.put("Aid", code);
        a.put("date", today);
        a.put("status", status);
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\AgentCommision.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad.setLayout(new BorderLayout());
              panelLoad.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}

public void loadGraphreport() {


        HashMap a = new HashMap();
        a.put("Aid", code);
        a.put("date", today);
        a.put("status", status);
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\GraphAgentCommission.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad.setLayout(new BorderLayout());
              panelLoad.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}


public void loadFilteredreport() {
       SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd");
       String fdate=date1.format(datechooser_from.getDate());
       SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd");
       String tdate=date2.format(datechooser_to.getDate());


        HashMap a = new HashMap();
        a.put("Aid", code);
        a.put("date", today);
        a.put("sdate", fdate);
        a.put("edate", tdate);
       a.put("status",status );
        panelLoad1.removeAll();
        panelLoad1.repaint();
        panelLoad1.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\FilteredAgentreport.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad1.setLayout(new BorderLayout());
              panelLoad1.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}


private void ageCalc()
{
   
    Date date1 = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    int YearNow = Integer.parseInt(sdf.format(date1));
    int YearSel = Integer.parseInt(sdf.format(datechooser_udob.getDate()));
    int age;
    u_age = YearNow - YearSel;
    System.out.println(""+u_age);
}
    private void calToday(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        u_jdate = sdf.format(date);
        datechooser_udob.setDate(date);
        System.out.println(""+u_jdate);
}
private void validateClient(){
    if (u_ctype.equals("Select")) {
         JOptionPane.showMessageDialog(this,"Please Select Your Region");
    } else {

     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_type, this);
     main.inputUser();

    Client mainc = new Client();
    mainc.setClient(c_id, u_id, code, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_ctype, this);
    mainc.inputClient();
    fetchClient();
    clear();
    
    }
}
private void validateSupplier(){
    if (u_sbacc.equals("")) {
         JOptionPane.showMessageDialog(this,"Please Your Bank Account Number");
    }else if(u_sbacc.length()<8||u_sbacc.length()>15){
         JOptionPane.showMessageDialog(this,"Please Valid Bank Account Number");
    }else if(u_scid.equals("")){
         JOptionPane.showMessageDialog(this,"Please Your Gem Selling License Number");
    }else if(u_scid.length()<8||u_scid.length()>15){
         JOptionPane.showMessageDialog(this,"Please Valid Gem Selling License Number");
    }else {

     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_type, this);
     main.inputUser();
     
     Supplier mains = new Supplier();
     mains.setSupplier(s_id, u_id, code, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_scid, u_sbacc, this);
     mains.inputSupplier();
     fetchSupplier();
     clear();
     
    }
}
public void clear(){
      text_ufname.setText("");
      text_ulname.setText("");
      text_uadd1.setText("");
      text_uadd2.setText("");
      text_uadd3.setText("");
      text_uphone.setText("");
      datechooser_udob.setDate(null);
      text_unic.setText("");
      text_uemail.setText("");
      text_upwd.setText("");
      comboUType.setSelectedIndex(0);
      comboURegion.setSelectedIndex(0);
      text_ulno.setText("");
      text_ubacc.setText("");     
}
private void hideFields(){
     if((comboUType.getSelectedItem().toString()).equals("Client")){
            label_ubacc.setVisible(false);
            label_ulno.setVisible(false);
            text_ubacc.setVisible(false);
            text_ulno.setVisible(false);
            label_uregion.setVisible(true);
            comboURegion.setVisible(true);

            }
        else if((comboUType.getSelectedItem().toString()).equals("Supplier"))
        {
            label_ubacc.setVisible(true);
            label_ulno.setVisible(true);
            text_ubacc.setVisible(true);
            text_ulno.setVisible(true);
            label_uregion.setVisible(false);
            comboURegion.setVisible(false);
        }
        else{
            label_ubacc.setVisible(false);
            label_ulno.setVisible(false);
            text_ubacc.setVisible(false);
            text_ulno.setVisible(false);
            label_uregion.setVisible(false);
            comboURegion.setVisible(false);
            }
    }




    private void fetchSupplier(){
        Connection con = SqlConnection.getCon();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{String query = "SELECT * FROM supplier where a_no =?";
           pst = con.prepareStatement(query);
           pst.setString(1,code);
           rs=pst.executeQuery();
           tbl_supCli.setModel(DbUtils.resultSetToTableModel(rs));              
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
    
    
    private void fetchClient(){
        Connection con = SqlConnection.getCon();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{String query = "SELECT * FROM client where a_no =?";
           pst = con.prepareStatement(query);
           pst.setString(1,code);
           rs=pst.executeQuery();
           tbl_supCli.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetch(){
        Connection con = SqlConnection.getCon();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{String query = "SELECT * FROM finish where fg_status =?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Finished");
           rs=pst.executeQuery();
           tbl_fgems.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
private void fetchClick(){
       int row = tbl_fgems.getSelectedRow();
       String tc =tbl_fgems.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `finish` where fg_status =? and fg_id=? "; 
           pst = con.prepareStatement(query);
           pst.setString(1,"Finished");
           pst.setString(2,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                String  fg_id=rs.getString("fg_id");
                int  g_no=rs.getInt("g_no");;
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
                 

                 txt_gname.setText(fg_name);
                 txt_gtype.setText(fg_type);
                 txt_gcolor.setText(fg_color);
                 txt_gweight.setText(fg_weight);
                 txt_gsprice.setText(fg_sprice);
                 txt_cid.setText(fg_cid);
                 txt_gshape.setText(fg_shape);
                 txt_gpieces.setText(fg_pieces);
                 
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        btnMnageGems = new javax.swing.JPanel();
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
        btnReports = new javax.swing.JPanel();
        lblBar4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnHelp = new javax.swing.JPanel();
        lblBar5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
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
        jInternalFrameManageUsers = new javax.swing.JInternalFrame();
        label_utype = new javax.swing.JLabel();
        comboUType = new javax.swing.JComboBox<>();
        label_ufname = new javax.swing.JLabel();
        text_ufname = new javax.swing.JTextField();
        label_ulname = new javax.swing.JLabel();
        text_ulname = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        label_uaddress = new javax.swing.JLabel();
        text_uadd1 = new javax.swing.JTextField();
        text_uadd2 = new javax.swing.JTextField();
        text_uadd3 = new javax.swing.JTextField();
        datechooser_udob = new com.toedter.calendar.JDateChooser();
        label_udob = new javax.swing.JLabel();
        label_unic = new javax.swing.JLabel();
        text_unic = new javax.swing.JTextField();
        text_uemail = new javax.swing.JTextField();
        label_uemail = new javax.swing.JLabel();
        label_uphone = new javax.swing.JLabel();
        text_uphone = new javax.swing.JTextField();
        text_upwd = new javax.swing.JTextField();
        label_upwd = new javax.swing.JLabel();
        label_uregion = new javax.swing.JLabel();
        comboURegion = new javax.swing.JComboBox<>();
        text_ulno = new javax.swing.JTextField();
        label_ulno = new javax.swing.JLabel();
        label_ubacc = new javax.swing.JLabel();
        text_ubacc = new javax.swing.JTextField();
        btnClear = new View.MyButton();
        btn_register = new View.MyButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_supCli = new javax.swing.JTable();
        btn_viewClient = new View.MyButton();
        btn_viewSupplier = new View.MyButton();
        jInternalFrameManageGems = new javax.swing.JInternalFrame();
        lbl_gname = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_fgems = new javax.swing.JTable();
        txt_gname = new javax.swing.JTextField();
        lbl_gsprice = new javax.swing.JLabel();
        txt_gsprice = new javax.swing.JTextField();
        txt_gshape = new javax.swing.JTextField();
        txt_cid = new javax.swing.JTextField();
        txt_gpieces = new javax.swing.JTextField();
        lbl_gpieces = new javax.swing.JLabel();
        lbl_certiID1 = new javax.swing.JLabel();
        lbl_gcid = new javax.swing.JLabel();
        lbl_gshape = new javax.swing.JLabel();
        txt_gtype = new javax.swing.JTextField();
        txt_gcolor = new javax.swing.JTextField();
        txt_gweight = new javax.swing.JTextField();
        lbl_weight = new javax.swing.JLabel();
        lbl_gweight = new javax.swing.JLabel();
        lbl_gcolor = new javax.swing.JLabel();
        lbl_gtype = new javax.swing.JLabel();
        lbl_gname1 = new javax.swing.JLabel();
        lbl_gemImage = new javax.swing.JLabel();
        jInternalFrameReports = new javax.swing.JInternalFrame();
        panelLoad = new javax.swing.JPanel();
        btnGenerateReport = new View.MyButton();
        btnGenerateGraphReport = new View.MyButton();
        btnfilter = new View.MyButton();
        jInternalFrameHelp = new javax.swing.JInternalFrame();
        jLabel86 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        btnMap = new View.MyButton();
        jLabel75 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jInternalFrameAgentProfile = new javax.swing.JInternalFrame();
        jLabel46 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        btnClear2 = new View.MyButton();
        btnSave = new View.MyButton();
        jInternalFrameReports1 = new javax.swing.JInternalFrame();
        panelLoad1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnBack6 = new View.MyButton();
        btnGenerate = new View.MyButton();
        datechooser_to = new com.toedter.calendar.JDateChooser();
        datechooser_from = new com.toedter.calendar.JDateChooser();
        jInternalFrameHome = new javax.swing.JInternalFrame();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(46, 43, 43));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMnageGems.setBackground(new java.awt.Color(46, 43, 43));
        btnMnageGems.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMnageGems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMnageGemsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMnageGemsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMnageGemsMouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-jewel-30 (1).png"))); // NOI18N

        lblBar3.setBackground(new java.awt.Color(255, 51, 51));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Gems");

        javax.swing.GroupLayout btnMnageGemsLayout = new javax.swing.GroupLayout(btnMnageGems);
        btnMnageGems.setLayout(btnMnageGemsLayout);
        btnMnageGemsLayout.setHorizontalGroup(
            btnMnageGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnMnageGemsLayout.createSequentialGroup()
                .addComponent(lblBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnMnageGemsLayout.setVerticalGroup(
            btnMnageGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnMnageGemsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnMnageGemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnMnageGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 170, 60));

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

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
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
                .addContainerGap(42, Short.MAX_VALUE))
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

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
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
                .addContainerGap(47, Short.MAX_VALUE))
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

        lblBar4.setBackground(new java.awt.Color(255, 51, 51));

        jLabel13.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/007.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Reports");

        javax.swing.GroupLayout btnReportsLayout = new javax.swing.GroupLayout(btnReports);
        btnReports.setLayout(btnReportsLayout);
        btnReportsLayout.setHorizontalGroup(
            btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReportsLayout.createSequentialGroup()
                .addComponent(lblBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        btnReportsLayout.setVerticalGroup(
            btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnReportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu.add(btnReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 170, -1));

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

        lblBar5.setBackground(new java.awt.Color(255, 51, 51));

        jLabel16.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/006.png"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Help");

        javax.swing.GroupLayout btnHelpLayout = new javax.swing.GroupLayout(btnHelp);
        btnHelp.setLayout(btnHelpLayout);
        btnHelpLayout.setHorizontalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHelpLayout.createSequentialGroup()
                .addComponent(lblBar5, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        btnHelpLayout.setVerticalGroup(
            btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblBar5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnHelpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu.add(btnHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 170, -1));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bbrdbb.png"))); // NOI18N
        jLabel34.setText("jLabel34");
        menu.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 380, 340, 450));

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
        lblUserName1.setText("Agent ID  :");

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
                    .addComponent(lblUserName1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(592, 592, 592))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnProfile)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 840, 50));

        form1.setBackground(new java.awt.Color(255, 255, 255));
        form1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrameManageUsers.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageUsers.setBorder(null);
        jInternalFrameManageUsers.setVisible(false);
        jInternalFrameManageUsers.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_utype.setBackground(new java.awt.Color(255, 255, 255));
        label_utype.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_utype.setText("Account for   :");
        jInternalFrameManageUsers.getContentPane().add(label_utype, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 100, 30));

        comboUType.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboUType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Client", "Supplier" }));
        comboUType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUTypeActionPerformed(evt);
            }
        });
        jInternalFrameManageUsers.getContentPane().add(comboUType, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 180, -1));

        label_ufname.setBackground(new java.awt.Color(255, 255, 255));
        label_ufname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_ufname.setText("First Name    :");
        jInternalFrameManageUsers.getContentPane().add(label_ufname, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 100, -1));

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
        jInternalFrameManageUsers.getContentPane().add(text_ufname, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 80, 30));

        label_ulname.setBackground(new java.awt.Color(255, 255, 255));
        label_ulname.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_ulname.setText("Last Name  :");
        jInternalFrameManageUsers.getContentPane().add(label_ulname, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, -1, -1));

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
        jInternalFrameManageUsers.getContentPane().add(text_ulname, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 280, 100, 20));

        jLabel49.setBackground(new java.awt.Color(255, 255, 255));
        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel49.setText("Register an User");
        jInternalFrameManageUsers.getContentPane().add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        label_uaddress.setBackground(new java.awt.Color(255, 255, 255));
        label_uaddress.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_uaddress.setText("Address         :");
        jInternalFrameManageUsers.getContentPane().add(label_uaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 100, -1));

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
        jInternalFrameManageUsers.getContentPane().add(text_uadd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 90, 30));

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
        jInternalFrameManageUsers.getContentPane().add(text_uadd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 320, 90, 30));

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
        jInternalFrameManageUsers.getContentPane().add(text_uadd3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 320, 100, 30));

        datechooser_udob.setBackground(new java.awt.Color(255, 255, 255));
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
        jInternalFrameManageUsers.getContentPane().add(datechooser_udob, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 130, 30));

        label_udob.setBackground(new java.awt.Color(255, 255, 255));
        label_udob.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_udob.setText("Date of Birth :");
        jInternalFrameManageUsers.getContentPane().add(label_udob, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 110, -1));

        label_unic.setBackground(new java.awt.Color(255, 255, 255));
        label_unic.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_unic.setText("NIC Number  :");
        jInternalFrameManageUsers.getContentPane().add(label_unic, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, -1, -1));

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
        jInternalFrameManageUsers.getContentPane().add(text_unic, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 130, 30));

        text_uemail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uemailActionPerformed(evt);
            }
        });
        text_uemail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_uemailKeyPressed(evt);
            }
        });
        jInternalFrameManageUsers.getContentPane().add(text_uemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 250, 130, 30));

        label_uemail.setBackground(new java.awt.Color(255, 255, 255));
        label_uemail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_uemail.setText("Email                   :");
        jInternalFrameManageUsers.getContentPane().add(label_uemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 260, -1, -1));

        label_uphone.setBackground(new java.awt.Color(255, 255, 255));
        label_uphone.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_uphone.setText("Contact Number:");
        jInternalFrameManageUsers.getContentPane().add(label_uphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 290, -1, -1));

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
        jInternalFrameManageUsers.getContentPane().add(text_uphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 280, 130, 30));

        text_upwd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_upwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_upwdActionPerformed(evt);
            }
        });
        text_upwd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_upwdKeyPressed(evt);
            }
        });
        jInternalFrameManageUsers.getContentPane().add(text_upwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 320, 130, 30));

        label_upwd.setBackground(new java.awt.Color(255, 255, 255));
        label_upwd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_upwd.setText("Password            :");
        jInternalFrameManageUsers.getContentPane().add(label_upwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 330, -1, -1));

        label_uregion.setBackground(new java.awt.Color(255, 255, 255));
        label_uregion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_uregion.setText("Region                :");
        jInternalFrameManageUsers.getContentPane().add(label_uregion, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 370, -1, -1));

        comboURegion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboURegion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "SriLanka", "China" }));
        comboURegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboURegionActionPerformed(evt);
            }
        });
        jInternalFrameManageUsers.getContentPane().add(comboURegion, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 360, 130, -1));

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
        jInternalFrameManageUsers.getContentPane().add(text_ulno, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 390, 130, 30));

        label_ulno.setBackground(new java.awt.Color(255, 255, 255));
        label_ulno.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_ulno.setText("License Number :");
        jInternalFrameManageUsers.getContentPane().add(label_ulno, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 410, -1, -1));

        label_ubacc.setBackground(new java.awt.Color(255, 255, 255));
        label_ubacc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        label_ubacc.setText("Bank Number     :");
        jInternalFrameManageUsers.getContentPane().add(label_ubacc, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 370, -1, -1));

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
        jInternalFrameManageUsers.getContentPane().add(text_ubacc, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 360, 130, 30));

        btnClear.setText("Clear");
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jInternalFrameManageUsers.getContentPane().add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 480, 110, 30));

        btn_register.setText("Register");
        btn_register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_registerMouseClicked(evt);
            }
        });
        btn_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registerActionPerformed(evt);
            }
        });
        jInternalFrameManageUsers.getContentPane().add(btn_register, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, 110, 30));

        tbl_supCli.setAutoCreateRowSorter(true);
        tbl_supCli.setBackground(new java.awt.Color(204, 204, 204));
        tbl_supCli.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_supCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_supCliMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_supCli);

        jInternalFrameManageUsers.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 800, 160));

        btn_viewClient.setText("View Client");
        btn_viewClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewClientActionPerformed(evt);
            }
        });
        jInternalFrameManageUsers.getContentPane().add(btn_viewClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 480, 110, 30));

        btn_viewSupplier.setText("View Suppliers");
        btn_viewSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewSupplierActionPerformed(evt);
            }
        });
        jInternalFrameManageUsers.getContentPane().add(btn_viewSupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 480, 130, 30));

        form1.add(jInternalFrameManageUsers, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        jInternalFrameManageGems.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameManageGems.setVisible(false);
        jInternalFrameManageGems.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_gname.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gname.setText("Available Finished Gems");
        jInternalFrameManageGems.getContentPane().add(lbl_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        tbl_fgems.setBackground(new java.awt.Color(102, 153, 255));
        tbl_fgems.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_fgems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_fgemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_fgems);

        jInternalFrameManageGems.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 790, 200));

        txt_gname.setEditable(false);
        txt_gname.setBackground(new java.awt.Color(255, 255, 255));
        txt_gname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gnameActionPerformed(evt);
            }
        });
        jInternalFrameManageGems.getContentPane().add(txt_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 330, 160, 40));

        lbl_gsprice.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gsprice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gsprice.setText("Gem price");
        jInternalFrameManageGems.getContentPane().add(lbl_gsprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 350, -1, -1));

        txt_gsprice.setEditable(false);
        txt_gsprice.setBackground(new java.awt.Color(255, 255, 255));
        txt_gsprice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gsprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gspriceActionPerformed(evt);
            }
        });
        jInternalFrameManageGems.getContentPane().add(txt_gsprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 330, 160, 40));

        txt_gshape.setEditable(false);
        txt_gshape.setBackground(new java.awt.Color(255, 255, 255));
        txt_gshape.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gshape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gshapeActionPerformed(evt);
            }
        });
        jInternalFrameManageGems.getContentPane().add(txt_gshape, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 370, 160, 40));

        txt_cid.setEditable(false);
        txt_cid.setBackground(new java.awt.Color(255, 255, 255));
        txt_cid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_cid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cidActionPerformed(evt);
            }
        });
        jInternalFrameManageGems.getContentPane().add(txt_cid, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 410, 160, 40));

        txt_gpieces.setEditable(false);
        txt_gpieces.setBackground(new java.awt.Color(255, 255, 255));
        txt_gpieces.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gpieces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gpiecesActionPerformed(evt);
            }
        });
        jInternalFrameManageGems.getContentPane().add(txt_gpieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 450, 160, 40));

        lbl_gpieces.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gpieces.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gpieces.setText("Gem pieces");
        jInternalFrameManageGems.getContentPane().add(lbl_gpieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 470, -1, -1));

        lbl_certiID1.setText("Gem Certificate ID");
        jInternalFrameManageGems.getContentPane().add(lbl_certiID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 450, -1, -1));

        lbl_gcid.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gcid.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gcid.setText("Gem ID");
        jInternalFrameManageGems.getContentPane().add(lbl_gcid, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, -1, -1));

        lbl_gshape.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gshape.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gshape.setText("Gem shape");
        jInternalFrameManageGems.getContentPane().add(lbl_gshape, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 390, -1, -1));

        txt_gtype.setEditable(false);
        txt_gtype.setBackground(new java.awt.Color(255, 255, 255));
        txt_gtype.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gtypeActionPerformed(evt);
            }
        });
        jInternalFrameManageGems.getContentPane().add(txt_gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 370, 160, 40));

        txt_gcolor.setEditable(false);
        txt_gcolor.setBackground(new java.awt.Color(255, 255, 255));
        txt_gcolor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gcolor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gcolorActionPerformed(evt);
            }
        });
        jInternalFrameManageGems.getContentPane().add(txt_gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 410, 160, 40));

        txt_gweight.setEditable(false);
        txt_gweight.setBackground(new java.awt.Color(255, 255, 255));
        txt_gweight.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gweightActionPerformed(evt);
            }
        });
        jInternalFrameManageGems.getContentPane().add(txt_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, 160, 40));

        lbl_weight.setText("Carats");
        jInternalFrameManageGems.getContentPane().add(lbl_weight, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 490, -1, 20));

        lbl_gweight.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gweight.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gweight.setText("Gem Weight");
        jInternalFrameManageGems.getContentPane().add(lbl_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, -1, -1));

        lbl_gcolor.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gcolor.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gcolor.setText("Gem Color");
        jInternalFrameManageGems.getContentPane().add(lbl_gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, -1, -1));

        lbl_gtype.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gtype.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gtype.setText("Gem Type");
        jInternalFrameManageGems.getContentPane().add(lbl_gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, -1, -1));

        lbl_gname1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gname1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gname1.setText("Gem Name");
        jInternalFrameManageGems.getContentPane().add(lbl_gname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));

        lbl_gemImage.setText("                        Image");
        jInternalFrameManageGems.getContentPane().add(lbl_gemImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 350, 170, 150));

        form1.add(jInternalFrameManageGems, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 880, 600));

        jInternalFrameReports.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameReports.setVisible(false);
        jInternalFrameReports.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLoad.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelLoadLayout = new javax.swing.GroupLayout(panelLoad);
        panelLoad.setLayout(panelLoadLayout);
        panelLoadLayout.setHorizontalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
        );
        panelLoadLayout.setVerticalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        jInternalFrameReports.getContentPane().add(panelLoad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 830, 530));

        btnGenerateReport.setText("Generate Commision Report");
        btnGenerateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateReportActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnGenerateReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 200, 30));

        btnGenerateGraphReport.setText("Generate Graph Report");
        btnGenerateGraphReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateGraphReportActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnGenerateGraphReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 220, 30));

        btnfilter.setText("Filter Report By Date");
        btnfilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfilterActionPerformed(evt);
            }
        });
        jInternalFrameReports.getContentPane().add(btnfilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, 220, 30));

        form1.add(jInternalFrameReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, 880, 610));

        jInternalFrameHelp.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameHelp.setVisible(false);
        jInternalFrameHelp.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel86.setBackground(new java.awt.Color(255, 255, 255));
        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel86.setText("Explore and Learn More About Gem Varieties and Ashraf Gems");
        jInternalFrameHelp.getContentPane().add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, 30));

        jLabel77.setBackground(new java.awt.Color(255, 255, 255));
        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel77.setText("Scan the QR Code Using Your Mobile Phone");
        jInternalFrameHelp.getContentPane().add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, 30));

        jLabel76.setBackground(new java.awt.Color(255, 255, 255));
        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel76.setText("Find us one Map");
        jInternalFrameHelp.getContentPane().add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 30));

        btnMap.setText("Click Here");
        btnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapActionPerformed(evt);
            }
        });
        jInternalFrameHelp.getContentPane().add(btnMap, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 100, 40));

        jLabel75.setBackground(new java.awt.Color(255, 255, 255));
        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel75.setText("Contact us :");
        jInternalFrameHelp.getContentPane().add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        jLabel82.setBackground(new java.awt.Color(255, 255, 255));
        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-call-30.png"))); // NOI18N
        jInternalFrameHelp.getContentPane().add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, -1, 30));

        jLabel83.setBackground(new java.awt.Color(255, 255, 255));
        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-mail-30.png"))); // NOI18N
        jInternalFrameHelp.getContentPane().add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 40, 20));

        jLabel74.setBackground(new java.awt.Color(255, 255, 255));
        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-address-30.png"))); // NOI18N
        jInternalFrameHelp.getContentPane().add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 40, 30));

        jLabel87.setBackground(new java.awt.Color(255, 255, 255));
        jLabel87.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel87.setText("NO 68/10 DHARGAROAD CHINAFORT BERUWALA");
        jInternalFrameHelp.getContentPane().add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, -1, 30));

        jLabel88.setBackground(new java.awt.Color(255, 255, 255));
        jLabel88.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel88.setText("ASHRAFGEMS@GMAIL.COM");
        jInternalFrameHelp.getContentPane().add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, 30));

        jLabel85.setBackground(new java.awt.Color(255, 255, 255));
        jLabel85.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel85.setText("0773431901 /  0112874534");
        jInternalFrameHelp.getContentPane().add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, -1, 30));

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

        jInternalFrameHelp.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 220, 200));

        jLabel79.setBackground(new java.awt.Color(255, 255, 255));
        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-facebook-30.png"))); // NOI18N
        jLabel79.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameHelp.getContentPane().add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, -1, 30));

        jLabel80.setBackground(new java.awt.Color(255, 255, 255));
        jLabel80.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-instagram-30.png"))); // NOI18N
        jLabel80.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameHelp.getContentPane().add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 320, -1, 30));

        jLabel81.setBackground(new java.awt.Color(255, 255, 255));
        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-wechat-30.png"))); // NOI18N
        jLabel81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameHelp.getContentPane().add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 320, -1, 30));

        jLabel78.setBackground(new java.awt.Color(255, 255, 255));
        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel78.setText("---------------------------------------------------------------------------------------------------------");
        jInternalFrameHelp.getContentPane().add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, 30));

        jLabel89.setBackground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Licence Dealer of National Gem and Jewellery Authority");
        jInternalFrameHelp.getContentPane().add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, 30));

        jLabel90.setBackground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Member of Chinafort Gem and Jewellery Trade Association (CGJTA)");
        jInternalFrameHelp.getContentPane().add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, -1, 30));

        jLabel91.setBackground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Registered Exporter of Sri Lanka Export Development Board");
        jInternalFrameHelp.getContentPane().add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, -1, 30));

        jLabel84.setBackground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Copyright © Ashraf Gems 2022");
        jInternalFrameHelp.getContentPane().add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 490, -1, 30));

        form1.add(jInternalFrameHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameAgentProfile.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameAgentProfile.setVisible(false);
        jInternalFrameAgentProfile.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setBackground(new java.awt.Color(255, 255, 255));
        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel46.setText("Manage your profile");
        jInternalFrameAgentProfile.getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        jLabel55.setBackground(new java.awt.Color(46, 43, 43));
        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel55.setText("User ID");
        jInternalFrameAgentProfile.getContentPane().add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, -1));

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 96, 180, -1));

        jLabel50.setBackground(new java.awt.Color(46, 43, 43));
        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel50.setText("First Name");
        jInternalFrameAgentProfile.getContentPane().add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, -1));

        jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 145, 180, -1));

        jLabel51.setBackground(new java.awt.Color(46, 43, 43));
        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel51.setText("Last Name");
        jInternalFrameAgentProfile.getContentPane().add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, -1, -1));

        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 160, -1));

        jLabel48.setBackground(new java.awt.Color(46, 43, 43));
        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setText("Address");
        jInternalFrameAgentProfile.getContentPane().add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, -1, -1));

        jTextField6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 195, 490, -1));

        jLabel56.setBackground(new java.awt.Color(46, 43, 43));
        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel56.setText("Email");
        jInternalFrameAgentProfile.getContentPane().add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, -1, -1));

        jTextField7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 238, 490, -1));

        jLabel57.setBackground(new java.awt.Color(46, 43, 43));
        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel57.setText("NIC Number");
        jInternalFrameAgentProfile.getContentPane().add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        jTextField10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 280, 190, -1));

        jLabel58.setBackground(new java.awt.Color(46, 43, 43));
        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel58.setText("Bank Account No");
        jInternalFrameAgentProfile.getContentPane().add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, -1, -1));

        jTextField11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 290, 190, -1));

        jLabel47.setBackground(new java.awt.Color(46, 43, 43));
        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel47.setText("Phone Number");
        jInternalFrameAgentProfile.getContentPane().add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, -1, -1));

        jTextField9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 190, -1));

        jLabel53.setBackground(new java.awt.Color(46, 43, 43));
        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel53.setText("Work type");
        jInternalFrameAgentProfile.getContentPane().add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 320, -1, -1));

        jTextField8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 330, 190, -1));

        jLabel54.setBackground(new java.awt.Color(46, 43, 43));
        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel54.setText("Joined Date");
        jInternalFrameAgentProfile.getContentPane().add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, -1, -1));

        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameAgentProfile.getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 190, -1));

        btnClear2.setText("Clear");
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
        jInternalFrameAgentProfile.getContentPane().add(btnClear2, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 463, 130, 50));

        btnSave.setText("Save ");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jInternalFrameAgentProfile.getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 463, 140, 50));

        form1.add(jInternalFrameAgentProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(-22, -34, 870, 600));

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
        jInternalFrameReports1.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, -1, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("Date From :");
        jInternalFrameReports1.getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, 30));

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
        jInternalFrameReports1.getContentPane().add(datechooser_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 120, 30));

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
        jInternalFrameReports1.getContentPane().add(datechooser_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 120, 30));

        form1.add(jInternalFrameReports1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, 880, 610));

        jInternalFrameHome.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameHome.setBorder(null);
        jInternalFrameHome.setVisible(true);
        jInternalFrameHome.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111_1.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, -30, 380, 430));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 300, 480));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/stock-vector-vector-illustration-cartoon-property-insurance-smiling-insurance-agent-provides-client-with-a-1249623367-removebg-preview.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 320, -1, -1));

        jLabel43.setBackground(new java.awt.Color(255, 255, 255));
        jLabel43.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 48)); // NOI18N
        jLabel43.setText("WELCOME !");
        jInternalFrameHome.getContentPane().add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, -1));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 50, -1));

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 50, -1));

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 50, -1));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jInternalFrameHome.getContentPane().add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 50, -1));

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setText("Quick User Guide :");
        jInternalFrameHome.getContentPane().add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, 30));

        jLabel61.setBackground(new java.awt.Color(255, 255, 255));
        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel61.setText("Manage(View / Register ) users(Clients / Suppliers).");
        jInternalFrameHome.getContentPane().add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, -1, 30));

        jLabel62.setBackground(new java.awt.Color(255, 255, 255));
        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel62.setText(" View all the finished gem details.");
        jInternalFrameHome.getContentPane().add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, -1, 30));

        jLabel63.setBackground(new java.awt.Color(255, 255, 255));
        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setText("Generate Commission report.");
        jInternalFrameHome.getContentPane().add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, -1, 30));

        jLabel65.setBackground(new java.awt.Color(255, 255, 255));
        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setText("Find more about Ashraf Gems.");
        jInternalFrameHome.getContentPane().add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, -1, 30));

        form1.add(jInternalFrameHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

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
        
        jInternalFrameReports.setVisible(false);
        jInternalFrameHelp.setVisible(false);
        jInternalFrameAgentProfile.setVisible(false);
      
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
        jInternalFrameReports.setVisible(false);
        jInternalFrameHelp.setVisible(false);
        jInternalFrameAgentProfile.setVisible(false);
    }//GEN-LAST:event_btnManageUsersMouseClicked

    private void btnManageUsersMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManageUsersMouseEntered
        btnManageUsers.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnManageUsersMouseEntered

    private void btnManageUsersMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnManageUsersMouseExited
         btnManageUsers.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnManageUsersMouseExited

    private void btnMnageGemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMnageGemsMouseClicked
        bar(lblBar3);
       jInternalFrameHome.setVisible(false);
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGems.setVisible(true);
        jInternalFrameReports.setVisible(false);
        jInternalFrameHelp.setVisible(false);
        jInternalFrameAgentProfile.setVisible(false);
        fetch();
    }//GEN-LAST:event_btnMnageGemsMouseClicked

    private void btnMnageGemsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMnageGemsMouseEntered
          btnMnageGems.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnMnageGemsMouseEntered

    private void btnMnageGemsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMnageGemsMouseExited
        btnMnageGems.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnMnageGemsMouseExited

    private void btnReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseClicked
        bar(lblBar4);
        jInternalFrameHome.setVisible(false);
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGems.setVisible(false);
       
       jInternalFrameReports.setVisible(true);
       jInternalFrameHelp.setVisible(false);
       jInternalFrameAgentProfile.setVisible(false);
    }//GEN-LAST:event_btnReportsMouseClicked

    private void btnReportsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseEntered
       btnReports.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnReportsMouseEntered

    private void btnReportsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseExited
        btnReports.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnReportsMouseExited

    private void btnHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHelpMouseClicked
       bar(lblBar5);
       jInternalFrameHome.setVisible(false);
       jInternalFrameManageUsers.setVisible(false);
       jInternalFrameManageGems.setVisible(false);
       
       jInternalFrameReports.setVisible(false);
       jInternalFrameHelp.setVisible(true);
       jInternalFrameAgentProfile.setVisible(false);
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
       jInternalFrameReports.setVisible(false);
       jInternalFrameHelp.setVisible(false);
       jInternalFrameAgentProfile.setVisible(true);
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
        lblBar3.setOpaque(false);
        lblBar4.setOpaque(false);
        lblBar5.setOpaque(false);
        
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

    private void btnClear2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClear2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClear2MouseClicked

    private void btnClear2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClear2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClear2ActionPerformed

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked

    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void comboUTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUTypeActionPerformed
        hideFields();
    }//GEN-LAST:event_comboUTypeActionPerformed

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

    private void datechooser_udobAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_udobAncestorAdded

    }//GEN-LAST:event_datechooser_udobAncestorAdded

    private void datechooser_udobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMouseClicked

    }//GEN-LAST:event_datechooser_udobMouseClicked

    private void datechooser_udobMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMouseEntered

    }//GEN-LAST:event_datechooser_udobMouseEntered

    private void datechooser_udobMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMouseExited
        ageCalc();
    }//GEN-LAST:event_datechooser_udobMouseExited

    private void datechooser_udobMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMousePressed

    }//GEN-LAST:event_datechooser_udobMousePressed

    private void text_unicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_unicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_unicActionPerformed

    private void text_uemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uemailActionPerformed

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

    private void text_upwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_upwdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_upwdActionPerformed

    private void comboURegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboURegionActionPerformed

    }//GEN-LAST:event_comboURegionActionPerformed

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

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btn_registerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_registerMouseClicked

    }//GEN-LAST:event_btn_registerMouseClicked

    private void btn_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registerActionPerformed
        calUserID();
        calSupID();
        calCliID();
        setUserData();

    }//GEN-LAST:event_btn_registerActionPerformed

    private void tbl_supCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_supCliMouseClicked

    }//GEN-LAST:event_tbl_supCliMouseClicked

    private void btn_viewSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewSupplierActionPerformed
      fetchSupplier();
    }//GEN-LAST:event_btn_viewSupplierActionPerformed

    private void btn_viewClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewClientActionPerformed
       fetchClient();
    }//GEN-LAST:event_btn_viewClientActionPerformed

    private void tbl_fgemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_fgemsMouseClicked
        fetchClick();
    }//GEN-LAST:event_tbl_fgemsMouseClicked

    private void txt_gnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gnameActionPerformed

    private void txt_gspriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gspriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gspriceActionPerformed

    private void txt_gshapeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gshapeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gshapeActionPerformed

    private void txt_cidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cidActionPerformed

    private void txt_gpiecesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gpiecesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gpiecesActionPerformed

    private void txt_gtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gtypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gtypeActionPerformed

    private void txt_gcolorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gcolorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gcolorActionPerformed

    private void txt_gweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gweightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gweightActionPerformed

    private void btnGenerateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateReportActionPerformed
        // TODO add your handling code here:
       loadreport();
    }//GEN-LAST:event_btnGenerateReportActionPerformed

    private void btnGenerateGraphReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateGraphReportActionPerformed
        // TODO add your handling code here:
        loadGraphreport();
    }//GEN-LAST:event_btnGenerateGraphReportActionPerformed

    private void btnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapActionPerformed
        // TODO add your handling code here:
        MapMain map = new MapMain();
        map.setVisible(true);
    }//GEN-LAST:event_btnMapActionPerformed

    private void btnfilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfilterActionPerformed
        jInternalFrameReports.setVisible(false);
        jInternalFrameReports1.setVisible(true);
    }//GEN-LAST:event_btnfilterActionPerformed

    private void btnBack6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack6ActionPerformed
        // TODO add your handling code here:
        jInternalFrameReports1.setVisible(false);
        jInternalFrameReports.setVisible(true);
    }//GEN-LAST:event_btnBack6ActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed

        if(datechooser_from.getDate()==null){
            JOptionPane.showMessageDialog(this,"Select Start date");
        }else if(datechooser_to.getDate()==null){
            JOptionPane.showMessageDialog(this,"Select End date");
        }else{

            loadFilteredreport();
        }
    }//GEN-LAST:event_btnGenerateActionPerformed

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

    private void text_uadd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd2KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd2.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd2.setText("");
        }

    }//GEN-LAST:event_text_uadd2KeyPressed

    private void text_unicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_unicKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_unic.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_unic.setText("");
        }

    }//GEN-LAST:event_text_unicKeyPressed

    private void text_uemailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uemailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uemailKeyPressed

    private void text_upwdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_upwdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_upwdKeyPressed

    private void text_ulnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ulnoKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ulno.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_ulno.setText("");
        }

    }//GEN-LAST:event_text_ulnoKeyPressed
    
    public void bar(JLabel lab){
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
        lblBar3.setOpaque(false);
        lblBar4.setOpaque(false);
        lblBar5.setOpaque(false);
        
        lab.setOpaque(true);
        menu.repaint();
    }
    
    
   
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private View.MyButton btnBack6;
    private View.MyButton btnClear;
    private View.MyButton btnClear2;
    private javax.swing.JLabel btnExit;
    private View.MyButton btnGenerate;
    private View.MyButton btnGenerateGraphReport;
    private View.MyButton btnGenerateReport;
    private javax.swing.JPanel btnHelp;
    private javax.swing.JLabel btnLogOut;
    private javax.swing.JPanel btnManageUsers;
    private View.MyButton btnMap;
    private javax.swing.JLabel btnMiniMize;
    private javax.swing.JPanel btnMnageGems;
    private javax.swing.JLabel btnProfile;
    private javax.swing.JPanel btnReports;
    private View.MyButton btnSave;
    private View.MyButton btn_register;
    private View.MyButton btn_viewClient;
    private View.MyButton btn_viewSupplier;
    private View.MyButton btnfilter;
    private javax.swing.JPanel buttonHome;
    private javax.swing.JComboBox<String> comboURegion;
    private javax.swing.JComboBox<String> comboUType;
    private com.toedter.calendar.JDateChooser datechooser_from;
    private com.toedter.calendar.JDateChooser datechooser_to;
    private com.toedter.calendar.JDateChooser datechooser_udob;
    private javax.swing.JPanel form1;
    private javax.swing.JInternalFrame jInternalFrameAgentProfile;
    private javax.swing.JInternalFrame jInternalFrameHelp;
    private javax.swing.JInternalFrame jInternalFrameHome;
    private javax.swing.JInternalFrame jInternalFrameManageGems;
    private javax.swing.JInternalFrame jInternalFrameManageUsers;
    private javax.swing.JInternalFrame jInternalFrameReports;
    private javax.swing.JInternalFrame jInternalFrameReports1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel label_uaddress;
    private javax.swing.JLabel label_ubacc;
    private javax.swing.JLabel label_udob;
    private javax.swing.JLabel label_uemail;
    private javax.swing.JLabel label_ufname;
    private javax.swing.JLabel label_ulname;
    private javax.swing.JLabel label_ulno;
    private javax.swing.JLabel label_unic;
    private javax.swing.JLabel label_uphone;
    private javax.swing.JLabel label_upwd;
    private javax.swing.JLabel label_uregion;
    private javax.swing.JLabel label_utype;
    private javax.swing.JLabel lblBar1;
    private javax.swing.JLabel lblBar2;
    private javax.swing.JLabel lblBar3;
    private javax.swing.JLabel lblBar4;
    private javax.swing.JLabel lblBar5;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserName1;
    private javax.swing.JLabel lbl_certiID1;
    private javax.swing.JLabel lbl_gcid;
    private javax.swing.JLabel lbl_gcolor;
    private javax.swing.JLabel lbl_gemImage;
    private javax.swing.JLabel lbl_gname;
    private javax.swing.JLabel lbl_gname1;
    private javax.swing.JLabel lbl_gpieces;
    private javax.swing.JLabel lbl_gshape;
    private javax.swing.JLabel lbl_gsprice;
    private javax.swing.JLabel lbl_gtype;
    private javax.swing.JLabel lbl_gweight;
    private javax.swing.JLabel lbl_weight;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel panelLoad;
    private javax.swing.JPanel panelLoad1;
    private javax.swing.JTable tbl_fgems;
    private javax.swing.JTable tbl_supCli;
    private javax.swing.JTextField text_uadd1;
    private javax.swing.JTextField text_uadd2;
    private javax.swing.JTextField text_uadd3;
    private javax.swing.JTextField text_ubacc;
    private javax.swing.JTextField text_uemail;
    private javax.swing.JTextField text_ufname;
    private javax.swing.JTextField text_ulname;
    private javax.swing.JTextField text_ulno;
    private javax.swing.JTextField text_unic;
    private javax.swing.JTextField text_uphone;
    private javax.swing.JTextField text_upwd;
    private javax.swing.JTextField txt_cid;
    private javax.swing.JTextField txt_gcolor;
    private javax.swing.JTextField txt_gname;
    private javax.swing.JTextField txt_gpieces;
    private javax.swing.JTextField txt_gshape;
    private javax.swing.JTextField txt_gsprice;
    private javax.swing.JTextField txt_gtype;
    private javax.swing.JTextField txt_gweight;
    // End of variables declaration//GEN-END:variables

}
