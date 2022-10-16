package nz.ac.auckland.se206.speech;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

/** Text-to-speech API using the JavaX speech library. */
public class TextToSpeech {
  /** Custom unchecked exception for Text-to-speech issues. */
  static class TextToSpeechException extends RuntimeException {
    public TextToSpeechException(final String message) {
      super(message);
    }
  }

  /**
   * Main function to speak the given list of sentences.
   *
   * @param args A sequence of strings to speak.
   */
  public static void main(final String[] args) {
    if (args.length == 0) {
      // get the correct exception
      throw new IllegalArgumentException(
          "You are not providing any arguments. You need to provide one or more sentences.");
    }

    final TextToSpeech textToSpeech = new TextToSpeech();
    // get the text to speech model

    textToSpeech.speak(args);
    // speak a sentence
    textToSpeech.terminate();
  }

  private final Synthesizer synthesizer;

  /**
   * Constructs the TextToSpeech object creating and allocating the speech synthesizer. English
   * voice: com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory
   */
  public TextToSpeech() {
    try {
      System.setProperty(
          "freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
      // sets up the basic properties of the system voice
      Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

      synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(java.util.Locale.ENGLISH));

      synthesizer.allocate();
      // synthesize the voice based on the settings
    } catch (final EngineException e) {
      throw new TextToSpeechException(e.getMessage());
    }
  }

  /**
   * Speaks the given list of sentences.
   *
   * @param sentences A sequence of strings to speak.
   */
  public void speak(final String... sentences) {
    boolean isFirst = true;

    for (final String sentence : sentences) {
      // speak the list of sentences from word to words
      if (isFirst) {
        isFirst = false;
      } else {
        // Add a pause between sentences.
        sleep();
      }

      speak(sentence);
    }
  }

  /**
   * Speaks the given sentence.
   *
   * @param sentence A string to speak.
   */
  public void speak(final String sentence) {
    if (sentence == null) {
      throw new IllegalArgumentException("Text cannot be null.");
      // throw exceptions when needed
    }

    try {
      synthesizer.resume();
      synthesizer.speakPlainText(sentence, null);
      // speak plain text
      synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
      // speak the list of sentences from word to words
    } catch (final AudioException | InterruptedException e) {
      throw new TextToSpeechException(e.getMessage());
    }
  }

  /** Sleeps a while to add some pause between sentences. */
  private void sleep() {
    // sleep method
    try {
      Thread.sleep(100);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * It deallocates the speech synthesizer. If you are experiencing an IllegalThreadStateException,
   * avoid using this method and run the speak method without terminating.
   */
  public void terminate() {
    try {
      synthesizer.deallocate();
      // deallocate the synthesizer
    } catch (final EngineException e) {
      throw new TextToSpeechException(e.getMessage());
    }
  }
}
