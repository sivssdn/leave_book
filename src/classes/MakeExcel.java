package classes;

/**
 * Write to excel file after reading from database
 * Write function input: SQL query to be performed on database with the name of the downloaded file.
 * Output XSSFWorkbook object.
 * The caller has the responsibility to either save or print the output workbook object.
 *
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
    public String errorMessage;
    public MakeExcel(){
        errorMessage="failed";

    }
    public XSSFWorkbook write() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //creating sheets
        XSSFSheet sheet = workbook.createSheet("report");

        DataBase fetch = new DataBase();
        if (fetch.success.intern() == "success") {
            //  ResultSet dataRows=fetch.select("SELECT * FROM master;");

            Row row = sheet.createRow((short) 1);
            Cell cell;
            cell = row.createCell((short) 1);
            cell.setCellValue("Bolo Jaikara");


          /*
            try {


                //downlaod excel file

                FileOutputStream performWrite = new FileOutputStream(new File("C:\\Users\\admin\\IdeaProjects\\LeaveBook\\web\\reports\\trial.xlsx"));
                workbook.write(performWrite);
                performWrite.close();
                errorMessage="success";
            }catch(Exception e){
                errorMessage=e.getMessage();
            }
        }
        */
            fetch.close();
            //return writeOperation;

        }
        return workbook;
    }
}