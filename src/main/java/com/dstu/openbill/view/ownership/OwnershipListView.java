package com.dstu.openbill.view.ownership;

import com.dstu.openbill.entity.Ownership;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "ownerships", layout = MainView.class)
@ViewController(id = "openbill_Ownership.list")
@ViewDescriptor(path = "ownership-list-view.xml")
@LookupComponent("ownershipsDataGrid")
@DialogMode(width = "64em")
public class OwnershipListView extends StandardListView<Ownership> {
}