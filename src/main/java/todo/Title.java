package todo;

public class Title implements Command {

    private final String title;
    private String oldTitle;

    public Title(final String title) {
        this.title = title;
    }

    @Override
    public void execute(final CompositeTask task) {
        oldTitle = task.getTitle();
        task.setTitle(title);
    }

    @Override
    public void undo(final CompositeTask task) {
        task.setTitle(oldTitle);
    }
}
