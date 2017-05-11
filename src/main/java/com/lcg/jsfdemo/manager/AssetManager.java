package com.lcg.jsfdemo.manager;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.lcg.jsfdemo.model.Asset;
import com.lcg.jsfdemo.model.AssetType;
import com.lcg.jsfdemo.model.PlatformArchType;
import com.lcg.jsfdemo.model.PlatformType;

@Stateless
public class AssetManager {

	@Inject
	EntityManager em;

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Asset> listAll() {
		TypedQuery<Asset> qry = em.createQuery("SELECT a from Asset a", Asset.class);
		return qry.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persist(Asset asset) {
		em.persist(asset);
	}

	protected void addAssets(int count, PlatformType platform, PlatformArchType arch, AssetType type) {
		for (int i = 0; i < count; i++) {
			Asset asset = new Asset();
			asset.setPlatform(platform);
			asset.setPlatformArch(arch);
			asset.setHostName(type.toString().toLowerCase() + i + ".example.lan");
			asset.setAssetType(type);
			asset.setLastUpdated(new Date());
			persist(asset);
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void initTestData() {
		System.out.println("Initializing Test Data ...");
		addAssets(10, PlatformType.Linux, PlatformArchType.AMD64, AssetType.SERVER);
		addAssets(100, PlatformType.Windows, PlatformArchType.I386, AssetType.DESKTOP);
		addAssets(5, null, null, AssetType.PRINTER);
		em.flush();
		System.out.println("Done.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Asset update(Asset asset) {
		return em.merge(asset);
	}

}
