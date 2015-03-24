package no.iterate.util

/**
 * Provides flakey methods which fail randomly
 */
class FlakeyHelper {

    private final Random random

    FlakeyHelper(long seed) {
        random = new Random(seed)
    }

    void throwSlowException() {
        int nextInt = random.nextInt(20)
        if (nextInt == 10) {
            sleep(20000,{throw new IllegalStateException("Argh I was sleeping")})
            throw new RuntimeException("A weird network error occured")
        }
    }
}
