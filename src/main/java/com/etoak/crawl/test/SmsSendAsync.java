package com.etoak.crawl.test;

import com.etoak.common.commonUtil.DateUtil;

public class SmsSendAsync{

    private String prtNo;

    public SmsSendAsync(String prtNo) {
        this.prtNo = prtNo;
    }

    public void send() {
        new Thread(){
            public void run(){
                System.out.println( "1"+ DateUtil.getDateTImeStr() );
                try {
                    this.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("已经休息了5秒");
                System.out.println(  "2"+  DateUtil.getDateTImeStr() );
            }
        }.start();
        System.out.println(  "4"+  DateUtil.getDateTImeStr() );
    }



    public static void main(String[] args) {

        String prtNo = "1001200912310155555";
        SmsSendAsync sendAsync = new SmsSendAsync(prtNo);
        sendAsync.send();
        System.out.println( "3"+  DateUtil.getDateTImeStr() );
    }


}
