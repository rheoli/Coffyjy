/*
    D I S A S S : Disassembler f"ur JavaByteCodeClassFiles
     
    cCONSTANTPool.java: Oberklasse aller m"oglichen ConstantPool-Typen.

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

public class cCONSTANTPool implements cRuntimeConstants
{
  private int iTag;
  
  public int getTag ()
  {
    return ( iTag );
  }
  
  public void setTag ( int _iTag )
  {
    iTag = _iTag;
  }
  
  public int getIndex () throws Exception
  {
    throw new CONSTANTException ( "cCONSTANTPool - Function not definied." );
  }

  public int getDescriptorIndex () throws Exception
  {
    throw new CONSTANTException ( "cCONSTANTPool - Function not definied." );
  }
  
  public int getNameAndTypeIndex () throws Exception
  {
    throw new CONSTANTException ( "cCONSTANTPool - Function not definied." );
  }

  public int getBytes () throws Exception
  {
    throw new CONSTANTException ( "cCONSTANTPool - Function not definied." );
  }
  
  public float getFloat () throws Exception
  {
    throw new CONSTANTException ( "cCONSTANTPool - Function not definied." );
  }
  
  public long getLong () throws Exception
  {
    throw new CONSTANTException ( "cCONSTANTPool - Function not definied." );
  }
  
  public double getDouble () throws Exception
  {
    throw new CONSTANTException ( "cCONSTANTPool - Function not definied." );
  }
  
  public String getString () throws Exception
  {
    throw new CONSTANTException ( "cCONSTANTPool - Function not definied." );
  }
}
