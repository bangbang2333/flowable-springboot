package com.creativec.common.tools;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtil.class.getName());

    /**
     * get请求
     * @return
     */
    public static String doGet(String url) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/

                return EntityUtils.toString(response.getEntity());
            }
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        return null;
    }

    /**get请求(用于key-value格式的参数,且带有header请求头)
     * @param url
     * @param map            请求的参数map<key,value>键值对
     * @param headerKey        headers Key
     * @param headerValue    headers Value
     * @return
     */
    public static String doGet(String url,Map<String, String> map,String headerKey,String headerValue) {
        // 获取连接客户端工具
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            /*
             * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
             */
            URIBuilder uriBuilder = new URIBuilder(url);
            /** 第一种添加参数的形式 */
            /*uriBuilder.addParameter("name", "root");
            uriBuilder.addParameter("password", "123456");*/

            for (Map.Entry<String, String> entry : map.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }


            /** 第二种添加参数的形式 */
            /* List<NameValuePair> list = new LinkedList<>();
            BasicNameValuePair param1 = new BasicNameValuePair("startTime", "2019-1-1 00:00:00");
            BasicNameValuePair param2 = new BasicNameValuePair("endTime", "2019-1-9 00:00:00");
            list.add(param1);
            list.add(param2);
            uriBuilder.setParameters(list);*/

            // 根据带参数的URI对象构建GET请求对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            /*
             * 添加请求头信息
             */
            // 浏览器表示
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            // 传输的类型
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            //header的<key,value>
            httpGet.setHeader(headerKey, headerValue);

            // 执行请求
            response = httpClient.execute(httpGet);
            // 获得响应的实体对象
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            logger.info(e.getMessage());
        } catch (ParseException e) {
            System.err.println("解析错误");
            logger.info(e.getMessage());
        } catch (URISyntaxException e) {
            System.err.println("URI解析异常");
            logger.info(e.getMessage());
        } catch (IOException e) {
            System.err.println("IO异常");
            logger.info(e.getMessage());
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    logger.info(e.getMessage());
                }
            }
        }
        return entityStr;
    }




    /**
     * post请求(用于key-value格式的参数)
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map params,String headerKey,String headerValue)  throws IOException {
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            // 定义HttpClient
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 实例化HTTP方法
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(url));

            httpPost.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
            httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            if (headerKey != null && !headerKey.isBlank()) {
                //header的<key,value>
                httpPost.setHeader(headerKey, headerValue);
            }

            //设置参数
            List<NameValuePair> nvps = new ArrayList<>();
            for (Object o : params.keySet()) {
                String name = (String) o;
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));

                //System.out.println(name +"-"+value);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));

            HttpResponse response = httpClient.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {    //请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(), StandardCharsets.UTF_8));

                String line;
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line).append(NL);
                }
            } else {    //
                System.out.println("状态码：" + code);
                return null;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());

            return null;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return sb.toString();
    }

    /**
     * post请求（用于请求json格式的参数）
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, String params,String headerKey,String headerValue) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);


        if (headerKey != null && !headerKey.isBlank()) {
            //header的<key,value>
            httpPost.setHeader(headerKey, headerValue);
        }

        if (params != null && !params.isBlank()) {
            StringEntity entity = new StringEntity(params, StandardCharsets.UTF_8);
            entity.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpPost.setEntity(entity);
        }
        CloseableHttpResponse response = null;

        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                return EntityUtils.toString(responseEntity);
            }
            else{
                logger.info("请求返回:"+state+"("+url+")");
            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }
        return null;
    }
}
