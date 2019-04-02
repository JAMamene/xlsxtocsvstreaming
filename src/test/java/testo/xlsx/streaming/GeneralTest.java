package testo.xlsx.streaming;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testo.xlsx.streaming.database.Database;
import testo.xlsx.streaming.importing.Streamer;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneralTest {

    private CSVImporter csvImporter;
    private Streamer streamer;
    private Database db;

    @Before
    public void setUp() {
        csvImporter = new CSVImporter();
        streamer = new Streamer();
        db = Database.getInstance();
    }

    @Test
    public void oneLineTest() throws IOException {
        streamer.stream(csvImporter.convertToCSV("line.xlsx"), 1, true);
        assertThat(db.getChunkNumber(), is(2)); // 1 + 1 empty
        assertThat(db.getSavedChunks().get(0).size(), is(1));
        assertThat(db.getSavedChunks().get(0).get(0).getProductId(), equalTo("1"));
        assertThat(db.getSavedChunks().get(0).get(0).getFields().length, is(57));
    }

    @Test
    public void twoLinesWithEmptyProductId() throws IOException {
        streamer.stream(csvImporter.convertToCSV("line2.xlsx"), 1, true);
        assertThat(db.getChunkNumber(), is(3)); // 2 + 1 empty
        assertThat(db.getSavedChunks().get(0).size(), is(1));
        assertThat(db.getSavedChunks().get(0).get(0).getProductId(), equalTo("1"));
        assertThat(db.getSavedChunks().get(1).get(0).getProductId(), equalTo(""));
        assertThat(db.getSavedChunks().get(0).get(0).getFields().length, is(57));
        assertThat(db.getSavedChunks().get(1).get(0).getFields().length, is(57));
    }

    @Test
    public void linesNotAMultiple() throws IOException {
        streamer.stream(csvImporter.convertToCSV("line3.xlsx"), 6, true);
        assertThat(db.getChunkNumber(), is(2));
        assertThat(db.getSavedChunks().get(0).size(), is(6));
        assertThat(db.getSavedChunks().get(1).size(), is(2));
        assertThat(db.getSavedChunks().get(0).get(0).getProductId(), equalTo("1"));
        assertThat(db.getSavedChunks().get(1).get(0).getProductId(), equalTo("7"));
    }


    @Test
    public void handleCommaAndQuotes() throws IOException {
        streamer.stream(csvImporter.convertToCSV("line4.xlsx"), 6, true);
        assertThat(db.getChunkNumber(), is(2));
        assertThat(db.getSavedChunks().get(0).get(0).getFields()[1], equalTo("\"ahahah\""));
        assertThat(db.getSavedChunks().get(0).get(1).getFields()[1], equalTo("salut, ca va"));
    }


    @After
    public void tearDown() {
        db.clean();
    }
}
