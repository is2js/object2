package practice_next_deco_for_adding_cumulative_func;


import goodComposition.Money;

public class Main {
    public static void main(String[] args) {

        final Calculator calculator1 = new Calculator(new BaseCalc());

        calculator1
            .setNext(new AdditionalCalc(Money.of(200D)))
            .setNext(new AdditionalCalc(Money.of(300D)));

        System.out.println("calculator1.calculate() = " + calculator1.calculate());

    }
}
