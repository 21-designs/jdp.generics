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
package org.drost.jdp.generics.builder;

import java.io.InvalidClassException;
import java.lang.reflect.InvocationTargetException;

import org.drost.jdp.generics.builder.Builder;

/**
 * This example shows the usage of the builder pattern by creating a Person
 * instance.
 * 
 * @author kimschorat
 *
 */
public class BuilderExample
{

	public static void main( String[] args ) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, InvalidClassException
	{
		Person p = new Builder<Person>( Person.class ).set( "age", 35 ).set( "setName", "Mike" ).build( );
		p.sayHello( );
	}

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

		public void sayHello( )
		{
			System.out.println( "Hello, I'm " + name + " and I'm " + age + " years old." );
		}
	}
}
