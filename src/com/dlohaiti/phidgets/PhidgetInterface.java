package com.dlohaiti.phidgets;

import com.phidgets.PhidgetException;

public interface PhidgetInterface {

    public String readValue() throws PhidgetException;

    void Close();
}
