package com.dstu.openbill.view.tariff;

import com.dstu.openbill.entity.Tariff;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "tariffs/:id", layout = MainView.class)
@ViewController(id = "openbill_Tariff.detail")
@ViewDescriptor(path = "tariff-detail-view.xml")
@EditedEntityContainer("tariffDc")
public class TariffDetailView extends StandardDetailView<Tariff> {
}