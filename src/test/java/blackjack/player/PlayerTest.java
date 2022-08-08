package blackjack.player;

import static blackjack.testutil.Fixtures.스페이드_2;
import static org.assertj.core.api.Assertions.assertThat;

import blackjack.Deck;
import blackjack.state.Ready;
import blackjack.strategy.RandomCardGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PlayerTest {

    @Test
    void draw____ready상태의_player가_draw_카드를_1장만_받으면_아직_ready상태다() {
        //given(조건)
        final Player player = new Player();

        //when(행위)
        player.draw(스페이드_2);

        //then(검증)
        assertThat(player.getState()).isInstanceOf(Ready.class);
    }

    @Test
    void draw____ready상태의_player가_draw_카드를_2장_받으면_더이상_Ready상태가_아니다() {
        //given(조건)
        final Player player = new Player();

        //when(행위)
        player.draw(스페이드_2);
        player.draw(스페이드_2);

        //then(검증)
        assertThat(player.getState()).isNotInstanceOf(Ready.class);
    }

    @Test
    void draw___ready상태의_player가_draw_Deck을_전달받아_draw하면_카드1장을_받아_ready상태가_된다() {
        //given(조건)
        final Player player = new Player();
        final Deck deck = new Deck(new RandomCardGenerator());

        //when(행위)
        player.draw(deck);

        //then(검증)
        assertThat(player.getState()).isInstanceOf(Ready.class);
    }


}
