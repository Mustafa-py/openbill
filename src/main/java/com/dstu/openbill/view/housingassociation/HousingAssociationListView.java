package com.dstu.openbill.view.housingassociation;

import com.dstu.openbill.entity.HousingAssociation;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "housingAssociations", layout = MainView.class)
@ViewController(id = "openbill_HousingAssociation.list")
@ViewDescriptor(path = "housing-association-list-view.xml")
@LookupComponent("housingAssociationsDataGrid")
@DialogMode(width = "64em")
public class HousingAssociationListView extends StandardListView<HousingAssociation> {
}