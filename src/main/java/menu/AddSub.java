package menu;

public class AddSub implements Command {

    private final CommandMenu commandMenu;
    private CompositeMenu addedSubMenu;

    public AddSub(final CommandMenu commandMenu) {
        this.commandMenu = commandMenu;
    }

    @Override
    public void execute(final CompositeMenu menu) {
        addedSubMenu = menu.addMenu(commandMenu.getMenu());
    }

    @Override
    public void undo(final CompositeMenu menu) {
        menu.remove(addedSubMenu);
    }
}
