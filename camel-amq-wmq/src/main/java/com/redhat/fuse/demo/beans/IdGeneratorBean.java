package com.redhat.fuse.demo.beans;

import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * A bean which we use in the route
 */
@Component
public class IdGeneratorBean implements IdGenerator {

	private Random random = new Random();

	@Override
	public String generateNewID() {
		return "" + random.nextInt(10000);
	}

}
