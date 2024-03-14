package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    
//    @Test
//    public void helloMessage() throws Exception {
//        //given
//        String hello = ms.getMessage("hello", null, null);
//        assertThat(hello).isEqualTo("안녕");
//        //when
//        //then
//    }
//    @Test
//    public void notFoundMessage() throws Exception {
//        //given
//        assertThatThrownBy(() -> ms.getMessage("no_code",null, null))
//                .isInstanceOf(NoSuchMessageException.class);
//        //when
//        //then
//    }
}
