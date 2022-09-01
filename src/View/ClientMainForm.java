
package View;

import AppPackage.AnimationClass;
import Model.CliOrder;
import Model.Expense;
import Model.ExpenseAgent;
import Model.MapMain;
import Model.SqlConnection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ClientMainForm extends javax.swing.JFrame {
    Connection con = SqlConnection.getCon();
    PreparedStatement pst = null;
    ResultSet rs = null;

                String fg_id;
                int g_no;
                String fg_name;
                String fg_type;
                String fg_color;
                String fg_weight;
                String fg_sprice;
                String fg_cid;
                String fg_shape;
                String fg_pieces;

               String today;
           
               String c_aid;
               String c_fname;
               String c_phone;
               String c_nic;

               int c_oid;
               String c_ooid;
             
               String c_btype;
               String c_bacc;
               String c_bcvc;
               String c_bexp;

              int ex_id;
              int exa_id;
              String ex_no;
              String exa_no;



                

    AnimationClass ac = new AnimationClass();
    public String code;
    public ClientMainForm(String code) {
        this.code = code;
        initComponents();
        slide();
        getID();  
        fetch(); 
        fetchCliOrders();
        calExpenseAgentID();
         
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

    public void orderSetData() {
        c_btype = combo_cardType.getSelectedItem().toString();
        c_bacc = txt_cardno.getText();
        c_bcvc = txt_cvc.getText();
        c_bexp = txt_exp.getText();
}
    public void validateCusOrder(){
        if(c_btype.equals("Select")){
            JOptionPane.showMessageDialog(this, "Please Enter Card Type");   
        }else if(c_bacc.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Bank No");
        }else if(c_bacc.length()<8 ||c_bacc.length()>11){
            JOptionPane.showMessageDialog(this, "Please Enter Valid Bank No");
        }
        else if(c_bexp.isEmpty()||c_bexp.equals("dd/mm")){
            JOptionPane.showMessageDialog(this, "Please Enter Card Expire Date");
        }else if(c_bexp.length()<5 ||c_bexp.length()>5){
            JOptionPane.showMessageDialog(this, "Please Enter Card Expire Date Correctly");
        }
        else if(c_bcvc.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please Enter Card cvc");
        }else if(c_bcvc.length()<3 ||c_bcvc.length()>3){
            JOptionPane.showMessageDialog(this, "Please Enter Correct Card cvc");
        }
        else
        {
          CliOrder main = new CliOrder(c_ooid, code, g_no, today, c_btype, c_bacc,fg_sprice,this);
          main.input(); 
        if(!c_aid.equals("0")){
             calExpenseID();
             calExpenseAgentID();
             Expense mainc = new Expense(ex_no,"0","00.00","Not Selected" ,today,"Approved","AgentExpense");
             mainc.input();
             ExpenseAgent maine = new ExpenseAgent();
             maine.ExpenseAgent(ex_no, "0", "00.00", "Not Selected", today, "Approved", exa_no, c_aid, c_ooid,"0", fg_sprice,"0.0");
             maine.input();
             }
       combo_cardType.setSelectedIndex(0);
       txt_cardno.setText("");
       txt_cvc.setText("");
       txt_exp.setText("");
       fetch();
       Clear();
       txt_sgname.setText("");

        jInternalFrameOrderConfirm.setVisible(false);
        jInternalFrameInvoice.setVisible(true);
        loadInvoice();

        }
    }

     private void getID(){

         try{
            lblUserName2.setText(code);
           }catch(Exception e)
           {
            JOptionPane.showMessageDialog(null, e);
           }
    }
    private void calCliOrderID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(c_oid) from cliorder");
            
            if(rs.next()){
                
                c_oid = rs.getInt(1);
                c_oid =c_oid+1; 
                c_ooid = String.valueOf("co"+c_oid); 
                label_coid1.setText(c_ooid);   
            }else
            {
                c_oid =1; 
                c_ooid = String.valueOf("co"+c_oid);
                label_coid1.setText(c_ooid);
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
       
    }
    public void calExpenseID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(ex_id) from expense");
            
            if(rs.next()){
                
                ex_id = rs.getInt(1);
                ex_id =ex_id+1; 
                ex_no = String.valueOf(""+ex_id); 
                   
            }else
            {
                ex_id =1; 
                ex_no = String.valueOf(""+ex_no);
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
       
    }
    public void calExpenseAgentID(){
        try {
            
            Connection con = SqlConnection.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(aex_id) from expenseagent");
            
            if(rs.next()){
                
                exa_id= rs.getInt(1);
                exa_id =exa_id+1; 
                exa_no = String.valueOf("AE"+exa_id); 
                   
            }else
            {
                exa_id =1; 
                exa_no = String.valueOf("AE"+exa_id);
            }
            System.out.println(exa_no);
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e);
        }
       
    }


    private void fetch(){
        try{String query = "SELECT * FROM finish where fg_status =?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Finished");
           rs=pst.executeQuery();
           tbl_fgems.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
    private void fetchCliOrders(){
        try{String query = "SELECT * FROM cliorder where c_no =?";
           pst = con.prepareStatement(query);
           pst.setString(1,code);
           rs=pst.executeQuery();
           tbl_cliorder.setModel(DbUtils.resultSetToTableModel(rs));           
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);          
        }        
    }
    private void fetchCliOrderClick(){
       int row = tbl_cliorder.getSelectedRow();
       String tc =tbl_cliorder.getModel().getValueAt(row,0).toString();
      
       try{
           String query = "SELECT * FROM `cliorder` where c_no =? and c_oid = ? "; 
           pst = con.prepareStatement(query);
           pst.setString(1,code);
           pst.setString(2,tc);
           rs =pst.executeQuery();
           if(rs.next()){
                 String oid=rs.getString("c_oid");
                 String gno=rs.getString("g_no");
                 String codate=rs.getString("c_odate");
                 String cotot=rs.getString("c_ototal");

                 tesxtf_oid.setText(oid);
                 tesxtf_gid.setText(gno);
                 tesxtf_tot.setText(cotot);
                 tesxtf_odate.setText(codate);
                 getClientData();
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

    private void setConfirmData()
     {
                 label_cid1.setText(code);
                 label_codate1.setText(today);
                 label_gname1.setText(fg_name);
                 label_gtype1.setText(fg_type);
                 label_gcolor1.setText(fg_color);
                 label_gweight1.setText(fg_weight);
                 text_gcost.setText(fg_sprice);
                 label_gcid1.setText(fg_cid);
                 label_gpieces1.setText(fg_pieces); 
     }
    private void searchGem(){
        Connection con = SqlConnection.getCon();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{String query = "SELECT * FROM finish where fg_status =? and fg_name=?";
           pst = con.prepareStatement(query);
           pst.setString(1,"Finished");
           pst.setString(2,fg_name);
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
                 fg_id=rs.getString("fg_id");
                 g_no=rs.getInt("g_no");;
                 fg_name=rs.getString("fg_name");
                 fg_type=rs.getString("fg_type");
                 fg_color=rs.getString("fg_color");
                 fg_weight=rs.getString("fg_weight");
                 fg_sprice=rs.getString("fg_sprice");
                 fg_cid=rs.getString("fg_cid");
                 fg_shape=rs.getString("fg_shape");
                 fg_pieces=rs.getString("fg_pieces");

                 byte[] img = rs.getBytes("fg_image");
                 ImageIcon image = new ImageIcon(img);
                 Image im = image.getImage();
                 Image myImg = im.getScaledInstance(lbl_gemImage.getWidth(), lbl_gemImage.getHeight(),Image.SCALE_DEFAULT);
                 Image myImg1 = im.getScaledInstance(label_gimage.getWidth(), label_gimage.getHeight(),Image.SCALE_DEFAULT);
                 ImageIcon newImage =new ImageIcon(myImg);
                 ImageIcon newImage1 =new ImageIcon(myImg);
                 lbl_gemImage.setIcon(newImage);
                 label_gimage.setIcon(newImage1);

                 txt_gname.setText(fg_name);
                 txt_gtype.setText(fg_type);
                 txt_gcolor.setText(fg_color);
                 txt_gweight.setText(fg_weight);
                 txt_gsprice.setText(fg_sprice);
                 txt_cid.setText(fg_cid);
                 txt_gshape.setText(fg_shape);
                 txt_gpieces.setText(fg_pieces);
                 txt_tot.setText(fg_sprice);
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }
    private void getClientData(){
   
       try{
           String query = "SELECT * FROM `client` where c_id =?"; 
           pst = con.prepareStatement(query);
           pst.setString(1,code);
           rs =pst.executeQuery();
           if(rs.next()){
                 c_aid=rs.getString("a_no");
                 c_fname=rs.getString("c_fname");
                 c_phone=rs.getString("c_phone");
                 c_nic=rs.getString("c_nic");
                 label_agid1.setText(c_aid);
                 label_cname1.setText(c_fname);
                 label_cphone1.setText(c_phone);
                 label_cnic2.setText(c_nic);
                 tesxtf_cname.setText(c_fname);
                 
           }           
       }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);  
       }
    }

    public void Clear(){
                 txt_gname.setText("");
                 txt_gtype.setText("");
                 txt_gcolor.setText("");
                 txt_gweight.setText("");
                 txt_gsprice.setText("");
                 txt_cid.setText("");
                 txt_gshape.setText("");
                 txt_gpieces.setText("");
                 lbl_gemImage.setIcon(null); 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        buttonHome = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblBar1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        buttonCollection = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblBar2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnOrderDetails = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblBar4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btnCustomerReports = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblBar5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnCustomerHelp = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        lblBar6 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnLogOut = new javax.swing.JLabel();
        btnProfile = new javax.swing.JLabel();
        lblUserName2 = new javax.swing.JLabel();
        btnExit = new javax.swing.JLabel();
        btnMiniMize = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        form1 = new javax.swing.JPanel();
        jInternalFrameHome = new javax.swing.JInternalFrame();
        jPanelFrame1 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblPara = new javax.swing.JLabel();
        lblPrice1 = new javax.swing.JLabel();
        lblPrice2 = new javax.swing.JLabel();
        btnMore = new View.MyButton();
        jLabel64 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jInternalFrameCollection = new javax.swing.JInternalFrame();
        jPanelFrame2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbl_gemImage = new javax.swing.JLabel();
        txt_gsprice = new javax.swing.JTextField();
        lbl_gpieces = new javax.swing.JLabel();
        lbl_searchGemName = new javax.swing.JLabel();
        lbl_gweight = new javax.swing.JLabel();
        lbl_gsprice = new javax.swing.JLabel();
        lbl_gshape = new javax.swing.JLabel();
        lbl_gcid = new javax.swing.JLabel();
        btnOrder = new View.MyButton();
        btn_search = new View.MyButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_fgems = new javax.swing.JTable();
        txt_gpieces = new javax.swing.JTextField();
        txt_gweight = new javax.swing.JTextField();
        txt_gshape = new javax.swing.JTextField();
        txt_cid = new javax.swing.JTextField();
        lbl_gcolor = new javax.swing.JLabel();
        btnClear = new View.MyButton();
        txt_gcolor = new javax.swing.JTextField();
        txt_gname = new javax.swing.JTextField();
        lbl_gname = new javax.swing.JLabel();
        lbl_gtype = new javax.swing.JLabel();
        txt_gtype = new javax.swing.JTextField();
        lbl_weight = new javax.swing.JLabel();
        lbl_certiID1 = new javax.swing.JLabel();
        txt_sgname = new javax.swing.JTextField();
        btn_reset = new View.MyButton();
        jInternalFrameInvoice = new javax.swing.JInternalFrame();
        btnDone = new View.MyButton();
        panelLoad2 = new javax.swing.JPanel();
        jInternalFrameOrderSummary = new javax.swing.JInternalFrame();
        jLabel35 = new javax.swing.JLabel();
        btnCancel = new View.MyButton();
        label_gimage = new javax.swing.JLabel();
        btnOrderConfirm = new View.MyButton();
        label_coid = new javax.swing.JLabel();
        label_codate = new javax.swing.JLabel();
        label_cid = new javax.swing.JLabel();
        label_cname = new javax.swing.JLabel();
        label_cphone = new javax.swing.JLabel();
        label_gtype = new javax.swing.JLabel();
        text_gcost = new javax.swing.JTextField();
        label_gname = new javax.swing.JLabel();
        label_gcolor = new javax.swing.JLabel();
        label_gweight = new javax.swing.JLabel();
        label_gimagel = new javax.swing.JLabel();
        label_gcid = new javax.swing.JLabel();
        label_gcost = new javax.swing.JLabel();
        label_gpieces = new javax.swing.JLabel();
        label_coid1 = new javax.swing.JLabel();
        label_codate1 = new javax.swing.JLabel();
        label_cid1 = new javax.swing.JLabel();
        label_cname1 = new javax.swing.JLabel();
        label_cphone1 = new javax.swing.JLabel();
        label_gname1 = new javax.swing.JLabel();
        label_gtype1 = new javax.swing.JLabel();
        label_gcolor1 = new javax.swing.JLabel();
        label_gweight1 = new javax.swing.JLabel();
        label_gcid1 = new javax.swing.JLabel();
        label_gpieces1 = new javax.swing.JLabel();
        label_cnic = new javax.swing.JLabel();
        label_cnic2 = new javax.swing.JLabel();
        lbl_certiID2 = new javax.swing.JLabel();
        lbl_certiID3 = new javax.swing.JLabel();
        label_agid = new javax.swing.JLabel();
        label_agid1 = new javax.swing.JLabel();
        jInternalFrameOrderConfirm = new javax.swing.JInternalFrame();
        btnBack = new View.MyButton();
        lbl_cvc = new javax.swing.JLabel();
        btn_pay = new View.MyButton();
        combo_cardType = new javax.swing.JComboBox<>();
        lbl_cardtype = new javax.swing.JLabel();
        txt_cvc = new javax.swing.JTextField();
        lbl_tot = new javax.swing.JLabel();
        lbl_cardno = new javax.swing.JLabel();
        lbl_info = new javax.swing.JLabel();
        txt_tot = new javax.swing.JTextField();
        txt_exp = new javax.swing.JTextField();
        lbl_image3 = new javax.swing.JLabel();
        lbl_exp = new javax.swing.JLabel();
        lbl_image1 = new javax.swing.JLabel();
        lbl_image2 = new javax.swing.JLabel();
        txt_cardno = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jInternalFrameOrderDetails = new javax.swing.JInternalFrame();
        tesxtf_odate = new javax.swing.JTextField();
        lbl_cdate = new javax.swing.JLabel();
        tesxtf_tot = new javax.swing.JTextField();
        lbl_otot = new javax.swing.JLabel();
        tesxtf_gid = new javax.swing.JTextField();
        tesxtf_cname = new javax.swing.JTextField();
        tesxtf_oid = new javax.swing.JTextField();
        lbl_coid = new javax.swing.JLabel();
        lbl_cid = new javax.swing.JLabel();
        lbl_cname = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_cliorder = new javax.swing.JTable();
        lbl_coid1 = new javax.swing.JLabel();
        jInternalFrameCustomerReports = new javax.swing.JInternalFrame();
        panelLoad = new javax.swing.JPanel();
        btnClientGraphReport = new View.MyButton();
        btnClientReport = new View.MyButton();
        btnfilter = new View.MyButton();
        jInternalFrameCustomerHelp = new javax.swing.JInternalFrame();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        btnMap = new View.MyButton();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jInternalFrameCustomerProfile = new javax.swing.JInternalFrame();
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
        jLabel53 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(46, 43, 43));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        buttonCollection.setBackground(new java.awt.Color(46, 43, 43));
        buttonCollection.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonCollection.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCollectionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonCollectionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buttonCollectionMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-jewel-30 (1).png"))); // NOI18N

        lblBar2.setBackground(new java.awt.Color(255, 51, 51));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Collection");

        javax.swing.GroupLayout buttonCollectionLayout = new javax.swing.GroupLayout(buttonCollection);
        buttonCollection.setLayout(buttonCollectionLayout);
        buttonCollectionLayout.setHorizontalGroup(
            buttonCollectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonCollectionLayout.createSequentialGroup()
                .addComponent(lblBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonCollectionLayout.setVerticalGroup(
            buttonCollectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonCollectionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonCollectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(buttonCollection, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 170, 60));

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

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Orders");

        javax.swing.GroupLayout btnOrderDetailsLayout = new javax.swing.GroupLayout(btnOrderDetails);
        btnOrderDetails.setLayout(btnOrderDetailsLayout);
        btnOrderDetailsLayout.setHorizontalGroup(
            btnOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnOrderDetailsLayout.createSequentialGroup()
                .addComponent(lblBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        btnOrderDetailsLayout.setVerticalGroup(
            btnOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnOrderDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnOrderDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
            .addComponent(lblBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnOrderDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 170, -1));

        btnCustomerReports.setBackground(new java.awt.Color(46, 43, 43));
        btnCustomerReports.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCustomerReports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerReportsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCustomerReportsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCustomerReportsMouseExited(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/007.png"))); // NOI18N

        lblBar5.setBackground(new java.awt.Color(255, 51, 51));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Reports");

        javax.swing.GroupLayout btnCustomerReportsLayout = new javax.swing.GroupLayout(btnCustomerReports);
        btnCustomerReports.setLayout(btnCustomerReportsLayout);
        btnCustomerReportsLayout.setHorizontalGroup(
            btnCustomerReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCustomerReportsLayout.createSequentialGroup()
                .addComponent(lblBar5, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        btnCustomerReportsLayout.setVerticalGroup(
            btnCustomerReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCustomerReportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnCustomerReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
            .addComponent(lblBar5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnCustomerReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 170, -1));

        btnCustomerHelp.setBackground(new java.awt.Color(46, 43, 43));
        btnCustomerHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCustomerHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCustomerHelpMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCustomerHelpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCustomerHelpMouseExited(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Cambria Math", 1, 36)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/006.png"))); // NOI18N

        lblBar6.setBackground(new java.awt.Color(255, 51, 51));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Help");

        javax.swing.GroupLayout btnCustomerHelpLayout = new javax.swing.GroupLayout(btnCustomerHelp);
        btnCustomerHelp.setLayout(btnCustomerHelpLayout);
        btnCustomerHelpLayout.setHorizontalGroup(
            btnCustomerHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCustomerHelpLayout.createSequentialGroup()
                .addComponent(lblBar6, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        btnCustomerHelpLayout.setVerticalGroup(
            btnCustomerHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCustomerHelpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnCustomerHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
            .addComponent(lblBar6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        menu.add(btnCustomerHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 170, -1));

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

        lblUserName2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUserName2.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName2.setText("ID ");

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

        lblUserName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(255, 255, 255));
        lblUserName.setText("Client ID :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(499, Short.MAX_VALUE)
                .addComponent(btnProfile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUserName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName2)
                .addGap(29, 29, 29)
                .addComponent(btnLogOut)
                .addGap(18, 18, 18)
                .addComponent(btnMiniMize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit)
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnProfile)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMiniMize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(587, 587, 587))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        bg.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 840, 50));

        form1.setBackground(new java.awt.Color(255, 255, 255));
        form1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jInternalFrameHome.setBorder(null);
        jInternalFrameHome.setVisible(true);
        jInternalFrameHome.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelFrame1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFrame1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/gem1.png"))); // NOI18N
        jPanelFrame1.add(lblImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(-600, 170, 360, 380));

        lblPrice.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        lblPrice.setText("-------------");
        jPanelFrame1.add(lblPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 390, 130, 80));

        lblPara.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPara.setForeground(new java.awt.Color(51, 51, 51));
        lblPara.setText("<html>\n<body>\n<h3>Red Spinel</h3>\n<p>\nRed Spinel,also known as 'Spinel ruby gemstone', is the deep red color variety of Spinel that is considered most valuable. It is quite rare and is worn as a substitute for Ruby in astrology and jewelry. It also holds deep metaphysical meaning and is worn to gain stability and balance in life.\n</p>\n</body>\n</html>");
        jPanelFrame1.add(lblPara, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 210, 350, 175));

        lblPrice1.setFont(new java.awt.Font("Tw Cen MT", 0, 48)); // NOI18N
        lblPrice1.setForeground(new java.awt.Color(204, 0, 0));
        lblPrice1.setText("Rs 99990");
        lblPrice1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelFrame1.add(lblPrice1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 430, 190, 80));

        lblPrice2.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        lblPrice2.setForeground(new java.awt.Color(204, 0, 0));
        lblPrice2.setText("RS120000");
        jPanelFrame1.add(lblPrice2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 390, 120, 80));

        btnMore.setText("More Details");
        btnMore.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMoreMouseClicked(evt);
            }
        });
        jPanelFrame1.add(btnMore, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 500, 150, 50));

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/sale 3.png"))); // NOI18N
        jPanelFrame1.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, -1, 250));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111.png"))); // NOI18N
        jPanelFrame1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 300, 480));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/11111111_1.png"))); // NOI18N
        jPanelFrame1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, -30, 380, 430));

        jInternalFrameHome.getContentPane().add(jPanelFrame1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 840, 570));

        form1.add(jInternalFrameHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        jInternalFrameCollection.setBorder(null);
        jInternalFrameCollection.setVisible(false);
        jInternalFrameCollection.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelFrame2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFrame2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_gemImage.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gemImage.setText("                        Image");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_gemImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_gemImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
        );

        jPanelFrame2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 310, 170, 140));

        txt_gsprice.setEditable(false);
        txt_gsprice.setBackground(new java.awt.Color(255, 255, 255));
        txt_gsprice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gsprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gspriceActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_gsprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, 160, 40));

        lbl_gpieces.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gpieces.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gpieces.setText("Gem pieces");
        jPanelFrame2.add(lbl_gpieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 430, -1, -1));

        lbl_searchGemName.setBackground(new java.awt.Color(255, 255, 255));
        lbl_searchGemName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_searchGemName.setText("Enter Gem Name :");
        jPanelFrame2.add(lbl_searchGemName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        lbl_gweight.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gweight.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gweight.setText("Gem Weight");
        jPanelFrame2.add(lbl_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, -1, -1));

        lbl_gsprice.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gsprice.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gsprice.setText("Gem price");
        jPanelFrame2.add(lbl_gsprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 310, -1, -1));

        lbl_gshape.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gshape.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gshape.setText("Gem shape");
        jPanelFrame2.add(lbl_gshape, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 350, -1, -1));

        lbl_gcid.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gcid.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gcid.setText("Gem ID");
        jPanelFrame2.add(lbl_gcid, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 390, -1, -1));

        btnOrder.setText("Order");
        btnOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOrderMouseClicked(evt);
            }
        });
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });
        jPanelFrame2.add(btnOrder, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 510, 120, 40));

        btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Webp.net-resizeimage (2).gif"))); // NOI18N
        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });
        jPanelFrame2.add(btn_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 120, 30));

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

        jPanelFrame2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 790, 200));

        txt_gpieces.setEditable(false);
        txt_gpieces.setBackground(new java.awt.Color(255, 255, 255));
        txt_gpieces.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gpieces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gpiecesActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_gpieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 410, 160, 40));

        txt_gweight.setEditable(false);
        txt_gweight.setBackground(new java.awt.Color(255, 255, 255));
        txt_gweight.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gweightActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 160, 40));

        txt_gshape.setEditable(false);
        txt_gshape.setBackground(new java.awt.Color(255, 255, 255));
        txt_gshape.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gshape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gshapeActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_gshape, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 330, 160, 40));

        txt_cid.setEditable(false);
        txt_cid.setBackground(new java.awt.Color(255, 255, 255));
        txt_cid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_cid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cidActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_cid, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, 160, 40));

        lbl_gcolor.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gcolor.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gcolor.setText("Gem Color");
        jPanelFrame2.add(lbl_gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, -1, -1));

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanelFrame2.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 510, 120, 40));

        txt_gcolor.setEditable(false);
        txt_gcolor.setBackground(new java.awt.Color(255, 255, 255));
        txt_gcolor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gcolor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gcolorActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 160, 40));

        txt_gname.setEditable(false);
        txt_gname.setBackground(new java.awt.Color(255, 255, 255));
        txt_gname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gnameActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 160, 40));

        lbl_gname.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gname.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gname.setText("Gem Name");
        jPanelFrame2.add(lbl_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, -1));

        lbl_gtype.setBackground(new java.awt.Color(255, 255, 255));
        lbl_gtype.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_gtype.setText("Gem Type");
        jPanelFrame2.add(lbl_gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, -1, -1));

        txt_gtype.setEditable(false);
        txt_gtype.setBackground(new java.awt.Color(255, 255, 255));
        txt_gtype.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_gtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_gtypeActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 160, 40));

        lbl_weight.setText("Carats");
        jPanelFrame2.add(lbl_weight, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, -1, 20));

        lbl_certiID1.setText("Gem Certificate ID");
        jPanelFrame2.add(lbl_certiID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 410, -1, -1));

        txt_sgname.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_sgname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_sgname.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_sgname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sgnameActionPerformed(evt);
            }
        });
        jPanelFrame2.add(txt_sgname, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 170, 30));

        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });
        jPanelFrame2.add(btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 130, 30));

        jInternalFrameCollection.getContentPane().add(jPanelFrame2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 870, 580));

        form1.add(jInternalFrameCollection, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -30, 870, 590));

        jInternalFrameInvoice.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameInvoice.setVisible(false);
        jInternalFrameInvoice.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDone.setText("Done");
        btnDone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDoneMouseClicked(evt);
            }
        });
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });
        jInternalFrameInvoice.getContentPane().add(btnDone, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 510, 120, 40));

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

        jInternalFrameInvoice.getContentPane().add(panelLoad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 820, 490));

        form1.add(jInternalFrameInvoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 880, 600));

        jInternalFrameOrderSummary.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameOrderSummary.setVisible(false);
        jInternalFrameOrderSummary.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel35.setText("Order Summary");
        jInternalFrameOrderSummary.getContentPane().add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        btnCancel.setText("Cancel");
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });
        jInternalFrameOrderSummary.getContentPane().add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 500, 150, 34));

        label_gimage.setBackground(new java.awt.Color(255, 255, 255));
        label_gimage.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jInternalFrameOrderSummary.getContentPane().add(label_gimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 290, 190, 140));

        btnOrderConfirm.setText("Confirm");
        btnOrderConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOrderConfirmMouseClicked(evt);
            }
        });
        jInternalFrameOrderSummary.getContentPane().add(btnOrderConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 500, 150, 34));

        label_coid.setBackground(new java.awt.Color(255, 255, 255));
        label_coid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_coid.setText("Order ID              :");
        jInternalFrameOrderSummary.getContentPane().add(label_coid, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));

        label_codate.setBackground(new java.awt.Color(255, 255, 255));
        label_codate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_codate.setText("Order Date          :");
        jInternalFrameOrderSummary.getContentPane().add(label_codate, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, -1));

        label_cid.setBackground(new java.awt.Color(255, 255, 255));
        label_cid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_cid.setText("Customer ID        :");
        jInternalFrameOrderSummary.getContentPane().add(label_cid, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, -1, -1));

        label_cname.setBackground(new java.awt.Color(255, 255, 255));
        label_cname.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_cname.setText("Customer Name  :");
        jInternalFrameOrderSummary.getContentPane().add(label_cname, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, -1, -1));

        label_cphone.setBackground(new java.awt.Color(255, 255, 255));
        label_cphone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_cphone.setText("Customer Phone :");
        jInternalFrameOrderSummary.getContentPane().add(label_cphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, -1, -1));

        label_gtype.setBackground(new java.awt.Color(255, 255, 255));
        label_gtype.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gtype.setText("Gem Type      :");
        jInternalFrameOrderSummary.getContentPane().add(label_gtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, -1, -1));

        text_gcost.setEditable(false);
        text_gcost.setBackground(new java.awt.Color(255, 255, 255));
        text_gcost.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameOrderSummary.getContentPane().add(text_gcost, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 430, 140, 30));

        label_gname.setBackground(new java.awt.Color(255, 255, 255));
        label_gname.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gname.setText("Gem Name          :");
        jInternalFrameOrderSummary.getContentPane().add(label_gname, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, -1, -1));

        label_gcolor.setBackground(new java.awt.Color(255, 255, 255));
        label_gcolor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gcolor.setText("Gem Color     :");
        jInternalFrameOrderSummary.getContentPane().add(label_gcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, -1, -1));

        label_gweight.setBackground(new java.awt.Color(255, 255, 255));
        label_gweight.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gweight.setText("Gem Weight  :");
        jInternalFrameOrderSummary.getContentPane().add(label_gweight, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, -1, -1));

        label_gimagel.setBackground(new java.awt.Color(255, 255, 255));
        label_gimagel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gimagel.setText("Gem Image   :");
        jInternalFrameOrderSummary.getContentPane().add(label_gimagel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, -1, -1));

        label_gcid.setBackground(new java.awt.Color(255, 255, 255));
        label_gcid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gcid.setText("Gem ID          :");
        jInternalFrameOrderSummary.getContentPane().add(label_gcid, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 210, -1, -1));

        label_gcost.setBackground(new java.awt.Color(255, 255, 255));
        label_gcost.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gcost.setText("Total cost");
        jInternalFrameOrderSummary.getContentPane().add(label_gcost, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 430, -1, -1));

        label_gpieces.setBackground(new java.awt.Color(255, 255, 255));
        label_gpieces.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gpieces.setText("Gem Pieces    :");
        jInternalFrameOrderSummary.getContentPane().add(label_gpieces, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, -1, -1));

        label_coid1.setBackground(new java.awt.Color(255, 255, 255));
        label_coid1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_coid1.setText("Order ID");
        jInternalFrameOrderSummary.getContentPane().add(label_coid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 130, -1));

        label_codate1.setBackground(new java.awt.Color(255, 255, 255));
        label_codate1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_codate1.setText("Order Date");
        jInternalFrameOrderSummary.getContentPane().add(label_codate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 130, -1));

        label_cid1.setBackground(new java.awt.Color(255, 255, 255));
        label_cid1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_cid1.setText("Customer ID");
        jInternalFrameOrderSummary.getContentPane().add(label_cid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 210, 130, -1));

        label_cname1.setBackground(new java.awt.Color(255, 255, 255));
        label_cname1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_cname1.setText("Customer Name");
        jInternalFrameOrderSummary.getContentPane().add(label_cname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, 130, -1));

        label_cphone1.setBackground(new java.awt.Color(255, 255, 255));
        label_cphone1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_cphone1.setText("Customer Phone");
        jInternalFrameOrderSummary.getContentPane().add(label_cphone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 330, -1, -1));

        label_gname1.setBackground(new java.awt.Color(255, 255, 255));
        label_gname1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gname1.setText("Gem Name");
        jInternalFrameOrderSummary.getContentPane().add(label_gname1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, 130, -1));

        label_gtype1.setBackground(new java.awt.Color(255, 255, 255));
        label_gtype1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gtype1.setText("Gem Type");
        jInternalFrameOrderSummary.getContentPane().add(label_gtype1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, -1, -1));

        label_gcolor1.setBackground(new java.awt.Color(255, 255, 255));
        label_gcolor1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gcolor1.setText("Gem Color ");
        jInternalFrameOrderSummary.getContentPane().add(label_gcolor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 130, -1, -1));

        label_gweight1.setBackground(new java.awt.Color(255, 255, 255));
        label_gweight1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gweight1.setText("Gem Weight");
        jInternalFrameOrderSummary.getContentPane().add(label_gweight1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, -1, -1));

        label_gcid1.setBackground(new java.awt.Color(255, 255, 255));
        label_gcid1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gcid1.setText("Gem ID");
        jInternalFrameOrderSummary.getContentPane().add(label_gcid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 210, -1, -1));

        label_gpieces1.setBackground(new java.awt.Color(255, 255, 255));
        label_gpieces1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_gpieces1.setText("Gem Pieces ");
        jInternalFrameOrderSummary.getContentPane().add(label_gpieces1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 260, -1, -1));

        label_cnic.setBackground(new java.awt.Color(255, 255, 255));
        label_cnic.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_cnic.setText("Customer NIC      :");
        jInternalFrameOrderSummary.getContentPane().add(label_cnic, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, -1, -1));

        label_cnic2.setBackground(new java.awt.Color(255, 255, 255));
        label_cnic2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_cnic2.setText("Customer NIC");
        jInternalFrameOrderSummary.getContentPane().add(label_cnic2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, 140, -1));

        lbl_certiID2.setText("Carats");
        jInternalFrameOrderSummary.getContentPane().add(lbl_certiID2, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, 40, 20));

        lbl_certiID3.setText("Gem Certificate ID");
        jInternalFrameOrderSummary.getContentPane().add(lbl_certiID3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, -1, -1));

        label_agid.setBackground(new java.awt.Color(255, 255, 255));
        label_agid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_agid.setText("Refferal Agent ID :");
        jInternalFrameOrderSummary.getContentPane().add(label_agid, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, -1, -1));

        label_agid1.setBackground(new java.awt.Color(255, 255, 255));
        label_agid1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_agid1.setText("Agent ID");
        jInternalFrameOrderSummary.getContentPane().add(label_agid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 130, -1));

        form1.add(jInternalFrameOrderSummary, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameOrderConfirm.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameOrderConfirm.setForeground(new java.awt.Color(0, 0, 0));
        jInternalFrameOrderConfirm.setVisible(false);
        jInternalFrameOrderConfirm.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jInternalFrameOrderConfirm.getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 470, 83, 34));

        lbl_cvc.setBackground(new java.awt.Color(255, 255, 255));
        lbl_cvc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_cvc.setText("CVC :");
        jInternalFrameOrderConfirm.getContentPane().add(lbl_cvc, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 290, -1, -1));

        btn_pay.setText("Pay");
        btn_pay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_payMouseClicked(evt);
            }
        });
        btn_pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_payActionPerformed(evt);
            }
        });
        jInternalFrameOrderConfirm.getContentPane().add(btn_pay, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 470, 83, 34));

        combo_cardType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Visa", "Master", "Amex" }));
        combo_cardType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_cardTypeActionPerformed(evt);
            }
        });
        jInternalFrameOrderConfirm.getContentPane().add(combo_cardType, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 140, 30));

        lbl_cardtype.setBackground(new java.awt.Color(255, 255, 255));
        lbl_cardtype.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_cardtype.setText("Pay using : ");
        jInternalFrameOrderConfirm.getContentPane().add(lbl_cardtype, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, -1, -1));

        txt_cvc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_cvc.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_cvc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cvcActionPerformed(evt);
            }
        });
        txt_cvc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cvcKeyPressed(evt);
            }
        });
        jInternalFrameOrderConfirm.getContentPane().add(txt_cvc, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, 140, -1));

        lbl_tot.setBackground(new java.awt.Color(255, 255, 255));
        lbl_tot.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_tot.setText("Total Amount(Rs) :");
        jInternalFrameOrderConfirm.getContentPane().add(lbl_tot, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, -1, -1));

        lbl_cardno.setBackground(new java.awt.Color(255, 255, 255));
        lbl_cardno.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_cardno.setText("Card Number :");
        jInternalFrameOrderConfirm.getContentPane().add(lbl_cardno, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, -1, -1));

        lbl_info.setBackground(new java.awt.Color(255, 255, 255));
        lbl_info.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl_info.setText("Approved by the Central Bank of Sri Lanka");
        jInternalFrameOrderConfirm.getContentPane().add(lbl_info, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 410, -1, -1));

        txt_tot.setEditable(false);
        txt_tot.setBackground(new java.awt.Color(255, 255, 255));
        txt_tot.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_tot.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_tot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totActionPerformed(evt);
            }
        });
        jInternalFrameOrderConfirm.getContentPane().add(txt_tot, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, 150, 30));

        txt_exp.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_exp.setText("dd/mm");
        txt_exp.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_exp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_expMouseClicked(evt);
            }
        });
        txt_exp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_expActionPerformed(evt);
            }
        });
        txt_exp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_expKeyPressed(evt);
            }
        });
        jInternalFrameOrderConfirm.getContentPane().add(txt_exp, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 320, 140, -1));

        lbl_image3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_image3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-card-security-30.png"))); // NOI18N
        jInternalFrameOrderConfirm.getContentPane().add(lbl_image3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 370, -1, -1));

        lbl_exp.setBackground(new java.awt.Color(255, 255, 255));
        lbl_exp.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_exp.setText("Expiry :");
        jInternalFrameOrderConfirm.getContentPane().add(lbl_exp, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, -1, -1));

        lbl_image1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_image1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-visa-30.png"))); // NOI18N
        jInternalFrameOrderConfirm.getContentPane().add(lbl_image1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 370, -1, -1));

        lbl_image2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_image2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-mastercard-30.png"))); // NOI18N
        jInternalFrameOrderConfirm.getContentPane().add(lbl_image2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, -1, -1));

        txt_cardno.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_cardno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txt_cardno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cardnoActionPerformed(evt);
            }
        });
        txt_cardno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cardnoKeyPressed(evt);
            }
        });
        jInternalFrameOrderConfirm.getContentPane().add(txt_cardno, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 240, 310, -1));

        jLabel62.setBackground(new java.awt.Color(255, 255, 255));
        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel62.setText("Payment");
        jInternalFrameOrderConfirm.getContentPane().add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, -1, -1));

        form1.add(jInternalFrameOrderConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameOrderDetails.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameOrderDetails.setVisible(false);
        jInternalFrameOrderDetails.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tesxtf_odate.setEditable(false);
        tesxtf_odate.setBackground(new java.awt.Color(255, 255, 255));
        tesxtf_odate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameOrderDetails.getContentPane().add(tesxtf_odate, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 360, 180, 40));

        lbl_cdate.setBackground(new java.awt.Color(255, 255, 255));
        lbl_cdate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_cdate.setText("Order Date");
        jInternalFrameOrderDetails.getContentPane().add(lbl_cdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 380, -1, -1));

        tesxtf_tot.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameOrderDetails.getContentPane().add(tesxtf_tot, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 410, 180, 40));

        lbl_otot.setBackground(new java.awt.Color(255, 255, 255));
        lbl_otot.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_otot.setText("Total Cost");
        jInternalFrameOrderDetails.getContentPane().add(lbl_otot, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 430, -1, -1));

        tesxtf_gid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameOrderDetails.getContentPane().add(tesxtf_gid, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 460, 180, 40));

        tesxtf_cname.setEditable(false);
        tesxtf_cname.setBackground(new java.awt.Color(255, 255, 255));
        tesxtf_cname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameOrderDetails.getContentPane().add(tesxtf_cname, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, 180, 40));

        tesxtf_oid.setEditable(false);
        tesxtf_oid.setBackground(new java.awt.Color(255, 255, 255));
        tesxtf_oid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameOrderDetails.getContentPane().add(tesxtf_oid, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 360, 180, 40));

        lbl_coid.setBackground(new java.awt.Color(255, 255, 255));
        lbl_coid.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        lbl_coid.setText("My Orders");
        jInternalFrameOrderDetails.getContentPane().add(lbl_coid, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        lbl_cid.setBackground(new java.awt.Color(255, 255, 255));
        lbl_cid.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_cid.setText("Gem ID");
        jInternalFrameOrderDetails.getContentPane().add(lbl_cid, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, -1, -1));

        lbl_cname.setBackground(new java.awt.Color(255, 255, 255));
        lbl_cname.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_cname.setText("Customer Name");
        jInternalFrameOrderDetails.getContentPane().add(lbl_cname, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        tbl_cliorder.setAutoCreateRowSorter(true);
        tbl_cliorder.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_cliorder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_cliorderMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_cliorder);

        jInternalFrameOrderDetails.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 760, 270));

        lbl_coid1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_coid1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbl_coid1.setText("Order ID");
        jInternalFrameOrderDetails.getContentPane().add(lbl_coid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, -1, -1));

        form1.add(jInternalFrameOrderDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameCustomerReports.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameCustomerReports.setVisible(false);
        jInternalFrameCustomerReports.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelLoad.setBackground(new java.awt.Color(255, 255, 255));
        panelLoad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout panelLoadLayout = new javax.swing.GroupLayout(panelLoad);
        panelLoad.setLayout(panelLoadLayout);
        panelLoadLayout.setHorizontalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 824, Short.MAX_VALUE)
        );
        panelLoadLayout.setVerticalGroup(
            panelLoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );

        jInternalFrameCustomerReports.getContentPane().add(panelLoad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 830, 500));

        btnClientGraphReport.setText("Generate Graph Report");
        btnClientGraphReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientGraphReportActionPerformed(evt);
            }
        });
        jInternalFrameCustomerReports.getContentPane().add(btnClientGraphReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 200, 30));

        btnClientReport.setText("Generate Order Cost Report");
        btnClientReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientReportActionPerformed(evt);
            }
        });
        jInternalFrameCustomerReports.getContentPane().add(btnClientReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 210, 30));

        btnfilter.setText("Filter Report By Date");
        btnfilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfilterActionPerformed(evt);
            }
        });
        jInternalFrameCustomerReports.getContentPane().add(btnfilter, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 210, 30));

        form1.add(jInternalFrameCustomerReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, 880, 610));

        jInternalFrameCustomerHelp.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameCustomerHelp.setVisible(false);
        jInternalFrameCustomerHelp.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel74.setBackground(new java.awt.Color(255, 255, 255));
        jLabel74.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-address-30.png"))); // NOI18N
        jInternalFrameCustomerHelp.getContentPane().add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 40, 30));

        jLabel75.setBackground(new java.awt.Color(255, 255, 255));
        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel75.setText("Contact us :");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        jLabel76.setBackground(new java.awt.Color(255, 255, 255));
        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel76.setText("Find us one Map");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 30));

        jLabel77.setBackground(new java.awt.Color(255, 255, 255));
        jLabel77.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel77.setText("Scan the QR Code Using Your Mobile Phone");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, 30));

        jLabel78.setBackground(new java.awt.Color(255, 255, 255));
        jLabel78.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel78.setText("---------------------------------------------------------------------------------------------------------");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, 30));

        jLabel79.setBackground(new java.awt.Color(255, 255, 255));
        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-facebook-30.png"))); // NOI18N
        jLabel79.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameCustomerHelp.getContentPane().add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, -1, 30));

        jLabel80.setBackground(new java.awt.Color(255, 255, 255));
        jLabel80.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-instagram-30.png"))); // NOI18N
        jLabel80.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameCustomerHelp.getContentPane().add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 320, -1, 30));

        jLabel81.setBackground(new java.awt.Color(255, 255, 255));
        jLabel81.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel81.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-wechat-30.png"))); // NOI18N
        jLabel81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jInternalFrameCustomerHelp.getContentPane().add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 320, -1, 30));

        jLabel82.setBackground(new java.awt.Color(255, 255, 255));
        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel82.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-call-30.png"))); // NOI18N
        jInternalFrameCustomerHelp.getContentPane().add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, -1, 30));

        jLabel83.setBackground(new java.awt.Color(255, 255, 255));
        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8-mail-30.png"))); // NOI18N
        jInternalFrameCustomerHelp.getContentPane().add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 40, 20));

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

        jInternalFrameCustomerHelp.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 220, 200));

        jLabel84.setBackground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("Copyright © Ashraf Gems 2022");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 490, -1, 30));

        jLabel85.setBackground(new java.awt.Color(255, 255, 255));
        jLabel85.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel85.setText("0773431901 /  0112874534");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, -1, 30));

        jLabel86.setBackground(new java.awt.Color(255, 255, 255));
        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel86.setText("Explore and Learn More About Gem Varieties and Ashraf Gems");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, 30));

        btnMap.setText("Click Here");
        btnMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapActionPerformed(evt);
            }
        });
        jInternalFrameCustomerHelp.getContentPane().add(btnMap, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 100, 40));

        jLabel87.setBackground(new java.awt.Color(255, 255, 255));
        jLabel87.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel87.setText("NO 68/10 DHARGAROAD CHINAFORT BERUWALA");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, -1, 30));

        jLabel88.setBackground(new java.awt.Color(255, 255, 255));
        jLabel88.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel88.setText("ASHRAFGEMS@GMAIL.COM");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, -1, 30));

        jLabel89.setBackground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Licence Dealer of National Gem and Jewellery Authority");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, -1, 30));

        jLabel90.setBackground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Member of Chinafort Gem and Jewellery Trade Association (CGJTA)");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, -1, 30));

        jLabel91.setBackground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Registered Exporter of Sri Lanka Export Development Board");
        jInternalFrameCustomerHelp.getContentPane().add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 490, -1, 30));

        form1.add(jInternalFrameCustomerHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -30, 870, 600));

        jInternalFrameCustomerProfile.setBackground(new java.awt.Color(255, 255, 255));
        jInternalFrameCustomerProfile.setVisible(false);
        jInternalFrameCustomerProfile.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setBackground(new java.awt.Color(255, 255, 255));
        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel46.setText("Manage your profile");
        jInternalFrameCustomerProfile.getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        jLabel55.setBackground(new java.awt.Color(46, 43, 43));
        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel55.setText("User ID");
        jInternalFrameCustomerProfile.getContentPane().add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, -1, -1));

        jTextField4.setEditable(false);
        jTextField4.setBackground(new java.awt.Color(255, 255, 255));
        jTextField4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameCustomerProfile.getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 96, 180, -1));

        jLabel50.setBackground(new java.awt.Color(46, 43, 43));
        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel50.setText("First Name");
        jInternalFrameCustomerProfile.getContentPane().add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, -1));

        jTextField5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameCustomerProfile.getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 145, 180, -1));

        jLabel51.setBackground(new java.awt.Color(46, 43, 43));
        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel51.setText("Last Name");
        jInternalFrameCustomerProfile.getContentPane().add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, -1, -1));

        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameCustomerProfile.getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 160, -1));

        jLabel48.setBackground(new java.awt.Color(46, 43, 43));
        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setText("Address");
        jInternalFrameCustomerProfile.getContentPane().add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, -1, -1));

        jTextField6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameCustomerProfile.getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 195, 490, -1));

        jLabel56.setBackground(new java.awt.Color(46, 43, 43));
        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel56.setText("Email");
        jInternalFrameCustomerProfile.getContentPane().add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, -1, -1));

        jTextField7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameCustomerProfile.getContentPane().add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 238, 490, -1));

        jLabel53.setBackground(new java.awt.Color(46, 43, 43));
        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel53.setText("NIC Number");
        jInternalFrameCustomerProfile.getContentPane().add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, -1, -1));

        jTextField8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameCustomerProfile.getContentPane().add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 281, 190, -1));

        jLabel47.setBackground(new java.awt.Color(46, 43, 43));
        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel47.setText("Phone Number");
        jInternalFrameCustomerProfile.getContentPane().add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, -1, -1));

        jTextField9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameCustomerProfile.getContentPane().add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 324, 190, -1));

        jLabel54.setBackground(new java.awt.Color(46, 43, 43));
        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel54.setText("Joined Date");
        jInternalFrameCustomerProfile.getContentPane().add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, -1, -1));

        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jInternalFrameCustomerProfile.getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 368, 190, -1));

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
        jInternalFrameCustomerProfile.getContentPane().add(btnClear2, new org.netbeans.lib.awtextra.AbsoluteConstraints(227, 463, 130, 50));

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
        jInternalFrameCustomerProfile.getContentPane().add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 463, 140, 50));

        form1.add(jInternalFrameCustomerProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(-22, -34, 870, 600));

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
        jInternalFrameReports1.getContentPane().add(btnBack6, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 20, 90, 30));

        btnGenerate.setText("Generate");
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });
        jInternalFrameReports1.getContentPane().add(btnGenerate, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 90, 30));

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
       jInternalFrameCollection.setVisible(false);
       jInternalFrameInvoice.setVisible(false);
        jInternalFrameOrderConfirm.setVisible(false);
        jInternalFrameOrderDetails.setVisible(false);
        jInternalFrameCustomerReports.setVisible(false);
        jInternalFrameCustomerHelp.setVisible(false);
        jInternalFrameCustomerProfile.setVisible(false);
        jInternalFrameOrderSummary.setVisible(false);
       slide();
    }//GEN-LAST:event_buttonHomeMouseClicked

    private void buttonHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseEntered
        buttonHome.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_buttonHomeMouseEntered

    private void buttonHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonHomeMouseExited
        buttonHome.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_buttonHomeMouseExited

    private void buttonCollectionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCollectionMouseClicked
       bar(lblBar2);
       jInternalFrameHome.setVisible(false);
       jInternalFrameCollection.setVisible(true);
       jInternalFrameInvoice.setVisible(false);
        jInternalFrameOrderConfirm.setVisible(false);
        jInternalFrameOrderDetails.setVisible(false);
        jInternalFrameCustomerReports.setVisible(false);
        jInternalFrameCustomerHelp.setVisible(false);
        jInternalFrameCustomerProfile.setVisible(false);
        jInternalFrameOrderSummary.setVisible(false);
    }//GEN-LAST:event_buttonCollectionMouseClicked

    private void buttonCollectionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCollectionMouseEntered
        buttonCollection.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_buttonCollectionMouseEntered

    private void buttonCollectionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCollectionMouseExited
         buttonCollection.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_buttonCollectionMouseExited

    private void txt_gspriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gspriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gspriceActionPerformed

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
       jInternalFrameHome.setVisible(false);
       jInternalFrameCollection.setVisible(false);
       jInternalFrameInvoice.setVisible(false);
       jInternalFrameOrderConfirm.setVisible(false);
       jInternalFrameOrderDetails.setVisible(false);
       jInternalFrameCustomerReports.setVisible(false);
       jInternalFrameCustomerHelp.setVisible(false);
       jInternalFrameCustomerProfile.setVisible(false);
       jInternalFrameOrderSummary.setVisible(true);
    }//GEN-LAST:event_btnBackMouseClicked

    private void btnOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_btnOrderMouseClicked

    private void btnMoreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoreMouseClicked
       jInternalFrameHome.setVisible(false);
       jInternalFrameCollection.setVisible(true);
       jInternalFrameInvoice.setVisible(false);
       jInternalFrameOrderConfirm.setVisible(false);
       jInternalFrameOrderDetails.setVisible(false);
       jInternalFrameCustomerReports.setVisible(false);
       jInternalFrameCustomerHelp.setVisible(false);
       jInternalFrameCustomerProfile.setVisible(false);
       jInternalFrameOrderSummary.setVisible(false);
       bar(lblBar2);
    }//GEN-LAST:event_btnMoreMouseClicked

    private void btnOrderDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderDetailsMouseClicked
        bar(lblBar4);
        jInternalFrameHome.setVisible(false);
       jInternalFrameCollection.setVisible(false);
       jInternalFrameInvoice.setVisible(false);
       jInternalFrameOrderConfirm.setVisible(false);
       jInternalFrameOrderDetails.setVisible(true);
       jInternalFrameCustomerReports.setVisible(false);
       jInternalFrameCustomerHelp.setVisible(false);
       jInternalFrameCustomerProfile.setVisible(false);
       jInternalFrameOrderSummary.setVisible(false);
       fetchCliOrders();
    }//GEN-LAST:event_btnOrderDetailsMouseClicked

    private void btnOrderDetailsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderDetailsMouseEntered
       btnOrderDetails.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnOrderDetailsMouseEntered

    private void btnOrderDetailsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderDetailsMouseExited
        btnOrderDetails.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnOrderDetailsMouseExited

    private void btnCustomerReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerReportsMouseClicked
       bar(lblBar5);
       jInternalFrameHome.setVisible(false);
       jInternalFrameCollection.setVisible(false);
       jInternalFrameInvoice.setVisible(false);
       jInternalFrameOrderConfirm.setVisible(false);
       jInternalFrameOrderDetails.setVisible(false);
       jInternalFrameCustomerReports.setVisible(true);
       jInternalFrameCustomerHelp.setVisible(false);
       jInternalFrameCustomerProfile.setVisible(false);
       jInternalFrameOrderSummary.setVisible(false);
    }//GEN-LAST:event_btnCustomerReportsMouseClicked

    private void btnCustomerReportsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerReportsMouseEntered
        btnCustomerReports.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnCustomerReportsMouseEntered

    private void btnCustomerReportsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerReportsMouseExited
         btnCustomerReports.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnCustomerReportsMouseExited

    private void btnCustomerHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerHelpMouseClicked
       bar(lblBar6);
       jInternalFrameHome.setVisible(false);
       jInternalFrameCollection.setVisible(false);
       jInternalFrameInvoice.setVisible(false);
       jInternalFrameOrderConfirm.setVisible(false);
       jInternalFrameOrderDetails.setVisible(false);
       jInternalFrameCustomerReports.setVisible(false);
       jInternalFrameCustomerHelp.setVisible(true);
       jInternalFrameCustomerProfile.setVisible(false);
       jInternalFrameOrderSummary.setVisible(false);
    }//GEN-LAST:event_btnCustomerHelpMouseClicked

    private void btnCustomerHelpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerHelpMouseEntered
        btnCustomerHelp.setBackground(Color.decode("#565255"));
    }//GEN-LAST:event_btnCustomerHelpMouseEntered

    private void btnCustomerHelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCustomerHelpMouseExited
        btnCustomerHelp.setBackground(Color.decode("#2a2729"));
    }//GEN-LAST:event_btnCustomerHelpMouseExited

    private void btnProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileMouseClicked
       jInternalFrameHome.setVisible(false);
       jInternalFrameCollection.setVisible(false);
       jInternalFrameInvoice.setVisible(false);
       jInternalFrameOrderConfirm.setVisible(false);
       jInternalFrameOrderDetails.setVisible(false);
       jInternalFrameCustomerReports.setVisible(false);
       jInternalFrameCustomerHelp.setVisible(false);
       jInternalFrameCustomerProfile.setVisible(true);
       jInternalFrameOrderSummary.setVisible(false);
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
        
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

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        jInternalFrameOrderSummary.setVisible(false);
        jInternalFrameCollection.setVisible(true);
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnOrderConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrderConfirmMouseClicked
        
        jInternalFrameOrderSummary.setVisible(false);
        jInternalFrameOrderConfirm.setVisible(true);

    }//GEN-LAST:event_btnOrderConfirmMouseClicked

    private void btn_payMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_payMouseClicked
        
 
        
    }//GEN-LAST:event_btn_payMouseClicked

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

    private void combo_cardTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_cardTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_cardTypeActionPerformed

    private void txt_cvcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cvcActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cvcActionPerformed

    private void txt_totActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totActionPerformed

    private void txt_expActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_expActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_expActionPerformed

    private void txt_cardnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cardnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cardnoActionPerformed

    private void txt_gpiecesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gpiecesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gpiecesActionPerformed

    private void txt_gweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gweightActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gweightActionPerformed

    private void txt_gshapeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gshapeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gshapeActionPerformed

    private void txt_cidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cidActionPerformed

    private void txt_gcolorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gcolorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gcolorActionPerformed

    private void txt_gnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gnameActionPerformed

    private void txt_gtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_gtypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_gtypeActionPerformed

    private void tbl_fgemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_fgemsMouseClicked
       fetchClick();
    }//GEN-LAST:event_tbl_fgemsMouseClicked

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
       Clear();
       fg_name = txt_sgname.getText();              
       if(fg_name.equals("")){
         JOptionPane.showMessageDialog(null,"Please Enter Gem Name First!"); 
       }else{
            
            searchGem();

            }       
    }//GEN-LAST:event_btn_searchActionPerformed

    private void txt_sgnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sgnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sgnameActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
         fetch();
         Clear();
         txt_sgname.setText("");
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
       Clear();
       txt_sgname.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        if(txt_gname.getText().equals("")) {
            JOptionPane.showMessageDialog(null,"Please Select Gem First!"); 
        }
        else {
        setConfirmData(); 
        getClientData();
        calCliOrderID();

       jInternalFrameHome.setVisible(false);
       jInternalFrameCollection.setVisible(false);
       jInternalFrameInvoice.setVisible(false);
       jInternalFrameOrderConfirm.setVisible(false);
       jInternalFrameOrderSummary.setVisible(true);
       jInternalFrameOrderDetails.setVisible(false);
       jInternalFrameCustomerReports.setVisible(false);
       jInternalFrameCustomerHelp.setVisible(false);
       jInternalFrameCustomerProfile.setVisible(false); 

        
         }
    }//GEN-LAST:event_btnOrderActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackActionPerformed

    private void btn_payActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_payActionPerformed
             calCliOrderID();
             calExpenseAgentID();
             calExpenseID();
             orderSetData();
             validateCusOrder();




    }//GEN-LAST:event_btn_payActionPerformed

    private void txt_cardnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cardnoKeyPressed
 char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_cardno.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            txt_cardno.setText("");
            
        }
      
    }//GEN-LAST:event_txt_cardnoKeyPressed

    private void txt_expKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_expKeyPressed
        
  char c = evt.getKeyChar();
           if(Character.isLetter(c))
           {
              JOptionPane.showMessageDialog(this,"This Field must have Numbers");
              txt_exp.setText("");
           }
    }//GEN-LAST:event_txt_expKeyPressed

    private void txt_cvcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cvcKeyPressed
char c = evt.getKeyChar();
        if (Character.isDigit(c) ||Character.isISOControl(c)||evt.getKeyCode()==KeyEvent.VK_SHIFT||evt.getKeyCode()==KeyEvent.VK_CAPS_LOCK ||evt.getKeyCode()==KeyEvent.VK_CONTROL) {
             txt_cvc.setEditable(true);
        } else {
           
            JOptionPane.showMessageDialog(this,"This Field should have Numbers Only");
            txt_cvc.setText("");
            
        }
    }//GEN-LAST:event_txt_cvcKeyPressed

    private void tbl_cliorderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cliorderMouseClicked
     fetchCliOrderClick();
    }//GEN-LAST:event_tbl_cliorderMouseClicked

    private void btnClientGraphReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientGraphReportActionPerformed
        // TODO add your handling code here:
       loadGraphreport();
    }//GEN-LAST:event_btnClientGraphReportActionPerformed

    private void btnClientReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientReportActionPerformed
        // TODO add your handling code here:
        loadreport();
    }//GEN-LAST:event_btnClientReportActionPerformed

    private void btnDoneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoneMouseClicked
       
    }//GEN-LAST:event_btnDoneMouseClicked

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        jInternalFrameInvoice.setVisible(false);
        jInternalFrameCollection.setVisible(true);
    }//GEN-LAST:event_btnDoneActionPerformed

    private void btnMapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapActionPerformed
        // TODO add your handling code here:
        MapMain map = new MapMain();
        map.setVisible(true);
    }//GEN-LAST:event_btnMapActionPerformed

    private void btnfilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfilterActionPerformed
        jInternalFrameCustomerReports.setVisible(false);
        jInternalFrameReports1.setVisible(true);
    }//GEN-LAST:event_btnfilterActionPerformed

    private void btnBack6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack6ActionPerformed
        // TODO add your handling code here:
        jInternalFrameReports1.setVisible(false);
        jInternalFrameCustomerReports.setVisible(true);
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

    private void txt_expMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_expMouseClicked
        txt_exp.setText("");
    }//GEN-LAST:event_txt_expMouseClicked
    


public void loadreport() {


        HashMap a = new HashMap();
        a.put("Clientid", code);
        a.put("date", today);
        
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\ReportClientOrderCost.jrxml");
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
        a.put("Clientid", code);
        a.put("date", today);
        
      
        panelLoad.removeAll();
        panelLoad.repaint();
        panelLoad.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\GraphReportClient.jrxml");
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
        a.put("Clientid", ""+code);
        a.put("date", today);
        a.put("sdate", fdate);
        a.put("edate", tdate);
       
        panelLoad1.removeAll();
        panelLoad1.repaint();
        panelLoad1.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\FilteredClientReport.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad1.setLayout(new BorderLayout());
              panelLoad1.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }
}

public void loadInvoice() {


        HashMap a = new HashMap();
        a.put("Rid",c_aid);
        a.put("Oid", c_ooid);
        a.put("Odate", today);
        a.put("Cid", code);
        a.put("Cname", c_fname);
        a.put("CNIC", c_nic);
        a.put("Cphone",c_phone);
        a.put("Gname",fg_name );
        a.put("Gtype", fg_type);
        a.put("Gcolor", fg_color);
        a.put("date",today);
        a.put("gID",fg_cid );
        a.put("gPieces", fg_pieces);
        a.put("total",fg_sprice );
      
        panelLoad2.removeAll();
        panelLoad2.repaint();
        panelLoad2.revalidate();
        
        try {
              JasperDesign jdesign = JRXmlLoader.load("D:\\AshrafGems\\Ashraf Gems\\src\\View\\InvoicGem.jrxml");
              JasperReport jreport = JasperCompileManager.compileReport(jdesign);

              JasperPrint jprint = JasperFillManager.fillReport(jreport, a, con);

              JRViewer v = new JRViewer(jprint);
              panelLoad2.setLayout(new BorderLayout());
              panelLoad2.add(v);

        } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e);
        }

}


    public void bar(JLabel lab){
        lblBar1.setOpaque(false);
        lblBar2.setOpaque(false);
      
        lblBar4.setOpaque(false);
        lblBar5.setOpaque(false);
        lblBar6.setOpaque(false);
        lab.setOpaque(true);
        menu.repaint();
    }
    
    public void slide(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                int nb=0;
                try{
                    while(true){
                        switch(nb){
                            case 0:
                                Thread.sleep(300);
                                ac.jLabelXLeft(850, 400, 12, 10, lblPara);
                                
                                ac.jLabelXRight(-600, 0, 12, 10, lblImage);
                        }
                    }
                }catch(Exception e){
                    
                }
            }
        }){
            
        }.start();
    }
   
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private View.MyButton btnBack;
    private View.MyButton btnBack6;
    private View.MyButton btnCancel;
    private View.MyButton btnClear;
    private View.MyButton btnClear2;
    private View.MyButton btnClientGraphReport;
    private View.MyButton btnClientReport;
    private javax.swing.JPanel btnCustomerHelp;
    private javax.swing.JPanel btnCustomerReports;
    private View.MyButton btnDone;
    private javax.swing.JLabel btnExit;
    private View.MyButton btnGenerate;
    private javax.swing.JLabel btnLogOut;
    private View.MyButton btnMap;
    private javax.swing.JLabel btnMiniMize;
    private View.MyButton btnMore;
    private View.MyButton btnOrder;
    private View.MyButton btnOrderConfirm;
    private javax.swing.JPanel btnOrderDetails;
    private javax.swing.JLabel btnProfile;
    private View.MyButton btnSave;
    private View.MyButton btn_pay;
    private View.MyButton btn_reset;
    private View.MyButton btn_search;
    private View.MyButton btnfilter;
    private javax.swing.JPanel buttonCollection;
    private javax.swing.JPanel buttonHome;
    private javax.swing.JComboBox<String> combo_cardType;
    private com.toedter.calendar.JDateChooser datechooser_from;
    private com.toedter.calendar.JDateChooser datechooser_to;
    private javax.swing.JPanel form1;
    private javax.swing.JInternalFrame jInternalFrameCollection;
    private javax.swing.JInternalFrame jInternalFrameCustomerHelp;
    private javax.swing.JInternalFrame jInternalFrameCustomerProfile;
    private javax.swing.JInternalFrame jInternalFrameCustomerReports;
    private javax.swing.JInternalFrame jInternalFrameHome;
    private javax.swing.JInternalFrame jInternalFrameInvoice;
    private javax.swing.JInternalFrame jInternalFrameOrderConfirm;
    private javax.swing.JInternalFrame jInternalFrameOrderDetails;
    private javax.swing.JInternalFrame jInternalFrameOrderSummary;
    private javax.swing.JInternalFrame jInternalFrameReports1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel64;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelFrame1;
    private javax.swing.JPanel jPanelFrame2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel label_agid;
    private javax.swing.JLabel label_agid1;
    private javax.swing.JLabel label_cid;
    private javax.swing.JLabel label_cid1;
    private javax.swing.JLabel label_cname;
    private javax.swing.JLabel label_cname1;
    private javax.swing.JLabel label_cnic;
    private javax.swing.JLabel label_cnic2;
    private javax.swing.JLabel label_codate;
    private javax.swing.JLabel label_codate1;
    private javax.swing.JLabel label_coid;
    private javax.swing.JLabel label_coid1;
    private javax.swing.JLabel label_cphone;
    private javax.swing.JLabel label_cphone1;
    private javax.swing.JLabel label_gcid;
    private javax.swing.JLabel label_gcid1;
    private javax.swing.JLabel label_gcolor;
    private javax.swing.JLabel label_gcolor1;
    private javax.swing.JLabel label_gcost;
    private javax.swing.JLabel label_gimage;
    private javax.swing.JLabel label_gimagel;
    private javax.swing.JLabel label_gname;
    private javax.swing.JLabel label_gname1;
    private javax.swing.JLabel label_gpieces;
    private javax.swing.JLabel label_gpieces1;
    private javax.swing.JLabel label_gtype;
    private javax.swing.JLabel label_gtype1;
    private javax.swing.JLabel label_gweight;
    private javax.swing.JLabel label_gweight1;
    private javax.swing.JLabel lblBar1;
    private javax.swing.JLabel lblBar2;
    private javax.swing.JLabel lblBar4;
    private javax.swing.JLabel lblBar5;
    private javax.swing.JLabel lblBar6;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblPara;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblPrice1;
    private javax.swing.JLabel lblPrice2;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserName2;
    private javax.swing.JLabel lbl_cardno;
    private javax.swing.JLabel lbl_cardtype;
    private javax.swing.JLabel lbl_cdate;
    private javax.swing.JLabel lbl_certiID1;
    private javax.swing.JLabel lbl_certiID2;
    private javax.swing.JLabel lbl_certiID3;
    private javax.swing.JLabel lbl_cid;
    private javax.swing.JLabel lbl_cname;
    private javax.swing.JLabel lbl_coid;
    private javax.swing.JLabel lbl_coid1;
    private javax.swing.JLabel lbl_cvc;
    private javax.swing.JLabel lbl_exp;
    private javax.swing.JLabel lbl_gcid;
    private javax.swing.JLabel lbl_gcolor;
    private javax.swing.JLabel lbl_gemImage;
    private javax.swing.JLabel lbl_gname;
    private javax.swing.JLabel lbl_gpieces;
    private javax.swing.JLabel lbl_gshape;
    private javax.swing.JLabel lbl_gsprice;
    private javax.swing.JLabel lbl_gtype;
    private javax.swing.JLabel lbl_gweight;
    private javax.swing.JLabel lbl_image1;
    private javax.swing.JLabel lbl_image2;
    private javax.swing.JLabel lbl_image3;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JLabel lbl_otot;
    private javax.swing.JLabel lbl_searchGemName;
    private javax.swing.JLabel lbl_tot;
    private javax.swing.JLabel lbl_weight;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel panelLoad;
    private javax.swing.JPanel panelLoad1;
    private javax.swing.JPanel panelLoad2;
    private javax.swing.JTable tbl_cliorder;
    private javax.swing.JTable tbl_fgems;
    private javax.swing.JTextField tesxtf_cname;
    private javax.swing.JTextField tesxtf_gid;
    private javax.swing.JTextField tesxtf_odate;
    private javax.swing.JTextField tesxtf_oid;
    private javax.swing.JTextField tesxtf_tot;
    private javax.swing.JTextField text_gcost;
    private javax.swing.JTextField txt_cardno;
    private javax.swing.JTextField txt_cid;
    private javax.swing.JTextField txt_cvc;
    private javax.swing.JTextField txt_exp;
    private javax.swing.JTextField txt_gcolor;
    private javax.swing.JTextField txt_gname;
    private javax.swing.JTextField txt_gpieces;
    private javax.swing.JTextField txt_gshape;
    private javax.swing.JTextField txt_gsprice;
    private javax.swing.JTextField txt_gtype;
    private javax.swing.JTextField txt_gweight;
    private javax.swing.JTextField txt_sgname;
    private javax.swing.JTextField txt_tot;
    // End of variables declaration//GEN-END:variables
}
