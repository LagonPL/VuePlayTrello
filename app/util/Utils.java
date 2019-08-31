package util;
import models.EventLog;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;

public class Utils {
    public static Boolean Spliter(String string, int id){
        if(string == null){
            return true;
        }
        if(string.isEmpty()){
            return true;
        }
        String[] parts = string.split(";");
        for (String part : parts) {
            if(part.equals(Integer.toString(id))){
                return false;
            }
        }
        return true;
    }

    public static Boolean CheckIfUserAlreadyExist(String string, int id){
        String[] parts = string.split(";");
        for (String part : parts) {
            System.out.println(part + Integer.toString(id) + " - Test czy już jest wpisany w board");
            if(part.equals(Integer.toString(id))){
                return true;
            }
        }
        return false;
    }

    public static String AddBoard(String user1, String user2, int owner){
        String owneruser = Integer.toString(owner);
        if(user1.isEmpty()){
            System.out.println(user2 + "userzy w teamie?");
            List<String> list2 = new ArrayList<>(Arrays.asList(user2.split(";")));
            list2.remove(owneruser);
            for (int i=0; i<list2.size();i++) {
                    if(i==0){
                        user1 = list2.get(i);
                    }
                    else{
                        user1 += ";"+list2.get(i);
                    }
                }
                System.out.println(user1 + "xD"+ owneruser+"xP\n");
                return user1;
        }//sadwqs
        List<String> list1 =  new ArrayList<>(Arrays.asList(user1.split(";")));
        System.out.println(user1);
        List<String> list2 = new ArrayList<>(Arrays.asList(user2.split(";")));
        System.out.println(user2);

        list1.addAll(list2);
        list1 = list1.stream().distinct().collect(Collectors.toList());
        list1.remove(owneruser);
        int size = list1.size();
        for (int i=0; i<size;i++) {
            if(i==0){
                user1 = list1.get(i);
            }
            else{
                user1 += ";"+list1.get(i);
            }
        }
        System.out.println(user1+"plsdzialaj");
        return user1;
    }

    public static EventLog eLog(int boardid, String mail, String var1, String id){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date();
        EventLog eventLog;
        switch(id){
            case "utworzenieboardu":
            eventLog = new EventLog(boardid, formatter.format(date) + ": " + mail + " utworzył tablicę: \"" + var1 + "\".");
            break;
            case "nowyuser":
            eventLog = new EventLog(boardid, formatter.format(date) + ": " + mail + " dodał do tablicy użytkownika: \"" + var1 + "\".");
            break;
            case "zmiananazwyboardu":
            eventLog = new EventLog(boardid, formatter.format(date) + ": " + mail + " zmienił nazwę tablicy na: \"" + var1 + "\".");
            break;
            case "nowalista":
            eventLog = new EventLog(boardid, formatter.format(date) + ": " + mail + " utworzył listę: \"" + var1 + "\".");
            break;
            case "nowakarta":
            eventLog = new EventLog(boardid, formatter.format(date) + ": " + mail + " utworzył kartę: \"" + var1 + "\".");
            break;
            default:
            eventLog = null;
        }
        
        return eventLog;
    }
    
}