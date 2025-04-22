package com.dstu.openbill.view.balance;

import com.dstu.openbill.entity.Balance;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "balances", layout = MainView.class)
@ViewController(id = "openbill_Balance.list")
@ViewDescriptor(path = "balance-list-view.xml")
@LookupComponent("balancesDataGrid")
@DialogMode(width = "64em")
public class BalanceListView extends StandardListView<Balance> {
}