package menu;

public class Title implements Command {

    private final String title;
    private String oldTitle;

    public Title(final String title) {
        this.title = title;
    }

    @Override
    public void execute(final CompositeMenu menu) {
        oldTitle = menu.getTitle();
        menu.setTitle(title);
    }

    @Override
    public void undo(final CompositeMenu menu) {
        menu.setTitle(oldTitle);
    }
}
