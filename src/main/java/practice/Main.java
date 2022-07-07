package practice;

public class Main {

    public static void main(String[] args) {
        //1. 정책이 적용될 객체는 확장(부모)가능성이 있는 class면, 생성자 주입을 받지 않는다.
        final Plan plan = new Plan();

        //2. void setter받기기능으로 전략객체가 아닌 전략주입 구상클래스를 받는다.
        // -> 단순히 전략메서드만 필요한게 아니라, 템플릿메소드에서 변형된 것으로 생각하여
        // --> 전략객체들의 공통기능들 or 전략조건들을 [여러개 받는]  or [여러 전략객체들을 모아서 여러번 적용]할 이외 같은 형태를 만든다.
        // ---> 인터페이스로만 구성하면, 모아서 한번에 적용해주는 기능이 없다.
        //plan.setCalculator(new CalculatorBasedOnCalls());

        //3.전략주입 구상클래스는 전략객체들을 받되, 1개는 필수로 받도록 생성장체서 받고
        // -> 그 이후로는 setNext()를 통해 다음 것을 받는다.
        plan.setCalculator(new CalculatorBasedOnCalls(new FirstStrategy())
            .setNext(new SecondStrategy())
            .setNext(new ThirdStrategy())
        );

        //4. 전략관련 주입받은 적용대상 plan이 계산을 한다.
        plan.calculateFee();
    }
}
