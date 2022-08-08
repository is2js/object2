package todo;

public interface Command {

    void execute(final CompositeTask task);
    void undo(final CompositeTask task);
}
