/*
 * JUnit Test Cases for "BanksCacheBased.java"
 * 
 * Unit tests check to see if the cache is initialized and a response is 
 * returned respectively. Mockito also used to mock the possibility of an 
 * exception occurring.
 * 
 * Test methods named to indicate their purpose.
 * 
 */
package io.bankbridge.handler;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import spark.Request;
import spark.Response;

public class TestBanksCacheBased {

	static BanksCacheBased banksCacheBasedMgr;
	static Mockito mock;

	@Before
	public void setUp() throws Exception {
		banksCacheBasedMgr = new BanksCacheBased();
		mock = new Mockito();
	}

	@After
	public void tearDown() throws Exception {
		banksCacheBasedMgr.cacheManager.getCache("banks", String.class, String.class).clear();
		banksCacheBasedMgr = null;
		mock = null;
	}

	@Test
	public void testCacheGetsProperlyInitialized() throws Exception {

		try {
			banksCacheBasedMgr.init();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

		assertTrue(true); // No exception thrown, cache initialized
	}

	@Test
	public void testCacheThrowsException() throws Exception {

		BanksCacheBased bcmMock = Mockito.mock(BanksCacheBased.class);
		try {
			mock.doThrow(new Exception()).when(bcmMock).init();
		} catch (Exception e) {
			assertTrue(true); // Catch exception thrown upwards
		}

	}

	@Test
	public void testCacheGetsHandledReturnsCorrectInfo() throws Exception {

		try {
			banksCacheBasedMgr.init();
			Request request = null;
			Response response = null;
		
			String result = (String) banksCacheBasedMgr.handle(request,response);
			assertNotNull(result); // verify the result is not null
			assertFalse(result.isEmpty()); // verify the result is not empty
			assertFalse(result.equals("[]")); // verify result returns info from cache
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testCacheGetsHandledNotInitialized() throws Exception {

		try {
			Request request = null;
			Response response = null;
			String result = (String) banksCacheBasedMgr.handle(request,response);
			assertTrue(result.equals("[]")); // verify that without initializing the cache, result returns empty.
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test(expected = Exception.class)
	public void testCacheHandlingThrowsException() throws Exception {
		
		BanksCacheBased bcmMock = Mockito.mock(BanksCacheBased.class);
		banksCacheBasedMgr.init();
		Request request = null;
		Response response = null;
		mock.when(bcmMock.handle(request, response)).thenThrow(new Exception()); 
	}

}
