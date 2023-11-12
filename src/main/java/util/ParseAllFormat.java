package util;

import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

public class ParseAllFormat extends Format{
    private final Format fDelegate;

    public ParseAllFormat( Format aDelegate ) {
        fDelegate = aDelegate;
    }

    @Override
    public StringBuffer format( Object obj, StringBuffer toAppendTo, FieldPosition pos ) {
        return fDelegate.format( obj, toAppendTo, pos );
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator( Object obj ) {
        return fDelegate.formatToCharacterIterator( obj );
    }

    @Override
    public Object parseObject( String source, ParsePosition pos ) {
        int initialIndex = pos.getIndex();
        Object result = fDelegate.parseObject( source, pos );
        if ( result != null && pos.getIndex() < source.length() ) {
            int errorIndex = pos.getIndex();
            pos.setIndex( initialIndex );
            pos.setErrorIndex( errorIndex );
            return null;
        }
        return result;
    }

    @Override
    public Object parseObject( String source ) throws ParseException {
        return super.parseObject( source );
    }
}
