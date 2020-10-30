package logger;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class LogTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerWhenLogFileIsEmpty() throws IOException {
        File file = Log.getLogFile();
        Files.delete(file.toPath());

        Log.showLog();

    }

    @Test
    public void shouldWriteStringLogToLogFile() throws FileNotFoundException {

        String line = "Test line";

        Log.clear();
        Log.out(line);
        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains(line);
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldWriteErrorLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out(new IllegalAccessException("test error").getMessage());

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("test error");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();
    }

    @Test
    public void shouldWriteIntLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out(4);
        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("4");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldWriteBooleanLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out(true);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("true");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldWriteLongLogToLogFile() throws FileNotFoundException {

        Log.clear();
        long test = 999999999999999999L;
        Log.out(test);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("999999999999999999");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldWriteCharLogToLogFile() throws FileNotFoundException {

        Log.clear();
        char test = 'c';
        Log.out(test);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("c");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldSetLogPriorityDEBUG() throws FileNotFoundException {

        Log.clear();
        Log.level(Log.Level.DEBUG);
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("DEBUG");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();
    }

    @Test
    public void shouldSetLogPriorityWARNING() throws FileNotFoundException {

        Log.clear();
        Log.level(Log.Level.WARNING);
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("WARNING");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldSetLogPriorityLOG() throws FileNotFoundException {

        Log.clear();
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("LOG");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldSetLogPriorityERROR() throws FileNotFoundException {

        Log.clear();
        Log.level(Log.Level.ERROR);
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("ERROR");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldUpdateLogLocation() throws FileNotFoundException {

        Log.clear();
        Log.location("logs\\log");

        Log.out("this is the test line");

        assertThat(Log.getLogFile()).exists();
        Scanner scan = new Scanner(new File(Log.getLocation()));

        assertThat(scan.nextLine()).contains("this is the test line");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldUpdateLogName() throws FileNotFoundException {

        Log.name("second log");

        Log.out("this is the test line for the second log location");

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(new File(Log.getLocation()));
        assertThat(scan.nextLine()).contains("this is the test line for the second log location");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();
    }

    @Test
    public void shouldDeleteLogFile() {
        Log.out("test line");
        assertThat(Log.getLogFile()).exists();

        assertThat(Log.delete()).isTrue();
        assertThat(Log.getLogFile()).doesNotExist();
    }
}
