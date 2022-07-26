package menu;

import java.util.function.Supplier;

public class MenuRenderer {


    private final Supplier<MenuVisitor> factory;

    public MenuRenderer(final Supplier<MenuVisitor> factory) {
        this.factory = factory;
    }

    public void render(final MenuReport report) {
        // 내부에 depth라는 누적하여 변하는 인자가 필요할 땐, 꼬리재귀로서
        // 전체 로직을 메서드 추출한다.
        render(factory.get(), report, 0);
    }

    private void render(final MenuVisitor menuVisitor, final MenuReport report, final int depth) {
        // 자신이 할일
        menuVisitor.drawMenu(report.getCompositeMenu(), depth);

        // (필요시) 자식들 순회 전 정렬
        // 자식들 순회하며 재귀호출
        for (final MenuReport subReport : report.getReports()) {
            render(menuVisitor, subReport, depth + 1);
        }

        // 종결처리(결과물 반환 or 출력마무리)
        menuVisitor.endMenu(depth);
    }
}
