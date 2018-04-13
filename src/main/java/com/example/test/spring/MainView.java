package com.example.test.spring;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.websocket.RemoteEndpoint;
import java.util.function.Consumer;

/**
 * The main view contains a simple label element and a template element.
 */
@HtmlImport("styles/shared-styles.html")
@Route("")
@Push(value = PushMode.MANUAL, transport = Transport.LONG_POLLING)
public class MainView extends VerticalLayout {

    public MainView(@Autowired ExampleTemplate template,
                    @Autowired AsyncSender asyncSender) {


        Tabs tabs = new Tabs();
        tabs.add(new Tab("Tab 1"));
        add(tabs);

        // This is just a simple label created via Elements API
        Button button = new Button("Click me",
                event -> {
                    template.setValue("Clicked!");
                });
        // This is a simple template example
        add(button, template);
        setClassName("main-layout");

        asyncSender.init(UI.getCurrent(), s -> {
            tabs.add(new Tab("Tab 2?"));
        });

    }

}
