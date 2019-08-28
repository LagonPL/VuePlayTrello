package util;

public class Utils {
    public static Boolean Spliter(String string, int id){
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
}