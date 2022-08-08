package todo;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. 컴포지트 객체는 root부터 생성한다.
//        final CompositeTask root = new CompositeTask("Root", LocalDateTime.now());
        // 7. CommandTask로 변환
        final CommandTask root = new CommandTask("Root", LocalDateTime.now());
        // 2. root에 자식들sub을 집어넣는다.
        root.addTask("sub1", LocalDateTime.now());
        root.addTask("sub2", LocalDateTime.now());
        final TaskReport report = root.getReport(TaskReportSortType.TITLE_ASC);
        final List<TaskReport> reportList = report.getList();
        final CompositeTask sub1 = reportList.get(0).getTask();
        final CompositeTask sub2 = reportList.get(1).getTask();
        sub1.addTask("sub1_sub1", LocalDateTime.now());
        sub1.addTask("sub1_sub2", LocalDateTime.now());
        sub2.addTask("sub2_sub1", LocalDateTime.now());
        sub2.addTask("sub2_sub2", LocalDateTime.now());
        // 콘솔로 먼저 확인
        final Renderer renderer1 = new Renderer(() -> new ConsoleVisitor());
        renderer1.render(root.getReport(TaskReportSortType.TITLE_ASC));
        //11. save/load후 report만들어서 출력
        root.save("k");
        root.load("k");

        final JsonVisitor visitor = new JsonVisitor();
        final TaskReport report2 = root.getReport(TaskReportSortType.TITLE_ASC);
        new Renderer(() -> visitor).render(report2);
        System.out.println(visitor.getJson());

        //10. composite root(커맨더홀더)를 save하고 찍어보기
//        final JsonVisitor visitor = new JsonVisitor();
//        final Renderer renderer1 = new Renderer(() -> visitor);
//        renderer1.render(root.getReport(TaskReportSortType.TITLE_ASC));
//        System.out.println(visitor.getJson());

        //8.
//        final Renderer renderer1 = new Renderer(() -> new JsonVisitor());
//        renderer1.render(root.getReport(TaskReportSortType.TITLE_ASC));

        //9. 커맨드객체 root에서 addTask(내부 addCommand함)에 대한 undo하고, 다시 출력해보자
//        root.undo();
//        renderer1.render(root.getReport(TaskReportSortType.TITLE_ASC));
//        root.undo();
//        renderer1.render(root.getReport(TaskReportSortType.TITLE_ASC));
//        root.redo();
//        renderer1.render(root.getReport(TaskReportSortType.TITLE_ASC));
//        // 3. 추가한 자식에, 그 자식들을 추가하려면, 추가한 자식을 얻어야하는데
//        //    -> 컴포짓객체는 자식반환기능을 TaskReport에게 위임했으므로
//        //    --> report를 만들고, list를 반환받아 꺼내쓰는 수 밖에 없다.
//        //   (add가 추가한 것을 그대로 외부로 반환해주면, 저장된 것을 바로 수정도 가능)
//        final TaskReport report = root.getReport(TaskReportSortType.TITLE_ASC);
//        final List<TaskReport> reportList = report.getList();
//        // 4. 자식report -> get자신 == getTask()를 호출해서 꺼내 쓴다.
//        final CompositeTask sub1 = reportList.get(0).getTask();
//        final CompositeTask sub2 = reportList.get(1).getTask();
//
//        // 5. report를 통해 list -> get(index) -> getTask() -> 자식 task 중 1개를 꺼냈다면 조작할 수 있다.
//        // -> 얕은 복사된 list에서 객체요소를 뽑아 조작하면, 실제로 원본 객체가 조작된다.
//        sub1.addTask("sub1_sub1", LocalDateTime.now());
//        sub1.addTask("sub1_sub2", LocalDateTime.now());
//        sub2.addTask("sub2_sub1", LocalDateTime.now());
//        sub2.addTask("sub2_sub2", LocalDateTime.now());

        // 6. taskDraw출력해보기
//        final Renderer renderer1 = new Renderer(() -> new ConsoleVisitor());
//        renderer1.render(root.getReport(TaskReportSortType.TITLE_DESC));
//        final Renderer renderer2 = new Renderer(() -> new JsonVisitor());
//        renderer2.render(root.getReport(TaskReportSortType.TITLE_ASC));
    }
}
