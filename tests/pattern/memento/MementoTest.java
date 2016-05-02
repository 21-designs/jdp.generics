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
package pattern.memento;

import org.junit.Test;
import static org.junit.Assert.*;

public class MementoTest
{
	String stateA = "State A", stateB = "State B", stateC = "State C";
	
	Originator<String> o = new Originator<String>();
	Caretaker<String> c = new Caretaker<String>();

	@Test
	public void testSetState()
	{
		o.setState( stateA );
		o.setState( stateB );
		
		assertEquals( stateB, o.getState( ) );
	}
	
	@Test
	public void testSaveStatetoMemento()
	{
		o.setState( stateA );
		
		Memento<String> m1 = o.saveStateToMemento( );
		
		assertEquals( m1.getState( ), stateA );
		
		c.addMemento( m1 );
		
		assertEquals( c.getMemento( 0 ), m1 );
		
		o.setState( stateB );
		
		Memento<String> m2 = o.saveStateToMemento( );
		
		assertEquals( m2.getState( ), stateB );
		
		c.addMemento( m2 );
		
		assertEquals( c.getMemento( 1 ), m2 );
	}
	
	@Test
	public void testGetStateFromMemento()
	{
		// Same sequence as in 'testSaveStateToMemento()'
		o.setState( stateA );
		Memento<String> m1 = o.saveStateToMemento( );
		c.addMemento( m1 );
				
		o.setState( stateB );
		Memento<String> m2 = o.saveStateToMemento( );
		c.addMemento( m2 );
		
		o.setState( stateC );
		
		o.getStateFromMemento( c.getMemento( 1 ) );
		
		assertEquals( o.getState( ), m2.getState( ));
		
		o.getStateFromMemento( c.getMemento( 0 ) );
		
		assertEquals( o.getState( ), m1.getState( ));
	}
}
