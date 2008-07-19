package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;

public class cCONSTANT_Utf8_Info extends cCONSTANTPool
{
  String strBytes;
  
  public cCONSTANT_Utf8_Info ()
  {
    strBytes = null;
  }
  
  public void setUtf8Info ( String _strBytes )
  {
    setTag ( CONSTANT_Utf8 );
    strBytes = _strBytes;
  }
  
  public String getString ()
  {
    return ( strBytes );
  }
}
