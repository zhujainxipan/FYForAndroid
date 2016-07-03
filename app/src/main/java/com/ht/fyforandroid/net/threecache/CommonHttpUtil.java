package com.ht.fyforandroid.net.threecache;

/**
 * Created by IntelliJ IDEA
 * Project:WantToGo
 * Author:gudao[FR]
 * Email:lizhenmeng90@163.com
 * Date:15-6-23
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * 通用的HttpUrlConnection网络请求工具类
 */
public final class CommonHttpUtil {
    //默认连接超时时间
    public static final int TIMEOUT_CONNECTION = 6000;

    public static byte[] doGet(String url){
        return doGet(url,null);
    }

    public static byte[] doDelete(String url){
        return doDelete(url, null);
    }

    public static byte[] doPost(String url,HashMap<String,String> headers){
        return doPost(url, null, headers);
    }

    public static byte[] doPut(String url,HashMap<String,String> headers){
        return doPut(url,null,headers);
    }
    /**
     * 实现get请求，并且可以设置网络的请求头，例如授权认证，就需要请求头的支持
     * @param url
     * @param headers
     * @return
     */
    public static byte[] doGet(String url,HashMap<String,String> headers){
        byte[] ret = null;
        if (url != null) {
            try {
                URL u = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                //1、设置请求方法
                conn.setRequestMethod("GET");
                //2.设置连接方式，是否长连接:colse 每次连接的时候，创建新的Socket
                //keep-alive 每次连接尝试重用原来的Socket
                //设置请求头的部分
                conn.setRequestProperty("Connection","keep-alive");
                //设置网络的User-Agent字段，服务器根据这个来判断设置的系统
                conn.setRequestProperty("User-Agent","CommonHttpUtil/0.1[Android]");
                //TODO 进行附加HTTP头字段的设置
                if(headers!=null){
                    Set<String> keySet = headers.keySet();
                    for (String key : keySet) {
                        String value = headers.get(key);
                        conn.setRequestProperty(key,value);
                    }
                }

                //TODO 进行连接操作
                //设置联网超时，指定时间之后，如果没有连接，抛异常
                //conn.setConnectTimeout(TIMEOUT_CONNECTION);

                //允许连接自动的处理网络重定向
                conn.setInstanceFollowRedirects(true);
                //设置客户端与服务器接收数据的时候，总共允许接收多长时间
                //超过这个时间，抛异常
                //conn.setReadTimeout(TIMEOUT_CONNECTION);
                //连接
                //conn.connect();
                //进行联网，返回服务器的状态码
                int responseCode = conn.getResponseCode();
                if (responseCode==200){
                    InputStream inputStream = conn.getInputStream();
                    //网络数据可能是经过压缩的
                    String contentEncoding = conn.getContentEncoding();
                    if (contentEncoding != null) {
                        if (contentEncoding.equals("gzip")){
                            //对应 Connection-Encoding:gizp
                            inputStream = new GZIPInputStream(inputStream);
                        }else{
                            //TODO 处理非GZIP压缩方式
                        }
                    }
                    //TODO 读取输入流的部分
                    ret = StreamUtil.readStream(inputStream);
                    inputStream.close();
                }else{

                }
                //关闭网络连接
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    //post请求

    /**
     * Post请求，传递key=value&key=value的数据请求
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static byte[] doPost(
            String url,
            HashMap<String,String> params,
            HashMap<String,String> headers){
        byte[] ret = null;
        if (url != null) {
            try {
                URL u = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                ////////////////////////////
                //1.方法
                conn.setRequestMethod("POST");
                //2.Connection 与 User-Agent设置
                conn.setRequestProperty("Accept-Language","zh-CN");
                conn.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; Samsung Galaxy S2 - 4.1.1 - API 16 - 480x800 Build/JRO03S) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 (xiangqu; Client/Android4.1.1 V/1986|1.9.86 channel/yingyb) Paros/3.2.13");
                conn.setRequestProperty("MBus-spm","53.1.0.0");
                conn.setRequestProperty("User-DeviceId","MDAwMDAwMDAwMDAwMDAwLzMxMDI2MDAwMDAwMDAwMC8wODowMDoyNzpmNDplYzpjYQ==");
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestProperty("Host","api.xiangqu.com");
                //3.设置附加的Http头字段
                //TODO 当进行post请求的时候，必须要设置Content-Type字段
                //只有设置这个字段之后，服务器才能正确获取请求参数
                //提交服务器key =value方法的参数，就是使用这种类型
                //Connection: Keep-Alive
                conn.setRequestProperty("Connection", "Keep-Alive");
                if(headers!=null){
                   Set<String> keySet =  headers.keySet();
                    for (String key : keySet) {
                        String value = headers.get(key);
                        conn.setRequestProperty(key,value);
                    }
                }

                ////////////////
                //Post请求的设置部分 设置数据的部分
                conn.setDoInput(true);
                if (params!=null){
                    if (!params.isEmpty()){
                        //设置开启数据输出
                        conn.setDoOutput(true);
                    }
                }

                //先写数据
                if (params!=null && !params.isEmpty()){
                    StringBuffer sb = new StringBuffer();
                    Set<String> keySet = params.keySet();
                    for (String key : keySet) {
                        String value = params.get(key);
                        sb.append(URLEncoder.encode(key, "UTF-8"));
                        sb.append("=");
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    sb.deleteCharAt(sb.length()-1);//去掉最后一个&
                    String requestStr = sb.toString();
                    byte[] data = requestStr.getBytes();

                    //获取长度，设置请求头COntent-Length 代表提交的内容的长度
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length));
                    //输出数据
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(data);
                    outputStream.flush();
                    outputStream.close();
                }

                //连接
                conn.connect();


                //读取服务器返回值
                int responseCode = conn.getResponseCode();
                //可以不用判断responsecode 因为其他的码也可能有数据
                InputStream inputStream = conn.getInputStream();
                if (inputStream != null) {
                    //TODO 处理压缩的情况
                    String contentEncodint = conn.getContentEncoding();
                    if("gizp".equals(contentEncodint)){
                        inputStream = new GZIPInputStream(inputStream);
                    }

                    ret = StreamUtil.readStream(inputStream);
                    inputStream.close();
                }

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 进行put请求的方法 实际操作和post类似
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static byte[] doPut(
            String url,
            HashMap<String,String> params,
            HashMap<String,String> headers){
        byte[] ret = null;
        if (url != null) {
            try {
                URL u = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                ////////////////////////////
                //1.方法
                conn.setRequestMethod("PUT");
                //2.Connection 与 User-Agent设置
                conn.setRequestProperty("Connection","keep-alive");
                conn.setRequestProperty("User-Agent","CommonHttpUtil/0.1[Android]");
                //3.设置附加的Http头字段
                //TODO 当进行post请求的时候，必须要设置Content-Type字段
                //只有设置这个字段之后，服务器才能正确获取请求参数
                //提交服务器key =value方法的参数，就是使用这种类型
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                if(headers!=null){
                    Set<String> keySet =  headers.keySet();
                    for (String key : keySet) {
                        String value = headers.get(key);
                        conn.setRequestProperty(key,value);
                    }
                }

                ////////////////
                //Post请求的设置部分 设置数据的部分
                conn.setDoInput(true);
                if (params!=null){
                    if (!params.isEmpty()){
                        //设置开启数据输出
                        conn.setDoOutput(true);
                    }
                }

                //连接
                conn.connect();
                //先写数据
                if (params!=null && !params.isEmpty()){
                    StringBuffer sb = new StringBuffer();
                    Set<String> keySet = params.keySet();
                    for (String key : keySet) {
                        String value = params.get(key);
                        sb.append(URLEncoder.encode(key, "UTF-8"));
                        sb.append("=");
                        sb.append(URLEncoder.encode(value, "UTF-8"));
                        sb.append("&");
                    }
                    sb.deleteCharAt(sb.length()-1);//去掉最后一个&
                    String requestStr = sb.toString();
                    byte[] data = requestStr.getBytes();

                    //获取长度，设置请求头COntent-Length 代表提交的内容的长度
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length));
                    //输出数据
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(data);
                    outputStream.flush();
                    outputStream.close();
                }

                //读取服务器返回值
                int responseCode = conn.getResponseCode();
                //可以不用判断responsecode 因为其他的码也可能有数据
                InputStream inputStream = conn.getInputStream();
                if (inputStream != null) {
                    //TODO 处理压缩的情况
                    String contentEncodint = conn.getContentEncoding();
                    if("gizp".equals(contentEncodint)){
                        inputStream = new GZIPInputStream(inputStream);
                    }

                    ret = StreamUtil.readStream(inputStream);

                    inputStream.close();
                }

                ////////////

                ///////////////////////
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }


    /**
     * 进行delete请求的方法，实际操作和get类似
     * @param url
     * @param headers
     * @return
     */
    public static byte[] doDelete(String url,HashMap<String,String> headers){
        byte[] ret = null;
        if (url != null) {
            try {
                URL u = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                //1、设置请求方法
                conn.setRequestMethod("DELETE");
                //2.设置连接方式，是否长连接:colse 每次连接的时候，创建新的Socket
                //keep-alive 每次连接尝试重用原来的Socket
                //设置请求头的部分
                conn.setRequestProperty("Connection","keep-alive");
                //设置网络的User-Agent字段，服务器根据这个来判断设置的系统
                conn.setRequestProperty("User-Agent","CommonHttpUtil/0.1[Android]");
                //TODO 进行附加HTTP头字段的设置
                if(headers!=null){
                    Set<String> keySet = headers.keySet();
                    for (String key : keySet) {
                        String value = headers.get(key);
                        conn.setRequestProperty(key,value);
                    }
                }

                //TODO 进行连接操作
                //设置联网超时，指定时间之后，如果没有连接，抛异常
                //conn.setConnectTimeout(TIMEOUT_CONNECTION);

                //允许连接自动的处理网络重定向
                conn.setInstanceFollowRedirects(true);
                //设置客户端与服务器接收数据的时候，总共允许接收多长时间
                //超过这个时间，抛异常
                //conn.setReadTimeout(TIMEOUT_CONNECTION);
                //连接
                conn.connect();
                //进行联网，返回服务器的状态码
                int responseCode = conn.getResponseCode();
                if (responseCode==200){
                    InputStream inputStream = conn.getInputStream();
                    //网络数据可能是经过压缩的
                    String contentEncoding = conn.getContentEncoding();
                    if (contentEncoding != null) {
                        if (contentEncoding.equals("gzip")){
                            //对应 Connection-Encoding:gizp
                            inputStream = new GZIPInputStream(inputStream);
                        }else{
                            //TODO 处理非GZIP压缩方式
                        }
                    }
                    //TODO 读取输入流的部分
                    ret = StreamUtil.readStream(inputStream);
                    inputStream.close();
                }else{

                }
                //关闭网络连接
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

}
