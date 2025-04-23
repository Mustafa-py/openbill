package com.dstu.openbill.view.service;

import com.dstu.openbill.entity.Service;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "services/:id", layout = MainView.class)
@ViewController(id = "openbill_Service.detail")
@ViewDescriptor(path = "service-detail-view.xml")
@EditedEntityContainer("serviceDc")
public class ServiceDetailView extends StandardDetailView<Service> {
}