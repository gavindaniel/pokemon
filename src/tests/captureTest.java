package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;

import org.junit.Test;

import items.Rock;
import items.SafariBall;
import model.TempTrainer;
import pokemon.Frequency;
import pokemon.Pokemon;
import strategies.Capture;

public class captureTest {

	@Test
	public void testFrequency() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		Pokemon uncommonPoke = new Pokemon(Frequency.UNCOMMON,true,"Pinser");
		Pokemon rarePoke = new Pokemon(Frequency.RARE,true,"Dratini");
		assertEquals(commonPoke.getCatchRate(),25);
		assertEquals(uncommonPoke.getCatchRate(),50);
		assertEquals(rarePoke.getCatchRate(),80);
		assertEquals(rarePoke.getRunRate(),40);
		assertEquals(uncommonPoke.getRunRate(),25);
		assertEquals(commonPoke.getRunRate(),10);
	}
	@Test
	public void testAddSafariBalls() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		TempTrainer trainer=new TempTrainer();
		for (int i=0;i<30;i++) {
		trainer.tools.add(new SafariBall());
		}
		assertEquals(trainer.tools.size(),30);
	}
	
	@Test
	public void testCommonRetreat() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		TempTrainer trainer=new TempTrainer();
		for (int i=0;i<30;i++) {
		trainer.tools.add(new SafariBall());
		}
		Capture common = new Capture(commonPoke,trainer);
		//Test when the Pokemon is neutral
		assertFalse(common.retreat(11));
		assertTrue(common.retreat(9));
		//Test when the Pokemon is eating
		common.currentPoke.mood="Eating";
		assertFalse(common.retreat(6));
		assertTrue(common.retreat(4));
		//Test when the Pokemon is angry
		common.currentPoke.mood="Angry";
		assertFalse(common.retreat(21));
		assertTrue(common.retreat(19));
		
	}
	@Test
	public void testUncommonRetreat() {
		Pokemon uncommonPoke = new Pokemon(Frequency.UNCOMMON,true,"Pinser");
		TempTrainer trainer=new TempTrainer();
		for (int i=0;i<30;i++) {
		trainer.tools.add(new SafariBall());
		}
		Capture uncommon = new Capture(uncommonPoke,trainer);
		//Test when the Pokemon is neutral
		assertFalse(uncommon.retreat(26));
		assertTrue(uncommon.retreat(24));
		//Test when the Pokemon is eating
		uncommon.currentPoke.mood="Eating";
		assertFalse(uncommon.retreat(13));
		assertTrue(uncommon.retreat(11));
		//Test when the Pokemon is angry
		uncommon.currentPoke.mood="Angry";
		assertFalse(uncommon.retreat(51));
		assertTrue(uncommon.retreat(49));
		
	}
	@Test
	public void testRareRetreat() {
		Pokemon rarePoke = new Pokemon(Frequency.RARE,true,"Dratini");
		TempTrainer trainer=new TempTrainer();
		for (int i=0;i<30;i++) {
		trainer.tools.add(new SafariBall());
		}
		Capture rare = new Capture(rarePoke,trainer);
		//Test when the Pokemon is neutral
		assertFalse(rare.retreat(41));
		assertTrue(rare.retreat(39));
		//Test when the Pokemon is eating
		rare.currentPoke.mood="Eating";
		assertFalse(rare.retreat(21));
		assertTrue(rare.retreat(19));
		//Test when the Pokemon is angry
		rare.currentPoke.mood="Angry";
		assertFalse(rare.retreat(81));
		assertTrue(rare.retreat(79));
		
	}
	@Test
	public void testThrowItems() {
		Pokemon rarePoke = new Pokemon(Frequency.RARE,true,"Dratini");
		TempTrainer trainer=new TempTrainer();
		for (int i=0;i<30;i++) {
		trainer.tools.add(new SafariBall());
		}
		Capture rare = new Capture(rarePoke,trainer);
		assertEquals(rare.currentPoke.mood,"Neutral");
		rare.throwRock();
		assertEquals(rare.currentPoke.mood,"Angry");
		rare.throwBait();
		assertEquals(rare.currentPoke.mood,"Eating");
		rare.retreat(81);
		assertEquals(rare.currentPoke.mood,"Eating");
	}
	
	
	@Test
	public void testCommonCapture() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		TempTrainer trainer=new TempTrainer();
		for (int i=0;i<30;i++) {
		trainer.tools.add(new SafariBall());
		}
		assertEquals(trainer.tools.size(),30);
		Capture common = new Capture(commonPoke,trainer);
		//Test when the Pokemon is neutral
		assertTrue(common.throwBall(26));
		assertFalse(common.throwBall(24));
		//Test when the Pokemon is eating
		common.currentPoke.mood="Eating";
		assertTrue(common.throwBall(32));
		assertFalse(common.throwBall(30));
		//Test when the Pokemon is angry
		common.currentPoke.mood="Angry";
		assertTrue(common.throwBall(13));
		assertFalse(common.throwBall(11));		
	}
	@Test
	public void testStartCaptureThrowRock() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		TempTrainer trainer=new TempTrainer();
		for (int i=0;i<30;i++) {
		trainer.tools.add(new SafariBall());
		}
		assertEquals(trainer.tools.size(),30);
		Capture common = new Capture(commonPoke,trainer);
		assertTrue(common.checkSafariBalls());
		
	    String input = "1 3 3 3 3 3 3 3 ";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		common.startCapture();
	
	}
	@Test
	public void testStartCaptureThrowBait() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		TempTrainer trainer=new TempTrainer();
		for (int i=0;i<30;i++) {
		trainer.tools.add(new SafariBall());
		}
		assertEquals(trainer.tools.size(),30);
		Capture common = new Capture(commonPoke,trainer);
		assertTrue(common.checkSafariBalls());
		
	    String input = "2 3 3 3 3 3 3 3 ";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		common.startCapture();
	
	}
	@Test
	public void testStartCaptureThrowBall() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		TempTrainer trainer=new TempTrainer();
		for (int i=0;i<30;i++) {
		trainer.tools.add(new SafariBall());
		}
		assertEquals(trainer.tools.size(),30);
		Capture common = new Capture(commonPoke,trainer);
		assertTrue(common.checkSafariBalls());
		
	    String input = "3 3 3 3 3 3 3 3";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		common.startCapture();
	
	}
	
	@Test
	public void testStartCaptureNoSafariBalls() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		TempTrainer trainer=new TempTrainer();
		Capture common = new Capture(commonPoke,trainer);
	    String input = "3";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		common.startCapture();
	
	}
	@Test
	public void testInvalidChoice() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		TempTrainer trainer=new TempTrainer();
		Capture common = new Capture(commonPoke,trainer);
	    String input = "q 3";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		common.startCapture();
	
	}
	@Test
	public void testNoSafariBalls() {
		Pokemon commonPoke = new Pokemon(Frequency.COMMON,true,"Caterpie");
		TempTrainer trainer=new TempTrainer();
		Capture common = new Capture(commonPoke,trainer);
		assertFalse(common.checkSafariBalls());
	
	}
	@Test
	public void testRetreat() {
		Pokemon commonPoke = new Pokemon(Frequency.RARE,true,"Dratini");
		TempTrainer trainer=new TempTrainer();
		Capture common = new Capture(commonPoke,trainer);
		String input = "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		common.startCapture();
	
	}

}
