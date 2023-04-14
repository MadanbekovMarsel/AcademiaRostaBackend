package kg.school.restschool.rostAbacus.pb;

import kg.school.restschool.rostAbacus.Generatable;

public class MinusOne implements Generatable {
    public int head(int sum){
        switch (sum){
            case 0:
                int probab = random.nextInt(5) + 1;
                if(probab <= 3)     return random.nextInt(5) + 5;
                return random.nextInt(4) + 1;
            case 1:
                probab = random.nextInt(5) + 1;
                if(probab <= 3)     return random.nextInt(4) + 5;
                return random.nextInt(3) + 1;
            case 2:
                probab = random.nextInt(5) + 1;
                if(probab <= 3)     return random.nextInt(3) + 5;
                return random.nextInt(2) + 1;
            case 3:
                probab = random.nextInt(5) + 1;
                if(probab <= 3)     return random.nextInt(2) + 5;
                return random.nextInt(1) + 1;
            case 4:
                probab = random.nextInt(5) + 1;
                if(probab <= 4)     return 5;
                return -(random.nextInt(4) + 1);
            case 5:
                return -1;
            case 6:
                probab = random.nextInt(5) + 1;
                if(probab <= 3)     return -(1);
                else{
                    if(random.nextBoolean())    return -(random.nextInt(2) + 5);
                    return random.nextInt(3) + 1;
                }
            case 7:
                probab = random.nextInt(5) + 1;
                if(probab <= 3)     return -2;
                else{
                    if(random.nextBoolean())    return -(random.nextInt(3) + 5);
                    return -(random.nextInt(2) + 1);
                }
            case 8:
                probab = random.nextInt(5) + 1;
                if(probab <= 3)     return -3;
                else{
                    if(random.nextBoolean())    return -(random.nextInt(4) + 5);
                    return  -(random.nextInt(1) + 1);
                }
            case 9:
                return -(random.nextInt(9) + 1);

        }
        return sum;
    }
    public int tail(int sum, boolean isPlus){
        switch (sum){
            case 0:
                int probab = random.nextInt(5) + 1;
                if(isPlus) {
                    if (probab <= 3) return random.nextInt(5) + 5;
                    return random.nextInt(4 + 1);
                }
                return 0;
            case 1:
                if(isPlus) {
                    probab = random.nextInt(5) + 1;
                    if (probab <= 3) return random.nextInt(4) + 5;
                    return random.nextInt(3 + 1);
                }
                return -(random.nextInt(2));
            case 2:
                if(isPlus) {
                    probab = random.nextInt(5) + 1;
                    if (probab <= 3) return random.nextInt(3) + 5;
                    return random.nextInt(2 + 1);
                }
                return -random.nextInt(3);
            case 3:
                if(isPlus) {
                    probab = random.nextInt(5) + 1;
                    if (probab <= 3) return random.nextInt(2) + 5;
                    return random.nextInt(1 + 1);
                }
                return -random.nextInt(4);
            case 4:
                if(isPlus) {
                    probab = random.nextInt(5) + 1;
                    if (probab <= 4) return 5;
                    return 0;
                }
                return -random.nextInt(5);
            case 5:
                if(isPlus)  return random.nextInt(5);
                return -1;
            case 6:
                if(isPlus) {
                    random.nextInt(4);
                }
                else {
                    if(random.nextBoolean())    return -(random.nextInt(2));
                    return -(random.nextInt(2) + 5);
                }
            case 7:
                if(isPlus)  return random.nextInt(3);
                else {
                    probab = random.nextInt(5) + 1;
                    if(probab <= 3)     return -(random.nextInt(3));
                    return -(random.nextInt(3) + 5);
                }
            case 8:
                if(isPlus)  return random.nextInt(2);
                else{
                    probab = random.nextInt(5) + 1;
                    if(probab <= 3)     return -(random.nextInt(4));
                    return -(random.nextInt(4) + 5);
                }
            case 9:
                if(isPlus)  return 0;
                else return -random.nextInt(10);

        }
        return sum;
    }
}
