package com.dondavid.crawler;

import com.dondavid.jrjvip.pojo.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by herbert.tang on 12/16/2016.
 */
public class HttpClientTest {

    private ObjectMapper objectMapper;

    private void generateVipHtml(String url){
        String vipId = url.split("/")[9];

        String result = get(url);
        try {
            Map<String, List> maps;
            maps = objectMapper.readValue(result,Map.class);
            System.out.println(maps.size());
            List list = null;
            if(maps.size()==2){
                list = maps.get("data");
            }
            if(list.size()==10){
                //update next viewId
            }

            List<DataModel> contentList = new ArrayList<DataModel>();
            if(list!=null && list.size()>0){
                for(Object o : list){
                    Map<String,String> map = (Map<String, String>) o;
                    DataModel model = new DataModel();
                    model.setIdx(0);
                    model.setId(Integer.parseInt(map.get("id")));
                    model.setContent(map.get("content"));
                    model.setTime(map.get("time"));
                    model.setImage(map.get("image"));
                    System.out.println(model);
                    contentList.add(model);
                }
            }

            File html = new File("D:\\My Documents\\桌面\\jrj\\test\\" + vipId +".html");
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(html));
            BufferedWriter bw = new BufferedWriter(ow);

            bw.write("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<meta charset=\"utf-8\">\n" +
                    "<title>jrj vips</title>");
            bw.write("<style>\n" +
                    "div {\n" +
                    "    border: 1px solid black;\n" +
                    "    margin: 5px;\n" +
                    "}\n" +
                    ".points {\n" +
                    "    height: 718px;\n" +
                    "    overflow: auto;\n" +
                    "}\n" +
                    "\n" +
                    ".vip_points {\n" +
                    "    height: 657px;\n" +
                    "\twidth: 600px;\n" +
                    "}\n" +
                    "</style>");
            bw.write("</head>\n" +
                    "<body>");

            bw.write("<div class=\"points vip_points\">\n");
            bw.write("<ul>\n");

            for(DataModel data : contentList){
                bw.write("<li>\n");
                bw.write("<div>\n");
                bw.write(data.getContent());
                bw.write("</div>\n");
                bw.write("<div>\n");
                bw.write(data.getTime());
                bw.write("</div>\n");
                bw.write("<div>\n");
                bw.write(data.getImage());
                bw.write("</div>\n");
                bw.write("</li>\n");
            }
            bw.write("</ul>\n");
            bw.write("</div>");
            bw.write("</body>\n" +
                    "</html>");
            bw.close();
            ow.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jUnitTest() throws IOException {
        objectMapper = new ObjectMapper();
        File vipListFile = new File("D:\\My Documents\\桌面\\jrj\\viplist.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(vipListFile)));
        String url=null;
        while((url = reader.readLine())!=null){
            System.out.println(url);
            generateVipHtml(url);
        }
    }
    /**
     * 发送 get请求
     */
    public String get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result=null;
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    result = EntityUtils.toString(entity);
                    System.out.println("Response content: " + result);
                }
                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
