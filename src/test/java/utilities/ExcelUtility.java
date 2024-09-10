/* package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    private String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            return sheet.getPhysicalNumberOfRows(); // Better for row count including empty rows
        }
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(rownum);
            if (row != null) {
                return row.getLastCellNum(); // Get number of cells in the row
            }
            return 0; // No cells if row is null
        }
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(rownum);
            if (row != null) {
                XSSFCell cell = row.getCell(colnum);
                DataFormatter formatter = new DataFormatter();
                return formatter.formatCellValue(cell);
            }
            return "";
        }
    }

    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File file = new File(path);
        boolean fileExists = file.exists();

        try (XSSFWorkbook workbook = fileExists ? new XSSFWorkbook(new FileInputStream(file)) : new XSSFWorkbook();
             FileOutputStream fo = new FileOutputStream(file)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            XSSFRow row = sheet.getRow(rownum);
            if (row == null) {
                row = sheet.createRow(rownum);
            }

            XSSFCell cell = row.createCell(colnum);
            cell.setCellValue(data);

            workbook.write(fo);
        }
    }

    public void fillCellColor(String sheetName, int rownum, int colnum, IndexedColors color) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(rownum);
            if (row != null) {
                XSSFCell cell = row.getCell(colnum);
                if (cell != null) {
                    CellStyle style = workbook.createCellStyle();
                    style.setFillForegroundColor(color.getIndex());
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cell.setCellStyle(style);
                }
            }

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }

    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        fillCellColor(sheetName, rownum, colnum, IndexedColors.GREEN);
    }

    public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        fillCellColor(sheetName, rownum, colnum, IndexedColors.RED);
    }
} 


package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;

	public ExcelUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowcount = sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowcount;
	}

	public int getCellCount(String sheetName, int rownum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int cellcount = sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return cellcount;
	}

	public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum); // returntype XSSFRow, class XSSFSheet
		cell = row.getCell(colnum);

		DataFormatter formatter = new DataFormatter();
		String data;
		try {
			data = formatter.formatCellValue(cell); // returns the formatted value of a cell as a string regardless
		} catch (Exception e) {
			data = "";
		}
		workbook.close();
		fi.close();
		return data;
	}

	public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
		File xlfile = new File(path);
		if (!xlfile.exists()) { // if file not exists then create new file
			workbook = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			workbook.write(fo);
		}

		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);

		if (workbook.getSheetIndex(sheetName) == -1) // if sheet not exists then create new sheet
			workbook.createSheet(sheetName);
		sheet = workbook.getSheet(sheetName);

		if (sheet.getRow(rownum) == null) // if row not exists then create new row
			sheet.createRow(rownum);
		row = sheet.getRow(rownum);

		cell = row.createCell(colnum);
		cell.setCellValue(data);

		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}

	public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {

		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);

		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);

		style = workbook.createCellStyle();

		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}

	public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {

		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);

		style = workbook.createCellStyle();

		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}

} */

package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    private String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            return sheet.getPhysicalNumberOfRows(); // Better for row count including empty rows
        }
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(rownum);
            if (row != null) {
                return row.getLastCellNum(); // Get number of cells in the row
            }
            return 0; // No cells if row is null
        }
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(rownum);
            if (row != null) {
                XSSFCell cell = row.getCell(colnum);
                DataFormatter formatter = new DataFormatter();
                return formatter.formatCellValue(cell);
            }
            return "";
        }
    }

    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        File file = new File(path);
        boolean fileExists = file.exists();

        try (XSSFWorkbook workbook = fileExists ? new XSSFWorkbook(new FileInputStream(file)) : new XSSFWorkbook();
             FileOutputStream fo = new FileOutputStream(file)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            XSSFRow row = sheet.getRow(rownum);
            if (row == null) {
                row = sheet.createRow(rownum);
            }

            XSSFCell cell = row.createCell(colnum);
            cell.setCellValue(data);

            workbook.write(fo);
        }
    }

    public void fillCellColor(String sheetName, int rownum, int colnum, IndexedColors color) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(rownum);
            if (row != null) {
                XSSFCell cell = row.getCell(colnum);
                if (cell != null) {
                    CellStyle style = workbook.createCellStyle();
                    style.setFillForegroundColor(color.getIndex());
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    cell.setCellStyle(style);
                }
            }

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }

    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        fillCellColor(sheetName, rownum, colnum, IndexedColors.GREEN);
    }

    public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        fillCellColor(sheetName, rownum, colnum, IndexedColors.RED);
    }
}

