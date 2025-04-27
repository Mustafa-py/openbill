package com.dstu.openbill.view.payment;

import com.dstu.openbill.entity.Payment;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "payments/:id", layout = MainView.class)
@ViewController(id = "openbill_Payment.detail")
@ViewDescriptor(path = "payment-detail-view.xml")
@EditedEntityContainer("paymentDc")
public class PaymentDetailView extends StandardDetailView<Payment> {
}