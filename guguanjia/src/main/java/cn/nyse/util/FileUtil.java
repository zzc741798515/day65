package cn.nyse.util;


import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class FileUtil {


    public static void download(String fname, HttpServletResponse response){
        try {

            //1.通过文件名获得文件
            File file=new File(fname);
            //将文件变成流,写入到HttpServletResponse的输出流里面
            //使用commons-io.jar的文件处理类实现,将文件转成一个byte[]字节流
            byte[] array=null;
            array = FileUtils.readFileToByteArray(file);
            //在response输出之前,设置输出的格式
            //默认不支持中文,new String(fname.getBytes(),"ISO-8859-1"),转义中文编码
            response.addHeader("Content-Disposition", "attachment;filename="+new String(fname.getBytes(),"ISO-8859-1"));
            //将文件写入到response的输出流
            response.getOutputStream().write(array);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

