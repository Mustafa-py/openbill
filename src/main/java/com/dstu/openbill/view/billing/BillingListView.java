package com.dstu.openbill.view.billing;

import com.dstu.openbill.entity.Billing;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "billings", layout = MainView.class)
@ViewController(id = "openbill_Billing.list")
@ViewDescriptor(path = "billing-list-view.xml")
@LookupComponent("billingsDataGrid")
@DialogMode(width = "64em")
public class BillingListView extends StandardListView<Billing> {
}