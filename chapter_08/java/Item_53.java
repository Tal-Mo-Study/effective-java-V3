package chapter_08.java;

public class Item_53 {
    
    static int sum(int... args){
        int sum=0;
        for (int arg : args) {
            sum += arg;
        }
        return sum;
    }

    static int minV1(int... args){
        if(args.length == 0)
            throw new IllegalArgumentException("인수가 1개 이상 필요");
        int min = args[0];
        for(int i=1;i<args.length;i++){
            if(args[i]<min) min = args[i];
        }
        return min;
    }

    static int minV2(int firstArg, int... remainingArgs){
        int min = firstArg;
        for (int i : remainingArgs) {
            if (i < min) min = i;
        }
        return min;
    }

    public static void main(String[] args) {
        System.out.println(sum(1,2,3));

        // System.out.println(minV1(1,3,2));

        // System.out.println(minV1());

        // System.out.println(minV2(1,3,2));

        // System.out.println(minV2());
    }
}
