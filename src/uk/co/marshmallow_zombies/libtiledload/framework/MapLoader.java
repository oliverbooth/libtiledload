package uk.co.marshmallow_zombies.libtiledload.framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * Specific functions for loading a map.
 * 
 * @author Oliver Davenport
 */
class MapLoader {

	/**
	 * Loads a map from file.
	 * 
	 * @param mapFile
	 *            The map file.
	 * @return Returns a {@code Map} holding the data for the map.
	 */
	static Map load(File mapFile) {
		System.out.printf("-----------------------------------------------------\n");
		System.out.printf("| libtiledload_0.3-dev (A Tiled XML TMX Map Loader) |\n");
		System.out.printf("|            Written by Oliver Davenport            |\n");
		System.out.printf("-----------------------------------------------------\n\n");
		
		// Create a new map
		Map map = new Map();

		// Output status
		System.out.printf("Determined mapfile: %s\n", mapFile.getAbsolutePath());

		try {
			// Create the builder and build the map file
			SAXBuilder builder = new SAXBuilder();
			Document document = (Document) builder.build(mapFile);

			// Get the document's root element
			// (should be <map>)
			Element documentRootElement = document.getRootElement();

			// Set a predefined map colour
			Color mapBackgroundColor = new Color(128, 128, 128);

			// Attempt to get map background colour
			String sMapBackgroundColor = documentRootElement.getAttributeValue("backgroundcolor");
			if (sMapBackgroundColor != null && !sMapBackgroundColor.isEmpty())
				// Set the background colour ONLY if the attribute exists
				mapBackgroundColor = getColorFromHex(sMapBackgroundColor);

			// Set map background colour
			map.setBackgroundColor(mapBackgroundColor);

			// Get map dimensions
			int mapWidth = Integer.valueOf(documentRootElement.getAttributeValue("width"));
			int mapHeight = Integer.valueOf(documentRootElement.getAttributeValue("height"));

			// Get map tile dimensions
			int mapTileWidth = Integer.valueOf(documentRootElement.getAttributeValue("tilewidth"));
			int mapTileHeight = Integer.valueOf(documentRootElement.getAttributeValue("tileheight"));

			// Create a Dimension for each
			Dimension mapSize = new Dimension(mapWidth, mapHeight);
			Dimension mapTileSize = new Dimension(mapTileWidth, mapTileHeight);

			// Set map sizes
			map.setSize(mapSize);
			map.setTileSize(mapTileSize);
			
			System.out.printf("Reading map metadata...");
			// Get the <properties>/<property> children
			List<Element> rootElementPropertiesElementPropertyList = new ArrayList<Element>();
			try {
				rootElementPropertiesElementPropertyList = documentRootElement.getChildren("properties").get(0)
						.getChildren("property");
				for (int i = 0; i < rootElementPropertiesElementPropertyList.size(); i++) {
					// Get the current element
					Element propertyElement = (Element) rootElementPropertiesElementPropertyList.get(i);

					// Get the property from the element
					Property property = getPropertyFromElement(propertyElement);

					// Add the property to the map
					map.addProperty(property);
				}
			} catch (Exception e) {
			}

			// Output status
			System.out.printf(" done\n");
			System.out.printf("Loading tilesets");

			// Get the <tileset> children
			List<Element> rootElementTilesetList = documentRootElement.getChildren("tileset");
			for (int i = 0; i < rootElementTilesetList.size(); i++) {
				// Get the current element
				Element tilesetElement = (Element) rootElementTilesetList.get(i);

				// Get all the attributes
				String tilesetName = tilesetElement.getAttributeValue("name");
				int tilesetFirstGID = Integer.valueOf(tilesetElement.getAttributeValue("firstgid"));
				int tilesetTileWidth = Integer.valueOf(tilesetElement.getAttributeValue("tilewidth"));
				int tilesetTileHeight = Integer.valueOf(tilesetElement.getAttributeValue("tileheight"));

				// Create a dimension for tile size
				Dimension tilesetTileSize = new Dimension(tilesetTileWidth, tilesetTileHeight);

				byte[] imageData = new byte[] {};

				// Get the <image> children
				List<Element> tilesetElementImageList = tilesetElement.getChildren("image");
				for (int j = 0; j < tilesetElementImageList.size(); j++) {
					// Get the current element
					Element imageElement = (Element) tilesetElementImageList.get(j);

					// Get the image source
					String imageSource = imageElement.getAttributeValue("source");

					// Create a proper image source by finding the parent path
					// of the map file
					String mapParentPath = getPathWithoutFilename(mapFile);

					// Reassign imageSource
					imageSource = String.format("%s/%s", mapParentPath, imageSource);

					// Create a file
					File imageFile = new File(imageSource);

					// Load the image, and get the byte data for it
					BufferedImage imageImage = ImageIO.read(imageFile);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();

					ImageIO.write(imageImage, "png", baos);
					baos.flush();
					imageData = baos.toByteArray();
					baos.close();
				}

				// Create the tileset and add it to the map
				Tileset tileset = new Tileset(tilesetName, tilesetFirstGID, tilesetTileSize, imageData);
				map.addTileset(tileset);
			}

			System.out.printf("... done\n", rootElementTilesetList.size());
			System.out.printf("Loading layers");

			// Get all children
			// (we only want <layer> and <objectgroup>)
			List<Element> rootElementLayerList = documentRootElement.getChildren();
			for (int i = 0; i < rootElementLayerList.size(); i++) {

				// Get current element
				Element layerChildElement = rootElementLayerList.get(i);

				if (layerChildElement.getName().equals("layer")) {
					// We have a <layer>

					// Get the attributes
					String layerName = layerChildElement.getAttributeValue("name");
					int layerWidth = Integer.valueOf(layerChildElement.getAttributeValue("width"));
					int layerHeight = Integer.valueOf(layerChildElement.getAttributeValue("height"));

					// Create a dimension
					Dimension layerSize = new Dimension(layerWidth, layerHeight);

					// Create a new tile layer
					TileLayer tileLayer = new TileLayer(layerName, layerSize);

					// Get the layer tiles (skip <data>, get <tile> children)
					List<Element> layerTilesList = layerChildElement.getChildren("data").get(0).getChildren("tile");
					for (int j = 0; j < layerTilesList.size(); j++) {
						// Get the current element
						Element layerTileElement = layerTilesList.get(j);

						// Get the attributes
						// (only one)
						int tileGid = Integer.valueOf(layerTileElement.getAttributeValue("gid"));

						if (tileGid == 0) {
							// Empty tile
							tileLayer.addTile(Tile.EMPTY);
						} else {
							// Create tile data ready
							byte[] tileData = map.getTileDataByGID(tileGid);

							// Create a new tile and add it to the layer
							Tile tile = new Tile(tileGid, tileData);
							tileLayer.addTile(tile);
						}
					}

					List<Element> layerChildElementProperties = new ArrayList<Element>();
					try {
						layerChildElementProperties = layerChildElement.getChildren("properties").get(0)
								.getChildren("property");
						for (int l = 0; l < layerChildElementProperties.size(); l++) {
							// Get the current element
							Element propertyElement = (Element) layerChildElementProperties.get(l);

							// Get the property from the element
							Property property = getPropertyFromElement(propertyElement);

							// Add the property to the map
							tileLayer.addProperty(property);
						}
					} catch (Exception e) {
					}

					// Add the layer
					map.addLayer(tileLayer);
				} else if (layerChildElement.getName().equals("objectgroup")) {
					// We have an <objectgroup>

					// Get the attributes
					String layerName = layerChildElement.getAttributeValue("name");
					int layerWidth = Integer.valueOf(layerChildElement.getAttributeValue("width"));
					int layerHeight = Integer.valueOf(layerChildElement.getAttributeValue("height"));

					// Create a dimension
					Dimension layerSize = new Dimension(layerWidth, layerHeight);

					// Create a new tile layer
					ObjectGroup objectGroup = new ObjectGroup(layerName, layerSize);

					// Get the layer tiles (skip <data>, get <tile> children)
					List<Element> layerObjectList = layerChildElement.getChildren("object");
					for (int j = 0; j < layerObjectList.size(); j++) {
						// Get the current element
						Element layerObjectElement = layerObjectList.get(j);

						// Get known string attributes
						String strObjectX = layerObjectElement.getAttributeValue("x");
						String strObjectY = layerObjectElement.getAttributeValue("y");

						// Now fetch conditional attributes
						String strObjectName = layerObjectElement.getAttributeValue("name");
						String strObjectType = layerObjectElement.getAttributeValue("type");
						String strObjectWidth = layerObjectElement.getAttributeValue("width");
						String strObjectHeight = layerObjectElement.getAttributeValue("height");
						String strObjectGID = layerObjectElement.getAttributeValue("gid");

						// Validate name and type
						if (strObjectName == null)
							// Fill with empty
							strObjectName = "";
						if (strObjectType == null)
							// Fill with empty
							strObjectType = "";

						// Validate width and height
						int nObjectWidth = -1;
						int nObjectHeight = -1;
						if (strObjectWidth != null)
							// Convert to int
							nObjectWidth = Integer.valueOf(strObjectWidth);
						if (strObjectHeight != null)
							// Convert to int
							nObjectHeight = Integer.valueOf(strObjectHeight);

						// Validate GID
						int iObjectGID = -1;
						if (strObjectGID != null)
							// Convert to int
							iObjectGID = Integer.valueOf(strObjectGID);

						// Convert X and Y
						int nObjectX = Integer.valueOf(strObjectX);
						int nObjectY = Integer.valueOf(strObjectY);

						// Get <ellipse> children
						List<Element> objectEllipseElementList = layerObjectElement.getChildren("ellipse");

						// Create a new object
						MapObject object = null;

						// Are we working with an ellipse ?
						if (objectEllipseElementList.size() > 0) {
							// Create an ellipse
							Ellipse2D.Float objectEllipse = new Ellipse2D.Float((float) nObjectX, (float) nObjectY,
									(float) nObjectWidth, (float) nObjectHeight);
							object = new EllipseObject(strObjectName, strObjectType, objectEllipse);
						} else {
							// Get <polygon> children
							List<Element> objectPolygonElementList = layerObjectElement.getChildren("polygon");

							// Are we working with a polygon ?
							if (objectPolygonElementList.size() > 0) {
								// Get the first element
								Element objectPolygonElement = objectPolygonElementList.get(0);

								// Now fetch the points
								String points = objectPolygonElement.getAttributeValue("points");

								// Create a polygon, ready to add points
								Polygon objectPolygon = new Polygon();

								// Create a regex and fetch points
								Pattern pointPattern = Pattern.compile("(-?[0-9]+),(-?[0-9]+)");
								Matcher pointMatcher = pointPattern.matcher(points);
								while (pointMatcher.find()) {
									// Convert to ints
									int polygonX = Integer.valueOf(pointMatcher.group(1));
									int polygonY = Integer.valueOf(pointMatcher.group(2));

									// Add the point to the polygon
									objectPolygon.addPoint(polygonX, polygonY);

									// Set the object
									object = new PolygonObject(strObjectName, strObjectType, nObjectX, nObjectY,
											objectPolygon);
								}
							} else {
								if (iObjectGID != -1) {
									// We're working with a tile object

									// Get the byte data for the GID
									byte[] tileData = map.getTileDataByGID(iObjectGID);

									// Set the object
									object = new TileObject(strObjectName, strObjectType, nObjectX, nObjectY,
											iObjectGID, tileData);
								} else {
									// We're working with a rectangle object

									// Create the rectangle
									Rectangle objectRectangle = new Rectangle(nObjectX, nObjectY, nObjectWidth,
											nObjectHeight);

									// Set the object
									object = new RectangleObject(strObjectName, strObjectType, objectRectangle);
								}
							}
						}

						List<Element> layerObjectElementProperties = new ArrayList<Element>();
						try {
							layerObjectElementProperties = layerObjectElement.getChildren("properties").get(0)
									.getChildren("property");
							for (int l = 0; l < layerObjectElementProperties.size(); l++) {
								// Get the current element
								Element propertyElement = (Element) layerObjectElementProperties.get(l);

								// Get the property from the element
								Property property = getPropertyFromElement(propertyElement);

								// Add the property to the map
								object.addProperty(property);
							}
						} catch (Exception e) {
						}

						// Add object to the group
						objectGroup.addObject(object);
					}

					List<Element> layerChildElementProperties = new ArrayList<Element>();
					try {
						layerChildElementProperties = layerChildElement.getChildren("properties").get(0)
								.getChildren("property");
						for (int l = 0; l < layerChildElementProperties.size(); l++) {
							// Get the current element
							Element propertyElement = (Element) layerChildElementProperties.get(l);

							// Get the property from the element
							Property property = getPropertyFromElement(propertyElement);

							// Add the property to the map
							objectGroup.addProperty(property);
						}
					} catch (Exception e) {
					}

					// Add the layer
					map.addLayer(objectGroup);
				}
			}
			System.out.printf("... done\n", map.getLayers().length);
		} catch (Exception e) {
			// Something went wrong
			e.printStackTrace();
		}

		System.out.printf("Map successfully loaded\n");

		// Return the map
		return map;
	}

	/**
	 * Gets the full path of a file without the filename.
	 * 
	 * Example: {@code /home/user/test.txt} will return {@code /home/user}
	 * 
	 * @param file
	 *            The file.
	 */
	private static String getPathWithoutFilename(File file) {
		// Fetch the absolute path
		String absolutePath = file.getAbsolutePath();

		// Trim the filename
		String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));

		// Return the result
		return filePath;
	}

	/**
	 * Determines the property values from an element.
	 * 
	 * @param element
	 *            The {@code Element} to check.
	 * @return Returns a {@code Property} or a subclass of {@code Property}
	 *         representing the property element.
	 */
	private static Property getPropertyFromElement(Element element) {
		// Get property name and value
		String sPropertyName = element.getAttributeValue("name");
		String sPropertyValue = element.getAttributeValue("value");

		// Boolean is the most unlikely to succeed, so we check it first.
		if (sPropertyValue.equals("true"))
			return new BooleanProperty(sPropertyName, true);
		else if (sPropertyValue.equals("false"))
			return new BooleanProperty(sPropertyName, false);
		else {

			try {
				// And then integer
				int nPropertyValue = Integer.valueOf(sPropertyValue);
				return new IntegerProperty(sPropertyName, nPropertyValue);
			} catch (Exception e) {
			}

			try {
				// And then float
				float fPropertyValue = Float.valueOf(sPropertyValue);
				return new FloatProperty(sPropertyName, fPropertyValue);
			} catch (Exception e) {
			}

			// Finally no data types were successfully converted.
			// Must be a String!
			return new StringProperty(sPropertyName, sPropertyValue);
		}
	}

	/**
	 * Converts a hexadecimal color to a {@code java.awt.Color}.
	 * 
	 * @param hex
	 *            The hexadecimal color (leading '#' is optional).
	 * @return Returns a {@code java.awt.Color} representing the color whose hex
	 *         code is {@code hex}.
	 */
	private static Color getColorFromHex(String hex) {
		if (hex.charAt(0) == '#')
			// Remove leading # character
			hex = hex.substring(1);

		// Get decimal RGB values by changing from base 16
		int r = Integer.valueOf(hex.substring(0, 2), 16);
		int g = Integer.valueOf(hex.substring(2, 4), 16);
		int b = Integer.valueOf(hex.substring(4, 6), 16);

		// Return the colour whose RGB values are such
		return new Color(r, g, b);
	}

};
