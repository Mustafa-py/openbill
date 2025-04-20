package com.dstu.openbill.view.house;

import com.dstu.openbill.entity.House;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "houses", layout = MainView.class)
@ViewController(id = "openbill_House.list")
@ViewDescriptor(path = "house-list-view.xml")
@LookupComponent("housesDataGrid")
@DialogMode(width = "64em")
public class HouseListView extends StandardListView<House> {
}