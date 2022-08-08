package functioalInterface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StringCalculatorTest {

    @Test
    void 숫자_하나____1() {
        //given & when
        final int actual = StringCalculator.splitAndSum("1");

        //then
        assertThat(actual).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,1", "2,2","3,3"})
    void 숫자_하나____2를_포함한_여러_케이스(final String text, final int expected) {
        //when
        final int actual = StringCalculator.splitAndSum(text);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    //@org.junit.jupiter.api.DisplayName("")
    @Test
    void 컴마_구분자() {
        //given
        final int expected = 3;

        //when
        final int actual = StringCalculator.splitAndSum("1,2");

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 콜론_구분자() {
        //given
        final int expected = 3;

        //when
        final int actual = StringCalculator.splitAndSum("1:2");

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 커스텀_구분자() {
        //given
        final String text = "//;\n1;2";
        final int expected = 3;

        //when
        final int actual = StringCalculator.splitAndSum(text);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    // 요구사항이 복잡할 땐, 단계별 학습테스트를 아래서 시행하면 된다.

    //@org.junit.jupiter.api.DisplayName("")
    @Test
    void split_커스텀_구분자_구분하기() {
        //given
        final String text = "//;\n1;2";

        //when
        //배열의 반환은 바로  actual로 잡지말고, 지역변수로 잡은 뒤
        //인덱싱을 통해서 확인하자.
        final String[] values = text.split("\n");

        //then
        assertAll(
          () -> assertThat(values[0]).isEqualTo("//;"),
          () -> assertThat(values[1]).isEqualTo("1;2")
        );
    }
    @Test
    void split_커스텀_구분자_추출하기() {
        //given
        final String text = "//;\n1;2";

        //when
        //배열의 반환은 바로  actual로 잡지말고, 지역변수로 잡은 뒤
        //인덱싱을 통해서 확인하자.
        final String actual = text.split("\n")[0].replace("//", "");

        //then
        assertThat(actual).isEqualTo(";");
    }

    @Test
    void split_커스텀_구분자_뒤_text_추출하기() {
        //given
        final String text = "//;\n1;2";

        //when
        //배열의 반환은 바로  actual로 잡지말고, 지역변수로 잡은 뒤
        //인덱싱을 통해서 확인하자.
        final String actual = text.split("\n")[1];

        //then
        assertThat(actual).isEqualTo("1;2");
    }

    //@org.junit.jupiter.api.DisplayName("")
    @Test
    void splitAndSum_인자를_통해_기존로직인_컴마와콜론_이외_또다른_케이스_인지_확인() {
        //given
        final String text = "//;\n1;2;3";

        //when
        final boolean actual = text.startsWith("//");

        //then
        Assertions.assertThat(actual).isTrue();
    }

    //@org.junit.jupiter.api.DisplayName("")
    @Test
    void splitAndSum_다른케이스_구분하여_처리() {
        //given
        final String text = "//;\n1;2;3";
        final int expected = 6;

        //when
        final int actual = StringCalculator.splitAndSum(text);

        //then
        assertThat(actual).isEqualTo(expected);
    }


    // 기존에 완성되고 있는 코드에, 여러가지 메서드가 여러일을 하고 있을 텐데
    // 일부로직을 추가하거나 수정한다면
    // [관련 메서드]만 떼어와서 테스트하고 넣어준다.
    // -> 음수확인은, toInts끝나서 숫자가 되고 난 뒤, sum()할때 확인해야하므로
    // -> sum()메서드만 테스트하면 되는데
    // -> private인 경우, TestClass로 복사해와서 테스트하고
    // -> 통째로 처리하고 싶다면, 2222기능을 만들어서 통째로 수정한다.
    private static int sum(final List<Integer> numbers) {
        int total = 0;
        for (final Integer number : numbers) {
            if (number < 0){
                throw new RuntimeException();
            }
            total += number;
        }
        return total;
    }

    @Test
    void sum_음수예외_테스트() {
        Assertions.assertThatThrownBy(() -> sum(Arrays.asList(-1, 2, 3)))
          .isInstanceOf(RuntimeException.class);
    }

    @Test
    void splitAndSum_음수입력시_예외발생() {
        //given
        final String text = "-1:2:3";

        //when
        Assertions.assertThatThrownBy(() -> StringCalculator.splitAndSum(text))
          .isInstanceOf(RuntimeException.class);
    }

    // 들어간 인자의 사전게약 테스트로서 null/empty체크를 하고 싶다면
    // -> 메서드만 떼올 수 없다.
    // -> 일부메서드만 떼올 수 없으면, 바깥 메서드 전체를 복사한 뒤, 테스트하고 수정해준다.
    // -> 수정된 코드만 반영하고, 복사한 메서드는 날려야한다.
    // -> 테스트에 사용한 메서드도 222메서드 -> 수정된 원본메서드로 바꿔줘야한다.


    //@org.junit.jupiter.api.DisplayName("")
    @ParameterizedTest
    @NullAndEmptySource
    void splitAndSum_null_과_Empty_입력시_0을_반환한다(final String text) {
        //when
        final int actual = StringCalculator.splitAndSum(text);

        //then
        assertThat(actual).isEqualTo(0);
    }
}
