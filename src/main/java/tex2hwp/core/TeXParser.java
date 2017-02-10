package tex2hwp.core;

import org.w3c.dom.Node;
import uk.ac.ed.ph.snuggletex.SessionConfiguration;
import uk.ac.ed.ph.snuggletex.SnuggleEngine;
import uk.ac.ed.ph.snuggletex.SnuggleInput;
import uk.ac.ed.ph.snuggletex.SnuggleSession;

import java.io.IOException;

public class TeXParser {
    private final SnuggleEngine engine;
    private final SessionConfiguration configuration;
    private String mlString = null;
    private Node mlDom = null;

    public TeXParser() {
        engine = new SnuggleEngine();
        configuration = new SessionConfiguration();

        // not ignore error
        configuration.setFailingFast(true);
    }

    public void parse(String tex) throws IOException {
        SnuggleSession session = engine.createSession(configuration);
        SnuggleInput input = new SnuggleInput(tex);

        if (session.parseInput(input)) {
            mlString = session.buildXMLString();
            mlDom = session.buildDOMSubtree().item(0);
        } else {
            throw new IOException(session.getErrors().toString());
        }
    }

    public String getMlString() {
        return mlString;
    }

    public Node getMlDom() {
        return mlDom;
    }
}
