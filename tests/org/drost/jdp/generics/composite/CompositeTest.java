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
package org.drost.jdp.generics.composite;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.drost.jdp.generics.composite.Composite;
import org.junit.After;
import org.junit.Before;

public class CompositeTest
{
	Person p = new Person("Mike");
	Person child1 = new Person("Oliver");
	Person child2 = new Person("Emma");

	@Before
	public void setUp()
	{
		p.addChild( child1 );
		p.addChild( child2 );
	}
	
	@After
	public void tearDown()
	{
		if(p.getChildCount( ) > 0)
		p.removeAllChildren( );
	}
	
	@Test
	public void testAddChild()
	{
		assertEquals( p.getChildCount( ), 2 );
	}
	
	@Test
	public void testRemoveChild1()
	{
		assertTrue( p.removeChild( child1 ) );
		assertEquals( p.getChildCount( ), 1);
	}
	
	@Test
	public void testRemoveChild2()
	{
		assertEquals( child1, p.removeChild( 0 ) );
		assertEquals( p.getChildCount( ), 1);
	}
	
	
	@Test
	public void testRemoveAllChildren()
	{
		p.removeAllChildren( );
		
		assertEquals( 0, p.getChildCount( ) );
	}
	
	@Test
	public void testGetChildCount()
	{
		assertEquals( 2, p.getChildCount( ) );
	}
	
	@Test
	public void testGetChildren()
	{
		assertEquals( Arrays.asList( child1, child2 ), p.getChildren( ) );
	}
	
	@Test
	public void testSetChildren()
	{
		ArrayList<Person> children = new ArrayList<Person>();
		children.add( child1 );
		p.setChildren( children );
		
		assertEquals( 1, p.getChildCount( ) );
		assertEquals( Arrays.asList( child1 ), p.getChildren( ) );
	}
	
	
	
	
	/**
	 * This class extends the composite pattern.
	 * 
	 * @author kimschorat
	 *
	 */
	private static class Person extends Composite<Person>
	{
		private final String name;

		public Person( String name )
		{
			this.name = name;
		}
	}
}
