package com.mfanw.scoresort.views;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.mfanw.scoresort.consts.AppConst;

public class AboutBox1 extends JDialog {

    public static final int FRAMEW = 470;
    public static final int FRAMEH = 194;

    private Container mainPanel;

    /**
     * 
     */
    private static final long serialVersionUID = -6036881259030339187L;

    public AboutBox1() {
        initComponents();
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((scrSize.width - FRAMEW) / 2, (scrSize.height - FRAMEH) / 2, FRAMEW, FRAMEH);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("考试成绩分析 - 关于");
        this.setResizable(false);
    }

    private void initComponents() {

        mainPanel = this.getContentPane();
        this.setLayout(null);
        // 图片
        JLabel imageLabel = new JLabel();
        URL url = this.getClass().getResource("/images/byz.jpg");
        imageLabel.setIcon(new ImageIcon(url));
        mainPanel.add(imageLabel);
        imageLabel.setBounds(5, 5, 230, 155);

        JLabel appTitleLabel = new JLabel("考试成绩分析");
        appTitleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        mainPanel.add(appTitleLabel);
        appTitleLabel.setBounds(250, 5, 200, 25);

        JLabel appDescLabel = new JLabel("通过对学生成绩的分析，评定级别。");
        mainPanel.add(appDescLabel);
        appDescLabel.setBounds(250, 30, 200, 25);

        JLabel appDescLabel2 = new JLabel("第一版创建于2011年3月26日");
        mainPanel.add(appDescLabel2);
        appDescLabel2.setBounds(250, 45, 200, 25);

        JLabel versionLabel = new JLabel("版本:");
        versionLabel.setFont(new Font("宋体", Font.BOLD, 12));
        mainPanel.add(versionLabel);
        versionLabel.setBounds(250, 80, 200, 25);
        JLabel appVersionLabel = new JLabel(AppConst.VERSION);
        appVersionLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        mainPanel.add(appVersionLabel);
        appVersionLabel.setBounds(300, 80, 200, 25);

        JLabel authorLabel = new JLabel("作者:");
        authorLabel.setFont(new Font("宋体", Font.BOLD, 12));
        mainPanel.add(authorLabel);
        authorLabel.setBounds(250, 100, 200, 25);
        JLabel appAuthorLabel = new JLabel("孟伟");
        appAuthorLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        mainPanel.add(appAuthorLabel);
        appAuthorLabel.setBounds(300, 100, 200, 25);

        JLabel emailLabel = new JLabel("邮箱:");
        emailLabel.setFont(new Font("宋体", Font.BOLD, 12));
        mainPanel.add(emailLabel);
        emailLabel.setBounds(250, 120, 200, 25);
        JLabel appemailLabel = new JLabel("772333621@qq.com");
        appemailLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        mainPanel.add(appemailLabel);
        appemailLabel.setBounds(300, 120, 200, 25);
    }

}
