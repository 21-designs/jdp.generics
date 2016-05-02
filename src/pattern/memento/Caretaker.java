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

import java.util.ArrayList;
import java.util.List;

/**
 * This class does something to the {@code Originator} class and retrieves an {@code Memento}
 * object. After some operations it may return the {@code Memento} back to the
 * {@code Originator} instance and thus undo the operations (via roll back).
 * 
 * @author kimschorat
 *
 * @param <T>
 *            The generic type parameter.
 */
public class Caretaker<T>
{
	private List<Memento<T>> mementos = new ArrayList<Memento<T>>();
	
	
	public boolean addMemento(Memento<T> m)
	{
		return mementos.add( m );
	}
	
	
	public void clearAll()
	{
		mementos.clear( );
	}
	
	
	public Memento<T> getMemento(int index)
	{
		return mementos.get( index );
	}
	
	
	public Memento<T> overwriteMemento(int index, Memento<T> m)
	{
		return mementos.set( index, m );
	}
}
