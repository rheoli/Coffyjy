package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;

public class cCONSTANT_Float_Info extends cCONSTANTPool
{
  float fBytes;
  
  public cCONSTANT_Float_Info ()
  {
    fBytes = (float)0.0;
  }
  
  public void setFloatInfo ( float _fBytes )
  {
    setTag ( CONSTANT_Float );
    fBytes = _fBytes;
  }
  
  public float getFloat ()
  {
    return ( fBytes );
  }
}
