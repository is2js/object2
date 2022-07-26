package menu;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CompositeMenu {
    private String title;
    private LocalDateTime date;
    private boolean available = true;
    @SerializedName("sub")
    private final Set<CompositeMenu> menus = new HashSet<>();


    public CompositeMenu(final String title, final LocalDateTime date) {
        setTitle(title);
        setDate(date);
    }

    public boolean addMenu(final String title, final LocalDateTime date) {
        return addMenu(new CompositeMenu(title, date));
    }

    public boolean addMenu(final CompositeMenu compositeMenu) {
        if (menus.contains(compositeMenu)) {
            return false;
        }
        menus.add(compositeMenu);
        return true;
    }

    public boolean remove(final CompositeMenu menu) {
        if (menus.contains(menu)) {
            return false;
        }
        menus.remove(menu);
        return true;
    }

    public List<CompositeMenu> getMenus() {
        return new ArrayList<>(this.menus);
    }

    public MenuReport createReport(CompositeMenuSortType compositeMenuSortType) {
        // 나 자신의 사본
        final MenuReport menuReport = new MenuReport(this);
        // 내 자식들을 순회전에 변환 + 전환까지
        final List<CompositeMenu> copiedMenus = new ArrayList<>(menus);
        copiedMenus.sort((a, b) -> compositeMenuSortType.compare(a, b));
        
        for (final CompositeMenu menu : copiedMenus) {
            menuReport.addReport(menu.createReport(compositeMenuSortType));
        }

        return menuReport;
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

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public void toggle() {
        available = !available;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompositeMenu that = (CompositeMenu) o;
        return isAvailable() == that.isAvailable() && Objects.equals(getTitle(), that.getTitle())
            && Objects.equals(getDate(), that.getDate()) && Objects.equals(menus, that.menus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDate(), isAvailable(), menus);
    }

    @Override
    public String toString() {
        return "CompositeMenu{" +
            "title='" + title + '\'' +
            ", date=" + date +
            ", available=" + available +
            ", menus=" + menus +
            '}';
    }
}
