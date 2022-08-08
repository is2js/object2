package functioalInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RacingGameResultTest {
    //@org.junit.jupiter.api.DisplayName("")
    @Test
    void 랭킹기능_테스트를_위해_각_상태변경_메서드를_여러번_호출() {
        final List<Car> cars = new ArrayList<>();

        Car 재성 = new Car(() -> true, "재성");
        //move를 3번해서 position3을 만든다.
        재성 = 재성.move()
            .move()
            .move();
        Car 재경 = new Car(() -> true, "재경");
        //move를 5번해서 position3을 만든다.
        재경 = 재경.move()
            .move()
            .move()
            .move()
            .move();

        cars.add(재성);
        cars.add(재경);
    }

    //@org.junit.jupiter.api.DisplayName("")
    @Test
    void 랭킹기능_테스트를_위해_특정상태의_객체를_만든다() {
        final List<Car> cars = Arrays.asList(
            new Car("재성", 3),
            new Car("재경", 5)
        );
    }
}
