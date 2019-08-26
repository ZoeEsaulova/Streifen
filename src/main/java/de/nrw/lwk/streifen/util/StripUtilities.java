package main.java.de.nrw.lwk.streifen.util;

import com.vividsolutions.jts.geom.Geometry;

/**
 *  Utility-Klasse f�r Operationen mit Streifen-Geometrien
 *
 * @author zrommel
 *
 * </br>
 * Copyright (c) 2019 Landwirtschaftskammer NRW
 */
public class StripUtilities {

	/**
	 * Gibt die durchschnittliche Breite eines Streifens zur�ck
	 * Quelle der Formel: Konzept zur Erweiterung der Softwarekomponente LaFIS Januar 2017, GAF AG, M�nchen
	
	 * @param stripe
	 * @return
	 */
	public static double getStripeWidth(final Geometry strip) {
		final double stripeLength = strip.getLength();
		return (stripeLength / 4) - Math.sqrt(((stripeLength * stripeLength) / 16) - strip.getArea());
	}

	/**
	 * Gibt die durchschnittliche L�nge eines Streifens zur�ck
	
	 * @param stripe
	 * @return
	 */
	public static double getStripeLength(final Geometry strip) {
		return (strip.getLength() - (getStripeWidth(strip) * 2)) / 2;
	}

}
