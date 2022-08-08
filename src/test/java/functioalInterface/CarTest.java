package functioalInterface;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CarTest {

    @Test
    void 생성자_검사() {
        assertDoesNotThrow(() -> new Car(() -> true, "재성"));

        final Car actual = new Car(() -> true, "재경");
        Assertions.assertThat(actual).isNotNull();
    }

    @Test
    void move_1칸_전진시_1을_응답한다() {
        final Car car = new Car(() -> true, "재성");

        final Car car2 = car.move();

        Assertions.assertThat(car2.getPosition()).isEqualTo(1);
    }

    @Test
    void move_2칸_전진시_2를_응답한다() {
        final Car car = new Car(() -> true, "재성");

        final Car car2 = car.move()
            .move();

        Assertions.assertThat(car2.getPosition()).isEqualTo(2);
    }

    @Test
    void move_전진해도_stop_상태인_경우() {
        final Car car = new Car(() -> false, "재성");

        final Car car2 = car.move();

        Assertions.assertThat(car2.getPosition()).isEqualTo(0);
    }

    @Test
    void move_의_응답값_대신_객체상태로_비교() {
        final Car car = new Car(() -> true, "재성");

        final Car car2 = car.move();

        Assertions.assertThat(car2.getPosition()).isEqualTo(1);
    }

    @Test
    void move_의_상태변화_대신_상태변화한_객체를_응답() {
        final Car car = new Car(() -> true, "재성");

        final Car car_2번움직임 = car.move()
            .move();

        Assertions.assertThat(car_2번움직임.getPosition())
            .isEqualTo(2);
    }
}
