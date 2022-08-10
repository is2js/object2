package menu;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        final CompositeMenu 의료진_소개 = new CompositeMenu("의료진 소개", LocalDateTime.now());
        의료진_소개.addMenu("대표원장", LocalDateTime.now());
        의료진_소개.addMenu("부원장", LocalDateTime.now());

        final CompositeMenu 한의원_소개 = new CompositeMenu("한의원 소개", LocalDateTime.now());
        한의원_소개.addMenu("한의원 내부", LocalDateTime.now());
        한의원_소개.addMenu("오시는 길", LocalDateTime.now());

        final CompositeMenu 게시판 = new CompositeMenu("게시판", LocalDateTime.now());
        게시판.addMenu("치료후기", LocalDateTime.now());
        게시판.addMenu("상담게시판", LocalDateTime.now());

        final CompositeMenu root = new CompositeMenu("메뉴", LocalDateTime.now());
        root.addMenu(의료진_소개);
        root.addMenu(한의원_소개);
        root.addMenu(게시판);

        final MenuReport report = root.createReport(CompositeMenuSortType.DATE_ASC);

//        final MenuRenderer renderer = new MenuRenderer(() -> new ConsoleMenuVisitor());
//        renderer.render(report);
        final MenuRenderer renderer2 = new MenuRenderer(() -> new JsonMenuVisitor());
        renderer2.render(report);

//        final Gson gson = new Gson();
//        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        final String menuString = gson.toJson(root);
//        System.out.println(menuString);
//        final String reportString = gson.toJson(report);
//        System.out.println(reportString);
    }
}
