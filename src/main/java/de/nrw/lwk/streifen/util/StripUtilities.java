package main.java.de.nrw.lwk.streifen.util;

import com.vividsolutions.jts.geom.Geometry;

/**
 *  Utility-Klasse für Operationen mit Streifen-Geometrien
 *
 * @author zrommel
 *
 * </br>
 * Copyright (c) 2019 Landwirtschaftskammer NRW
 */
public class StripUtilities {

	/**
	 * Gibt die durchschnittliche Breite eines Streifens zurück
	 * Quelle der Formel: Konzept zur Erweiterung der Softwarekomponente LaFIS Januar 2017, GAF AG, München
	
	 * @param stripe
	 * @return
	 */
	public static double getStripeWidth(final Geometry strip) {
		final double stripeLength = strip.getLength();
		return (stripeLength / 4) - Math.sqrt(((stripeLength * stripeLength) / 16) - strip.getArea());
	}

	/**
	 * Gibt die durchschnittliche Länge eines Streifens zurück
	
	 * @param stripe
	 * @return
	 */
	public static double getStripeLength(final Geometry strip) {
		return (strip.getLength() - (getStripeWidth(strip) * 2)) / 2;
	}

}
