package functioalInterface;

import java.util.concurrent.ThreadLocalRandom;

public class RandomMovingStrategy implements MovingStrategy {

    @Override
    public boolean isMovable() {
        return ThreadLocalRandom.current().nextInt(10) >= 4;
    }
}
