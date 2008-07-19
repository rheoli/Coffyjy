package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;

public class cCONSTANT_Long_Info extends cCONSTANTPool
{
  long lBytes;
  
  public cCONSTANT_Long_Info ()
  {
    lBytes = 0;
  }
  
  public void setLongInfo ( long _lBytes )
  {
    setTag ( CONSTANT_Long );
    lBytes = _lBytes;
  }
  
  public long getLong ()
  {
    return ( lBytes );
  }
}
