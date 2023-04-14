package kg.school.restschool.rostAbacus.pb;

import kg.school.restschool.rostAbacus.Generatable;

public class MinusTwo implements Generatable {
    public int head(int sum){
        switch (sum){
            case 0:
                if(random.nextInt(5) + 1 <= 3)    return random.nextInt(5) + 5;
                return random.nextInt(4) + 1;
            case 1:
                if(random.nextInt(5) + 1 <= 3)    return random.nextInt(4) + 5;
                return random.nextInt(3) + 1;
            case 2:
                if(random.nextInt(5) + 1 <= 3)    return random.nextInt(3) + 5;
                return random.nextInt(2) + 1;
            case 3:
                if(random.nextInt(5) + 1 <= 3)    return random.nextInt(2) + 5;
                return random.nextInt(2) + 1;
            case 4:
                if(random.nextBoolean())  return random.nextInt(2) + 1;
                return 5;
            case 5:
                return -2;
            case 6:
                if(random.nextInt(5) + 1 <= 3)    return -(random.nextInt(2) + 1);
                return random.nextInt(3) + 1;
            case 7:
                if(random.nextInt(5) + 1 <= 3)    return -(random.nextInt(2) + 1);
                return random.nextInt(2) + 1;
            case 8:
                if(random.nextBoolean())  return -(random.nextInt(3) + 1);
                return -(random.nextInt(4) + 5);
            case 9:
                if(random.nextBoolean())  return -(random.nextInt(4) + 1);
                return -(random.nextInt(5) + 5);
        }
        return 0;
    }
    public int tail(int sum, boolean isPlus){
        switch (sum){
            case 0:
                if(isPlus)  {
                    if(random.nextInt(5) + 1 <= 3)    return random.nextInt(5) + 5;
                    return random.nextInt(6);
                }
                return 0;
            case 1:
                if(isPlus){
                    if(random.nextInt(5) + 1 <= 3)    return random.nextInt(4) + 5;
                    return random.nextInt(4);
                }
                return -random.nextInt(2);
            case 2:
                if(isPlus){
                    if(random.nextInt(5) + 1 <= 3)    return random.nextInt(3) + 5;
                    return random.nextInt(3);
                }
                return -random.nextInt(3);
            case 3:
                if(isPlus){
                    if(random.nextInt(5) + 1 <= 3)    return random.nextInt(2) + 5;
                    return random.nextInt(3);
                }
                return -random.nextInt(4);
            case 4:
                if(isPlus){
                    if(random.nextBoolean())  return random.nextInt(3);
                    return 5;
                }
                return -(random.nextInt(5));
            case 5:
                if(isPlus)  return random.nextInt(5);
                return -2;
            case 6:
                if(isPlus)  return random.nextInt(4);
                return -random.nextInt(3);
            case 7:
                if(isPlus)  return random.nextInt(3);
                else{
                    if(random.nextBoolean())  return -(random.nextInt(3));
                    return -(random.nextInt(3) + 5);
                }
            case 8:
                if(isPlus)  return random.nextInt(2);
                else{
                    if(random.nextBoolean())  return -(random.nextInt(4));
                    return -(random.nextInt(4) + 5);
                }
            case 9:
                if(isPlus)  return 0;
                else{
                    if(random.nextInt(5) + 1 <= 3)    return -(random.nextInt(5));
                    return -(random.nextInt(5) + 5);
                }
        }
        return 0;
    }
}
