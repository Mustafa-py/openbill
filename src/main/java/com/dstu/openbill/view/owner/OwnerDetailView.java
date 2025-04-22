package com.dstu.openbill.view.owner;

import com.dstu.openbill.entity.Owner;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "owners/:id", layout = MainView.class)
@ViewController(id = "openbill_Owner.detail")
@ViewDescriptor(path = "owner-detail-view.xml")
@EditedEntityContainer("ownerDc")
public class OwnerDetailView extends StandardDetailView<Owner> {
}