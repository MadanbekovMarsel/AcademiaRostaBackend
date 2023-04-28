package kg.school.restschool.rostAbacus.psv;

import kg.school.restschool.rostAbacus.Generatable;

public class PSV implements Generatable {

    @Override
    public int head(int sum) {
        switch (sum) {
            case 0:
                return random.nextInt(9) + 1;
            case 1:
                //Is Plus
                if (random.nextBoolean()) {
                    //Is more than 5
                    if (random.nextInt(10) > 3) return random.nextInt(4) + 5;
                    else return random.nextInt(3) + 1;
                } else return -1;
            case 2:
                if (random.nextInt(10) > 3) {
                    //Is more than 5
                    if (random.nextInt(10) > 3) return random.nextInt(3) + 5;
                    else return random.nextInt(2) + 1;
                } else return -(random.nextInt(2) + 1);
            case 3:
                if (random.nextInt(10) > 3) {
                    //Is more than 5
                    if (random.nextInt(10) > 3) return random.nextInt(2) + 5;
                    else return 1;
                } else return -(random.nextInt(3) + 1);
            case 4:
                if (random.nextInt(10) > 6) return 5;
                else return -(random.nextInt(4) + 1);
            case 5:
                if (random.nextInt(10) > 3) return random.nextInt(4) + 1;
                else return -5;
            case 6:
                if (random.nextInt(10) > 6) return random.nextInt(3) + 1;
                else {
                    if (random.nextInt(10) > 4) return -(random.nextInt(2) + 5);
                    else return -1;
                }
            case 7:
                if (random.nextInt(10) > 6) return random.nextInt(2) + 1;
                else {
                    if (random.nextInt(10) > 2) return -(random.nextInt(3) + 5);
                    else return -(random.nextInt(2) + 1);
                }
            case 8:
                if (random.nextInt(10) > 7) return 1;
                else {
                    if (random.nextInt(10) > 3) return -(random.nextInt(4) + 5);
                    else return -(random.nextInt(3) + 1);
                }
            case 9:
                return -(random.nextInt(9) + 1);
            default:
                return 0;
        }
    }

    @Override
    public int tail(int sum, boolean isPlus) {
        switch (sum) {
            case 0:
                if (isPlus) return random.nextInt(10);
                else return 0;
            case 1:
                if (isPlus) {
                    //Is more than 5
                    if (random.nextInt(10) > 3) return random.nextInt(4) + 5;
                    else return random.nextInt(4);
                } else return -random.nextInt(2);

            case 2:
                if (isPlus) {
                    //Is more than 5
                    if (random.nextInt(10) > 5) return random.nextInt(3) + 5;
                    else return random.nextInt(3);
                } else return -random.nextInt(3);
            case 3:
                if (isPlus) {
                    if (random.nextBoolean()) {
                        //Is more than 5
                        if (random.nextInt(10) > 3) return random.nextInt(2) + 5;
                        else return random.nextInt(2);
                    }
                } else {
                    return -(random.nextInt(4));
                }
            case 4:
                if (isPlus) {
                    return random.nextInt(10) > 3 ? 5 : 0;
                } else {
                    return -random.nextInt(5);
                }
            case 5:
                if (isPlus) return random.nextInt(5);
                else return random.nextBoolean() ? -5 : 0;
            case 6:
                if (isPlus) return random.nextInt(4);
                else {
                    if (random.nextInt(10) > 3) return -(random.nextInt(2) + 5);
                    else return -random.nextInt(2);
                }
            case 7:
                if (isPlus) return random.nextInt(3);
                else {
                    if (random.nextInt(10)>3) return -(random.nextInt(3) + 5);
                    else return -random.nextInt(3);
                }
            case 8:
                if (isPlus) return random.nextInt(2);
                else {
                    if (random.nextBoolean()) return -(random.nextInt(4) + 5);
                    else return -(random.nextInt(2));
                }
            case 9:
                if (isPlus) return 0;
                else    return -random.nextInt(10);
            default:
                return 0;
        }
    }
}
