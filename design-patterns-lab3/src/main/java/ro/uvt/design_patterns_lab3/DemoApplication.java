package ro.uvt.design_patterns_lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ro.uvt.difexamples.ClientComponent;
import ro.uvt.difexamples.SingletonComponent;
import ro.uvt.difexamples.TransientComponent;

@SpringBootApplication(scanBasePackages = "ro.uvt") // difexamples paketini de tara
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

        // Prototype: her çağrıda yeni instance
        TransientComponent t1 = context.getBean(TransientComponent.class);
        t1.operation();
        TransientComponent t2 = context.getBean(TransientComponent.class);
        t2.operation();

        // Singleton: her çağrıda aynı instance
        SingletonComponent s1 = context.getBean(SingletonComponent.class);
        s1.operation();
        SingletonComponent s2 = context.getBean(SingletonComponent.class);
        s2.operation();

        // İkisini kullanan client
        ClientComponent c = context.getBean(ClientComponent.class);
        c.operation();
    }
}
