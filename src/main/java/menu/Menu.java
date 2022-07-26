package menu;

import java.time.LocalDateTime;
import java.util.Objects;

public class Menu {
    private String title;
    private LocalDateTime date;
    private boolean available = true;

    Menu(final String title, final LocalDateTime date) {
        setTitle(title);
        setDate(date);
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public void toggle(){
        available = !available;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Menu menu = (Menu) o;
        return available == menu.available && Objects.equals(title, menu.title) && Objects.equals(date,
            menu.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, available);
    }

    @Override
    public String toString() {
        return "Menu{" +
            "title='" + title + '\'' +
            ", date=" + date +
            ", available=" + available +
            '}';
    }
}
