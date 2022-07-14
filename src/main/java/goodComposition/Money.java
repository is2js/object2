package goodComposition;

public class Money {
    public static final Money ZERO = Money.of(0.0);
    private final Double amount;

    public Money(final Double amount) {
        this.amount = amount;
    }

    public static Money of(final Double amount) {
        return new Money(amount);
    }

    public Money minus(Money amount) {
        return new Money(this.amount > amount.amount ? this.amount - amount.amount : 0.0);
    }

    public Money multi(Double times) {
        return new Money(this.amount * times);
    }

    public Money plus(Money amount) {
        return new Money(this.amount + amount.amount);
    }

    public boolean isGreaterThan(Money amount) {
        return this.amount >= amount.amount;
    }

    public boolean isLessThan(final Money amount) {
        return this.amount < 0.0;
    }

    public boolean isLessThanOrEqualTo(final Money amount) {
        return this.amount <= 0.0;
    }

    public Money times(double multiple) {
        return new Money(this.amount * multiple);
    }

    public Double getAmount() {
        return amount;
    }
}
