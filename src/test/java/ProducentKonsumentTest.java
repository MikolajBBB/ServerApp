import com.example.server.Konsument;
import com.example.server.Odpowiedz;
import com.example.server.Producent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducentKonsumentTest {

    @Test
    void test() throws InterruptedException {
        BlockingQueue<Odpowiedz> kolejka = new ArrayBlockingQueue<>(50);
        Producent producent = new Producent(kolejka, null);
        Konsument konsument = new Konsument(kolejka,null);
        new Thread(producent).start();
        new Thread(producent).start();
        new Thread(producent).start();
        new Thread(konsument).start();
        Thread.sleep(1000);
        Assertions.assertTrue(!kolejka.isEmpty());
    }

}
