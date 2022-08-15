package menu;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CommandMenuTest {

    @Test
    void load() {
        final CommandMenu menu = new CommandMenu("root", LocalDateTime.now());
        menu.addMenu("sub1", LocalDateTime.now());
        menu.addMenu("sub2", LocalDateTime.now());
        menu.save("abc");
        final CompositeMenu loadedMenu = menu.load("abc");

        final MenuRenderer render = new MenuRenderer(() -> new ConsoleMenuVisitor());
        render.render(loadedMenu.createReport(CompositeMenuSortType.TITLE_ASC));
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

            // (2) 자식의 자신데이터외에 자식들 depth인 sub: [에 데이터가 있는지 확인해야한다
            //     전제 조건이 (1)번 if에서 최소 1개의 자식이 발견되어야지, 자식의 자식을 확인한다.
            if (child != null && character == '[') {
                cursor = load(child, json, cursor);
            }

            // (3) 재귀를 타는 중이라면, 반복문이 json끝날때까지 도는 것이 아니라 ']'를 만날경우, 해당depth를 탈출해야한다.
            if (character == ']') {
                return cursor;
            }

            cursor++;
        }
        return cursor;
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
}
