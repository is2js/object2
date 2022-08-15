package menu;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        final CommandMenu 의료진_소개 = new CommandMenu("의료진 소개", LocalDateTime.now());
        의료진_소개.addMenu("대표원장", LocalDateTime.now());
        의료진_소개.addMenu("부원장", LocalDateTime.now());

        final CommandMenu 한의원_소개 = new CommandMenu("한의원 소개", LocalDateTime.now());
        한의원_소개.addMenu("한의원 내부", LocalDateTime.now());
        한의원_소개.addMenu("오시는 길", LocalDateTime.now());

        final CommandMenu 게시판 = new CommandMenu("게시판", LocalDateTime.now());
        게시판.addMenu("치료후기", LocalDateTime.now());
        게시판.addMenu("상담게시판", LocalDateTime.now());

        final CommandMenu root = new CommandMenu("메뉴", LocalDateTime.now());
        root.addMenu(의료진_소개);
        root.addMenu(한의원_소개);
        root.addMenu(게시판);
        root.undo();
        root.redo();

        final CommandMenu holder = new CommandMenu("메뉴", LocalDateTime.now());
        holder.addCommand(new Add("제목", LocalDateTime.now()));
        holder.addCommand(new AddSub(new CommandMenu("자식메뉴1", LocalDateTime.now())));
        holder.addCommand(new Title("바뀐메뉴"));

        final MenuRenderer renderer2 = new MenuRenderer(() -> new ConsoleMenuVisitor());
        renderer2.render(root.createReport(CompositeMenuSortType.DATE_ASC));

        final String json = createJson(root.createReport(CompositeMenuSortType.DATE_ASC));
        System.out.println(json);
    }

    private static String createJson(final MenuReport report) {
        final JsonMenuVisitor jsonMenuVisitor = new JsonMenuVisitor();
        final MenuRenderer renderer = new MenuRenderer(() -> jsonMenuVisitor);
        renderer.render(report);

        return jsonMenuVisitor.getJson();
    }
}
