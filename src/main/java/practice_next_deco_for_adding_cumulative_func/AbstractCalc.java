package practice_next_deco_for_adding_cumulative_func;

import goodComposition.Money;

public abstract class AbstractCalc {

    private AbstractCalc next;

    public final AbstractCalc setNext(final AbstractCalc next) {
        this.next = next;
        return this;
    }

    public Money calculate(Money result) {
        //1. 개별 데코객체들의 계산은 내수용형태의 훅메서드로, 누적 결과값 변수를 인자로 받았다가 -> 반환받아 업데이트 한다.
        //   next데코객체는, null소유한 마지막 객체도 연산을 하기 때문에, 연산이 끝나고 누적결과값을 업데이트하고 난 뒤 null터미네이팅 해야한다.
        //postcondition검증 생략

//        result = result.plus(calculateMoney(result));
        result = calculateMoney(result);

        //2. return내에서 [삼항연산자]로 null터미네이팅(지금까지 누적결과값 변수를 반환)
        //  + next필드에 저장된 다음 next데코객체로 [업데이트된 누적결과값을 받아 꼬리재귀]를 호출한다
        //   -> next데코객체는, null소유한 마지막 객체도 연산을 하기 때문에, 연산이 끝나고 누적결과값을 업데이트하고 난 뒤 null터미네이팅 해야한다.
        return next == null ? result : next.calculate(result);
    }

    protected abstract Money calculateMoney(final Money result);
}
