package menu;

import java.time.LocalDateTime;

public class Remove implements Command {

    private final CompositeMenu menu;
    private String oldTitle;
    private LocalDateTime oldDate;
    private boolean oldAvailable;

    public Remove(final CompositeMenu menu) {
        this.menu = menu;
    }

    @Override
    public void execute(final CompositeMenu menu) {
        oldTitle = this.menu.getTitle();
        oldDate = this.menu.getDate();
        oldAvailable = this.menu.isAvailable();

        menu.remove(this.menu);
    }

    @Override
    public void undo(final CompositeMenu menu) {
        menu.addMenu(oldTitle, oldDate, oldAvailable);
    }
}
