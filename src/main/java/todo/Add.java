package todo;

import java.time.LocalDateTime;

public class Add implements Command {

    private final String title;
    private final LocalDateTime date;
    private CompositeTask oldTask;

    public Add(final String title, final LocalDateTime date) {
        this.title = title;
        this.date = date;
    }

    @Override
    public void execute(final CompositeTask task) {
        oldTask = task.addTask(title, date);
    }

    @Override
    public void undo(final CompositeTask task) {
        task.remove(oldTask);
    }
}
