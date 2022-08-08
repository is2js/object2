package todo;

public class JsonVisitor implements Visitor {

    private String result = "";

    @Override
    public void drawTask(final CompositeTask task, final int depth) {
        result += "{";
        result += " title: \"" + task.getTitle() + "\",";
        result += " date: \"" + task.getDate() + "\",";
        result += " isComplete: " + task.isComplete() + ",";
        result += " sub: [ ";
        // 자식들이 다음줄부터 자신의padding + { 로 시작될 것이다.
    }

    @Override
    public void end(final int depth, final boolean isEnd) {
        result += " ]";
        result += "}";
        if (!isEnd) {
            result += ",";
        }
    }

    public String getJson() {
        return result;
    }
}
