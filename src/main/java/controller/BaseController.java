package com.citycloud.ccuap.ybhw.controller;

import com.citycloud.ccuap.ybhw.util.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author meiming_mm@163.com
 * @since 2019/11/12
 */
public class BaseController {


    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired(required = false)
    protected HttpServletRequest request;

    @Autowired(required = false)
    protected HttpServletResponse response;

    // 返回页面的json字符串
    protected String jsonString;

    protected Long projectId;

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    /**
     * 默认构造函数
     */
    public BaseController() {
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 把文件流写到response
     *
     * @param filename
     *            文件名
     * @param bytes
     *            文件字节内容
     * @throws Exception
     */
    protected void writeFile(String filename, byte[] bytes) throws Exception {
		/*
		 * //得到format，为空为xls String format = request.getParameter("format"); if(format
		 * != null && format.equals("xlsx")){ //workbook = new XSSFWorkbook(); } else{
		 * workbook = new HSSFWorkbook(); }
		 *
		 * String filename = request.getParameter("filename"); if(filename == null ||
		 * filename.equals("")){ filename = "export.xls"; }
		 */

        response.setContentType("application/octet-stream; charset=iso-8859-1");
        StringBuffer contentDisposition = new StringBuffer("attachment; filename=\"");
        contentDisposition.append(URLEncoder.encode(filename, "UTF-8")).append("\"");
        // contentDisposition.append(filename).append("\"");
        // 解决火狐下下载文件名不能正常显示的问题
        // contentDisposition.append(new
        // String(filename.getBytes("utf-8"),"ISO-8859-1"));
        // contentDisposition.append("\"");
        response.setHeader("Content-Disposition", contentDisposition.toString());

        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        out.flush();
        out.close();
    }

    /**
     * 把文件流写到response
     *
     * @param filename
     *            文件名
     * @param in
     *            输入流
     * @throws Exception
     */
    protected void writeFile(String filename, InputStream in) throws Exception {
        response.setContentType("application/octet-stream; charset=iso-8859-1");
        StringBuffer contentDisposition = new StringBuffer("attachment; filename=\"");
        // contentDisposition.append(URLEncoder.encode(filename,
        // "UTF-8")).append("\"");
        // contentDisposition.append(filename).append("\"");
        // 解决火狐浏览器下载文件名中文乱码问题
        contentDisposition.append(new String(filename.getBytes("UTF-8"), "ISO-8859-1"));
        contentDisposition.append("\"");
        response.setHeader("Content-disposition", contentDisposition.toString());

        ServletOutputStream out = response.getOutputStream();

        byte[] buf = new byte[in.available()];
        in.read(buf);
        out.write(buf, 0, buf.length);

        in.close();
        out.flush();
        out.close();
    }

    /**
     * 把文件流写到response
     *
     * @param in
     * @throws Exception
     */
    protected void writeFile(InputStream in) throws Exception {
        response.setContentType("text/javascript; charset=utf-8");

        ServletOutputStream out = response.getOutputStream();

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
        in.close();
        out.flush();
        out.close();
    }

    /**
     * 把生成Excel写到response
     *
     * @param filename
     * @param workbook
     * @throws Exception
     */
    protected void writeExcel(String filename, Workbook workbook) throws Exception {
		/*
		 * //得到format，为空为xls String format = request.getParameter("format"); if(format
		 * != null && format.equals("xlsx")){ //workbook = new XSSFWorkbook(); } else{
		 * workbook = new HSSFWorkbook(); }
		 *
		 * String filename = request.getParameter("filename"); if(filename == null ||
		 * filename.equals("")){ filename = "export.xls"; }
		 */

        response.setContentType("application/octet-stream; charset=iso-8859-1");
        StringBuffer contentDisposition = new StringBuffer("attachment; filename=\"");
        // contentDisposition.append(URLEncoder.encode(filename,"UTF-8"));
        contentDisposition.append(filename).append("\"");
        response.setHeader("Content-disposition", contentDisposition.toString());

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }

    /**
     * 返回一个真实的路径
     *
     * @param filePath
     * @return String
     */
    protected String getRealPath(String filePath) {
        return request.getServletContext().getRealPath(filePath);
    }

//    /**
//     * 得到当前用户信息
//     *
//     * @return 当前用户
//     */
//    protected CurrentUser getCurrentUser() {
//        return (CurrentUser) request.getSession().getAttribute(SessionConstant.CURRENT_USER_KEY);
//
//    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @param defaultValue
     *            缺省值
     * @return 参数值
     */
    protected boolean getBooleanParameter(String key, Boolean defaultValue) {
        String str = request.getParameter(key);
        if (str == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(str.trim());
    }


    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @return 参数值
     */
    protected boolean getBooleanParameter(String key) {
        return getBooleanParameter(key, false);
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @param defaultValue
     *            缺省值
     * @return 参数值
     */
    protected String getStringParameter(String key, String defaultValue) {
        String str = request.getParameter(key);
        if ((str == null) || (str.trim().length() == 0)) {
            return defaultValue;
        } else {
            return str.trim();
        }
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @return 参数值
     */
    protected Integer getIntParameterFromHex(String key) {
        return getIntParameterFromHex(key, null);
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @param defaultValue
     *            缺省值
     * @return 参数值
     */
    protected Integer getIntParameterFromHex(String key, Integer defaultValue) {
        String str = request.getParameter(key);
        if ((str == null) || (str.trim().length() == 0)) {
            return defaultValue;
        } else {
            return Integer.parseInt(str.trim(), 16);
        }
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @return 参数值
     */
    protected String getStringParameter(String key) {
        return getStringParameter(key, "");
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @param defaultValue
     *            缺省值
     * @return 参数值
     */
    protected Integer getIntParameter(String key, Integer defaultValue) {
        String str = request.getParameter(key);
        if ((str == null) || (str.trim().length() == 0) || str.equals("null")) {
            return defaultValue;
        } else {
            str = str.trim();
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException exc) {
            throw new NumberFormatException("值：'" + str + "' 不是真正的整型。");
        }
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @return 参数值
     */
    protected Integer getIntParameter(String key) {
        return getIntParameter(key, null);
    }

    protected Short getShortParameter(String key, Short defaultValue) {
        Integer defaultIntValue = null;
        if (defaultValue != null) {
            defaultIntValue = new Integer(defaultValue);
        }
        Integer v = getIntParameter(key, defaultIntValue);
        if (v == null) {
            return null;
        } else {
            return v.shortValue();
        }
    }

    protected Short getShortParameter(String key) {
        return getShortParameter(key, null);
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @param defaultValue
     *            缺省值
     * @return 参数值
     */
    protected Double getDoubleParameter(String key, Double defaultValue) {
        String str = request.getParameter(key);
        if ((str == null) || (str.trim().length() == 0) || str.equals("null")) {
            return defaultValue;
        } else {
            str = str.trim();
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException exc) {
            throw new NumberFormatException("值：'" + str + "' 不是真正的浮点数。");
        }
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @return 参数值
     */
    protected Double getDoubleParameter(String key) {
        return getDoubleParameter(key, null);
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @param defaultValue
     *            缺省值
     * @return 参数值
     */
    protected Long getLongParameter(String key, Long defaultValue) {
        String str = request.getParameter(key);
        if ((str == null) || (str.trim().length() == 0)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException exc) {
            throw new NumberFormatException(key + ":不是真正的长整型.");
        }
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @return 参数值
     */
    protected Long getLongParameter(String key) {
        return getLongParameter(key, null);
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @param defaultValue
     *            缺省值
     * @return 参数值
     */
    protected Date getDateParameter(String key, Date defaultValue) throws Exception {
        String str = request.getParameter(key);
        if ((str == null) || (str.trim().length() == 0)) {
            return defaultValue;
        }
        try {
            return DateUtil.stringToDate(str.trim(), "yyyy-MM-dd");
        } catch (Exception ex) {
            throw new Exception(key + ":不是时间.");
        }
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @param defaultValue
     *            缺省值
     * @return 参数值
     */
    protected Date getDateTimeParameter(String key, Date defaultValue) throws Exception {
        String str = request.getParameter(key);
        if ((str == null) || (str.trim().length() == 0)) {
            return defaultValue;
        }
        try {
            return DateUtil.stringToDate(str.trim(), "yyyy-MM-dd HH24:MI:SS");
        } catch (Exception ex) {
            throw new Exception(key + ":不是时间.");
        }
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @return 参数值
     */
    protected Date getDateTimeParameter(String key) throws Exception {
        return getDateTimeParameter(key, null);
    }

    /**
     * 得到请求参数的值
     *
     * @param key
     *            键值
     * @return 参数值
     */
    protected Date getDateParameter(String key) throws Exception {
        return getDateParameter(key, null);
    }

    /**
     * 得到请求的file
     *
     * @param key
     *            键值
     * @return 文件
     */
    protected MultipartFile getFileParameter(String key) {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile multipartFile = multipartRequest.getFile(key);

        return multipartFile;
    }

    /**
     * 客户端ip地址
     *
     * @return ip地址
     */
    protected String getRemortIP() {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        } else {
            return request.getHeader("x-forwarded-for");
        }
    }

    /**
     * 从request得到字符串
     *
     * @return 请求字符串
     * @throws IOException
     */
    protected String getRequestString() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        char[] cbuf = new char[1024];
        int len = 0;
        while ((len = reader.read(cbuf)) != -1) {
            sb.append(new String(cbuf, 0, len));
        }
        return sb.toString();
    }

//    /**
//     * 获取项目id
//     * @return 项目id
//     */
//    protected Long getProjectId() {
//        CurrentUser user = getCurrentUser();
//        Long projectId = null;
//        if (user.getProject() != null) {
//            projectId = user.getProject().getProjectId();
//        }
//        return projectId;
//    }
//
//    /**
//     * 获取分管组织过滤表达式
//     * @return 过滤表达式
//     */
//    protected String getSqlFilter() {
//        CurrentUser user = getCurrentUser();
//        return user.getSqlFilter();
//    }




}
