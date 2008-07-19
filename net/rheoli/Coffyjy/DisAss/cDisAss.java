/*
    D I S A S S : Disassembler f"ur JavaByteCodeClassFiles
     
    cDisAss.java: Hauptklasse mit den Disassemlierfunktionen.

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
 
package net.rheoli.Coffyjy.DisAss;

import java.io.*;
import net.rheoli.Coffyjy.General.*;
import net.rheoli.Coffyjy.ConstantPool.*;

public final class cDisAss implements cRuntimeConstants
{  
  private DataInputStream  oIStream;
// private PrintWriter      oOutput;  // jdk1.1
  private PrintStream      oOutput;  // jdk1.0.2
  private cCONSTANTMgr     oPoolMgr;
  private cLabelMgr        oLabelMgr;
  
  private boolean          fConstantPool = true;

  public cDisAss ()
  {
    oIStream  = null;
    oOutput   = null;
    oPoolMgr  = null;
    oLabelMgr = new cLabelMgr();
  }

  private void magicTest () throws Exception
  {
    int iMagic = oIStream.readInt();
    if ( iMagic != JAVA_MAGIC )
    {    
      throw new DisAssException ( "cDisAss - Magic-Word 0xCAFEBABE not found, (" + Integer.toHexString(iMagic) + " found)." );
    }
  }
  
  private void printVersion () throws Exception
  {
    int iMajor = oIStream.readUnsignedShort();
    int iMinor = oIStream.readUnsignedShort();
    
    oOutput.println ( "; Class Version: " + iMajor + "." + iMinor + " " );    
  }

  private void constantPool () throws Exception
  {
    int iCount = oIStream.readUnsignedShort();
    if ( fConstantPool )
    {
      oOutput.println();
      oOutput.println ( "; ConstantPool:" );
      oOutput.println ( "; ConstantPoolCount: " + iCount );
    }
    oPoolMgr = new cCONSTANTMgr ( iCount+1 );
    for ( int i = 1; i < iCount; i++ )
    {
      int iTag = oIStream.readUnsignedByte();
      if ( fConstantPool )
      {
        oOutput.print ( "; " + i + ": " );
      }
      switch ( iTag )
      {
        case CONSTANT_Utf8:
        {
          int          iLength   = oIStream.readUnsignedShort();
          StringBuffer strbBytes = new StringBuffer();
          for ( int j = 0; j < iLength; j++ )
          {
            int iRead = oIStream.readUnsignedByte();
            if ( (iRead&0x80) == 0 )
            {  // \u0001-\u007F
              strbBytes.append ( (char)iRead );
            }
            else
            {
              if ( (iRead&0xE0) == 0xC0 )
              {  // \u0080-\u07FFF
                iRead = oIStream.readUnsignedByte();
                System.err.println ( "cClass - Achtung! Unicode." );
              }
              else
              { // \u0800-\uFFFF
                iRead = oIStream.readUnsignedByte();
                iRead = oIStream.readUnsignedByte();
                System.err.println ( "cClass - Achtung! Unicode." );
              }
            }
          } 
          oPoolMgr.addUtf8 ( strbBytes.toString() );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_Utf8: text \"" + strbBytes.toString() + "\"" );
          }
          break;
        }
        /* case CONSTANT_Unicode: */
        case CONSTANT_Integer:
        {
          int iBytes = oIStream.readInt();
          oPoolMgr.addInteger ( iBytes );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_Integer: bytes " + iBytes );
          }
          break;
        }
        case CONSTANT_Float:
        {
          float fBytes = oIStream.readFloat();
          oPoolMgr.addFloat ( fBytes );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_Float: bytes " + fBytes );
          }
          break;
        }
        case CONSTANT_Long:
        {
          long lBytes = oIStream.readLong();
          oPoolMgr.addLong ( lBytes );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_Long: bytes " + lBytes );
          }
          i++;
          break;
        }
        case CONSTANT_Double:
        {
          double dBytes = oIStream.readDouble();
          oPoolMgr.addDouble ( dBytes );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_Double: bytes " + dBytes );
          }
          i++;
          break;
        }
        case CONSTANT_Class:
        {
          int iNameIndex = oIStream.readUnsignedShort();
          oPoolMgr.addClass ( iNameIndex );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_Class: name_index " + iNameIndex );
          }
          break;
        }
        case CONSTANT_String:
        {
          int iStringIndex = oIStream.readUnsignedShort();
          oPoolMgr.addString ( iStringIndex );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_String: string_index " + iStringIndex );
          }
          break;
        }
        case CONSTANT_Fieldref:
        {
          int iClassIndex       = oIStream.readUnsignedShort();
          int iNameAndTypeIndex = oIStream.readUnsignedShort();
          oPoolMgr.addFieldref ( iClassIndex, iNameAndTypeIndex );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_Fieldref: class_index " + iClassIndex + " / name_and_type_index " + iNameAndTypeIndex );
          }
          break;
        }
        case CONSTANT_Methodref:
        {
          int iClassIndex       = oIStream.readUnsignedShort();
          int iNameAndTypeIndex = oIStream.readUnsignedShort();
          oPoolMgr.addMethodref ( iClassIndex, iNameAndTypeIndex );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_Methodref: class_index " + iClassIndex + " / name_and_type_index " + iNameAndTypeIndex );
          }
          break;
        }
        case CONSTANT_InterfaceMethodref:
        {
          int iClassIndex       = oIStream.readUnsignedShort();
          int iNameAndTypeIndex = oIStream.readUnsignedShort();
          oPoolMgr.addInterfaceMethodref ( iClassIndex, iNameAndTypeIndex );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_InterfaceMethodref: class_index " + iClassIndex + " / name_and_type_index " + iNameAndTypeIndex );
          }
          break;
        }
        case CONSTANT_NameAndType:
        {
          int iNameIndex       = oIStream.readUnsignedShort();
          int iDescriptorIndex = oIStream.readUnsignedShort();
          oPoolMgr.addNameAndType ( iNameIndex, iDescriptorIndex );
          if ( fConstantPool )
          {
            oOutput.println ( "CONSTANT_NameAndType: name_index " + iNameIndex + " / descriptor_index " + iDescriptorIndex );
          }
          break;
        }
        default:
           throw new DisAssException ( "Tag " + iTag + " is not a java tag." );
      }
    }
  }     

  private void lookAccessFlag ( boolean _fFunc, int _iFlag ) throws Exception
  {
    if ( (_iFlag&ACC_PUBLIC) == ACC_PUBLIC )
      oOutput.print ( "public " );
    if ( (_iFlag&ACC_PRIVATE) == ACC_PRIVATE )
      oOutput.print ( "private " );
    if ( (_iFlag&ACC_PROTECTED) == ACC_PROTECTED )
      oOutput.print ( "protected " );
    if ( (_iFlag&ACC_STATIC) == ACC_STATIC )
      oOutput.print ( "static " );
    if ( (_iFlag&ACC_FINAL) == ACC_FINAL )
      oOutput.print ( "final " );
    if ( (_iFlag&ACC_SUPER) == ACC_SUPER )
    {
      if ( _fFunc )
        oOutput.print ( "synchronized " );
      else
        oOutput.print ( "super " );
    }
    if ( (_iFlag&ACC_VOLATILE) == ACC_VOLATILE )
      oOutput.print ( "volatile " );
    if ( (_iFlag&ACC_TRANSIENT) == ACC_TRANSIENT )
      oOutput.print ( "transient " );
    if ( (_iFlag&ACC_NATIVE) == ACC_NATIVE )
      oOutput.print ( "native " );
    if ( (_iFlag&ACC_INTERFACE) == ACC_INTERFACE )
      oOutput.print ( "interface " );
    if ( (_iFlag&ACC_ABSTRACT) == ACC_ABSTRACT )
      oOutput.print ( "abstract " );
  }

  private void printAccessFlag () throws Exception
  {
    int iAccessFlag = oIStream.readUnsignedShort();
    lookAccessFlag ( true, iAccessFlag );
  }
  
  private void printClass () throws Exception
  {
    oOutput.println();
    oOutput.print ( ".class " );
    int iAccessFlag = oIStream.readUnsignedShort();
    lookAccessFlag ( true, iAccessFlag );
    int iClass = oIStream.readUnsignedShort();    
    oOutput.println ( oPoolMgr.getClass(iClass) );
    iClass = oIStream.readUnsignedShort();
    oOutput.println ( ".super " + oPoolMgr.getClass(iClass) );
  }
  
  private void printInterfaces () throws Exception
  {
    int iCount = oIStream.readUnsignedShort();
    oOutput.println();
    oOutput.print ( ".interface " );
    for ( int i = 0; i < iCount; i++ )
    {
      int iClassIndex = oIStream.readUnsignedShort();
      oOutput.println ( oPoolMgr.getClass(iClassIndex) + " " );
    }
    oOutput.println();
  }

  private void printFields () throws Exception
  {
    int iCount = oIStream.readUnsignedShort();
    for ( int i = 0; i < iCount; i++ )
    {
      int iFlag = oIStream.readUnsignedShort();
      oOutput.print ( ".field " );
      lookAccessFlag ( true, iFlag );
      int iNameIndex       = oIStream.readUnsignedShort();
      int iDescriptorIndex = oIStream.readUnsignedShort();
      oOutput.print ( oPoolMgr.getUtf8(iDescriptorIndex) + " " + oPoolMgr.getUtf8(iNameIndex) );
      printAttributes();
    }
  }
  
  private void printMethods () throws Exception
  {
    int iCount = oIStream.readUnsignedShort();
    oOutput.println();
    for ( int i = 0; i < iCount; i++ )
    {
      int iFlag = oIStream.readUnsignedShort();
      oOutput.println();
      oOutput.print ( ".method " );
      lookAccessFlag ( true, iFlag );
      int iNameIndex       = oIStream.readUnsignedShort();
      int iDescriptorIndex = oIStream.readUnsignedShort();
      oOutput.println ( oPoolMgr.getUtf8(iNameIndex) + " " + oPoolMgr.getUtf8(iDescriptorIndex) );
      printAttributes();
      oOutput.println ( ".end method" );
      oOutput.println();
    }
  }

  private void printAttributes () throws Exception
  {
    int iCount = oIStream.readUnsignedShort();
    for ( int i = 0; i < iCount; i++ )
    {
      int iIndexName = oIStream.readUnsignedShort();
      int iLength    = oIStream.readInt();

      if ( (oPoolMgr.getUtf8(iIndexName)).compareTo(NAME_CODE) == 0 )
      {  // "Code" (JVM 4.7.4)
        cLabelMgr oLabelMgr   = new cLabelMgr();
        int       iMaxStack   = oIStream.readUnsignedShort();
        int       iMaxLocals  = oIStream.readUnsignedShort();
        int       iCodeLength = oIStream.readInt();
        int       iNr         = 1;
        oOutput.println ( "  .limit stack " + iMaxStack );
        oOutput.println ( "  .limit locals " + iMaxLocals );
        oOutput.println();
        // Code auslesen
        int[] iaCode = new int[iCodeLength];
        for ( int k = 0; k < iCodeLength; k++ )
        {
          iaCode[k] = oIStream.readUnsignedByte();
        }
        // Exceptions auslesen
        int iExceptionLength = oIStream.readUnsignedShort();
        cException[] oEx = new cException[iExceptionLength];
        for ( int k = 0; k < iExceptionLength; k++ )
        {
          oEx[k] = new cException();
          oEx[k].iStartPC    = oIStream.readUnsignedShort();
          oEx[k].iEndPC      = oIStream.readUnsignedShort();
          oEx[k].iHandlerPC  = oIStream.readUnsignedShort();
          oEx[k].iCatchIndex = oIStream.readUnsignedShort();
          oOutput.print ( "  .catch " + oPoolMgr.getClass(oEx[k].iCatchIndex) + " from LabelS" + k + " to LabelE" );
          oOutput.println ( k + " using Handler" + k + "    ; " + oEx[k].iStartPC + " : " + oEx[k].iEndPC + " : " + oEx[k].iCatchIndex );
        }

        // Pass 1: Verarbeitung der Spr"unge ohne Ausgabe
        for ( int k = 0; k < iCodeLength; k++ )
        {
          int iByteCode = iaCode[k];
          if ( opcLengths[iByteCode] == 0 )
          {
            System.err.print ( " Hier mit Parameter 0!!" );
            System.exit ( 1 );
          }
          switch ( iByteCode )
          {
            case 148:  // 'lcmp'
            case 149:  // 'fcmpl'
            case 150:  // 'fcmpg'
            case 151:  // 'dcmpl'
            case 152:  // 'dcmpg'
            case 153:  // 'ifeq'
            case 154:  // 'ifne'
            case 155:  // 'iflt'
            case 156:  // 'ifge'
            case 157:  // 'ifgt'
            case 158:  // 'ifle'
            case 159:  // 'if_icmpeq'
            case 160:  // 'if_icmpne'
            case 161:  // 'if_icmplt'
            case 162:  // 'if_icmpge'
            case 163:  // 'if_icmpgt'
            case 164:  // 'if_icmple'
            case 165:  // 'if_acmpeq'
            case 166:  // 'if_acmpne'
            case 167:  // 'goto'
            case 168:  // 'jsr'           
            case 198:  // 'ifnull'
            case 199:  // 'ifnonull'
            {
              short sOffset = (short)((iaCode[k+1]<<8)|iaCode[k+2]);
              oLabelMgr.addLabel ( sOffset+k );
              k += 2;
              break;
            }
            case 171:  // 'loopupswitch'
            {
              // erste Adresse muss /4 teilbar sein
              int iOldAddr = k;
              k = k+(4-(k%4));
              int iDefault = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              int iNPairs = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              for ( int l = 0; l < iNPairs; l++ )
              {
                int iMatch = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
                k += 4;
                int iOffset = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
                k += 4;
                iOffset += iOldAddr;
                oLabelMgr.addLabel ( iOffset );
              }
              iDefault += iOldAddr;
              oLabelMgr.addLabel ( iDefault );
              k--;
              break;
            }
            case 170:  // 'tableswitch'
            {
              // erste Adresse muss /4 teilbar sein
              int iOldAddr = k;
              k = k+(4-(k%4));
              int iDefault = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              int iLow     = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              int iHigh    = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              for ( int l = iLow; l <= iHigh; l++ )
              {
                int iOffset = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
                iOffset += iOldAddr;
                k += 4;
                oLabelMgr.addLabel ( iOffset );
              }
              iDefault += iOldAddr;
              oLabelMgr.addLabel ( iDefault );
              k--;
              break;
            }
            default:
            {
              for ( int l = 0; l < (opcLengths[iByteCode]-1); l++ )
              {
                k++;
              }
            }
          }
        }

        // Pass 2: Verarbeitung der Opcodes mit Ausgabe in File
        for ( int k = 0; k < iCodeLength; k++ )
        {
          for ( int m = 0; m < iExceptionLength; m++ )
          {
            if ( oEx[m].iStartPC == k )
            {
              oOutput.println ( "LabelS" + m + ":" );
            }
            if ( oEx[m].iEndPC == k )
            {
              oOutput.println ( "LabelE" + m + ":" );
            }
            if ( oEx[m].iHandlerPC == k )
            {
              oOutput.println ( "Handler" + m + ":" );
            }
          }
          int iLabelNr = 0;
          if ( (iLabelNr=oLabelMgr.getLabel(k)) != 0 )
          {
            oOutput.println ( "LabelM" + iLabelNr + ":" );
          }
          int iByteCode = iaCode[k];
          oOutput.print ( "  " + k + " : " + opcNames[iByteCode] );
          if ( opcLengths[iByteCode] == 0 )
          {
            throw new DisAssException ( "Hier mit Parameter 0!." );
          }
          switch ( iByteCode )
          {
            case 18:   // 'ldc'
            {
              int iIndex = iaCode[k+1];
              k++;
              switch ( oPoolMgr.getTagOf(iIndex) )
              {
                case CONSTANT_Float:
                  oOutput.print ( " " + oPoolMgr.getFloat(iIndex) );
                  break;
                case CONSTANT_Integer:
                  oOutput.print ( " " + oPoolMgr.getInteger(iIndex) );
                  break;
                case CONSTANT_String:
                  oOutput.print ( " \"" + oPoolMgr.getString(iIndex) + "\"" );
                  break;
                default:
                  throw new DisAssException ( "cDisAss - unknown tag." );
              }
              oOutput.println ( "   ; " + iIndex );
              break;
            }
            case 19:   // 'ldc_w'
            {
              int iIndex = (iaCode[k+1]<<8)|iaCode[k+2];
              k += 2;
              switch ( oPoolMgr.getTagOf(iIndex) )
              {
                case CONSTANT_Float:
                  oOutput.print ( " " + oPoolMgr.getFloat(iIndex) );
                  break;
                case CONSTANT_Integer:
                  oOutput.print ( " " + oPoolMgr.getInteger(iIndex) );
                  break;
                case CONSTANT_String:
                  oOutput.print ( " \"" + oPoolMgr.getString(iIndex) + "\"" );
                  break;
                default:
                  throw new DisAssException ( "cDisAss - unknown tag." );
              }
              oOutput.println ( "    ; " + iIndex );
              break;
            }
            case 148:  // 'lcmp'
            case 149:  // 'fcmpl'
            case 150:  // 'fcmpg'
            case 151:  // 'dcmpl'
            case 152:  // 'dcmpg'
            case 153:  // 'ifeq'
            case 154:  // 'ifne'
            case 155:  // 'iflt'
            case 156:  // 'ifge'
            case 157:  // 'ifgt'
            case 158:  // 'ifle'
            case 159:  // 'if_icmpeq'
            case 160:  // 'if_icmpne'
            case 161:  // 'if_icmplt'
            case 162:  // 'if_icmpge'
            case 163:  // 'if_icmpgt'
            case 164:  // 'if_icmple'
            case 165:  // 'if_acmpeq'
            case 166:  // 'if_acmpne'
            case 167:  // 'goto'
            case 168:  // 'jsr'           
            case 198:  // 'ifnull'
            case 199:  // 'ifnonull'
            {
              short sOffset = (short)((iaCode[k+1]<<8)|iaCode[k+2]);
              oOutput.println ( " LabelM" + oLabelMgr.addLabel(sOffset+k) + "    ; " + (sOffset+k) );
              k += 2;
              break;
            }
            case 171:  // 'loopupswitch'
            {
              // erste Adresse muss /4 teilbar sein
              int iOldAddr = k;
              k = k+(4-(k%4));
              int iDefault = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              int iNPairs = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              oOutput.println ( " " + iNPairs );
              for ( int l = 0; l < iNPairs; l++ )
              {
                int iMatch = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
                k += 4;
                int iOffset = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
                k += 4;
                iOffset += iOldAddr;
                oOutput.println ( "    " + iMatch + ":LabelM" + oLabelMgr.addLabel(iOffset) );
              }
              iDefault += iOldAddr;
              oOutput.println ( "    default:LabelM" + oLabelMgr.addLabel(iDefault) );
              k--;
              break;
            }
            case 170:  // 'tableswitch'
            {
              // erste Adresse muss /4 teilbar sein
              int iOldAddr = k;
              k = k+(4-(k%4));
              int iDefault = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              int iLow     = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              int iHigh    = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
              k += 4;
              oOutput.println ( " " + iLow + " to " + iHigh + ":" );
              for ( int l = iLow; l <= iHigh; l++ )
              {
                int iOffset = (iaCode[k]<<24)|(iaCode[k+1]<<16)|(iaCode[k+2]<<8)|iaCode[k+3];
                iOffset += iOldAddr;
                k += 4;
                oOutput.println ( "    " + l + ":LabelM" + oLabelMgr.addLabel(iOffset) );
              }
              iDefault += iOldAddr;
              oOutput.println ( "    default:LabelM" + oLabelMgr.addLabel(iDefault) );
              k--;
              break;
            }
            case 178:  // 'getstatic'
            case 180:  // 'getfield'
            case 181:  // 'putfield'
            {
              int iIndex           = (iaCode[k+1]<<8)|iaCode[k+2];
              StringBuffer strClass      = new StringBuffer();
              StringBuffer strName       = new StringBuffer();
              StringBuffer strDescriptor = new StringBuffer();
              oPoolMgr.getFieldref ( iIndex, strClass, strName, strDescriptor );
              oOutput.println ( " " + strClass.toString() + "." + strName.toString() + " " + strDescriptor.toString() + "     ; " + iIndex );
              k += 2;
              break;
            }
            case 182:  // 'invokevirtual'
            case 183:  // 'invokespecial'
            case 184:  // 'invokestatic'
            {
              int iIndex           = (iaCode[k+1]<<8)|iaCode[k+2];
              StringBuffer strClass      = new StringBuffer();
              StringBuffer strName       = new StringBuffer();
              StringBuffer strDescriptor = new StringBuffer();
              oPoolMgr.getMethodref ( iIndex, strClass, strName, strDescriptor );
              oOutput.println ( " " + strClass.toString() + "." + strName.toString() + " " + strDescriptor.toString() + "     ; " + iIndex );
              k += 2;
              break;
            }
            case 187:  // 'new'
            case 192:  // 'checkcast'
            {
              int iIndex = (iaCode[k+1]<<8)|iaCode[k+2];
              oOutput.println ( " " + oPoolMgr.getClass(iIndex) + "    ; " + iIndex );
              k += 2;
              break;
            }
            default:
            {
              for ( int l = 0; l < (opcLengths[iByteCode]-1); l++ )
              {
                k++;
                oOutput.print ( " " + iaCode[k] );
              }
              oOutput.println();
            }
          }
        }
        oOutput.println();
        printAttributes();
      }
      else
      {
        oOutput.print ( "; .attributes " + oPoolMgr.getUtf8(iIndexName) + ": " );
        if ( (oPoolMgr.getUtf8(iIndexName)).compareTo(NAME_SOURCEFILE) == 0 )
        {  // "SourceFile" (JVM 4.7.2)
          if ( iLength != 2 )
            throw new DisAssException ( "cDisAss - unknown tag." );
          int iSourceIndex = oIStream.readUnsignedShort();
          oOutput.print ( oPoolMgr.getUtf8(iSourceIndex) );
        }
        else if ( oPoolMgr.getUtf8(iIndexName).compareTo(NAME_CONSTANTVALUE) == 0 )
        {  // "ConstantValue" (JVM 4.7.3)
          if ( iLength != 2 )
          {
            throw new DisAssException ( "cDisAss - ConstantValue error." );
          }
          int iCVIndex = oIStream.readUnsignedShort();
          switch ( oPoolMgr.getTagOf(iCVIndex) )
          {
            case CONSTANT_Long:
              oOutput.print ( " = " + oPoolMgr.getLong(iCVIndex) );
              break;
            case CONSTANT_Float:
              oOutput.print ( " = " + oPoolMgr.getFloat(iCVIndex) );
              break;
            case CONSTANT_Double:
              oOutput.print ( " = " + oPoolMgr.getDouble(iCVIndex) );
              break;
            case CONSTANT_Integer:
              oOutput.print ( " = " + oPoolMgr.getInteger(iCVIndex) );
              break;
            case CONSTANT_String:
              oOutput.print ( " = " + oPoolMgr.getString(iCVIndex) );
              break;
            default:
              throw new DisAssException ( "cDisAss - unknown tag." );
          }
        }
        else if ( (oPoolMgr.getUtf8(iIndexName)).compareTo(NAME_EXCEPTIONS) == 0 )
        {  // "Exceptions" (JVM 4.7.5)
          int iNumberOfExceptions = oIStream.readUnsignedShort();
          for ( int x = 0; x < iNumberOfExceptions; x++ )
          {
            int iIndex = oIStream.readUnsignedShort();
            if ( iIndex != 0 )
            {
              oOutput.print ( oPoolMgr.getClass(iIndex) + " " );
            }
          }
        }
        else if ( (oPoolMgr.getUtf8(iIndexName)).compareTo(NAME_LINENUMBERTABLE) == 0 )
        {  // "LineNumberTable" (JVM 4.7.6)
          int iLines = oIStream.readUnsignedShort();
          for ( int x = 0; x < iLines; x++ )
          {
            int iPC = oIStream.readUnsignedShort();
            int iLN = oIStream.readUnsignedShort();
            oOutput.print ( "(PC: " + iPC + "/LN: " + iLN + ") " );
          }
        }
        else
        {
          oOutput.print ( "..." );
          oIStream.skipBytes ( iLength );
        }
        oOutput.println();
      }      
    }
    if ( iCount == 0 )
      oOutput.println();
  }

  public void run ( String _strFile ) throws Exception
  {
    int          iIdx    = _strFile.lastIndexOf ( '.' );
    StringBuffer sbOFile = new StringBuffer ( _strFile.substring(0,iIdx) );

    sbOFile.append ( ".disass" );
    oIStream = new DataInputStream ( new FileInputStream(_strFile) );
    oOutput  = new PrintStream ( new FileOutputStream(sbOFile.toString()) );
    magicTest();
    printVersion();
    constantPool();
    printClass();
    printInterfaces();
    printFields();
    printMethods();
    printAttributes();
    oIStream.close();
    oOutput.close();
  }
}
