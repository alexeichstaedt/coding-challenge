/*
 * JUnit Test Cases for "BanksCacheRemoteCalls.java"
 * 
 * Unit tests check to see if the config is initialized and a response is 
 * returned respectively. Mockito also used to mock the possibility of an 
 * exception occurring.
 * 
 * NOTE: verify the main in MockRemotes.java is running before running these tests.
 * If port(1234) is in use and a BindException is thrown, may need to open a 
 * terminal and exectute "lsof -ti:1234 | xargs kill" and then try again.
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

public class TestBanksRemoteCalls {

	private static BanksRemoteCalls banksRemoteCallsMgr;
	private static Mockito mock;
	
	@Before
	public void setUp() throws Exception {
		banksRemoteCallsMgr = new BanksRemoteCalls();
		mock = new Mockito();
	}

	@After
	public void tearDown() throws Exception {
		banksRemoteCallsMgr = null;
		mock = null;
	}

	@Test
	public void testConfigGetsInitializedWithNoErrors() throws Exception {
		banksRemoteCallsMgr.init();
		assertTrue(true); // config is initialized without errors
	}
	
	@Test
	public void testRemoteHandle() throws Exception {
		
		banksRemoteCallsMgr.init();
		Request request = null;
		Response response = null;
		String json = banksRemoteCallsMgr.handle(request, response);
		// System.out.println(json); // Can uncomment to verify response data
		assertNotNull(json);
		assertFalse(json.isEmpty());
	}
	
	@Test
	public void testRemoteHandleNoConfigInit() throws Exception {
		
		Request request = null;
		Response response = null;
		String json = banksRemoteCallsMgr.handle(request, response);
		// System.out.println(json); // Can uncomment to verify response data
		assertNotNull(json);
		assertFalse(json.isEmpty());
		assertFalse(json.equals("[]")); // Config should already be initialized
	}
	
	@Test(expected = Exception.class)
	public void testCacheHandlingThrowsException() throws Exception {
		
		BanksRemoteCalls brcMock = Mockito.mock(BanksRemoteCalls.class);
		banksRemoteCallsMgr.init();
		Request request = null;
		Response response = null;
		mock.when(brcMock.handle(request, response)).thenThrow(new Exception()); 
	}

}
