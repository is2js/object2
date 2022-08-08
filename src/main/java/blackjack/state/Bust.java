package blackjack.state;

public class Bust extends Finished{

    Bust() {
    }

    @Override
    protected double earningRate() {
        return -1;
    }
}
