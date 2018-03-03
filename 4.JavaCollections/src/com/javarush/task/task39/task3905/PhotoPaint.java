package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int r, int c, Color desiredColor) {
        if (!isValid(image, c, r)) return false;
        if (image[c][r] == desiredColor) return false;
//        print(image);
        paint(image, c, r, image[c][r], desiredColor);
//        print(image);
        return true;
    }

    private void paint(Color[][] image, int c, int r, Color base, Color desiredColor) {
        if (!isValid(image, c, r)) return;
        if (image[c][r] != base) return;

        image[c][r] = desiredColor;
        paint(image, c - 1, r, base, desiredColor);
        paint(image, c + 1, r, base, desiredColor);
        paint(image, c, r - 1, base, desiredColor);
        paint(image, c, r + 1, base, desiredColor);

        paint(image, c - 1, r - 1, base, desiredColor);
        paint(image, c - 1, r + 1, base, desiredColor);
        paint(image, c + 1, r - 1, base, desiredColor);
        paint(image, c + 1, r + 1, base, desiredColor);
    }

    private boolean isValid(Color[][] image, int r, int c) {
        return c >= 0 && r >= 0 && image.length > c && image[0].length > r;
    }

    private void print(Color[][] image) {
        for (Color[] anImage : image) {
            for (int x = 0; x < image[0].length; x++)
                System.out.print(anImage[x].toString().charAt(0));
            System.out.println();
        }
        System.out.println();
    }
}
