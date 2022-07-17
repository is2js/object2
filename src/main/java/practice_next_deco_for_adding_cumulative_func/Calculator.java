package practice_next_deco_for_adding_cumulative_func;

import goodComposition.Money;

public class Calculator {

    private final AbstractCalc calculator;

    public Calculator(final AbstractCalc calculator) {
        this.calculator = calculator;
    }

    public Money calculate() {
        // calculator에게 계산을 호출하면, 추상체에서 템플림메소드로 작성될 것이다.
        // -> 하지만, 이 메서드를 각 next데코들이 재귀형식으로 호출할 예정이므로
        // -> 재귀의 양식에 맞춰서 작성한다(
        // (1) 계산에 필요한 정보 외 [누적 결과값변수의 초기값]을 인자로 넣어서 시작객체를 최초호출한다
        // (2) 메서드 정의부에서는, [nul터미네이팅 + return문에서 다음 꼬리재귀호출 with 업데이트된 인자] 형식으로 정의 될 것이다.
        return calculator.calculate(Money.ZERO);
    }
}
