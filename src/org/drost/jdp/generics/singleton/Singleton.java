package org.drost.jdp.generics.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

/**
 * This class provides a thread safe implementation of the singleton design
 * pattern by using a double checked synchronization. It can handle any class
 * type and stores multiple instances of different types.
 * 
 * This class cannot be instantiated itself but provides the main method
 * {@code get()} to access the singleton instances.
 * 
 * @author Yannick Drost
 *
 * @see #getInstance(Class)
 */
public final class Singleton
{
	/**
	 * Stores all singleton instances of different class types.
	 */
	private static final HashMap<Class<?>, InstanceWrapper<?>> instanceMap = new HashMap<Class<?>, InstanceWrapper<?>>( );

	/**
	 * Wraps the singleton instance for correctness. The performance of this
	 * pattern is not necessarily better than the {@code volatile}
	 * implementation.
	 * 
	 * @author Yannick Drost
	 * @since 1.0
	 */
	private static final class InstanceWrapper<T>
	{
		/**
		 * The singleton instance wrapped in this container class. Since it
		 * stores a singleton this field has a {@code final} modifier.
		 */
		public final T instance;

		/**
		 * Create a new wrapper instance containing the generic singleton.
		 * 
		 * @param value
		 *            The generic instance.
		 */
		public InstanceWrapper( final T value )
		{
			this.instance = value;
		}
	}

	/**
	 * Restricted instantiation.
	 */
	private Singleton( )
	{
		assert false : "Uninstantiable class: " + this.getClass( ).getName( );
	}

	/**
	 * Creates the singleton instance in the most safest way. This ensures that
	 * no second instance might be created at the same time using multi-threaded
	 * applications.
	 * </p>
	 * This method basically invokes the {@code Class.newInstance()} method thus
	 * the class type parameter needs to implement a default constructor. Due to
	 * the singleton design pattern creates and handles the only instance of
	 * type {@code singletonClass} prevent any instantiation of the
	 * {@code singletonClass} by hiding the default constructor. An example
	 * shows the proper usage:
	 * 
	 * <pre>
	 * public class SingletonExample
	 * {
	 * 	private SingletonExample( )
	 * 	{
	 * 		assert false : "Uninstantiable";
	 * 	}
	 * 
	 * 	public static SingletonExample getInstance( )
	 * 	{
	 * 		return Singleton.get( ExampleClass.class );
	 * 	}
	 * }
	 * </pre>
	 * 
	 * While the class has not been instantiated yet, a new instance is created
	 * (assuming there is a default constructor provided by the class argument).
	 * 
	 * @param singletonClass
	 *            The class to be created as a singleton instance.
	 * @return The singleton application instance.
	 * @throws IllegalAccessException
	 *             if the class or its constructor is not accessible.
	 * @throws InstantiationException
	 *             if the instantiation fails for some reason.
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 *             if the parameter is {@code null}.
	 * 
	 * @see Constructor#newInstance()
	 * @see <a href=
	 *      "https://en.wikipedia.org/wiki/Double-checked_locking#Usage_in_Java">
	 *      Double check synchronization</a>
	 */
	@SuppressWarnings( "unchecked" )
	public static final <T> Object getInstance( Class<T> singletonClass )
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if( singletonClass == null )
			throw new IllegalArgumentException( "Null argument. Cannot instantiate singleton." );

		InstanceWrapper<T> wrapper = (InstanceWrapper<T>) instanceMap.get( singletonClass );

		if( wrapper == null )
		{
			synchronized ( Singleton.class )
			{
				if( instanceMap.get( singletonClass ) == null )
				{
					T instance = null;

					Constructor<T>[] ctors = (Constructor<T>[]) singletonClass.getDeclaredConstructors( );
					Constructor<T> ctor = null;

					for( int i = 0; i < ctors.length; i++ )
					{
						ctor = ctors[i];
						if( ctor.getGenericParameterTypes( ).length == 0 )
							break;
					}

					ctor.setAccessible( true );
					instance = ctor.newInstance( );
					ctor.setAccessible( false );

					instanceMap.put( singletonClass, new InstanceWrapper<T>( instance ) );
				}

				wrapper = (InstanceWrapper<T>) instanceMap.get( singletonClass );
			}
		}

		return wrapper.instance;
	}

	/**
	 * Returns whether this class has already been instantiated.
	 * 
	 * @param singletonClass
	 *            The class type of the specified singleton instance.
	 * @return whether this class has already been instantiated.
	 */
	public static final boolean isInstance( Class<?> singletonClass )
	{
		if( singletonClass == null )
			return false;

		return ( instanceMap.get( singletonClass ) != null );
	}

	/**
	 * Returns a set of all classes that already have been initialized by
	 * invoking {@link #getInstance(Class)}. This set can be empty.
	 * 
	 * @return a set of all classes that already have been initialized by
	 *         invoking {@link #getInstance(Class)}.
	 */
	public static Set<Class<?>> getInstanceTypes( )
	{
		return instanceMap.keySet( );
	}
}
