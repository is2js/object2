package practice;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public interface CalculatorStrategy {

    Money calculateFee(Set<Call> calls, Money money);
}
