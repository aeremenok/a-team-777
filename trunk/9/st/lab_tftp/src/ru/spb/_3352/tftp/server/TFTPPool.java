package ru.spb._3352.tftp.server;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

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
        implements
            DynamicMBean
    {
        final static String        CLIENTS  = "Clients";
        final static String        IDENTITY = "Identity";
        final static String        RUNNING  = "Running";
        private InetAddress        address;
        private boolean            die      = false;
        int                        port;
        private FRQ                requestPacket;
        private boolean            running  = false;
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

            String on = "ROS:" + this.getName();

            try
            {
                ObjectName name = new ObjectName( on );

                if ( agent != null )
                {
                    agent.unregisterMBean( name );
                }
            }
            catch ( MalformedObjectNameException e )
            {
                System.out.println( on + " is not a valid ObjectName!\n" + e.getMessage() );
            }
            catch ( InstanceNotFoundException e )
            {
                System.out.println( "An instance of " + on + " could not be found in agent!\n" + e.getMessage() );
            }
            catch ( MBeanRegistrationException e )
            {
                System.out.println( on + " could not be unregistered in agent!\n" + e.getMessage() );
            }

            interrupt();
        }

        /* (non-Javadoc)
         * @see javax.management.DynamicMBean#getAttribute(java.lang.String)
         */
        public Object getAttribute(
            String attribute )
            throws AttributeNotFoundException
        {
            if ( attribute == null || attribute.equals( "" ) )
            {
                throw new IllegalArgumentException( "empty attribute name" );
            }

            if ( attribute.equals( RUNNING ) )
            {
                return new Boolean( running );
            }

            if ( attribute.equals( IDENTITY ) )
            {
                return this.getName();
            }

            if ( attribute.equals( CLIENTS ) )
            {
                return getClient();
            }

            throw new AttributeNotFoundException( "Attribute " + attribute + " not found" );
        }

        /* (non-Javadoc)
         * @see javax.management.DynamicMBean#getAttributes(java.lang.String[])
         */
        public AttributeList getAttributes(
            String[] attributes )
        {
            if ( attributes == null )
            {
                throw new IllegalArgumentException( "null array" );
            }

            AttributeList list = new AttributeList();

            for ( int i = 0; i < attributes.length; ++i )
            {
                try
                {
                    list.add( new Attribute( attributes[i], getAttribute( attributes[i] ) ) );
                }
                catch ( AttributeNotFoundException notfound )
                {
                }
            }

            return list;
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

        public MBeanInfo getMBeanInfo()
        {
            MBeanAttributeInfo atrRunning =
                                            new MBeanAttributeInfo(
                                                                    RUNNING,
                                                                    String.class.getName(),
                                                                    "True indicates the worker is working, false means it is waiting for a new job.",
                                                                    true, false, true );

            MBeanAttributeInfo identity =
                                          new MBeanAttributeInfo( IDENTITY, String.class.getName(),
                                                                  "String with worker id and timestamp of creation.",
                                                                  true, false, false );

            MBeanAttributeInfo clients =
                                         new MBeanAttributeInfo(
                                                                 CLIENTS,
                                                                 String.class.getName(),
                                                                 "IP address of client which the worker is talking to.",
                                                                 true, false, false );

            MBeanConstructorInfo constructor =
                                               new MBeanConstructorInfo(
                                                                         "main constructor",
                                                                         "this constructor takes a TFTPRequesthandlers and is used in the pool as worker",
                                                                         new MBeanParameterInfo[] {
                                                                                         new MBeanParameterInfo(
                                                                                                                 "id",
                                                                                                                 String.class
                                                                                                                             .getName(),
                                                                                                                 "This string identifies the worker" ),
                                                                                         new MBeanParameterInfo(
                                                                                                                 "rh",
                                                                                                                 TFTPRequestHandler.class
                                                                                                                                         .getName(),
                                                                                                                 "This is passed the actual class that handles the tftp xRQ" ) } );

            // Constrcutor attribute and operation lists
            MBeanConstructorInfo[] constructors = new MBeanConstructorInfo[] { constructor };
            // maybe it could be null in MBeanInfo
            MBeanOperationInfo[] operations = new MBeanOperationInfo[] {}; // maybe
            // it
            // could
            // be
            // null
            // in
            // MBeanInfo
            MBeanAttributeInfo[] attributes = new MBeanAttributeInfo[] { atrRunning, identity, clients };
            return new MBeanInfo( getClass().getName(), this.getName(), attributes, constructors, operations, null );
        }

        public boolean getRunning()
        {
            return running;
        }

        public TFTPRequestHandler getWorker()
        {
            return tftpRequestHandler;
        }

        public Object invoke(
            String actionName,
            Object[] params,
            String[] signature )
            throws MBeanException,
                ReflectionException
        {
            if ( actionName == null || actionName.equals( "" ) )
            {
                throw new IllegalArgumentException( "no operation" );
            }
            throw new UnsupportedOperationException( "unknown operation " + actionName );
        }

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
                            /**
                             * Wait a little longer for requestPacket to be
                             * assigned
                             */
                            continue;
                        }
                    }
                    running = true;
                    tftpRequestHandler.run( requestPacket, address, port );
                    running = false;
                    /* new InetSocketAddress(requestPacket.getAddress(), requestPacket.getPort()) can be replaced
                     * by requestPacket.getSocketAddress when jdk1.4 is used. */
                    /* connections.remove(new InetSocketAddress(requestPacket.getAddress(), requestPacket.getPort())); */
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
                // Whatever occured, stop the requestHandler, so no socket leaks
                tftpRequestHandler.stop();
            }
        }

        public void setAttribute(
            Attribute attribute )
            throws AttributeNotFoundException,
                InvalidAttributeValueException
        {
            if ( attribute == null )
            {
                throw new IllegalArgumentException( "null attribute" );
            }
            throw new AttributeNotFoundException( "Attribute " + attribute.getName() + " not found or not writable" );
        }

        public AttributeList setAttributes(
            AttributeList list )
        {
            if ( list == null )
            {
                throw new IllegalArgumentException( "null list" );
            }

            AttributeList results = new AttributeList();
            Iterator it = list.iterator();
            while ( it.hasNext() )
            {
                try
                {
                    Attribute attr = (Attribute) it.next();
                    setAttribute( attr );
                    results.add( attr );
                }
                catch ( JMException ignored )
                {
                }
            }
            return results;
        }

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

    private MBeanServer       agent    = null;
    private Hashtable         connections;
    private Stack             idleWorkers;
    private EventListener     listener = null;
    private int               size;

    private VirtualFileSystem vfs      = null;

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
        List srvrList = MBeanServerFactory.findMBeanServer( null );
        if ( srvrList.iterator().hasNext() )
        {
            agent = (MBeanServer) srvrList.iterator().next();
        }

        idleWorkers = new Stack();
        TFTPRHThread wt;
        /**
         * Create n=size workers, start the workers and put them waiting on
         * stack
         */
        for ( int i = 0; i < size; i++ )
        {
            String birthDate = "name=TFTPRequestHandler,workerid=" + i + ",birthdate=" + (new Date()).getTime();
            try
            {
                wt = new TFTPRHThread( birthDate, new TFTPRequestHandler( vfs, listener ) );
                wt.start();
                idleWorkers.push( wt );
                // register with management agent
                if ( agent != null )
                {
                    ObjectName name = new ObjectName( "ROS:" + wt.getName() );
                    /*               
                                   Descriptor d = new DescriptorSupport();
                                   d.setField(wt.RESOURCE_REFERENCE, wt);
                                   d.setField(wt.RESOURCE_TYPE, "file:///home/marco/tftpthread.xml");
                                   d.setField(wt.SAX_PARSER, "org.apache.crimson.parser.XMLReaderImpl");
                                   XMBean xmb = new XMBean(d, wt.DESCRIPTOR);
                    */
                    agent.registerMBean( wt, name );
                }
            }
            catch ( MalformedObjectNameException e )
            {
                System.out.println( birthDate + " is not a valid ObjectName!\n" + e.getMessage() );
            }
            catch ( InstanceAlreadyExistsException e )
            {
                System.out.println( "An instance of " + birthDate + " already exists in agent!\n" + e );
            }
            catch ( MBeanRegistrationException e )
            {
                System.out.println( birthDate + " could not be registered in agent!\n" + e.getMessage() );
            }
            catch ( NotCompliantMBeanException e )
            {
                System.out
                          .println( birthDate
                                    + " does not comply to the Java Management Extensions Intrumentation and Agent Specification, v1.1!"
                                    + e.getMessage() );
            }
            /*         catch (MBeanException e)
                     {
                        tftpLog.warn("could not register worker thread as XMBean with JMX agent");
                        e.printStackTrace();
                     } */
            catch ( SocketException e )
            {
                System.out.println( "Could not create TFTPRequestHandler nr: " + i + " for TFTPPool" );
            }
        }
    }

    /**
     * This method is called from the server socket on a received write request
     * or read request. This method on the TFTPPool will delegate to a non busy
     * worker thread which handles the client
     */
    synchronized public void performWork(
        FRQ frq,
        InetAddress clientAddress,
        int clientPort )
    {

        TFTPRHThread wt = null;

        /* Received packet! Check if already in queue otherwise add! 
         * this thus means, that I need to extract the packet and see 
         * where it comes from! */
        /* InetSocketAddress fromAddress = new InetSocketAddress(packet.getAddress(), packet.getPort()); */
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
                // sleep at least 2 msec to ensure birthdate to be unique
                // and limit the growth of threads with a max of 1 per 2msec
                try
                {
                    Thread.sleep( 2 );
                }
                catch ( InterruptedException e1 )
                {
                }

                /* Oh, oh, all workers busy
                		* generate new one to handle this request and alert! */
                String birthDate = "name=TFTPRequestHandler,workerid=x,birthdate=" + (new Date()).getTime();
                try
                {
                    System.out.println( "WARNING: Overload on tftpPool! ReHash!" );
                    wt = new TFTPRHThread( birthDate, new TFTPRequestHandler( vfs, listener ) );
                    wt.start();
                    // Register new thread with management Agent
                    if ( agent != null )
                    {
                        ObjectName name = new ObjectName( "ROS:" + wt.getName() );
                        agent.registerMBean( wt, name );
                    }
                }
                catch ( MalformedObjectNameException e )
                {
                    System.out.println( birthDate + " is not a valid ObjectName!\n" + e.getMessage() );
                }
                catch ( InstanceAlreadyExistsException e )
                {
                    System.out.println( "An instance of " + birthDate + " already exists in agent!\n" + e.getMessage() );
                }
                catch ( MBeanRegistrationException e )
                {
                    System.out.println( birthDate + " could not be registered in agent!\n" + e.getMessage() );
                }
                catch ( NotCompliantMBeanException e )
                {
                    System.out
                              .println( birthDate
                                        + " does not comply to the Java Management Extensions Intrumentation and Agent Specification, v1.1!\n"
                                        + e.getMessage() );
                }
                catch ( SocketException e )
                {
                    System.out.println( "ERROR: Can not handle overload!" );
                }
            }
            else
            {
                /* Get an idle TFTPRequestHandler to handle packet */
                wt = (TFTPRHThread) idleWorkers.pop();
            }
        }

        /*
        * make sure we put in connections before woken thread can remove
        */
        synchronized ( connections )
        {
            wt.wake( frq, clientAddress, clientPort );
            /* Check if a new worker is needed, or that one caused a timeout! 
             * have a hashtable with references to TFTPRequestHandler and IP's and SID's */
            connections.put( clientAddress + ":" + clientPort, wt.getWorker() );
        }
    }

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
            else
            {
                // deregister thread from JMX Agent
            }
        }
        return stayAround;
    }

    /* Size will be changed but the actual amount of workers
     * will only increase over time when needed */
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
                // remaining not idle workers do not return on stack when
                // finished
                wt.die();
            }
        }
        size = newPoolSize;
    };
}
