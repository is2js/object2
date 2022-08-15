package menu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandMenu {

    private final CompositeMenu menu;

    private final List<Command> commands = new ArrayList<>();
    private int cursor = -1; // 원래는 -1로 초기화해야할 듯
    private final Map<String, String> saved = new HashMap<>();

    public CommandMenu(final String title, final LocalDateTime date) {
        menu = new CompositeMenu(title, date);
    }

    public void save(final String key) {
        saved.put(key, createJson());
    }

    private String createJson() {
        final JsonMenuVisitor jsonMenuVisitor = new JsonMenuVisitor();
        final MenuRenderer renderer = new MenuRenderer(() -> jsonMenuVisitor);
        renderer.render(menu.createReport(CompositeMenuSortType.DATE_ASC));

        return jsonMenuVisitor.getJson();
    }

    public CompositeMenu load(final String key) {
        final String json = loadJson(key);
        menu.removeAll();

        menu.setTitle(extractData(json, cursor = updateCursor(json, cursor, "title:  \"")));
        menu.setDate(LocalDateTime.parse(extractData(json, cursor = updateCursor(json, cursor, "date:  \""))));
        menu.setAvailable(
            Boolean.parseBoolean(extractData(json, cursor = updateCursor(json, cursor, "available:  \""))));

        cursor = updateCursor(json, cursor, "sub:  [");

        cursor = load(menu, json, cursor);

        return menu;
    }

    private int load(final CompositeMenu parent, final String json, int cursor) {
        final int length = json.length();
        CompositeMenu child = null;
        while (cursor < length) {
            final char character = json.charAt(cursor);
            if (character == '{') {
                child = parent.addMenu(
                    extractData(json, cursor = updateCursor(json, cursor, "title:  \"")),
                    LocalDateTime.parse(extractData(json, cursor = updateCursor(json, cursor, "date:  \""))),
                    Boolean.parseBoolean(extractData(json, cursor = updateCursor(json, cursor, "available:  \""))));
            }

            if (child != null && character == '[') {
                cursor = load(child, json, cursor);
            }

            if (character == ']') {
                return cursor;
            }

            cursor++;
        }
        return cursor;
    }

    private void checkExists(final String key) {
        if (!saved.containsKey(key)) {
            throw new RuntimeException("You have to json before load");
        }
    }

    private void checkFormat(final String json) {
        if (json.trim().charAt(0) != '{') {
            throw new RuntimeException("the format of the JSON is only allowed for loading to CommandMenu");
        }
    }

    private String loadJson(final String key) {
        checkExists(key);
        final String json = saved.get(key);
        checkFormat(json);
        return json;
    }

    private String extractData(final String json, final int cursor) {
        final int titleEndPlusOneIndex = json.indexOf("\"", cursor + 1);
        return json.substring(cursor, titleEndPlusOneIndex);
    }
    private int updateCursor(final String json, int cursor, final String dataPrefix) {
        final int dataStartIndex = json.indexOf(dataPrefix, cursor);
        if (dataStartIndex == -1) {
            return -1;
        }
        return dataStartIndex + dataPrefix.length();
    }


    public void redo() {
        //reoo못하는 상황 == undo안한 상황 == add-execute만 한 상황 == cursor가 저장소 맨 마지막 에 위치
        if (cursor == commands.size() - 1) {
            return;
        }

        commands.get(++cursor)
            .execute(menu);
    }

    public void undo() {
        // undo못하는 상황 == 저장List에 cursor가 0에 위치하면 그거 꺼내서 undo가능하다. == cursor < 0일 때 불가
        if (cursor < 0) {
            return;
        }
        commands.get(cursor--)
            .undo(menu);
    }

    public void addMenu(final String title, final LocalDateTime date) {
        addCommand(new Add(title, date));
    }

    public void addMenu(final String title, final LocalDateTime date, final boolean available) {
        addCommand(new Add(title, date, available));
    }

    public void addMenu(final CommandMenu commandMenu) {
        addCommand(new AddSub(commandMenu));
    }

    public void remove(final CompositeMenu menu) {
        addCommand(new Remove(menu));
    }

    public void setTitle(final String title) {
        addCommand(new Title(title));
    }

    public void setDate(final LocalDateTime date) {
        addCommand(new Date(date));
    }

    public void setAvailable(final boolean available) {
        addCommand(new Available(available));
    }

    public void toggle() {
        addCommand(new Toggle());
    }

    public void addCommand(final Command cmd) {
        for (int i = commands.size() - 1; i > cursor; i--) {
            commands.remove(i);
        }

        commands.add(cmd);
        cursor = commands.size() - 1;

        cmd.execute(menu);
    }

    public List<CompositeMenu> getMenus() {
        return menu.getMenus();
    }

    public MenuReport createReport(CompositeMenuSortType compositeMenuSortType) {
        return menu.createReport(compositeMenuSortType);
    }

    public String getTitle() {
        return menu.getTitle();
    }

    public LocalDateTime getDate() {
        return menu.getDate();
    }

    public boolean isAvailable() {
        return menu.isAvailable();
    }

    public CompositeMenu getMenu() {
        return menu;
    }
}
