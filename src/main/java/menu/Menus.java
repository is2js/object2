package menu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menus {

    private String title;
    private final Set<Menu> menus = new HashSet<>();

    public Menus(final String title) {
        setTitle(title);
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public boolean addMenu(final String title, final LocalDateTime date){
        final Menu target = new Menu(title, date);
        if (menus.contains(target)) {
            return false;
        }
        menus.add(target);
        return true;
    }

    public boolean remove(final Menu menu){
        if (menus.contains(menu)) {
            return false;
        }
        menus.remove(menu);
        return true;
    }

    public List<Menu> getMenus(MenuSortType sortType) {
        final List<Menu> copiedMenus = new ArrayList<>(menus);
        copiedMenus.sort((a, b) -> sortType.compare(a, b));
        return copiedMenus;
    }
}
