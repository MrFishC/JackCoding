//package com.jack.a01_design_pattern.a01_dynamic_agent;
//
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class ProxyUtils {
//
//    public static void generateClassFile(Class clazz,String proxyName){
//        /*ProxyGenerator.generateProxyClass(
//                proxyName, interfaces, accessFlags);*/
//        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
//                proxyName, new Class[]{clazz});
//        String paths = clazz.getResource(".").getPath();
//        System.out.println(paths);
//        FileOutputStream out = null;
//
//        try {
//            out = new FileOutputStream(paths+proxyName+".class");
//            out.write(proxyClassFile);
//            out.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
