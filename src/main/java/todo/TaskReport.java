package todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskReport {

    private final CompositeTask task;
    private final TaskReportSortType type;
    private final List<TaskReport> list = new ArrayList<>();

    public TaskReport(final CompositeTask task, final TaskReportSortType type) {
        this.task = task;
        this.type = type;
    }

    public void add(final TaskReport report) {
        list.add(report);
    }

    public String getTitle() {
        return task.getTitle();
    }
    public LocalDateTime getDate() {
        return task.getDate();
    }

    public CompositeTask getTask() {
        return task;
    }

    public List<TaskReport> getList() {
        final List<TaskReport> reports = new ArrayList<>(list);
        reports.sort( (a, b) -> type.compare(a,b));
        return reports;
    }
}
