/*
    D I S A S S : Disassembler f"ur JavaByteCodeClassFiles
     
    cLabelMgr.java: Manager f"ur die Labelverwaltung (Branches, ...)

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

/**
 * cLabelMgr.java
 * Manager f"ur die Labelverwaltung (Branches, ...)
 * @author Stephan Toggweiler
 * @version $Id$
 */

package net.rheoli.Coffyjy.General;

import java.io.*;
import net.rheoli.Coffyjy.General.*;

public class cLabelMgr
{
  private int[]    iaLine;
  private String[] saLabel;
  private int      iLabelNr;

  /**
   * Konstruktor mit Grundinitialisator
   */
  public cLabelMgr ()
  {
    iaLine    = new int[300];
    saLabel   = new String[300];
    iLabelNr  = 1;
  }

  /**
   * Alles neu initialisieren
   */
  public void newMethod ()
  {
    iLabelNr = 1;
  }

  /**
   * Neues Label hinzufuegen
   * @param  _strName	Name des Labels
   * @return ID	des Labels
   */ 
  public int addLabel ( String _strLabel )
  {
    int iLabel = 0;
    for ( int i= 1; i < iLabelNr; i++ )
    {
      if ( saLabel[i].compareTo(_strLabel) == 0 )
      {
        iLabel = i;
        break;
      }
    }
    if ( iLabel != 0 )
    {
      return ( iLabel );
    }
    iaLine[iLabelNr]  = 0;
    saLabel[iLabelNr] = _strLabel;
    iLabelNr++;
    return ( iLabelNr-1 );
  }

  /**
   * Neues Label hinzufuegen
   * @param  _iLine	Zeilennummer des Labels
   * @return ID	des Labels
   */ 
  public int addLabel ( int _iLine )
  {
    int iLabel = 0;
    for ( int i= 1; i < iLabelNr; i++ )
    {
      if ( iaLine[i] == _iLine )
      {
        iLabel = i;
        break;
      }
    }
    if ( iLabel != 0 )
    {
      return ( iLabel );
    }
    iaLine[iLabelNr]  = _iLine;
    saLabel[iLabelNr] = null;
    iLabelNr++;
    return ( iLabelNr-1 );
  }

  /**
   * Anhand der Zeilennummer die Label-ID suchen
   * @param _iLine	Zeilennummer
   * @return ID des Labels
   * @exception cLabelException wenn die Zeilennummer nicht vorhanden ist.
   */
  public int getLabel ( int _iLine ) throws LabelException
  {
    int iLabel = 0;
    for ( int i = 1; i < iLabelNr; i++ )
    {
      if ( iaLine[i] == _iLine )
      {
        iLabel = i;
        break;
      }
    }
/*    if ( iLabel == 0 )
    {
      throw new LabelException ( "cLabelMgr - Label not found" );
    } */
    return ( iLabel );
  }
  
  /**
   * Einem Label eine Bytecode-Zeilennummer zuweisen.
   * @param _strLabel	Labelname
   * @param _iLineNr	einzutragende Zeilennummer
   */
  public void setLineNumber ( String _strLabel, int _iLineNr )
  {
    int iLabel = 0;
    for ( int i= 1; i < iLabelNr; i++ )
    {
      if ( saLabel[i].compareTo(_strLabel) == 0 )
      {
        iLabel = i;
        break;
      }
    }
    if ( iLabel != 0 )
    {
      iaLine[iLabel] = _iLineNr;
      return;
    }
    iaLine[iLabelNr]  = _iLineNr;
    saLabel[iLabelNr] = _strLabel;
    iLabelNr++;
  }

  /**
   * Bytecode-Zeilennummer von gegebenen Label auslesen.
   * @param _iID	Label-ID
   * @return Bytecode-Zeilennummer
   * @exception cLabelException wenn das Label nicht vorhanden ist.
   */
  public int getLineNumber ( int _iID ) throws LabelException
  {
    if ( (_iID<1) || (_iID>iLabelNr) )
    {
      throw new LabelException ( "cLabelMgr - Label not found" );
    }
    return ( iaLine[_iID] );
  }
}
    
