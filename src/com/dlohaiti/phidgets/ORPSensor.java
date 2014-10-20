package com.dlohaiti.phidgets;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

/**
 * Created by jijeshm on 29/08/14.
 */
public class ORPSensor implements PhidgetInterface {
    private InterfaceKitPhidget sensor;

    public ORPSensor(String label) {
        try {
            this.sensor = new InterfaceKitPhidget();
            this.sensor.openLabel(label);
        } catch (PhidgetException e) {
            System.err.println(e.toString());
        }
    }

     public ORPSensor(int serial) {
        try {
            this.sensor = new InterfaceKitPhidget();
            this.sensor.open(serial);
        } catch (PhidgetException e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public String readValue() throws PhidgetException {
        int sensorValue = sensor.getSensorValue(0);
        double phValue = 0.0178 * sensorValue - 1.889;
        return String.format("%.2f", phValue);

    }

    @Override
    public void Close() {
        try {
            sensor.close();
        } catch (PhidgetException e) {
            System.err.println(e.toString());
        }
    }

}
