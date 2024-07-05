package cn.nanchengyu.spider_score.test;

import java.util.Arrays;
import java.util.Comparator;

public class StudentScores {

    public static void main(String[] args) {
        // 示例输入
        int[][] studentGrades = {
            {1, 85, 90, 95},
            {2, 75, 80, 85},
            {3, 90, 95, 100},
            {4, 60, 65, 70}
        };

        // 调用方法并接收排序后的数组
        int[][] sortedStudentScores = calculateTotalScores(studentGrades);

        // 打印结果
        for (int[] student : sortedStudentScores) {
            for (int score : student) {
                System.out.print(score + " ");
            }
            System.out.println();
        }
    }

    public static int[][] calculateTotalScores(int[][] studentGrades) {
        // 创建一个数组用于存储学生的总分
        int[] totalScores = new int[studentGrades.length];

        // 计算每个学生的总分
        for (int i = 0; i < studentGrades.length; i++) {
            totalScores[i] = studentGrades[i][1] + studentGrades[i][2] + studentGrades[i][3];
        }

        // 根据总分对原始数组进行排序
        // 使用Arrays.sort()和自定义Comparator
        Arrays.sort(studentGrades, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                // 减去操作用于实现降序排序
                return Integer.compare(o2[1] + o2[2] + o2[3], o1[1] + o1[2] + o1[3]);
            }
        });

        // 增加一列用于存储总分
        int[][] result = new int[studentGrades.length][5];
        for (int i = 0; i < studentGrades.length; i++) {
            result[i][0] = studentGrades[i][0]; // 学号
            result[i][1] = studentGrades[i][1]; // 数学成绩
            result[i][2] = studentGrades[i][2]; // 物理成绩
            result[i][3] = studentGrades[i][3]; // 化学成绩
            result[i][4] = studentGrades[i][1] + studentGrades[i][2] + studentGrades[i][3]; // 总分
        }

        return result;
    }
}