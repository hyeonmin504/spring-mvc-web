package hello.itemservice.converter;

import hello.itemservice.converter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
class StringToIntegerConverterTest {
    @Test
    public void stringToInteger() throws Exception {
        //given
        StringToIntegerConverter converter = new StringToIntegerConverter();
        //when
        Integer result = converter.convert("10");
        //then
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void StringToIpPort() throws Exception {
        //given
        IpPortToStringConverter converter = new IpPortToStringConverter();
        IpPort source = new IpPort("127.0.0.1", 8080);
        String result = converter.convert(source);
        //when
        //then
        assertThat(result).isEqualTo("127.0.0.1:8080");
    }

    @Test
    public void conversionService() throws Exception {
        //given
        DefaultConversionService conversionService = new DefaultConversionService();
        //when
        //then
    }
}