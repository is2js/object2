package menu;

import java.util.ArrayList;
import java.util.List;

public class MenuReport {

    private final CompositeMenu compositeMenu;
    private final List<MenuReport> reports = new ArrayList<>();

    public MenuReport(final CompositeMenu compositeMenu) {
        this.compositeMenu = compositeMenu;
    }

    public boolean addReport(final MenuReport menuReport){
        if (reports.contains(menuReport)) {
            return false;
        }

        reports.add(menuReport);
        return true;
    }

    public CompositeMenu getCompositeMenu() {
        return compositeMenu;
    }

    public List<MenuReport> getReports() {
        return reports;
    }
}
