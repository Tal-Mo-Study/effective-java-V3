package chapter_01.java;

public class Item_02_2 {
  public static void main(String[] args) {
    NutritionFacts n = new NutritionFacts(240, 2);
    n.setCalories(22221);
  }
}
class NutritionFacts{
  //필수
  private final int servingSize;
  private final int servings;
  //선택
  private int calories;
  private int fat;
  private int sodium;
  private int carbohydrate;

  public NutritionFacts(int servingSize, int servings){
      this.servingSize=servingSize;
      this.servings=servings;
  }

  public void setCalories(int calories) {
      this.calories = calories;
  }

  public void setFat(int fat) {
      this.fat = fat;
  }

  public void setSodium(int sodium) {
      this.sodium = sodium;
  }

  public void setCarbohydrate(int carbohydrate) {
      this.carbohydrate = carbohydrate;
  }
}
