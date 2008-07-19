/*
    A S S E M: Assembler f"ur die Generierung von JavaByteCode.
     
    cMethod.java: Verwaltung der Opcodes einer Methode.

    Copyright (c) 1997-2008 Stephan Toggweiler

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.

 */

package net.rheoli.Coffyjy.Assem;

import java.io.*;
import java.util.*;
import net.rheoli.Coffyjy.ConstantPool.*;

public final class cMethod
{
  Vector       vCommand;
  int          iNrOfBytes;
  cCONSTANTMgr oPoolMgr;

  public cMethod () throws Exception
  {
    throw new cAssemException ( "cMethod - not the correct constructor." );
  }

  public cMethod ( cCONSTANTMgr _oPool )
  {
    oPoolMgr     = _oPool;
    vCommand     = Vector ( 20 );
    iNrOfBytes   = 0;
  }

  private void addOpcode ( int _iOpcode ) throws Exception
  {
    if ( opcLength[_iOpcode] != 1 )
    {
      throw new cAssemException ( "cMethod - Error in Opcode" );
    }
    cCommand oCommand = new cCommand();
    oCommand.iOpcode  = _iOpcode;
    oCommand.iParams  = 0;
    vCommand.addElement ( oCommand );
    iNrOfBytes++;
  }

  private void addOpcode ( int _iOpcode, String _str1, String _str2, String _str3 ) throws Exception
  {
    int iNr = 0;
    switch ( _iOpcode )
    {
      // CONSTANT_Fieldref
      case opc_getfield:
      case opc_getstatic:
      case opc_putfield:
      case opc_putstatic:
      {
        iNr = oPoolMgr.newFieldref ( _str1, _str2, _str3 );
        break;
      }
      // CONSTANT_Methodref
      case opc_invokespecial:
      case opc_invokestatic:
      case opc_invokevirtual:
      {
        iNr = oPoolMgr.newMethodref ( _str1, _str2, _str3 );
        break;
      }
      // CONSTANT_InterfaceMethodref
      case opc_invokeinterface:
      {
        iNr = oPoolMgr.newInterfaceMethodref ( _str1, _str2, _str3 );
        break;
      }
      default:
        throw new cAssemException ( "cMethod - Error in Opcode" );        
    }
    cCommand oCommand   = new cCommand();
    oCommand.iOpcode    = _iOpcode;
    oCommand.iParams    = 2;
    oCommand.iaParam    = new int[2];
    oCommand.iaParam[0] = (iNr/256)&0xff;
    oCommand.iaParam[1] = iNr&0xff;
    vCommand.addElement ( oCommand );
    iNrOfBytes += 3;
  }
}
