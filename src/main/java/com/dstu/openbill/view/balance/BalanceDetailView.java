package com.dstu.openbill.view.balance;

import com.dstu.openbill.entity.Balance;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "balances/:id", layout = MainView.class)
@ViewController(id = "openbill_Balance.detail")
@ViewDescriptor(path = "balance-detail-view.xml")
@EditedEntityContainer("balanceDc")
public class BalanceDetailView extends StandardDetailView<Balance> {
}