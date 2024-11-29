package store;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 5개",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개"
            );
        });
    }

    @Test
    void 프로모션_상품_구매_일반() {
        assertSimpleTest(() -> {
            run("[콜라-3],[에너지바-5]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈9,000");
        });
    }

    @Test
    void 프로모션_상품_구매_초과() {
        assertSimpleTest(() -> {
            run("[콜라-10],[에너지바-5]", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("");
        });
    }

    @Test
    void 프로모션_상품_구매_초과2() {
        assertSimpleTest(() -> {
            run("[콜라-12],[에너지바-5]", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("");
        });
    }

    @Test
    void 더블_프로모션_상품_구매() {
        assertSimpleTest(() -> {
            run("[콜라-10],[감자칩-5]", "Y", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("");
        });
    }

    @Test
    void 다수_일반_프로모션_상품_구매() {
        assertSimpleTest(() -> {
            run("[콜라-5],[감자칩-3],[물-2],[오렌지주스-1]", "Y", "Y", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("");
        });
    }

    @Test
    void 프로모션_상품_구매_미달() {
        assertSimpleTest(() -> {
            run("[콜라-5],[에너지바-5]", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("");
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("예외 테스트_재고 수량 초과")
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    @DisplayName("예외 테스트_존재하지 않는 품목")
    void 예외_테스트2() {
        assertSimpleTest(() -> {
            runException("[껌-1]", "N", "N");
            assertThat(output()).contains("[ERROR]");
        });
    }

    @Test
    @DisplayName("예외 테스트_입력 포맷팅 오류")
    void 예외_테스트3() {
        assertSimpleTest(() -> {
            runException(";콜라-2{", "N", "N");
            assertThat(output()).contains("[ERROR]");
        });
    }

    @Test
    @DisplayName("예외 테스트_수량 0개 구매 예외")
    void 예외_테스트4() {
        assertSimpleTest(() -> {
            runException("[콜라-0]", "N", "N");
            assertThat(output()).contains("[ERROR]");
        });
    }

    @Test
    @DisplayName("예외 테스트_빈 문자 상품 구매 시도 예외")
    void 예외_테스트5() {
        assertSimpleTest(() -> {
            runException("[-2]", "N", "N");
            assertThat(output()).contains("[ERROR]");
        });
    }

    @Test
    @DisplayName("예외 테스트_Y/N 이외 대답 예외")
    void 예외_테스트6() {
        assertSimpleTest(() -> {
            runException("[콜라-2]", "K", "ㅏ");
            assertThat(output()).contains("[ERROR]");
        });
    }

    @Override
    public void runMain() {
        try {
            Application.main(new String[]{});
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
