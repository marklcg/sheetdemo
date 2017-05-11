package com.lcg.jsfdemo.producer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.lcg.jsfdemo.model.AssetType;
import com.lcg.jsfdemo.model.PlatformArchType;
import com.lcg.jsfdemo.model.PlatformType;

@ApplicationScoped
public class FilterOptionProducer {

	List<SelectItem> assetTypes;
	List<SelectItem> platformTypes;
	List<SelectItem> archTypes;

	@PostConstruct
	private void init() {
		assetTypes = createEnumList(AssetType.values());
		platformTypes = createEnumList(PlatformType.values());
		archTypes = createEnumList(PlatformArchType.values());
	}

	private <T extends Enum<?>> List<SelectItem> createEnumList(T[] values) {
		List<SelectItem> result = new ArrayList<SelectItem>();
		result.add(new SelectItem("", "All"));
		for (T value : values)
			result.add(new SelectItem(value, value.name()));
		return result;
	}

	@Named
	@Produces
	public List<SelectItem> getAssetTypes() {
		return assetTypes;
	}

	@Named
	@Produces
	public List<SelectItem> getPlatformTypes() {
		return platformTypes;
	}

	@Named
	@Produces
	public List<SelectItem> getArchTypes() {
		return archTypes;
	}

}
