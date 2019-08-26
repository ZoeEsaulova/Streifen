package main.java.de.nrw.lwk.streifen.util;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryCollectionIterator;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;

/**
 * Utility-Klasse für Operationen mit Geometrien
 *
 * @author zrommel
 *
 * </br>
 * Copyright (c) 2019 Landwirtschaftskammer NRW
 */
public class GeomUtilities {

	static final GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

	/**
	 * Berechnet den kleineren Winkel zwischen zwei Strecken
	 * @param line1 Erste Strecke
	 * @param line2 Zweite Strecke
	 * @return kleinerer Winkel zwischen zwei Strecken in Grad
	 */
	public static double angleBetweenLines(final LineString line1, final LineString line2) {
		final double bearing1 = (Math.abs(bearing(getFirstPoint(line1), getLastPoint(line1))) * 180) / Math.PI;
		final double bearing2 = (Math.abs(bearing(getFirstPoint(line2), getLastPoint(line2))) * 180) / Math.PI;

		double angle = Math.abs(bearing1 - bearing2);
		if ((angle % 90) == 0) {
			angle = angle % 180;
		} else {
			angle = angle % 90;
		}
		return angle;

	}

	/**
	 * Berechnet den Winkel zwischen der Nordaxe und dem Vektor (p1,p2)
	 * @param p1 erster Punkt des Vektors, p2 zweiter Punkt des Vektors
	 * @return den Winkel zwischen der Nordaxe und dem Vektor (p1,p2) in Radianten
	 */
	public static double bearing(final Point p1, final Point p2) {
		double dBearing;
		final double dE1 = p1.getCoordinate().x;
		final double dN1 = p1.getCoordinate().y;
		final double dE2 = p2.getCoordinate().x;
		final double dN2 = p2.getCoordinate().y;
		final double dEast = dE2 - dE1;
		final double dNorth = dN2 - dN1;
		if (dEast == 0) {
			if (dNorth < 0) {
				dBearing = Math.PI;
			} else {
				dBearing = 0;
			}

		} else {
			dBearing = -Math.atan(dNorth / dEast) + (Math.PI / 2);
		}
		if (dEast < 0) {
			dBearing = dBearing + Math.PI;
		}

		return dBearing;

	}

	/**
	 * Verlängert eine Linie an ihrem Ende
	 * @param line Linie
	 * @param extend Verlängerung in Meter
	 * @return verlängerte Linie
	 */
	public static LineString extendLineOneSide(final Geometry line, final double extend) {
		final LineString newLine = (LineString) line.clone();
		final int numPoints = newLine.getNumPoints();
		final LineString lastSegment = geometryFactory.createLineString(new Coordinate[] { newLine.getCoordinateN(numPoints - 2), newLine.getCoordinateN(numPoints - 1) });
		final double lineLength = lastSegment.getLength();
		newLine.getCoordinateN(numPoints - 1).x = newLine.getCoordinateN(numPoints - 1).x
				+ ((newLine.getCoordinateN(numPoints - 1).x - newLine.getCoordinateN(numPoints - 2).x) * (extend / lineLength));
		newLine.getCoordinateN(numPoints - 1).y = newLine.getCoordinateN(numPoints - 1).y
				+ ((newLine.getCoordinateN(numPoints - 1).y - newLine.getCoordinateN(numPoints - 2).y) * (extend / lineLength));
		return newLine;

	}

	/**
	 * Verlängert eine Linie an beiden Seiten
	 * @param line Linie
	 * @param extend Verlängerung in Meter
	 * @return verlängerte Linie
	 */
	public static LineString extendLine(final Geometry line, final double extend) {
		LineString newLine = extendLineOneSide(line, extend);
		newLine = (LineString) extendLineOneSide(newLine.reverse(), extend).reverse();
		return newLine;

	}

	/**
	 * Gibt den ersten Punkt einer Geometrie zurück
	 * @param geom Input-Geometrie
	 * @return erster Punkt der Input-Geometrie
	 */
	public static Point getFirstPoint(final Geometry geom) {
		return geometryFactory.createPoint(geom.getCoordinates()[0]);
	}

	/**
	 * Gibt den letzten Punkt einer Geometrie zurück
	 * @param geom Input-Geometrie
	 * @return den letzten Punkt der Input-Geometrie
	 */
	public static Point getLastPoint(final Geometry geom) {
		return geometryFactory.createPoint(geom.getCoordinates()[geom.getNumPoints() - 1]);
	}

	/**
	 * Liefert die längste Geometrie einer GeometrieCollection zurück
	 * @param pGeom Input-Geometrie
	 * @return längste Geometrie
	 */
	public static Geometry getLongestGeom(final Geometry pGeom) {
		if (nullOrEmpty(pGeom)) {
			return null;
		}
		if (!(pGeom instanceof GeometryCollection)) {
			return pGeom;
		}
		final GeometryCollectionIterator iterator = new GeometryCollectionIterator(pGeom);
		Geometry geom = null;
		Geometry longestGeom = null;
		double maxLength = 0;
		double length = 0;
		while (iterator.hasNext()) {
			geom = (Geometry) iterator.next();
			length = geom.getLength();
			if (length > maxLength) {
				maxLength = length;
				longestGeom = geom;
			}
		}

		return longestGeom;
	}

	/**
	 * Lefert eine Koordinate, die zwischen zwei anderen Koordinaten liegt
	 * @param coord1 Erste Koordinate
	 * @param coord2 Zweite Koordinate
	 * @return mittlere Koordinate
	 */
	protected Coordinate getMiddleCoord(final Coordinate coord1, final Coordinate coord2) {
		return new Coordinate((coord1.x + coord2.x) / 2, (coord1.y + coord2.y) / 2);
	}

	/**
	 * Prüft, ob die Geometrie null oder leer ist
	 * @param geom Input-Geometrie
	 * @return true wenn die Geometrie null oder leer ist, false sonst
	 */
	public static boolean nullOrEmpty(final Geometry geom) {
		return (geom == null) || geom.isEmpty();
	}

	/**
	 * Transformiert die Input-Geometrie in eine Punktwolke
	 * @param geom Input-Geometrie
	 * @return MultiPoint-Geometrie, die aus allen Punkten der Input-Geometrie besteht
	 */
	public static MultiPoint toMultipoint(final Geometry geom) {
		return geometryFactory.createMultiPoint(geom.getCoordinates());
	}

}
