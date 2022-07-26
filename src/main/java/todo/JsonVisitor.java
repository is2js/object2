package todo;

public class JsonVisitor implements Visitor {

    @Override
    public void drawTask(final CompositeTask task, final int depth) {
        String padding = getPadding(depth);
        System.out.println(padding + "{");
        System.out.println(padding + " title: \"" + task.getTitle() + "\",");
        System.out.println(padding + " date: \"" + task.getDate() + "\",");
        System.out.println(padding + " isComplete: " + task.isComplete() + ",");
        System.out.println(padding + " sub: [ ");
        // 자식들이 다음줄부터 자신의padding + { 로 시작될 것이다.
    }

    private String getPadding(final int depth) {
        String padding = "";
        for (int i = 0; i < depth; i++) {
            padding += " ";
        }
        return padding;
    }

    @Override
    public void end(final int depth) {
        // 자신 + 자식들은 모두 종결처리를 같이 해줘야한다.
        // 자식들이 다돌고, 나한테 왔을 경우, ] 대괄호로 닫아주고 + 나의 중괄호 또한 닫아줘야한다.
        // -> 내 턴의 중괄호는 닫고 콤마까지 찍어준다.

        final String padding = getPadding(depth);
        System.out.println(padding + " ]");
        System.out.println(padding + "},");
    }
}
