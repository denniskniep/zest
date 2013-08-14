/**
/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * 
 * @author Alessandro Secco: seccoale@gmail.com
 */
package org.mozilla.zest.test.v1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mozilla.zest.core.v1.ZestConditional;
import org.mozilla.zest.core.v1.ZestLoopBreak;
import org.mozilla.zest.core.v1.ZestLoopInteger;
import org.mozilla.zest.core.v1.ZestLoopNext;
import org.mozilla.zest.core.v1.ZestLoopString;
import org.mozilla.zest.core.v1.ZestStatement;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class ZestLoopIntegerUnitTest {
	static String[] values = { "a", "b", "c" };
	static List<ZestStatement> statements = new LinkedList<>();
	{
		statements.add(new ZestConditional());
		statements.add(new ZestLoopString(values));
	}

	@Test
	public void testLoop() {
		int maxIt = 10;
		ZestLoopInteger loop = new ZestLoopInteger(0, maxIt, statements);
		int iteration = loop.getCurrentToken();
		while (loop.loop()) {
			++iteration;
			assertTrue("right iteration index",
					iteration == loop.getCurrentToken());
		}
		assertTrue("right number of iterations",
				loop.getCurrentToken() == maxIt-1);// start is inclusive, end is exclusive!
	}

	@Test
	public void testEndLoop() {
		ZestLoopInteger loop = new ZestLoopInteger(0, 10, statements);
		loop.endLoop();
		assertTrue(loop.getCurrentState().isLastState(loop.getSet()));// it recognize the
															// last state
		assertFalse(loop.loop());// it returs false if the method loop is called
									// again
	}

	@Test
	public void testDeepCopy() {
		ZestLoopInteger loop = new ZestLoopInteger(0, 10, statements);
		loop.loop();
		ZestLoopInteger copy = loop.deepCopy();
		assertTrue(loop.getCurrentState().equals(copy.getCurrentState()));
	}

	@Test
	public void testHasMoreElements() {
		int numOfToken = 10;
		ZestLoopInteger loop = new ZestLoopInteger(0, numOfToken, statements);
		int counter = 0;
		while (loop.hasMoreElements()) {
			ZestStatement stmt = loop.nextElement();
			assertTrue(
					"statement " + counter,
					stmt.getClass().equals(
							statements.get(counter % statements.size())
									.getClass()));// check if the classes of the
													// statements are equals
			counter++;
		}
		assertTrue("right number of iteration ", counter == (numOfToken-1)
				* statements.size());// include start, exclude end!
	}
	@Test
	public void testZestLoopBreak(){
		List<ZestStatement> statements2=new LinkedList<>(statements);
		statements2.add(new ZestLoopBreak());
		ZestLoopInteger loop=new ZestLoopInteger(0, 1000000, statements2);
		int counterIteration=0;
		while(loop.hasMoreElements()){
			loop.nextElement();
			counterIteration++;
		}
		assertTrue(counterIteration==statements2.size()-1);
	}
	@Test
	public void testZestLoopNext(){
		LinkedList<ZestStatement> statements2=new LinkedList<>(statements);
		statements2.add(0,new ZestLoopNext());
		ZestLoopInteger loop=new ZestLoopInteger(0, 10, statements2);
		int counter=0;
		while(loop.hasMoreElements()){
			ZestStatement tmp=loop.nextElement();
			assertTrue("iteration "+counter, tmp instanceof ZestLoopNext);
			counter++;
		}
	}
	@Test
	public void testZestLoopDifferentStep(){
		ZestLoopInteger loop=new ZestLoopInteger("with step = 7", 0, 100, statements);
		loop.setStep(7);
		int counter=0;
		while(loop.hasMoreElements()){
			loop.loop();
			++counter;
			assertTrue("step: "+counter,loop.getCurrentToken()==counter*7);
		}
		assertTrue("end at: ", loop.getCurrentToken()==105);
	}
}