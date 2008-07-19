/*
    D I S A S S : Disassembler f"ur JavaByteCodeClassFiles
     
    cCONSTANTMgr.java: Manager f"ur Daten des ConstantPool.

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

package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;
import net.rheoli.Coffyjy.General.*;

public class cCONSTANTMgr implements cRuntimeConstants
{
  private cCONSTANTPool[] oPool;
  private int             iMaxConstants;
  private int             iNextConstant;

  public cCONSTANTMgr ()
  {
    iMaxConstants = 100;
    iNextConstant = 1;
    oPool = new cCONSTANTPool[iMaxConstants];
  }

  public cCONSTANTMgr ( int _iMaxConstants )
  {
    iMaxConstants = _iMaxConstants;
    iNextConstant = 1;
    oPool = new cCONSTANTPool[iMaxConstants];
  }

  public int getTagOf ( int _iIndex )
  {
    return ( oPool[_iIndex].getTag() );
  }

  public String getUtf8 ( int _iIndex ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_Utf8 )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (Utf8;" + _iIndex + ")." );
    }
    return ( oPool[_iIndex].getString() );
  }

  public float getFloat ( int _iIndex ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_Float )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (Float;" + _iIndex + ")." );
    }
    return ( oPool[_iIndex].getFloat() );
  }

  public long getLong ( int _iIndex ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_Long )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (Long;" + _iIndex + ")." );
    }
    return ( oPool[_iIndex].getLong() );
  }

  public double getDouble ( int _iIndex ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_Double )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (Double;" + _iIndex + ")." );
    }
    return ( oPool[_iIndex].getDouble() );
  }

  public String getString ( int _iIndex ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_String )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (String;" + _iIndex + ")." );
    }
    return ( getUtf8(oPool[_iIndex].getIndex()) );
  }

  public String getClass ( int _iIndex ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_Class )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (Class;" + _iIndex + ")." );
    }
    return ( getUtf8(oPool[_iIndex].getIndex()) );
  }

  public void getNameAndType ( int          _iIndex,
                               StringBuffer _strName,
                               StringBuffer _strDescriptor ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_NameAndType )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (NameAndType;" + _iIndex + ")." );
    }
    try
    {
      _strName.append ( getUtf8(oPool[_iIndex].getIndex()) );
      _strDescriptor.append ( getUtf8(oPool[_iIndex].getDescriptorIndex()) );
    }
    catch ( Exception e )
    {
      System.err.println ( e );
    }
  }

  private void getRef ( int         _iIndex,
                       StringBuffer _strClass,
                       StringBuffer _strName,
                       StringBuffer _strDescriptor ) throws Exception
  {
    int iNATIndex;
    try
    {
      _strClass.append ( getClass(oPool[_iIndex].getIndex()) );
      getNameAndType ( oPool[_iIndex].getNameAndTypeIndex(), _strName, _strDescriptor );
    } 
    catch ( Exception e )
    {
      System.err.println ( e );
    }
  }
  
  public void getFieldref ( int          _iIndex,
                            StringBuffer _strClass,
                            StringBuffer _strName,
                            StringBuffer _strDescriptor ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_Fieldref )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (Fieldref;" + _iIndex + ")." );
    }
    getRef ( _iIndex, _strClass, _strName, _strDescriptor );
  }
  
  public void getMethodref ( int         _iIndex,
                            StringBuffer _strClass,
                            StringBuffer _strName,
                            StringBuffer _strDescriptor ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_Methodref )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (Methodref;" + _iIndex + ")." );
    }
    getRef ( _iIndex, _strClass, _strName, _strDescriptor );
  }

  public void getInterfaceMethodref ( int          _iIndex,
                                      StringBuffer _strClass,
                                      StringBuffer _strName,
                                      StringBuffer _strDescriptor ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_InterfaceMethodref )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (InterfaceMethodref;" + _iIndex + ")." );
    }
    getRef ( _iIndex, _strClass, _strName, _strDescriptor );
  }

  public int getInteger ( int _iIndex ) throws Exception
  {
    if ( oPool[_iIndex].getTag() != CONSTANT_Integer )
    {
      throw new CONSTANTException ( "cCONSTANTMgr - Not a correct tag (Integer)." );
    }
    return ( oPool[_iIndex].getBytes() );
  }

  public void addUtf8 ( String _strString ) throws Exception
  {
    cCONSTANT_Utf8_Info oNew = new cCONSTANT_Utf8_Info();
    oNew.setUtf8Info ( _strString );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
  }
  
  public void addFloat ( float _fBytes ) throws Exception
  {
    cCONSTANT_Float_Info oNew = new cCONSTANT_Float_Info();
    oNew.setFloatInfo ( _fBytes );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
  }

  public void addLong ( long _lBytes ) throws Exception
  {
    cCONSTANT_Long_Info oNew = new cCONSTANT_Long_Info();
    oNew.setLongInfo ( _lBytes );
    oPool[iNextConstant] = oNew;
    iNextConstant += 2;
  }

  public void addDouble ( double _dBytes ) throws Exception
  {
    cCONSTANT_Double_Info oNew = new cCONSTANT_Double_Info();
    oNew.setDoubleInfo ( _dBytes );
    oPool[iNextConstant] = oNew;
    iNextConstant += 2;
  }
    
  public void addString ( int _iIndex ) throws Exception
  {
    cCONSTANT_String_Info oNew = new cCONSTANT_String_Info();
    oNew.setStringInfo ( _iIndex );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
  }
  
  public void addClass ( int _iIndex ) throws Exception
  {
    cCONSTANT_Class_Info oNew = new cCONSTANT_Class_Info();
    oNew.setClassInfo ( _iIndex );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
  }
  
  public void addNameAndType ( int _iIndex, int _iDIndex ) throws Exception
  {
    cCONSTANT_NameAndType_Info oNew = new cCONSTANT_NameAndType_Info();
    oNew.setNameAndTypeInfo ( _iIndex, _iDIndex );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
  }
  
  public void addMethodref ( int _iIndex, int _iNATIndex ) throws Exception
  {
    cCONSTANT_Ref_Info oNew = new cCONSTANT_Ref_Info();
    oNew.setRefInfo ( CONSTANT_Methodref, _iIndex, _iNATIndex );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
  }
  
  public void addInterfaceMethodref ( int _iIndex, int _iNATIndex ) throws Exception
  {
    cCONSTANT_Ref_Info oNew = new cCONSTANT_Ref_Info();
    oNew.setRefInfo ( CONSTANT_InterfaceMethodref, _iIndex, _iNATIndex );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
  }
  
  public void addFieldref ( int _iIndex, int _iNATIndex ) throws Exception
  {
    cCONSTANT_Ref_Info oNew = new cCONSTANT_Ref_Info();
    oNew.setRefInfo ( CONSTANT_Fieldref, _iIndex, _iNATIndex );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
  }

  public void addInteger ( int _iBytes ) throws Exception
  {
    cCONSTANT_Integer_Info oNew = new cCONSTANT_Integer_Info();
    oNew.setIntegerInfo ( _iBytes );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
  }  
  
  public int newUtf8 ( String _strString ) throws Exception
  {
    cCONSTANT_Utf8_Info oNew   = null;
    int                 iIndex = 0;
    // Auf Vorhandensein des Strings pruefen
    for ( int i = 1; i < iNextConstant; i++ )
    {
      if ( oPool[i].getTag() == CONSTANT_Utf8 )
      {
        if ( oPool[i].getString().compareTo(_strString) == 0 )
        {
          iIndex = i;
          break;
        }
      }
    }
    if ( iIndex != 0 )
    {
      return ( iIndex );
    }
    oNew = new cCONSTANT_Utf8_Info();
    oNew.setUtf8Info ( _strString );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
    return ( iNextConstant-1 );
  }

  public int newFloat ( float _fBytes ) throws Exception
  {
    cCONSTANT_Float_Info oNew   = null;
    int                  iIndex = 0;
    // Auf Vorhandensein des Strings pruefen
    for ( int i = 1; i < iNextConstant; i++ )
    {
      if ( oPool[i].getTag() == CONSTANT_Float )
      {
        if ( oPool[i].getFloat() == _fBytes )
        {
          iIndex = i;
          break;
        }
      }
    }
    if ( iIndex != 0 )
    {
      return ( iIndex );
    }
    oNew = new cCONSTANT_Float_Info();
    oNew.setFloatInfo ( _fBytes );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
    return ( iNextConstant-1 );
  }

  public int newLong ( long _lBytes ) throws Exception
  {
    cCONSTANT_Long_Info oNew   = null;
    int                 iIndex = 0;
    // Auf Vorhandensein des Strings pruefen
    for ( int i = 1; i < iNextConstant; i++ )
    {
      if ( oPool[i].getTag() == CONSTANT_Long )
      {
        if ( oPool[i].getLong() == _lBytes )
        {
          iIndex = i;
          break;
        }
      }
    }
    if ( iIndex != 0 )
    {
      return ( iIndex );
    }
    oNew = new cCONSTANT_Long_Info();
    oNew.setLongInfo ( _lBytes );
    oPool[iNextConstant] = oNew;
    iNextConstant += 2;
    return ( iNextConstant-1 );
  }

  public int newDouble ( double _dBytes ) throws Exception
  {
    cCONSTANT_Double_Info oNew   = null;
    int                   iIndex = 0;
    // Auf Vorhandensein des Strings pruefen
    for ( int i = 1; i < iNextConstant; i++ )
    {
      if ( oPool[i].getTag() == CONSTANT_Double )
      {
        if ( oPool[i].getDouble() == _dBytes )
        {
          iIndex = i;
          break;
        }
      }
    }
    if ( iIndex != 0 )
    {
      return ( iIndex );
    }
    oNew = new cCONSTANT_Double_Info();
    oNew.setDoubleInfo ( _dBytes );
    oPool[iNextConstant] = oNew;
    iNextConstant += 2;
    return ( iNextConstant-1 );
  }
  
  public int newString ( String _strString ) throws Exception
  {
    cCONSTANT_String_Info oNew    = null;
    int                   iIndex  = newUtf8 ( _strString );
    int                   iString = 0;
    for ( int i = 1; i < iNextConstant; i++ )
    {
      if ( oPool[i].getTag() == CONSTANT_String )
      {
        if ( oPool[i].getIndex() == iIndex )
        {
          iString = i;
          break;
        }
      }
    }
    if ( iString != 0 )
    {
      return ( iString );
    }
    oNew = new cCONSTANT_String_Info();
    oNew.setStringInfo ( iIndex );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
    return ( iNextConstant-1 );
  }

  public int newClass ( String _strString ) throws Exception
  {
    cCONSTANT_Class_Info oNew    = null;
    int                  iIndex  = newUtf8 ( _strString );
    int                  iString = 0;
    for ( int i = 1; i < iNextConstant; i++ )
    {
      if ( oPool[i].getTag() == CONSTANT_Class )
      {
        if ( oPool[i].getIndex() == iIndex )
        {
          iString = i;
          break;
        }
      }
    }
    if ( iString != 0 )
    {
      return ( iString );
    }
    oNew = new cCONSTANT_Class_Info();
    oNew.setClassInfo ( iIndex );
    oPool[iNextConstant] = oNew;
    System.out.println ( "Class_New: " + iNextConstant + " / " + iIndex );
    iNextConstant++;
    return ( iNextConstant-1 );
  }

  public int newNameAndType ( String _strName,
                              String _strDescriptor ) throws Exception
  {
    cCONSTANT_NameAndType_Info oNew    = null;
    int                        iIndex1 = newUtf8 ( _strName );
    int                        iIndex2 = newUtf8 ( _strDescriptor );
    int                        iNAT    = 0;
    for ( int i = 1; i < iNextConstant; i++ )
    {
      if ( oPool[i].getTag() == CONSTANT_NameAndType )
      {
        if ( (oPool[i].getIndex()==iIndex1) && (oPool[i].getDescriptorIndex()==iIndex2) )
        {
          iNAT = i;
          break;
        }
      }
    }
    if ( iNAT != 0 )
    {
      return ( iNAT );
    }
    oNew = new cCONSTANT_NameAndType_Info();
    oNew.setNameAndTypeInfo ( iIndex1, iIndex2 );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
    return ( iNextConstant-1 );
  }

  private int newRef ( int    _iTag,
                       String _strClass,
                       String _strName,
                       String _strDescriptor ) throws Exception
  {
    cCONSTANT_Ref_Info oNew    = null;
    int                iIndex1 = newClass ( _strClass );
    int                iIndex2 = newNameAndType ( _strName, _strDescriptor );
    int                iRef    = 0;
    for ( int i = 1; i < iNextConstant; i++ )
    {
      if ( oPool[i].getTag() == _iTag )
      {
        if ( (oPool[i].getIndex()==iIndex1) && (oPool[i].getNameAndTypeIndex()==iIndex2) )
        {
          iRef = i;
          break;
        }
      }
    }
    if ( iRef != 0 )
    {
      return ( iRef );
    }
    oNew = new cCONSTANT_Ref_Info();
    oNew.setRefInfo ( _iTag, iIndex1, iIndex2 );
    oPool[iNextConstant] = oNew;
    iNextConstant++;
    return ( iNextConstant-1 );
  }

  public int newFieldref ( String _strClass,
                           String _strName,
                           String _strDescriptor ) throws Exception
  {
    return ( newRef(CONSTANT_Fieldref, _strClass, _strName, _strDescriptor) );
  }
  
  public int newMethodref ( String _strClass,
                           String _strName,
                           String _strDescriptor ) throws Exception
  {
    return ( newRef(CONSTANT_Methodref, _strClass, _strName, _strDescriptor) );
  }

  public int newInterfaceMethodref ( String _strClass,
                           String _strName,
                           String _strDescriptor ) throws Exception
  {
    return ( newRef(CONSTANT_InterfaceMethodref, _strClass, _strName, _strDescriptor) );
  }
  
  public void writePool ( DataOutputStream _oDO ) throws Exception
  {
    _oDO.writeShort ( iNextConstant );
    for ( int i = 1; i < iNextConstant; i++ )
    {
      switch ( oPool[i].getTag() )
      {
        case CONSTANT_Class:
        case CONSTANT_String:
        {
          _oDO.writeByte ( oPool[i].getTag() );
          _oDO.writeShort ( oPool[i].getIndex() );
          break;
        }
        case CONSTANT_Fieldref:
        case CONSTANT_Methodref:
        case CONSTANT_InterfaceMethodref:
        {
          _oDO.writeByte ( oPool[i].getTag() );
          _oDO.writeShort ( oPool[i].getIndex() );
          _oDO.writeShort ( oPool[i].getNameAndTypeIndex() );
          break;
        }
        case CONSTANT_Integer:
        {
          _oDO.writeByte ( oPool[i].getTag() );
          _oDO.writeInt ( oPool[i].getBytes() );
          break;
        }
        case CONSTANT_Float:
        {
          _oDO.writeByte ( oPool[i].getTag() );
          _oDO.writeFloat ( oPool[i].getFloat() );
          break;
        }
        case CONSTANT_Long:
        {
          _oDO.writeByte ( oPool[i].getTag() );
          _oDO.writeLong ( oPool[i].getLong() );
          break;
        }
        case CONSTANT_Double:
        {
          _oDO.writeByte ( oPool[i].getTag() );
          _oDO.writeDouble ( oPool[i].getDouble() );
          break;
        }
        case CONSTANT_NameAndType:
        {
          _oDO.writeByte ( oPool[i].getTag() );
          _oDO.writeShort ( oPool[i].getIndex() );
          _oDO.writeShort ( oPool[i].getDescriptorIndex() );
          break;
        }
        case CONSTANT_Utf8:
        {
          _oDO.writeByte ( oPool[i].getTag() );
          _oDO.writeUTF ( oPool[i].getString() );
          break;
        }
        default:
          System.err.println ( "Hier gibt es ein Problem." );
      }
    }
  }
}
