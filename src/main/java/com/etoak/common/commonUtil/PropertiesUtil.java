package com.etoak.common.commonUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {


    /**
     * 根据属性文件路径获取所有的属性键值对
     * @param propertiesFilePath
     * @return
     * @throws IOException
     */
    public  static Properties getProps(String propertiesFilePath)  {
        Properties props = new Properties();
        InputStream in = null;
        try {
            //第一种，通过类加载器进行获取properties文件流
            //in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesFilePath);
            //第二种，通过类进行获取properties文件流
            in = PropertiesUtil.class.getResourceAsStream("/"+propertiesFilePath);
            try {
                props.load(new InputStreamReader(in, "utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }


    /**
     * 根据属性文件路径和属性的键获取属性的值
     * @param propertiesFilePath
     * @param key
     * @return
     * @throws IOException
     */
    public static String getProperty(String propertiesFilePath, String key){
        Properties props = null;
        props = getProps(propertiesFilePath);
        return props.getProperty(key);
    }

    /**
     * 写Properties文件
     */
    private static void writePropertiesFile(Map<String,String> propsMap , String outputPropertiesFilePath) throws Exception {

        if(propsMap.isEmpty() || propsMap.containsKey("") ){
            throw  new Exception("传入的键值对不能为空且键值对的主键不能包含空!");
        }

        if( !outputPropertiesFilePath.endsWith(".properties")){
            throw  new Exception("输出文件路径需要以.properties为结尾!");
        }

        //文件路径的文件夹不存在则创建
        String fileSeparator = File.separator;
        String dirPath = outputPropertiesFilePath.substring(0,outputPropertiesFilePath.lastIndexOf(fileSeparator)) ;
        if( !new File(dirPath).exists()){
            new File(dirPath).mkdirs();
        }

        Properties prop = new Properties() ;
        Iterator<Map.Entry<String, String>> it = propsMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String>  entry = it.next();
            prop.setProperty( entry.getKey(),entry.getValue());
        }

        FileOutputStream oFile = null ;
        try {
            //保存属性到b.properties文件
            oFile = new FileOutputStream(outputPropertiesFilePath, false);//true表示追加打开,false每次都是清空再重写
            //prop.store(oFile, "此参数是保存生成properties文件中第一行的注释说明文字");//这个会两个地方乱码
            //prop.store(new OutputStreamWriter(oFile, "utf-8"), "汉字乱码");//这个就是生成的properties文件中第一行的注释文字乱码
            prop.store(new OutputStreamWriter(oFile, "utf-8"), null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oFile != null ) {
                oFile.close();
            }
        }
    }


    public static void main(String[] args) throws Exception {
        String propertiesFilePath = "properties/userinfo.properties";
        Properties props = getProps(propertiesFilePath);
        Iterator<String> it = props.stringPropertyNames().iterator();
        Map<String,String> propsMap = new HashMap<>();
        while (it.hasNext()) {
            String key = it.next();
            String value = props.getProperty(key) ;
            System.out.println(key + ":" + value);
            propsMap.put(key,value);
        }
        writePropertiesFile(propsMap,"C:\\Users\\Administrator\\Desktop\\properties\\userinfo2.properties");

    }
}
