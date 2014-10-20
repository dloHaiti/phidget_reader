package com.dlohaiti.phidgets;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

public class PHSensor implements PhidgetInterface {
    private InterfaceKitPhidget sensor;

    public PHSensor(String label) {
        try {
            this.sensor=new InterfaceKitPhidget();
            this.sensor.openLabel(label);
        } catch (PhidgetException e) {
            System.err.println(e.toString());
        }
    }

     public PHSensor(int serial) {
        try {
            this.sensor=new InterfaceKitPhidget();
            this.sensor.open(serial);
        } catch (PhidgetException e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public String readValue() throws PhidgetException {
        int sensorValue = sensor.getSensorValue(0);
        double phValue = 0.0178 * sensorValue - 1.889;
        return String.format("%.2f", phValue) ;

    }

    @Override
    public void Close(){
        try {
            sensor.close();
        } catch (PhidgetException e) {
            System.err.println(e.toString());

        }
    }
}

