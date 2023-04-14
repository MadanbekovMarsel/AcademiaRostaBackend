package kg.school.restschool.services;

import kg.school.restschool.rostAbacus.Generatable;
import kg.school.restschool.rostAbacus.pb.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrenajerService {

    public List<int[]> getArray(String taskName, int digits, int count, int arrayCount) {
        Generatable task = getTask(taskName);
        List<int[]> responseArray = new ArrayList<>();
        for (int j = 0; j < arrayCount; j++) {
            int[] currentArray = new int[count + 1];
            int sum = 0;
            if (digits == 1) {
                for (int i = 0; i < count; i++) {
                    int currentNum = task.head(splitter(sum, 0));
                    currentArray[i] = currentNum;
                    sum += currentNum;
                }
            } else {
                for (int i = 0; i < count; i++) {
                    int currentNum = task.head(splitter(sum, digits - 1));
                    int k = 2;
                    while (k <= digits) {
                        currentNum = currentNum * 10 + task.tail(splitter(sum, digits - k), currentNum >= 0);
                        k++;
                    }
                    currentArray[i] = currentNum;
                    sum += currentNum;
                }
            }
            currentArray[count] = sum;
            responseArray.add(currentArray);
        }
        return responseArray;
    }

    private int splitter(int currentNum, int positionFromRight) {
        try {
            String num = new StringBuilder(Integer.toString(currentNum)).reverse().toString();
            return Character.getNumericValue(num.charAt(positionFromRight));
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    private Generatable getTask(String taskName) {
        return switch (taskName) {
            case "PB+1" -> new PlusOne();
            case "PB+2" -> new PlusTwo();
            case "PB+3" -> new PlusThree();
            case "PB+4" -> new PlusFour();
            case "PB-1" -> new MinusOne();
            case "PB-2" -> new MinusTwo();
            case "PB-3" -> new MinusThree();
            case "PB-4" -> new MinusFour();
            default -> new PlusOne();
        };

    }
}