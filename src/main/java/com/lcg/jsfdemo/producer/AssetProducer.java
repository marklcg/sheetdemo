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

/**
 * Simple producer object that produces a list of Assets. This is application scoped which would normally be bad
 * with multiple clients/etc but works for demo.
 */
@ApplicationScoped
public class AssetProducer implements Serializable {

	@Inject
	AssetManager manager;

	private List<Asset> assets;

	@PostConstruct
	void init() {
		manager.initTestData();
	}

	void refresh() {
		assets = manager.listAll();
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
