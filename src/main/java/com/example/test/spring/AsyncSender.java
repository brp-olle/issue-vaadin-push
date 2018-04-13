package com.example.test.spring;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@VaadinSessionScope
public class AsyncSender {

    private UI vaadinUI;
    private Consumer<Void> consumer;

    void init(UI vaadinUI, Consumer<Void> consumer) {
        this.vaadinUI = vaadinUI;
        this.consumer = consumer;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            doAsync(); // This command will fail
        }).start();
    }

    private void doAsync() {
        vaadinUI.access((Command) () -> {
            consumer.accept(null);
            vaadinUI.push();
        });
    }
}
