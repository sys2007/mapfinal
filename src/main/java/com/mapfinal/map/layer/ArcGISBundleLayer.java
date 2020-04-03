package com.mapfinal.map.layer;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.TileDispatcher;
import com.mapfinal.event.Event;
import com.mapfinal.map.MapView;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.resource.bundle.BundleCollection;
import com.mapfinal.resource.bundle.BundleManager;
import org.locationtech.jts.geom.Envelope;

public class ArcGISBundleLayer extends AbstractLayer {

	// 调度器
	private TileDispatcher dispatcher;
	// 资源
	private BundleCollection resource;

	public ArcGISBundleLayer(String name, String url) {
		// TODO Auto-generated constructor stub
		resource = BundleManager.me().create(name, url);
		setName(resource.getName());
		setTitle(resource.getName());
		TileDispatcher dispatcher = (TileDispatcher) resource.connection();
		System.out.println("FeatureDispatcher: " + resource.getName());
		setDispatcher(dispatcher);
		setSpatialReference(SpatialReference.mercator());
	}
	
	public ArcGISBundleLayer(BundleCollection resource) {
		this.resource = resource;
		setName(resource.getName());
		setTitle(resource.getName());
		TileDispatcher dispatcher = (TileDispatcher) resource.connection();
		System.out.println("FeatureDispatcher: " + resource.getName());
		setDispatcher(dispatcher);
		setSpatialReference(SpatialReference.mercator());
	}
	
	public void addTo(MapView mapNode) {
		mapNode.add(this);
	}

	@Override
	public void onRender(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(!isVisible()) return;
		MapContext context = event.get("map");
		int zoom = (int) context.getZoom();
		if(zoom < getMinZoom() || zoom > getMaxZoom()) return;
		//System.out.println("feature layer render.");
		if(dispatcher!=null) {
			event.set("layer", this);
			dispatcher.onRender(event, engine, getRenderer());
		}
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		if(dispatcher!=null) {
			dispatcher.onEvent(event);
		}
	}

	public TileDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(TileDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public BundleCollection getResource() {
		return resource;
	}

	public void setResource(BundleCollection resource) {
		this.resource = resource;
	}
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setMinZoom(int minZoom) {
		// TODO Auto-generated method stub
		super.setMinZoom(minZoom);
		this.dispatcher.setMinZoom(minZoom);
	}
	
	@Override
	public void setMaxZoom(int maxZoom) {
		// TODO Auto-generated method stub
		super.setMaxZoom(maxZoom);
		this.dispatcher.setMaxZoom(maxZoom);
	}
}