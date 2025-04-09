package adactin.utils;


        	import java.io.FileInputStream;
        	import java.util.ArrayList;
        	import java.util.List;

        	import org.apache.poi.ss.usermodel.*;
        	import org.apache.poi.xssf.usermodel.XSSFWorkbook;

        	public class ExcelUtils {

        	    public static Object[][] getPastDates(String filePath, String sheetName) {
        	        return getData("F:\\Software\\AdactinCapstone\\src\\test\\resources\\TestData.xlsx", "Sheet1", 1, 1, 2); 
        	    }

        	    public static Object[][] getUserDetails(String filePath, String sheetName) {
        	        return getData("F:\\Software\\AdactinCapstone\\src\\test\\resources\\TestData.xlsx", "Sheet1", 6, 6, 5); 
        	    }
        	    
        	    public static Object[][] getInvalidUserDetails(String filePath, String sheetName) {
        	        return getData("F:\\Software\\AdactinCapstone\\src\\test\\resources\\TestData.xlsx", "Sheet2", 1, 1, 5); 
        	    }

        	    private static Object[][] getData(String filePath, String sheetName, int startRow, int endRow, int colCount) {
        	        List<Object[]> dataList = new ArrayList<>();

        	        try (FileInputStream fis = new FileInputStream(filePath);
        	             Workbook workbook = new XSSFWorkbook(fis)) {

        	            Sheet sheet = workbook.getSheet(sheetName);

        	            for (int i = startRow; i <= endRow; i++) {
        	                Row row = sheet.getRow(i);
        	                if (row == null || row.getCell(0) == null || row.getCell(0).toString().isEmpty()) {
        	                    continue; // skip empty
        	                }

        	                Object[] rowData = new Object[colCount];
        	                for (int j = 0; j < colCount; j++) {
        	                    Cell cell = row.getCell(j);
        	                    rowData[j] = cell != null ? cell.toString() : "";
        	                }
        	                dataList.add(rowData);
        	            }

        	        } catch (Exception e) {
        	            e.printStackTrace();
        	        }

        	        Object[][] data = new Object[dataList.size()][];
        	        return dataList.toArray(data);
        	    }
        	}
          