package com.blocklynukkit.loader.utils;

import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;
import com.blocklynukkit.loader.Loader;
import com.blocklynukkit.loader.other.net.http.MyCustomHandler;
import com.blocklynukkit.loader.other.net.http.MyFileHandler;
import com.blocklynukkit.loader.other.net.http.MyHttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;


public class Utils {
    public static void makeHttpServer(int port) {
        try {
            InetSocketAddress address = new InetSocketAddress(port);
            Loader.httpServer = HttpServer.create(address, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Loader.httpServer.createContext("/", new MyHttpHandler());
            Loader.httpServer.createContext("/file", new MyFileHandler());
            Loader.httpServer.createContext("/api", new MyCustomHandler());
            //设置服务器的线程池对象
            Loader.httpServer.setExecutor(Executors.newFixedThreadPool(10));
            //启动服务器
            Loader.httpServer.start();
        } catch (Exception e) {
            try {
                Loader.httpServer = HttpServer.create(new InetSocketAddress(54321), 10);
                Loader.httpServer.createContext("/", new MyHttpHandler());
                Loader.httpServer.createContext("/file", new MyFileHandler());
                Loader.httpServer.createContext("/api", new MyCustomHandler());
                //设置服务器的线程池对象
                Loader.httpServer.setExecutor(Executors.newFixedThreadPool(10));
                //启动服务器
                Loader.httpServer.start();
                if (Server.getInstance().getLanguage().getName().contains("中文")) {
                    Loader.getPluginLogger().info(TextFormat.RED + "您的" + port + "端口被占用！尝试在54321端口启动httpapi！");
                } else {
                    Loader.getPluginLogger().info(TextFormat.RED + "The server's PORT" + port + " is not available! BlocklyNukkit is trying to start htttpapi service on PORT54321!");
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                if (Server.getInstance().getLanguage().getName().contains("中文")) {
                    Loader.getPluginLogger().info(TextFormat.RED + "启动httpapi服务失败！端口被完全拦截！");
                    Loader.getPluginLogger().info(TextFormat.YELLOW + "解释器正在以无网络服务模式运行！修改port.yml以解决此问题！");
                } else {
                    Loader.getPluginLogger().info(TextFormat.RED + "Failed to start httpapi service!No available PORT to use!");
                    Loader.getPluginLogger().info(TextFormat.YELLOW + "BlocklyNukkit is running without providing net service! Rewrite port.yml to solve this problem!");
                }
            }
        }
    }

    public static String replaceLast(String string, String match, String replace) {
        if (null == replace) {
            //参数不合法，原样返回
            return string;
        }

        StringBuilder sBuilder = new StringBuilder(string);
        int lastIndexOf = sBuilder.lastIndexOf(match);
        if (-1 == lastIndexOf) {
            return string;
        }

        return sBuilder.replace(lastIndexOf, lastIndexOf + match.length(), replace).toString();
    }

    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];

        if (file.length() != filelength.intValue()) {
            return null;
        }

        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            return new String(filecontent, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String randomDeveloper() {
        String[] list = new String[]{"冰凉", "电池酱", "企鹅", "红楼君", "夏亚", "亦染", "WetABQ", "HBJ", "你的旺财", "若水", "神奇的YYT"
                , "pqguanfang", "泥土怪", "“伟大的”P(屁)爷"};
        return list[(int) Math.floor(list.length * Math.random())];
    }

    public static void download(String downloadUrl, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            URL url = new URL(downloadUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, length);
            }
            fileOutputStream.close();
            inputStream.close();
//            if(isWindows()){
//                if (Server.getInstance().getLanguage().getName().contains("中文"))
//                    Loader.getlogger().info(TextFormat.YELLOW+"正在为windows转码... "+TextFormat.GREEN+"作者对微软的嘲讽：(sb Windows,都老老实实用utf编码会死吗？)");
//                else
//                    Loader.getlogger().info(TextFormat.YELLOW+"Transcoding for windows... "+TextFormat.GREEN+"The Author says:(Will Bill Gates die if windows uses utf in all countries?)");
//            }
        } catch (IOException e) {
            Loader.getPluginLogger().error("download error ! url :{" + downloadUrl + "}, exception:{" + e + "}");
        }
        if (Server.getInstance().getLanguage().getName().contains("中文"))
            Loader.getPluginLogger().info(TextFormat.GREEN + "成功同步：" + file.getName());
        else
            Loader.getPluginLogger().info(TextFormat.GREEN + "successfully update: " + file.getName());
    }

    public static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }

    public static String readToString(File file) {
        String encoding = getFilecharset(file);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            if (Server.getInstance().getLanguage().getName().contains("中文"))
                System.err.println("操作系统不支持 " + encoding);
            else
                System.err.println("Your OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public static void writeWithString(File file, String string, String charSet) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charSet));
            writer.write(string);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static void writeWithString(File file, String string) {
        writeWithString(file, string, "UTF-8");
    }

    public static boolean check(File file1, File file2) {
        boolean isSame = false;
        if ((!file1.exists()) || (!file2.exists())) {
            return false;
        }
        String img1Md5 = getMD5(file1);
        String img2Md5 = getMD5(file2);
        if (img1Md5.equals(img2Md5)) {
            isSame = true;
        } else {
            isSame = false;
        }
        return isSame;
    }

    public static byte[] getByte(File file) {
        // 得到文件长度
        byte[] b = new byte[(int) file.length()];
        try {
            InputStream in = new FileInputStream(file);
            try {
                in.read(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }

    public static String getMD5(byte[] bytes) {
        // 16进制字符
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = bytes;
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            // 移位 输出字符串
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMD5(File file) {
        return getMD5(getByte(file));
    }

    public static void downLoadFromUrl(String urlStr, String fileName, String savePath, String toekn) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.setRequestProperty("lfwywxqyh_token", toekn);

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }


    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }

    }

    public static void downloadPlugin(String urlStr) throws IOException {
        File jar = new File(Server.getInstance().getPluginPath(), urlStr.substring(urlStr.lastIndexOf('/') + 1, urlStr.length()));
        if (jar.exists()) {
            return;
        }
        File tmp = new File(jar.getPath() + ".au");
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3 * 1000);
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        InputStream is = conn.getInputStream();
        int totalSize = conn.getContentLength();
        ProgressBarThread pbt = new ProgressBarThread(totalSize);
        new Thread(pbt).start();
        FileOutputStream os = new FileOutputStream(tmp);
        byte[] buf = new byte[4096];
        int size = 0;
        while ((size = is.read(buf)) != -1) {
            os.write(buf, 0, size);
            pbt.updateProgress(size);
        }
        is.close();
        os.flush();
        os.close();
        if (jar.exists())
            jar.delete();
        tmp.renameTo(jar);
        Server.getInstance().getPluginManager().loadPlugin(jar.getPath());
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static String sendGet(String url, String param) {
        String result = "NULL";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (!(param.length() == 0 || param == null)) {
                urlNameString = url + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            result = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendGetBlackBE(String url, String param) {
        String result = "NULL";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            Loader.getPluginLogger().warning(TextFormat.RED + "The cloud black-list server crashed!goto https://ban.bugmc.com/ to solve the provlem!");
            Loader.getPluginLogger().warning(TextFormat.RED + "BlackBE的云黑数据库炸了！快去https://ban.bugmc.com/求救！");
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String param) {
        try {
            return sendPost(url, param, null);
        } catch (IOException e) {
            e.printStackTrace();
            return "NULL";
        }
    }

    public static String sendPost(String url, String param, Map<String, String> header) throws UnsupportedEncodingException, IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(15000);
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        out = new PrintWriter(conn.getOutputStream());
        out.print(param);
        out.flush();
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf8"));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        return result;
    }


    private static String getFilecharset(File sourceFile) {
        String charset = "UTF-8";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0) {
                        charset = "GBK";
                        break;
                    }
                    if (0x80 <= read && read <= 0xBF) {// 单独出现BF以下的，也算是GBK
                        charset = "GBK";
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else {
                            charset = "GBK";
                            break;
                        }
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else {
                                charset = "GBK";
                                break;
                            }
                        } else {
                            charset = "GBK";
                            break;
                        }
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String StringEncryptor(String str, String type) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance(type);
            // 计算md5函数
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getPrivateField(Object instance, String filedName) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(filedName);
        field.setAccessible(true);
        return field.get(instance);
    }

    public static double limit(double a, double b) {
        b = Math.abs(b);
        if (a > b) {
            return b;
        } else if (a < (-b)) {
            return -b;
        } else {
            return a;
        }
    }

    /**
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。
     *
     * @param clazz      目标类
     * @param methodName 方法名
     * @param classes    方法参数类型数组
     * @return 方法对象
     * @throws Exception
     */
    public static Method getMethod(Class clazz, String methodName,
                                   final Class[] classes) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName,
                            classes);
                }
            }
        }
        return method;
    }

    /**
     * @param obj        调整方法的对象
     * @param methodName 方法名
     * @param classes    参数类型数组
     * @param objects    参数数组
     * @return 方法的返回值
     */
    public static Object invoke(final Object obj, final String methodName,
                                final Class[] classes, final Object[] objects) {
        try {
            Method method = getMethod(obj.getClass(), methodName, classes);
            method.setAccessible(true);// 调用private方法的关键一句话
            return method.invoke(obj, objects);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invoke(final Object obj, final String methodName,
                                final Class[] classes) {
        return invoke(obj, methodName, classes, new Object[]{});
    }

    public static Object invoke(final Object obj, final String methodName) {
        return invoke(obj, methodName, new Class[]{}, new Object[]{});
    }

    public static java.awt.Color hex2rgb(String hex) {
        // 用Integer转为十六进制的rgb值
        hex = hex.replace("#", "0x");
        int rgb = Integer.parseInt(hex.substring(2), 16);
        // 实例化java.awt.Color,获取对应的r、g、b值
        java.awt.Color color = new java.awt.Color(rgb);
        return color;
    }

    public static <K, V> Map.Entry<K, V> getTailByReflection(LinkedHashMap<K, V> map)
            throws NoSuchFieldException, IllegalAccessException {
        Field tail = map.getClass().getDeclaredField("tail");
        tail.setAccessible(true);
        return (Map.Entry<K, V>) tail.get(map);
    }
}

