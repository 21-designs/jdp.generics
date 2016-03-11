package pattern.singleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import pattern.singleton.SingletonExample.SingletonClass;


public class SingletonTest
{
	
	@Test
	public void testMultiThreadedInstantiation( )
	{
		try
		{
			multiThreadedSingletonAccess( 3 );
		}
		catch ( InterruptedException | ExecutionException e )
		{
			e.printStackTrace( );
		}

		assertTrue( Singleton.isInstance( SingletonClass.class ) );
		
		assertEquals( SingletonClass.instantiationCounter, 1 );
		
		assertEquals( Singleton.getInstanceTypes( ).size( ), 1 );
		
		assertTrue( Singleton.getInstanceTypes( ).contains( SingletonClass.class ) );

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidArgument() throws Exception
	{
		Singleton.getInstance( null );
	}
	
	/**
	 * Creates multiple threads trying asynchronously to launch an individual
	 * singleton instance.
	 * 
	 * @param threadCount
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private void multiThreadedSingletonAccess( final int threadCount ) throws InterruptedException, ExecutionException
	{
		if( threadCount < 2 )
			throw new IllegalArgumentException( );

		Callable<SingletonClass> task = new Callable<SingletonClass>( )
		{
			@Override
			public SingletonClass call( )
			{
				return SingletonClass.get( );
			}
		};

		List<Callable<SingletonClass>> tasks = Collections.nCopies( threadCount, task );
		ExecutorService executorService = Executors.newFixedThreadPool( threadCount );
		List<Future<SingletonClass>> futures = executorService.invokeAll( tasks );

		List<SingletonClass> resultList = new ArrayList<SingletonClass>( futures.size( ) );

		try
		{
			// Check for exceptions
			for( Future<SingletonClass> future : futures )
			{
				// Throws an exception if an exception was thrown by the task.
				resultList.add( future.get( ) );
			}
		}
		catch ( Exception e )
		{
			fail( "Unexpected exception " + e.getMessage( ));
		}
	}
}
