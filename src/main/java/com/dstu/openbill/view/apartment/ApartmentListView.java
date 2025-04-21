package com.dstu.openbill.view.apartment;

import com.dstu.openbill.entity.Apartment;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "apartments", layout = MainView.class)
@ViewController(id = "openbill_Apartment.list")
@ViewDescriptor(path = "apartment-list-view.xml")
@LookupComponent("apartmentsDataGrid")
@DialogMode(width = "64em")
public class ApartmentListView extends StandardListView<Apartment> {
}