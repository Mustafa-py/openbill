package com.dstu.openbill.view.servicetariff;

import com.dstu.openbill.entity.ServiceTariff;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "serviceTariffs", layout = MainView.class)
@ViewController(id = "openbill_ServiceTariff.list")
@ViewDescriptor(path = "service-tariff-list-view.xml")
@LookupComponent("serviceTariffsDataGrid")
@DialogMode(width = "64em")
public class ServiceTariffListView extends StandardListView<ServiceTariff> {
}