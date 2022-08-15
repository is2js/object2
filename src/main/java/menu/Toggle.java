package menu;

public class Toggle implements Command {

    @Override
    public void execute(final CompositeMenu menu) {
        menu.toggle();
    }

    @Override
    public void undo(final CompositeMenu menu) {
        menu.toggle();
    }
}
