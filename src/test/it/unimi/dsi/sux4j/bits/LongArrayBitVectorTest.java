package test.it.unimi.dsi.sux4j.bits;

import it.unimi.dsi.sux4j.bits.BooleanListBitVector;
import it.unimi.dsi.sux4j.bits.LongArrayBitVector;
import it.unimi.dsi.sux4j.util.LongBigList;

import java.io.IOException;
import java.util.Random;

import junit.framework.TestCase;

public class LongArrayBitVectorTest extends TestCase {

	public void testSetClearFlip() {
		LongArrayBitVector v = LongArrayBitVector.getInstance();
		v.size( 1 );
		BitVectorTestCase.testSetClearFlip( v );
		v.size( 64 );
		BitVectorTestCase.testSetClearFlip( v );
		v.size( 80 );
		BitVectorTestCase.testSetClearFlip( v );
		v.size( 150 );
		BitVectorTestCase.testSetClearFlip( v );
		
		BitVectorTestCase.testSetClearFlip( v.subVector( 0, 90 ) );
		BitVectorTestCase.testSetClearFlip( v.subVector( 5, 90 ) );
	}

	public void testFillFlip() {
		LongArrayBitVector v = LongArrayBitVector.getInstance();
		v.size( 100 );
		BitVectorTestCase.testFillFlip( v );
		BitVectorTestCase.testFillFlip( v.subVector( 0, 90 ) );
		BitVectorTestCase.testFillFlip( v.subVector( 5, 90 ) );
	}
	
	public void testRemove() {
		BitVectorTestCase.testRemove( LongArrayBitVector.getInstance() );
		LongArrayBitVector v = LongArrayBitVector.getInstance();
		
		v.clear();
		v.size( 65 );
		v.set( 64 );
		v.removeBoolean( 0 );
		assertEquals( 0, v.bits[ 1 ] );
	}

	public void testAdd() {
		BitVectorTestCase.testAdd( LongArrayBitVector.getInstance() );
	}

	public void testCopy() {
		BitVectorTestCase.testCopy( LongArrayBitVector.getInstance() );
	}

	public void testBits() {
		BitVectorTestCase.testBits( LongArrayBitVector.getInstance() );
	}
		
	public void testLongBigListView() {
		BitVectorTestCase.testLongBigListView( LongArrayBitVector.getInstance() );
	}
	
	public void testLongSetView() {
		BitVectorTestCase.testLongSetView( LongArrayBitVector.getInstance() );
	}
	
	public void testFirstLast() {
		BitVectorTestCase.testFirstLastPrefix( LongArrayBitVector.getInstance() );
	}
	
	public void testLogicOperators() {
		BitVectorTestCase.testLogicOperators( LongArrayBitVector.getInstance() );
	}

	public void testCount() {
		BitVectorTestCase.testCount( LongArrayBitVector.getInstance() );
	}

	public void testSerialisation() throws IOException, ClassNotFoundException {
		BitVectorTestCase.testSerialisation( LongArrayBitVector.getInstance() );
	}
	
	public void testReplace() {
		BitVectorTestCase.testReplace( LongArrayBitVector.getInstance() );
	}

	
	public void testTrim() {
		assertTrue( LongArrayBitVector.getInstance( 100 ).trim() );
		assertFalse( LongArrayBitVector.getInstance( 100 ).length( 65 ).trim() );
		assertFalse( LongArrayBitVector.getInstance( 0 ).trim() );
	}
	
	public void testClone() throws CloneNotSupportedException {
		LongArrayBitVector v = LongArrayBitVector.getInstance( 100 ).length( 100 );
		for( int i = 0; i < 50; i++ ) v.set( i * 2 );
		assertEquals( v, v.clone() );
	}

	public void testEquals() {
		LongArrayBitVector v = LongArrayBitVector.getInstance( 100 ).length( 100 );
		for( int i = 0; i < 50; i++ ) v.set( i * 2 );
		LongArrayBitVector w = v.copy();
		assertEquals( v, w );
		w.length( 101 );
		assertFalse( v.equals( w ) );
		w.length( 100 );
		w.set( 3 );
		assertFalse( v.equals( w ) );
	}
	
	public void testConstructor() {
		final long bits[] = { 0, 1, 0 };
		
		boolean ok = false;
		try {
			LongArrayBitVector.wrap( bits, 64 );
		}
		catch( IllegalArgumentException e ) {
			ok = true;
		}
		
		assertTrue( ok );

		LongArrayBitVector.wrap( bits, 65 );
		LongArrayBitVector.wrap( bits, 128 );

		ok = false;
		try {
			LongArrayBitVector.wrap( bits, 193 );
		}
		catch( IllegalArgumentException e ) {
			ok = true;
		}
		
		assertTrue( ok );

		bits[ 0 ] = 10;
		bits[ 1 ] = 0;
		
		ok = false;
		try {
			LongArrayBitVector.wrap( bits, 3 );
		}
		catch( IllegalArgumentException e ) {
			ok = true;
		}
		
		assertTrue( ok );

		LongArrayBitVector.wrap( bits, 4 );
		
		bits[ 2 ] = 1;
		
		ok = false;
		try {
			LongArrayBitVector.wrap( bits, 4 );
		}
		catch( IllegalArgumentException e ) {
			ok = true;
		}
		
		assertTrue( ok );

	}
	
	public void testLongBig() {
		LongArrayBitVector v =  LongArrayBitVector.getInstance( 16 * 1024 );
		LongBigList l = v.asLongBigList( Short.SIZE );
		l.set( 0, 511 );
		assertEquals( 511, v.bits[ 0 ] );
		System.err.println( Long.toHexString( v.bits[ 0 ] ) );
	}
	
	public void testCopyAnotherVector() {
		Random r = new Random( 1 );
		LongArrayBitVector bv = LongArrayBitVector.getInstance( 200 );
		for( int i = 0; i < 100; i++ ) bv.add( r.nextBoolean() );
		assertEquals( LongArrayBitVector.copy( bv ), bv );
		bv = LongArrayBitVector.getInstance( 256 );
		for( int i = 0; i < 256; i++ ) bv.add( r.nextBoolean() );
		assertEquals( LongArrayBitVector.copy( bv ), bv );
		bv = LongArrayBitVector.getInstance( 10 );
		for( int i = 0; i < 10; i++ ) bv.add( r.nextBoolean() );
		assertEquals( LongArrayBitVector.copy( bv ), bv );
		BooleanListBitVector bbv = BooleanListBitVector.getInstance( 200 );
		for( int i = 0; i < 100; i++ ) bbv.add( r.nextBoolean() );
		assertEquals( LongArrayBitVector.copy( bbv ), bbv );
		bbv = BooleanListBitVector.getInstance( 256 );
		for( int i = 0; i < 256; i++ ) bbv.add( r.nextBoolean() );
		assertEquals( LongArrayBitVector.copy( bbv ), bbv );
		bbv = BooleanListBitVector.getInstance( 10 );
		for( int i = 0; i < 10; i++ ) bbv.add( r.nextBoolean() );
		assertEquals( LongArrayBitVector.copy( bbv ), bbv );
	}
}
