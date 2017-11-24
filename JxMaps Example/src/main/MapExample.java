package main;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.Icon;
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

import parser.Parser;

/**
 * This example demonstrates how to draw polylines on the map.
 *
 * @author Vitaly Eremenko
 */
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
                    map.setCenter(new LatLng(44.4879010, 11.3325960));
                    // Setting initial zoom value
                    map.setZoom(17.0);                          
                }
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

    public static void main(String[] args) throws InterruptedException, ParserConfigurationException, SAXException, IOException {
        final MapExample sample = new MapExample();

        JFrame frame = new JFrame("Polylines");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(sample, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        Thread.sleep(2000);     
        Parser p = new Parser("./gpx/path_test.gpx");
        Parser p2 = new Parser("./gpx/path2.gpx");
        Parser p3 = new Parser("./gpx/32.gpx");
        
        List<LatLng> points = p.getListOfPoint();
        List<LatLng> points2 = p2.getListOfPoint();
        List<LatLng> points3 = p3.getListOfPoint();
        
        Marker m= sample.addMarker(points.get(0));
        Marker m2= sample.addMarker(points2.get(0));
        Marker m3= sample.addMarker(points3.get(0));
        
        sample.addPolyline(points2, "#FFFFF");
        sample.addPolyline(points, "#FF000");
        sample.addPolyline(points3, "#FFF00");
        int i=0, i2=0;
        for (LatLng point: points3) {
        	sample.moveMarker(m2, points2.get(i2));
        	sample.moveMarker(m, points.get(i));
        	sample.moveMarker(m3, point);
        	if(i < points.size() - 1)
        		i++;
        	if(i2 < points2.size() - 1)
        		i2++;
            Thread.sleep(100);
        }
        
        
        
    }

}
