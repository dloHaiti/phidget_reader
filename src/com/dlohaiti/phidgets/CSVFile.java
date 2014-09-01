package com.dlohaiti.phidgets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVFile {
    private final String filePath;
    private StringBuffer data;
    private String timeStamp;

    public CSVFile(String ftpPath) {
        this.filePath = ftpPath;
        this.data = new StringBuffer("Time,sensor,value\n");
        DateFormat my_output_dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        Date now_date = new Date();
        timeStamp = my_output_dateFormat.format(now_date);
    }

    public void addRow(String sensor, String value) {
        data.append(timeStamp + "," + sensor + "," + value + "\n");
    }

    public void save() {
        Date now_date = new Date();
        String filename = "DLO_Data_" + (now_date.getTime() / 1000) + ".csv";
        try {
            final FileOutputStream datFile = new FileOutputStream((new File(filePath, filename)).toString());
            datFile.write(data.toString().getBytes(), 0, data.toString().length());
            datFile.flush();
            datFile.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        data = null;
    }
}
