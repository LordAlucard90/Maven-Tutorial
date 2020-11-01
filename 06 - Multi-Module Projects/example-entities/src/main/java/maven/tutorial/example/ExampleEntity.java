package maven.tutorial.example;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ExampleEntity {
    @Id
    private Long id;
    private String example;
}
