package com.stanford.sleepjournal.utils;

import android.content.Context;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class ExcelManager {

    private Context mContext;
    private String mName = "Annina";
    private String mTA = "Chloe";

    public ExcelManager(Context context){
        this.mContext = context;
    }

    // see https://poi.apache.org/spreadsheet/how-to.html#sxssf
    public void createSheet() throws FileNotFoundException, IOException{
        Log.d(ExcelManager.class.toString(), getWriteLocation());
        FileOutputStream out = new FileOutputStream(getWriteLocation());
        Workbook wb = new HSSFWorkbook();
        Sheet s = wb.createSheet();

        wb.setSheetName(0, "Sleep Journal");

        Row r = s.createRow(0);
        Cell c = r.createCell(0);
        c.setCellValue("Sleep Journal");

        r = s.createRow(2);
        c = r.createCell(0);
        c.setCellValue("Psychiatry 135/235");

        r = s.createRow(3);
        c = r.createCell(0);
        c.setCellValue("Sleep and Dreams Winter 2016");

        r = s.createRow(4);
        c = r.createCell(0);
        c.setCellValue("William C. Dement, M.D, Ph.D.");

        r = s.createRow(6);
        c = r.createCell(0);
        c.setCellValue("Name: " + mName);

        r = s.createRow(7);
        c = r.createCell(0);
        c.setCellValue("Your TA: " + mTA);

        for (int n = 12; n < 57; n++){
            Row rn = s.createRow(n);
            for (int x = 0; x < 11; x++) {
                Cell cn = rn.createCell(0);
            }
        }

        wb.write(out);
        out.close();
    }

    //to be used to change file name depending on date
    private String getWriteLocation(){
        return mContext.getExternalFilesDir(null) + File.separator + "workbook.xls";
    }

}
