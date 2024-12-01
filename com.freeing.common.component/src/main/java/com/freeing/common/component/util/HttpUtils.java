package com.freeing.common.component.util;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * http请求工具类（单例模式）
 *
 * @author xingo
 */
public class HttpUtils {

    /**
     * http协议
     */
    private static final String HTTP = "http";

    /**
     * https协议
     */
    private static final String HTTPS = "https";

    /**
     * 默认编码：UTF-8
     */
    private static final String CHAR_SET = "UTF-8";

    /**
     * 默认超时时间（毫秒）
     */
    private static final int DEFAULT_TIMEOUT = 3000;

    /**
     * 默认重试时间间隔
     */
    private static final int WAIT_TIME = 1000;

    /**
     * 连接池大小
     */
    private static final int POOL_MAX_SIZE = 500;

    /**
     * 每个线路上连接大小
     */
    private static final int PER_ROUTE_MAX_SIZE = 200;

    /**
     * 失败重试次数
     */
    private static final int RETRY_TIMES = 5;

    private HttpUtils() {

    }

    /**
     * 单例对象
     */
    private volatile static HttpUtils instance = null;

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }

        return instance;
    }

    private volatile CloseableHttpClient hc = null;

    private CloseableHttpClient getHttpClient() {
        if (hc == null) {
            synchronized (HttpUtils.class) {
                if (hc == null) {
                    try {
                        // 重试配置
                        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
                            @Override
                            public boolean retryRequest(IOException e, int count, HttpContext context) {
                                System.out.println("连接失败次数|" + count);
                                e.printStackTrace();

                                // 达到最大重试次数就放弃
                                if (count >= RETRY_TIMES) {
                                    return false;
                                }
                                // 返回true需要重试
                                if (e instanceof NoHttpResponseException        // 请求无响应就重试
                                    || e instanceof ConnectTimeoutException     // 连接超时
                                    || e instanceof SocketException             // 连接异常
                                    || e instanceof SocketTimeoutException      // socket超时
                                ) {
                                    try {
                                        // 重试延迟时间：1s
                                        TimeUnit.MILLISECONDS.sleep(WAIT_TIME);
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                    return true;
                                }
                                // 返回false不需要重试
                                if (e instanceof SSLException                       // SSL握手异常不要重试
                                    || e instanceof InterruptedIOException          // 中断
                                    || e instanceof UnknownHostException            // 目标server不可达
                                    || e instanceof UnsupportedSchemeException      // 未配置协议
                                ) {
                                    return false;
                                }

                                HttpClientContext clientContext = HttpClientContext.adapt(context);
                                HttpRequest request = clientContext.getRequest();
                                // 假设请求是幂等的，就再次尝试
                                return !(request instanceof HttpEntityEnclosingRequest);
                            }
                        };

//                        // 设置信任ssl访问：方式1
//                        SSLContext sslContext = SSLContext.getInstance("SSL");
//                        X509TrustManager tm = new X509TrustManager() {
//                            @Override
//                            public void checkClientTrusted(X509Certificate[] xcs, String string) {
//                            }
//
//                            @Override
//                            public void checkServerTrusted(X509Certificate[] xcs, String string) {
//                            }
//
//                            @Override
//                            public X509Certificate[] getAcceptedIssuers() {
//                                return null;
//                            }
//                        };
//                        sslContext.init(null, new TrustManager[]{ tm }, null);
                        // 设置信任ssl访问：方式2
                        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();

                        Registry<ConnectionSocketFactory> sf = RegistryBuilder
                            .<ConnectionSocketFactory>create()
                            .register(HTTP, PlainConnectionSocketFactory.INSTANCE)
                            .register(HTTPS, SSLConnectionSocketFactory.getSocketFactory())
//                                .register(HTTPS, new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
//                                .register(HTTPS, new SSLConnectionSocketFactory(sslContext))
                            .build();

                        // 连接池配置
                        PoolingHttpClientConnectionManager poolingManager = new PoolingHttpClientConnectionManager(sf);
                        // 最大连接数
                        poolingManager.setMaxTotal(POOL_MAX_SIZE);
                        // 每个路由最大连接数
                        poolingManager.setDefaultMaxPerRoute(PER_ROUTE_MAX_SIZE);

                        // 超时配置
                        RequestConfig defaultConfig = RequestConfig.custom()
                            .setConnectionRequestTimeout(DEFAULT_TIMEOUT)
                            .setConnectTimeout(DEFAULT_TIMEOUT)
                            .setSocketTimeout(DEFAULT_TIMEOUT)
                            .build();

                        hc = HttpClients.custom()
                            .setConnectionManager(poolingManager)
                            .setDefaultRequestConfig(defaultConfig)
                            .setRetryHandler(retryHandler)
                            .setSSLContext(sslContext)
                            .build();

//                       hc = HttpClientBuilder
//                                .create()
//                                .setConnectionManager(poolingManager)
//                                .setDefaultRequestConfig(defaultConfig)
//                                .setRetryHandler(retryHandler)
//                                .setSSLContext(sslContext)
//                                .build();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        return hc;
    }

    /**
     * 请求配置信息
     *
     * @param socketTime  等待服务器响应超时时间
     * @param connTime    连接建立超时时间，也就是三次握手完成时间
     * @param connReqTime 获取连接超时时间，httpclient使用池化管理，如果连接池中的连接都被占用了且超过该时间还没有获取到连接就会抛出异常
     * @return
     */
    private RequestConfig getRequestConfig(int socketTime, int connTime, int connReqTime) {
        Builder config = RequestConfig.custom();
        if (connReqTime > 0) {
            config.setConnectionRequestTimeout(connReqTime);
        } else {
            config.setConnectionRequestTimeout(DEFAULT_TIMEOUT);
        }
        if (connTime > 0) {
            config.setConnectTimeout(connTime);
        } else {
            config.setConnectTimeout(DEFAULT_TIMEOUT);
        }
        if (socketTime > 0) {
            config.setSocketTimeout(socketTime);
        } else {
            config.setSocketTimeout(DEFAULT_TIMEOUT);
        }

        return config.build();
    }

    /**
     * 发送post请求
     *
     * @param url    请求URL地址
     * @param params 请求参数
     * @return
     */
    public HttpResult doPost(String url, Map<String, Object> params) {
        return this.doPost(url, null, params, CHAR_SET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送post请求
     *
     * @param url        请求URL地址
     * @param params     请求参数
     * @param socketTime 等待响应超时时间
     * @return
     */
    public HttpResult doPost(String url, Map<String, Object> params, int socketTime) {
        return this.doPost(url, null, params, CHAR_SET, socketTime, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送post请求
     *
     * @param url     请求URL地址
     * @param headers 请求头部
     * @param params  请求参数
     * @return
     */
    public HttpResult doPost(String url, Map<String, String> headers, Map<String, Object> params) {
        return this.doPost(url, headers, params, CHAR_SET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送post请求
     *
     * @param url        请求URL地址
     * @param headers    请求头部
     * @param params     请求参数
     * @param socketTime 等待响应超时时间
     * @return
     */
    public HttpResult doPost(String url, Map<String, String> headers, Map<String, Object> params, int socketTime) {
        return this.doPost(url, headers, params, CHAR_SET, socketTime, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送post请求
     *
     * @param url         请求地址
     * @param headers     请求头部
     * @param params      请求参数
     * @param charset     编码
     * @param socketTime  等待响应超时时间
     * @param connTime    建立连接超时时间
     * @param connReqTime 获取连接超时时间
     * @return
     */
    public HttpResult doPost(String url, Map<String, String> headers, Map<String, Object> params, String charset, int socketTime, int connTime, int connReqTime) {
        if (url == null || "".equals(url)) {
            return HttpResult.builder().status(-1).error("url为空").build();
        }

        CloseableHttpClient hc = this.getHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpPost hpost = new HttpPost(url);
            // 默认超时时间
            hpost.setConfig(this.getRequestConfig(socketTime, connTime, connReqTime));

            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    String val = headers.get(key);
                    if (key != null && !"".equals(key) && val != null && !"".equals(val)) {
                        hpost.setHeader(key, val);
                    }
                }
            }

            if (params != null && !params.isEmpty()) {
                List<NameValuePair> param = new ArrayList<>();
                for (String key : params.keySet()) {
                    if (key != null && !"".equals(key) && params.get(key) != null) {
                        param.add(new BasicNameValuePair(key, params.get(key).toString()));
                    }
                }
                //设置字符集
                HttpEntity httpEntity = new UrlEncodedFormEntity(param, charset);
                //添加参数
                hpost.setEntity(httpEntity);
            }
            response = hc.execute(hpost);

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
            return HttpResult.builder()
                .status(response.getStatusLine().getStatusCode())
                .body(data)
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).body(e.getMessage()).build();
        } finally {
            try {
                if (response != null) response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送json数据
     *
     * @param url  请求地址
     * @param json 请求json数据
     * @return
     */
    public HttpResult postJson(String url, String json) {
        return this.postJson(url, null, json, CHAR_SET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送json数据
     *
     * @param url        请求地址
     * @param json       请求json数据
     * @param socketTime 等待响应超时时间
     * @return
     */
    public HttpResult postJson(String url, String json, int socketTime) {
        return this.postJson(url, null, json, CHAR_SET, socketTime, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送JSON数据
     *
     * @param url     请求地址
     * @param headers 请求头部
     * @param json    请求json数据
     * @return
     */
    public HttpResult postJson(String url, Map<String, String> headers, String json) {
        return this.postJson(url, headers, json, CHAR_SET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送JSON数据
     *
     * @param url        请求地址
     * @param headers    请求头部
     * @param json       请求json数据
     * @param socketTime 等待响应超时时间
     * @return
     */
    public HttpResult postJson(String url, Map<String, String> headers, String json, int socketTime) {
        return this.postJson(url, headers, json, CHAR_SET, socketTime, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送json请求
     *
     * @param url         请求地址
     * @param headers     请求头部
     * @param json        请求json数据
     * @param charset     编码
     * @param socketTime  等待响应超时时间
     * @param connTime    建立连接超时时间
     * @param connReqTime 获取连接超时时间
     * @return
     */
    public HttpResult postJson(String url, Map<String, String> headers, String json, String charset, int socketTime, int connTime, int connReqTime) {
        if (url == null || "".equals(url)) {
            return HttpResult.builder().status(-1).error("url为空").build();
        }

        CloseableHttpClient hc = this.getHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpPost hpost = new HttpPost(url);
            //默认超时时间
            hpost.setConfig(this.getRequestConfig(socketTime, connTime, connReqTime));

            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    String val = headers.get(key);
                    if (key != null && !"".equals(key) && val != null && !"".equals(val)) {
                        hpost.setHeader(key, val);
                    }
                }
            }

            hpost.setHeader("Content-Type", "application/json; charset=UTF-8");
            StringEntity msg = new StringEntity(json, charset);
            hpost.setEntity(msg);
            response = hc.execute(hpost);

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
            return HttpResult.builder().status(response.getStatusLine().getStatusCode()).body(data).build();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).error(e.getMessage()).build();
        } finally {
            try {
                if (response != null) response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送json请求，返回原始数据字节码
     *
     * @param url        请求地址
     * @param json       请求json数据
     * @param socketTime 超时时间
     * @return
     */
    public HttpResult postJsonResultBytes(String url, String json, int socketTime) {
        if (url == null || "".equals(url)) {
            return HttpResult.builder().status(-1).error("url为空").build();
        }

        CloseableHttpClient hc = this.getHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpPost hpost = new HttpPost(url);
            //默认超时时间
            hpost.setConfig(this.getRequestConfig(socketTime, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT));

            hpost.setHeader("Content-Type", "application/json; charset=UTF-8");
            StringEntity msg = new StringEntity(json, "UTF-8");
            hpost.setEntity(msg);
            response = hc.execute(hpost);

            HttpEntity entity = response.getEntity();
            byte[] bytes = stream2Bytes(entity.getContent());
            EntityUtils.consume(entity);
            return HttpResult.builder().status(response.getStatusLine().getStatusCode()).bytes(bytes).build();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).error(e.getMessage()).build();
        } finally {
            try {
                if (response != null) response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送xml数据
     *
     * @param url 请求地址
     * @param xml 请求xml数据
     * @return
     */
    public HttpResult postXml(String url, String xml) {
        return this.postXml(url, null, xml, CHAR_SET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送xml数据
     *
     * @param url 请求地址
     * @param xml 请求xml数据
     * @return
     */
    public HttpResult postXml(String url, String xml, int socketTime) {
        return this.postXml(url, null, xml, CHAR_SET, socketTime, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送xml数据
     *
     * @param url         请求地址
     * @param headers     请求头部
     * @param xml         请求xml数据
     * @param charset     编码
     * @param socketTime  等待响应超时时间
     * @param connTime    建立连接超时时间
     * @param connReqTime 获取连接超时时间
     * @return
     */
    public HttpResult postXml(String url, Map<String, String> headers, String xml, String charset, int socketTime, int connTime, int connReqTime) {
        if (url == null || "".equals(url)) {
            return HttpResult.builder().status(-1).error("url为空").build();
        }

        CloseableHttpClient hc = this.getHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpPost hpost = new HttpPost(url);
            //默认超时时间
            hpost.setConfig(this.getRequestConfig(socketTime, connTime, connReqTime));

            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    String val = headers.get(key);
                    if (key != null && !"".equals(key) && val != null && !"".equals(val)) {
                        hpost.setHeader(key, val);
                    }
                }
            }
            hpost.setHeader("Content-Type", "application/xml; charset=UTF-8");
            StringEntity msg = new StringEntity(xml, charset);
            hpost.setEntity(msg);
            response = hc.execute(hpost);

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
            return HttpResult.builder().status(response.getStatusLine().getStatusCode()).body(data).build();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).error(e.getMessage()).build();
        } finally {
            try {
                if (response != null) response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送get请求
     *
     * @param url 请求URL地址
     * @return
     */
    public HttpResult doGet(String url) {
        return this.doGet(url, null, null, CHAR_SET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送get请求
     *
     * @param url        请求地址
     * @param socketTime 等待响应超时时间
     * @return
     */
    public HttpResult doGet(String url, int socketTime) {
        return this.doGet(url, null, null, CHAR_SET, socketTime, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送get请求
     *
     * @param url    请求URL地址
     * @param params 请求参数
     * @return
     */
    public HttpResult doGet(String url, Map<String, String> params) {
        return this.doGet(url, null, params, CHAR_SET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送get请求
     *
     * @param url     请求URL地址
     * @param headers 请求头部
     * @param params  请求参数
     * @return
     */
    public HttpResult doGet(String url, Map<String, String> headers, Map<String, String> params) {
        return this.doGet(url, headers, params, CHAR_SET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送get请求
     *
     * @param url        请求URL地址
     * @param headers    请求头部
     * @param params     请求参数
     * @param socketTime 等待响应超时时间
     * @return
     */
    public HttpResult doGet(String url, Map<String, String> headers, Map<String, String> params, int socketTime) {
        return this.doGet(url, headers, params, CHAR_SET, socketTime, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 发送GET请求
     *
     * @param url         请求地址
     * @param headers     请求头部
     * @param params      请求参数
     * @param charset     编码
     * @param socketTime  等待响应超时时间
     * @param connTime    建立连接超时时间
     * @param connReqTime 获取连接超时时间
     * @return
     */
    public HttpResult doGet(String url, Map<String, String> headers, Map<String, String> params, String charset, int socketTime, int connTime, int connReqTime) {
        if (url == null || "".equals(url)) {
            return HttpResult.builder().status(-1).error("url为空").build();
        }

        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.setParameter(entry.getKey(), entry.getValue());
                }
            }
            url = builder.build().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).error(e.getMessage()).build();
        }

        CloseableHttpClient hc = this.getHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpGet hget = new HttpGet(url);
            // 设置超时时间
            hget.setConfig(this.getRequestConfig(socketTime, connTime, connReqTime));
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    String val = headers.get(key);
                    if (key != null && !"".equals(key) && val != null && !"".equals(val)) {
                        hget.setHeader(key, val);
                    }
                }
            }
            response = hc.execute(hget);

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
            return HttpResult.builder().status(response.getStatusLine().getStatusCode()).body(data).build();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).error(e.getMessage()).build();
        } finally {
            try {
                if (response != null) response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载文件
     *
     * @param url         请求地址
     * @param headers     请求头部
     * @param params      请求参数
     * @param localPath   本地保存文件路径
     * @param socketTime  数据传输过程中数据包之间间隔的最大时间
     * @param connTime    建立连接超时时间
     * @param connReqTime 从连接池获取连接的超时时间
     * @return
     */
    public HttpResult downloadFile(String url, Map<String, String> headers, Map<String, String> params, String localPath, int socketTime, int connTime, int connReqTime) {
        if (url == null || "".equals(url)) {
            return HttpResult.builder().status(-1).error("url为空").build();
        }

        try {
            URIBuilder builder = new URIBuilder(url);
            if (null != params) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.setParameter(entry.getKey(), entry.getValue());
                }
            }
            url = builder.build().toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).error("url连接错误").build();
        }

        CloseableHttpClient hc = this.getHttpClient();
        CloseableHttpResponse response = null;
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            HttpGet hget = new HttpGet(url);
            // 超时时间
            hget.setConfig(this.getRequestConfig(socketTime, connTime, connReqTime));
            if (null != headers && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    String val = headers.get(key);
                    if (key != null && !"".equals(key) && val != null && !"".equals(val)) {
                        hget.setHeader(key, val);
                    }
                }
            }
            response = hc.execute(hget);

            HttpEntity entity = response.getEntity();
            // 数据长度
            long length = entity.getContentLength();
            // 读取数据输入流，并将数据写入到文件中
            is = entity.getContent();
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int read = 0;
            while ((read = is.read(buffer)) > 0) {
                bos.write(buffer, 0, read);
            }
            fos = new FileOutputStream(localPath);
            bos.writeTo(fos);
            bos.flush();
            fos.flush();
            EntityUtils.consume(entity);
            return HttpResult.builder().status(response.getStatusLine().getStatusCode()).body(length + "").build();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).error(e.getMessage()).build();
        } finally {
            try {
                if (response != null) response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (bos != null) bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (is != null) is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件
     *
     * @param url      文件接收地址URL
     * @param filePath 本地文件路径
     * @return
     */
    public HttpResult postFile(String url, String filePath) {
        return this.postFile(url, null, null, null, filePath, "UTF-8", 20000, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 上传文件
     *
     * @param url       文件接收地址URL
     * @param fileParam 文件参数名（默认值：file）
     * @param filePath  本地文件路径
     * @return
     */
    public HttpResult postFile(String url, String fileParam, String filePath) {
        return this.postFile(url, null, null, fileParam, filePath, "UTF-8", 20000, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
    }

    /**
     * 上传文件
     *
     * @param url         文件接收地址URL
     * @param headers     头信息
     * @param params      附加参数
     * @param fileParam   参数中文件参数名（默认值：file）
     * @param filePath    本地文件路径
     * @param charset     编码
     * @param socketTime  等待响应超时时间
     * @param connTime    连接建立超时时间
     * @param connReqTime 从连接池获取连接的超时时间
     * @return
     */
    public HttpResult postFile(String url, Map<String, String> headers, Map<String, String> params, String fileParam, String filePath, String charset, int socketTime, int connTime, int connReqTime) {
        if (url == null || "".equals(url)) {
            return HttpResult.builder().status(-1).error("url为空").build();
        }

        if (filePath == null || "".equals(filePath)) {
            return HttpResult.builder().status(-1).error("文件路径错误").build();
        }
        File localFile = new File(filePath);
        if (!localFile.exists()) {
            return HttpResult.builder().status(-1).error("未获取到文件").build();
        }
        fileParam = (fileParam == null || "".equals(fileParam)) ? "file" : fileParam;

        CloseableHttpClient hc = this.getHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpPost hpost = new HttpPost(url);
            // 超时时间
            hpost.setConfig(this.getRequestConfig(socketTime, connTime, connReqTime));

            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    String val = headers.get(key);
                    if (key != null && !"".equals(key) && val != null && !"".equals(val)) {
                        hpost.setHeader(key, val);
                    }
                }
            }
            // hpost.setHeader("Content-Type", "multipart/form-data");

            MultipartEntityBuilder multiBuilder = MultipartEntityBuilder.create();
            // 解决中文文件名乱码
            // multiBuilder.setMode(HttpMultipartMode.RFC6532);
            multiBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            multiBuilder.setCharset(Charset.forName(charset));

            multiBuilder.addBinaryBody(fileParam, localFile);
            // multipartEntityBuilder.addPart("comment", new StringBody("This is comment", ContentType.TEXT_PLAIN));
            if (params != null && !params.isEmpty()) {
                for (String key : params.keySet()) {
                    String val = params.get(key);
                    if (key != null && val != null) {
                        multiBuilder.addTextBody(key, val);
//                        postEntity.addPart(key, new StringBody(params.get(key).toString(), Charset.forName(charset)));
                    }
                }
            }
            HttpEntity postEntity = multiBuilder.build();
            hpost.setEntity(postEntity);
            response = hc.execute(hpost);

            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
            return HttpResult.builder().status(response.getStatusLine().getStatusCode()).body(data).build();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).error(e.getMessage()).build();
        } finally {
            try {
                if (response != null) response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件
     *
     * @param url        请求url
     * @param stream     文件流
     * @param filename   原始文件名
     * @param socketTime 等待响应超时时间
     */
    public HttpResult uploadFile(String url, InputStream stream, String filename, int socketTime) {
        if (url == null || "".equals(url)) {
            return HttpResult.builder().status(-1).error("url为空").build();
        }

        CloseableHttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(this.getRequestConfig(socketTime, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT));
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addBinaryBody("file", stream, ContentType.MULTIPART_FORM_DATA, filename);
            post.setEntity(builder.build());
            response = getHttpClient().execute(post);
            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);
            return HttpResult.builder().status(response.getStatusLine().getStatusCode()).body(data).build();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.builder().status(-1).error(e.getMessage()).build();
        } finally {
            try {
                if (response != null) response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输入流转字节码
     *
     * @param is 输入流
     * @return
     * @throws IOException
     */
    public static final byte[] stream2Bytes(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int rc = 0;
        while ((rc = is.read(buff, 0, 1024)) > 0) {
            bos.write(buff, 0, rc);
        }

        return bos.toByteArray();
    }

    /**
     * 获取本机IP
     *
     * @return
     */
    public String getLocalHostIP() {
        String sIP = "";
        InetAddress ip = null;
        try {
            boolean bFindIP = false;
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                if (bFindIP) {
                    break;
                }
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")
                        && !"127.0.0.1".equals(ip.getHostAddress())) {
                        bFindIP = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ip != null) {
            sIP = ip.getHostAddress();
        }

        return sIP;
    }
}
