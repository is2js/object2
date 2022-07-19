package goodComposition;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DateTimeInterval {

    private final LocalDateTime from;
    private final LocalDateTime to;

    private DateTimeInterval(final LocalDateTime from, final LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public static DateTimeInterval of(final LocalDateTime from, final LocalDateTime to) {
        return new DateTimeInterval(from, to);
    }

    public static DateTimeInterval toMidnight(final LocalDateTime from) {
        //첫날(from) ~ 밤12시까지를 잘라서 새로운 DateTimeInterval을 만든다.
        //첫날의 시간 ~ 첫날날짜로date, 23시59분59초를time으로 한 LocalDateTime
        return new DateTimeInterval(from, LocalDateTime.of(from.toLocalDate(), LocalTime.of(23, 59, 59)));
    }

    public static DateTimeInterval fromMidnight(final LocalDateTime to) {
        return new DateTimeInterval(LocalDateTime.of(to.toLocalDate(), LocalTime.of(0, 0, 0)), to);
    }

    public static DateTimeInterval during(final LocalDate date) {
        return new DateTimeInterval(LocalDateTime.of(date, LocalTime.of(0, 0, 0)),
            LocalDateTime.of(date, LocalTime.of(23, 59, 59)));
    }

    public Duration duration() {
        return Duration.between(from, to);
    }

    public int days() {
        // LocalDate로 바꾸고, 차이 + 1일
        return Period.between(from.toLocalDate(), to.toLocalDate())
            .plusDays(1)
            .getDays();
    }

    public List<DateTimeInterval> split(final int days) {
        List<DateTimeInterval> result = new ArrayList<>();

        // 첫날의 시간 ~ 밤12시까지
        addFirstDay(result, from);

        // 첫날+1일부터 ~ [첫날 + 전체days -2] ex> 1 2 3 4 5 ->  1+1 ~ 1+3(5-2) -> loop는 +1 ~ (days-2)까지 돌아야한다.
        // 종일 (0시 ~ 23시 59분 59초)
        addMiddleDays(days, result, from);

        // 마지막날0시 ~ 마지막날의 시간
        addLastDay(result, to);

        return result;
    }

    private void addFirstDay(final List<DateTimeInterval> result, final LocalDateTime from1) {
        result.add(DateTimeInterval.toMidnight(from1));
    }

    private void addMiddleDays(final int days, final List<DateTimeInterval> result, final LocalDateTime from1) {
        for (int loop = 1; loop < days - 1; loop++) {
            result.add(DateTimeInterval.during(from1.toLocalDate().plusDays(loop)));
        }
    }

    private void addLastDay(final List<DateTimeInterval> result, final LocalDateTime to1) {
        result.add(DateTimeInterval.fromMidnight(to1));
    }

    public List<DateTimeInterval> splitByDay() {
        // split을 2일이상인지, 1일인지에 따라 다르게 로직 작동하는 래핑메서드
        
        // 2일 이상이면, interval list로 쪼개기
        if (days() > 1) {
            return split(days());
        }

        // 1일 이하면, list만들 필요없이, inteval을 나 자신 -> list에 넣어서 ㅂ나환하기
        return Arrays.asList(this);
    }

    public boolean isMiddleDuration() {
        return from.toLocalTime().isAfter(LocalTime.of(0, 0, 0));
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public Duration getLargerDuration() {
        return Duration.between(LocalTime.of(0, 0, 0), to.toLocalTime());
    }

    public Duration getSmallerDuration() {
        return Duration.between(LocalTime.of(0, 0, 0), from.toLocalTime());
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "DateTimeInterval{" +
            "from=" + from +
            ", to=" + to +
            '}';
    }
}
