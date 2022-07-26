package menu;

public interface MenuVisitor {

    void drawMenu(CompositeMenu compositeMenu, int depth);

    void endMenu(int depth);
}
