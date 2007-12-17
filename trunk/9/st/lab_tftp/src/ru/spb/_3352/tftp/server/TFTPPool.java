package ru.spb._3352.tftp.server;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Stack;

import ru.spb._3352.tftp.common.FRQ;
import ru.spb._3352.tftp.common.VirtualFileSystem;

/**
 * ��� ������������ ��������
 */
public class TFTPPool
{
    /**
     * �������� � �������� � ������������� ��������
     */
    class TFTPRHThread
        extends Thread
    {
        /**
         * ����� �������
         */
        private InetAddress        address;

        /**
         * ���� ���������� ������
         */
        private boolean            die     = false;

        /**
         * ���� �������
         */
        int                        port;

        /**
         * ������
         */
        private FRQ                requestPacket;

        /**
         * ���� ���������
         */
        private boolean            running = false;

        /**
         * ���������� ��������
         */
        private TFTPRequestHandler tftpRequestHandler;

        /**
         * �����������
         * 
         * @param id ������-�������������
         * @param rh ���������� ��������
         */
        public TFTPRHThread(
            String id,
            TFTPRequestHandler rh )
        {
            super( id );
            tftpRequestHandler = rh;
            requestPacket = null;
            address = null;
            port = 0;
        }

        /**
         * ������ �����
         */
        synchronized public void die()
        {
            die = true;

            interrupt();
        }

        /**
         * ���������� IP � ���� �������
         * 
         * @return IP � ���� �������
         */
        public String getClient()
        {
            if ( address == null )
            {
                return "No client helped yet";
            }
            return address.getHostAddress() + ":" + port;
        }

        /**
         * ���������� �������� ����� ���������
         * 
         * @return ���� ���������
         */
        public boolean getRunning()
        {
            return running;
        }

        /**
         * ���������� ���������� ��������
         * 
         * @return ���������� ��������
         */
        public TFTPRequestHandler getWorker()
        {
            return tftpRequestHandler;
        }

        /* (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        synchronized public void run()
        {
            try
            {
                boolean stayAround = true;
                while ( stayAround )
                {
                    if ( die )
                    {
                        return;
                    }

                    if ( requestPacket == null )
                    {
                        try
                        {
                            wait();
                        }
                        catch ( InterruptedException e )
                        {
                            continue;
                        }
                    }

                    running = true;
                    tftpRequestHandler.run( requestPacket, address, port );
                    running = false;

                    synchronized ( connections )
                    {
                        connections.remove( address + ":" + port );
                    }

                    requestPacket = null;
                    address = null;
                    port = 0;
                    stayAround = push( this );
                }
            }
            finally
            {
                tftpRequestHandler.stop();
            }
        }

        /**
         * ��������� �����
         * 
         * @param frq ������
         * @param clientAddress �����
         * @param clientPort ����
         */
        synchronized void wake(
            FRQ frq,
            InetAddress clientAddress,
            int clientPort )
        {
            requestPacket = frq;
            address = clientAddress;
            port = clientPort;
            notify();
        }
    }

    /**
     * ���������� � ���������
     */
    private Hashtable         connections;

    /**
     * ��������� �����������
     */
    private Stack             idleWorkers;

    /**
     * ��������� �������
     */
    private EventListener     listener = null;

    /**
     * ������ ����
     */
    private int               size;

    /**
     * ����������� �������� �������
     */
    private VirtualFileSystem vfs      = null;

    /**
     * �����������
     * 
     * @param size ������ ������� ������������
     * @param connections ���������� � ���������
     * @param vfs �������� �������
     * @param listener ���������
     */
    public TFTPPool(
        int size,
        Hashtable connections,
        VirtualFileSystem vfs,
        EventListener listener )
    {
        this.vfs = vfs;
        this.listener = listener;
        this.size = size;
        this.connections = connections;

        idleWorkers = new Stack();
        TFTPRHThread wt;

        // ������� �������, ��������� � ���Ĩ� � ����
        for ( int i = 0; i < size; i++ )
        {
            String birthDate = "name=TFTPRequestHandler,workerid=" + i + ",birthdate=" + (new Date()).getTime();
            try
            {
                wt = new TFTPRHThread( birthDate, new TFTPRequestHandler( vfs, listener ) );
                wt.start();
                idleWorkers.push( wt );
            }
            catch ( SocketException e )
            {
                System.out.println( "Could not create TFTPRequestHandler nr: " + i + " for TFTPPool" );
            }
        }
    }

    /**
     * ����� ���������� �� ���������� ������ ��� ��������� ������� ��
     * ������/������. ������ ���������� ���������� �����������.
     * 
     * @param frq ������
     * @param clientAddress ����� �������
     * @param clientPort ���� �������
     */
    synchronized public void performWork(
        FRQ frq,
        InetAddress clientAddress,
        int clientPort )
    {

        TFTPRHThread wt = null;

        // ������� �����. ��������� �� ������������ � ���������.
        TFTPRequestHandler existingConnection;
        synchronized ( connections )
        {
            existingConnection = (TFTPRequestHandler) connections.get( clientAddress + ":" + clientPort );
        }

        if ( existingConnection != null )
        {
            if ( !existingConnection.waitingForNewRequest( frq ) )
            {
                System.out.println( "Hey, I am already working for you. Be patient!" );
                return;
            }
        }

        synchronized ( idleWorkers )
        {
            if ( idleWorkers.empty() )
            {
                // �������� �.�. �� 2 �����������, ����� �������������
                // ������������ birthdate � ���������� ���� ���������� �������
                try
                {
                    Thread.sleep( 2 );
                }
                catch ( InterruptedException e1 )
                {
                }

                // ��� ����������� ������, ������� ������ � �������
                // ��������������
                String birthDate = "name=TFTPRequestHandler,workerid=x,birthdate=" + (new Date()).getTime();
                try
                {
                    System.out.println( "WARNING: Overload on tftpPool! ReHash!" );
                    wt = new TFTPRHThread( birthDate, new TFTPRequestHandler( vfs, listener ) );
                    wt.start();
                }
                catch ( SocketException e )
                {
                    System.out.println( "ERROR: Can not handle overload!" );
                }
            }
            else
            {
                // �������� ���������� �����������
                wt = (TFTPRHThread) idleWorkers.pop();
            }
        }

        synchronized ( connections )
        {
            wt.wake( frq, clientAddress, clientPort );
            connections.put( clientAddress + ":" + clientPort, wt.getWorker() );
        }
    }

    /**
     * ��������� � ���� �����������
     * 
     * @param wt ����������
     * @return �������?
     */
    private boolean push(
        TFTPRHThread wt )
    {
        boolean stayAround = false;

        synchronized ( idleWorkers )
        {
            if ( idleWorkers.size() < size )
            {
                stayAround = true;
                idleWorkers.push( wt );
            }
        }

        return stayAround;
    }

    /**
     * �������� ������ ���� ������������. ������ ��������� �����, � �����������
     * ����� ����������� �� ���� �������������.
     * 
     * @param newPoolSize ����� ������ ����
     */
    public void resize(
        int newPoolSize )
    {
        if ( newPoolSize < size )
        {
            for ( int x = 0; x < (size - newPoolSize); x++ )
            {
                if ( idleWorkers.isEmpty() )
                {
                    break;
                }

                TFTPRHThread wt = (TFTPRHThread) idleWorkers.pop();

                if ( wt == null )
                {
                    break;
                }

                wt.die();
            }
        }

        size = newPoolSize;
    };
}
