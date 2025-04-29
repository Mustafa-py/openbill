package com.dstu.openbill.view.contractservicetariff;

import com.dstu.openbill.entity.ContractServiceTariff;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "contractServiceTariffs", layout = MainView.class)
@ViewController(id = "openbill_ContractServiceTariff.list")
@ViewDescriptor(path = "contract-service-tariff-list-view.xml")
@LookupComponent("contractServiceTariffsDataGrid")
@DialogMode(width = "64em")
public class ContractServiceTariffListView extends StandardListView<ContractServiceTariff> {
}