package com.mfanw.scoresort.dao;

import com.mfanw.scoresort.bean.SchoolBean;
import com.mfanw.scoresort.bean.StudentBean;
import com.mfanw.scoresort.views.ScoreSortSettings;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.VerticalAlignment;
import jxl.write.Number;
import jxl.write.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class ExcelDao {

    private static Sheet sheet = null;

    private static String[] subjects = new String[7];

    private ExcelDao() {
    }

    /**
     * 加载Excel文件到Vector<Vector(String)>中<br/ >
     * 每行数据包装成一个Vector,再把所有行包装成一个Vector返回
     *
     * @param xlsPath 需要加载的Excel的文件目录
     */
    public static List<StudentBean> loadExcel(String xlsPath) throws Exception {
        ArrayList<StudentBean> xlsContents = new ArrayList<>();
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new FileInputStream(xlsPath);// 工作簿需要是标准的工作簿
            wb = Workbook.getWorkbook(is); // 得到工作薄
            sheet = wb.getSheet(0);// 只关心第一个工作表
            int sHCount = sheet.getRows(); // 得到excel的总行数
            // 读取第一行中的科目信息
            for (int i = 0; i < 6; i++) {
                subjects[i] = tStr(i + 3, 0);
            }
            subjects[6] = "总分";
            // 过滤第一行，第一行为表头
            for (int i = 1; i < sHCount; i++) {
                String tempStr = tStr(2, i).trim();
                if (tempStr.length() == 0) {
                } else {
                    double[] scores = new double[]{toDouble(3, i), toDouble(4, i), toDouble(5, i), toDouble(6, i), toDouble(7, i), toDouble(8, i)};
                    StudentBean stu = new StudentBean(tStr(0, i), tStr(1, i), tStr(2, i), scores);
                    xlsContents.add(stu);
                }
            }
            wb.close();
            is.close();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
            }
        }
        return xlsContents;
    }

    public static List<StudentBean> loadExcel(String xlsPath, int maxMingCi, double lessScore) {
        ArrayList<StudentBean> xlsContents = new ArrayList<>();
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new FileInputStream(xlsPath);// 工作簿需要是标准的工作簿
            wb = Workbook.getWorkbook(is); // 得到工作薄
            sheet = wb.getSheet(9);// 只关心第一个工作表
            int sHCount = sheet.getRows(); // 得到excel的总行数
            // 过滤第一行，第一行为表头
            for (int i = 1; i < sHCount; i++) {
                // 21是总分
                String tempZF = tStr(21, i).trim();
                // 22是总分班级内名次
                String tempZFBRank = tStr(22, i).trim();
                if (tempZFBRank.length() != 0 && Integer.parseInt(tempZFBRank) <= maxMingCi && Double.parseDouble(tempZF) > lessScore) {
                    double[] scores = new double[]{toDouble(3, i), toDouble(6, i), toDouble(9, i), toDouble(12, i), toDouble(15, i), toDouble(18, i)};
                    StudentBean stu = new StudentBean(tStr(0, i), tStr(1, i), tStr(2, i), scores);
                    xlsContents.add(stu);
                }
            }
            wb.close();
            is.close();
        } catch (Exception e) {

        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
            }
        }
        return xlsContents;
    }

    public static String tStr(int i, int j) {
        String s = "";
        try {
            s = sheet.getCell(i, j).getContents();
        } catch (Exception e) {
            s = "";
        }
        return s;
    }

    public static double toDouble(int i, int j) {
        double f = 0;
        try {
            f = Double.parseDouble(sheet.getCell(i, j).getContents());
        } catch (Exception e) {
            f = 0;
        }
        return f;
    }

    /**
     * 新建Excel文件
     *
     * @param xlsPath 保存目录
     */
    public static void saveExcel(String xlsPath, List<StudentBean> stus, SchoolBean school, ScoreSortSettings settings) {
        stus = new ArrayList<>(stus);
        WritableWorkbook wb = null;
        OutputStream os = null;
        try {
            os = new FileOutputStream(xlsPath);// 文件写出的路径
            wb = Workbook.createWorkbook(os);// 创建一个工作簿
            WritableSheet[] sheet = new WritableSheet[10];
            sheet[0] = wb.createSheet("数据总表", 0);// 创建一个工作表
            sheet[1] = wb.createSheet("综合分析", 1);// 创建一个工作表
            for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                sheet[2 + keMu] = wb.createSheet(subjects[keMu] + "分析", 2 + keMu);// 创建一个工作表
            }
            sheet[9] = wb.createSheet("名次表", 9);// 创建一个工作表

            // 公共变量
            Label lb = null;
            Number num = null;
            // 百分比格式的单元格
            WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
            DisplayFormat displayFormat = NumberFormats.PERCENT_FLOAT;
            WritableCellFormat percentFormat = new WritableCellFormat(wf, displayFormat);
            percentFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            percentFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            // 保留两位小数的单元格
            NumberFormat point2 = new NumberFormat("#.##");
            WritableCellFormat point2Format = new WritableCellFormat(point2);
            point2Format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            // 边框的单元格
            WritableCellFormat border = new WritableCellFormat(wf);
            border.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            // 向第一个工作簿中写入数据
            lb = new Label(0, 0, "学号", border);
            sheet[0].addCell(lb);
            lb = new Label(1, 0, "姓名", border);
            sheet[0].addCell(lb);
            lb = new Label(2, 0, "班级", border);
            sheet[0].addCell(lb);
            // 添加数据总表的表头信息
            for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                lb = new Label(3 + keMu * 2, 0, subjects[keMu], border);
                sheet[0].addCell(lb);
                lb = new Label(4 + keMu * 2, 0, subjects[keMu] + "等级", border);
                sheet[0].addCell(lb);
            }
            // 添加数据总表的成绩信息
            for (int i = 0; i < stus.size(); i++) {
                StudentBean stu = stus.get(i);
                lb = new Label(0, i + 1, stu.getCode(), border);
                sheet[0].addCell(lb);
                lb = new Label(1, i + 1, stu.getName(), border);
                sheet[0].addCell(lb);
                lb = new Label(2, i + 1, stu.getClassName(), border);
                sheet[0].addCell(lb);
                for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                    num = new Number(3 + keMu * 2, i + 1, stu.getScores()[keMu], border);
                    sheet[0].addCell(num);
                    lb = new Label(4 + keMu * 2, i + 1, stu.getScoresDegree()[keMu], border);
                    sheet[0].addCell(lb);
                }
            }
            // 向第二个工作簿中写入数据
            // 添加数据到Excel中
            lb = new Label(0, 0, "班级个数", border);
            sheet[1].addCell(lb);
            lb = new Label(1, 0, "学生总数", border);
            sheet[1].addCell(lb);
            lb = new Label(2, 0, "无等级人数", border);
            sheet[1].addCell(lb);
            lb = new Label(3, 0, "等级个数", border);
            sheet[1].addCell(lb);
            lb = new Label(4, 0, "每等级人数", border);
            sheet[1].addCell(lb);
            num = new Number(0, 1, school.getClasses().size(), border);
            sheet[1].addCell(num);
            num = new Number(1, 1, school.getStudents().size(), border);
            sheet[1].addCell(num);
            num = new Number(2, 1, (school.getStudents().size() - settings.getStuPerDegree() * settings.getCutNum()), border);
            sheet[1].addCell(num);
            num = new Number(3, 1, settings.getCutNum(), border);
            sheet[1].addCell(num);
            num = new Number(4, 1, (settings.getStuPerDegree()), border);
            sheet[1].addCell(num);
            // ---------------分级线
            lb = new Label(0, 3, "科目", border);
            sheet[1].addCell(lb);
            for (int i2 = 0; i2 < settings.getCutNum(); i2++) {
                if (!settings.isDistribution()) {
                    lb = new Label(i2 + 1, 3, (char) (65 + i2) + "级最低分", border);
                } else {
                    lb = new Label(i2 + 1, 3, settings.getDistributionName()[i2] + "级最低分", border);
                }
                sheet[1].addCell(lb);
            }

            for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                lb = new Label(0, 4 + keMu, subjects[keMu], border);
                sheet[1].addCell(lb);
                for (int jbsm = 0; jbsm < settings.getCutNum(); jbsm++) {
                    num = new Number(jbsm + 1, 4 + keMu, settings.getDegreeScores()[keMu][jbsm], border);
                    sheet[1].addCell(num);
                }
            }
            lb = new Label(0, 12, "科目", border);
            sheet[1].addCell(lb);
            for (int ii = 0; ii < school.getClasses().size(); ii++) {
                lb = new Label(ii + 1, 12, school.getClasses().get(ii).getClassName() + "班及格率", border);
                sheet[1].addCell(lb);
            }

            for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                lb = new Label(0, 13 + keMu, subjects[keMu], border);
                sheet[1].addCell(lb);
                for (int ii = 0; ii < school.getClasses().size(); ii++) {
                    num = new Number(ii + 1, 13 + keMu, school.getClasses().get(ii).getPassRates()[keMu], percentFormat);
                    sheet[1].addCell(num);
                }
            }
            // ---------------平均分开始---------------
            lb = new Label(0, 21, "科目", border);
            sheet[1].addCell(lb);
            for (int ii = 0; ii < school.getClasses().size(); ii++) {
                lb = new Label(ii + 1, 21, school.getClasses().get(ii).getClassName() + "班平均分", border);
                sheet[1].addCell(lb);
            }
            for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                lb = new Label(0, 22 + keMu, subjects[keMu], border);
                sheet[1].addCell(lb);
                for (int ii = 0; ii < school.getClasses().size(); ii++) {
                    num = new Number(ii + 1, 22 + keMu, school.getClasses().get(ii).getAverages()[keMu], point2Format);
                    sheet[1].addCell(num);
                }
            }
            // ---------------平均分结束---------------
            for (int sheetIndex = 2; sheetIndex <= 8; sheetIndex++) {
                lb = new Label(0, 0, "班级", border);
                sheet[sheetIndex].addCell(lb);
            }
            for (int djs = 0; djs < settings.getCutNum(); djs++) {
                for (int sheetIndex = 2; sheetIndex <= 8; sheetIndex++) {
                    if (!settings.isDistribution()) {
                        lb = new Label(djs + 1, 0, (char) (65 + djs) + "级人数", border);
                    } else {
                        lb = new Label(djs + 1, 0, settings.getDistributionName()[djs] + "级人数", border);
                    }
                    sheet[sheetIndex].addCell(lb);
                }
            }

            // 填充名次档表头信息
            for (int sheetIndex = 2; sheetIndex <= 8; sheetIndex++) {
                lb = new Label(settings.getCutNum() + 3, 0, "班级", border);
                sheet[sheetIndex].addCell(lb);
            }

            int[] mingCiDang = settings.getMingCiFenDang();
            int[][][] MCDDetailTotal = school.getMcdDetailTotal();
            for (int djs = 0; djs < mingCiDang.length; djs++) {
                for (int i = 2; i < 9; i++) {
                    lb = new Label(djs + settings.getCutNum() + 4, 0, "前" + mingCiDang[djs] + "名", border);
                    sheet[i].addCell(lb);
                    if (djs == 0) {
                        lb = new Label(mingCiDang.length + settings.getCutNum() + 4, 0, "累计其余名次", border);
                        sheet[i].addCell(lb);
                    }
                }
            }
            // 填充名次档数据信息
            for (int bj = 0; bj < school.getClasses().size(); bj++) {
                for (int i = 2; i < 9; i++) {
                    lb = new Label(settings.getCutNum() + 3, bj + 1, school.getClasses().get(bj).getClassName(), border);
                    sheet[i].addCell(lb);
                }
                // 填充名次档数据信息(起始X=settings.getCutNum() + 4, Y=1)
                for (int mcd = 0; mcd < mingCiDang.length + 1; mcd++) {
                    for (int i = 2; i < 9; i++) {
                        int mcdNum = MCDDetailTotal[bj][i - 2][mcd];
                        if (mcdNum > 0) {
                            num = new Number(settings.getCutNum() + 4 + mcd, bj + 1, mcdNum, border);
                            sheet[i].addCell(num);
                        }
                    }
                }
            }

            for (int bj = 0; bj < school.getClasses().size(); bj++) {
                // 填充等级人数
                for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                    lb = new Label(0, bj + 1, school.getClasses().get(bj).getClassName(), border);
                    sheet[2 + keMu].addCell(lb);
                    for (int jb = 0; jb < settings.getCutNum(); jb++) {// 一行数据
                        num = new Number(jb + 1, bj + 1, school.getClasses().get(bj).getLevelDegreeDetails()[keMu][jb], border);
                        sheet[2 + keMu].addCell(num);
                    }

                    lb = new Label(settings.getCutNum() + 1, 0, "合计人数", border);
                    sheet[2 + keMu].addCell(lb);// 设置-------------------------------------
                    for (int i = 0; i < school.getClasses().size(); i++) {
                        int HTotal = 0;
                        for (int j = 0; j < settings.getCutNum(); j++) {
                            HTotal += school.getClasses().get(i).getLevelDegreeDetails()[keMu][j];
                        }
                        num = new Number(settings.getCutNum() + 1, i + 1, HTotal, border);
                        sheet[2 + keMu].addCell(num);// 设置行的和-------------------------------------
                    }
                    int totalALL = 0;
                    lb = new Label(0, school.getClasses().size() + 1, "合计人数", border);
                    sheet[2 + keMu].addCell(lb);// 设置-------------------------------------
                    for (int i = 0; i < settings.getCutNum(); i++) {
                        int LTotal = 0;
                        for (int j = 0; j < school.getClasses().size(); j++) {
                            LTotal += school.getClasses().get(j).getLevelDegreeDetails()[keMu][i];
                        }
                        totalALL += LTotal;
                        num = new Number(i + 1, school.getClasses().size() + 1, LTotal, border);
                        sheet[2 + keMu].addCell(num);// 设置列的和-------------------------------------
                    }
                    num = new Number(settings.getCutNum() + 1, school.getClasses().size() + 1, totalALL, border);
                    sheet[2 + keMu].addCell(num);// 设置总和-------------------------------------
                }
            }

            lb = new Label(0, school.getClasses().size() + 4, "分数段", border);
            sheet[8].addCell(lb);
            lb = new Label(1, school.getClasses().size() + 4, "段内人数", border);
            sheet[8].addCell(lb);
            lb = new Label(2, school.getClasses().size() + 4, "累计人数", border);
            sheet[8].addCell(lb);
            int tt = 0;
            for (int i = 0; i < school.getxScoreDang().length; i++) {
                lb = new Label(0, school.getClasses().size() + 5 + i, (school.getxScoreDang()[school.getxScoreDang().length - i - 1] - school.getxScore() + 1) + "-" + school.getxScoreDang()[school.getxScoreDang().length - i - 1], border);
                sheet[8].addCell(lb);
                num = new Number(1, school.getClasses().size() + 5 + i, school.getxScoreDangStuNum()[school.getxScoreDang().length - i - 1], border);
                sheet[8].addCell(num);
                tt += school.getxScoreDangStuNum()[school.getxScoreDang().length - i - 1];
                num = new Number(2, school.getClasses().size() + 5 + i, tt, border);
                sheet[8].addCell(num);
            }
            // ---------------------------------------------------------------------------------------------------------------
            lb = new Label(0, 0, "学号", border);
            sheet[9].addCell(lb);
            lb = new Label(1, 0, "姓名", border);
            sheet[9].addCell(lb);
            lb = new Label(2, 0, "班级", border);
            sheet[9].addCell(lb);

            for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                lb = new Label(3 + keMu * 3, 0, subjects[keMu], border);
                sheet[9].addCell(lb);
                lb = new Label(4 + keMu * 3, 0, subjects[keMu] + "班名次", border);
                sheet[9].addCell(lb);
                lb = new Label(5 + keMu * 3, 0, subjects[keMu] + "校名次", border);
                sheet[9].addCell(lb);
            }

            for (int i = 0; i < stus.size(); i++) {
                StudentBean stu = stus.get(i);
                // 添加数据到Excel中
                lb = new Label(0, i + 1, stu.getCode(), border);
                sheet[9].addCell(lb);
                lb = new Label(1, i + 1, stu.getName(), border);
                sheet[9].addCell(lb);
                lb = new Label(2, i + 1, stu.getClassName(), border);
                sheet[9].addCell(lb);

                for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                    num = new Number(3 + keMu * 3, i + 1, stu.getScores()[keMu], border);
                    sheet[9].addCell(num);
                    num = new Number(4 + keMu * 3, i + 1, stu.getScoresRankClass()[keMu], border);
                    sheet[9].addCell(num);
                    num = new Number(5 + keMu * 3, i + 1, stu.getScoresRankSchool()[keMu], border);
                    sheet[9].addCell(num);
                }

            }
            // -------------------光荣榜-------------------
            List<List<Map<Integer, List<StudentBean>>>> grb = school.getGlories();
            WritableSheet[] grbSheet = new WritableSheet[grb.size()];// 创建一个工作表
            for (int classTypeIndex = 0; classTypeIndex < grb.size(); classTypeIndex++) {
                grbSheet[classTypeIndex] = wb.createSheet(settings.getGRBClassTypeText()[classTypeIndex] + "班光荣榜", sheet.length + classTypeIndex);
                int yIndex = 0;
                // 各科第一名信息
                lb = new Label(0, yIndex, "科目", border);
                grbSheet[classTypeIndex].addCell(lb);
                lb = new Label(1, yIndex, "班级", border);
                grbSheet[classTypeIndex].addCell(lb);
                lb = new Label(2, yIndex, "学号", border);
                grbSheet[classTypeIndex].addCell(lb);
                lb = new Label(3, yIndex, "姓名", border);
                grbSheet[classTypeIndex].addCell(lb);
                lb = new Label(4, yIndex, "分数", border);
                grbSheet[classTypeIndex].addCell(lb);
                yIndex++;
                for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                    List<StudentBean> mc1Stus = grb.get(classTypeIndex).get(keMu).get(1);
                    for (StudentBean mc1Stu : mc1Stus) {
                        lb = new Label(0, yIndex, subjects[keMu], border);
                        grbSheet[classTypeIndex].addCell(lb);
                        lb = new Label(1, yIndex, mc1Stu.getClassName(), border);
                        grbSheet[classTypeIndex].addCell(lb);
                        lb = new Label(2, yIndex, mc1Stu.getCode(), border);
                        grbSheet[classTypeIndex].addCell(lb);
                        lb = new Label(3, yIndex, mc1Stu.getName(), border);
                        grbSheet[classTypeIndex].addCell(lb);
                        num = new Number(4, yIndex, mc1Stu.getScores()[keMu], border);
                        grbSheet[classTypeIndex].addCell(num);
                        yIndex++;
                    }
                }
                yIndex++;
                // TOPX信息
                for (int keMu = 0; keMu < settings.getKeMuSize(); keMu++) {
                    int keMuSpace = 8;
                    // 科目表头信息
                    String grbText = subjects[keMu] + "前" + settings.getGRBTopNum() + "名";
                    lb = new Label(keMu * keMuSpace, yIndex, grbText, border);
                    grbSheet[classTypeIndex].addCell(lb);
                    lb = new Label(keMu * keMuSpace, yIndex + 1, "班类名次", border);
                    grbSheet[classTypeIndex].addCell(lb);
                    lb = new Label(keMu * keMuSpace + 1, yIndex + 1, "班级", border);
                    grbSheet[classTypeIndex].addCell(lb);
                    lb = new Label(keMu * keMuSpace + 2, yIndex + 1, "学号", border);
                    grbSheet[classTypeIndex].addCell(lb);
                    lb = new Label(keMu * keMuSpace + 3, yIndex + 1, "姓名", border);
                    grbSheet[classTypeIndex].addCell(lb);
                    lb = new Label(keMu * keMuSpace + 4, yIndex + 1, "班名次", border);
                    grbSheet[classTypeIndex].addCell(lb);
                    lb = new Label(keMu * keMuSpace + 5, yIndex + 1, "校名次", border);
                    grbSheet[classTypeIndex].addCell(lb);
                    lb = new Label(keMu * keMuSpace + 6, yIndex + 1, "分数", border);
                    grbSheet[classTypeIndex].addCell(lb);
                    Map<Integer, List<StudentBean>> grbInfo = grb.get(classTypeIndex).get(keMu);
                    Integer[] mcArray = grbInfo.keySet().toArray(new Integer[0]);
                    Arrays.sort(mcArray);
                    int yIndexTemp = yIndex;
                    // 科目内具体信息
                    for (int mc : mcArray) {
                        List<StudentBean> grbStus = grbInfo.get(mc);
                        for (int sIndex = 0; sIndex < grbStus.size(); sIndex++) {
                            num = new Number(keMu * keMuSpace, yIndexTemp + 2, mc, border);
                            grbSheet[classTypeIndex].addCell(num);
                            lb = new Label(keMu * keMuSpace + 1, yIndexTemp + 2, grbStus.get(sIndex).getClassName(), border);
                            grbSheet[classTypeIndex].addCell(lb);
                            lb = new Label(keMu * keMuSpace + 2, yIndexTemp + 2, grbStus.get(sIndex).getCode(), border);
                            grbSheet[classTypeIndex].addCell(lb);
                            lb = new Label(keMu * keMuSpace + 3, yIndexTemp + 2, grbStus.get(sIndex).getName(), border);
                            grbSheet[classTypeIndex].addCell(lb);
                            num = new Number(keMu * keMuSpace + 4, yIndexTemp + 2, grbStus.get(sIndex).getScoresRankClass()[keMu], border);
                            grbSheet[classTypeIndex].addCell(num);
                            num = new Number(keMu * keMuSpace + 5, yIndexTemp + 2, grbStus.get(sIndex).getScoresRankSchool()[keMu], border);
                            grbSheet[classTypeIndex].addCell(num);
                            num = new Number(keMu * keMuSpace + 6, yIndexTemp + 2, grbStus.get(sIndex).getScores()[keMu], border);
                            grbSheet[classTypeIndex].addCell(num);
                            yIndexTemp++;
                        }
                    }
                }
            }
            wb.write();
            wb.close();
        } catch (Exception e) {
//            throw e;
        } finally {
            try {
                os.close();
            } catch (Exception e) {
            }
        }
    }
}
