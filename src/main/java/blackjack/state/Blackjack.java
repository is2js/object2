package blackjack.state;

public class Blackjack extends Finished {

    Blackjack() {
    }

    @Override
    protected double earningRate() {
        return 1.5;
    }
}
