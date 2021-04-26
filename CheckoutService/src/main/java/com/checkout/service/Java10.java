package com.checkout.service;

import java.util.List;
import java.util.stream.Collectors;

public class Java10 {
    public static void main(String[] args) {
        var v = "12345";
        var vInt = 100;// if tries v = 100, compile time error as its a string already.
        System.out.println(v);
        List<Integer> l = List.of(1, 2, 3).stream().collect(Collectors.toList());
        l.add(10);
        System.out.println(l);
        List.of(1, 2, 3).stream().collect(Collectors.toUnmodifiableList()).add(10);
    }
}
