package com.blocklynukkit.loader.other.net.http;

import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.UnionData;
import com.blocklynukkit.loader.utils.Utils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            StringBuilder responseText = new StringBuilder();
//            responseText.append("请求方法：").append(httpExchange.getRequestMethod()).append("<br/>");
//            responseText.append("请求参数：").append(getRequestParam(httpExchange)).append("<br/>");
//            responseText.append("请求头：<br/>").append(getRequestHeader(httpExchange));
            String url = Loader.plugin.getDataFolder() + "/index.html";
            File f = new File(url);
            if (f.exists())    //判断请求的文件是否存在
            {
                String html = Utils.readToString(new File(url));
                if (html.length() <= 3) html = "<p>404_error</p>";

                for (Map.Entry<String, String> entry : UnionData.htmlHolderMap.entrySet()) {
                    html = html.replaceAll(entry.getKey(), entry.getValue());
                }
//                if(Server.getInstance().getPluginManager().getPlugins().keySet().contains("PlaceholderAPI") && Class.forName("com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI")!=null){
//                    html = PlaceholderAPI.getInstance().translateString(html);
//
//                }
                html = html.replaceAll("%random_developer%", Utils.randomDeveloper());

                html = html.replaceAll("##", "%");
                responseText.append(html);
            } else {
                responseText.append("<h1>哦！网页被" + Utils.randomDeveloper() + "偷走了！</h1>");
            }
            handleResponse(httpExchange, responseText.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取请求头
     *
     * @param httpExchange
     * @return
     */
    private String getRequestHeader(HttpExchange httpExchange) {
        Headers headers = httpExchange.getRequestHeaders();
        return headers.entrySet().stream()
                .map((Map.Entry<String, List<String>> entry) -> entry.getKey() + ":" + entry.getValue().toString())
                .collect(Collectors.joining("<br/>"));
    }

    /**
     * 获取请求参数
     *
     * @param httpExchange
     * @return
     * @throws Exception
     */
    private String getRequestParam(HttpExchange httpExchange) throws Exception {
        String paramStr = "";

        if (httpExchange.getRequestMethod().equals("GET")) {
            //GET请求读queryString
            paramStr = httpExchange.getRequestURI().getQuery();
        } else {
            //非GET请求读请求体
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8));
            StringBuilder requestBodyContent = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                requestBodyContent.append(line);
            }
            paramStr = requestBodyContent.toString();
        }

        return paramStr;
    }

    /**
     * 处理响应
     *
     * @param httpExchange
     * @param responsetext
     * @throws Exception
     */
    private void handleResponse(HttpExchange httpExchange, String responsetext) throws Exception {
        //生成html
        StringBuilder responseContent = new StringBuilder();
        responseContent.append(responsetext);
        String responseContentStr = responseContent.toString();
        byte[] responseContentByte = responseContentStr.getBytes(StandardCharsets.UTF_8);

        //设置响应头，必须在sendResponseHeaders方法之前设置！
        httpExchange.getResponseHeaders().add("Content-Type", "text/html;charset=utf-8");

        //设置响应码和响应体长度，必须在getResponseBody方法之前调用！
        httpExchange.sendResponseHeaders(200, responseContentByte.length);

        OutputStream out = httpExchange.getResponseBody();
        out.write(responseContentByte);
        out.flush();
        out.close();
    }
}
