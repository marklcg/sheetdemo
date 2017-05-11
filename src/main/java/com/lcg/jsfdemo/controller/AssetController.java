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
import com.lcg.jsfdemo.producer.AssetProducer;

@Named
@RequestScoped
public class AssetController {

	@Inject
	AssetManager manager;

	@Inject
	Event<Asset> updateEvent;

	@Inject
	AssetProducer assets;

	private Integer value;

	public void cellEditEvent(AjaxBehaviorEvent event) {
		Sheet sheet = (Sheet) event.getComponent();
		List<SheetUpdate> updates = sheet.getUpdates();
		// A SheetUpdate exists for each column updated, the same row may
		// appear more than once
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
		// sheet.commitUpdates();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Update Success", Integer.toString(rowUpdates) + " rows updated"));
	}

	public void submit() {
		for (Asset asset : assets.getAssets())
			if (asset.getValue1() != null)
				System.out.println("Updating asset " + asset.getAssetId() + " with value " + asset.getValue1());
	}

	/**
	 * Returns the value value.
	 *
	 * @return the value value.
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Updates the value value.
	 *
	 * @param value
	 * 		the value value.
	 */
	public void setValue(Integer value) {
		this.value = value;
	}
}
