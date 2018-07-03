package com.mfanw.scoresort.views;

import java.util.ArrayList;

import com.mfanw.scoresort.bean.ClassBean;
import com.mfanw.scoresort.bean.SchoolBean;

public class ScoreSortSettings {

    /**
     * 及格线
     */
    private double[] JGLow = new double[]{90, 90, 90, 72, 65, 43, 450};

    /**
     * 暂时就是只有六个科目
     */
    private int keMuSize = 7;

    /**
     * 每个等级内学生人数，是一个平均数量，如果发生名次相同可能会变大或者变小
     */
    private int stuPerDegree = 0;

    /**
     * 等级个数
     */
    private int cutNum = 5;

    /**
     * 各科的等级分数线（每个等级的最低分数）degreeScores[科目][等级]
     */
    private double[][] degreeScores = new double[this.keMuSize][this.cutNum];

    private int scoreDegreePC = 0;

    /**
     * 名次分档
     */
    private int[] mingCiFenDang = new int[]{10, 50, 100, 150, 200};

    /**
     * 光荣榜班类名称集合
     */
    private String[] GRBClassTypeText;

    /**
     * 光荣榜班类集合
     */
    private ArrayList<ArrayList<ClassBean>> GRBClassType;

    /**
     * 光荣榜截取前X名，默认X=3
     */
    private int GRBTopNum = 3;

    private boolean distribution;
    private String[] distributionName;
    private double[] distributionPercent;

    public double[] getJGLow() {
        return JGLow;
    }

    public void setJGLow(double[] jGLow) {
        JGLow = jGLow;
    }

    public int getKeMuSize() {
        return keMuSize;
    }

    public void setKeMuSize(int keMuSize) {
        this.keMuSize = keMuSize;
    }

    public int getCutNum() {
        return cutNum;
    }

    public void setCutNum(int cutNum) {
        this.cutNum = cutNum;
        this.degreeScores = new double[this.getKeMuSize()][this.cutNum];
    }

    public double[][] getDegreeScores() {
        return degreeScores;
    }

    /**
     * 给科目设置等级分数线（每个等级的最低分数）
     *
     * @param keMu 科目
     * @param degreeScore 等级分数线（每个等级的最低分数）
     */
    public void setDegreeScore(int keMu, double[] degreeScore) {
        this.degreeScores[keMu] = degreeScore;
    }

    public int getStuPerDegree() {
        return stuPerDegree;
    }

    public void setStuPerDegree(int stuPerDegree) {
        this.stuPerDegree = stuPerDegree;
    }

    public void setDegreeScores(double[][] degreeScores) {
        this.degreeScores = degreeScores;
    }

    public int getScoreDegreePC() {
        return scoreDegreePC;
    }

    public void setScoreDegreePC(int scoreDegreePC) {
        this.scoreDegreePC = scoreDegreePC;
    }

    public void setMingCiFenDang(String MCFD) {
        String[] tempTFs = MCFD.split(",");
        int[] tempTFInt = new int[tempTFs.length];
        for (int i = 0; i < tempTFs.length; i++) {
            tempTFInt[i] = Integer.parseInt(tempTFs[i].trim());
        }
        this.mingCiFenDang = tempTFInt;
    }

    public int[] getMingCiFenDang() {
        return mingCiFenDang;
    }

    public ArrayList<ArrayList<ClassBean>> getGRBClassType() {
        return GRBClassType;
    }

    public void setGRBClassType(String gRBClassType, SchoolBean school) {
        GRBClassType = new ArrayList<ArrayList<ClassBean>>();
        if (gRBClassType == null || gRBClassType.length() == 0) {
            this.GRBClassType.add(school.getClasses());
            this.GRBClassTypeText = new String[]{"全部"};
            return;
        }
        this.GRBClassTypeText = gRBClassType.split(",");
        for (int i = 0; i < GRBClassTypeText.length; i++) {
            ArrayList<ClassBean> classes = new ArrayList<ClassBean>();
            String[] temp = GRBClassTypeText[i].split("-");
            int start = Integer.parseInt(temp[0].trim());
            int end = -1;
            try {
                end = Integer.parseInt(temp[1].trim());
            } catch (Exception e) {
                // 没有结束班级，则表示一个班级自成一个班类
                ClassBean cla = school.getClassByName("" + start);
                if (cla == null) {
//                    throw new Exception("找不到班级名称=" + start + "的班级，请检查班类设置！");
                }
                classes.add(cla);
                this.GRBClassType.add(classes);
                continue;
            }
            for (int j = start; j <= end; j++) {
                ClassBean cla = school.getClassByName("" + j);
                if (cla == null) {
//                    throw new Exception("找不到班级名称=" + start + "的班级，请检查班类设置！");
                }
                classes.add(cla);
            }
            this.GRBClassType.add(classes);
        }
    }

    public String[] getGRBClassTypeText() {
        return GRBClassTypeText;
    }

    public int getGRBTopNum() {
        return GRBTopNum;
    }

    public void setGRBTopNum(String gRBTopNum) {
        GRBTopNum = Integer.parseInt(gRBTopNum);
    }

    public boolean isDistribution() {
        return distribution;
    }

    public String[] getDistributionName() {
        return distributionName;
    }

    public double[] getDistributionPercent() {
        return distributionPercent;
    }

    public void setDistribution(boolean distribution) {
        this.distribution = distribution;
    }

    public void setDistributionName(String[] distributionName) {
        this.distributionName = distributionName;
    }

    public void setDistributionPercent(double[] distributionPercent) {
        this.distributionPercent = distributionPercent;
    }

}
