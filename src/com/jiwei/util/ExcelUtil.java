package com.jiwei.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil {
	
		//当前文件已经存在
		private String excelPath = "D:\\comments.xls";
		//从第几行插入进去
		private int rowNum = 3;
		//在当前工作薄的那个工作表单中插入这行数据 
		private String sheetName = "Sheet1";
	 
		/**
		 * 总的入口方法
		 * @throws Exception 
		 */
//		public static void main(String[] args) throws Exception {
//			ExcelUtil crt = new ExcelUtil();
//			XSSFWorkbook wb = crt.createExcel(crt.excelPath);
//			wb.createSheet("Sheet1");
//			crt.insertRows(wb,"Sheet1",1);
//			crt.saveExcel(wb, crt.excelPath);
//		}
		
		public XSSFSheet createSheet(XSSFWorkbook wb,String sheetName)
		{
			XSSFSheet sheet = wb.createSheet(sheetName);
			return sheet;
		}
		
		/**
		 * 在已有的Excel文件中插入一行新的数据的入口方法
		 * @throws IOException 
		 */
		public static XSSFRow insertRows(XSSFWorkbook wb,String sheetName,int rowNum) throws IOException {
			XSSFSheet sheet1 = wb.getSheet(sheetName);
			XSSFRow row = createRow(sheet1, rowNum);
			return row;
		}
	    /**
	     * 保存工作薄
	     * @param wb
	     */
		public static void saveExcel(XSSFWorkbook wb,String path) {
			FileOutputStream fileOut;
			try {
				fileOut = new FileOutputStream(path);
				wb.write(fileOut);
				fileOut.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
		}
	    /**
	     * 创建要出入的行中单元格
	     * @param row
	     * @return
	     */
		private static XSSFCell createCell(XSSFRow row) {
			XSSFCell cell = row.createCell((short) 0);
			cell.setCellValue(999999);
			row.createCell(1).setCellValue(1.2);
			row.createCell(2).setCellValue("This is a string cell");
			return cell;
		}
	   /**
	    * 得到一个已有的工作薄的POI对象
	    * @return
	 * @throws IOException 
	    */
		private static XSSFWorkbook getWorkBook(String path) throws IOException {
			XSSFWorkbook wb = null;
			FileInputStream fis = null;
			File f = new File(path);
			f.createNewFile();
			try {
				if (f != null) {
					fis = new FileInputStream(f);
					wb = new XSSFWorkbook(fis);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return wb;
		}
	   /**
	    * 找到需要插入的行数，并新建一个POI的row对象
	    * @param sheet
	    * @param rowIndex
	    * @return
	    */
		private static XSSFRow createRow(XSSFSheet sheet, Integer rowIndex) {
			XSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {
				row = sheet.createRow(rowIndex);
			}
			return row;
		}
	 
		
	 
		public static XSSFWorkbook createExcel(String path) throws Exception {
			XSSFWorkbook wb = new XSSFWorkbook();
			//XSSFSheet sheet = wb.createSheet("第一个sheet页");
	        //用sheet对象创建行对象  
			//XSSFRow row = sheet.createRow(0);
	        //创建单元格样式     
	        //CellStyle cellStyle = wb.createCellStyle();
	        //用行对象创建单元格对象Cell 
//	        Cell cell = row.createCell(0);
//	        //用cell对象读写。设置Excel工作表的值
//	        cell.setCellValue(1);

	        FileOutputStream output = new FileOutputStream(path);
	        wb.write(output);
	        output.flush();
	        return wb;
	    }
}
