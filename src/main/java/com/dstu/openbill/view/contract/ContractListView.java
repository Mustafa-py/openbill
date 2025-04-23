package com.dstu.openbill.view.contract;

import com.dstu.openbill.entity.Contract;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "contracts", layout = MainView.class)
@ViewController(id = "openbill_Contract.list")
@ViewDescriptor(path = "contract-list-view.xml")
@LookupComponent("contractsDataGrid")
@DialogMode(width = "64em")
public class ContractListView extends StandardListView<Contract> {
}