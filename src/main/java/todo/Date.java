package todo;

import java.time.LocalDateTime;

public class Date implements Command {

    private final LocalDateTime date;
    private LocalDateTime oldDate;

    public Date(final LocalDateTime date) {
        this.date = date;
    }

    @Override
    public void execute(final CompositeTask task) {
        oldDate = task.getDate();
        task.setDate(date);
    }

    @Override
    public void undo(final CompositeTask task) {
        task.setDate(oldDate);
    }
}
