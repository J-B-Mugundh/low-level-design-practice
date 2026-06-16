package org.example.com;

/*
    Singleton Pattern - Used across places like DB conn creation/accessing, Logging, etc - when used with threads
 */
public class SingletonPattern {

    public static void main(String[] args) {
        // i) EagerSingletonPattern - thread-safe, but inefficient
        EagerSingletonPattern eagerSingletonPattern = EagerSingletonPattern.getInstance();

        // ii) LazySingletonPattern - efficient, but not thread-safe
        LazySingletonPattern lazySingletonPattern = LazySingletonPattern.getInstance();

        // iii) SynchronizedSingletonPattern - thread-safe as we use synchronized keyword, but performance overhead can happen in high-concurrency scenarios
        SynchronizedSingletonPattern synchronizedSingletonPattern = SynchronizedSingletonPattern.getInstance();

        // iv) DoubleCheckedSingleton - thread-safe, less overhead compared to the way we synchronized getInstance() method with the help of volatile keyword which ensures changes made by one thread are visible to others.
        DoubleCheckedSingleton doubleCheckedSingleton = DoubleCheckedSingleton.getInstance();

        // iv) Bill Pugh Singleton - uses a static inner helper class to hold the Singleton instance. The instance is created only when the inner class is loaded
        BillPughSingletonPattern billPughSingletonPattern = BillPughSingletonPattern.getInstance();
    }
}

class LazySingletonPattern {
    private static LazySingletonPattern singletonInstance;

    private LazySingletonPattern(){

    }

    public static LazySingletonPattern getInstance(){
        if(singletonInstance == null){
            singletonInstance = new LazySingletonPattern();
        }
        return singletonInstance;
    }
}

class EagerSingletonPattern {
    private static final EagerSingletonPattern singletonInstance = new EagerSingletonPattern();

    private EagerSingletonPattern(){

    }

    public static EagerSingletonPattern getInstance(){
        return singletonInstance;
    }
}

class SynchronizedSingletonPattern {
    private static SynchronizedSingletonPattern singletonInstance;

    private SynchronizedSingletonPattern(){

    }

    public static synchronized SynchronizedSingletonPattern getInstance(){
        if(singletonInstance == null){
            singletonInstance = new SynchronizedSingletonPattern();
        }
        return singletonInstance;
    }
}

class BillPughSingletonPattern{

    private BillPughSingletonPattern(){

    }

    private static class Helper{
        private static BillPughSingletonPattern singletonInstance = new BillPughSingletonPattern();
    }

    public static BillPughSingletonPattern getInstance(){
        return Helper.singletonInstance;
    }
}

class DoubleCheckedSingleton {

    private static volatile DoubleCheckedSingleton singletonInstance;

    private DoubleCheckedSingleton(){}

    public static DoubleCheckedSingleton getInstance(){

        if(singletonInstance == null){
            synchronized(DoubleCheckedSingleton.class){
                if(singletonInstance == null){
                    singletonInstance =
                            new DoubleCheckedSingleton();
                }
            }
        }
        return singletonInstance;
    }
}
