package chapter_01.java;

//점층적 생성자 패턴
public class item_02_1 {
  NutritionFacts n = new NutritionFacts(0, 0, 0, 0, 0, 0);
}

class NutritionFacts{
  //필수
  private final int servingSize;
  private final int servings;
  //선택
  private final int calories;
  private final int fat;
  private final int sodium;
  private final int carbohydrate;

  public NutritionFacts(int servingSize, int servings){
    this(servingSize, servings,0);
  }
  public NutritionFacts(int servingSize, int servings, int calories){
    this(servingSize, servings, calories, 0);
  }
  public NutritionFacts(int servingSize, int servings, int calories, int fat){
    this(servingSize, servings, calories,fat,0);
  }
  public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium){
    this(servingSize, servings, calories, fat, sodium, 0);
  }
  public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate){
    this.servingSize=servingSize;
    this.servings=servings;
    this.calories=calories;
    this.fat=fat;
    this.sodium=sodium;
    this.carbohydrate=carbohydrate;
  }
}
