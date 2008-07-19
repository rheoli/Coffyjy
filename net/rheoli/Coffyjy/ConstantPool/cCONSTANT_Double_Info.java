package net.rheoli.Coffyjy.ConstantPool;

import java.io.*;

public class cCONSTANT_Double_Info extends cCONSTANTPool
{
  double dBytes;
  
  public cCONSTANT_Double_Info ()
  {
    dBytes = 0.0;
  }
  
  public void setDoubleInfo ( double _dBytes )
  {
    setTag ( CONSTANT_Double );
    dBytes = _dBytes;
  }
  
  public double getDouble ()
  {
    return ( dBytes );
  }
}
