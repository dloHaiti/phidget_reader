package com.dlohaiti.phidgets;

import com.phidgets.PhidgetException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class PhidgetsReader extends Thread{
    HashMap<String,PhidgetInterface> senors = new HashMap<String, PhidgetInterface>();
    String ftpPath;
    long timeInMinutes=60;

    public PhidgetsReader(Properties properties) {
        super("PhidgetsReader");
        addTemperatureSensors(properties);
        addPHSensors(properties);
        addORPSensors(properties);
        addFlowMeters(properties);

        ftpPath=properties.getProperty("file.path");
        timeInMinutes = Long.parseLong(properties.getProperty("time.interval","60"));
        this.start();

    }

    private void addFlowMeters(Properties properties) {
        String[] flowMeters = properties.getProperty("flow_meter.labels", "").split(",");
        for(String label :flowMeters){
            if(label.isEmpty()){
                continue;
            }
            int serial = Integer.parseInt(label);
            int pluseRate=Integer.parseInt(properties.getProperty("flow_meter." + label + ".pulserate", "48"));
            senors.put(label, new FlowMeter(serial, pluseRate));
        }
    }

    private void addORPSensors(Properties properties) {
        String[] orpSensors = properties.getProperty("orp.labels", "").split(",");
        for(String label :orpSensors){
            if(label.isEmpty()){
                continue;
            }
            int serial = Integer.parseInt(label);
            senors.put(label, new ORPSensor(serial));
        }
    }

    private void addPHSensors(Properties properties) {
        String[] pHSensors = properties.getProperty("ph.labels", "").split(",");
        for(String label :pHSensors){
            if(label.isEmpty()){
                continue;
            }
            int serial = Integer.parseInt(label);
            senors.put(label, new PHSensor(serial));
        }
    }

    private void addTemperatureSensors(Properties properties) {
        String[] temperatureSensors = properties.getProperty("temperature.labels", "").split(",");
        for(String label :temperatureSensors){
            if(label.isEmpty()){
                continue;
            }
            int serial = Integer.parseInt(label);
            senors.put(label, new TemperatureSensor(serial));
        }
    }

    public void run(){
        while(true){
            if (senors.size()==0){
                System.out.println("There are no sensors available, please check property file");
                continue;
            }
            System.out.println("Reading data from sensors");
            CSVFile csvFile = new CSVFile(ftpPath);
            Iterator<String> keySetIterator = senors.keySet().iterator();
            while(keySetIterator.hasNext()){
                String key = keySetIterator.next();
                try {
                    csvFile.addRow(key, ((PhidgetInterface) senors.get(key)).readValue());
                } catch (PhidgetException e) {
                    System.err.println(key +" : " + e.toString());
                }
            }
            csvFile.save();

            try {
                Thread.sleep(timeInMinutes*60*1000);
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }
    }
}
