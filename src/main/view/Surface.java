package main.view;

import java.util.ArrayList;

public class Surface {

    String name;
    
    public ArrayList<Panel> panels;

    public Surface() {}

    public void setName(String s){
        this.name = s;
    }

    public String getName() {
        return name;
    }
}