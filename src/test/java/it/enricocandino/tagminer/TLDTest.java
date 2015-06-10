package it.enricocandino.tagminer;

import it.enricocandino.tagminer.miner.TLD;
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
        assertFalse(TLD.INSTANCE.isUrl("d'or.merci"));
        assertFalse(TLD.INSTANCE.isUrl("src=\"http://adcast.deviantart.com/delivery/avw.php?"));
        assertFalse(TLD.INSTANCE.isUrl("s'ouvre...de"));
    }

    @Test
    public void isUrl() {
        assertTrue(TLD.INSTANCE.isUrl("abc.com"));
        assertTrue(TLD.INSTANCE.isUrl("abc.co.uk"));
        assertTrue(TLD.INSTANCE.isUrl("abc.it"));
    }

}
