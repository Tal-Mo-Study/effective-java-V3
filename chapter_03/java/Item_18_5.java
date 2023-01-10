package chapter_03.java;

public class Item_18_5 {
    public static void main(String[] args) {

        Controller c = new Controller();
        ModelWrapper m = new ModelWrapper(new Model(c));
        c.doChanges();
        // Controller doChanges
        // 기본 Model makeChange
        m.makeChange();
        // 기본 Model makeChange
        // wrapper 모델 makeChange : 1
    }
}

// wrapping 대상 기본클래스
class Model{
    Controller controller;

    Model(Controller controller){
        this.controller = controller;
        controller.register(this); //Pass SELF reference
    }

    public void makeChange(){
        System.out.println("기본 Model makeChange");
    }
}
// wrapper 클래스
class ModelWrapper {

    private final Model model;

    private int count;

    ModelWrapper(Model model){
        this.model = model;
    }

    // 원래의 목적은 Model.makeChange() 메서드 대신에 해당 메서드를 호출해 count를 증가시키는 것
    public void makeChange(){
        model.makeChange();
        count++;
        System.out.println("wrapper 모델 makeChange : " + count);
    }
}
class Controller {

    private Model model;

    public void register(Model model){
        this.model = model;
    }

    // wrapper 클래스인 ModelWrapper의 목적은 변경사항을 카운트하는 것이지만, model 객체는 register() 메서드에서
    // 자기 자신(this)을 전달했기 때문에 Controller에서는 래핑된 객체에 대한 참조를 알지 못하고 내부 객체를 호출하게 된다 -> SELF 문제
    public void doChanges(){
        System.out.println("Controller doChanges");
        model.makeChange();
    }
}