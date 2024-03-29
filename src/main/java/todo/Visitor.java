package todo;

public interface Visitor {

    void drawTask(CompositeTask task, int depth);

    void end(int depth, final boolean isEnd);
}
