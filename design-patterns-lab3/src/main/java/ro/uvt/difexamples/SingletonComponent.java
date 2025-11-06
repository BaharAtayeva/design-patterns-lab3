package ro.uvt.difexamples;

import org.springframework.stereotype.Component;

@Component
public class SingletonComponent {
    public SingletonComponent() {
        System.out.println("SingletonComponent::constructor -> " + this);
    }

    public void operation() {
        System.out.println("Invoked SingletonComponent::operation() on " + this);
    }
}
