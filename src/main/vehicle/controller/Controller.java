package main.vehicle.controller;

import java.util.Map;

public abstract class Controller {

    public Map<String,Object> options;

    public Controller() {}

    public void setOptions(Map<String,Object> options) {
        this.options = options;
    }
    
}