package chapter_01.java;


public class Item_01{
  public static void main(String[] args) {
    Animal cat1 = Animal.getCat();
    Animal dog1 = Animal.getDog();
    cat1.bark();
    dog1.bark();

    Animal cat2 = Animal.getAnimalByType("cat");
    Animal dog2 = Animal.getAnimalByType("dog");
    cat2.bark();
    dog2.bark();
  }
}

abstract class Animal{
  private static Dog dog;
  private static Cat cat;

  abstract void bark();
  static Animal getCat(){
    if(cat!=null) return cat;
    else return new Cat();
  }
  static Animal getDog(){
    if(dog!=null) return dog;
    else return new Dog();
  }
  static Animal getAnimalByType(String type){
    if(type.equalsIgnoreCase("dog")){
      return getDog();
    }else{
      return getCat();
    }
  }
}
class Cat extends Animal{
  public void bark(){
    System.out.println("nyang");
  }
}
class Dog extends Animal{
  public void bark(){
    System.out.println("mung");
  }
}