package kg.school.restschool.rostAbacus.pb;

import kg.school.restschool.rostAbacus.Generatable;

public class PlusTwo implements Generatable {

    public int head(int sum){
        switch (sum){
            case 0:
                int probab = random.nextInt(5) + 1;
                if(probab <= 3) return random.nextInt(4) + 1;
                return random.nextInt(5) + 5;
            case 1:
                probab = random.nextInt(5) + 1;
                if(probab <= 3) return random.nextInt(3) + 1;
                else    return random.nextInt(4) + 5;
            case 2:
                probab = random.nextInt(5) + 1;
                if(probab <= 3) return random.nextInt(2) + 1;
                return random.nextInt(3) + 5;
            case 3:
                return 2;
            case 4:
                if(random.nextBoolean())  {
                    if(random.nextBoolean())  return 2;
                    else{
                        if(random.nextBoolean())  return 1;
                        return 5;
                    }
                }
                return -(random.nextInt(4) + 1);
            case 5:
                if(random.nextBoolean())  return -5;
                return random.nextInt(4) + 1;
            case 6:
                if(random.nextBoolean())  return -5;
                return random.nextInt(3) + 1;
            case 7:
                if(random.nextBoolean()){
                    if(random.nextBoolean())  return -(random.nextInt(3) + 5);
                    return -(random.nextInt(2) + 1);
                }
                return random.nextInt(2) + 1;
            case 8:
                if(random.nextBoolean()){
                    if(random.nextBoolean())  return -(random.nextInt(4) + 5);
                    return -(random.nextInt(3) + 1);
                }
                return -5;
            case 9:
                return -(random.nextInt(9) + 1);
        }
        return sum;
    }
    public int tail(int sum,boolean isPlus){
        switch (sum){
            case 0:
                if(isPlus)  {
                    int probab = random.nextInt(5) + 1;
                    if(probab <= 3) return random.nextInt(4 + 1);
                    return random.nextInt(5) + 5;
                }
                return 0;
            case 1:
                if(isPlus) {
                    int probab = random.nextInt(5) + 1;
                    if(probab <= 3) return random.nextInt(3 + 1);
                    return random.nextInt(4) + 5;
                }
                return -random.nextInt(2);
            case 2:
                if(isPlus){
                    int probab = random.nextInt(5) + 1;
                    if(probab <= 3) return random.nextInt(2 + 1);
                    return random.nextInt(3) + 5;
                }
                return -random.nextInt(3);
            case 3:
                if(isPlus)  return 2;
                return -random.nextInt(4);
            case 4:
                if(isPlus)  {
                    if(random.nextBoolean())  return 2;
                    return random.nextInt(2)*5;
                }
                return -(random.nextInt(5));
            case 5:
                if(isPlus)  return random.nextInt(5);
                return -(random.nextInt(2)*5);
            case 6:
                if(isPlus)  return random.nextInt(4);
                else{
                    if(random.nextBoolean())  return -(random.nextInt(2));
                    return -(random.nextInt(2) + 5);
                }
            case 7:
                if(isPlus)  return random.nextInt(3);
                else{
                    if(random.nextBoolean())  return -(random.nextInt(3));
                    return -(random.nextInt(3) + 5);
                }
            case 8:
                if(isPlus)  return random.nextInt(2);
                else{
                    if(random.nextInt(5) + 1 > 3)  return -(random.nextInt(4));
                    return -(random.nextInt(4) + 5);
                }
            case 9:
                if(isPlus)  return 0;
                else{
                    if(random.nextInt(5) + 1 > 3)  return -(random.nextInt(5));
                    return -(random.nextInt(5) + 5);
                }
        }
        return sum;
    }
}
