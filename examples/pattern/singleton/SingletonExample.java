package pattern.singleton;

import java.lang.reflect.InvocationTargetException;

public class SingletonExample
{
	/**
	 * Accesses the custom singleton class instance.
	 */
	public static void main(String[] args)
	{
		SingletonClass s = SingletonClass.get( );
		
		s.setValue( 5 );
		
		print();
	}
	
	/**
	 * Prints out the value
	 */
	private static void print()
	{
		SingletonClass s = SingletonClass.get( );
		
		System.out.println("There exist " + SingletonClass.instantiationCounter + " instance with value: " + s.getValue( ));
	}
		
	/**
	 * A custom singleton class using {@link Singleton} to provide a thread safe
	 * access to the singleton instance.
	 * 
	 * @author Yannick Drost
	 *
	 */
	public static class SingletonClass
	{
		public static int instantiationCounter = 0;
		
		private int value = 0;
		
		private SingletonClass()
		{
			// private constructor
			instantiationCounter++;
		}
		
		public static SingletonClass get()
		{
			try
			{
				return (SingletonClass) Singleton.getInstance( SingletonClass.class );
			}
			catch ( InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e )
			{
				e.printStackTrace();
			}
			
			return null;
		}
		
		public void setValue(int v)
		{
			value = v;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
}


