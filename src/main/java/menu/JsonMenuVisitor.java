package menu;

public class JsonMenuVisitor implements MenuVisitor {

    private String json = "";

    @Override
    public void drawMenu(final CompositeMenu compositeMenu, final int depth) {
        json += "{";
        json += "  title:  \"" + compositeMenu.getTitle() + "\",";
        json += "  date:  \"" + compositeMenu.getDate() + "\",";
        json += "  available:  \"" + compositeMenu.isAvailable() + "\",";
        json += "  sub:  [  ";
    }

    @Override
    public void endMenu(final int depth, final boolean isEnd) {
        json += "  ]";
        json += "}";
        if (!isEnd) {
            json += ",";
        }
    }

    public String getJson() {
        return json;
    }
}
