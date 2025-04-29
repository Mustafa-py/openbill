package com.dstu.openbill.view.billing;

import com.dstu.openbill.entity.Billing;

import com.dstu.openbill.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "billings/:id", layout = MainView.class)
@ViewController(id = "openbill_Billing.detail")
@ViewDescriptor(path = "billing-detail-view.xml")
@EditedEntityContainer("billingDc")
public class BillingDetailView extends StandardDetailView<Billing> {
}