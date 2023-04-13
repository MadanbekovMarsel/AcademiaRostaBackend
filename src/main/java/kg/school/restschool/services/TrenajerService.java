package kg.school.restschool.services;

import kg.school.restschool.rostAbacus.Generatable;
import kg.school.restschool.rostAbacus.pb.PlusOne;
import org.springframework.stereotype.Service;

@Service
public class TrenajerService {

    public int[] getArray(String taskName, int digits, int count) {
        Generatable task = getTask(taskName);
        int[] responseArray = new int[count + 1];

        int sum = 0;
        if (digits == 1) {
            for (int i = 0; i < count; i++) {
                int currentNum = task.head(splitter(sum, 0));
                responseArray[i] = currentNum;
                sum += currentNum;
            }
        } else {
            for (int i = 0; i < count; i++) {
                int currentNum = task.head(splitter(sum, digits - 1));
                int k = 2;
                while(k <= digits){
                    currentNum = currentNum * 10 + task.tail(splitter(sum,digits - k),currentNum >= 0);
                    k++;
                }
                responseArray[i] = currentNum;
                sum += currentNum;
            }
        }
        responseArray[count] = sum;
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
        return new PlusOne();
//        throw new SearchException(SearchException.TASK_NOT_FOUND);
    }
}
