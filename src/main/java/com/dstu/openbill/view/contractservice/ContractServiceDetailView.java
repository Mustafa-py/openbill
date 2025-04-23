package com.dstu.openbill.view.contractservice;

import com.dstu.openbill.entity.ContractService;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "contractServices/:id", layout = MainView.class)
@ViewController(id = "ContractService.detail")
@ViewDescriptor(path = "contract-service-detail-view.xml")
@EditedEntityContainer("contractServiceDc")
public class ContractServiceDetailView extends StandardDetailView<ContractService> {
}