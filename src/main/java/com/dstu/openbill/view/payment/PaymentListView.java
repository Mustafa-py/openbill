package com.dstu.openbill.view.payment;

import com.dstu.openbill.entity.Payment;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "payments", layout = MainView.class)
@ViewController(id = "openbill_Payment.list")
@ViewDescriptor(path = "payment-list-view.xml")
@LookupComponent("paymentsDataGrid")
@DialogMode(width = "64em")
public class PaymentListView extends StandardListView<Payment> {
}