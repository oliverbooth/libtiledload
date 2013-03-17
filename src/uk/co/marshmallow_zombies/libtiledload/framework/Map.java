package uk.co.marshmallow_zombies.libtiledload.framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import uk.co.marshmallow_zombies.libtiledload.interfaces.IProperties;
import uk.co.marshmallow_zombies.libtiledload.interfaces.ISizable;

/**
 * A map.
 * 
 * @author Oliver Davenport
 */
public class Map implements IProperties, ISizable {

	List<Property> m_PropertiesList; // List of properties
	List<Tileset> m_TilesetList; // List of tilesets
	List<Layer> m_LayerList; // List of layers
	Dimension m_MapSize; // Map size
	Dimension m_MapTileSize; // Tile size
	Color m_MapBackgroundColor; // Background color

	/**
	 * Initialises a new {@code Map}.
	 */
	Map() {
		// Initialise variables
		this.m_PropertiesList = new ArrayList<Property>();
		this.m_TilesetList = new ArrayList<Tileset>();
		this.m_LayerList = new ArrayList<Layer>();
	}

	/**
	 * Gets an array of all the {@code Layer}s on the map.
	 */
	public Layer[] getLayers() {
		// Clone the layer list
		List<Layer> layersList = this.m_LayerList;

		// Get the size of the list
		int size = layersList.size();

		// Create a new array and go!
		Layer[] layers = new Layer[size];

		// Convert to array
		layersList.toArray(layers);

		// Return the array
		return layers;
	}

	/**
	 * Adds a layer to the map.
	 * 
	 * @param layer
	 *            The {@code Layer} to add.
	 */
	public void addLayer(Layer layer) {
		// Add layer to list
		this.m_LayerList.add(layer);
	}

	/**
	 * Adds a tileset to the map.
	 * 
	 * @param tileset
	 *            The {@code Tileset} to add.
	 */
	public void addTileset(Tileset tileset) {
		// Add the tileset to the list
		this.m_TilesetList.add(tileset);
	}

	@Override
	public void addProperty(Property property) {
		// Add the property to the list
		this.m_PropertiesList.add(property);
	}

	@Override
	public Property[] getProperties() {
		// Clone the properties list
		List<Property> propertiesList = this.m_PropertiesList;

		// Get the size of the list
		int size = propertiesList.size();

		// Create a new array and go!
		Property[] properties = new Property[size];

		// Convert to array
		propertiesList.toArray(properties);

		// Return the array
		return properties;
	}

	@Override
	public Property getPropertyByName(String name) {
		// Iterate through the array
		for (int i = 0; i < this.m_PropertiesList.size(); i++) {
			// Does the name match?
			if (this.m_PropertiesList.get(i).getName().equals(name)) {
				// Return the property found in the array at the current index
				return this.m_PropertiesList.get(i);
			}
		}

		// No property matched
		return null;
	}

	/**
	 * Sets the map background color.
	 * 
	 * @param color
	 *            The color to set.
	 */
	public void setBackgroundColor(Color color) {
		// Store value
		this.m_MapBackgroundColor = color;
	}

	/**
	 * Gets the map background color.
	 */
	public Color getBackgroundColor() {
		// Return value
		return this.m_MapBackgroundColor;
	}

	/**
	 * Sets the map tile size.
	 * 
	 * @param size
	 *            The size to set.
	 */
	public void setTileSize(Dimension size) {
		// Store value
		this.m_MapTileSize = size;
	}

	/**
	 * Gets the map tile size.
	 */
	public Dimension getTileSize() {
		// Return value
		return this.m_MapTileSize;
	}

	/**
	 * Sets the map size.
	 * 
	 * @param size
	 *            The size to set.
	 */
	@Override
	public void setSize(Dimension size) {
		// Store value
		this.m_MapSize = size;
	}

	/**
	 * Gets the map size.
	 */
	@Override
	public Dimension getSize() {
		// Return value
		return this.m_MapSize;
	}

	/**
	 * Loads a map from file.
	 * 
	 * @param mapFile
	 *            The {@code File} to read.
	 */
	public static Map load(File mapFile) {
		return MapLoader.load(mapFile);
	}

	/**
	 * Gets the image byte data for the given GID.
	 * 
	 * @param gid
	 *            The GID to scan.
	 */
	byte[] getTileDataByGID(int gid) {
		// Iterate through the tilesets
		for (int i = 0; i < this.m_TilesetList.size(); i++) {
			try {
				// Get the current tileset
				Tileset tileset = this.m_TilesetList.get(i);

				// Get the byte data for the tileset and convert it to an image
				byte[] tilesetData = tileset.getData();
				final BufferedImage tilesetImage = ImageIO.read(new ByteArrayInputStream(tilesetData));

				// Get the tileset first and last GID
				int firstGid = tileset.getFirstGID();

				// Get image size
				int imageWidth = tilesetImage.getWidth();
				int imageHeight = tilesetImage.getHeight();

				// Get tile size
				Dimension tileSize = tileset.getTileSize();
				int tileWidth = (int) tileSize.getWidth();
				int tileHeight = (int) tileSize.getHeight();

				// Calculate last GID
				int lastGid = firstGid + (imageWidth / tileWidth) * (imageHeight / tileHeight) - 1;

				if (gid < firstGid || gid > lastGid)
					// The GID fell outside of the range of this tileset
					// Onward, ho!
					continue;

				// Calculate the actual GID to use with image cropping
				int actualGid = gid - firstGid;

				// Calculate the x-coordinate and y-coordinate values for
				// cropping
				int x = (actualGid % (imageWidth / tileWidth)) * tileWidth;
				int y = (actualGid / (imageWidth / tileWidth)) * tileHeight;

				// Create a sub-image of the tileset
				BufferedImage tileImage = tilesetImage.getSubimage(x, y, tileWidth, tileHeight);

				// Convert to byte array by means of a ByteArrayOutputStream
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(tileImage, "png", baos);
				baos.flush();
				byte[] imageData = baos.toByteArray();
				baos.close();

				// And we're done!
				return imageData;
			} catch (Exception e) {
				// Skip to the next tileset
				e.printStackTrace();
				continue;
			}
		}

		// Something went wrong
		return null;
	}

};
