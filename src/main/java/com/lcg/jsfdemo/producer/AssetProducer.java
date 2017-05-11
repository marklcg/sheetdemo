package com.lcg.jsfdemo.producer;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.lcg.jsfdemo.manager.AssetManager;
import com.lcg.jsfdemo.model.Asset;

@ApplicationScoped
public class AssetProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	AssetManager manager;

	private List<Asset> assets;

	@PostConstruct
	void refresh() {
		assets = manager.listAll();
		if (assets.isEmpty()) {
			manager.initTestData();
			assets = manager.listAll();
		}
	}

	@Produces
	@Named
	public List<Asset> getAssets() {
		if (assets == null)
			refresh();
		return assets;
	}

	public void handleUpdateEvent(@Observes(during = TransactionPhase.AFTER_COMPLETION) Asset asset) {
		assets = null;
	}
}
