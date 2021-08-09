package com.zensar.helper;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.zensar.model.Student;


public class ExcelCreater {

	 public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "stdId", "stdName", "Cycle", "stdCource","stdFee" };
	  static String SHEET = "Student";
	  
	  public static boolean hasExcelFormat(MultipartFile file) {
		  System.out.println("dhoni");

		    if (!TYPE.equals(file.getContentType())) {
		      return false;
		    }

		    return true;
		  }
	  
	  public static ByteArrayInputStream tutorialsToExcel(List<Student> student) {

		    try (Workbook workbook = new XSSFWorkbook(); 
		    		ByteArrayOutputStream out = new ByteArrayOutputStream();) {
		      Sheet sheet = workbook.createSheet(SHEET);

		      // Header
		      Row headerRow = sheet.createRow(0);

		      for (int col = 0; col < HEADERs.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(HEADERs[col]);
		      }

		      int rowIdx = 1;
		      for (Student st : student) {
		        Row row = sheet.createRow(rowIdx++);

		        row.createCell(0).setCellValue(st.getStdId());
		       row.createCell(1).setCellValue(st.getStdName()); 
		       row.createCell(2).setCellValue(st.getStdCource());
		       row.createCell(3).setCellValue(st.getStdFee());
		      }

		      workbook.write(out);
		      return new ByteArrayInputStream(out.toByteArray());
		    } catch (IOException e) {
		      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		    }
		  }
	  @SuppressWarnings("unchecked")
	public static List<Student> excelToStudents(InputStream is) {
		  

		  try {
				Workbook workbook = new XSSFWorkbook(is);

				Sheet sheet = workbook.getSheet("SHEET");
				System.out.println("sheet creatd");
				@SuppressWarnings("rawtypes")
				Iterator rows = sheet.iterator();
				System.out.println("Object creatd");
				@SuppressWarnings("rawtypes")
				List students = new ArrayList();

				int rowNumber = 0;
				while (rows.hasNext()) {
					Row currentRow = (Row) rows.next();

					// skip header
					if (rowNumber == 0) {
						rowNumber++;
						continue;
					}

					@SuppressWarnings("rawtypes")
					Iterator cellsInRow = currentRow.iterator();

					Student std = new Student();

					int cellIndex = 0;
					while (cellsInRow.hasNext()) {
						Cell currentCell = (Cell) cellsInRow.next();

						if (cellIndex == 0) { // ID
							std.setStdId((long) currentCell.getNumericCellValue());
						} else if (cellIndex == 1) { // Name
							std.setStdName(currentCell.getStringCellValue());
						} else if (cellIndex == 2) { // Address
							std.setStdCource(currentCell.getStringCellValue());
						} else if (cellIndex == 3) { // Age
							std.setStdFee((int) currentCell.getNumericCellValue());
						}

						cellIndex++;
					}

					students.add(std);
				}

				// Close WorkBook
				workbook.close();

				return students;
			} catch (IOException e) {
				throw new RuntimeException("FAIL! -> message = " + e.getMessage());
			}      
		        
		    
}
}
