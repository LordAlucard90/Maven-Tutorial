package maven.tutorial.example;

public class ExampleService {
    public ExampleEntity createExample(Long id, String example) {
        var entity = new ExampleEntity();
        entity.setId(id);
        entity.setExample(example);
        return entity;
    }
}
