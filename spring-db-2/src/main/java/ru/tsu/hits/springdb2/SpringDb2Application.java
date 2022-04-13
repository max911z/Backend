package ru.tsu.hits.springdb2;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.tsu.hits.springdb2.dto.CreateUpdateUserDto;
import ru.tsu.hits.springdb2.service.UserService;

import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringDb2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringDb2Application.class, args);


    }

}
