package kg.school.restschool.settings;

import java.util.HashMap;

public class Text {
    private static final HashMap<String, String> data = new HashMap<>();
    public static String get(String key){
        return data.get(key);
    }
    public static String[] getMonths(){
        String[] months = new String[12];
        months[0] = get("JANUARY");
        months[1] = get("FEBRUARY");
        months[2] = get("MARCH");
        months[3] = get("APRIL");
        months[4] = get("MAY");
        months[5] = get("JUNE");
        months[6] = get("JULY");
        months[7] = get("AUGUST");
        months[8] = get("SEPTEMBER");
        months[9] = get("OCTOBER");
        months[10] = get("NOVEMBER");
        months[11] = get("DECEMBER");
        return months;
    }
    public static void init(){
        data.put("PROGRAM_NAME","Офисный менеджер");
        data.put("MENU_FILE","Файл");
        data.put("MENU_EDIT","Правка");
        data.put("MENU_VIEW","Вид");
        data.put("MENU_HELP","Помощь");

        data.put("JANUARY","Январь");
        data.put("FEBRUARY","Февраль");
        data.put("MARCH","Март");
        data.put("APRIL","Апрель");
        data.put("MAY","Май");
        data.put("JUNE","Июнь");
        data.put("JULY","Июль");
        data.put("AUGUST","Август");
        data.put("SEPTEMBER","Сентябрь");
        data.put("OCTOBER","Октябрь");
        data.put("NOVEMBER","Ноябрь");
        data.put("DECEMBER","Декабрь");

        data.put("ERROR_TITLE_EMPTY","Вы не ввели название!");
        data.put("ERROR_IS_EXISTS","Такая запись уже существует!");
        data.put("ERROR_DATE_FORMAT","Неправильный формат даты!");

        data.put("ERROR_USER_NOT_FOUND","Пользователь не найден!");
        data.put("ERROR_SUBJECT_NOT_FOUND","Предмет не найден!");
        data.put("ERROR_GROUP_NOT_FOUND","Группа не найдена!");
        data.put("ERROR_TIMETABLE_NOT_FOUND","Расписание не найдено!");
        data.put("ERROR_TASK_NOT_FOUND","Тема не найдена!");

        data.put("ERROR_USER_EXISTS","Пользователь с таким логином уже существует!");
        data.put("ERROR_SUBJECT_EXISTS","Предмет с таким названием уже существует!");
        data.put("ERROR_GROUP_EXISTS","Группа с таким именем уже существует!");
        data.put("ERROR_ADDITIONAL_EXISTS","Такая запись уже существует!");
        data.put("ERROR_TIMETABLE_EXISTS","Расписание уже существует!");

        data.put("ERROR_NAME_EMPTY","Поле имени не может быть пустым!");
        data.put("ERROR_PASSWORD_EMPTY","Пароль не должен быть пустым!");
        data.put("ERROR_USERNAME_EMPTY","Имя пользователя не может быть пустым!");
        data.put("ERROR_AGE_MUST_BE_BETWEEN_0_100","Возраст должен быть в диапазоне 0-100!");

        data.put("ERROR_GROUP_CONTAINS_USER","Этот пользователь уже состоит в данной группе");

        data.put("ERROR_INVALID_NAME","В имени есть недопустимые символы!");
        data.put("ERROR_INVALID_AGE","Возраст введен неверно!");
        data.put("ERROR_INVALID_EMAIL","Возраст введен неверно!");
        data.put("ERROR_INVALID_TIME_FORMAT","Время введено неверно");
        data.put("ERROR_INVALID_PHONE_NUMBER","Номер введен неправильно");
        data.put("ERROR_TARGET_USER_NOT_EQUAL_REQUESTER","Вы не можете изменять данные другого пользователя");


        data.put("YES","Да");
        data.put("NO","Нет");
    }
}
