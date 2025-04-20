package com.dstu.openbill.view.house;

import com.dstu.openbill.entity.House;
import com.dstu.openbill.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "houses/:id", layout = MainView.class)
@ViewController(id = "openbill_House.detail")
@ViewDescriptor(path = "house-detail-view.xml")
@EditedEntityContainer("houseDc")
public class HouseDetailView extends StandardDetailView<House> {
}