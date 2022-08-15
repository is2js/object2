package menu;

public interface Command {

    void execute(final CompositeMenu menu);
    void undo(final CompositeMenu menu);
}
