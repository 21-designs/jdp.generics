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
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import static org.junit.Assert.*;

public class BuilderTest
{
	/**
	 * Catches an exception that is thrown whenever the associated class type
	 * does not provide an accessible default constructor.
	 * 
	 * @throws InvalidClassException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Test( expected = InvalidClassException.class )
	public void testInvalidClass( ) throws InvalidClassException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		InvalidClass ic = new Builder<InvalidClass>( InvalidClass.class ).build( );
	}

	/**
	 * Ensures that the {@code build} method returns a valid instance of the
	 * associated class type.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InvalidClassException
	 */
	@Test
	public void testBuild( ) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InvalidClassException
	{
		Person p = new Builder<Person>( Person.class ).build( );
		assertNotEquals( p, null );
	}

	/**
	 * Sets a property of the associated class type by invoking
	 * {@link Builder#set(String, Object...)}.
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws InvalidClassException
	 */
	@Test
	public void testSetProperty( ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, InstantiationException, InvalidClassException
	{
		Person p = new Builder<Person>( Person.class ).set( "setAge", 35 ).set( "setName", "Mike" ).build( );

		assertEquals( p.age, 35 );
		assertEquals( p.name, "Mike" );
	}

	/**
	 * The Builder pattern does not work with this class definition.
	 * 
	 * @author kimschorat
	 *
	 */
	private static class InvalidClass
	{
		private Object arg;

		public InvalidClass( Object arg )
		{
			this.arg = arg;
		}
	}

	/**
	 * A valid class definition.
	 * 
	 * @author kimschorat
	 *
	 */
	private static class Person
	{
		int age;

		String name;

		public void setAge( int age )
		{
			this.age = age;
		}

		public void setName( String name )
		{
			this.name = name;
		}
	}
}
