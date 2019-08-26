package main.java.de.nrw.lwk.streifen.util;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;

/**
 * Utility-Klasse für Operationen mit Geometrien im WKT-Format
 *
 * @author zrommel
 *
 * </br>
 * Copyright (c) 2019 Landwirtschaftskammer NRW
 */
//$Log: $
public class WktUtilities {

	static final GeometryFactory	geometryFactory	= JTSFactoryFinder.getGeometryFactory();
	static final WKTReader			wktRdr			= new WKTReader(geometryFactory);

	/**
	 * Converts Geometry to WellKnownText
	 * @param geom Input geometry
	 * @return Textual representation of geometry
	 */
	public static String geomToWkt(final Geometry geom) {
		return new WKTWriter().write(geom);
	}

	/**
	 * Converts WellKnownText to Geometry
	 * @param wkt Textual representation of geometry
	 * @return Geometry
	 * @throws ParseException
	 */
	public static Geometry wktToGeom(final String wkt) throws ParseException {
		return wktRdr.read(wkt);
	}

	/**
	 * Berechnet den kleineren Winkel zwischen zwei Strecken
	 * @param line1 Erste Strecke
	 * @param line2 Zweite Strecke
	 * @return kleinerer Winkel zwischen zwei Strecken in Grad
	 */
	public static double angleBetweenLines(final String line1, final String line2) throws ParseException {
		return GeomUtilities.angleBetweenLines((LineString) wktToGeom(line1), (LineString) wktToGeom(line2));

	}

	/**
	 * Berechnet den Winkel zwischen der Nordaxe und dem Vektor (p1,p2)
	 * @param p1 erster Punkt des Vektors, p2 zweiter Punkt des Vektors
	 * @return den Winkel zwischen der Nordaxe und dem Vektor (p1,p2) in Radianten
	 */
	public static double bearing(final String p1, final String p2) throws ParseException {
		return GeomUtilities.bearing((Point) wktToGeom(p1), (Point) wktToGeom(p1));

	}

	/**
	 * Verlängert eine Linie an ihrem Ende
	 * @param line Linie
	 * @param extend Verlängerung in Meter
	 * @return verlängerte Linie
	 */
	public static LineString extendLineOneSide(final String line, final double extend) throws ParseException {
		return GeomUtilities.extendLineOneSide(wktToGeom(line), extend);

	}

	/**
	 * Verlängert eine Linie an beiden Seiten
	 * @param line Linie
	 * @param extend Verlängerung in Meter
	 * @return verlängerte Linie
	 */
	public static LineString extendLine(final String line, final double extend) throws ParseException {
		return GeomUtilities.extendLine(wktToGeom(line), extend);

	}

	/**
	 * Gibt den ersten Punkt einer Geometrie zurück
	 * @param geom Input-Geometrie
	 * @return erster Punkt der Input-Geometrie
	 */
	public static Point getFirstPoint(final String geom) throws ParseException {
		return GeomUtilities.getFirstPoint(wktToGeom(geom));
	}

	/**
	 * Gibt den letzten Punkt einer Geometrie zurück
	 * @param geom Input-Geometrie
	 * @return den letzten Punkt der Input-Geometrie
	 */
	public static Point getLastPoint(final String geom) throws ParseException {
		return GeomUtilities.getLastPoint(wktToGeom(geom));
	}

	/**
	 * Liefert die längste Geometrie einer GeometrieCollection zurück
	 * @param pGeom Input-Geometrie
	 * @return längste Geometrie
	 */
	public static Geometry getLongestGeom(final String geom) throws ParseException {
		return GeomUtilities.getLongestGeom(wktToGeom(geom));
	}

	/**
	 * Prüft, ob die Geometrie null oder leer ist
	 * @param geom Input-Geometrie
	 * @return true wenn die Geometrie null oder leer ist, false sonst
	 * @throws ParseException
	 */
	public static boolean nullOrEmpty(final String geom) throws ParseException {
		return GeomUtilities.nullOrEmpty(wktToGeom(geom));
	}

	/**
	 * Transformiert die Input-Geometrie in eine Punktwolke
	 * @param geom Input-Geometrie
	 * @return MultiPoint-Geometrie, die aus allen Punkten der Input-Geometrie besteht
	 * @throws ParseException
	 */
	public static MultiPoint toMultipoint(final String geom) throws ParseException {
		return GeomUtilities.toMultipoint(wktToGeom(geom));
	}

}
