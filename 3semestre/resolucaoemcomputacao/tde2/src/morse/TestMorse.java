package morse;

/**
 * Testes simples automatizados para validar a decodificação Morse
 */
public class TestMorse {
    public static void main(String[] args) {
        MorseTreeConsole tree = new MorseTreeConsole();
        tree.buildTree();

        int failed = 0;

        failed += assertEquals("HELLO", tree.decode(".... . .-.. .-.. ---"), "HELLO test");
        failed += assertEquals("SOS", tree.decode("... --- ..."), "SOS test");
        failed += assertEquals("A", tree.decode(".-"), "A test");
        failed += assertEquals("Z", tree.decode("--.."), "Z test");
        failed += assertEquals("01234", tree.decode("----- .---- ..--- ...-- ....-"), "Numbers test");
        failed += assertEquals("?", tree.decode("..-.-"), "Unknown sequence test (should be ?)");

        if (failed == 0) {
            System.out.println("All tests passed.");
            System.exit(0);
        } else {
            System.err.println(failed + " tests failed.");
            System.exit(1);
        }
    }

    private static int assertEquals(String expected, String actual, String name) {
        if (expected.equals(actual)) {
            System.out.println("PASS: " + name + " -> " + actual);
            return 0;
        } else {
            System.err.println("FAIL: " + name + " expected='" + expected + "' actual='" + actual + "'");
            return 1;
        }
    }
}
