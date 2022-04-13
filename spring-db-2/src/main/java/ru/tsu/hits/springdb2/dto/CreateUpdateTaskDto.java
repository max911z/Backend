package ru.tsu.hits.springdb2.dto;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@Data
public class CreateUpdateTaskDto {

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByPosition(position = 0)
    private Date creationDate;

    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByPosition(position = 1)
    private Date editDate;

    @CsvBindByPosition(position = 2)
    private String name;

    @CsvBindByPosition(position = 3)
    private String description;

    @CsvBindByPosition(position = 4)
    private String creatorId;

    @CsvBindByPosition(position = 5)
    private String editorId;

    @CsvBindByPosition(position = 6)
    private String projectId;

    @CsvBindByPosition(position = 7)
    private String priority;

    @CsvBindByPosition(position = 8)
    private String timeMark;
}
