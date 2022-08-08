package functioalInterface;

public class Car {

    private final String name;
    private final int position;
    private final MovingStrategy movingStrategy;

    public Car(final String name) {
        this(new RandomMovingStrategy(), name);
    }

    public Car(final MovingStrategy movingStrategy, final String name) {
        this(movingStrategy, name, 0);
    }

    public Car(final String name, final int position) {
        this(new RandomMovingStrategy(), name, position);
    }

    public Car(final MovingStrategy movingStrategy, final String name, final int position) {
        this.movingStrategy = movingStrategy;
        this.name = name;
        this.position = position;
    }

    public Car move() {
        if (movingStrategy.isMovable()) {
            return new Car(this.movingStrategy, this.name, position + 1);
        }
        return this;
    }

    public int getPosition() {
        return position;
    }
}
