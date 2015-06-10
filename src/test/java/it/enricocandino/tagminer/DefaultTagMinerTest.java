package it.enricocandino.tagminer;

import it.enricocandino.model.TaggedSentence;
import org.junit.Test;

/**
 * @author Enrico Candino
 */
public class DefaultTagMinerTest {

    @Test
    public void strangeResultTest() throws Exception {
        String original = "Vromb - L’appareillage ultratonique [ vromb - sito ufficiale ] [ mémoires paramoléculaires - il sito ] [ disques hushush ] [ buy this @ strange fortune ] Posted: August 5, 2005 5 Comments» The URI to TrackBack this entry is: http://23rd.blogsome.com/2005/08/05/vromb/trackback/ testcomment155 Comment by testanchor458 — October 16, 2005 @ 1:48 am Many peoples use this way of culture.";

        DefaultMiner miner = new DefaultMiner();
        TaggedSentence taggedSentence = miner.mine(original);

        System.out.println(taggedSentence);
    }
}
