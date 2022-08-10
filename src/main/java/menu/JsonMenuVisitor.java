package menu;

public class JsonMenuVisitor implements MenuVisitor {

    @Override
    public void drawMenu(final CompositeMenu compositeMenu, final int depth) {
        String padding = toPadding(depth);
        System.out.println(padding + "{");
        System.out.println(padding + "  title:  \"" + compositeMenu.getTitle() + "\",");
        System.out.println(padding + "  date:  \"" + compositeMenu.getDate() + "\",");
        System.out.println(padding + "  available:  \"" + compositeMenu.isAvailable() + "\",");
        System.out.println(padding + "  sub:  [  ");
    }

    private String toPadding(final int depth) {
        String padding = "";
        for (int i = 0; i < depth; i++) {
            padding += "  ";
        }
        return padding;
    }

    @Override
    public void endMenu(final int depth, final boolean isEnd) {
        final String padding = toPadding(depth);
        // 자식들 시작시 열어주는 대괄호
        System.out.println(padding + "  ]");
        // 내 시작시 열어주는 괄호
        System.out.println(padding + "}");
        if (!isEnd) {
            System.out.println(",");
        }
    }
}
