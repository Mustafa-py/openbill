package com.dstu.openbill.view.housingassociation;

import com.dstu.openbill.entity.HousingAssociation;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "housingAssociations/:id", layout = MainView.class)
@ViewController(id = "openbill_HousingAssociation.detail")
@ViewDescriptor(path = "housing-association-detail-view.xml")
@EditedEntityContainer("housingAssociationDc")
public class HousingAssociationDetailView extends StandardDetailView<HousingAssociation> {
}