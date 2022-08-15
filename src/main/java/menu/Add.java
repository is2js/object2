package menu;

import java.time.LocalDateTime;

public class Add implements Command {

    private final String title;
    private final LocalDateTime date;
    private final boolean available;
    private CompositeMenu addedMenu;

    public Add(final String title, final LocalDateTime date) {
        this(title, date, true);
    }

    public Add(final String title, final LocalDateTime date, final boolean available) {
        this.title = title;
        this.date = date;
        this.available = available;
    }

    @Override
    public void execute(final CompositeMenu menu) {
        addedMenu = menu.addMenu(title, date, available);
    }

    @Override
    public void undo(final CompositeMenu menu) {
        menu.remove(addedMenu);
    }
}
