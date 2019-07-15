/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrics.cbt.ui.pages;

import SecuGen.FDxSDKPro.jni.JSGFPLib;
import SecuGen.FDxSDKPro.jni.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.jni.SGFDxDeviceName;
import SecuGen.FDxSDKPro.jni.SGFDxErrorCode;
import SecuGen.FDxSDKPro.jni.SGFingerInfo;
import SecuGen.FDxSDKPro.jni.SGFingerPosition;
import SecuGen.FDxSDKPro.jni.SGImpressionType;
import SecuGen.FDxSDKPro.jni.SGPPPortAddr;
import biometrics.cbt.domain.DBHandler;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author JBoss
 */
public class Application extends javax.swing.JDialog {

    private JSGFPLib fplib = null;
    private long deviceName;
    private long devicePort;
    private long ret;
    private final byte[] regMin1 = new byte[400];
    private final SGDeviceInfoParam deviceInfo = new SGDeviceInfoParam();
    private BufferedImage imgRegistration1;
    private boolean r1Captured;
    private String fingerPath;

    private Webcam web;
    private WebcamPanel webPanel;
    private BufferedImage ri;
    private Image image;

    private final Connection con;
    private String facePath;

    public Application(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        //initializeDevice();
        captureButton.setIcon(createCustomizedImage("/biometrics/cbt/ui/images/exit.png", 0));
        snapButton.setIcon(createCustomizedImage("/biometrics/cbt/ui/images/cam.png", 0));
        cancelButton.setIcon(createCustomizedImage("/biometrics/cbt/ui/images/cancel.PNG", 0));
        registerButton.setIcon(createCustomizedImage("/biometrics/cbt/ui/images/Add.png", 0));

        Image imge = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/biometrics/cbt/ui/images/images.png"));
        imge = imge.getScaledInstance(210, 190, Image.SCALE_SMOOTH);
        jLabelRegisterImage1.setIcon(new ImageIcon(imge));
        con = DBHandler.connect();
    }

    private boolean validateField() {
        if (nameField.getText().equals("")) {
            return false;
        }
        if (phoneField.getText().equals("")) {
            return false;
        }
        if (emailField.getText().equals("")) {
            return false;
        }
        if (usernameField.getText().equals("")) {
            return false;
        }
        if (new String(passwordField.getPassword()).equals("")) {
            return false;
        }
        return !addressField.getText().equals("");
    }

    private void register() {
        try {
            PreparedStatement ps = con.prepareStatement("insert into student_reg(name, phone, email, "
                    + "address,username, password, face, finger) values(?,?,?,?,?,?,?,?)");
            ps.setString(1, nameField.getText());
            ps.setString(2, phoneField.getText());
            ps.setString(3, emailField.getText());
            ps.setString(4, addressField.getText());
            ps.setString(5, usernameField.getText());
            ps.setString(6, new String(passwordField.getPassword()));
            ps.setString(7, facePath);
            ps.setString(8, fingerPath);
            int x = ps.executeUpdate();
            if (x != -1) {
                JOptionPane.showMessageDialog(this, "Registration Successful");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private Icon createCustomizedImage(String name, int w) {
        Image imge = Toolkit.getDefaultToolkit().createImage(getClass().getResource(name));
        if (w == 0) {
            imge = imge.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        } else {
            imge = imge.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        }
        return new ImageIcon(imge);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        phoneField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addressField = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabelRegisterImage1 = new javax.swing.JLabel();
        captureButton = new javax.swing.JButton();
        jLabelStatus = new javax.swing.JLabel();
        facePanel = new javax.swing.JPanel();
        faceImage = new javax.swing.JLabel();
        snapButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(java.awt.Color.white);

        jLabel1.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 0));
        jLabel1.setText("Registration Form");

        jPanel2.setBackground(java.awt.Color.white);

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 0, 0));
        jLabel3.setText("Name:");

        nameField.setBackground(java.awt.Color.white);
        nameField.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        nameField.setForeground(new java.awt.Color(102, 0, 0));
        nameField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        phoneField.setBackground(java.awt.Color.white);
        phoneField.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        phoneField.setForeground(new java.awt.Color(102, 0, 0));
        phoneField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 0, 0));
        jLabel4.setText("Phone:");

        emailField.setBackground(java.awt.Color.white);
        emailField.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        emailField.setForeground(new java.awt.Color(102, 0, 0));
        emailField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 0, 0));
        jLabel5.setText("Email:");

        usernameField.setBackground(java.awt.Color.white);
        usernameField.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        usernameField.setForeground(new java.awt.Color(102, 0, 0));
        usernameField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 0, 0));
        jLabel6.setText("Username:");

        jLabel7.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 0, 0));
        jLabel7.setText("Password:");

        passwordField.setBackground(java.awt.Color.white);
        passwordField.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        passwordField.setForeground(new java.awt.Color(102, 0, 0));
        passwordField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1)));

        jLabel8.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 0, 0));
        jLabel8.setText("Address:");

        jScrollPane1.setBackground(java.awt.Color.white);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)));
        jScrollPane1.setForeground(new java.awt.Color(0, 0, 0));

        addressField.setBackground(java.awt.Color.white);
        addressField.setColumns(20);
        addressField.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        addressField.setForeground(new java.awt.Color(102, 0, 0));
        addressField.setLineWrap(true);
        addressField.setRows(5);
        addressField.setWrapStyleWord(true);
        jScrollPane1.setViewportView(addressField);

        jPanel3.setBackground(java.awt.Color.white);
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)), "Finger Print", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(102, 0, 0))); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabelRegisterImage1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelRegisterImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/biometrics/cbt/ui/images/images.png"))); // NOI18N
        jLabelRegisterImage1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)));
        jLabelRegisterImage1.setPreferredSize(new java.awt.Dimension(2, 200));
        jPanel3.add(jLabelRegisterImage1, java.awt.BorderLayout.CENTER);

        captureButton.setFont(new java.awt.Font("Gabriola", 1, 16)); // NOI18N
        captureButton.setForeground(new java.awt.Color(102, 0, 0));
        captureButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/biometrics/cbt/ui/images/exit.png"))); // NOI18N
        captureButton.setText("Capture");
        captureButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)));
        captureButton.setContentAreaFilled(false);
        captureButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        captureButton.setFocusPainted(false);
        captureButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        captureButton.setPreferredSize(new java.awt.Dimension(115, 30));
        captureButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                captureButtonActionPerformed(evt);
            }
        });
        jPanel3.add(captureButton, java.awt.BorderLayout.SOUTH);

        jLabelStatus.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabelStatus.setForeground(new java.awt.Color(51, 0, 255));

        facePanel.setBackground(java.awt.Color.white);
        facePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)), "Face", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(102, 0, 0))); // NOI18N
        facePanel.setLayout(new java.awt.BorderLayout());

        faceImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        faceImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)));
        faceImage.setPreferredSize(new java.awt.Dimension(2, 200));
        facePanel.add(faceImage, java.awt.BorderLayout.CENTER);

        snapButton.setFont(new java.awt.Font("Gabriola", 1, 16)); // NOI18N
        snapButton.setForeground(new java.awt.Color(102, 0, 0));
        snapButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/biometrics/cbt/ui/images/exit.png"))); // NOI18N
        snapButton.setText("Webcam");
        snapButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)));
        snapButton.setContentAreaFilled(false);
        snapButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        snapButton.setFocusPainted(false);
        snapButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        snapButton.setPreferredSize(new java.awt.Dimension(115, 30));
        snapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snapButtonActionPerformed(evt);
            }
        });
        facePanel.add(snapButton, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8)
                                .addComponent(jLabel7))
                            .addGap(9, 9, 9)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(facePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {emailField, jScrollPane1, nameField, passwordField, phoneField, usernameField});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {facePanel, jPanel3});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(facePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        registerButton.setFont(new java.awt.Font("Gabriola", 1, 18)); // NOI18N
        registerButton.setForeground(new java.awt.Color(102, 0, 0));
        registerButton.setText("Register");
        registerButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)));
        registerButton.setContentAreaFilled(false);
        registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Gabriola", 1, 18)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(102, 0, 0));
        cancelButton.setText("Cancel");
        cancelButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)));
        cancelButton.setContentAreaFilled(false);
        cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(153, 153, 153))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, registerButton});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerButton)
                    .addComponent(cancelButton))
                .addGap(214, 214, 214))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void captureButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_captureButtonActionPerformed
        int[] quality = new int[1];
        System.out.println("Hi buffer");
        try{
        byte[] imageBuffer1 = ((java.awt.image.DataBufferByte) imgRegistration1.getRaster().getDataBuffer()).getData();
        System.out.println("I am here");
        long iError = SGFDxErrorCode.SGFDX_ERROR_NONE;
        System.out.println("I am not here");
        iError = fplib.GetImageEx(imageBuffer1, 5 * 1000, 0, 50);
        fplib.GetImageQuality(deviceInfo.imageWidth, deviceInfo.imageHeight, imageBuffer1, quality);
        SGFingerInfo fingerInfo = new SGFingerInfo();
        fingerInfo.FingerNumber = SGFingerPosition.SG_FINGPOS_LI;
        fingerInfo.ImageQuality = quality[0];
        fingerInfo.ImpressionType = SGImpressionType.SG_IMPTYPE_LP;
        fingerInfo.ViewNumber = 1;
        

        if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
            this.jLabelRegisterImage1.setIcon(new ImageIcon(imgRegistration1.getScaledInstance(130, 150, Image.SCALE_DEFAULT)));
            if (quality[0] == 0) {
                this.jLabelStatus.setText("GetImageEx() Success [" + ret + "] but image quality is [" + quality[0] + "]. Please try again");
            } else {

                this.jLabelStatus.setText("GetImageEx() Success [" + ret + "]");

                iError = fplib.CreateTemplate(fingerInfo, imageBuffer1, regMin1);
                if (iError == SGFDxErrorCode.SGFDX_ERROR_NONE) {
                    this.jLabelStatus.setText("First registration image was captured");
                    r1Captured = true;
                    registerButton.setEnabled(true);
                } else {
                    this.jLabelStatus.setText("CreateTemplate() Error : " + iError);
                }
            }
        } else {
            this.jLabelStatus.setText("GetImageEx() Error : " + iError);
        }
        }
        catch(Exception ee){
            ee.printStackTrace();
        }
    }//GEN-LAST:event_captureButtonActionPerformed

    private void snapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snapButtonActionPerformed
        if (!snapButton.getText().equals("Capture")) {
            try {
                web = Webcam.getDefault();
                webPanel = new WebcamPanel(web);
                webPanel.setFillArea(true);
                facePanel.add(webPanel);
                facePanel.repaint();
                webPanel.setSize(facePanel.getWidth() - 10, 200);
                snapButton.setText("Capture");
            } catch (IllegalArgumentException iae) {
                ri = null;
                JOptionPane.showMessageDialog(this, "System Webcam not accessible. Please connect a webcam", "ERROR", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            try {
                ri = web.getImage();
                image = ri;
                faceImage.setIcon(new ImageIcon(ri));
                facePanel.remove(webPanel);
                facePanel.add(faceImage);
                facePanel.repaint();
                web.close();
                snapButton.setText("Webcam");
            } catch (Exception t) {

                ri = null;
            }
        }
    }//GEN-LAST:event_snapButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        try {
            if (!validateField()) {
                throw new IllegalArgumentException("One or more field is empty");
            }
            File path = new File(System.getProperty("user.home") + File.separator + ".biocbt" + File.separator + "fingerSamples");
            path.mkdirs();

            File path2 = new File(System.getProperty("user.home") + File.separator + ".biocbt" + File.separator + "faceSamples");
            path2.mkdirs();
            ImageIO.write(imgRegistration1, "jpg", new File(path.getPath() + File.separator + usernameField.getText() + ".jpg"));
            ImageIO.write(ri, "jpg", new File(path2.getPath() + File.separator + usernameField.getText() + ".jpg"));
            fingerPath = path.getAbsolutePath() + File.separator + usernameField.getText() + ".jpg";
            facePath = path2.getAbsolutePath() + File.separator + usernameField.getText() + ".jpg";
            if (!(new File(fingerPath).exists())) {
                throw new IllegalArgumentException("Capture fingerprint image first");
            }
            if (!(new File(facePath).exists())) {
                throw new IllegalArgumentException("Capture face image first");
            }
            register();
            dispose();
        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_registerButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application dialog = new Application(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea addressField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton captureButton;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel faceImage;
    private javax.swing.JPanel facePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelRegisterImage1;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField phoneField;
    private javax.swing.JButton registerButton;
    private javax.swing.JButton snapButton;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables

    private void initializeDevice() {
        int selectedDevice = 0;
        switch (selectedDevice) {
            case 0: //USB
            default:
                this.deviceName = SGFDxDeviceName.SG_DEV_AUTO;
                break;
            case 1: //FDU05
                this.deviceName = SGFDxDeviceName.SG_DEV_FDU05;
                break;
            case 2: //FDU04
                this.deviceName = SGFDxDeviceName.SG_DEV_FDU04;
                break;
            case 3: //CN_FDU03
                this.deviceName = SGFDxDeviceName.SG_DEV_FDU03;
                break;
            case 4: //CN_FDU02
                this.deviceName = SGFDxDeviceName.SG_DEV_FDU02;
                break;
        }
        fplib = new JSGFPLib();
        ret = fplib.Init(this.deviceName);
        if ((fplib != null) && (ret == SGFDxErrorCode.SGFDX_ERROR_NONE)) {
            this.devicePort = SGPPPortAddr.AUTO_DETECT;
            int sel = 0;
            switch (sel) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    this.devicePort = sel - 1;
                    break;
            }
            ret = fplib.OpenDevice(this.devicePort);
            if (ret == SGFDxErrorCode.SGFDX_ERROR_NONE) {
                this.jLabelStatus.setText("OpenDevice() Success [" + ret + "]");
                ret = fplib.GetDeviceInfo(deviceInfo);
                if (ret == SGFDxErrorCode.SGFDX_ERROR_NONE) {
                    imgRegistration1 = new BufferedImage(deviceInfo.imageWidth, deviceInfo.imageHeight, BufferedImage.TYPE_BYTE_GRAY);
                } else {
                    this.jLabelStatus.setText("GetDeviceInfo() Error [" + ret + "]");
                }
            } else {

            }
        } else {

        }
    }
}
