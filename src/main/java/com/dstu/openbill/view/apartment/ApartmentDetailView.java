package com.dstu.openbill.view.apartment;

import com.dstu.openbill.entity.Apartment;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "apartments/:id", layout = MainView.class)
@ViewController(id = "openbill_Apartment.detail")
@ViewDescriptor(path = "apartment-detail-view.xml")
@EditedEntityContainer("apartmentDc")
public class ApartmentDetailView extends StandardDetailView<Apartment> {
}