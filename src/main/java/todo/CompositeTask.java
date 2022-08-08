package todo;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class CompositeTask {

    private String title;

    private LocalDateTime date;
    private Boolean isComplete = false; // task(파일) 필드
    private final Set<CompositeTask> list = new HashSet<>(); // tasks(폴더) 필드

    public CompositeTask(final String title, final LocalDateTime date) {
        setTitle(title);
        setDate(date);
    }

    public void removeAll() {
        // 삭제전 존재검증 -> early return
        if (list.isEmpty()) {
            return;
        }
        // 자식들 재귀돌면서 삭제
        for (final CompositeTask subTask : list) {
            subTask.removeAll();
        }
        // 자신의 끝처리로서, list자식들을 완전히 clear()
        list.clear();
    }

    public void toggle() {
        isComplete = !isComplete;
    }

    public CompositeTask addTask(final String title, final LocalDateTime date) {
        final CompositeTask task = new CompositeTask(title, date);
        list.add(task);
        return task;
    }

    public void remove(final CompositeTask task) {
        list.remove(task);
    }

    public TaskReport getReport(final TaskReportSortType type) {
        //파일이 하는 일 -> 내 자신의 사본 반환
        final TaskReport report = new TaskReport(this, type);
        //폴더가 하는 일 -> 내 자식들의 사본들을 재귀로 만들어 만들어 반환
        for (CompositeTask task : list) {
            report.add(task.getReport(type)); //현재report의 자식이 재귀로 호출하여, 그 report자식들을 만들어넣는다.
        }

        return report;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Boolean isComplete() {
        return isComplete;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }
}
