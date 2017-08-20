/*
Copyright CHORA INFORMATIQUE 2010-2011
 */

package com.choranet.badr.core.commandrunner;

import java.io.Closeable;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;

/**
 * Cette classe permet simplement d'encapsuler un CharSequence
 * afin de l'utiliser comme un Readable.
 * 
 * @see Readable
 * @see CharSequence
 * 
 * @author rabbah
 * @version 1.0-SNAPSHOT
 */
public class ReadableCharSequence implements Readable, Closeable {

	/** Le CharSequence depuis lequel les donn�es seront lues. */
	private final CharSequence cs;
	/** Position courante dans le CharSequence. */
	private int pos = 0;
	
	/**
	 * Construit un ReadableCharSequence qui lit les donn�es
	 * dans l'objet CharSequence en param�tre. 
	 * @param cs Le CharSequence source des donn�es.
	 */
	public ReadableCharSequence(CharSequence cs) {
		this.cs = cs;
		if (this.cs==null) {
			throw new NullPointerException("null");
		}
	}
	
	/**
	 * Ferme le flxu courant, et evenutellement le
	 * CharSequence si celui-ci impl�mente Closeable.
	 * @see Closeable
	 */
	public void close() throws IOException {
		if (this.cs instanceof Closeable) {
			((Closeable)this.cs).close();
		}
		this.pos = -1;
	}

	/**
	 * Lit lesflux depuis le CharSequence vers le CharBuffer en param�tre.
	 */
	public int read(CharBuffer cb) throws IOException {
		if (this.pos < 0) {
			throw new ClosedChannelException();
		}
		int remaining = this.cs.length()-pos;
		if (remaining>0) {
			int len = Math.min( cb.remaining() , remaining);
			cb.append(this.cs, pos, len);
			this.pos += len;
			return len;
		}
		return -1;
	}
}
