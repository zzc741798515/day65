package cn.nyse.controller;

import cn.nyse.utils.EditorUtils;
import com.baidu.ueditor.ActionEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/*
 * 统一处理富文本操作ajax请求及响应的controller
 */
@Controller
@RequestMapping("ueditor")
public class EditorController {
    @Autowired
    EditorUtils editorUtils;

    @RequestMapping("execute")
    @ResponseBody    //自动将响应对象转换json格式
    public String doExecute(String action, HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "upfile", required = false) MultipartFile upfile) throws UnsupportedEncodingException {
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String exec = "";

        //如果参数action的值是config，则是初始化富文本请求，获取后台的配置config.json数据
        if ("config".equals(action)) {
            exec = new ActionEnter(request, rootPath).exec();
        } else if ("uploadimage".equals(action)) {
            //如果参数action的值是uploadimage，则是图片上传，该值uploadimage与配置文件config.json中的图片设置imageActionName属性一致  其他文件、视频上传也是根据该值判断
            exec = editorUtils.uploadImg(upfile);
        }

        return exec;
    }
}
