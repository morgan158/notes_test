package models;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    private Long id;
    private String title;
    private LocalDate date;
    private String text;

}
