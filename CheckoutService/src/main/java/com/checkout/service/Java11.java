package com.checkout.service;

import java.util.Optional;

public class Java11 {
    public static void main(String[] args) {
        String str = new String("Asha\nMo\rl");
        System.out.println(str);
        str.lines().forEach(System.out::println);
        System.out.println(str.isEmpty());//Blank());
        Optional.of("abc").isEmpty();//previously only isPresent() was there.
        //File operations with strig are easier with Files.writeString(path, data).
    }
}
