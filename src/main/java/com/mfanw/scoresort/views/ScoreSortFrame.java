/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfanw.scoresort.views;

import com.mfanw.scoresort.bean.SchoolBean;
import com.mfanw.scoresort.bean.StudentBean;
import com.mfanw.scoresort.dao.ExcelDao;
import com.mfanw.scoresort.dao.ExcelFilter;
import com.mfanw.scoresort.utils.ConfigUtil;
import com.mfanw.scoresort.utils.ExceptionUtil;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mengwei
 */
public class ScoreSortFrame extends javax.swing.JFrame {

    private MessageBox messagesBox = null;

    private AboutDialog aboutDialog = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelectFile;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBtnBeginHandle;
    private javax.swing.JComboBox<String> jCBDDHFGS;
    private javax.swing.JCheckBox jCBIsFilter;
    private javax.swing.JComboBox<String> jCBXPerDang;
    private javax.swing.JComboBox<String> jCBXPerDangPC;
    private javax.swing.JLabel jLStudentDetail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRBAdvance;
    private javax.swing.JRadioButton jRBNormal;
    private javax.swing.JTextField jTFAdvance;
    private javax.swing.JTextField jTFExceptLessTotalScore;
    private javax.swing.JTextField jTFGRBClassType;
    private javax.swing.JTextField jTFGRBTopNum;
    private javax.swing.JTextField jTFMingCiFenDang;
    private javax.swing.JTextField jTFNumPerDD;
    private javax.swing.JTextField jTFTopNumForFilter;
    private javax.swing.JTextField jTfFileName;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    private File selectedExcel = null;
    List<StudentBean> students = null;
    private SchoolBean school = null;
    private ScoreSortSettings settings = new ScoreSortSettings();
    /**
     * Creates new form ScoreSortFrame
     */
    public ScoreSortFrame() {
        initComponents();
        afterInitComponents();
        //将Java桌面应用程序的外观设置为Windows样式
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException err) {
            System.out.println("xp:" + err);
        }
        messagesBox = new MessageBox(this, true);
        messagesBox.setVisible(false);

        aboutDialog = new AboutDialog(this, true);
        aboutDialog.setVisible(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScoreSortFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScoreSortFrame().setVisible(true);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jTfFileName = new javax.swing.JTextField();
        btnSelectFile = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jRBNormal = new javax.swing.JRadioButton();
        jRBAdvance = new javax.swing.JRadioButton();
        jCBDDHFGS = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTFAdvance = new javax.swing.JTextField();
        jTFNumPerDD = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jCBXPerDang = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jCBXPerDangPC = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTFMingCiFenDang = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTFGRBTopNum = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTFGRBClassType = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jCBIsFilter = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jTFTopNumForFilter = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFExceptLessTotalScore = new javax.swing.JTextField();
        jBtnBeginHandle = new javax.swing.JButton();
        jLStudentDetail = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(200, 100, 0, 0));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setResizable(false);

        jLabel1.setText("文件名称：");

        jTfFileName.setEditable(false);
        jTfFileName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTfFileNameActionPerformed(evt);
            }
        });

        btnSelectFile.setText("选择文件");
        btnSelectFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectFileActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "等第选项"));

        buttonGroup1.add(jRBNormal);
        jRBNormal.setText("平均划分");
        jRBNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBNormalActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRBAdvance);
        jRBAdvance.setSelected(true);
        jRBAdvance.setText("正态划分");

        jCBDDHFGS.setActionCommand("");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jRBNormal, org.jdesktop.beansbinding.ELProperty.create("${selected}"), jCBDDHFGS, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jRBNormal, org.jdesktop.beansbinding.ELProperty.create("${selected}"), jCBDDHFGS, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jCBDDHFGS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBDDHFGSActionPerformed(evt);
            }
        });

        jLabel2.setText("每等第内数目:");
        jTFAdvance.setEditable(false);
        jTFAdvance.setText("A,B,C,D,E/0.15,0.35,0.35,0.13,0.02");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jRBNormal, org.jdesktop.beansbinding.ELProperty.create("${selected}"), jTFNumPerDD, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jRBNormal)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCBDDHFGS, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(31, 31, 31)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTFNumPerDD, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jRBAdvance)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTFAdvance)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRBNormal)
                                        .addComponent(jCBDDHFGS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(jTFNumPerDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jRBAdvance)
                                        .addComponent(jTFAdvance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "分档选项"));

        jLabel3.setText("总分分档：");

        jCBXPerDang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBXPerDangActionPerformed(evt);
            }
        });

        jLabel4.setText("微调");

        jLabel5.setText("名次分档：");

        jTFMingCiFenDang.setText("10,30,50,100,150,200,250,300,350,400,450,500");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCBXPerDang, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(33, 33, 33)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jCBXPerDangPC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTFMingCiFenDang)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jCBXPerDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4)
                                        .addComponent(jCBXPerDangPC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jTFMingCiFenDang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "光荣榜选项"));

        jLabel6.setText("总分前X名：");

        jTFGRBTopNum.setText("3");

        jLabel7.setText("班类：");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFGRBTopNum, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFGRBClassType)
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(jTFGRBTopNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)
                                        .addComponent(jTFGRBClassType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "过滤选项"));

        jCBIsFilter.setText("是否启用过滤");

        jLabel8.setText("保留总分每班TopX");

        jTFTopNumForFilter.setText("100");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jCBIsFilter, org.jdesktop.beansbinding.ELProperty.create("${selected}"), jTFTopNumForFilter, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jLabel9.setText("保留总分>=");

        jTFExceptLessTotalScore.setText("300");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jCBIsFilter, org.jdesktop.beansbinding.ELProperty.create("${selected}"), jTFExceptLessTotalScore, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jCBIsFilter)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTFTopNumForFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTFExceptLessTotalScore, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jCBIsFilter)
                                        .addComponent(jLabel8)
                                        .addComponent(jTFTopNumForFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9)
                                        .addComponent(jTFExceptLessTotalScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(24, Short.MAX_VALUE))
        );

        jBtnBeginHandle.setText("开始处理");
        jBtnBeginHandle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBeginHandleActionPerformed(evt);
            }
        });

        jLStudentDetail.setText("学生总数目：X   ");

        jMenu2.setText("版本信息");

        jMenuItem3.setText("版本信息");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(265, 265, 265)
                                                .addComponent(jBtnBeginHandle))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jLabel1)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLStudentDetail)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(jTfFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addComponent(btnSelectFile))))
                                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                .addContainerGap(88, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSelectFile)
                                        .addComponent(jTfFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLStudentDetail)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnBeginHandle)
                                .addContainerGap(38, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRBNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBNormalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRBNormalActionPerformed

    private void jTfFileNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTfFileNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTfFileNameActionPerformed

    private void btnSelectFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectFileActionPerformed
        try {
            String path = ConfigUtil.getConfig("jf_path");
            JFileChooser jfc = path == null ? new JFileChooser() : new JFileChooser(path);
            jfc.setDialogTitle("请选择需要处理的Excel文件");
            jfc.setFileFilter(new ExcelFilter("xls", "Excel文件"));
            int result = jfc.showOpenDialog(jfc);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedExcel = jfc.getSelectedFile();
                this.jTfFileName.setText(selectedExcel.getAbsolutePath());
                try {
                    students = ExcelDao.loadExcel(selectedExcel.getAbsolutePath());
                    school = new SchoolBean(settings, students);
                    ConfigUtil.saveConfig("jf_path", selectedExcel.getAbsolutePath());
                } catch (Exception e) {
                    messagesBox.setContent("运算发生异常：\n" + e.getMessage() + "\n" + ExceptionUtil.printStackTraceMgr(e.getStackTrace()));
                    messagesBox.setModal(true);
                    messagesBox.setVisible(true);
                }
            }
            if (selectedExcel != null) {
                // 设置显示信息
                int tempCutNum = this.jCBDDHFGS.getSelectedIndex() + 2;
                this.jTFNumPerDD.setText("" + students.size() / tempCutNum);
                this.settings.setStuPerDegree(students.size() / tempCutNum);
                this.jLStudentDetail.setText("学生总数：" + students.size());
                setXScoreBar();
            }
        } catch (Exception ex) {
            Logger.getLogger(ScoreSortFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSelectFileActionPerformed
    // End of variables declaration//GEN-END:variables

    private void jCBXPerDangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBXPerDangActionPerformed
        setXScoreBar();
    }//GEN-LAST:event_jCBXPerDangActionPerformed

    private void jBtnBeginHandleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBeginHandleActionPerformed
        try {
            // 重新设置settings
            settings.setDistribution(this.jRBAdvance.isSelected());
            String[] advanceText = this.jTFAdvance.getText().split("/");
            settings.setDistributionName(advanceText[0].split(","));
            String[] perStr = advanceText[1].split(",");
            double[] percents = new double[perStr.length];
            for (int i = 0; i < perStr.length; i++) {
                percents[i] = Double.parseDouble(perStr[i]);
            }
            settings.setDistributionPercent(percents);
            settings.setCutNum(this.jCBDDHFGS.getSelectedIndex() + 2);
            settings.setStuPerDegree(Integer.parseInt(this.jTFNumPerDD.getText()));
            // 重新加载数据
            students = ExcelDao.loadExcel(selectedExcel.getAbsolutePath());
            school = new SchoolBean(settings, students);

            String saveName = selectedExcel.getAbsolutePath();
            String saveName2 = "";
            saveName = saveName.substring(0, saveName.toLowerCase().lastIndexOf(".xls"));
            if (settings.isDistribution()) {
                saveName += "-正态划分";
            } else {
                saveName += "-" + settings.getCutNum() + "级划分";
            }
            saveName += "-" + school.getxScore() + "分一档.xls";
            settings.setMingCiFenDang(this.jTFMingCiFenDang.getText());
            settings.setGRBClassType(this.jTFGRBClassType.getText(), school);
            settings.setGRBTopNum(this.jTFGRBTopNum.getText());
            school.mainHandler();
            ExcelDao.saveExcel(saveName, students, school, settings);
            // 文件处理结束
            // 根据筛选条件，筛选处理后的文件
            if (this.jCBIsFilter.isSelected()) {
                int topNum = Integer.parseInt(this.jTFTopNumForFilter.getText());
                double exceptLessScore = Double.parseDouble(this.jTFExceptLessTotalScore.getText());

                students = ExcelDao.loadExcel(saveName, topNum, exceptLessScore);
                // 每等级学生数目需要重新设置
                settings.setStuPerDegree(students.size() / settings.getCutNum());
                this.jTFNumPerDD.setText("" + students.size() / settings.getCutNum());
                school = new SchoolBean(settings, students);

                // 重新设置文件名称
                saveName2 = saveName.substring(0, saveName.toLowerCase().lastIndexOf(".xls"));
                saveName2 += "-启用过滤.xls";
                school.mainHandler();
                ExcelDao.saveExcel(saveName2, students, school, settings);
            }

            if (saveName2 != null && saveName2.length() > 0) {
                messagesBox.setContent("运算结果为两个文件：\n" + saveName + "\n" + saveName2);
                messagesBox.setModal(true);
                messagesBox.setVisible(true);
            } else {
                messagesBox.setContent("运算结果为一个文件：\n" + saveName);
                messagesBox.setModal(true);
                messagesBox.setVisible(true);
            }
        } catch (Exception e) {
            messagesBox.setContent("运算发生异常：" + e.toString() + "\n" + ExceptionUtil.printStackTraceMgr(e.getStackTrace()));
            messagesBox.setModal(true);
            messagesBox.setVisible(true);
        }
    }//GEN-LAST:event_jBtnBeginHandleActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        aboutDialog.setModal(true);
        aboutDialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jCBDDHFGSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBDDHFGSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBDDHFGSActionPerformed

    /**
     * 设置显示的X分一档的下拉框,默认是5分一档
     */
    public void setXScoreBar() {
        jCBXPerDangPC.removeAllItems();
        int xScore = this.jCBXPerDang.getSelectedIndex() + 1;
        if (school == null || school.getScoresHigh() == null) {
            return;
        }
        long high = Math.round(school.getScoresHigh()[settings.getKeMuSize() - 1]);
        long low = Math.round(school.getScoresLow()[settings.getKeMuSize() - 1]);
        for (int i = 0; i < xScore; i++) {
            String message = i + ":[" + (high - xScore + 1) + "," + high + "]";
            long temp = (high - low) % xScore;
            if (temp == 0) {
                temp = xScore;
            }
            message += "-[" + (temp - xScore + 1) + "," + (temp) + "]";
            high++;
            jCBXPerDangPC.addItem(message);
        }
        school.setxScore(xScore);
    }

    private void afterInitComponents() {
        jCBDDHFGS.removeAllItems();
        for (int i = 2; i <= 26; i++) {
            jCBDDHFGS.addItem(i + " 级划分");
        }
        jCBDDHFGS.setSelectedIndex(3);
        jCBXPerDang.removeAllItems();
        for (int i = 0; i < 20; i++) {
            jCBXPerDang.addItem((i + 1) + " 分一档 ");
        }
        jCBXPerDang.setSelectedIndex(4);
    }
}
