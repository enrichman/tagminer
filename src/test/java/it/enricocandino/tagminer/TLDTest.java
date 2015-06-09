package it.enricocandino.tagminer;

import it.enricocandino.tagminer.TLD;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Enrico Candino
 */
public class TLDTest {

    @Test
    public void isNotUrl() {
        assertFalse(TLD.INSTANCE.isUrl("abc"));
        assertFalse(TLD.INSTANCE.isUrl("abc.djnksjkc"));
        assertFalse(TLD.INSTANCE.isUrl("abc@dsjk.com"));
    }

    @Test
    public void isUrl() {
        assertTrue(TLD.INSTANCE.isUrl("abc.com"));
        assertTrue(TLD.INSTANCE.isUrl("abc.co.uk"));
        assertTrue(TLD.INSTANCE.isUrl("abc.it"));
    }

}
