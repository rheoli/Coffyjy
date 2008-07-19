package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;

public class cCONSTANT_Integer_Info extends cCONSTANTPool
{
  int iBytes;
  
  public cCONSTANT_Integer_Info ()
  {
    iBytes = 0;
  }
  
  public void setIntegerInfo ( int _iBytes )
  {
    setTag ( CONSTANT_Integer );
    iBytes = _iBytes;
  }
  
  public int getBytes ()
  {
    return ( iBytes );
  }
}
