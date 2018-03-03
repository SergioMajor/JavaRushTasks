package com.javarush.task.task39.task3912;

/* 
Максимальная площадь
*/

public class Solution {
    public static void main(String[] args) {

    }

    public static int maxSquare(int[][] matrix) {
        int max = 0;
        int start = -1;

        for (int j = 0; j < matrix.length; j++) {
            for (int i = 0; i < matrix[0].length; i++) {
                if (matrix[j][i] == 1 && start == -1) start = i;
                if (matrix[j][i] == 0) start = -1;

                if (isSquare(matrix, j, start, i, i - start + 1)) {
                    if (i - start + 1 > max)
                        max = i - start + 1;
                }
            }
            start = -1;
        }
        return max * max;
    }

    private static boolean isSquare(int[][] matrix, int line, final int start, final int end, int height) {
        try {
            if (start > end) return false;
            if (height > 0) {
                for (int i = start; i <= end; i++) {
                    if (matrix[line][i] != 1) return false;
                }
                return isSquare(matrix, line + 1, start, end, height - 1);
            } else return true;
        } catch (IndexOutOfBoundsException ignore) {
            return false;
        }
    }
}
