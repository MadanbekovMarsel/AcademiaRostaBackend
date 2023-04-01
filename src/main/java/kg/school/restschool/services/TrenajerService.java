package kg.school.restschool.services;

import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.rostAbacus.Generatable;
import org.springframework.stereotype.Service;

@Service
public class TrenajerService {

    public int[] getArray(String taskName){
//        Generatable task = getTask(taskName);
        int[] res = new int[10];
        for(int i = 0; i < 10; i++){
            res[i] = i;
        }
        return res;
    }

    private Generatable getTask(String taskName) {
        switch (taskName){
            case "PSV":
        }
        throw new SearchException(SearchException.TASK_NOT_FOUND);
    }
}
