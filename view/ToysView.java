package view;

import java.util.*;

import classes.*;

public class ToysView {
    List<Toy> toys;
    
    public ToysView(List<Toy> toys) {
        this.toys = toys;
    }

    public void ShowTable() {
        
        System.out.println("\nТаблица Игрушки\n---------------");
        for (Toy item : toys) {
            System.out.println(item.toString());
        }
    }

}
