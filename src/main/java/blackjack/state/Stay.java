package blackjack.state;

public class Stay extends Finished{

    Stay() {
    }

    @Override
    protected double earningRate() {
        return 1;
    }
}
