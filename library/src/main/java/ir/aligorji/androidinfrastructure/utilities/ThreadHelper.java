package ir.aligorji.androidinfrastructure.utilities;


public class ThreadHelper
{
    public static void throwExceptionIfInterrupted() throws InterruptedException
    {
        if (Thread.interrupted())
        {
            throw new InterruptedException();
        }
    }
}
