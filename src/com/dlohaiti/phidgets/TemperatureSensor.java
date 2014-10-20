package com.dlohaiti.phidgets;

import com.phidgets.PhidgetException;
import com.phidgets.TemperatureSensorPhidget;

public class TemperatureSensor implements PhidgetInterface {
    TemperatureSensorPhidget sensor;

    public TemperatureSensor(String label) {
        try {
            this.sensor = new TemperatureSensorPhidget();
            this.sensor.openLabel(label);
        } catch (PhidgetException e) {
            System.err.println(e.toString());
        }
    }

    public TemperatureSensor(int serial){
        try{
            this.sensor = new TemperatureSensorPhidget();
            this.sensor.open(serial);
        }catch (PhidgetException e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public String readValue() throws PhidgetException {
        return String.valueOf(sensor.getTemperature(0));

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
