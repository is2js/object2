package todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tasks {

    // 생성자를 통해 외부에서 받지만, -> not final + setter로 변경가능성도 있다면,
    // 코드 중복이 발생한다.
    private String title; // 폴더 이름
    private final Set<Task> list = new HashSet<>();

    public Tasks(final String title) {
        setTitle(title);
    }


    public void setTitle(final String title) {
        this.title = title;
    }

    public void addTask(final String title, final LocalDateTime date) {
        list.add(new Task(title, date));
    }

    public void remove(final Task task) {
        list.remove(task);
    }

    // task들은 렌더링 되려면 외부에 제공해줘야한다.
    // -> 내부요소들이 원래 외부에서 조작가능한  public메서드들을 가진 객체이므로
    // --> 생성자를 통한 얕은복사만 해준다.
    // -> 외부에 제공할 때, 다양한 [제공방식]을 적용한다면, getter의 인자로 받는다.
    public List<Task> getList(final SortType type) {
        final List<Task> tasks = new ArrayList<>(list);
        tasks.sort((a,b) -> type.compare(a, b));
        return tasks;
    }
}
