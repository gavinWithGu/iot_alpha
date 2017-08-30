package com.iot.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.iot.common.exception.BasicException;

public class ExcelUtils {
	public static void main(String[] args) throws Exception {
		File file = new File("D:\\tmp\\tmpl.xlsx");
		InputStream in = new FileInputStream(file);
		readXlsx(in, 4);
	}

	public static Map<Integer, List<String>> readXlsx(InputStream in,
			int maxRowNum) throws Exception {
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}

			// 循环行Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow == null) {
					continue;
				}
				if (xssfRow.getLastCellNum() < maxRowNum) {
					throw new BasicException("文件格式不正确!");
				}

				List<String> list = new ArrayList<String>();
				for (int i = 0; i < maxRowNum; i++) {
					list.add(getValue(xssfRow.getCell(i)));
				}
				map.put(rowNum, list);
			}
		}

		return map;
	}

	private static String getValue(XSSFCell xssfCell) {
		if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfCell.getNumericCellValue());
		} else {
			return String.valueOf(xssfCell.getStringCellValue());
		}
	}
}
