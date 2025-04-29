package com.dstu.openbill.view.contractservicetariff;

import com.dstu.openbill.entity.ContractServiceTariff;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "contractServiceTariffs/:id", layout = MainView.class)
@ViewController(id = "openbill_ContractServiceTariff.detail")
@ViewDescriptor(path = "contract-service-tariff-detail-view.xml")
@EditedEntityContainer("contractServiceTariffDc")
public class ContractServiceTariffDetailView extends StandardDetailView<ContractServiceTariff> {
}