package model;

import classes.*;
import java.io.*;
import java.util.*;

public class ToysModel {
    private List<Toy> toys; 
    private String fnameToys; 

    public ToysModel() {
        fnameToys = "./db/toys.csv";
    }

    public void add(Toy rec) {
        
        toys.add(rec);
    }

    public boolean deleteById(int curId) {
        
        for (Toy item : toys) {
            if (item.getId() == curId) {
                toys.remove(item);
                System.out.println("Игрушка с id=" + curId + " успешно удалена.");
                return true;
            }
        }
        System.out.println("Игрушка с id=" + curId + " не найдена. Удаление не выполнено!");
        return false;
    }

    public boolean save() {
       
        try {
            FileWriter fr1 = new FileWriter(fnameToys);
           
            fr1.append("id|name|count|price|weight\n");
          
            for (Toy item : toys) {
                fr1.append(item.getId() + "|" +
                        item.getName() + "|" +
                        item.getCount() + "|" +
                        item.getPrice() + "|" +
                        item.getWeight() + "\n");
            }
            fr1.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean load() {
        
        toys = new LinkedList<>();
        
        try (FileReader fr = new FileReader(fnameToys)) {
            Scanner scanner = new Scanner(fr);
            int i = 0; 
            while (scanner.hasNextLine()) {
               
                String curRow = scanner.nextLine();
                if (i > 0) {
                    
                    String[] fields = curRow.split("\\|");
                    if (fields.length != 5) {
                        throw new Exception("В исходном файле ошибка в строке " + i
                                + ". Количество полей не равно 5.");
                    }
                    
                    int curId = Integer.parseInt(fields[0].trim());
                    String curName = fields[1].trim();
                    int curCount = Integer.parseInt(fields[2].trim());
                    float curPrice = Float.parseFloat(fields[3].trim());
                    int curWeight = Integer.parseInt(fields[4].trim());

                    Toy curToy = new Toy(curId, curName, curCount,
                            curPrice, curWeight);
                    toys.add(curToy);
                }
                i++;
            }
            scanner.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    
    public List<Toy> getToysAll() {
        return toys;
    }

    public int getNewId() {
        int maxId = -1;
        for (Toy item : toys) {
            if (item.getId() > maxId)
                maxId = item.getId();
        }
        return maxId + 1;
    }

    public Toy getToyById(int curToyId) {
        for (Toy item : toys) {
            if (item.getId() == curToyId)
                return item;
        }
        return null;
    }

    public Toy getRandomToyByWeight() {
        
        
        if (toys.size() == 0) {
            System.out.println("Нет игрушек!");
            return null;
        }

        int SumWt = 0;
        List<Toy> selToys = new LinkedList<>();
        
        for (Toy item : toys) {
            if (item.getCount() > 0) {
                selToys.add(item);
                SumWt += item.getWeight(); 
            }
        }

        if (selToys.size() == 0) {
            System.out.println("Игрушки для выдачи призов закончились!");
            return null;
        }

        
        int RndWt = new Random().nextInt(SumWt + 1);
        
        SumWt=0; 
        for (Toy item : selToys) {
            SumWt += item.getWeight(); 
            if (SumWt >= RndWt) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String res = "";
        for (Toy item : toys) {
            res += item.toString();
        }
        return "Таблица игрушек\n---------------\n: " + res;
    }

}
