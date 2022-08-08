package todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandTask {

    private final CompositeTask task;
    private final List<Command> commands = new ArrayList<>();
    private int cursor = 0;
    private final Map<String, String> saved = new HashMap<>();


    public void save(String key) {
        final JsonVisitor visitor = new JsonVisitor();
        final Renderer renderer1 = new Renderer(() -> visitor);
        renderer1.render(task.getReport(TaskReportSortType.TITLE_ASC));

        saved.put(key, visitor.getJson());
    }

    public void load(String key) {
        checkExists(key);
        final String json = saved.get(key);
        checkJsonFormat(json);
        task.removeAll();

        //자신의 필드채우기
        // string에서 title가 있는 prefix + 출발시작index를 cursor -> [원하는 데이터 시작cursor]를 찾는다.
        int cursor = 0;
        task.setTitle(getValue(json, cursor = next(json, cursor, "title: \"")));
        task.setDate(LocalDateTime.parse(getValue(json, cursor = next(json, cursor, "date: \""))));
        // 자식시작직전까지 처리해야하므로, sub: [  부분은 데이터는 안뽑아내되, 커서만 이동시킨다.
        cursor = next(json, cursor, "sub: [");

        // 자식정보가 있는 만큼만 자식 반복문돌기
        // -> composite객체의 생성은, root만 직접채우고, 자식들부터 재귀를 쓴다!!
        load(json, cursor, task);
    }

    private int load(final String json, int cursor, final CompositeTask parent) {
        final String currentParent = parent.getTitle();
        final int length = json.length();
        CompositeTask child = null;
        while (cursor < length) {
            //판단은 cursor위치의 charactor로 판단한다.
            final char character = json.charAt(cursor);
            //(1) sub: [담에 {가 나왔다면, 자식의 시작이다.
            if (character == '{') {
                // title과 date를 찾은 다음, root객체에 add 해준다.
                // (2) add의 결과는, 자식객체다. 자식객체를 반복문위 업데이트변수로 받아, 첫자식을 확인한다.
                child = parent.addTask(
                    getValue(json, cursor = next(json, cursor, "title: \"")),
                    LocalDateTime.parse(getValue(json, cursor = next(json, cursor, "date: \"")))
                );
            }

            //(2) [로 시작하면 자식의 다음자식의 시작이다.
            //  -> child가 차있다 -> sub: [ 이전에 [title, date발견하여, 현재{ }요소가 [add되는 객체로 parent자격]이 있따
            if (character == '[' && child != null) {
                cursor = load(json, cursor, child);
            }

            //(3) 재귀의 종착역 -> level선상 마지막요소의 끝을 만났다면, return해주되 cursor를 반환한다
            if ( character == ']') { // 레벨선상 마지막 ]로 수정
                return cursor;
            }
            cursor++;
        }

        return cursor;
    }

    private int next(final String json, int cursor, final String target) {
        final int targetStartCursor = json.indexOf(target, cursor);
        // 만약 못찾았으면 -1이 반환된다. -> 못찾은 커서의 상태를 -1로 표기한다.
        if (targetStartCursor == -1) {
            return -1;
        }
        // 찾은 상태의 startIndex + prefix길이를 더하여, data의 시작index를 cursor로 반환한다.
        return targetStartCursor + target.length();
    }

    private String getValue(final String json, final int cursor) {
        return json.substring(cursor, json.indexOf("\"", cursor + 1));
    }

    private void checkJsonFormat(final String json) {
        if (json.trim().charAt(0) != '{') {
            throw new RuntimeException("The format of the JSON is not allowed for loading to CommandTask");
        }
    }

    private void checkExists(final String key) {
        if (!saved.containsKey(key)) {
            throw new RuntimeException("You have to json before load.");
        }
    }

    public void redo() {
        if (cursor == commands.size() - 1) {
            return;
        }
        commands.get(++cursor).execute(task);

    }

    public void undo() {
        if (cursor < 0) {
            return;
        }

        commands.get(cursor--).undo(task);
    }


    public CommandTask(final String title, final LocalDateTime date) {
        task = new CompositeTask(title, date);
    }

    private void addCommand(final Command cmd) {
        for (int i = commands.size() - 1; i > cursor; i--) {
            commands.remove(i);
        }
        commands.add(cmd);
        cursor = commands.size() - 1;
        cmd.execute(task);
    }

    public void toggle() {
        addCommand(new Toggle());
    }

    public void addTask(final String title, final LocalDateTime date) {
        addCommand(new Add(title, date));
    }

    public void remove(final CompositeTask task) {
        addCommand(new Remove(task));
    }

    public void setTitle(final String title) {
        addCommand(new Title(title));
    }

    public void setDate(final LocalDateTime date) {
        addCommand(new Date(date));
    }

    public TaskReport getReport(final TaskReportSortType type) {
        return task.getReport(type);
    }

    public String getTitle() {
        return task.getTitle();
    }

    public LocalDateTime getDate() {
        return task.getDate();
    }

    public Boolean isComplete() {
        return task.isComplete();
    }
}
