/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfanw.scoresort.bean;

import com.mfanw.scoresort.utils.ScoreSortUtil;
import com.mfanw.scoresort.views.ScoreSortSettings;

import java.util.*;

/**
 * <b>加载XLS文件即处理：</b><br />
 * 班排名（全科目），校排名（全科目）<br />
 * <b>最终处理：</b><br />
 * 名次分档（总分）、分数分档（总分）、等级分档（全科目）<br />
 * <b>班级自己处理：</b><br />
 * 及格率（班级全科目）、平均分（班级全科目）
 *
 * @author 孟伟
 */
public class SchoolBean {

    /**
     * 全部学生
     */
    private List<StudentBean> students;

    private List<ClassBean> classes = new ArrayList<>();

    /**
     * 总分最高分
     */
    private double[] scoresHigh;
    /**
     * 总分最低分
     */
    private double[] scoresLow;

    /**
     * X分一档：X具体值
     */
    private int XScore = 5;

    /**
     * X分一档：分割线列表,记录的分档的最大分，根据XScore可以计算出每一档的最小分
     */
    private long[] XScoreDang;

    /**
     * X分一档：分割线列表内的学生个数
     */
    private int[] XScoreDangStuNum;

    /**
     * 每个班学生数目
     */
    private int[] numPerBanJi;

    /**
     * 名次档详情<br />
     * MCDDetail[班级][科目][名次档索引]
     */
    private int[][][] MCDDetail;

    /**
     * 名次档累计详情<br />
     * MCDDetail[班级][科目][名次档索引]
     */
    private int[][][] MCDDetailTotal;

    /**
     * 光荣榜[班类][科目][名次][学生]
     */
    private List<List<Map<Integer, List<StudentBean>>>> GRB = new ArrayList<>();

    private ScoreSortSettings settings;

    /**
     * 构造函数
     */
    public SchoolBean(ScoreSortSettings settings, List<StudentBean> students) {
        this.settings = settings;
        this.students = students;
        // 添加总分作为一个特殊科目
        ScoreSortUtil.scoreSortRankMark(students, ScoreSortUtil.RANK_SCHOOL);
        this.initClassDetails();
        this.initScoresHighLow();
    }

    public void mainHandler() {
        this.handleDistribution();
        // X分一档
        this.handleXScoreDegree(this.scoresHigh[6], this.scoresLow[6]);
        // 分数等级划分
        this.handleScoreLevel();
        // 名次档
        this.handleMingCiDang();
        // 光荣榜
        this.handleGRB();
    }

    private void handleDistribution() {
        if (!settings.isDistribution()) {
            return;
        }
        for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
            final int keMuIndex = keMu;
            Collections.sort(students, (StudentBean o1, StudentBean o2) -> {
                if (o1 == null || o1.getScores() == null || o1.getScores().length < keMuIndex) {
                    return -1;
                }
                if (o2 == null || o2.getScores() == null || o2.getScores().length < keMuIndex) {
                    return 1;
                }
                return new Double(o1.getScores()[keMuIndex]).compareTo(o2.getScores()[keMuIndex]);
            });
            Collections.reverse(students);
            double[] degreeHScore = new double[settings.getDistributionPercent().length];
            double[] degreeLScore = new double[settings.getDistributionPercent().length];
            double totalPercent = 0;
            int lastIndex = 0;
            double[] standarHScore = new double[]{100, 90, 80, 70, 60, 50, 40, 30};
            double[] standarLScore = new double[]{91, 81, 71, 61, 51, 41, 31, 21};
            for (int i = 0; i < settings.getDistributionPercent().length; i++) {
                // 此处有分数重叠问题
                int studentIndex = (int) Math.round(students.size() * totalPercent);
                degreeHScore[i] = students.get(studentIndex).getScores()[keMuIndex];
                if (i > 0) {
                    degreeHScore[i] = degreeLScore[i - 1] - 1;
                }
                if (degreeHScore[i] < 0) {
                    degreeHScore[i] = 0;
                }
                totalPercent += settings.getDistributionPercent()[i];
                studentIndex = (int) Math.round(students.size() * totalPercent);
                if (studentIndex >= students.size()) {
                    studentIndex = students.size() - 1;
                }
                degreeLScore[i] = students.get(studentIndex).getScores()[keMuIndex];
                for (; lastIndex < students.size(); lastIndex++) {
                    double c = students.get(lastIndex).getScores()[keMuIndex];
                    if (c < degreeLScore[i]) {
                        break;
                    }
                    double sScoreUp = degreeHScore[i] * standarLScore[i] - c * standarLScore[i] + c * standarHScore[i] - degreeLScore[i] * standarHScore[i];
                    double sScoreDown = degreeHScore[i] - degreeLScore[i];
                    double sScore = Math.round(sScoreUp / sScoreDown);
                    if (sScoreUp != 0 && sScoreDown != 0) {
                        students.get(lastIndex).setScoreByIndex(keMuIndex, sScore);
                    }
                }
            }
            if (keMu == 3) {
                for (int i = 0; i < degreeHScore.length; i++) {
                    System.out.println(keMu + ":" + degreeHScore[i] + "~" + degreeLScore[i]);
                }
            }
        }
        for (int j = 0; j < students.size(); j++) {
            double[] scores = students.get(j).getScores();
            double total = 0;
            for (int i = 0; i < 6; i++) {
                total += scores[i];
            }
            students.get(j).setScoreByIndex(6, total);
        }
    }

    private void handleGRB() {
        this.GRB.clear();
        if (settings.getGRBClassType() == null || settings.getGRBClassType().size() == 0) {
            return;
        }
        for (int classTypeIndex = 0; classTypeIndex < settings.getGRBClassType().size(); classTypeIndex++) {
            List<ClassBean> clas = settings.getGRBClassType().get(classTypeIndex);
            List<StudentBean> stusOfClas = new ArrayList<>();
            for (ClassBean cla : clas) {
                stusOfClas.addAll(cla.getStudents());
            }
            List<Map<Integer, List<StudentBean>>> classType = new ArrayList<>();
            for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                classType.add(ScoreSortUtil.scoreTopX(stusOfClas, keMu, settings.getGRBTopNum()));
            }
            this.GRB.add(classType);
        }
    }

    /**
     * 分数级别划分
     */
    private void handleScoreLevel() {
        if (!settings.isDistribution()) {
            for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                settings.setDegreeScore(keMu, scoreCut(keMu, settings.getCutNum()));
            }
            for (ClassBean cla : classes) {
                cla.initLevelDegreeDetails(settings);
                for (StudentBean stu : cla.getStudents()) {
                    for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                        int temp = scoreLevel(settings.getDegreeScores()[keMu], stu.getScores()[keMu]);
                        String stuDegree = null;
                        if (temp != -1) {
                            stuDegree = "" + (char) (65 + temp);
                        } else {
                            stuDegree = "无等级";
                        }
                        stu.setScoreDegree(keMu, stuDegree);
                        cla.addLevelDegreeDetails(keMu, temp);
                    }
                }
            }
        } else {
            settings.setCutNum(settings.getDistributionPercent().length);
            for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                final int keMuIndex = keMu;
                Collections.sort(students, (StudentBean o1, StudentBean o2) -> {
                    if (o1 == null || o1.getScores() == null || o1.getScores().length < keMuIndex) {
                        return -1;
                    }
                    if (o2 == null || o2.getScores() == null || o2.getScores().length < keMuIndex) {
                        return 1;
                    }
                    return new Double(o1.getScores()[keMuIndex]).compareTo(o2.getScores()[keMuIndex]);
                });
                Collections.reverse(students);
                double[] degreeLScore = new double[settings.getDistributionPercent().length];
                double totalPercent = 0;
                for (int i = 0; i < settings.getDistributionPercent().length; i++) {
                    totalPercent += settings.getDistributionPercent()[i];
                    int studentIndex = (int) Math.round(students.size() * totalPercent);
                    if (studentIndex >= students.size()) {
                        studentIndex = students.size() - 1;
                    }
                    degreeLScore[i] = students.get(studentIndex).getScores()[keMuIndex];
                }
                settings.setDegreeScore(keMu, degreeLScore);
            }
            for (ClassBean cla : classes) {
                cla.initLevelDegreeDetails(settings);
                for (StudentBean stu : cla.getStudents()) {
                    for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                        int temp = scoreLevel(settings.getDegreeScores()[keMu], stu.getScores()[keMu]);
                        String stuDegree = null;
                        if (temp != -1) {
                            stuDegree = settings.getDistributionName()[temp];
                        } else {
                            stuDegree = "无等级";
                        }
                        stu.setScoreDegree(keMu, stuDegree);
                        cla.addLevelDegreeDetails(keMu, temp);
                    }
                }
            }
        }
    }

    private int scoreLevel(double[] scoreCut, double now) {
        int ret = -1;
        for (int i = 0; i < scoreCut.length; i++) {
            if (now >= scoreCut[i]) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    /**
     * 按照名次档给各科目统计
     */
    private void handleMingCiDang() {
        // 暂时就是只有六个科目
        if (settings.getMingCiFenDang().length <= 0) {
            return;
        }
        this.MCDDetail = new int[this.classes.size()][this.settings.getKeMuSize()][settings.getMingCiFenDang().length + 1];
        // 默认设置各档都是0
        for (int i = 0; i < this.classes.size(); i++) {
            for (int j = 0; j < this.settings.getKeMuSize(); j++) {
                for (int k = 0; k < settings.getMingCiFenDang().length + 1; k++) {
                    this.MCDDetail[i][j][k] = 0;
                }
            }
        }
        // 统计各科名次档
        for (StudentBean student : students) {
            int banJiIndex = this.findBJIndex(student.getClassName());
            if (banJiIndex < 0) {
                continue;
            }
            for (int i = 0; i < this.settings.getKeMuSize(); i++) {
                int tempMingCiDang = judgeMingCiDang(student.getScoresRankSchool()[i]);
                if (tempMingCiDang >= 0) {
                    this.MCDDetail[banJiIndex][i][tempMingCiDang]++;
                }
            }
        }
        handleMCDDetailTotal(MCDDetail);
    }

    /**
     * 根据名次档详情计算出累计情况
     */
    private void handleMCDDetailTotal(final int[][][] mcdDetail) {
        this.MCDDetailTotal = new int[this.classes.size()][this.settings.getKeMuSize()][settings.getMingCiFenDang().length + 1];
        // 默认设置各档都是0
        for (int i = 0; i < this.classes.size(); i++) {
            for (int j = 0; j < this.settings.getKeMuSize(); j++) {
                int temp = 0;
                for (int k = 0; k < settings.getMingCiFenDang().length + 1; k++) {
                    temp += mcdDetail[i][j][k];
                    this.MCDDetailTotal[i][j][k] = temp;
                }
            }
        }
    }

    private int judgeMingCiDang(int mingCi) {
        try {
            if (mingCi <= settings.getMingCiFenDang()[0]) {
                return 0;
            }
            for (int i = 0; i < settings.getMingCiFenDang().length; i++) {
                if (mingCi > settings.getMingCiFenDang()[i] && mingCi <= settings.getMingCiFenDang()[i + 1]) {
                    return i + 1;
                }
            }
        } catch (Exception e) {
            // 捕捉到异常信息，肯定是名次档设置问题，不予处理
        }
        // 其余超出统计范围的情况
        return settings.getMingCiFenDang().length;
    }

    public List<StudentBean> getStudents() {
        return students;
    }

    public void setStudents(List<StudentBean> students) {
        this.students = students;
    }

    /**
     * 获取学校的全部班级，班级是有序的，名称从小到大
     */
    public List<ClassBean> getClasses() {
        return classes;
    }

    public ClassBean getClassByName(String className) {
        for (ClassBean cla : classes) {
            if (cla.getClassName().equals(className)) {
                return cla;
            }
        }
        return null;
    }

    /**
     * MCDDetail[班级][科目][名次档索引]
     */
    public int[][][] getMCDDetail() {
        return MCDDetail;
    }

    public void setMCDDetail(int[][][] mCDDetail) {
        MCDDetail = mCDDetail;
    }

    /**
     * getMCDDetailTotal[班级][科目][名次档索引]
     */
    public int[][][] getMCDDetailTotal() {
        return MCDDetailTotal;
    }

    public void setMCDDetailTotal(int[][][] mCDDetailTotal) {
        MCDDetailTotal = mCDDetailTotal;
    }

    /**
     * 初始化班级信息
     */
    private void initClassDetails() {
        // 班级内学生列表，存储所有的<班级名称, 班级内学生>
        HashMap<String, ArrayList<StudentBean>> classStudents = new HashMap<String, ArrayList<StudentBean>>();
        for (int i = 0; i < students.size(); i++) {
            ArrayList<StudentBean> classStudent = classStudents.get(students.get(i).getClassName());
            if (classStudent == null) {
                classStudent = new ArrayList<>();
            }
            classStudent.add(students.get(i));
            classStudents.put(students.get(i).getClassName(), classStudent);
        }
        String[] classNames = classStudents.keySet().toArray(new String[0]);
        ArrayList<String> classNameList = new ArrayList<>(Arrays.asList(classNames));
        Collections.sort(classNameList, (o1, o2) -> Integer.parseInt(o1) - Integer.parseInt(o2));
        for (int i = 0; i < classNameList.size(); i++) {
            this.classes.add(new ClassBean(settings, i, classNameList.get(i), classStudents.get(classNameList.get(i))));
        }
    }

    /**
     * 查看班级名称是否在班级列表中,并且返回那个班级的index
     *
     * @param className 班级名称
     * @return -1表示不在，其余表示在
     */
    public int findBJIndex(String className) {
        for (int i = 0; i < this.classes.size(); i++) {
            if (className.equals(this.classes.get(i).getClassName())) {
                return this.classes.get(i).getClassIndex();
            }
        }
        return -1;
    }

    /**
     * 寻找全部学生各科的最大值和最小值<br />
     * 需要初始化班级之后才可以正确运行
     */
    private void initScoresHighLow() {
        this.scoresHigh = new double[this.settings.getKeMuSize()];
        this.scoresLow = new double[this.settings.getKeMuSize()];
        for (int keMu = 0; keMu < this.settings.getKeMuSize(); keMu++) {
            for (int cla = 0; cla < this.getClasses().size(); cla++) {
                double tempHigh = this.getClasses().get(cla).getScoresHigh()[keMu];
                if (this.scoresHigh[keMu] < tempHigh) {
                    this.scoresHigh[keMu] = tempHigh;
                }
                double tempLow = this.getClasses().get(cla).getScoresLow()[keMu];
                if (this.scoresLow[keMu] > tempLow) {
                    this.scoresHigh[keMu] = tempLow;
                }
            }
        }
    }

    /**
     * 根据最高分和最低分以及偏差获取分档表，存储在XScoreDang中
     *
     * @param High
     * @param Low
     */
    public void handleXScoreDegree(double High, double Low) {
        // 正常的偏差是0，偏差的计算方法是 Low+x-1-第一个存储值 x表示x分一档
        int scoreDegreePC = settings.getScoreDegreePC();
        long ZFHigh = Math.round(getScoresHigh()[this.settings.getKeMuSize() - 1]);
        long ZFLow = Math.round(getScoresLow()[this.settings.getKeMuSize() - 1]);
        // 根据最高分和最低分以及偏差获取分档表，存储在XScoreDang中
        int fdNum = (int) ((ZFHigh - ZFLow + scoreDegreePC + settings.getCutNum()) / settings.getCutNum());
        // 创建分档列表
        XScoreDang = new long[fdNum];
        int firstScoreDang = (int) ((ZFHigh - ZFLow) % settings.getCutNum());
        // 根据分数存储分档列表
        for (int i = 0; i < XScoreDang.length; i++) {
            XScoreDang[i] = firstScoreDang + scoreDegreePC + XScore * i;
        }
        // 创建统计列表
        XScoreDangStuNum = new int[fdNum];
        // 初始化统计列表
        for (int i = 0; i < XScoreDangStuNum.length; i++) {
            XScoreDangStuNum[i] = 0;
        }
        // 开始统计,填充统计列表
        double tZF = 0;
        for (int i = 0; i < students.size(); i++) {
            tZF = students.get(i).getScores()[this.settings.getKeMuSize() - 1];
            // 从小到大循环分档，直到找到合适的分档（从小到大分档分数线逐渐提高）
            for (int mm = 0; mm < XScoreDang.length; mm++) {
                if (tZF < XScoreDang[mm]) {
                    XScoreDangStuNum[mm]++;
                    break;
                }
            }
        }
    }

    /**
     * 返回的是 划分各个等级的分数线
     *
     * @param cutNum 分等级的个数
     * @return
     */
    public double[] scoreCut(int keMu, int cutNum) {
        double[] ret = new double[cutNum];
        int containNum = settings.getStuPerDegree();
        double[] score = new double[this.students.size()];
        for (int i = 0; i < this.students.size(); i++) {
            score[i] = students.get(i).getScores()[keMu];
        }
        Arrays.sort(score);
        double temp;
        // 这个for循环就是为了将score变为是降序排列
        for (int i = 0; i < score.length / 2; i++) {
            temp = score[i];
            score[i] = score[score.length - 1 - i];
            score[score.length - 1 - i] = temp;
        }
        // 这个for循环是为了找到划分的分数线
        for (int i = 0; i < cutNum; i++) {
            ret[i] = score[containNum * (i + 1) - 1];
        }
        return ret;
    }

    public double[] getScoresHigh() {
        return scoresHigh;
    }

    public void setScoresHigh(double[] scoresHigh) {
        this.scoresHigh = scoresHigh;
    }

    public double[] getScoresLow() {
        return scoresLow;
    }

    public void setScoresLow(double[] scoresLow) {
        this.scoresLow = scoresLow;
    }

    public int getXScore() {
        return XScore;
    }

    public void setXScore(int xScore) {
        XScore = xScore;
    }

    public long[] getXScoreDang() {
        return XScoreDang;
    }

    public int[] getXScoreDangStuNum() {
        return XScoreDangStuNum;
    }

    public void setXScoreDangStuNum(int[] xScoreDangStuNum) {
        XScoreDangStuNum = xScoreDangStuNum;
    }

    public int[] getNumPerBanJi() {
        return numPerBanJi;
    }

    public void setNumPerBanJi(int[] numPerBanJi) {
        this.numPerBanJi = numPerBanJi;
    }

    public List<List<Map<Integer, List<StudentBean>>>> getGRB() {
        return GRB;
    }

}
