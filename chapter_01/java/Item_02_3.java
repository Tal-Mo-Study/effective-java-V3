package chapter_01.java;

public class Item_02_3 {
    public static void main(String[] args) {
        NutritionFacts n = new NutritionFacts.Builder(240,8).calories(200).build();
    }
}
class NutritionFacts {
    //필수
    private final int servingSize;
    private final int servings;
    //선택
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public NutritionFacts(Builder build){
        servingSize= build.servingSize;
        servings=build.servings;
        calories=build.calories;
        fat=build.fat;
        sodium= build.sodium;
        carbohydrate= build.carbohydrate;
    }

    static class Builder{
        //필수
        private final int servingSize;
        private final int servings;
        //선택
        private int calories;
        private int fat;
        private int sodium;
        private int carbohydrate;

        public Builder(int servingSize, int servings){
            this.servingSize=servingSize;
            this.servings=servings;
        }
        public Builder calories(int calories){
            this.calories=calories;
            return this;
        }
        public Builder fat(int fat){
            this.fat=fat;
            return this;
        }
        public Builder sodium(int sodium){
            this.sodium=sodium;
            return this;
        }
        public Builder carbohydrate(int carbohydrate){
            this.carbohydrate=carbohydrate;
            return this;
        }
        public NutritionFacts build(){
            return new NutritionFacts(this);
        }
    }
}