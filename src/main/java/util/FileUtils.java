/** 
 * @ClassName: FileUtils 
 * @Description: TODO
 * @author: sb
 * @date: 2019年11月16日 下午5:17:01 
 *  
 */
package com.citycloud.ccuap.ybhw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import com.citycloud.ccuap.ybhw.dao.ImgInfoMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: FileUtils
 * @Description: TODO
 * @author: Hyman-->guonq@citycloud.com.cn
 * @date: 2019年11月16日 下午5:17:01
 * 
 */
@Slf4j
public class FileUtils {

	/**
	 * @param fileImg
	 * @return
	 */
	public static String uploadFileImg(MultipartFile fileImg, String paths) {
		// TODO Auto-generated method stub
		log.warn("-------保存路径------"+paths);
		String path = "";
		if (null == fileImg || null == paths) {
			return path;
		}
		String fileName = fileImg.getOriginalFilename();
		
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		String subName = fileName.substring(0, fileName.lastIndexOf("."));//截取名字
		String name = new StringBuilder()
				.append(subName).append("_").append(System.currentTimeMillis())
				.append(".").append(fileType).toString().trim();
		File file = new File(paths + name);
		File parent = file.getParentFile();
		if(!parent.exists()){
			parent.mkdirs();
		}
		
			if(!file.exists()){
				try {
					file.createNewFile();
					fileImg.transferTo(file);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		return paths + name;
	}

	/**
	 * @param filePath
	 * @param response
	 */
	public static void resFile(String filePath, HttpServletResponse response,String contentType) {
		// TODO Auto-generated method stub
		
		FileInputStream fis = null;
        response.setContentType(contentType);
        try {
            OutputStream out = response.getOutputStream();
            File file = new File(filePath);
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
             e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                   fis.close();
                } catch (IOException e) {
                e.printStackTrace();
            }   
              }
        }
	}

	/**
	 * @param ids
	 * @param fileImg
	 * @param string
	 */
	public static void batchUploadImg(String ids, MultipartFile[] fileImg, String string) {
		// TODO Auto-generated method stub
		if(null == fileImg || null == string){
			return;
		}
		ImgInfoMapper imgInfoMapper = (ImgInfoMapper)SpringContextUtils.getBean("imgInfoMapper");
		List<Map<String,String>> list = new ArrayList<>();
		for(MultipartFile file :fileImg){
			if(null == file || file.isEmpty()){
				continue;
			}
			
			String path = uploadFileImg(file, string);//返回绝对路径
			
			Map<String,String> param = new HashMap<>();
//			String fileName = file.getOriginalFilename();
//			String subFile = fileName.substring(0, fileName.lastIndexOf("."));//截取文件名字
			param.put("ids", ids);
			param.put("path", path);
			param.put("img_desc", "维保凭证");
			param.put("img_type", "main_up_typ");
			list.add(param);
		}
		imgInfoMapper.insertImgInfo(list);
		
	}
	
	public static String imgTransferBase64(String path){
		
		if(StringUtils.isEmpty(path))
			return "";
		File file = new File(path);
		if(!file.exists())
			return "";
		byte []data = null;
		InputStream ist = null;
		
		try {
			ist = new FileInputStream(file);
			data = new byte[ist.available()];
			ist.read(data);
			
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			log.error("读取文件失败");
			e.printStackTrace();
		}finally{
			if(null != ist){
				try {
					ist.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("关闭文件流失败");
					e.printStackTrace();
				}
			}
		}
		
		String code = Base64.encodeBase64String(Objects.requireNonNull(data));
		
		return code;
	} 

}
