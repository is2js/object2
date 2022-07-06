package goodComposition.plan;

import goodComposition.Call;
import goodComposition.Money;
import java.util.Set;

public interface Calculator {

    Money calculateCallFee(Money result, Set<Call> calls);
}
