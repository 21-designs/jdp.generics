/*
 * This file is part of the application library that simplifies common
 * initialization and helps setting up any java program.
 * 
 * Copyright (C) 2016 Yannick Drost, all rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pattern.builder;

import java.io.InvalidClassException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Builder<T>
{
	public final T instance;

	private final Class<?> clazz;

	/**
	 * Creates a builder object associated to a specified class type. This
	 * builder object allows to preset any class fields by using
	 * {@link #set(String, Object...)} before instantiating the actual class
	 * type with {@link #build()}.
	 * <p>
	 * For this generic builder design pattern to work the specified class type
	 * needs to provide an default constructor not consuming any arguments.
	 * </p>
	 * 
	 * @param clazz
	 *            The specified class type.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InvalidClassException 
	 */
	public Builder( Class<T> clazz )
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidClassException
	{
		super( );
		this.clazz = clazz;

		@SuppressWarnings( "unchecked" )
		Constructor<T>[] ctors = (Constructor<T>[]) clazz.getDeclaredConstructors( );
		Constructor<T> ctor = null;

		for( int i = 0; i < ctors.length; i++ )
		{
			
			if( ctors[i].getGenericParameterTypes( ).length == 0 )
			{
				ctor = ctors[i];
				break;
			}
		}

		if( ctor != null )
		{
			ctor.setAccessible( true );
			this.instance = ctor.newInstance( );
			ctor.setAccessible( false );
		}
		else
		{
			throw new InvalidClassException( "The generic class type does not provide an accessible default constructor." );
		}
		
	}

	/**
	 * Presets any class fields accessible by the associated class type. While
	 * this method returns itself it allows to invoke several method calls in a
	 * row like the following example shows
	 * 
	 * <pre>
	 * new Builder( JFrame.class ).set( "setDefaultCloseOperation", 3 ).set( "setLocation", 50, 50 );
	 * </pre>
	 * 
	 * <p>
	 * After applying all necessary parameters the predefined object can be
	 * returned by calling {@link #build()}. This is also possible in the same
	 * row as all the presetting takes place to write the whole instantiation in
	 * a single line like
	 * </p>
	 * 
	 * <pre>
	 * JFrame frame = new Builder( JFrame.class ).set( ... ).build();
	 * </pre>
	 * 
	 * <p>
	 * This method consumes the name of the setter method rather than any class
	 * member names but while the related class member has the same name as its
	 * setter method it is possible to use that name too. The following code
	 * shows a short example.
	 * </p>
	 * 
	 * <pre>
	 * public class Example
	 * {
	 * 	public int state;
	 * 
	 * 	public void setState( int newState )
	 * 	{
	 * 		this.state = newState;
	 * 	}
	 * }
	 * </pre>
	 * 
	 * <p>
	 * Assuming this {@code Example} class the {@link #set(String, Object...)}
	 * method can either be used as {@code set( "setState", 5 )} or as
	 * {@code set( "state", 5 )}. This is only possible for methods having the
	 * same name as their associated members.
	 * </p>
	 * 
	 * @param name
	 *            The name of the associated setter method or even the same
	 *            named class field.
	 * @param values
	 *            The values this method consumes or one argument while
	 *            referring to a class member (usually all setters also take one
	 *            single argument writing it to the associated field).
	 * @return This builder instance.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public Builder<T> set( String name, Object... values ) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException
	{

		if( name == null || name.isEmpty( ) )
			throw new IllegalArgumentException( "Invalid property name: " + name );

		// Set to lower case.
		name.toLowerCase( );

		Class<?>[] classes = new Class<?>[values.length];
		for( int i = 0; i < values.length; i++ )
		{
			Class<?> type = values[i].getClass( );
			
			if(type.equals( Integer.class ))
			{
				classes[i] = int.class;
				continue;
			}
			if(type.equals( Double.class ))
			{
				classes[i] = double.class;
				continue;
			}
			if(type.equals( Short.class ))
			{
				classes[i] = short.class;
				continue;
			}
			if(type.equals( Long.class ))
			{
				classes[i] = long.class;
				continue;
			}
			if(type.equals( Float.class ))
			{
				classes[i] = float.class;
				continue;
			}
			if(type.equals( Character.class ))
			{
				classes[i] = char.class;
				continue;
			}
			if(type.equals( Boolean.class ))
			{
				classes[i] = boolean.class;
				continue;
			}
			
			classes[i] = type;
		}

		String mName = ( name.startsWith( "set" ) ) ? name : ("set" + name.substring( 0, 1 ).toUpperCase( ) + name.substring( 1 ));
		
		System.out.println(mName + "(" + classes[0] + ")");
		
		// Fetch associated method.
		Method method = clazz.getMethod(
				mName,
				classes );

		method.invoke( instance, values );
		return this;
	}

	/**
	 * Generally invoking this method using a specified builder pattern it
	 * creates and returns a new instance of the associated class type as
	 * defined in the design pattern, regarding all specified parameters that
	 * have been preset. Actually this generic builder pattern simply returns a
	 * already instantiated and predefined instance of the type {@code T}, but
	 * has the same effect as the official builder pattern.
	 * 
	 * @return A new and predefined instance of the specified class type
	 *         {@code T}.
	 */
	public T build( )
	{
		return instance;
	}

	/**
	 * Returns the specified class type this builder is associated to.
	 * 
	 * @return the specified class type this builder is associated to.
	 */
	public final Class<?> getAssociatedClassType( )
	{
		return clazz;
	}
}
