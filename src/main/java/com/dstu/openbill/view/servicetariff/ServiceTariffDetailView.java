package com.dstu.openbill.view.servicetariff;

import com.dstu.openbill.entity.ServiceTariff;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "serviceTariffs/:id", layout = MainView.class)
@ViewController(id = "openbill_ServiceTariff.detail")
@ViewDescriptor(path = "service-tariff-detail-view.xml")
@EditedEntityContainer("serviceTariffDc")
public class ServiceTariffDetailView extends StandardDetailView<ServiceTariff> {
}