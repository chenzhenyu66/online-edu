package com.czy1344.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 2020/8/3 14:40
 *
 * @author czy1344
 * 说明：
 */
public class Demo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        String s = list.toString();
        String s2 = s.substring(1, s.length() - 1);
        System.out.println(s2);
    }
}
