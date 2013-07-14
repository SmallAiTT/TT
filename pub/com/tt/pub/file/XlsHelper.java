package com.tt.pub.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.nutz.lang.Strings;

import com.tt.pub.map.TMap;

public class XlsHelper {
	
	private HSSFWorkbook book;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private Map<HSSFSheet, TMap> patriarchMap = new HashMap<HSSFSheet, TMap>();
	public XlsHelper(){
		book = new HSSFWorkbook();
	}
	
	public HSSFWorkbook getBook() {
		return book;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public HSSFSheet sheetAt(int index, String name){
		if(index < book.getNumberOfSheets()){
			return book.getSheetAt(index);
		}
		name = Strings.isEmpty(name) ? "sheet" + index : name;
		return book.createSheet(name);
	}
	public HSSFSheet sheetAt(int index){
		return sheetAt(index, null);
	}
	public HSSFSheet sheetOf(String name){
		HSSFSheet sheet = book.getSheet(name);
		if(sheet != null) return sheet;
		return book.createSheet(name);
	}
	
	public HSSFRow rowAt(HSSFSheet sheet, int rowIndex){
		if(sheet == null) return null;
		HSSFRow row = sheet.getRow(rowIndex);
		if(row != null) return row;
		return sheet.createRow(rowIndex);
	}
	public HSSFRow rowAt(int sheetIndex, int rowIndex){
		return rowAt(sheetAt(sheetIndex), rowIndex);
	}
	
	public HSSFCell cellAt(HSSFRow row, int cellIndex, int type){
		HSSFCell cell = row.getCell((short) cellIndex);
		if(cell != null) return cell;
		return row.createCell((short) cellIndex, type);
	}
	public HSSFCell cellAt(HSSFRow row, int cellIndex){
		HSSFCell cell = row.getCell((short) cellIndex);
		if(cell != null) return cell;
		return row.createCell((short) cellIndex);
	}
	@SuppressWarnings("deprecation")
	public HSSFCell setValue(HSSFCell cell, Object value){
		if(value == null){
			cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
		}else if(value instanceof Integer){
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Integer)value);
		}else if(value instanceof Long){
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Long)value);
		}else if(value instanceof Double){
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Double)value);
		}else if(value instanceof Short){
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Short)value);
		}else if(value instanceof Float){
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Float)value);
		}else if(value instanceof Boolean){
			cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
			cell.setCellValue((Boolean)value);
		}else if(value instanceof Character){
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue((Character)value);
		}else{
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(value.toString());
		}
		return cell;
	}
	public HSSFCell setValue(HSSFRow row, int cellIndex, Object value){
		return setValue(cellAt(row, cellIndex), value);
	}
	public HSSFCell setValue(HSSFSheet sheet, int rowIndex, int cellIndex, Object value){
		return setValue(rowAt(sheet, rowIndex), cellIndex, value);
	}
	public HSSFSheet setImg(HSSFSheet sheet, int rowIndex, int colIndex, String imgFile) throws IOException{
		int index = imgFile.lastIndexOf(".");
		String type = imgFile.substring(index + 1).toLowerCase();
        TMap piMap = patriarchMap.get(sheet);
        if(piMap == null){
        	piMap = new TMap();
        	patriarchMap.put(sheet, piMap);
        }
        HSSFPatriarch patriarch = piMap.get(type);
        if(patriarch == null){
            patriarch = sheet.createDrawingPatriarch();
            piMap.put(type, patriarch);
        }
		// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray   
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();   
		BufferedImage bufferImg = ImageIO.read(new File(imgFile));   
		ImageIO.write(bufferImg, type, byteArrayOut);   
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255,   
		        (short) colIndex, rowIndex, (short) colIndex, rowIndex);   
		anchor.setAnchorType(3);   
		// 插入图片1   
		int typeOfPi = 0;
		if("png".equals(type)) typeOfPi = HSSFWorkbook.PICTURE_TYPE_PNG;
		else if("jpg".equals(type)) typeOfPi = HSSFWorkbook.PICTURE_TYPE_JPEG;
		patriarch.createPicture(anchor, book.addPicture(byteArrayOut.toByteArray(), typeOfPi));  
		return sheet;
	}
	public HSSFRow addCols(HSSFSheet sheet, String[] cols){
		HSSFRow row = rowAt(sheet, 0);
		for(int i = 0; i < cols.length; ++i){
			setValue(row, i, cols[i]);
		}
		return row;
	}
	
	public HSSFRow addCols(HSSFSheet sheet, List<String> cols){
		return addCols(sheet, cols.toArray(new String[cols.size()]));
	}
	
	public HSSFSheet setWs(HSSFSheet sheet, int[] ws){
		int index = -1;
		for(int w : ws){
			index ++;
			if(w <= 0) continue;
			sheet.setColumnWidth((short)index, (short)w);
		}
		return sheet;
	}

	/**
	 * DESC:为文件名称没有".xls"的添加".xls"后缀。
	 * @author zheng.xiaojun
	 * @since 2012-2-8
	 *
	 * @param fileName
	 * @return
	 */
	private String fmtFileName(String fileName){
		if(Strings.isBlank(fileName)) return "";
		int index = fileName.lastIndexOf(".");
		if(index < 0) return fileName + ".xls";
		return fileName;
	}
	public void write(String fileName) throws IOException{
		String filePath = fmtFileName(fileName);
		File file = new File(filePath);
		if(!file.exists()) file.createNewFile();
		outputStream = new FileOutputStream(filePath);
		book.write(outputStream);
	}
	public void write(OutputStream outputStream) throws IOException{
		this.outputStream = outputStream;
		book.write(outputStream);
	}
	public void close() throws IOException{
		if(inputStream != null){
			inputStream.close();
		}
		if(outputStream != null){
			outputStream.close();
		}
	}
}
