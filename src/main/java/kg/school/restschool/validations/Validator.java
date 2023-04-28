package kg.school.restschool.validations;

import kg.school.restschool.settings.Templates;
import kg.school.restschool.settings.Text;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class Validator {

    public static void main(String[] args) {
        Templates.init();
//        String s = "70933497";
//        System.out.println(validNumber(s));

//        String time = "1:9";
//        System.out.println(validTime(time));

        String name = "marSeL1";
        System.out.println(validName(name));
    }

    public static String validNumber(String number) {
        for (String prefix : Templates.numberPrefixes) {
            if (number.startsWith(prefix)) {
                number = number.substring(prefix.length());
                number = number.replaceAll(" ", "");
            }
        }
        if (number.length() != Templates.numberBody.replaceAll(" ","").length()) return null;
        StringBuilder validN = new StringBuilder(Templates.numberPrefixes.get(1) + " ");
        int i = 0;
        for (char c : Templates.numberBody.toCharArray()) {
            if(!Character.isDigit(number.charAt(i)))    return null;
            validN.append((Character.isLetter(c)) ? number.charAt(i++) : ' ');
        }
        return validN.toString();
    }

    public static String validTime(String time) {
        if(!time.contains(":")) return null;
        String[] times = time.split(":");
        StringBuilder validT = new StringBuilder();
        try{
            int hours = Integer.parseInt(times[0]);
            validT.append((hours < 10)? "0" + hours + ":" : hours + ":");
        }catch (NumberFormatException e){
            return null;
        }
        try{
            int minutes = Integer.parseInt(times[1]);
            validT.append((minutes < 10)? "0" + minutes: minutes);
        }catch (NumberFormatException | IndexOutOfBoundsException e){
            validT.append("00");
        }
        return validT.toString();
    }

    public static String validName(String name) {
        for(char c : name.toCharArray())
            if(!Character.isLetter(c))  return null;
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
