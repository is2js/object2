package menu;

import java.time.LocalDateTime;

public class Date implements Command {

    private final LocalDateTime date;
    private LocalDateTime oldDate;

    public Date(final LocalDateTime date) {
        this.date = date;
    }


    @Override
    public void execute(final CompositeMenu menu) {
        oldDate = menu.getDate();
        menu.setDate(date);
    }

    @Override
    public void undo(final CompositeMenu menu) {
        menu.setDate(oldDate);
    }
}
