package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public interface Calc {

    Money calculateFee(Money result, Set<Call> calls);
}
