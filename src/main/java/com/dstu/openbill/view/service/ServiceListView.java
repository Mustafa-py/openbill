package com.dstu.openbill.view.service;

import com.dstu.openbill.entity.Service;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "services", layout = MainView.class)
@ViewController(id = "openbill_Service.list")
@ViewDescriptor(path = "service-list-view.xml")
@LookupComponent("servicesDataGrid")
@DialogMode(width = "64em")
public class ServiceListView extends StandardListView<Service> {
}