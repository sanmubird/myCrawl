package com.etoak.common.commonUtil;

import java.util.Random;

public class VerifyCodeUtil {

    public static String getVerifyCode(){
        Random r = new Random();
        String s = "" ;
        for(int i = 0 , l = 5; i <= l ; i++ ){
            s += r.nextInt(10);
        }
        return  s;
    }

    public static void main(String[] args) {
        getVerifyCode();
    }

}
