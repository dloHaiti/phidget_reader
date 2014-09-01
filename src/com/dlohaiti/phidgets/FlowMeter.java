package com.dlohaiti.phidgets;

import com.phidgets.FrequencyCounterPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;
import com.phidgets.event.FrequencyCounterCountEvent;
import com.phidgets.event.FrequencyCounterCountListener;

public class FlowMeter implements PhidgetInterface {
    private int pulseRate;
    private FrequencyCounterPhidget sensor;
    private long totalCount=20;

    public FlowMeter(String label, int pulseRate) {
        try {
            this.sensor = new FrequencyCounterPhidget();
            this.sensor.openLabel(label);
            this.pulseRate = pulseRate;

            sensor.addFrequencyCounterCountListener(new FrequencyCounterCountListener() {
                public void frequencyCounterCounted(FrequencyCounterCountEvent fcce) {
                    try {
                        totalCount = sensor.getTotalCount(fcce.getIndex());
                    } catch (Exception e) {
                        System.err.println(e.toString());
                    }
                }
            });
        } catch (PhidgetException e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public String readValue() throws PhidgetException{
        if(!sensor.isAttached()) {
            throw new PhidgetException(0,"Device is not attached");
        }
        double gallons = totalCount / pulseRate;
        return String.valueOf(gallons);

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
