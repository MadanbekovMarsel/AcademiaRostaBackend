package kg.school.restschool.rostAbacus.psv;

import kg.school.restschool.services.TrenajerService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TrenajerService trenajerService = new TrenajerService();
        List<int[]> ar = trenajerService.getArray("PD+9",2,10,1);
        for(int[] a: ar){
            for (int j : a) {
                System.out.println(j);
            }
        }
    }
}
