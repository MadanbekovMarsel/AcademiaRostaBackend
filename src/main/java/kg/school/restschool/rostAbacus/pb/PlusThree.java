package kg.school.restschool.rostAbacus.pb;

import kg.school.restschool.rostAbacus.Generatable;

public class PlusThree implements Generatable {

    public int head(int sum){
        switch (sum){
            case 0:
                int probab = random.nextInt(5) + 1;
                if(probab <= 3) return random.nextInt(4) + 1;
                return random.nextInt(5) + 5;
            case 1:
                probab = random.nextInt(5) + 1;
                if(probab <= 3) return random.nextInt(3) + 1;
                return random.nextInt(4) + 5;
            case 2:
                probab = random.nextInt(5) + 1;
                if(probab <= 3) return 3;
                else{
                    if(random.nextBoolean())  return random.nextInt(2) + 1;
                    return random.nextInt(3) + 5;
                }
            case 3:
                probab = random.nextInt(5) + 1;
                if(probab <= 3) return 3;
                else {
                    if(random.nextBoolean())  return random.nextInt(2) + 1;
                    return random.nextInt(2) + 5;
                }
            case 4:
                if(random.nextBoolean())  return random.nextInt(3) + 1;
                return 5;
            case 5:
                if(random.nextBoolean())  return -(random.nextInt(2) + 1);
                return random.nextInt(4) + 1;
            case 6:
                if(random.nextBoolean())  return -(random.nextInt(2) + 1);
                else{
                    if(random.nextBoolean())  return random.nextInt(3) + 1;
                    return -(random.nextInt(2) + 5);
                }
            case 7:
                if(random.nextBoolean())  return -(random.nextInt(2) + 1);
                else{
                    if(random.nextBoolean())  return random.nextInt(2) + 1;
                    return -(random.nextInt(3) + 5);
                }
            case 8:
                if(random.nextBoolean())  return -(random.nextInt(3) + 1);
                else return -(random.nextInt(4) + 5);
            case 9:
                if(random.nextBoolean())  return -(random.nextInt(4) + 1);
                return -(random.nextInt(5) + 5);
        }
        return 0;
    }
    public int tail(int sum,boolean isPlus){
        switch (sum){
            case 0:
                if(isPlus){
                    if(random.nextBoolean())  return random.nextInt(4) + 1;
                    return random.nextInt(5) + 5;
                }
                else return 0;
            case 1:
                if(isPlus){
                    if(random.nextBoolean())  return random.nextInt(3) + 1;
                    return random.nextInt(4) + 5;
                }
                else return -random.nextInt(2);
            case 2:
                if(isPlus){
                    int probab = random.nextInt(5) + 1;
                    if(probab <= 2) return 3;
                    return random.nextInt(4);
                }
                return -(random.nextInt(3));
            case 3:
                if(isPlus){
                    int probab = random.nextInt(5) + 1;
                    if(probab <= 2) return 3;
                    if(random.nextBoolean())  return random.nextInt(2);
                    return random.nextInt(2) + 5;
                }
                return -(random.nextInt(4));
            case 4:
                if(isPlus){
                    int probab = random.nextInt(5) + 1;
                    if(probab <= 2) return 3;
                    return 5*random.nextInt(2);
                }
                return -(random.nextInt(5));
            case 5:
                if(isPlus)  return random.nextInt(5);
                if(random.nextBoolean())  return -5;
                return -random.nextInt(3);
            case 6:
                if(isPlus)  return random.nextInt(4);
                if(random.nextBoolean())  return -(random.nextInt(2) + 5);
                return -random.nextInt(3);
            case 7:
                if(isPlus)  return random.nextInt(3);
                if(random.nextBoolean())  return -(random.nextInt(3) + 5);
                return -random.nextInt(3);
            case 8:
                if(isPlus)  return random.nextInt(2);
                if(random.nextBoolean())  return -(random.nextInt(4));
                return -(random.nextInt(4) + 5);
            case 9:
                if(isPlus)  return 0;
                return -(random.nextInt(10));
        }
        return 0;
    }
}
