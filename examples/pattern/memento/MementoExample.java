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

/**
 * The memento pattern provides the ability to undo changes. It allows to store
 * several versions of the associated data.
 * 
 * @author kimschorat
 *
 */
public class MementoExample
{

	
	/**
	 * Stores some states during a sequence of operations and finally restores those states. 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Originator<String> originator = new Originator<String>();
		Caretaker<String> caretaker = new Caretaker<String>();
		
		originator.setState( "State A" );
		caretaker.addMemento( originator.saveStateToMemento( ) );	// Store 'State A'
		
		// Apply some changes ('State A' to 'State B')
		
		originator.setState( "State B" );
		caretaker.addMemento( originator.saveStateToMemento( ) );	// Store 'State B'

		// Apply some additional changes ('State B' to 'State C')
		
		originator.setState( "State C" );
		
		// Undo last changes ('State C' to 'State B')
		
		originator.getStateFromMemento( caretaker.getMemento( 1 ) );
		
		// Discard everything (return to 'State A')
		
		originator.getStateFromMemento( caretaker.getMemento( 0 ) );
	}
}
