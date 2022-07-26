package todo;

import java.time.LocalDateTime;

public class Task {

    private String title;
    private LocalDateTime date;
    private Boolean isComplete = false;

    Task(final String title, final LocalDateTime date) {
        this.title = title;
        this.date = date;
    }

    // 1. 상태변화하는 not final필드에는 업데이트용 메서드를 만든다.
    //    그러나 boolean으로 제약걸린 필드는 setter를 바로 주면 안되고 void toggle()을 만든다.
    public void toggle() {
        isComplete = !isComplete;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
