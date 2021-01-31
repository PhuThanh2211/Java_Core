package com.phuthanh.java.IO.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    	
    	try (ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream("locations.dat")))) {
    		boolean eof = false;
    		while (!eof) {
    			try {
    				Location location = (Location) locFile.readObject();
    				
    				System.out.println("Read location " + location.getLocationID() + ": " + location.getDescription());
    				System.out.println("Found " + location.getExits().size() + " exits");
    				
    				locations.put(location.getLocationID(), location);
    				
    			} catch (EOFException e) {
    				eof = true;
    			}
    		}
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	
    	/*
    	try (DataInputStream locFile = new DataInputStream(new BufferedInputStream(new FileInputStream("locations.dat")))) {
    		boolean eof = false; // End Of File
    		while (!eof) {
    			try {
    				Map<String, Integer> exits = new LinkedHashMap<>();
    				int locationId = locFile.readInt();
    				String description = locFile.readUTF();
    				int numExits = locFile.readInt();
    				
    				System.out.println("Read location " + locationId + ": " + description);
    				System.out.println("Found " + numExits + " exits");
    				
    				for (int i = 0; i < numExits; i++) {
    					String direction = locFile.readUTF();
    					int destination = locFile.readInt();
    					
    					exits.put(direction, destination);
    					System.out.println("\t\t" + direction + "," + destination);
    				}
    				
    				locations.put(locationId, new Location(locationId, description, exits));
    				
    			} catch (EOFException e) {
    				eof = true;
    			}
    		}
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	*/
    	
    	/*
    	try (BufferedReader locFile = new BufferedReader(new FileReader("locations_big.txt"))) {
    		String input;
    		
    		while ((input = locFile.readLine()) != null) {
    			String[] data = input.split(",");
    			int loc = Integer.parseInt(data[0]);
    			String description = data[1];
    			
    			System.out.println("Imported location " + loc + ": " + description);
    			Map<String, Integer> tempExit = new HashMap<>();
    			locations.put(loc, new Location(loc, description, tempExit));
    		}
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	// Now read the exits
    	try (BufferedReader dirFile = new BufferedReader(new FileReader("directions_big.txt"))) {
    		String input;
    		
        	while ((input = dirFile.readLine()) != null) {
        		String[] data = input.split(",");
        		int loc = Integer.parseInt(data[0].trim());
        		String direction = data[1];
        		int destination = Integer.parseInt(data[2].trim());
        		
        		System.out.println(loc + ": " + direction + ": " + destination);
        		Location location = locations.get(loc);
        		location.addExit(direction, destination);
        	}
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	*/
    }
    
    public static void main(String[] args) throws IOException {
    	
    	// FileWriter is automatically closed with "Try with Resource" function
    	try (ObjectOutputStream locFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("locations.dat")))) {
    		for (Location location : locations.values()) {
    			locFile.writeObject(location);
    		}
    		
    	}
    	
    	/*
    	try (DataOutputStream locFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("locations.dat")))) {
    		for (Location location : locations.values()) {
    			locFile.writeInt(location.getLocationID());
    			locFile.writeUTF(location.getDescription());
    			
    			System.out.println("Writing location: " + location.getLocationID() + ": " + location.getDescription());
    			System.out.println("Writing " + (location.getExits().size() - 1) + " exits.");
    			
    			locFile.writeInt(location.getExits().size() - 1);
    			
    			for (String direction : location.getExits().keySet()) {
    				if (!direction.equalsIgnoreCase("Q")) {
    					System.out.println("\t\t" + direction + ", " + location.getExits().get(direction));
    					
    					locFile.writeUTF(direction);
    					locFile.writeInt(location.getExits().get(direction));
    				}
    			}
    		}
    	}
    	*/
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
