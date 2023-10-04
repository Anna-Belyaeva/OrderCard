import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class OrderCardTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));   //генерация даты:тек.дата, доб.дней, преобразовали в строку
    }

    @Test
    public void shouldOrderACard() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Петрозаводск");
        String currentDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);  //очистили поле даты
        $("[data-test-id='date'] input").setValue(currentDate);   ///установили дату
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+70000000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))   /// должна появиться иконка успешной брони
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));   ///иконка д. сод-ть текст
    }

    @Test
    public void shouldSetMeetingDateAWeekLater() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Пе");
        $$(".menu-item__control").findBy(text("Петрозаводск")).click();  //выбор из выпад-го списка
        String currentDate = generateDate(7, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);  //очистили поле даты
        $("[data-test-id='date'] input").setValue(currentDate);   ///установили дату
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+70000000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))   /// должна появиться иконка успешной брони
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));   ///иконка д. сод-ть текст
    }
}
