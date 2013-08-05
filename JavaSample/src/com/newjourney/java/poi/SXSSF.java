package com.newjourney.java.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

public class SXSSF {
	
	@Test
	public void testWriteToXlsx() throws Exception {
		SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		//SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
        Sheet sh = wb.createSheet();
        for(int rownum = 0; rownum < 1000; rownum++){
            Row row = sh.createRow(rownum);
            for(int cellnum = 0; cellnum < 10; cellnum++){
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }

        }

        // Rows with rownum < 900 are flushed and not accessible
        for(int rownum = 0; rownum < 900; rownum++){
          Assert.assertNull(sh.getRow(rownum));
        }

        // ther last 100 rows are still in memory
        for(int rownum = 900; rownum < 1000; rownum++){
            Assert.assertNotNull(sh.getRow(rownum));
        }
        
        FileOutputStream out = new FileOutputStream("G:/sxssf.xlsx");
        wb.write(out);
        out.close();

        // dispose of temporary files backing this workbook on disk
        wb.dispose();
	}
	
	@Test
	public void testRead() throws Exception {
		//InputStream inp = new FileInputStream("workbook.xls");
	    InputStream inp = new FileInputStream("G:/sxssf.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
	    Sheet sheet = wb.getSheetAt(0);
	    Row row = sheet.getRow(2);
	    Cell cell = row.getCell(3);
	    if (cell == null)
	        cell = row.createCell(3);
	    cell.setCellType(Cell.CELL_TYPE_STRING);
	    cell.setCellValue("HaHaHa");

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("G:/sxssf_cpy.xlsx");
	    wb.write(fileOut);
	    fileOut.close();
	}

}
