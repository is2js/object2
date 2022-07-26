package todo;

import java.util.function.Supplier;

public class Renderer {

    private final Supplier<Visitor> factory;

    public Renderer(final Supplier<Visitor> factory) {
        this.factory = factory;
    }

    public void render(final TaskReport report){
        render(factory.get(), report, 0);

    }

    private void render(final Visitor visitor, final TaskReport report, final int depth) {
        visitor.drawTask(report.getTask(), depth);
        for (final TaskReport subReport : report.getList()) {
            render(visitor, subReport, depth + 1);
        }
        visitor.end(depth);
    }
}
