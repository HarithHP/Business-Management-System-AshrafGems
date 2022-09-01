
package View;

import AppPackage.AnimationClass;
import Model.Client;
import Model.SqlConnection;
import Model.Supplier;
import Model.User;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



import javax.swing.JOptionPane;

public class SignInUp extends javax.swing.JFrame {

    int randomCode;//RANDOMCODE FOR USER PWD RECOVERY
    String fogotUserEmail;//PWD RECOVERY USER EMAIL

    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

    int mousePressedx;
    int mousePressedy;
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

    
    int uu_id;
    int ss_id;
    int cc_id;
    
    public SignInUp() {
        initComponents();
        lblUserIcon.setVisible(false);
        calToday();
        calUserID();
        calSupID();
        calCliID();


            label_ubacc.setVisible(false);
            label_ulno.setVisible(false);
            text_ubacc.setVisible(false);
            text_ulno.setVisible(false);
            label_uregion.setVisible(false);
            comboURegion.setVisible(false);
        
  
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

    private void validateFogotPwdSendCode(){
Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
Matcher mat = pattern.matcher(txtUserEmail.getText()); 
        fogotUserEmail = txtUserEmail.getText();
        if(txtUserEmail.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter User Email");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Email Address");
        }else
        {
            checkOwnerEmail();
        }
    }
        public void checkOwnerEmail(){
        try{String query = "select * from owner where o_email = ?";
            pst=con.prepareStatement(query);
            pst.setString(1,txtUserEmail.getText());
            rs=pst.executeQuery();
            if(rs.next()){
            checkEmail(txtUserEmail.getText());
            }else{
            checkUserEmail();
            }
    } catch (SQLException ex) {
            Logger.getLogger(SignInUp.class.getName()).log(Level.SEVERE, null, ex);          
            }           
    }
 public void checkUserEmail(){
        try{String query = "select * from user where u_email = ?";
            pst=con.prepareStatement(query);
            pst.setString(1,txtUserEmail.getText());
            rs=pst.executeQuery();
            if(rs.next()){
            checkEmail(txtUserEmail.getText());
            }else{
            JOptionPane.showMessageDialog(null, "There isn't an account for this email");
            }
    } catch (SQLException ex) {
            Logger.getLogger(SignInUp.class.getName()).log(Level.SEVERE, null, ex);          
            }           
    } 
public void checkEmail(String mailto){
        try {
            
            Random rand = new Random();
            randomCode = rand.nextInt(999999);
            System.out.println(""+randomCode);
            System.out.println("Preparing to send mail");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String myEmail = "ashrafgemstore@gmail.com";
        String password="ashrafgems1234";
                Session session = Session.getInstance(properties, new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myEmail,password);
                }

        });
        Message message = prepareMessage(session,myEmail,mailto,randomCode);
        Transport.send(message);
        JOptionPane.showMessageDialog(null,"Verify code has been sent to your Email \n Please check your Emails");
        
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null,e);
        }               
    }
  private static Message prepareMessage(Session session, String myEmail, String mailto, int randomCode) throws MessagingException {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailto));
            message.setSubject("Use this code for reset your PASSWORD");
            message.setText("Your Verify Code : "+randomCode);
            return message;        
            
        } catch (Exception ex) {
            Logger.getLogger(SignInUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
private void validateverify(){
         String v =  txtPincode.getText();
        if(v==null){
            JOptionPane.showMessageDialog(null, "Please Enter Pin Code!");
        }
        else{
             if(Integer.valueOf(txtPincode.getText())==randomCode){
        txtPincode.setText("");
        txtUserEmail.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
        jInternalFrameConfirmEmail.setVisible(false);
        jInternalFramePasswordReset.setVisible(true);
         
         }else{
                JOptionPane.showMessageDialog(null, "The Pin Code INVALID!");
         }
        } 
}
      private void validateFogotNewPwd(){

        
        if(txtNewPassword.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter New Password");
        }else if(txtNewPassword.getText().length()<5){
            JOptionPane.showMessageDialog(this, "Please New Password should have more than Five Characters");
        }else if(txtConfirmPassword.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Confirm Your New Password");
        }else if(!txtConfirmPassword.getText().equals(txtNewPassword.getText())){
            JOptionPane.showMessageDialog(this, "Your New password do not match with Confirm Password");
        }else
        {
              updateNewPWD();
        }
    }
private void updateNewPWD(){
          try{
           String sql = "UPDATE owner SET o_pwd=? WHERE o_email =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,txtNewPassword.getText());
           pst.setString(2,fogotUserEmail);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "New Password Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
            try{
           String sql = "UPDATE user SET u_pwd=? WHERE u_email =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,txtNewPassword.getText());
           pst.setString(2,fogotUserEmail);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "New Password Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
            try{
           String sql = "UPDATE agent SET a_pwd=? WHERE a_email =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,txtNewPassword.getText());
           pst.setString(2,fogotUserEmail);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "New Password Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
            try{
           String sql = "UPDATE client SET c_pwd=? WHERE c_email =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,txtNewPassword.getText());
           pst.setString(2,fogotUserEmail);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "New Password Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
            try{
           String sql = "UPDATE gempolisher SET e_pwd=? WHERE e_email =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,txtNewPassword.getText());
           pst.setString(2,fogotUserEmail);
           pst.executeUpdate();
            //JOptionPane.showMessageDialog(null, "New Password Updated Successfully");
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
            try{
           String sql = "UPDATE supplier SET s_pwd=? WHERE s_email =?";
           pst = con.prepareStatement(sql);
           pst.setString(1,txtNewPassword.getText());
           pst.setString(2,fogotUserEmail);
           pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "New Password Updated Successfully");
            txtNewPassword.setText("");
            txtConfirmPassword.setText("");
            jInternalFramePasswordReset.setVisible(false);
            jInternalFrameSignIn.setVisible(true);
            }
                catch(SQLException | HeadlessException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        
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
        datechooser_udob.setDate(date);
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
    private void setData(){
         u_email = txt_useremail.getText();
         u_pwd = txt_userpwd.getText();
    }
  
    private void validateSignIn(){
        if(u_email.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter User Email");
        }else if(u_pwd.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter User Password");
        }else
        {
         User main = new User();
         main.setInput(u_email, u_pwd,this);
         main.checkOwnerEmail();
        }
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
            JOptionPane.showMessageDialog(this,"Your Password must have more than 6 Characters");
        }else if(u_type.equals("Customer")){
            validateClient();
        }else if(u_type.equals("Supplier")){
            validateSupplier();
        }
        else{
              JOptionPane.showMessageDialog(this," System Error");
        }
}
private void validateClient(){
    if (u_ctype.equals("Select")) {
         JOptionPane.showMessageDialog(this,"Please Select Your Region");
    } else {

     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, "Client", this);
     main.inputUser();

    Client mainc = new Client();
    mainc.setClient(c_id, u_id, "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_ctype, this);
    mainc.inputClient();
    clear();
    backToSignIn();
    }
}
private void validateSupplier(){
    if(u_scid.equals("")){
         JOptionPane.showMessageDialog(this,"Please Enter Your Gem Selling License Number");
    }else if(u_scid.length()<8||u_scid.length()>15){
         JOptionPane.showMessageDialog(this,"Please Enter Valid Gem Selling License Number");
    } else if (u_sbacc.equals("")) {
         JOptionPane.showMessageDialog(this,"Please Enter Your Bank Account Number");
    }else if(u_sbacc.length()<8||u_sbacc.length()>15){
         JOptionPane.showMessageDialog(this,"Please Enter Valid Bank Account Number");
    }else{

     User main = new User();
     main.setInputUser(u_id, u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_type, this);
     main.inputUser();
     
     Supplier mains = new Supplier();
     mains.setSupplier(s_id, u_id, "0", u_fname, u_lname, u_ad_1, u_ad_2, u_ad_3, u_phone, u_dob, String.valueOf(u_age), u_nic, u_jdate, u_email, u_pwd, u_scid, u_sbacc, this);
     mains.inputSupplier();
     clear();
     backToSignIn();
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
     if((comboUType.getSelectedItem().toString()).equals("Customer")){
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
    private void backToSignIn(){
        jInternalFrameSignUp.setVisible(false);
        jInternalFrameSignIn.setVisible(true);
        lblUserIcon.setVisible(false);
}
   
    
    
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupCS = new javax.swing.ButtonGroup();
        bg = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnExit = new javax.swing.JLabel();
        btnMiniMize = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUserIcon = new javax.swing.JLabel();
        loginPanel = new javax.swing.JPanel();
        jInternalFrameSignIn = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        txt_useremail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_userpwd = new javax.swing.JPasswordField();
        lbl_userpwd = new javax.swing.JLabel();
        btnSignIn = new View.MyButton();
        btnSignupform = new View.MyButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblfrogotPassword = new javax.swing.JLabel();
        lbl_useremail = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jInternalFrameConfirmEmail = new javax.swing.JInternalFrame();
        jLabel5 = new javax.swing.JLabel();
        txtUserEmail = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtPincode = new javax.swing.JPasswordField();
        lblResendPin = new javax.swing.JLabel();
        btnGenerateCode = new View.MyButton();
        btnVerify = new View.MyButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jInternalFramePasswordReset = new javax.swing.JInternalFrame();
        jLabel7 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtNewPassword = new javax.swing.JPasswordField();
        btnReset = new View.MyButton();
        btnclear = new View.MyButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtConfirmPassword = new javax.swing.JPasswordField();
        lbl5 = new javax.swing.JLabel();
        lbl6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jInternalFrameSignUp = new javax.swing.JInternalFrame();
        btnClear = new View.MyButton();
        btnSaveandSignup = new View.MyButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        label_uphone = new javax.swing.JLabel();
        label_udob = new javax.swing.JLabel();
        label_ufname = new javax.swing.JLabel();
        label_ulname = new javax.swing.JLabel();
        label_utype = new javax.swing.JLabel();
        label_unic = new javax.swing.JLabel();
        label_uemail = new javax.swing.JLabel();
        text_upwd = new javax.swing.JTextField();
        label_upwd = new javax.swing.JLabel();
        text_ulname = new javax.swing.JTextField();
        text_uemail = new javax.swing.JTextField();
        text_unic = new javax.swing.JTextField();
        text_uphone = new javax.swing.JTextField();
        text_ufname = new javax.swing.JTextField();
        label_uaddress = new javax.swing.JLabel();
        text_uadd1 = new javax.swing.JTextField();
        text_uadd3 = new javax.swing.JTextField();
        text_uadd2 = new javax.swing.JTextField();
        text_ubacc = new javax.swing.JTextField();
        label_ubacc = new javax.swing.JLabel();
        label_ulno = new javax.swing.JLabel();
        text_ulno = new javax.swing.JTextField();
        comboURegion = new javax.swing.JComboBox<>();
        label_uregion = new javax.swing.JLabel();
        comboUType = new javax.swing.JComboBox<>();
        datechooser_udob = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(46, 43, 43));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

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

        jLabel3.setBackground(new java.awt.Color(46, 43, 43));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-gem-stone-48 (1).png"))); // NOI18N
        jLabel3.setText("Ashraf Gems PVT LTD");

        lblUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/profile.png"))); // NOI18N
        lblUserIcon.setToolTipText("");
        lblUserIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblUserIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 602, Short.MAX_VALUE)
                .addComponent(lblUserIcon)
                .addGap(29, 29, 29)
                .addComponent(btnMiniMize)
                .addGap(18, 18, 18)
                .addComponent(btnExit)
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMiniMize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblUserIcon))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(592, 592, 592))
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 50));

        loginPanel.setBackground(new java.awt.Color(46, 43, 43));
        loginPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                loginPanelMouseDragged(evt);
            }
        });
        loginPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                loginPanelKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loginPanelKeyReleased(evt);
            }
        });
        loginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrameSignIn.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameSignIn.setVisible(true);
        jInternalFrameSignIn.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-circled-user-male-skin-type-1-and-2-80.png"))); // NOI18N
        jInternalFrameSignIn.getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 150, -1, -1));

        txt_useremail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_useremail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_useremail.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_useremail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_useremailActionPerformed(evt);
            }
        });
        jInternalFrameSignIn.getContentPane().add(txt_useremail, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 280, 280, 30));

        jLabel4.setBackground(new java.awt.Color(46, 43, 43));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Or");
        jInternalFrameSignIn.getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 430, -1, -1));

        txt_userpwd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_userpwd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameSignIn.getContentPane().add(txt_userpwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 330, 280, 30));

        lbl_userpwd.setBackground(new java.awt.Color(46, 43, 43));
        lbl_userpwd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_userpwd.setText("Enter your Password");
        lbl_userpwd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_userpwd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_userpwdMouseClicked(evt);
            }
        });
        jInternalFrameSignIn.getContentPane().add(lbl_userpwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 310, -1, -1));

        btnSignIn.setText("Sign In");
        btnSignIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSignInMouseClicked(evt);
            }
        });
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });
        jInternalFrameSignIn.getContentPane().add(btnSignIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 430, 130, 50));

        btnSignupform.setText("Sign Up");
        btnSignupform.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSignupformMouseClicked(evt);
            }
        });
        btnSignupform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupformActionPerformed(evt);
            }
        });
        jInternalFrameSignIn.getContentPane().add(btnSignupform, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 430, 140, 50));

        jPanel2.setBackground(new java.awt.Color(46, 43, 43));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mainLogo.png"))); // NOI18N

        jLabel26.setBackground(new java.awt.Color(46, 43, 43));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bbrdbb.png"))); // NOI18N
        jLabel26.setText("Develop By");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jInternalFrameSignIn.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 570));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Developed By Team Ashraf");
        jInternalFrameSignIn.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 520, -1, -1));

        jLabel24.setBackground(new java.awt.Color(46, 43, 43));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("Support our app using Team@Ashraf.com");
        jInternalFrameSignIn.getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 540, -1, -1));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel25.setText("Welcome !");
        jInternalFrameSignIn.getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, -1, -1));

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setText("Login to your account");
        jInternalFrameSignIn.getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, -1, -1));

        lblfrogotPassword.setBackground(new java.awt.Color(46, 43, 43));
        lblfrogotPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblfrogotPassword.setText("Frogot password? Click here");
        lblfrogotPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblfrogotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblfrogotPasswordMouseClicked(evt);
            }
        });
        jInternalFrameSignIn.getContentPane().add(lblfrogotPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 380, -1, -1));

        lbl_useremail.setBackground(new java.awt.Color(46, 43, 43));
        lbl_useremail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_useremail.setText("Enter your User Email");
        lbl_useremail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_useremail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_useremailMouseClicked(evt);
            }
        });
        jInternalFrameSignIn.getContentPane().add(lbl_useremail, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 260, -1, -1));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111_1.png"))); // NOI18N
        jInternalFrameSignIn.getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, -30, 380, 430));

        loginPanel.add(jInternalFrameSignIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -30, 1030, 600));

        jInternalFrameConfirmEmail.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameConfirmEmail.setVisible(false);
        jInternalFrameConfirmEmail.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-forgot-password-80.png"))); // NOI18N
        jInternalFrameConfirmEmail.getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 150, -1, -1));

        txtUserEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtUserEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtUserEmail.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUserEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserEmailActionPerformed(evt);
            }
        });
        jInternalFrameConfirmEmail.getContentPane().add(txtUserEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 280, 280, 30));

        jLabel29.setBackground(new java.awt.Color(46, 43, 43));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Or");
        jInternalFrameConfirmEmail.getContentPane().add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 430, -1, -1));

        txtPincode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPincode.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtPincode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPincodeActionPerformed(evt);
            }
        });
        txtPincode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPincodeKeyPressed(evt);
            }
        });
        jInternalFrameConfirmEmail.getContentPane().add(txtPincode, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 330, 280, 30));

        lblResendPin.setBackground(new java.awt.Color(46, 43, 43));
        lblResendPin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblResendPin.setText("Didn't receive pincode! Resend");
        lblResendPin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblResendPin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResendPinMouseClicked(evt);
            }
        });
        jInternalFrameConfirmEmail.getContentPane().add(lblResendPin, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 380, -1, -1));

        btnGenerateCode.setText("Generate Code");
        btnGenerateCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGenerateCodeMouseClicked(evt);
            }
        });
        btnGenerateCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateCodeActionPerformed(evt);
            }
        });
        jInternalFrameConfirmEmail.getContentPane().add(btnGenerateCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 430, 130, 50));

        btnVerify.setText("Verify");
        btnVerify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerifyMouseClicked(evt);
            }
        });
        btnVerify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerifyActionPerformed(evt);
            }
        });
        jInternalFrameConfirmEmail.getContentPane().add(btnVerify, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 430, 140, 50));

        jPanel3.setBackground(new java.awt.Color(46, 43, 43));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mainLogo.png"))); // NOI18N

        jLabel30.setBackground(new java.awt.Color(46, 43, 43));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bbrdbb.png"))); // NOI18N
        jLabel30.setText("Develop By");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jInternalFrameConfirmEmail.getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 570));

        jLabel33.setBackground(new java.awt.Color(46, 43, 43));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setText("Support our app using Team@Ashraf.com");
        jInternalFrameConfirmEmail.getContentPane().add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 540, -1, -1));

        jLabel34.setBackground(new java.awt.Color(255, 255, 255));
        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel34.setText("Confirm Your Email");
        jInternalFrameConfirmEmail.getContentPane().add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, -1, -1));

        lbl3.setBackground(new java.awt.Color(46, 43, 43));
        lbl3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl3.setText("Enter your User Email");
        lbl3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl3MouseClicked(evt);
            }
        });
        jInternalFrameConfirmEmail.getContentPane().add(lbl3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 260, -1, -1));

        lbl4.setBackground(new java.awt.Color(46, 43, 43));
        lbl4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl4.setText("Enter pincode(Check Your Email)");
        lbl4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl4MouseClicked(evt);
            }
        });
        jInternalFrameConfirmEmail.getContentPane().add(lbl4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 310, -1, -1));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Developed By Team Ashraf");
        jInternalFrameConfirmEmail.getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 520, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jInternalFrameConfirmEmail.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, -1, -1));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111_1.png"))); // NOI18N
        jInternalFrameConfirmEmail.getContentPane().add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, -30, 380, 430));

        loginPanel.add(jInternalFrameConfirmEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -30, 1030, 600));

        jInternalFramePasswordReset.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFramePasswordReset.setVisible(false);
        jInternalFramePasswordReset.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-forgot-password-80.png"))); // NOI18N
        jInternalFramePasswordReset.getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 150, -1, -1));

        jLabel36.setBackground(new java.awt.Color(46, 43, 43));
        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Or");
        jInternalFramePasswordReset.getContentPane().add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 430, -1, -1));

        txtNewPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNewPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtNewPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewPasswordActionPerformed(evt);
            }
        });
        jInternalFramePasswordReset.getContentPane().add(txtNewPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 280, 280, 30));

        btnReset.setText("Reset");
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetMouseClicked(evt);
            }
        });
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jInternalFramePasswordReset.getContentPane().add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 430, 130, 50));

        btnclear.setText("Clear");
        btnclear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnclearMouseClicked(evt);
            }
        });
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });
        jInternalFramePasswordReset.getContentPane().add(btnclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 430, 140, 50));

        jPanel4.setBackground(new java.awt.Color(46, 43, 43));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mainLogo.png"))); // NOI18N

        jLabel31.setBackground(new java.awt.Color(46, 43, 43));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bbrdbb.png"))); // NOI18N
        jLabel31.setText("Develop By");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jInternalFramePasswordReset.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 570));

        jLabel40.setBackground(new java.awt.Color(46, 43, 43));
        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(51, 51, 51));
        jLabel40.setText("Support our app using Team@Ashraf.com");
        jInternalFramePasswordReset.getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 540, -1, -1));

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel41.setText("Reset Your Password");
        jInternalFramePasswordReset.getContentPane().add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, -1, -1));

        txtConfirmPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtConfirmPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtConfirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConfirmPasswordActionPerformed(evt);
            }
        });
        jInternalFramePasswordReset.getContentPane().add(txtConfirmPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 330, 280, 30));

        lbl5.setBackground(new java.awt.Color(46, 43, 43));
        lbl5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl5.setText("Enter New Password");
        lbl5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl5MouseClicked(evt);
            }
        });
        jInternalFramePasswordReset.getContentPane().add(lbl5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 260, -1, -1));

        lbl6.setBackground(new java.awt.Color(46, 43, 43));
        lbl6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl6.setText("Re Enter same Password");
        lbl6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl6MouseClicked(evt);
            }
        });
        jInternalFramePasswordReset.getContentPane().add(lbl6, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 310, -1, -1));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Developed By Team Ashraf");
        jInternalFramePasswordReset.getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 520, -1, -1));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111_1.png"))); // NOI18N
        jInternalFramePasswordReset.getContentPane().add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, -30, 380, 430));

        loginPanel.add(jInternalFramePasswordReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -30, 1030, 600));

        jInternalFrameSignUp.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameSignUp.setVisible(false);
        jInternalFrameSignUp.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jInternalFrameSignUp.getContentPane().add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 510, 110, 30));

        btnSaveandSignup.setText("Save & Sign In");
        btnSaveandSignup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveandSignupMouseClicked(evt);
            }
        });
        btnSaveandSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveandSignupActionPerformed(evt);
            }
        });
        jInternalFrameSignUp.getContentPane().add(btnSaveandSignup, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 510, 110, 30));

        jPanel5.setBackground(new java.awt.Color(46, 43, 43));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mainLogo.png"))); // NOI18N

        jLabel57.setBackground(new java.awt.Color(46, 43, 43));
        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bbrdbb.png"))); // NOI18N
        jLabel57.setText("Develop By");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jInternalFrameSignUp.getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 570));

        jLabel46.setBackground(new java.awt.Color(255, 255, 255));
        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel46.setText(" Create a new account");
        jInternalFrameSignUp.getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, -1, -1));

        label_uphone.setBackground(new java.awt.Color(255, 255, 255));
        label_uphone.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uphone.setText("Contact Number:");
        jInternalFrameSignUp.getContentPane().add(label_uphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, -1, -1));

        label_udob.setBackground(new java.awt.Color(255, 255, 255));
        label_udob.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_udob.setText("Date of Birth      :");
        jInternalFrameSignUp.getContentPane().add(label_udob, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, 150, -1));

        label_ufname.setBackground(new java.awt.Color(255, 255, 255));
        label_ufname.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ufname.setText("First Name:");
        jInternalFrameSignUp.getContentPane().add(label_ufname, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, 100, -1));

        label_ulname.setBackground(new java.awt.Color(255, 255, 255));
        label_ulname.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ulname.setText("Last Name  :");
        jInternalFrameSignUp.getContentPane().add(label_ulname, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 90, -1, -1));

        label_utype.setBackground(new java.awt.Color(255, 255, 255));
        label_utype.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_utype.setText("Continue as:");
        jInternalFrameSignUp.getContentPane().add(label_utype, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 110, -1));

        label_unic.setBackground(new java.awt.Color(255, 255, 255));
        label_unic.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_unic.setText("NIC Number       :");
        jInternalFrameSignUp.getContentPane().add(label_unic, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 210, -1, -1));

        label_uemail.setBackground(new java.awt.Color(255, 255, 255));
        label_uemail.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uemail.setText("Email                   :");
        jInternalFrameSignUp.getContentPane().add(label_uemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, -1, -1));

        text_upwd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_upwd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_upwdActionPerformed(evt);
            }
        });
        jInternalFrameSignUp.getContentPane().add(text_upwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 330, 270, 30));

        label_upwd.setBackground(new java.awt.Color(255, 255, 255));
        label_upwd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_upwd.setText("Password            :");
        jInternalFrameSignUp.getContentPane().add(label_upwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 330, -1, -1));

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
        jInternalFrameSignUp.getContentPane().add(text_ulname, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 90, 130, 30));

        text_uemail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        text_uemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_uemailActionPerformed(evt);
            }
        });
        jInternalFrameSignUp.getContentPane().add(text_uemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, 270, 30));

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
        jInternalFrameSignUp.getContentPane().add(text_unic, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, 270, 30));

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
        jInternalFrameSignUp.getContentPane().add(text_uphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 290, 270, 30));

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
        jInternalFrameSignUp.getContentPane().add(text_ufname, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 120, 30));

        label_uaddress.setBackground(new java.awt.Color(255, 255, 255));
        label_uaddress.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uaddress.setText("Address    :");
        jInternalFrameSignUp.getContentPane().add(label_uaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 100, -1));

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
        jInternalFrameSignUp.getContentPane().add(text_uadd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 110, 30));

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
        jInternalFrameSignUp.getContentPane().add(text_uadd3, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 130, 130, 30));

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
        jInternalFrameSignUp.getContentPane().add(text_uadd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 130, 110, 30));

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
        jInternalFrameSignUp.getContentPane().add(text_ubacc, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 410, 270, 30));

        label_ubacc.setBackground(new java.awt.Color(255, 255, 255));
        label_ubacc.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ubacc.setText("Bank Number     :");
        jInternalFrameSignUp.getContentPane().add(label_ubacc, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 410, -1, -1));

        label_ulno.setBackground(new java.awt.Color(255, 255, 255));
        label_ulno.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_ulno.setText("License Number :");
        jInternalFrameSignUp.getContentPane().add(label_ulno, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, -1, -1));

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
        jInternalFrameSignUp.getContentPane().add(text_ulno, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 370, 270, 30));

        comboURegion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboURegion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "SriLanka", "China" }));
        comboURegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboURegionActionPerformed(evt);
            }
        });
        jInternalFrameSignUp.getContentPane().add(comboURegion, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 370, 270, 30));

        label_uregion.setBackground(new java.awt.Color(255, 255, 255));
        label_uregion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label_uregion.setText("Region                :");
        jInternalFrameSignUp.getContentPane().add(label_uregion, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, -1, -1));

        comboUType.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        comboUType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Customer", "Supplier" }));
        comboUType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUTypeActionPerformed(evt);
            }
        });
        jInternalFrameSignUp.getContentPane().add(comboUType, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 190, 30));

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
        jInternalFrameSignUp.getContentPane().add(datechooser_udob, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 170, 270, 30));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-arrow-48.png"))); // NOI18N
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jInternalFrameSignUp.getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, -1, -1));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111_1.png"))); // NOI18N
        jInternalFrameSignUp.getContentPane().add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, -30, 380, 430));

        loginPanel.add(jInternalFrameSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -30, 1030, 600));

        bg.add(loginPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1010, 560));

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

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnMiniMizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMiniMizeMouseClicked
       this.setState(this.ICONIFIED);
    }//GEN-LAST:event_btnMiniMizeMouseClicked

    private void loginPanelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginPanelKeyPressed
       
    }//GEN-LAST:event_loginPanelKeyPressed

    private void loginPanelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_loginPanelKeyReleased
        
    }//GEN-LAST:event_loginPanelKeyReleased

    private void loginPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginPanelMouseDragged
      
    }//GEN-LAST:event_loginPanelMouseDragged

    private void btnSignupformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupformActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSignupformActionPerformed

    private void lblUserIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserIconMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblUserIconMouseClicked

    private void lbl_userpwdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_userpwdMouseClicked
        
       
    }//GEN-LAST:event_lbl_userpwdMouseClicked

    private void btnSignupformMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSignupformMouseClicked
        calToday();
        calUserID();
        calSupID();
        calCliID();
        jInternalFrameSignIn.setVisible(false);
        jInternalFrameSignUp.setVisible(true);
        lblUserIcon.setVisible(true);
    }//GEN-LAST:event_btnSignupformMouseClicked

    private void btnSignInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSignInMouseClicked

    }//GEN-LAST:event_btnSignInMouseClicked

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseDragged

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        int kordinatx=evt.getXOnScreen();
        int kordinatY=evt.getYOnScreen();
        
        this.setLocation(kordinatx, kordinatY);
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        
    }//GEN-LAST:event_jPanel1MousePressed

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed
        setData();        
        validateSignIn();

    }//GEN-LAST:event_btnSignInActionPerformed

    private void txt_useremailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_useremailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_useremailActionPerformed

    private void txtUserEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserEmailActionPerformed

    private void lblResendPinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResendPinMouseClicked
        validateFogotPwdSendCode();
    }//GEN-LAST:event_lblResendPinMouseClicked

    private void btnGenerateCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGenerateCodeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGenerateCodeMouseClicked

    private void btnGenerateCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateCodeActionPerformed
        // TODO add your handling code here:
        validateFogotPwdSendCode();
    }//GEN-LAST:event_btnGenerateCodeActionPerformed

    private void btnVerifyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerifyMouseClicked
        
    }//GEN-LAST:event_btnVerifyMouseClicked

    private void btnVerifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerifyActionPerformed
      Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
       Matcher mat = pattern.matcher(txtUserEmail.getText()); 
       if(txtUserEmail.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter User Email");
        }else if(!mat.matches()){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Email Address");
        }else if(txtPincode.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter the Pin Code");
        }else{
                validateverify();
        }
       
    }//GEN-LAST:event_btnVerifyActionPerformed

    private void txtPincodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPincodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPincodeActionPerformed

    private void txtNewPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNewPasswordActionPerformed

    private void btnResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseClicked
        
    }//GEN-LAST:event_btnResetMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        validateFogotNewPwd();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnclearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnclearMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnclearMouseClicked

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        // TODO add your handling code here:
         txtNewPassword.setText("");
        txtConfirmPassword.setText("");
    }//GEN-LAST:event_btnclearActionPerformed

    private void txtConfirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConfirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConfirmPasswordActionPerformed

    private void lblfrogotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblfrogotPasswordMouseClicked
       jInternalFrameSignIn.setVisible(false);
        jInternalFrameConfirmEmail.setVisible(true);
    }//GEN-LAST:event_lblfrogotPasswordMouseClicked

    private void lbl_useremailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_useremailMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_useremailMouseClicked

    private void lbl3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl3MouseClicked

    private void lbl4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl4MouseClicked

    private void lbl5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl5MouseClicked

    private void lbl6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl6MouseClicked

    private void btnSaveandSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveandSignupActionPerformed
          calUserID();
          calSupID();
          calCliID();             
          setUserData(); 
        
          
    }//GEN-LAST:event_btnSaveandSignupActionPerformed

    private void btnSaveandSignupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveandSignupMouseClicked
        
    }//GEN-LAST:event_btnSaveandSignupMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
       clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearMouseClicked

    private void text_upwdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_upwdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_upwdActionPerformed

    private void text_ulnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ulnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ulnameActionPerformed

    private void text_uemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uemailActionPerformed

    private void text_unicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_unicActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_unicActionPerformed

    private void text_uphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uphoneActionPerformed

    private void text_ufnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ufnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ufnameActionPerformed

    private void text_uadd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd1ActionPerformed

    private void text_uadd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd3ActionPerformed

    private void text_uadd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_uadd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_uadd2ActionPerformed

    private void text_ubaccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ubaccActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ubaccActionPerformed

    private void text_ulnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_ulnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_ulnoActionPerformed

    private void comboURegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboURegionActionPerformed
       

    }//GEN-LAST:event_comboURegionActionPerformed

    private void comboUTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUTypeActionPerformed
        hideFields();
    }//GEN-LAST:event_comboUTypeActionPerformed

    private void datechooser_udobMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMouseEntered
        
    }//GEN-LAST:event_datechooser_udobMouseEntered

    private void datechooser_udobMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMouseClicked
       
    }//GEN-LAST:event_datechooser_udobMouseClicked

    private void datechooser_udobAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_datechooser_udobAncestorAdded
      
    }//GEN-LAST:event_datechooser_udobAncestorAdded

    private void datechooser_udobMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMouseExited
         ageCalc();
    }//GEN-LAST:event_datechooser_udobMouseExited

    private void text_uadd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd1KeyPressed

       char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd1.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd1.setText("");
        }



     
    }//GEN-LAST:event_text_uadd1KeyPressed

    private void text_uadd1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd1MouseClicked
       text_uadd1.setText("");
    }//GEN-LAST:event_text_uadd1MouseClicked

    private void text_uadd2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd2MouseClicked
        text_uadd2.setText("");
    }//GEN-LAST:event_text_uadd2MouseClicked

    private void text_uadd3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd3KeyPressed
char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd3.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd3.setText("");
        }     
    }//GEN-LAST:event_text_uadd3KeyPressed

    private void datechooser_udobMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datechooser_udobMousePressed
       
    }//GEN-LAST:event_datechooser_udobMousePressed

    private void text_uphoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uphoneKeyPressed
           char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_uphone.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_uphone.setText("");
            
        } 
    }//GEN-LAST:event_text_uphoneKeyPressed

    private void text_ubaccKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ubaccKeyPressed
              char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             text_ubacc.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            text_ubacc.setText("");
            
        } 
    }//GEN-LAST:event_text_ubaccKeyPressed

    private void text_uadd3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_text_uadd3MouseClicked
       text_uadd3.setText("");        
    }//GEN-LAST:event_text_uadd3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
       jInternalFrameConfirmEmail.setVisible(false);
       jInternalFrameSignIn.setVisible(true);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
       jInternalFrameSignUp.setVisible(false);
       jInternalFrameSignIn.setVisible(true);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void text_ufnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ufnameKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ufname.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_ufname.setText("");
        }
    }//GEN-LAST:event_text_ufnameKeyPressed

    private void text_uadd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_uadd2KeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_uadd2.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_uadd2.setText("");
        }
    }//GEN-LAST:event_text_uadd2KeyPressed

    private void text_unicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_unicKeyPressed
        char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_unic.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_unic.setText("");
        }
    }//GEN-LAST:event_text_unicKeyPressed

    private void text_ulnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ulnoKeyPressed
char c = evt.getKeyChar();
        if (Character.isLetterOrDigit(c) || Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ulno.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_ulno.setText("");
        }

    }//GEN-LAST:event_text_ulnoKeyPressed

    private void text_ulnameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_ulnameKeyPressed
char c = evt.getKeyChar();
        if (Character.isLetter(c)||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
            text_ulname.setEditable(true);
        } else {
            
            JOptionPane.showMessageDialog(this,"This Field should have characters only!");
            text_ulname.setText("");
        }

    }//GEN-LAST:event_text_ulnameKeyPressed

    private void txtPincodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPincodeKeyPressed
       //NumbersOnly
 char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txtPincode.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            txtPincode.setText("");
            
        }

    }//GEN-LAST:event_txtPincodeKeyPressed
    
   
    
   
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private View.MyButton btnClear;
    private javax.swing.JLabel btnExit;
    private View.MyButton btnGenerateCode;
    private javax.swing.ButtonGroup btnGroupCS;
    private javax.swing.JLabel btnMiniMize;
    private View.MyButton btnReset;
    private View.MyButton btnSaveandSignup;
    private View.MyButton btnSignIn;
    private View.MyButton btnSignupform;
    private View.MyButton btnVerify;
    private View.MyButton btnclear;
    private javax.swing.JComboBox<String> comboURegion;
    private javax.swing.JComboBox<String> comboUType;
    private com.toedter.calendar.JDateChooser datechooser_udob;
    private javax.swing.JInternalFrame jInternalFrameConfirmEmail;
    private javax.swing.JInternalFrame jInternalFramePasswordReset;
    private javax.swing.JInternalFrame jInternalFrameSignIn;
    private javax.swing.JInternalFrame jInternalFrameSignUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
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
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lblResendPin;
    private javax.swing.JLabel lblUserIcon;
    private javax.swing.JLabel lbl_useremail;
    private javax.swing.JLabel lbl_userpwd;
    private javax.swing.JLabel lblfrogotPassword;
    private javax.swing.JPanel loginPanel;
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
    private javax.swing.JPasswordField txtConfirmPassword;
    private javax.swing.JPasswordField txtNewPassword;
    private javax.swing.JPasswordField txtPincode;
    private javax.swing.JTextField txtUserEmail;
    private javax.swing.JTextField txt_useremail;
    private javax.swing.JPasswordField txt_userpwd;
    // End of variables declaration//GEN-END:variables
}
