package hello.itemservice.converter.formatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.text.NumberFormatter;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();
    @Test
    void parse() throws ParseException {
        Number parse = formatter.parse("1,000", Locale.KOREA);
        assertThat(parse).isEqualTo(1000L);
    }
    @Test
    void print() {
        String print = formatter.print(1000, Locale.KOREA);
        assertThat(print).isEqualTo("1,000");
    }
}