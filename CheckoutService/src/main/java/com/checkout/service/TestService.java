package com.checkout.service;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TestService {
    public void testJava8(){
        List.of(1, 2, 3).stream().forEach(System.out::println);
        System.out.println(List.of(1, 2, 3).stream().min(Comparator.comparing(Integer::intValue)
                .thenComparing(Integer::intValue, (p, q)->{
            return p.compareTo(q);
        }).thenComparing(Comparator.nullsFirst(Comparator.reverseOrder())))
                .get().intValue());
    }
}
