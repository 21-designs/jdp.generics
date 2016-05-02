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
 * This class contains the relevant internal state the {@code Caretaker} is
 * working on. It basically creates and stores states to {@code Memento}
 * objects. Those {@code Memento} objects may be restored by the
 * {@code Caretaker}.
 * 
 * @author kimschorat
 *
 * @param <T>
 *            The generic type parameter.
 */
public class Originator<T>
{
	private T state;

	public void setState( T state )
	{
		this.state = state;
	}

	public T getState( )
	{
		return state;
	}

	public Memento<T> saveStateToMemento( )
	{
		return new Memento<T>( state );
	}

	public void getStateFromMemento( Memento<T> m )
	{
		state = m.getState( );
	}
}
