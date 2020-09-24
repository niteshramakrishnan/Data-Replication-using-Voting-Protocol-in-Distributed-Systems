import java.util.concurrent.Semaphore;

class Mutexthread extends Thread 
{

		Semaphore mutex = new Semaphore(1);
			   
        public void release_lock()
        {
            mutex.release();
        }
        
        public void acquire_lock()
        {
            try
            {
                mutex.acquire();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
}

class Test extends Thread
{
	int n;
	Semaphore sem = new Semaphore(3);

	Test(int n)
	{
		this.n = n;
	}

	public void run() 
	{
		
		
		System.out.println("Total available Semaphore permits : " + sem.availablePermits());

		try {

			System.out.println(n + " : acquiring lock...");
			System.out.println(n + " : available Semaphore permits now: " + sem.availablePermits());
			sem.acquire();
			System.out.println(n + " : got the permit!");

			try {

				
					System.out.println(n + " : is performing operation, available Semaphore permits : "	+ sem.availablePermits());

					// sleep 1 second
					Thread.sleep(1000);

				
			} finally {

				// calling release() after a successful acquire()
				System.out.println(n + " : releasing lock...");
				sem.release();
				System.out.println(n + " : available Semaphore permits now: " 
							+ sem.availablePermits());

			}

		} catch (InterruptedException e) {

			e.printStackTrace();

		}

	}
	public static void main(String args[])
	{
		Test t1 = new Test(1);
		t1.start();

		Test t2 = new Test(2);
		t2.start();

		
	}

}