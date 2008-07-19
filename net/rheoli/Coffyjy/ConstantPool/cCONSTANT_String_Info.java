package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;

public class cCONSTANT_String_Info extends cCONSTANTPool
{
  int iStringIndex;
  
  public cCONSTANT_String_Info ()
  {
    iStringIndex = 0;
  }
  
  public void setStringInfo ( int _iIndex )
  {
    setTag ( CONSTANT_String );
    iStringIndex = _iIndex;
  }
  
  public int getIndex ()
  {
    return ( iStringIndex );
  }
}
