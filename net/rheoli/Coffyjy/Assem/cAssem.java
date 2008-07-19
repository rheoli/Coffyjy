/*
    A S S E M: Assembler f"ur die Generierung von JavaByteCode.
     
    cAssem.java: Hauptklasse f"ur JavaByteCode-Assembler

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
import net.rheoli.Coffyjy.General.*;

public final class cAssem implement cRuntimeConstants
{  
  Vector       vMethod;
  cCONSTANTMgr oPoolMgr;

  public cAssem ()
  {
    vMethod  = new Vector ( 3 );
    oPoolMgr = new cCONSTANTMgr();
  }

  private void writeHeader ( DataOutputStream _oOStream )
  {
    oOStream.writeInt ( CLASS_HEADER );
    oOStream.writeShort ( 3 );
    oOStream.writeShort ( 45 );
  }

  private void writePool ( DataOutputStream _oOStream )
  {
    oPoolMgr.writePool ( _oOStream );
  }
  
  public void createClass ( String _sSourceFile )
  {
    int          iIdx   = _sSourceFile.lastIndexOf ( '.' );
    StringBuffer sbFile = new StringBuffer ( _sSourceFile.substring(0,iIdx) );

    int iClass   = oPoolMgr.newClass ( sbFile.toString() );
    int iSuper   = oPoolMgr.newClass ( "java/lang/Object" );
    int iMeth    = oPoolMgr.newMethodref ( "java/lang/Object", "<init>", "()V" );
    int iMeth2   = oPoolMgr.newMethodref ( "java/io/PrintStream", "println", "(Ljava/lang/String;)V" );
    int iField   = oPoolMgr.newFieldref ( "java/lang/System", "out", "Ljava/io/PrintStream;" );
    int iConstr  = oPoolMgr.newUtf8 ( "<init>" );
    int iDesc    = oPoolMgr.newUtf8 ( "()V" );
    int iConstr2 = oPoolMgr.newUtf8 ( "main" );
    int iHallo   = oPoolMgr.newString ( "Hallo Java." );
    int iDesc2   = oPoolMgr.newUtf8 ( "([Ljava/lang/String;)V" );
    int iCode    = oPoolMgr.newUtf8 ( NAME_CODE );
    int iSource  = oPoolMgr.newUtf8 ( NAME_SOURCEFILE );
    int iFile    = oPoolMgr.newUtf8 ( _sSourceFile );

    sbFile.append ( ".class" );
    DataOutputStream oOStream = new DataOutputStream ( new FileOutputStream(sbFile.toString()) );

    writeHeader ( oOStream );
    writePool ( oOStream );

    oOStream.writeShort ( cDefines.ACC_PUBLIC|cDefines.ACC_SYNCHRONIZED );    
    oOStream.writeShort ( iClass );
    oOStream.writeShort ( iSuper );
    oOStream.writeShort ( 0 );  // Interface-Count
    oOStream.writeShort ( 0 );  // Field-Count    
    // Methods
    oOStream.writeShort ( 2 );  // Method-Count

    // Main-Methode
    oOStream.writeShort ( cDefines.ACC_PUBLIC|cDefines.ACC_STATIC );
    oOStream.writeShort ( iConstr2 );
    oOStream.writeShort ( iDesc2 );
    oOStream.writeShort ( 1 );  // Attribute-Count
    oOStream.writeShort ( iCode );
    oOStream.writeInt ( 21 ); // Attr-Length
    oOStream.writeShort ( 2 ); // Max-Stack
    oOStream.writeShort ( 1 ); // Max-Locals
    oOStream.writeInt ( 9 );  // Code-Length
    oOStream.writeByte ( 178 );
    oOStream.writeShort ( iField );
    oOStream.writeByte ( 18 );
    oOStream.writeByte ( iHallo );
    oOStream.writeByte ( 182 );
    oOStream.writeShort ( iMeth2 );
    oOStream.writeByte ( 177 );
    oOStream.writeShort ( 0 );
    oOStream.writeShort ( 0 );
    // Construktor-Methode
    oOStream.writeShort ( cDefines.ACC_PUBLIC );
    oOStream.writeShort ( iConstr );
    oOStream.writeShort ( iDesc );
    oOStream.writeShort ( 1 );  // Attribute-Count
    oOStream.writeShort ( iCode );
    oOStream.writeInt ( 17 ); // Attr-Length
    oOStream.writeShort ( 1 ); // Max-Stack
    oOStream.writeShort ( 1 ); // Max-Locals
    oOStream.writeInt ( 5 );  // Code-Length
    oOStream.writeByte ( 42 );
    oOStream.writeByte ( 183 );
    oOStream.writeShort ( iMeth );
    oOStream.writeByte ( 177 );
    oOStream.writeShort ( 0 );
    oOStream.writeShort ( 0 );
    oOStream.writeShort ( 1 );
    oOStream.writeShort ( iSource );
    oOStream.writeInt ( 2 );
    oOStream.writeShort ( iFile );
  }
}
