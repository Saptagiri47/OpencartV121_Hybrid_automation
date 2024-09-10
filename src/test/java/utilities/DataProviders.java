package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name="LoginData")
    public String[][] getData() throws IOException {
        String path = "C:\\Users\\Sapth\\git\\opencark\\OpencarkV121_Hybrid_automation\\testData\\Opencart_LoginData.xlsx";
        
        ExcelUtility xlUtil = new ExcelUtility(path);
        
        int totalRows = xlUtil.getRowCount("sheet1");
        int totalCols = xlUtil.getCellCount("sheet1", 1); //here 0 or 1 no matter why?????
        
        String[][] logindata = new String[totalRows - 1][totalCols]; // Adjust size for header row
        
        for (int i = 1; i < totalRows; i++) { // Start from 1 to skip header
            for (int j = 0; j < totalCols; j++) {
                logindata[i - 1][j] = xlUtil.getCellData("sheet1", i, j);
            }
        }
        return logindata;
    }
    
    // Placeholder methods for additional DataProviders
    // @DataProvider(name="DataProvider2")
    // public Object[][] getData2() { ... }
    
    // @DataProvider(name="DataProvider3")
    // public Object[][] getData3() { ... }
    
    // @DataProvider(name="DataProvider4")
    // public Object[][] getData4() { ... }
    
    // @DataProvider(name="DataProvider5")
    // public Object[][] getData5() { ... }
}
