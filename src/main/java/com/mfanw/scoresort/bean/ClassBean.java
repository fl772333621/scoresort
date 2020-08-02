package com.mfanw.scoresort.bean;

import com.mfanw.scoresort.utils.ScoreSortUtil;
import com.mfanw.scoresort.views.ScoreSortSettings;

import java.util.List;

public class ClassBean {

    /**
     * 班级序号
     */
    private final int classIndex;

    /**
     * 班级名称
     */
    private final String className;

    /**
     * 班级内全部学生
     */
    private final List<StudentBean> students;

    /**
     * 每个班每个等级的计数器，degreeDetails[科目][等级]
     */
    private int[][] levelDegreeDetails;

    /**
     * 各班各科及格率[科目]
     */
    private double[] passRates;

    /**
     * 各班各科平均分[科目]
     */
    private double[] averages;

    /**
     * 各科最高分[科目]
     */
    private double[] scoresHigh;
    /**
     * 各科最低分[科目]
     */
    private double[] scoresLow;

    private final ScoreSortSettings settings;

    ClassBean(ScoreSortSettings settings, int classIndex, String className, List<StudentBean> students) {
        this.settings = settings;
        this.classIndex = classIndex;
        this.className = className;
        this.students = students;
        initScoresHighLow();
        initPassRate();
        initAverage();
        ScoreSortUtil.scoreSortRankMark(students, ScoreSortUtil.RANK_CLASS);
    }

    /**
     * 初始化及格率
     */
    private void initPassRate() {
        this.passRates = new double[this.settings.getKeMuSize()];
        for (int keMu = 0; keMu < this.settings.getKeMuSize(); keMu++) {
            double jgNum = ScoreSortUtil.scoreBetween(students, keMu, settings.getJGLow()[keMu], Double.MAX_VALUE).size();
            this.passRates[keMu] = jgNum / students.size();
        }
    }

    /**
     * 初始化平均分
     */
    private void initAverage() {
        this.averages = new double[this.settings.getKeMuSize()];
        for (StudentBean student : students) {
            for (int keMu = 0; keMu < this.settings.getKeMuSize(); keMu++) {
                this.averages[keMu] += student.getScores()[keMu];
            }
        }
        for (int keMu = 0; keMu < this.settings.getKeMuSize(); keMu++) {
            this.averages[keMu] = this.averages[keMu] / students.size();
        }
    }

    /**
     * 求班级内学生各科的最大值和最小值
     */
    private void initScoresHighLow() {
        this.scoresHigh = new double[this.settings.getKeMuSize()];
        this.scoresLow = new double[this.settings.getKeMuSize()];
        for (int keMu = 0; keMu < this.settings.getKeMuSize(); keMu++) {
            this.scoresHigh[keMu] = students.get(0).getScores()[keMu];
            this.scoresLow[keMu] = students.get(0).getScores()[keMu];
            for (StudentBean student : students) {
                if (student.getScores()[keMu] > this.scoresHigh[keMu]) {
                    this.scoresHigh[keMu] = student.getScores()[keMu];
                }
                if (student.getScores()[keMu] < this.scoresLow[keMu]) {
                    this.scoresLow[keMu] = student.getScores()[keMu];
                }
            }
        }
    }

    public int getClassIndex() {
        return classIndex;
    }

    public String getClassName() {
        return className;
    }

    public List<StudentBean> getStudents() {
        return students;
    }

    public int[][] getLevelDegreeDetails() {
        return levelDegreeDetails;
    }

    /**
     * 指定科目和级别累计数目增加一<br />
     * 操作前必须保证已经执行了initLevelDegreeDetails对班级的等级信息初始化<br />
     *
     * @param keMu  指定科目
     * @param level 指定级别，如果level==-1表示是无等级的学生，也需要计数
     */
    public void addLevelDegreeDetails(int keMu, int level) {
        if (level == -1) {
            this.levelDegreeDetails[keMu][settings.getCutNum()]++;
        } else {
            this.levelDegreeDetails[keMu][level]++;
        }
    }

    public void initLevelDegreeDetails(ScoreSortSettings settings) {
        this.levelDegreeDetails = new int[settings.getKeMuSize()][settings.getCutNum() + 1];
    }

    public double[] getPassRates() {
        return passRates;
    }

    public double[] getAverages() {
        return averages;
    }

    public double[] getScoresHigh() {
        return scoresHigh;
    }

    public double[] getScoresLow() {
        return scoresLow;
    }

}
