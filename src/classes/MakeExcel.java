package classes;

/**
 * Created by admin on 19-04-2016.
 */


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;

public class MakeExcel {
    public String write(){
        String writeOperation="failed";
        DataBase fetch=new DataBase();
        if(fetch.success.intern() == "success")
        {
          //  ResultSet dataRows=fetch.select("SELECT * FROM master;");
            XSSFWorkbook workbook=new XSSFWorkbook();
            //creating sheets
            XSSFSheet sheet=workbook.createSheet("report");

            Row row=sheet.createRow((short)1);
            Cell cell;
            cell=row.createCell((short)1);
            cell.setCellValue("Bolo Jaikara");


            try {

                FileOutputStream performWrite = new FileOutputStream(new File("C:\\Users\\admin\\IdeaProjects\\LeaveBook\\web\\reports\\trial.xlsx"));
                workbook.write(performWrite);
                performWrite.close();
                writeOperation="success";
            }catch(Exception e){
                writeOperation=e.getMessage();
            }
        }

        fetch.close();
        return writeOperation;
    }
}
