package com.dstu.openbill.view.owner;

import com.dstu.openbill.entity.Owner;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "owners", layout = MainView.class)
@ViewController(id = "openbill_Owner.list")
@ViewDescriptor(path = "owner-list-view.xml")
@LookupComponent("ownersDataGrid")
@DialogMode(width = "64em")
public class OwnerListView extends StandardListView<Owner> {
}