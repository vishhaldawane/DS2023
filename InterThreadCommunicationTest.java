public class InterThreadCommunicationTest {
    public static void main(String[] args) {
        FoodItem food = new FoodItem("Pizza");
        Producer prod = new Producer(food);
        Consumer cons = new Consumer(food);

    
        prod.start();
        cons.start();
        
    }
}

class Producer extends Thread {
    FoodItem ref;

    public Producer(FoodItem ref) {
        this.ref = ref;
    }

    void produce() {
        ref.served();//FoodItems's serve method
    }
    public void run() { //invoking produce
        produce();
    }
}

class Consumer extends Thread {
    FoodItem ref;

    public Consumer(FoodItem ref) {
        this.ref = ref;
    }

     void consume() {
        ref.eat();
    }
    public void run() {
        consume();
    }
}

class FoodItem
{
    String foodItemName;

    boolean isProduced;//default value is false

    public FoodItem(String foodItemName) {
        this.foodItemName = foodItemName;
    }   
    
    synchronized void eat() { // consumer thread is going to call this
        if(isProduced==false) {
            System.out.println("Waiting for "+foodItemName+" to be produced....");
            try {
            	 try {
     				Thread.sleep(40);
     			} catch (InterruptedException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
            	 
                wait(); // super class Object's method
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consuming...."+foodItemName);
    }
    
  
    synchronized void served() { //producer thread is going to call this
        if(isProduced==false) {
            System.out.println("Producing..." + foodItemName);
            isProduced = true;
            
            try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            System.out.println("Notifying the waiting thread....");
            notify(); //notifies the waiting thread...
        }
    }
}
