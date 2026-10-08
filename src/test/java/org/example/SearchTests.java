package org.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.ScrollIntoViewOptions.Block.start;
import static com.codeborne.selenide.ScrollIntoViewOptions.instant;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SearchTests
{


        @BeforeEach
        void setup() {
            Configuration.baseUrl = "https://demoqa.com";
        }

        @Test
        void registrationFormTest()
        {

            open("/automation-practice-form");
            $("#firstName").setValue("Bob");
            $("#lastName").setValue("Smith");
            $("#userEmail").setValue("bobRod1989@gmail.com");
            $("#genterWrapper").$(byText("Male")).click();
            $("#userNumber").setValue("8312314213");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOption("February");
            $(".react-datepicker__year-select").selectOption("1989");
            $(".react-datepicker__day--028").click();
            $("#subjectsInput").setValue("Computer science").pressEnter();
            $("#hobbies-checkbox-3").click();
            $("#uploadPicture").uploadFromClasspath("img/enot.jpg");
            $("#currentAddress").setValue("ulitsa Lenina, dom 15, kvartira 24\n Kazan, Respublika Tatarstan\n  420107\n RUSSIA");
            $("input#react-select-3-input").setValue("Haryana").pressEnter();
            $("input#react-select-4-input").setValue("Karnal").pressEnter();
            $("#submit").click();

            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".modal-body").shouldHave(text("Bob Smith"));
            $(".modal-body").shouldHave(text("bobRod1989@gmail.com"));
            $(".modal-body").shouldHave(text("Male"));
            $(".modal-body").shouldHave(text("8312314213"));
            $(".modal-body").shouldHave(text("28 February,1989"));
            $(".modal-body").shouldHave(text("Computer Science"));
            $(".modal-body").shouldHave(text("Music"));
            $(".modal-body").shouldHave(text("enot.jpg"));
            $(".modal-body").shouldHave(text("ulitsa Lenina, dom 15, kvartira 24 Kazan, Respublika Tatarstan 420107 RUSSIA"));
            $(".modal-body").shouldHave(text("Haryana Karnal"));

        }

        @Test
        void requiredFields(){
            open("/automation-practice-form");
            $("#firstName").setValue("Bob");
            $("#lastName").setValue("Smith");
            $("#genterWrapper").$(byText("Male")).click();
            $("#userNumber").setValue("8312314213");
            $("#submit").scrollIntoView(instant().block(start)).click();
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        }
        @Test
        //Баг: удаление даты стирает всю страницу
        void dateOfBirthBugTest() {
        open("/automation-practice-form");
        $("#firstName").setValue("Bob");
        $("#lastName").setValue("Smith");
        $("#userEmail").setValue("bobRod1989@gmail.com");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("8312314213");
        SelenideElement inputField = $("#dateOfBirthInput");
        $("#dateOfBirthInput").click();

        for (int i = 0; i < 11; i++) {
            inputField.sendKeys(Keys.BACK_SPACE);
        }
        $("#firstName").shouldNot(exist);
        }

        @Test
        void withoutFirstName(){
            open("/automation-practice-form");
            $("#lastName").setValue("Smith");
            $("#userEmail").setValue("bobRod1989@gmail.com");
            $("#genterWrapper").$(byText("Male")).click();
            $("#userNumber").setValue("8312314213");
            $("#submit").scrollIntoView(instant().block(start)).click();
            $(".modal-open").shouldNot(exist);
        }

        @Test
        void withoutLastName(){
            open("/automation-practice-form");
            $("#firstName").setValue("Bob");
            $("#userEmail").setValue("bobRod1989@gmail.com");
            $("#genterWrapper").$(byText("Male")).click();
            $("#userNumber").setValue("8312314213");
            $("#submit").scrollIntoView(instant().block(start)).click();
            $(".modal-open").shouldNot(exist);
        }

        @Test
        void invalidEmail(){
            open("/automation-practice-form");
            $("#firstName").setValue("Bob");
            $("#lastName").setValue("Smith");
            $("#userEmail").setValue("bobRod1989gmail.com");
            $("#genterWrapper").$(byText("Male")).click();
            $("#userNumber").setValue("8312314213");
            $("#submit").scrollIntoView(instant().block(start)).click();
            $(".modal-open").shouldNot(exist);
        }

        @Test
        void invalidPhoneNumber(){
            open("/automation-practice-form");
            $("#firstName").setValue("Bob");
            $("#lastName").setValue("Smith");
            $("#userEmail").setValue("bobRod1989@gmail.com");
            $("#genterWrapper").$(byText("Male")).click();
            $("#userNumber").setValue("831231413");
            $("#submit").scrollIntoView(instant().block(start)).click();
            $(".modal-open").shouldNot(exist);
        }
        @Test
        void simpleForm(){
            open("/text-box");
            $("#userName").setValue("Bob Smith Jr.");
            $("#userEmail").setValue("bobRod1989@gmail.com");
            $("#currentAddress").setValue("123 Maple Avenue, Apt. 4B");
            $("#permanentAddress").setValue("Pushkin Street, Kolotushkin’s house");
            $("#submit").scrollIntoView(instant().block(start)).click();

            $("#output").shouldHave(text("Bob Smith Jr."));
            $("#output").shouldHave(text("bobRod1989@gmail.com"));
            $("#output").shouldHave(text("123 Maple Avenue, Apt. 4B"));
            $("#output").shouldHave(text("Pushkin Street, Kolotushkin’s house"));

        }
        @Test
            void incorrectSimpleForm(){
            open("/text-box");
            $("#userName").setValue("Bob Smith Jr.");
            $("#userEmail").setValue("bobRod1989gmail.com");
            $("#currentAddress").setValue("123 Maple Avenue, Apt. 4B");
            $("#permanentAddress").setValue("Pushkin Street, Kolotushkin’s house");
            $("#submit").scrollIntoView(instant().block(start)).click();
            $("#output").shouldNotBe(visible);
        }

}