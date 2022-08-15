package menu;

public class Available implements Command {

    private final boolean available;
    private boolean oldAvailable;

    public Available(final boolean available) {
        this.available = available;
    }

    @Override
    public void execute(final CompositeMenu menu) {
        oldAvailable = menu.isAvailable();
        menu.setAvailable(available);
    }

    @Override
    public void undo(final CompositeMenu menu) {
        menu.setAvailable(oldAvailable);
    }
}
