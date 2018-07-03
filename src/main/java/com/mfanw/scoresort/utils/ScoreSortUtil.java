package com.mfanw.scoresort.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.mfanw.scoresort.bean.StudentBean;

public class ScoreSortUtil {

    public static final int RANK_CLASS = 1;
    public static final int RANK_SCHOOL = 2;

    /**
     * 根据科目和科目分数区间，过滤输入的学生<br />
     * minScore >= score >= maxScore
     * 
     * @param students 输入的学生
     * @param keMu 科目
     * @param minScore 科目最低分数
     * @param maxScore 科目最高分数
     * @return 科目分数在分数区间的学生
     */
    public static ArrayList<StudentBean> scoreBetween(ArrayList<StudentBean> students, int keMu, double minScore, double maxScore) {
        ArrayList<StudentBean> filteredStudents = new ArrayList<StudentBean>();
        for (StudentBean student : students) {
            if (student.getScores()[keMu] >= minScore && student.getScores()[keMu] <= maxScore) {
                filteredStudents.add(student);
            }
        }
        return filteredStudents;
    }

    /**
     * 根据科目成绩对学生排序
     * 
     * @param students 需要排序的学生
     * @param keMu 科目
     * @return 科目成绩有序的学生
     */
    public static ArrayList<StudentBean> scoreSort(ArrayList<StudentBean> students, final int keMu) {
        StudentBean[] stus = students.toArray(new StudentBean[0]);
        Arrays.sort(stus, new Comparator<StudentBean>() {
            public int compare(StudentBean s1, StudentBean s2) {
                // 为了自动适应为分数倒序排列，所以故意把大小的判断调节为相反
                if (s1.getScores()[keMu] > s2.getScores()[keMu]) {
                    return -1;
                } else if (s1.getScores()[keMu] == s2.getScores()[keMu]) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
        ArrayList<StudentBean> rets = new ArrayList<StudentBean>();
        for (StudentBean stu : stus) {
            rets.add(stu);
        }
        return rets;
    }

    /**
     * 为学生的全部学科排名
     * 
     * @param students 学生
     * @param markType 排名类别RANK_CLASS表示班级排名，RANK_SCHOOL表示校排名
     */
    public static void scoreSortRankMark(ArrayList<StudentBean> students, int markType) {
        for (int keMu = 0; keMu < students.get(0).getScores().length; keMu++) {
            ScoreSortUtil.scoreSortRankMark(students, keMu, markType);
        }
    }

    /**
     * 为学生排名
     * 
     * @param students 学生
     * @param keMu 科目
     * @param markType 排名类别RANK_CLASS表示班级排名，RANK_SCHOOL表示校排名
     */
    public static void scoreSortRankMark(ArrayList<StudentBean> students, int keMu, int markType) {
        ArrayList<StudentBean> stus = ScoreSortUtil.scoreSort(students, keMu);
        int realStuNum = 0;
        int mingCi = 0;
        double lastScore = -1;
        while (realStuNum < stus.size()) {
            if (lastScore != stus.get(realStuNum).getScores()[keMu]) {
                lastScore = stus.get(realStuNum).getScores()[keMu];
                mingCi = realStuNum + 1;
            }
            if (markType == ScoreSortUtil.RANK_CLASS) {
                stus.get(realStuNum).setScoreRankClass(keMu, mingCi);
            } else if (markType == ScoreSortUtil.RANK_SCHOOL) {
                stus.get(realStuNum).setScoreRankSchool(keMu, mingCi);
            }
            realStuNum++;
        }
    }

    /**
     * 获取科目成绩前X名的学生
     * 
     * @param students 学生列表
     * @param keMu 科目
     * @param topX 前X名
     * @return 返回学生中科目成绩前topX名(名次从1开始)
     */
    public static Map<Integer, ArrayList<StudentBean>> scoreTopX(ArrayList<StudentBean> students, int keMu, int topX) {
        ArrayList<StudentBean> stus = ScoreSortUtil.scoreSort(students, keMu);
        Map<Integer, ArrayList<StudentBean>> rets = new HashMap<Integer, ArrayList<StudentBean>>();
        int realStuNum = 0;
        int mingCi = 0;
        double lastScore = -1;
        while (mingCi <= topX) {
            realStuNum++;
            if (lastScore != stus.get(realStuNum - 1).getScores()[keMu]) {
                lastScore = stus.get(realStuNum - 1).getScores()[keMu];
                mingCi = realStuNum;
            }
            if (mingCi > topX) {
                break;
            }
            ArrayList<StudentBean> temp = rets.get(mingCi);
            if (temp == null) {
                temp = new ArrayList<StudentBean>();
            }
            temp.add(stus.get(realStuNum - 1));
            rets.put(mingCi, temp);
        }
        return rets;
    }

    /**
     * 将数组转换为值的展现形式
     * 
     * @param scores
     * @return
     */
    public static String arrayToString(double[] scores) {
        StringBuilder message = new StringBuilder("[");
        for (double score : scores) {
            message.append(score + ", ");
        }
        message.delete(message.length() - 2, message.length());
        message.append("]");
        return message.toString();
    }
}
