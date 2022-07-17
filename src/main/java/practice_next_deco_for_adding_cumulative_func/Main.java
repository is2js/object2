package practice_next_deco_for_adding_cumulative_func;


import goodComposition.Money;

public class Main {
    public static void main(String[] args) {
        // 구상 next 데코객체는 잘바꿈후 첫번째인자를 next데코객체로 주고 체이닝된다.
        // 끝 특이점 객체는 첫번재인자를 null을 가진다.


        // 구상 next 데코객체를 추상체 필드로 소유하고 계산시 next들을 돌며 사용(실행기)하는 클래스
        // -> 이 계산기가 부모가 될 가능성(상속)이 있으면, setter로 or 처음부터 생성자 주입받아 사용한다.
        // -> 만약, 기본계산기는 생성자주입받아 가지고 태어나고, 그다음부터는 setter로 연결해줄 수 있다?
        // -> prev는 나를 소유한 class에서 이미완성된 객체를 필드에 저장하고, 다음타자를 소유하여 업데이트되어, 마지막타자를 소유하지만
        // -> next는 나를 소유한 class에서 나를 받을 때, 이미 next필드에 다음 것이 저장된 완성된 [시작 데코객체]만 가진다.
        //    -> 이미 next를 채울만큼 채운 것만 받으니, final이다.
        //    -> 밖에서 체이닝 다끝내고 완성해놓고, 주입받는다.
        final Calculator calculator = new Calculator(
            new BaseCalc().setNext(
                    new AdditionalCalc(Money.of(200D)).setNext(
                        null)));

        final Money result = calculator.calculate();
        //기본 500원에 추가요금 200원
        System.out.println("result = " + result);
    }
}
