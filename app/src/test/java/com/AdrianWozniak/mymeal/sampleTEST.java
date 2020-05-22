package com.AdrianWozniak.mymeal;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class sampleTEST {


    @Test
    public void test(){

        String a ="abs123";
        String b ="asdbvd";

        boolean ma = Pattern.matches("^[a-zA-Z]*$", a);
        boolean mb = Pattern.matches("^[a-zA-Z]*$", b);

        String resultA = ma ? "T" : " F";
        String resultB = mb ? "T" : " F";
        System.out.println("Wynik: A:" + resultA + ", B:" + resultB);




    }
}
