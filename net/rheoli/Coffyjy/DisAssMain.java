/*
    D I S A S S : Disassembler f"ur JavaByteCodeClassFiles
     
    DisAssMain.java: Hauptprogramm

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

package net.rheoli.Coffyjy;

import java.io.*;
import net.rheoli.Coffyjy.DisAss.*;

public class DisAssMain
{
  public static void main ( String args[] )
  {
    System.out.println ( "DisAss Version 1.0.1, Copyright (c) 1997-2008 Stephan Toggweiler" );
    System.out.println ( "DisAss comes with ABSOLUTELY NO WARRANTY; see the file MIT-License for details." );
    System.out.println ();
    
    if ( args.length != 1 )
    {
      System.out.println ( " Usage: java net.rheoli.Coffyjy.DisAssMain <filename>" );
      System.exit ( 0 );
    }
    cDisAss oDisAss = new cDisAss();
    try
    {
      oDisAss.run ( args[0] );
    }
    catch ( Exception e )
    {
      System.err.println ( e );
      System.exit ( 1 );
    }
  }
}
