package todo;

import java.time.LocalDateTime;

public class Remove implements Command {

    private final CompositeTask baseTask;
    private String oldTitle;
    private LocalDateTime oldDate;

    public Remove(final CompositeTask baseTask) {
        this.baseTask = baseTask;
    }

    @Override
    public void execute(final CompositeTask task) {
        oldTitle = task.getTitle();
        oldDate = task.getDate();
        task.remove(baseTask);
    }

    @Override
    public void undo(final CompositeTask task) {
        task.addTask(oldTitle, oldDate);
    }
}
