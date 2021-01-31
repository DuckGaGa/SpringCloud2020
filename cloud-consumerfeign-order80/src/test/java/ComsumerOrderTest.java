import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ComsumerOrderTest {

    @Test
    public void fun1(){
        AtomicInteger i = new AtomicInteger(0);
        int current = i.get();
        i.compareAndSet(current,++current );
        System.out.println(i);

    }

}
