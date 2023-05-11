package kg.school.restschool.exceptions;

import com.jayway.jsonpath.spi.cache.Cache;
import kg.school.restschool.settings.Text;

public class InvalidDataException extends Exception{
    public static final int INVALID_NAME = 1;
    public static final int INVALID_AGE = 2;
    public static final int INVALID_EMAIL = 3;
    public static final int EMPTY_NAME = 4;
    public static final int INVALID_PHONE_NUMBER = 5;

    public static final int TARGET_USER_NOT_EQUAL_REQUESTER = 6;

    private final int code;
    public InvalidDataException(int code){
        this.code = code;
    }
    public String getMessage(){
        return switch (code) {
            case 1 -> Text.get("ERROR_INVALID_NAME");
            case 2 -> Text.get("ERROR_INVALID_AGE");
            case 3 -> Text.get("ERROR_INVALID_EMAIL");
            case 4 -> Text.get("ERROR_NAME_EMPTY");
            case 5 -> Text.get("ERROR_INVALID_PHONE_NUMBER");
            case 6 -> Text.get("ERROR_TARGET_USER_NOT_EQUAL_REQUESTER");
            default -> null;
        };
    }
}
