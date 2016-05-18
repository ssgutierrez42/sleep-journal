package com.stanford.sleepjournal.utils;

import android.content.Context;
import android.util.Log;

import com.stanford.sleepjournal.Constants;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.TimeFunc;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
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


    public ExcelManager(Context context, List<Day> days){
        this.mContext = context;
        mDays = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        String key = calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);
        Day day = new Day(key);
        day.setAsleep("2:00 AM");
        day.setDayOfWeek("Monday");
        day.setGroggyFor(10);
        day.setNappedFor(20);

        mDays.add(day);

        //mDays = days;
    }


    // see https://poi.apache.org/spreadsheet/how-to.html#sxssf
    public void createSheet() throws IOException{
        Log.d(ExcelManager.class.toString(), getWriteLocation());
        FileOutputStream out = new FileOutputStream(getWriteLocation());
        Workbook wb = new HSSFWorkbook();
        Sheet s = wb.createSheet();

        wb.setSheetName(0, "Sleep Journal");

        //cell style for cells with borders
        CellStyle cs = wb.createCellStyle();
        cs.setBorderBottom(cs.BORDER_THIN);
        cs.setBorderLeft(cs.BORDER_THIN);
        cs.setBorderRight(cs.BORDER_THIN);
        cs.setBorderTop(cs.BORDER_THIN);
        Font f = wb.createFont();
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cs.setFont(f);

        //basic cell for time just with border
        CellStyle csTime = wb.createCellStyle();
        csTime.setBorderBottom(csTime.BORDER_THIN);
        csTime.setBorderLeft(csTime.BORDER_THIN);
        csTime.setBorderRight(csTime.BORDER_THIN);
        csTime.setBorderTop(csTime.BORDER_THIN);
        DataFormat df = wb.createDataFormat();
        csTime.setDataFormat(df.getFormat("[h]:mm;@"));

        //basic cell for alertness
        CellStyle csAlert = wb.createCellStyle();
        csAlert.setBorderBottom(csAlert.BORDER_THIN);
        csAlert.setBorderLeft(csAlert.BORDER_THIN);
        csAlert.setBorderRight(csAlert.BORDER_THIN);
        csAlert.setBorderTop(csAlert.BORDER_THIN);
        csAlert.setDataFormat(csAlert.getDataFormat());
        DataFormat dfA = wb.createDataFormat();
        csAlert.setDataFormat(dfA.getFormat("00"));

        //cell style for cells that are blue text, bold and grey background
        CellStyle csb = wb.createCellStyle();
        csb.setBorderBottom(cs.BORDER_THIN);
        csb.setBorderLeft(cs.BORDER_THIN);
        csb.setBorderRight(cs.BORDER_THIN);
        csb.setBorderTop(cs.BORDER_THIN);
        Font fb = wb.createFont();
        fb.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fb.setColor( (short)0xc );
        csb.setFont(fb);

        //trying to format cells correctly
        int rownum;
        Row row;
        Cell col;
        for (rownum = (short) 0; rownum < 57; rownum++)
        {
            // create a row
            row = s.createRow(rownum);

            //row.setRowNum(( short ) rownum);
            // create 10 cells (0-9)
            for (short cellnum = (short) 0; cellnum < 20; cellnum++){
                // make this column a bit wider
                col = row.createCell(cellnum);
                //set cell type and deal with formulas
                s.setColumnWidth((short) (cellnum), (short) ((50 * 8) / ((double) 1 / 20)));
            }
        }

        row = s.getRow(0);
        col = row.createCell(0);
        col.setCellValue("Sleep Journal");

        row = s.getRow(6);
        col = row.createCell(0);
        col.setCellValue("Name: " + mName);

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
        col.setCellStyle(cs);
        col.setCellValue("A. Day and Date");
        col = row.createCell(1);
        col.setCellStyle(cs);
        col.setCellValue("B. Time in Bed (AM/PM)");
        col = row.createCell(2);
        col.setCellStyle(cs);
        col.setCellValue("C. Fell Asleep At (AM/PM)");
        col = row.createCell(3);
        col.setCellStyle(cs);
        col.setCellValue("D. Awoke at (AM/PM)");
        col = row.createCell(4);
        col.setCellStyle(cs);
        col.setCellValue("E. Time out of Bed (AM/PM)");
        col = row.createCell(5);
        col.setCellStyle(cs);
        col.setCellValue("F. Time awake in middle of night (h)");
        col = row.createCell(6);
        col.setCellStyle(cs);
        col.setCellValue("G. Time awake in bed (h)");
        col = row.createCell(7);
        col.setCellStyle(cs);
        col.setCellValue("H. Time asleep at night (h)");
        col = row.createCell(8);
        col.setCellStyle(cs);
        col.setCellValue("I. Time spent napping (h)");
        col = row.createCell(9);
        col.setCellStyle(cs);
        col.setCellValue("J. Total Time Asleep [H + I] (h)");
        col = row.createCell(10);
        col.setCellStyle(cs);
        col.setCellValue("K. Extent of morning grogginess (min)");

        //setting repeating titles for second row of info
        row = s.getRow(24);
        col = row.createCell(0);
        col.setCellStyle(cs);
        col.setCellValue("A. Day and Date");
        col = row.createCell(1);
        col.setCellStyle(cs);
        col.setCellValue("L. Level of alertness 12AM-2AM (1-6)");
        col = row.createCell(2);
        col.setCellStyle(cs);
        col.setCellValue("M. Level of alertness 2AM-4AM (1-6)");
        col = row.createCell(3);
        col.setCellStyle(cs);
        col.setCellValue("N. Level of alertness 4AM-6AM (1-6)");
        col = row.createCell(4);
        col.setCellStyle(cs);
        col.setCellValue("O. Level of alertness 6AM-8AM (1-6)");
        col = row.createCell(5);
        col.setCellStyle(cs);
        col.setCellValue("P. Level of alertness 8AM-10AM (1-6)");
        col = row.createCell(6);
        col.setCellStyle(cs);
        col.setCellValue("Q. Level of alertness 10AM-12PM (1-6)");
        col = row.createCell(7);
        col.setCellStyle(cs);
        col.setCellValue("R. Level of alertness 12PM-2PM (1-6)");

        //setting repeating titles for third row of info
        row = s.getRow(35);
        col = row.createCell(0);
        col.setCellStyle(cs);
        col.setCellValue("A. Day and Date");
        col = row.createCell(1);
        col.setCellStyle(cs);
        col.setCellValue("S. Level of alertness 2PM-4PM (1-6)");
        col = row.createCell(2);
        col.setCellStyle(cs);
        col.setCellValue("T. Level of alertness 4PM-6PM (1-6)");
        col = row.createCell(3);
        col.setCellStyle(cs);
        col.setCellValue("U. Level of alertness 6PM-8PM (1-6)");
        col = row.createCell(4);
        col.setCellStyle(cs);
        col.setCellValue("V. Level of alertness 8PM-10PM (1-6)");
        col = row.createCell(5);
        col.setCellStyle(cs);
        col.setCellValue("W. Level of alertness 10PM-12AM (1-6)");
        col = row.createCell(6);
        col.setCellStyle(cs);
        col.setCellValue("X. Time of optimal alertness (AM/PM)");
        col = row.createCell(7);
        col.setCellStyle(cs);
        col.setCellValue("Y. How you felt overall today (1-10)");

        //setting repeating titles for last row of info
        row = s.getRow(48);
        col = row.createCell(0);
        col.setCellStyle(cs);
        col.setCellValue("A. Day and Date");
        col = row.createCell(1);
        col.setCellStyle(cs);
        col.setCellValue("AA. Circumstances/Events/Activities/Consumption and Time of Day");
        col = row.createCell(3);
        col.setCellStyle(cs);
        col.setCellValue("AB. Number of Dreams per Night. N for nightmare/anxiety, L for lucid");

        // filling in first section of info for all days
        for (int n = 0; n < mDays.size(); n++){
            Day today = mDays.get(n);
            int rowNum = 13 + n;
            row = s.getRow(rowNum);
            col = row.createCell(0);
            col.setCellStyle(csTime);
            col.setCellValue(today.getDayOfWeek() + " " + today.getDate());
            col = row.createCell(1);
            col.setCellStyle(csTime);
            col.setCellValue(today.getInBed());
            col = row.createCell(2);
            col.setCellStyle(csTime);
            col.setCellValue(today.getAsleep());
            col = row.createCell(3);
            col.setCellStyle(csTime);
            col.setCellValue(today.getAwake());
            col = row.createCell(4);
            col.setCellStyle(csTime);
            col.setCellValue(today.getOutOfBed());
            col = row.createCell(5);
            col.setCellStyle(csTime);
            col.setCellValue(today.getTimeAwakeAtNight());
            col = row.createCell(6);
            col.setCellStyle(csTime);
            col.setCellValue("FIX");
            col = row.createCell(7);
            col.setCellStyle(csTime);
            col.setCellValue("FIX");
            col = row.createCell(8);
            col.setCellStyle(csTime);
            col.setCellValue(today.getNappedFor());
            col = row.createCell(9);
            col.setCellStyle(csAlert);
            col.setCellValue("FIX");
            col = row.createCell(10);
            col.setCellStyle(csAlert);
            col.setCellValue(today.getGroggyFor());
        }

        // filling in second section of info for all days
        for (int n = 0; n < mDays.size(); n++){
            Day today = mDays.get(n);
            int rowNum = 25 + n;
            row = s.getRow(rowNum);
            List<AlertnessEntry> alert = today.getAlertness();
            col = row.createCell(0);
            col.setCellStyle(cs);
            col.setCellValue(today.getDayOfWeek() + " " + today.getDate());
            for (int num = 0; num < 7; num++) {
                col = row.createCell(num+1);
                col.setCellStyle(csAlert);
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
            col.setCellStyle(cs);
            Log.d(ExcelManager.class.toString(), col.toString() + "- CELL - " + col.getRowIndex());
            col.setCellValue(today.getDayOfWeek() + " " + today.getDate());
            for (int num = 1; num < 6; num++) {
                col = row.getCell(num);
                col.setCellStyle(csAlert);
                for (int a = 0; a < alert.size(); a++){
                    String hour = alert.get(a).getHour();
                    if (Constants.HOURS[num+6].equals(hour)){
                        col.setCellValue(alert.get(a).getMood());
                        break;
                    }
                }
            }
            col = row.getCell(6);
            col.setCellStyle(csTime);
            col.setCellValue("FIX");
            col = row.getCell(7);
            col.setCellStyle(csTime);
            col.setCellValue("FIX");
        }

        // last section
        for (int n = 0; n < mDays.size(); n++){
            Day today = mDays.get(n);
            int rowNum = 49;
            row = s.getRow(rowNum);
            col = row.getCell(0);
            col.setCellStyle(csTime);
            col.setCellValue(today.getDayOfWeek() + " " + today.getDate());
            rowNum++;
        }

        // averages and shit
        row = s.getRow(20);
        col = row.getCell(0);
        col.setCellStyle(csb);
        col.setCellValue("Weekly Averages (B-K)");
        col = row.getCell(1);
        col.setCellStyle(csb);
        col.setCellValue("=IF(N4<N3,N3+N4,N4)");
        col = row.getCell(2);
        col.setCellStyle(csb);
        col.setCellValue("=IF(O4<O3,O3+O4,O4)");
        col = row.getCell(3);
        col.setCellStyle(csb);
        col.setCellValue("=AVERAGE(D14:D20)");
        col = row.getCell(4);
        col.setCellStyle(csb);
        col.setCellValue("=AVERAGE(E14:E20)");
        col = row.getCell(5);
        col.setCellStyle(csb);
        col.setCellValue("=AVERAGE(F14:F20)");
        col = row.getCell(6);
        col.setCellStyle(csb);
        col.setCellValue("=(SUMIF(H14:H20,\">0\",G14:G20))/(COUNTIF(H14:H20,\">0\"))");
        col = row.getCell(7);
        col.setCellStyle(csb);
        col.setCellValue("=(SUMIF(H14:H20,\">0\",H14:H20))/(COUNTIF(H14:H20,\">0\")");
        col = row.getCell(8);
        col.setCellStyle(csb);
        col.setCellValue("=AVERAGE(I14:I20)");
        col = row.getCell(9);
        col.setCellStyle(csb);
        col.setCellValue("=(SUMIF(J14:J20,\">0\",J14:J20))/(COUNTIF(J14:J20,\">0\"))");
        col = row.getCell(8);
        col.setCellStyle(csb);
        col.setCellValue("=AVERAGE(K14:K20)");

        wb.write(out);
        out.close();
    }

    //to be used to change file name depending on date
    private String getWriteLocation(){
        return mContext.getExternalFilesDir(null) + File.separator + "workbook.xls";
    }

}
