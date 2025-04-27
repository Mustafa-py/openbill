package com.dstu.openbill.view.ownership;

import com.dstu.openbill.entity.Ownership;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "ownerships/:id", layout = MainView.class)
@ViewController(id = "openbill_Ownership.detail")
@ViewDescriptor(path = "ownership-detail-view.xml")
@EditedEntityContainer("ownershipDc")
public class OwnershipDetailView extends StandardDetailView<Ownership> {
}