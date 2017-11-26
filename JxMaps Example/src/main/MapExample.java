package main;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.Polyline;
import com.teamdev.jxmaps.PolylineOptions;
import com.teamdev.jxmaps.swing.MapView;
import com.teamdev.jxmaps.Icon;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class MapExample extends MapView {
	
	private static final long serialVersionUID = -2877189569097540460L;
	private Map map;
    public MapExample() {
        // Setting of a ready handler to MapView object. onMapReady will be called when map initialization is done and
        // the map object is ready to use. Current implementation of onMapReady customizes the map object.
       
    	setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                    // Getting the associated map object
                    map = getMap();
                    // Creating a map options object
                    MapOptions mapOptions = new MapOptions();
                    // Creating a map type control options object
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    // Changing position of the map type control
                    controlOptions.setPosition(ControlPosition.TOP_RIGHT);
                    // Setting map type control options
                    mapOptions.setMapTypeControlOptions(controlOptions);
                    // Setting map options
                    map.setOptions(mapOptions);
                    // Setting the map center
                    map.setCenter(new LatLng(44.4989010, 11.3555960));
                    // Setting initial zoom value
                    map.setZoom(13.0);                          
                }
    
               /* 
        		try {
        			
                    Marker m = addMarker(points.get(0));
                    addPolyline(points, "#e60000");                                     
                    int size = points.size();
                    
                    for (int i = 0; i < size; i++) {
                    	Thread.sleep(50);
                    	moveMarker(m, points.get(i));
                    	
                    }
        		} catch (IOException | InterruptedException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
                */
            }
        });
    }
    
    public void addPolyline(List<LatLng> points , String color) {
    	// Creating a path (array of coordinates) that represents a polyline
    	LatLng[] path = new LatLng[points.size()];
    	path = points.toArray(path);
        
        // Creating a new polyline object
        Polyline polyline = new Polyline(map);
        // Initializing the polyline with created path
        polyline.setPath(path);
        // Creating a polyline options object
        PolylineOptions options = new PolylineOptions();
                        
        
        // Setting geodesic property value
        options.setGeodesic(true);
        // Setting stroke color value
        options.setStrokeColor(color);
        // Setting stroke opacity value
        options.setStrokeOpacity(1.0);
        // Setting stroke weight value
        options.setStrokeWeight(2.0);
        // Applying options to the polyline
        polyline.setOptions(options);
    }
    
    public Marker addMarker(LatLng ll) throws FileNotFoundException {
    	Marker marker = new Marker(map);
    	
    	Icon icon = new Icon();
    	InputStream inputstream = new FileInputStream("./res/bus.png");
    	icon.loadFromStream(inputstream, "png");
    	marker.setIcon(icon);
    	
        marker.setPosition(ll);
        return marker;
    }
    
    public void moveMarker(Marker m, LatLng ll) {
    	m.setPosition(ll);
    }
}
