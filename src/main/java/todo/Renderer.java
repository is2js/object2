package todo;

import java.util.List;
import java.util.function.Supplier;

public class Renderer {

    private final Supplier<Visitor> factory;

    public Renderer(final Supplier<Visitor> factory) {
        this.factory = factory;
    }

    public void render(final TaskReport report){
        render(factory.get(), report, 0, true);
    }

    private void render(final Visitor visitor,
                        final TaskReport report,
                        final int depth,
                        final boolean isEnd) {
        visitor.drawTask(report.getTask(), depth);

        final List<TaskReport> subReports = report.getList();
        int i = subReports.size();
        for (final TaskReport subReport : subReports) {
            render(visitor, subReport, depth + 1, --i == 0);
        }
        visitor.end(depth, isEnd);
    }
}
