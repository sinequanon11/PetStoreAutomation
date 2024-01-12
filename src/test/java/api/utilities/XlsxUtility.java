package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class XlsxUtility {


    public FileInputStream fi;
    public FileOutputStream fo;
    public XSSFWorkbook workbook;
    public String path;

    public XlsxUtility(String path) {
        this.path = path;
        try {
            fi = new FileInputStream(path);
            workbook = new XSSFWorkbook(fi);
            fi.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRowCount(String sheetName) {
        try {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            return (sheet == null) ? 0 : sheet.getLastRowNum() + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getCellCount(String sheetName, int rowNum) {
        try {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = (sheet != null) ? sheet.getRow(rowNum) : null;
            return (row == null) ? 0 : row.getLastCellNum();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getCellData(String sheetName, int rowNum, int colNum) {
        try {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                return "Sheet " + sheetName + " not found";
            }

            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                return "Row " + rowNum + " not found in sheet " + sheetName;
            }

            XSSFCell cell = row.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            return cell.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while getting cell data";
        }
    }

    public void setCellData(String sheetName, int rowNum, int colNum, String data) {
        try {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                return; // Sheet not found, do nothing
            }

            XSSFRow row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            XSSFCell cell = row.createCell(colNum);
            cell.setCellValue(data);

            fo = new FileOutputStream(path);
            workbook.write(fo);
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (fo != null) {
                fo.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
