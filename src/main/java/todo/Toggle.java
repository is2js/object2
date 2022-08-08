package todo;

public class Toggle implements Command {

    @Override
    public void execute(final CompositeTask task) {
        task.toggle();
    }

    @Override
    public void undo(final CompositeTask task) {
        task.toggle();
    }
}
