package com.axomble.java.javachat.base;



import java.util.Random;

/**
 * @author mfr
 */
public class RandomHelper{

    private static final String TAG = RandomHelper.class.getSimpleName();

    private static Random mRandom = null;

    private static Random getRandomGenerator(){
        if( mRandom == null ){
            mRandom = new Random( System.currentTimeMillis() );
        }
        return mRandom;
    }

    /**
     * BOOLEAN
     */

    public static boolean nextBool(){
        return getRandomGenerator().nextBoolean();
    }

    /**
     * INTEGER
     */

    public static int nextInt(){
        return getRandomGenerator().nextInt();
    }

    public static int nextInt( int min, int max ){
        return min + getRandomGenerator().nextInt( max - min );
    }

    /**
     * FLOAT
     */

    public static float nextFloat(){
        return getRandomGenerator().nextFloat();
    }

    public static float nextFloat( float min, float max ){
        return min + getRandomGenerator().nextFloat() * ( max - min );
    }

    /**
     * LONG
     */

    public static long nextLong(){
        return getRandomGenerator().nextLong();
    }

    public static long nextLong( long min, long max ){
        return (long) (min + getRandomGenerator().nextFloat() * ( max - min ));
    }

    /**
     * COLOR
     */

//    public static int nextColor(){
//        return Color.rgb( getRandomGenerator().nextInt( 256 ), getRandomGenerator().nextInt( 256 ), getRandomGenerator().nextInt( 256 ) );
//    }

    /**
     * STRING
     */

    public static String nextString(){
        return nextString( 10 );
    }

    public static String nextString( int charCount ){
        char[] chars = "abcdefghijklmnopqrstuvwxyz ,.-1234567980".toCharArray();
        return nextString( chars, charCount );
    }

    public static String nextString( char[] chars, int charCount ){
        StringBuilder sb = new StringBuilder();
        Random random = getRandomGenerator();
        for( int i = 0; i < charCount; i++ ){
            char c = chars[ random.nextInt( chars.length ) ];
            sb.append( c );
        }
        return sb.toString();
    }
    
    /**
     * STRING - MESSAGE
     */
    
    public static final String DUMMY_TEXT = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
    
    public static String nextMessage(){
    	return nextMessage(DUMMY_TEXT,10,50);
    }
    
    public static String nextMessage( String fullMessage, int minCharCount, int maxCharCount ){
    	int randomeCharCount = nextInt(minCharCount, Math.min(fullMessage.length(),maxCharCount));
    	int randomStartIndex = nextInt( 0, fullMessage.length()-randomeCharCount);
    	return DUMMY_TEXT.substring( randomStartIndex, randomStartIndex + randomeCharCount );
    }

    /**
     * -1, 0, 1
     */

    public static int nextTendency(){
        return nextBool() ? 1 : nextBool() ? -1 : 0;
    }

    /**
     * DATE
     */

//    public static DateTime nextDateTime(){
//        long now = DateTime.now().getMillis()/1000;
//        long min = DateTime.now().minusYears( 20 ).getMillis()/1000;
//        return new DateTime( nextLong( min, now ) );
//    }
//
//    public static String nextDateAsString( String format ){
//        return nextDateTime().toString( format );
//    }
}
