package com.lcg.jsfdemo.controller;

import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.lassitercg.faces.components.event.SheetUpdate;
import com.lassitercg.faces.components.sheet.Sheet;
import com.lcg.jsfdemo.manager.AssetManager;
import com.lcg.jsfdemo.model.Asset;

/**
 * Controller for page.  Most of what you will be interested in is here.  Namely responding to the Sheet cell update
 * events.  In this case, you can choose to persist those right away or simply leave the dirty changes in the model
 * to committed in bulk at a later time.  The commitUpdates method tells the component to stop tracking dirty changes.
 */
@Named
@RequestScoped
public class AssetController {

	@Inject
	AssetManager manager;

	@Inject
	Event<Asset> updateEvent;

	public void cellEditEvent(AjaxBehaviorEvent event) {
		Sheet sheet = (Sheet) event.getComponent();
		List<SheetUpdate> updates = sheet.getUpdates();
		// A SheetUpdate exists for each column updated, the same row may
		// appear more than once.  For this reason we will track those we already persisted
		HashSet<Asset> processed = new HashSet<Asset>();
		int rowUpdates = 0;
		for (SheetUpdate update : updates) {
			Asset asset = (Asset) update.getRowData();
			if (processed.contains(asset))
				continue;
			manager.update(asset);
			System.out.println("Asset " + asset.getAssetId() + " updated.");
			rowUpdates++;
			updateEvent.fire(asset);
		}
		sheet.commitUpdates();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Update Success", Integer.toString(rowUpdates) + " rows updated"));
	}

}
