package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.HashSet;
import java.util.Set;

public abstract class Plan {

    // 1. 부모 변수는 private을 써서 자식이 못쓰게 한다.
    private final Set<Call> calls = new HashSet<>();

    // 2. 부모클래스(추상층)은 생성자 인자를 받아면 안되니, 미리 초기화 -> add/setter(받기기능)으로 받아준다
    // 3. 부모메서드는 final을 써서 자식이 오버라이딩 못하게 한다. (부모context와 관계context를 대신 채울 순 없다.)
    public final void addCall(Call call){
        calls.add(call);
    }
    // 부모 메서드는 템플릿메소드도 final을 쓴다. (내부 훅메서드 소지와는 별개)
    public final Money calculateFee() {
        // early return없이  if or for을 거치면서 해당할 때마다 재할당으로 누적 적용 -> 최종 return이라면, default값으로 반환변수를 미리 만든다.
        Money result = Money.ZERO;
        for (final Call call : calls) {
            // 5. 자식들은 부모의 private calls를 터치하는 것이 아니라,
            //   개별 call의 아주 작은 부분만 개별구현하여 터치한다.
            //   -> 자식을 통한 확장시, 작은 역할만 주어지게 한다.
            result = result.plus(calculateCallFee(call));
        }

        return result;
    }

    // 4. 개별구현해야할 메서드는 protected abstract로 signaure만 넘겨서 받아온다.
    //    -> 만약, 자식들 개별구현 훅메서드 없다면, 일반class 부모다.
    protected abstract Money calculateCallFee(final Call call);
}
