package ru.netology.graphics.image;

public class TextColor implements TextColorSchema {
    public char[] symbol = new char[]{'#', '$', '@', '%', '*', '+', '-', '`'};

    @Override
    public char convert(int color) {
        return symbol[color / 32];
    }
}
