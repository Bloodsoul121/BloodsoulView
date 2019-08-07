package com.example.administrator.bloodsoulview.collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionTest {

    public static void main(String[] args) {

        String s = "";

        List<String> list = new ArrayList<>(3);

        for (int i = 0; i < 10; i++) {
            addText(list, s + i);
        }

    }

    private static void addText(List<String> list, String s) {
        if (list.size() < 3) {
            list.add(0, s);
        } else {
            list.remove(list.size() - 1);
            list.add(0, s);
        }

        System.out.println(list.toString());
    }

}
