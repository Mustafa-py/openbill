package com.dstu.openbill.view.tariff;

import com.dstu.openbill.entity.Tariff;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "tariffs", layout = MainView.class)
@ViewController(id = "openbill_Tariff.list")
@ViewDescriptor(path = "tariff-list-view.xml")
@LookupComponent("tariffsDataGrid")
@DialogMode(width = "64em")
public class TariffListView extends StandardListView<Tariff> {
}