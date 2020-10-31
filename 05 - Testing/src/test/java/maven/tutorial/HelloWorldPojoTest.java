package maven.tutorial;

public class HelloWorldPojoTest {
    public void testHello() {
        var hello = new HelloWorld();
        assert "Hello World!".equals(hello.getHello());
    }
}