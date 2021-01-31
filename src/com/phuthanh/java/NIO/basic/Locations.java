package com.phuthanh.java.NIO.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by timbuchalka on 2/04/2016.
 */
public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<>();

    static {
    	
    	Path byteDataPath = FileSystems.getDefault().getPath("locations.dat");
    	
    	try (ObjectInputStream byteDataFile = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(byteDataPath)))) {
    		boolean eof = false;
    		
    		while (!eof) {
    			try {
    				Location location = (Location) byteDataFile.readObject();
    				
    				locations.put(location.getLocationID(), location);
    				
    			} catch (EOFException e) {
    				eof = true;
    			}
    		}
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*
    	Path locPath = FileSystems.getDefault().getPath("locations.txt");
    	Path dirPath = FileSystems.getDefault().getPath("directions.txt");
    	
    	try (Scanner scanner = new Scanner(Files.newBufferedReader(locPath))) {
    		scanner.useDelimiter(",");
    		
    		while (scanner.hasNextLine()) {
    			int locationId = scanner.nextInt();
    			scanner.skip(scanner.delimiter());
    			String description = scanner.nextLine();
    			
    			System.out.println("Imported loc " + locationId + ": " + description);
    			
    			locations.put(locationId, new Location(locationId, description, null));
    		}
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	try (BufferedReader dirFile = Files.newBufferedReader(dirPath)) {
    		String input;
    		
    		while ((input = dirFile.readLine()) != null) {
    			String[] data = input.split(",");
    			
    			int locationId = Integer.parseInt(data[0]);
    			String direction = data[1];
    			int destination = Integer.parseInt(data[2]);
    			
    			System.out.println(locationId + ": " + direction + ": " + destination);
    			locations.get(locationId).addExit(direction, destination);
    		}
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	*/
    }
    
    public static void main(String[] args) throws IOException {
    	
    	Path locPath = FileSystems.getDefault().getPath("locations_big.txt");
    	Path dirPath = FileSystems.getDefault().getPath("directions_big.txt");
    	
    	try (BufferedWriter locFile = Files.newBufferedWriter(locPath);
    			BufferedWriter dirFile = Files.newBufferedWriter(dirPath)) {
    		
    		for (Location location : locations.values()) {
    			locFile.write(location.getLocationID() + ": " + location.getDescription() + "\n");
    			
    			for (String direction : location.getExits().keySet()) {
    				dirFile.write(location.getLocationID() + "," + direction + "," + location.getExits().get(direction) + "\n");
    			}
    		}
    		
    	}
    	
    	Path byteDataPath = FileSystems.getDefault().getPath("locations.dat");
    	try (ObjectOutputStream byteDataFile = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(byteDataPath)))) {
    		
    		for (Location location : locations.values()) {
    			byteDataFile.writeObject(location);
    		}
    	}
    	
    }
    
    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();

    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
