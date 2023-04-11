import view.*;
import classes.*;
import model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu {
    private String prevPos; 
                            
    private String choice; 
    private String newPos; 
    private boolean ShowNewChoice; 
    private Scanner sc;
    DateTimeFormatter formatter; 

    public void run() {
        showProgramGreeting();

       
        sc = new Scanner(System.in);
        while (true) {
            ShowNewChoice = true;
            
            if (getChoice() != "") {
                if (getPrevPos() != "") {
                    setNewPos((getPrevPos() + "," +
                            getChoice()));
                } else {
                    setNewPos(getChoice());
                }
            } else {
                
                if (getPrevPos() != "") {
                    setNewPos(getPrevPos());
                } else {
                    setNewPos("");
                }
            }
            
            if (getChoice().equals("0")) {
                
                showMainMenu();
            } else if (getChoice().equals("q")) {
             
                showProgramExit();
                return;
            } else {
                
                switch (getNewPos()) {
                    case (""): 
                        showMainMenu();
                        break;

                  
                    case ("1"): 
                        showBuyersMenu();
                        break;

                    case ("1,1"): 
                        BuyersShowTableAll();
                        break;

                    case ("1,2"): 
                        if (BuyerAddNew()) {
                            BuyersShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("1,3"): 
                        if (BuyerEdit()) {
                            BuyersShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("1,4"): 
                        if (BuyerDeleteById()) {
                            BuyersShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                   
                    case ("2"): 
                        showToysMenu();
                        break;

                    case ("2,1"): 
                        ToysShowTableAll();
                        break;

                    case ("2,2"): 
                        if (ToyAddNew() == true) {
                            ToysShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("2,3"): 
                        if (ToyEdit()) {
                            ToysShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("2,4"): 
                        if (ToyDeleteById()) {
                            ToysShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                   
                    case ("3"): 
                        showDrawingMenu();
                        break;

                    case ("3,1"): 
                        PrizesToAwardShowAll();
                        showDrawingMenu();
                        break;

                    case ("3,2"): 
                        if (PrizeAddNew()) {
                            PrizesToAwardShowAll();
                            showDrawingMenu();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("3,3"): 
                        if (PrizeSetAsAwarded()) {
                            PrizesAwardedShowAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("3,4"): 
                        PrizesAwardedShowAll();
                        break;

                    default:
                        showMenuItemNotFound();
                      
                        setChoice("");
                        ShowNewChoice = false;
                        break;
                }
            }
            

            if (ShowNewChoice) {
             
                System.out.printf("Укажите пункт меню: ");
                try {
                    setChoice(sc.nextLine().trim());
                } catch (NoSuchElementException exception) {
                    System.out.println("Пункт меню не выбран");
                    setChoice("");
                }
            }
        }
    }

    public void showProgramGreeting() {
        
        System.out.println();
        String s1 = "Программа - Проведение розыгрыша призов магазина игрушек";
        System.out.println(s1);
        System.out.println("-".repeat(s1.length()));
    }

    public void showMainMenu() {
        
        String s1 = "Главное меню";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        System.out.println("1. Покупатели (участники розыгрыша).");
        System.out.println("2. Игрушки.");
        System.out.println("3. Розыгрыш призов.");
        System.out.println("q. Выход из программы.");
        ResetMenuPos();
    }

    public void showBuyersMenu() {
       
        String s1 = "Меню-Таблица Покупатели (Участники розыгрыша)";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        System.out.println("1. Показать таблицу.");
        System.out.println("2. Добавить покупателя.");
        System.out.println("3. Редактировать покупателя.");
        System.out.println("4. Удалить покупателя.");
        System.out.println("0. Назад в Главное меню.");
        System.out.println("q. Выход.");
        setPrevPos(getNewPos());
    }

    public void showToysMenu() {
       
        String s1 = "Меню-Таблица Игрушки";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        System.out.println("1. Показать таблицу.");
        System.out.println("2. Добавить игрушку.");
        System.out.println("3. Редактировать игрушку.");
        System.out.println("4. Удалить игрушку.");
        System.out.println("0. Назад в Главное меню.");
        System.out.println("q. Выход.");
        setPrevPos(getNewPos());
    }

    public void showDrawingMenu() {
      
        System.out.println("\nМеню-Розыгрыш призов\n----------------");
        System.out.println("1. Показать таблицу-Разыгранные призы.");
        System.out.println("2. Разыграть следующий приз.");
        System.out.println("3. Отметить приз как врученный.");
        System.out.println("4. Показать таблицу-Врученные призы.");
        System.out.println("0. Назад в Главное меню.");
        System.out.println("q. Выход.");
        setPrevPos(getNewPos());
    }

    public void showProgramExit() {
      
        System.out.println();
        System.out.println("Завершение работы программы");
    }

    public void showMenuItemNotFound() {
        System.out.println("Не найден обработчик для указанного пункта меню.");
    }

    

    public void BuyersShowTableAll() {
      
        BuyersModel buyersModel = new BuyersModel();
        if (buyersModel.load()) {
            BuyersView buyersView = new BuyersView(buyersModel.getBuyersAll());
            buyersView.ShowTable();
        }
        ReturnToPrevPos();
        showBuyersMenu();
    }

    public boolean BuyerAddNew() {
        
        BuyersModel buyersModel = new BuyersModel();
        if (!buyersModel.load()) {
            System.out.println("\nФункция добавления покупателя прервана.");
            return false;
        }

        int curId = buyersModel.getNewId();
        
        System.out.println("\nДобавление покупателя. Введите значения полей.");
        System.out.print("ФИО: ");
        try {
            String curFullName = sc.nextLine();
            System.out.print("Номер чека: ");
            String curCheckNumber = sc.nextLine();
            System.out.print("Номер телефона: ");
            String curPhone = sc.nextLine();
            Buyer curBuyer = new Buyer(curId, curFullName, curCheckNumber,
                    curPhone);
            buyersModel.add(curBuyer);
        } catch (Exception ex) {
            System.out.println("Ошибка при вводе данных о покупателе.\n" + ex.toString());
            return false;
        }

        if (buyersModel.save()) {
            System.out.println("Новый покупатель успешно добавлен!");
        } else {
            System.out.println("Ошибка при добавлении нового покупателя.");
            return false;
        }
        return true;
    }

    public boolean BuyerDeleteById() {
       
        BuyersModel buyersModel = new BuyersModel();
        if (buyersModel.load()) {
            BuyersView buyersView = new BuyersView(buyersModel.getBuyersAll());
            buyersView.ShowTable();
        }
        System.out.print("\nВведите id удаляемого покупателя: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            
            if (buyersModel.deleteById(curId)) {
               
                buyersModel.save();
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Ошибка при удалении покупателя.\n" + ex.toString());
            return false;
        }
        return false;
    }

    public boolean BuyerEdit() {
       
        Buyer editedBuyer;
       
        BuyersModel buyersModel = new BuyersModel();
        if (buyersModel.load()) {
            BuyersView buyersView = new BuyersView(buyersModel.getBuyersAll());
            buyersView.ShowTable();
        }
        System.out.print("\nВведите id редактируемого покупателя: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            editedBuyer = buyersModel.getBuyerById(curId);
            String curValue;
            System.out.println("Введите новые значения полей (Enter - оставить прежнее значение).");
           
            System.out.print("ФИО (прежнее значение): " +
                    editedBuyer.getFullName() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedBuyer.setFullName(curValue);

            System.out.print("Номер чека (прежнее значение): " +
                    editedBuyer.getCheckNumber() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedBuyer.setCheckNumber(curValue);

            System.out.print("Номер телефона (прежнее значение): " +
                    editedBuyer.getPhone() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedBuyer.setPhone(curValue);

            
            if (buyersModel.save()) {
                System.out.println("Данные покупателя с id=" + curId + " успешно отредактированы.");
                return true;
            }

        } catch (Exception ex) {
            System.out.println("Ошибка при редактировании данных покупателя.\n" + ex.toString());
            return false;
        }
        return false;
    }

   
    public void ToysShowTableAll() {
        
        ToysModel toysModel = new ToysModel();
        if (toysModel.load()) {
            ToysView toysView = new ToysView(toysModel.getToysAll());
            toysView.ShowTable();
        }

        ReturnToPrevPos(); 
        showToysMenu();
    }

    public boolean ToyAddNew() {
        
        ToysModel toysModel = new ToysModel();
        if (!toysModel.load()) {
            System.out.println("\nФункция добавления игрушки прервана.");
            return false;
        }

        int curId = toysModel.getNewId();
        
        System.out.println("\nДобавление игрушки. Введите значения полей.");
        System.out.print("Название: ");
        try {
            String curName = sc.nextLine();
            System.out.print("Количество: ");
            int curCount = Integer.parseInt(sc.nextLine());
            System.out.print("Цена: ");
            float curPrice = Float.parseFloat(sc.nextLine());
            System.out.print("Вес: ");
            int curWeight = Integer.parseInt(sc.nextLine());
            Toy curToy = new Toy(curId, curName, curCount,
                    curPrice, curWeight);
            toysModel.add(curToy);
        } catch (Exception ex) {
            System.out.println("Ошибка при вводе данных.\n" + ex.toString());
            return false;
        }

        if (toysModel.save()) {
            System.out.println("Новая игрушка успешно добавлена!");
        } else {
            System.out.println("Ошибка при добавлении новой игрушки.");
            return false;
        }
        return true;
    }

    public boolean ToyDeleteById() {
        
        ToysModel toysModel = new ToysModel();
        if (toysModel.load()) {
            ToysView toysView = new ToysView(toysModel.getToysAll());
            toysView.ShowTable();
        }
        System.out.print("\nВведите id удаляемой игрушки: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            
            if (toysModel.deleteById(curId)) {
                
                toysModel.save();
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Ошибка при удалении игрушки.\n" + ex.toString());
            return false;
        }
        return false;
    }

    public boolean ToyEdit() {
        
        Toy editedToy;
        
        ToysModel toysModel = new ToysModel();
        if (toysModel.load()) {
            ToysView toysView = new ToysView(toysModel.getToysAll());
            toysView.ShowTable();
        }
        System.out.print("\nВведите id редактируемой игрушки: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            editedToy = toysModel.getToyById(curId);
            String curValue;
            System.out.println("Введите новые значения полей (Enter - оставить прежнее значение).");
           
            System.out.print("Название (прежнее значение): " +
                    editedToy.getName() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedToy.setName(curValue);

            System.out.print("Количество (прежнее значение): " +
                    editedToy.getCount() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedToy.setCount(Integer.parseInt(curValue));

            System.out.print("Цена (прежнее значение): " +
                    editedToy.getPrice() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedToy.setPrice(Float.parseFloat(curValue));

            System.out.print("Вес (прежнее значение): " +
                    editedToy.getWeight() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedToy.setWeight(Integer.parseInt(curValue));

            
            if (toysModel.save()) {
                System.out.println("Игрушка с id=" + curId + " успешно отредактирована.");
                return true;
            }

        } catch (Exception ex) {
            System.out.println("Ошибка при редактировании игрушки.\n" + ex.toString());
            return false;
        }
        return false;
    }

   
    public void PrizesToAwardShowAll() {
      
        DrawingModel drawingModel = new DrawingModel();
        if (drawingModel.loadPrizesToAward()) {
            drawingModel.ShowTablePrizesToAward();
        }
        ReturnToPrevPos(); 
        
    }

    public boolean PrizeAddNew() {
        DrawingModel drawingModel = new DrawingModel();
        if (drawingModel.PrizesToAwardAddNew())
            return true;
        return false;
    }

    public void PrizesAwardedShowAll() {
        
        DrawingModel drawingModel = new DrawingModel();
        if (drawingModel.loadPrizesAwarded()) {
            drawingModel.ShowTablePrizesAwarded();
        }
        ReturnToPrevPos(); 
        showDrawingMenu();
    }

    public boolean PrizeSetAsAwarded() {
            
            PrizesToAwardShowAll();
            System.out.print("Введите id разыгранного приза, для смены статуса на Вручен: ");
            try {
                int curId = Integer.parseInt(sc.nextLine());

                System.out.print("Введите дату, время вручения (Enter - текущая. пример: 06.07.2023 15:10): ");
                String curValue = sc.nextLine();
                LocalDateTime curDate;
                if (curValue.equals("")) {
                    curDate = LocalDateTime.now();
                } else {
                    curDate = LocalDateTime.parse(sc.nextLine(),formatter);
                }

                DrawingModel drawingModel = new DrawingModel();
                
                if (!drawingModel.loadPrizesToAward()) {
                    System.out.print("Ошибка при загрузке списка разыгранных призов!");
                    return false;
                }
                
                Prize curPrize = drawingModel.getPrizeToAwardById(curId);
             
                if (drawingModel.PrizeToAwardSetAsAwarded(curPrize, curDate)) {
                    
                    return true;
                }
            } catch (Exception ex) {
                System.out.println("Ошибка при редактировании игрушки.\n" + ex.toString());
                return false;
            }
       
        return false;
    }

  

    public String getPrevPos() {
        return prevPos;
    }

    public String getChoice() {
        return choice;
    }

    public String getNewPos() {
        return newPos;
    }

    public void setPrevPos(String prevPos) {
        this.prevPos = prevPos;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public void setNewPos(String newPos) {
        this.newPos = newPos;
    }

    public void ResetMenuPos() {
        prevPos = "";
        choice = "";
        newPos = "";
    }

    public void ReturnToPrevPos() {
        newPos = prevPos;
        choice = "";
    }

    public Menu() {
        prevPos = "";
        choice = "";
        newPos = "";
        formatter = DateTimeFormatter.ofPattern(
            "dd.MM.yyyy HH:mm");
    }
}
