package com.redhat.camel.amq.beans;

/**
 * An interface for implementing ID Generator services.
 */
public interface IdGenerator {

	/**
	 * @return New unique ID
	 */
	String generateNewID();
	
}
