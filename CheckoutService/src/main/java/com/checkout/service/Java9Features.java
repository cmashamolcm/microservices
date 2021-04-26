package com.checkout.service;

import java.util.List;

public class Java9Features {
    public static void main(String[] args) {
        List.of(1, 2, 3, 4, 5).stream().takeWhile(p->p!=3).forEach(p-> System.out.println(p));//1, 2
        //List.of(1, 2, 3, 4, 5).stream().dropWhile(p->p!=3).forEach(p-> System.out.println(p));//3, 4, 5

    }
}

interface PrivateStaticI{
    private static void print(){

    }
}
