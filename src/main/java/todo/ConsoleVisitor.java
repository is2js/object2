package todo;

public class ConsoleVisitor implements Visitor {

    @Override
    public void drawTask(final CompositeTask task, final int depth) {
        String padding = "";
        for (int i = 0; i < depth; i++) {
            padding += "-";
        }
        System.out.println(padding
            + (task.isComplete() ? "[v] " : "[ ] ")
            + task.getTitle()
            + "("
            + task.getDate()
            + ")"
        );
    }

    @Override
    public void end(final int depth) {

    }
}
