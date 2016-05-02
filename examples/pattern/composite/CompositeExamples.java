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
package pattern.composite;

public class CompositeExamples
{
	private static String[] names = { "Mike", "Susi", "Kim", "John", "Michael", "Caroline", "Oliver", "Harry", "Ethan",
			"William" };

	/**
	 * Builds up a simple family tree of two generations and prints out the
	 * relations between all persons.
	 * 
	 * @param args
	 */
	public static void main( String[] args )
	{
		Person[] persons = new Person[5];

		for( int i = 0; i < persons.length; i++ )
		{
			persons[i] = new Person( getName( ) );
		}

		persons[0].addChild( persons[1] );
		persons[0].addChild( persons[2] );
		persons[0].addChild( persons[3] );

		persons[3].addChild( persons[4] );

		for( int i = 0; i < persons.length; i++ )
		{
			Person p = persons[i];

			if( p.getChildCount( ) > 0 )
			{
				System.out.println( p.name + " has the following children: " );
				for( Person child : p.getChildren( ) )
				{
					System.out.println( "\t" + child.name );
				}
			}
			else
			{
				System.out.println( p.name + " has no children." );
			}
		}
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

	/**
	 * Return a random name.
	 * 
	 * @return
	 */
	private static String getName( )
	{
		return names[(int) ( Math.random( ) * 10 )];
	}

}
