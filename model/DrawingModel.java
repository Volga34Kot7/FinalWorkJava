package model;

import classes.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DrawingModel {
    private String fnamePrizesToAward; 
    private String fnamePrizesAwarded; 
    DateTimeFormatter formatter; 
    private Drawing drw;   

    public DrawingModel() {
        fnamePrizesToAward = "./db/prizestoaward.csv";
        fnamePrizesAwarded = "./db/prizesawarded.csv";
        drw = new Drawing();
        formatter = DateTimeFormatter.ofPattern(
            "dd.MM.yyyy HH:mm");
    }

    public boolean loadPrizesToAward() {
        
        List<Prize> prizesToAward = new LinkedList<>();
       
        try (FileReader fr = new FileReader(fnamePrizesToAward)) {
            Scanner scanner = new Scanner(fr);
            int i = 0; 
            while (scanner.hasNextLine()) {
               
                String curRow = scanner.nextLine();
                if (i > 0) {
                    
                    String[] fields = curRow.split("\\|");
                    if (fields.length != 3) {
                        throw new Exception("В исходном файле ошибка в строке " + i 
                                    + ". Количество полей не равно 3.");
                    }
                    
                    int curId = Integer.parseInt(fields[0].trim());
                    String[] buFields = fields[1].trim().split(";");
                    Buyer curBuyer = new Buyer(Integer.parseInt(buFields[0].trim()),
                                                buFields[1].trim(),
                                                buFields[2].trim(),
                                                buFields[3].trim());
                    String[] toFields = fields[2].trim().split(";");
                    
                    Toy curToy = new Toy(Integer.parseInt(toFields[0].trim()),
                                        toFields[1].trim(),
                                        0,
                                        Float.parseFloat(toFields[2].trim()),
                                        0);
                    Prize curPrize = new Prize(curId, curBuyer, curToy);
                    prizesToAward.add(curPrize);
                }
                i++;
            }
            drw.setPrizesToAward(prizesToAward);
            scanner.close();
        } catch (Exception ex) {
            System.out.println("Ошибка при загрузке списка призов для вручения.\n" + ex.toString());
            return false;
        }
        return true;
    }

    public boolean loadPrizesAwarded() {
        
        
        List<PrizeAwarded> prizesAwarded = new LinkedList<>();
        
        try (FileReader fr = new FileReader(fnamePrizesAwarded)) {
            Scanner scanner = new Scanner(fr);
            int i = 0; 
            while (scanner.hasNextLine()) {
              
                String curRow = scanner.nextLine();
                if (i > 0) {
                   
                    String[] fields = curRow.split("\\|");
                    if (fields.length != 4) {
                        throw new Exception("В исходном файле ошибка в строке " + i 
                                    + ". Количество полей не равно 4.");
                    }
                    
                    int curId = Integer.parseInt(fields[0].trim());
                    String[] buFields = fields[1].trim().split(";");
                    Buyer curBuyer = new Buyer(Integer.parseInt(buFields[0].trim()),
                                                buFields[1].trim(),
                                                buFields[2].trim(),
                                                buFields[3].trim());
                    String[] toFields = fields[2].trim().split(";");
                    
                    Toy curToy = new Toy(Integer.parseInt(toFields[0].trim()),
                                        toFields[1].trim(),
                                        0,
                                        Float.parseFloat(toFields[2].trim()),
                                        0);
                    
                    LocalDateTime curDate = LocalDateTime.parse(fields[3].trim(), formatter);
                    PrizeAwarded curPrize = new PrizeAwarded(curId, curBuyer, curToy, curDate);
                    prizesAwarded.add(curPrize);
                }
                i++;
            }
            drw.setPrizesAwarded(prizesAwarded);
            scanner.close();
        } catch (Exception ex) {
            System.out.println("Ошибка при загрузке таблицы-Призы врученные.\n" + ex.toString());
            return false;
        }
        return true;
    }

    public void ShowTablePrizesToAward() {
       
        String s1 = "Таблица-Разыгранные призы";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        for (Prize item : drw.getPrizesToAward()) {
            System.out.println(item.toString());
        }
    }

    public void ShowTablePrizesAwarded() {

        String s1 = "Таблица-Врученные призы";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        for (PrizeAwarded item : drw.getPrizesAwarded()) {
            System.out.println(item.toString());
        }
    }    
    
    public boolean PrizeToAwardSetAsAwarded(Prize curPrize, LocalDateTime curDate) {
        int curID = curPrize.getId();
        this.loadPrizesAwarded();
        List<PrizeAwarded> prizesAwarded = drw.getPrizesAwarded();
        int newId = getPrizesAwardedNewId();

        PrizeAwarded newPrizeAwarded = new PrizeAwarded(newId,
                                                    curPrize.getBuyer(),
                                                    curPrize.getToy(),
                                                    curDate);

        if (!prizesAwarded.add(newPrizeAwarded)) {
            System.out.print("Ошибка при добавлении врученного приза!");
            return false;
        }

        
        List<Prize> prizesToAward = drw.getPrizesToAward();
        for (Prize item : prizesToAward) {
            if (item.getId() == curID) {
                prizesToAward.remove(item);
                break;
            }
        }

        drw.setPrizesAwarded(prizesAwarded);
        savePrizesAwarded();

        drw.setPrizesToAward(prizesToAward);
        savePrizesToAward();

        System.out.println("Успешно изменен статус приза на вручен. id врученного приза=" + newId + ".");    
        return true;
    }

    public boolean PrizesToAwardAddNew() {
        

        loadPrizesToAward(); 

        ToysModel toysModel = new ToysModel();
        if (!toysModel.load()) {
            return false;
        }
        
        Toy RandomToy = toysModel.getRandomToyByWeight();
        if (RandomToy == null) {
            System.out.println("Ошибка. Игрушка для приза не выбрана!");
            return false;
        }
        
        
        BuyersModel buyersModel = new BuyersModel();
        if (!buyersModel.load()) {
            return false;
        }
        List<Buyer> buyersAll = buyersModel.getBuyersAll();
        List<Buyer> buyersNotAwarded = new LinkedList<>();
        for (Buyer buyer : buyersAll) {
            boolean isPresent = false;
            for (Prize prize : drw.getPrizesToAward()) {
                if (buyer.getId() == prize.getBuyer().getId()) {
                    isPresent = true;
                    break;
                }
            }
        
            if (!isPresent) {
                buyersNotAwarded.add(buyer);
            }
        }

        if (buyersNotAwarded.size()==0) {
            System.out.println("Приз не может быть выбран. Больше нет покупателей без призов!");
            return false;
        }

        
        int RndNumber = new Random().nextInt(buyersNotAwarded.size());
        Buyer RandomBuyer = buyersNotAwarded.get(RndNumber);       
        int NewId = getPrizesToAwardNewId();
        Prize newPrize = new Prize(NewId, RandomBuyer, RandomToy);
        drw.getPrizesToAward().add(newPrize);
        if (!savePrizesToAward()) {
            System.out.println("Ошибка при сохранении списка призов для вручения!");
            return false;
        }
        
        RandomToy.setCount(RandomToy.getCount()-1);
        
        toysModel.save();

        System.out.println("Новый приз успешно разыгран! id=" + NewId + ". Смотрите таблицу разыгранных призов.");
        return true;
    }

    public boolean savePrizesToAward() {
        
        try {
            FileWriter fr1 = new FileWriter(fnamePrizesToAward);
            
            fr1.append("id|Buyer=id;fullName;checkNumber;phone|Toy=id;name;price\n");
            
            for (Prize item : drw.getPrizesToAward()) {
                fr1.append(item.getId() + "|" +
                        item.getBuyer().toSavePrize() + "|" +
                        item.getToy().toSavePrize() + "\n");
            }
            fr1.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean savePrizesAwarded() {
        
        try {
            FileWriter fr1 = new FileWriter(fnamePrizesAwarded);
        
            fr1.append("id|Buyer=id;fullName;checkNumber;phone|Toy=id;name;price|dateAward\n");
           
            for (PrizeAwarded item : drw.getPrizesAwarded()) {
                fr1.append(item.getId() + "|" +
                        item.getBuyer().toSavePrize() + "|" +
                        item.getToy().toSavePrize() + "|" +
                        item.getDateAwardString() + "\n");
            }
            fr1.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public int getPrizesToAwardNewId() {
        int maxId = -1;
        for (Prize item : drw.getPrizesToAward()) {
            if (item.getId() > maxId)
                maxId = item.getId();
        }
        return maxId + 1;
    }

    public int getPrizesAwardedNewId() {
        int maxId = -1;
        for (Prize item : drw.getPrizesAwarded()) {
            if (item.getId() > maxId)
                maxId = item.getId();
        }
        return maxId + 1;
    }

    public Prize getPrizeToAwardById(int curPrizeId) {
        for (Prize item : drw.getPrizesToAward()) {
            if (item.getId() == curPrizeId)
                return item;
        }
        return null;
    }

}

