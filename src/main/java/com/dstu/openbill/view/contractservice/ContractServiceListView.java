package com.dstu.openbill.view.contractservice;

import com.dstu.openbill.entity.ContractService;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "contractServices", layout = MainView.class)
@ViewController(id = "ContractService.list")
@ViewDescriptor(path = "contract-service-list-view.xml")
@LookupComponent("contractServicesDataGrid")
@DialogMode(width = "64em")
public class ContractServiceListView extends StandardListView<ContractService> {
}