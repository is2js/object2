package menu;

public class ConsoleMenuVisitor implements MenuVisitor {

    @Override
    public void drawMenu(final CompositeMenu compositeMenu, final int depth) {
        String padding = "";
        for (int i = 0; i < depth; i++) {
            padding += "-";
        }
        System.out.println(padding
//            + (compositeMenu.isAvailable() ? "[이용가능] " : "[매진] ")
            + compositeMenu.getTitle()
            + "(출시: "
            + compositeMenu.getDate().toLocalDate()
            + ")"
            );
    }

    @Override
    public void endMenu(final int depth) {
//        System.out.println();
    }
}
