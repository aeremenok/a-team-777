/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.constants;

import org.gjt.jclasslib.structures.InvalidByteCodeException;

import java.io.*;

/**
    Describes a <tt>CONSTANT_InterfaceMethodref_info</tt> constant pool data structure.

    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.1 $ $Date: 2005/11/01 13:18:24 $
*/
public class ConstantInterfaceMethodrefInfo extends ConstantReference {

    public byte getTag() {
        return CONSTANT_INTERFACE_METHODREF;
    }

    public String getTagVerbose() {
        return CONSTANT_INTERFACE_METHODREF_VERBOSE;
    }

    public void read(DataInput in)
        throws InvalidByteCodeException, IOException {
        
        super.read(in);
        if (debug) debug("read ");
    }
    
    public void write(DataOutput out)
        throws InvalidByteCodeException, IOException {
        
        out.writeByte(CONSTANT_INTERFACE_METHODREF);
        super.write(out);
        if (debug) debug("wrote ");
    }
    
    protected void debug(String message) {
        super.debug(message + getTagVerbose() + " with class_index " + classIndex +
              " and name_and_type_index " + nameAndTypeIndex);
    }

}
