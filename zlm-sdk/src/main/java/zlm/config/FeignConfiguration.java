package zlm.config;

import feign.Contract;
import feign.Request;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class FeignConfiguration {

    /**
     * 启用Fegin自定义注解 如@RequestLine @Param
     * @return
     */
    @Bean
    public Contract feignContract(){
        return new Contract.Default();
    }

    /**
     * 调用feign 接口时 转换参数
     * @return
     */
    @Bean
    public FeignFormatterRegistrar localDataTime2StringFormatRegister() {
        return (registry) -> {
            //添加 LocalDateTime 转换器
            registry.addConverter(LocalDateTime.class, String.class,(@NonNull LocalDateTime source) ->
                    source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            //添加 LocalDate 转换器
            registry.addConverter(LocalDate.class, String.class,(@NonNull LocalDate source) ->
                    source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            //添加 LocalTime 转换器
            registry.addConverter(LocalTime.class, String.class,(@NonNull LocalTime source) ->
                    source.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        };
    }

    @Bean
    public static Request.Options requestOptions(ConfigurableEnvironment env) {
        return new Request.Options(10000, 60000);
    }

}
