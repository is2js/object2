package goodComposition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        final Pattern pattern = Pattern.compile("file_check_\\d{1,2}");
        final Matcher matcher = pattern.matcher("file_check_12 dddd");
        if (!matcher.find()) {
            System.out.println("패턴이 발견되지 않았습니다.");
            return;
        }
        final String group = matcher.group();
        System.out.println(group);
    }

}
