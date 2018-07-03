package com.mfanw.scoresort.bean;

import com.mfanw.scoresort.utils.ScoreSortUtil;

public class StudentBean {

    private String className;
    private String code;
    private String name;

    /**
     * 各科成绩
     */
    private double[] scores;

    /**
     * 各科等级
     */
    private String[] scoresDegree;

    /**
     * 各科的班级排名
     */
    private int[] scoresRankClass;

    /**
     * 各科的学校排名
     */
    private int[] scoresRankSchool;

    public StudentBean(String code, String name, String className, double[] scores) {
        this.code = code;
        this.name = name;
        this.className = className;
        this.scores = new double[scores.length + 1];
        double scoresTotal = 0;
        for (int i = 0; i < scores.length; i++) {
            scoresTotal += scores[i];
            this.scores[i] = scores[i];
        }
        // 计算总分
        this.scores[scores.length] = scoresTotal;
        // 根据分数的个数初始化排名
        this.scoresRankSchool = new int[this.scores.length];
        this.scoresRankClass = new int[this.scores.length];
        // 根据分数的个数初始化各科等级
        this.scoresDegree = new String[this.scores.length];
    }

    public double[] getScores() {
        return scores;
    }
    
    public void setScoreByIndex(int index, double score){
        this.scores[index] = score;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String[] getScoresDegree() {
        return scoresDegree;
    }

    public int[] getScoresRankClass() {
        return scoresRankClass;
    }

    public int[] getScoresRankSchool() {
        return scoresRankSchool;
    }

    /**
     * 根据科目设置等级
     * 
     * @param keMu 科目
     * @param scoreDegree 等级
     */
    public void setScoreDegree(int keMu, String scoreDegree) {
        this.scoresDegree[keMu] = scoreDegree;
    }

    /**
     * 根据科目设置班级排名
     * 
     * @param keMu 科目
     * @param scoreRankClass 班级排名
     */
    public void setScoreRankClass(int keMu, int scoreRankClass) {
        this.scoresRankClass[keMu] = scoreRankClass;
    }

    /**
     * 根据科目设置校排名
     * 
     * @param keMu 科目
     * @param scoreRankSchool 校排名
     */
    public void setScoreRankSchool(int keMu, int scoreRankSchool) {
        this.scoresRankSchool[keMu] = scoreRankSchool;
    }

    @Override
    public String toString() {
        String message = "班级=" + this.getClassName() + ", 学号=" + this.getCode();
        message += ", 姓名=" + this.getName() + ", 成绩=" + ScoreSortUtil.arrayToString(this.getScores());
        return message;
    }

}
