package com.citycloud.ccuap.intellisense.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.citycloud.ccuap.ybhw.util.StringUtils;

/**
 * 
 * @ClassName: ExcelUtils 
 * @Description: excel操作工具类
 * @author: Hyman-->guonq@citycloud.com.cn
 * @date: 2019年11月13日 下午2:13:37 
 *
 */
public class ExcelUtils {

	private static Logger logger = LogManager.getLogger(ExcelUtils.class);


	
    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     * @param inputStream 读取文件的输入流
     * @param fileType 文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static Map<Integer,List<Map<String,String>>> readExcel(File file,Map<String,String> map) {

        Workbook workbook = null;
        FileInputStream inputStream = null;
        String fileName = file.getName();
        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                logger.warn("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            Map<Integer,List<Map<String,String>>> resultDataList = parseExcel(workbook, map);

            return resultDataList;
        } catch (Exception e) {
            logger.warn("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warn("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }
    
    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static Map<Integer,List<Map<String,String>>> readExcel(MultipartFile file,Map<String,String> map) {

        Workbook workbook = null;
        InputStream inputStream = null;
        String fileName = file.getOriginalFilename();
        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

            // 获取Excel工作簿
            inputStream = file.getInputStream();
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            Map<Integer,List<Map<String,String>>> resultDataList = parseExcel(workbook, map);

            return resultDataList;
        } catch (Exception e) {
            logger.warn("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warn("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }
    
    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static Map<Integer,List<Map<String,String>>> readExcel(FileInputStream inputStream,Map<String,String> map,String fileType) {

        Workbook workbook = null;

        try {
        	if (fileType.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (fileType.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            }

            // 读取excel中的数据
            Map<Integer,List<Map<String,String>>> resultDataList = parseExcel(workbook, map);

            return resultDataList;
        } catch (Exception e) {
            logger.warn("解析Excel失败，" + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warn("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }
    
    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static Map<Integer,List<Map<String,String>>> readExcel(String fileName,Map<String,String> map) {

        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                logger.warn("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            Map<Integer,List<Map<String,String>>> resultDataList = parseExcel(workbook, map);

            return resultDataList;
        } catch (Exception e) {
            logger.warn("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warn("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 解析Excel数据
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static Map<Integer,List<Map<String,String>>> parseExcel(Workbook workbook,Map<String,String> map) {
        // 开始解析sheet
       Map<Integer,List<Map<String,String>>> datas = new HashMap<>();
       long startTime = System.currentTimeMillis();
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
        	List<Map<String,String>> resultDataList = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(sheetNum);
            // 校验sheet是否为空
            if (sheet == null) {
            	logger.error("---------第----------" + sheetNum + " 页为空");
                continue;
            }

            int rowEnd = sheet.getPhysicalNumberOfRows();
            int cells = 0;
            Map<Integer,String> keys = null;
            for (int rowNum = 0; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row)
                    continue;
                
                if (rowNum == 0){
                	cells = row.getPhysicalNumberOfCells();
                	keys = converDataKey(row,map);
                	continue;
                }

                Map<String,String> resultData = convertRowToData(row,cells,keys);
                if (null == resultData) {
                    logger.warn("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
            datas.put(sheetNum, resultDataList);
        }
        System.out.println("kkk");
        long endTime = System.currentTimeMillis();
        logger.warn("---------解析excel耗时--------" + (startTime - endTime)/1000 + " 秒");
        return datas;
    }

    /**
	 * @param cells
	 * @param row
	 * @return
	 */
	private static Map<Integer, String> converDataKey(Row row,Map<String,String> map) {
		int cells = row.getPhysicalNumberOfCells();
		Map<Integer,String> keys = new HashMap<Integer,String>();
		for(int i = 0;i<cells;i++){
			Cell cell = row.getCell(i);
			String key = convertCellValueToString(cell);
			keys.put(i, map.get(key));
		}
		return keys;
	}
	/**
     * 将单元格内容转换为字符串
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
            	
            	if (DateUtil.isCellDateFormatted(cell)) {
    				Date tempValue = cell.getDateCellValue();
    				SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
    				returnValue = simpleFormat.format(tempValue);
    				break;
    			}
            	
            	Long longVal = Math.round(cell.getNumericCellValue());
                Double doubleVal = cell.getNumericCellValue();
                Object obj = null;
                if(Double.parseDouble(longVal + ".0") == doubleVal){   //判断是否含有小数位.0
                	obj = longVal;
                }
                else{
                	obj = doubleVal;
                }

                returnValue = StringUtils.valueOf(obj);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    /**
     * 
     * @param row
     * @param indexCells
     * @param keys
     * @return
     */
    private static Map<String,String> convertRowToData(Row row,int indexCells,Map<Integer,String> keys) {
    	Map<String,String> resultData = new HashMap<String,String>();

        for(int i = 0;i<indexCells;i++){
        	Cell cell = row.getCell(i);
        	String value = convertCellValueToString(cell);
        	resultData.put(keys.get(i), value);
        }

        return resultData;
    }
    
    /**
     * 
     * @param sheetName
     * @param fileName
     * @param columnName
     * @param columEn
     * @param dataList
     * @param response
     * @throws Exception
     */
    public static void ExportWithResponse(String sheetName, String fileName,String[] columnName, 
    		String [] columEn,List<Map<String,Object>> dataList,
            HttpServletResponse response) throws Exception {
            // 创建webbook
            HSSFWorkbook wb = new HSSFWorkbook();
            // 添加sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            HSSFRow row = sheet.createRow((int) 0 );
            //新增第一行 头部
            for (int i = 0; i < columnName.length; i++) 
            {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(columnName[i]);
            }
  
            //开始设置数据
            for (int i = 0; i < dataList.size(); i++) 
            {
                row = sheet.createRow((int) i + 1);
                Map<String,Object> dataMap = dataList.get(i);
                HSSFCell datacell = null;
                for (int j = 0; j < columEn.length; j++) 
                {
                    datacell = row.createCell(j);
                    datacell.setCellValue(StringUtils.valueOf(dataMap.get(columEn[j])));
                }
            }
  
            //返回问价流
            /**
             * 注意
             * 文件格式必须为xls，因为book类型为HSSFWorkbook
             * xlsx格式book类型必须为XSSFWorkbook，可自行验证
             */
            String filename = fileName + ".xls";
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            OutputStream out = response.getOutputStream();
            try {
                wb.write(out);
                logger.warn("文件 " + fileName + "导出成功");
            } catch (Exception e) {
            	 logger.error("文件 " + fileName + "导出失败");
            	 logger.error(fileName + e.getMessage());
            } finally {
                out.close();
                wb.close();
            }
  
  
    }
	
}
