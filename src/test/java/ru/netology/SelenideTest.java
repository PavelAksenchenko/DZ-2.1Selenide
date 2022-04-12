package ru.netology;


import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactTextCaseSensitive;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {

    @BeforeEach
    void seUp() {
        open("http://localhost:9999");
    }

    @Test
    void getTrueInputValidForm() {
        SelenideElement form = $("[class='form form_size_m form_theme_alfa-on-white']");
        form.$("[type=text]").setValue("Иванов Николай");
        form.$("[type=tel]").setValue("+79882223345");
        form.$("[class=checkbox__box]").click();
        form.$("[type=button]").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void errorExpectedWhenEmptyFieldName() {
        $("[type='text']").setValue("");
        $("[type='tel']").setValue("+79882223345");
        $("[class='checkbox__box']").click();
        $("[type='button']").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave
                (exactTextCaseSensitive("Поле обязательно для заполнения"));
    }

    @Test
       void errorExpectedWhenEmptyFieldPhone() {
        $("[type='text']").setValue("Иванов Николай");
        $("[type='tel']").setValue("");
        $("[class='checkbox__box']").click();
        $("[type='button']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave
                (exactTextCaseSensitive("Поле обязательно для заполнения"));
    }

    @Test
    void errorExpectedWhenInputIncorrectName() {
        $("[type='text']").setValue("Nikolay");
        $("[type='tel']").setValue("+79882223345");
        $("[class='checkbox__box']").click();
        $("[type='button']").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave
                (exactTextCaseSensitive("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void errorExpectedWhenInputIncorrectTelNumber() {
        $("[type='text']").setValue("Иванов Николай");
        $("[type='tel']").setValue("+7 988 222 33 45");
        $("[class='checkbox__box']").click();
        $("[type='button']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave
                (exactTextCaseSensitive("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void errorExpectedWhenUncheckedCheckbox() {
        $("[type='text']").setValue("Иванов Николай");
        $("[type='tel']").setValue("+79882223345");
//        $("[class='checkbox__box']").click();
        $("[type='button']").click();
        $("[data-test-id=agreement].input_invalid").shouldHave
                (exactTextCaseSensitive("Я соглашаюсь с условиями обработки и использования моих " +
                        "персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}

