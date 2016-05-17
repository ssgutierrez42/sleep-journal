package com.stanford.sleepjournal.utils;

import android.content.Context;
import android.util.Log;

import com.stanford.sleepjournal.Constants;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class ExcelManager {

    private Context mContext;
    private String mName = "Annina";
    private String mTA = "Chloe";
    private List<Day> mDays;


    public ExcelManager(Context context){
        this.mContext = context;
        mDays = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        String key = calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);
        Day day = new Day(key);
        day.setAsleep("2:00 AM");
        day.setGroggyFor(10);

        mDays.add(day);
    }


    // see https://poi.apache.org/spreadsheet/how-to.html#sxssf
    public void createSheet() throws FileNotFoundException, IOException{
        Log.d(ExcelManager.class.toString(), getWriteLocation());
        FileOutputStream out = new FileOutputStream(getWriteLocation());
        Workbook wb = new HSSFWorkbook();
        Sheet s = wb.createSheet();

        wb.setSheetName(0, "Sleep Journal");

        //trying to format cells correctly
        int rownum;
        Row row;
        Cell col;
        for (rownum = (short) 0; rownum < 57; rownum++)
        {
            // create a row
            row = s.createRow(rownum);

            //row.setRowNum(( short ) rownum);
            // create 10 cells (0-9) (the += 2 becomes apparent later
            for (short cellnum = (short) 0; cellnum < 20; cellnum++){
                // make this column a bit wider
                row.createCell(cellnum);
                s.setColumnWidth((short) (cellnum), (short) ((50 * 8) / ((double) 1 / 20)));
            }
        }

        row = s.getRow(0);
        col = row.createCell(0);
        col.setCellValue("Sleep Journal");

        row = s.getRow(2);
        col = row.createCell(0);
        col.setCellValue("Psychiatry 135/235");

        row = s.getRow(3);
        col = row.createCell(0);
        col.setCellValue("Sleep and Dreams Winter 2016");

        row = s.getRow(4);
        col = row.createCell(0);
        col.setCellValue("William C. Dement, M.D, Ph.D.");

        row = s.getRow(6);
        col = row.createCell(0);
        col.setCellValue("Name: " + mName);

        row = s.getRow(7);
        col = row.createCell(0);
        col.setCellValue("Your TA: " + mTA);

        row = s.getRow(11);
        col = row.createCell(0);
        col.setCellValue("SLEEP DATA");

        row = s.getRow(23);
        col = row.createCell(0);
        col.setCellValue("ALERTNESS DATA");

        row = s.getRow(47);
        col = row.createCell(0);
        col.setCellValue("Sleep Altering Factors and Dreams:");

        // setting the repeating titles for first row of info
        row = s.getRow(12);
        col = row.createCell(0);
        col.setCellValue("A. Day and Date");
        col = row.createCell(1);
        col.setCellValue("B. Time in Bed (AM/PM)");
        col = row.createCell(2);
        col.setCellValue("C. Fell Asleep At (AM/PM)");
        col = row.createCell(3);
        col.setCellValue("D. Awoke at (AM/PM)");
        col = row.createCell(4);
        col.setCellValue("E. Time out of Bed (AM/PM)");
        col = row.createCell(5);
        col.setCellValue("F. Time awake in middle of night (h)");
        col = row.createCell(6);
        col.setCellValue("G. Time awake in bed (h)");
        col = row.createCell(7);
        col.setCellValue("H. Time asleep at night (h)");
        col = row.createCell(7);
        col.setCellValue("I. Time spent napping (h)");
        col = row.createCell(8);
        col.setCellValue("J. Total Time Asleep [H + I] (h)");
        col = row.createCell(9);
        col.setCellValue("K. Extent of morning grogginess (min)");

        //setting repeating titles for second row of info
        row = s.getRow(24);
        col = row.createCell(0);
        col.setCellValue("A. Day and Date");
        col = row.createCell(1);
        col.setCellValue("L. Level of alertness 12AM-2AM (1-6)");
        col = row.createCell(2);
        col.setCellValue("M. Level of alertness 2AM-4AM (1-6)");
        col = row.createCell(3);
        col.setCellValue("N. Level of alertness 4AM-6AM (1-6)");
        col = row.createCell(4);
        col.setCellValue("O. Level of alertness 6AM-8AM (1-6)");
        col = row.createCell(5);
        col.setCellValue("P. Level of alertness 8AM-10AM (1-6)");
        col = row.createCell(6);
        col.setCellValue("Q. Level of alertness 10AM-12PM (1-6)");
        col = row.createCell(7);
        col.setCellValue("R. Level of alertness 12PM-2PM (1-6)");

        //setting repeating titles for third row of info
        row = s.getRow(35);
        col = row.createCell(0);
        col.setCellValue("A. Day and Date");
        col = row.createCell(1);
        col.setCellValue("S. Level of alertness 2PM-4PM (1-6)");
        col = row.createCell(2);
        col.setCellValue("T. Level of alertness 4PM-6PM (1-6)");
        col = row.createCell(3);
        col.setCellValue("U. Level of alertness 6PM-8PM (1-6)");
        col = row.createCell(4);
        col.setCellValue("V. Level of alertness 8PM-10PM (1-6)");
        col = row.createCell(5);
        col.setCellValue("W. Level of alertness 10PM-12AM (1-6)");
        col = row.createCell(6);
        col.setCellValue("X. Time of optimal alertness (AM/PM)");
        col = row.createCell(7);
        col.setCellValue("Y. How you felt overall today (1-10)");

        //setting repeating titles for last row of info
        row = s.getRow(48);
        col = row.createCell(0);
        col.setCellValue("A. Day and Date");
        col = row.createCell(1);
        col.setCellValue("AA. Circumstances/Events/Activities/Consumption and Time of Day");
        col = row.createCell(3);
        col.setCellValue("AB. Number of Dreams per Night. N for nightmare/anxiety, L for lucid");

        // filling in first section of info for all days
        for (int n = 0; n < mDays.size(); n++){
            Day today = mDays.get(n);
            int rowNum = 13 + n;
            row = s.getRow(rowNum);
            col = row.createCell(0);
            col.setCellValue(today.getDate());
            col = row.createCell(1);
            col.setCellValue(today.getInBed());
            col = row.createCell(2);
            col.setCellValue(today.getAsleep());
            col = row.createCell(3);
            col.setCellValue(today.getAwake());
            col = row.createCell(4);
            col.setCellValue(today.getOutOfBed());
            col = row.createCell(5);
            col.setCellValue(today.getTimeAwakeAtNight());
            col = row.createCell(6);
            col.setCellValue("FIX");
            col = row.createCell(7);
            col.setCellValue("FIX");
            col = row.createCell(8);
            col.setCellValue(today.getNappedFor());
            col = row.createCell(9);
            col.setCellValue("FIX");
            col = row.createCell(10);
            col.setCellValue(today.getGroggyFor());

        }

        // filling in second section of info for all days
        for (int n = 0; n < mDays.size(); n++){
            Day today = mDays.get(n);
            int rowNum = 25 + n;
            row = s.getRow(rowNum);
            List<AlertnessEntry> alert = today.getAlertness();
            // DELETE LATER

            AlertnessEntry test = new AlertnessEntry(today.getDate(), Constants.HOURS[0]);
            test.setMood(3);

            alert.add(test);

            //
            col = row.createCell(0);
            col.setCellValue(today.getDate());
            for (int num = 0; num < 7; num++) {
                col = row.createCell(num+1);
                for (int a = 0; a < alert.size(); a++){
                    String hour = alert.get(a).getHour();
                    if (Constants.HOURS[num].equals(hour)){
                        col.setCellValue(alert.get(a).getMood());
                        break;
                    }
                }
            }
        }

//        // filling in third section of info for all days
        for (int n = 0; n < mDays.size(); n++){
            Day today = mDays.get(n);
            int rowNum = 36 + n;
            row = s.getRow(rowNum);
            List<AlertnessEntry> alert = today.getAlertness();
            col = row.getCell(0);
            Log.d(ExcelManager.class.toString(), col.toString() + "- CELL - " + col.getRowIndex());
            col.setCellValue(today.getDate());
            for (int num = 1; num < 6; num++) {
                col = row.getCell(num);
                for (int a = 0; a < alert.size(); a++){
                    String hour = alert.get(a).getHour();
                    if (Constants.HOURS[num+6].equals(hour)){
                        col.setCellValue(alert.get(a).getMood());
                        break;
                    }
                }
            }
            col = row.getCell(6);
            col.setCellValue("FIX");
            col = row.getCell(7);
            col.setCellValue("FIX");
        }

        wb.write(out);
        out.close();
    }

    //to be used to change file name depending on date
    private String getWriteLocation(){
        return mContext.getExternalFilesDir(null) + File.separator + "workbook.xls";
    }

}
