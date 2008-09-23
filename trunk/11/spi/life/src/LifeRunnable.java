public class LifeRunnable
    implements
        Runnable
{
    private boolean[][] fields;

    LifeRunnable(
        boolean[][] fields )
    {
        this.fields = fields;
    }

    public void run()
    {
        synchronized ( fields )
        {
            while ( true )
            {
                for ( int i = 0; i < fields.length; i++ )
                {
                    for ( int j = 0; j < fields[i].length; j++ )
                    {
                        fields[i][j] = !fields[i][j];
                    }
                }

                fields.notifyAll();

                try
                {
                    fields.wait();
                }
                catch ( InterruptedException e1 )
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
